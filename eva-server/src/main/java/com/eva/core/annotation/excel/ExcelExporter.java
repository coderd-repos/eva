package com.eva.core.annotation.excel;

import com.eva.core.constants.ResponseStatus;
import com.eva.core.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.core.annotation.AnnotationConfigurationException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel导出实现
 * @author Eva.Caesar Liu
 * @date 2021-06-19 00:09
 */
@Data
public class ExcelExporter<T> {

    private static final String DEFAULT_SHEET_NAME = "Sheet1";

    private Class<T> modelClass;

    private ExcelExporter(){}

    /**
     * 构造器
     * @param modelClass 实体Class对象
     */
    public static <T> ExcelExporter<T> build(Class<T> modelClass) {
        ExcelExporter<T> excelExporter = new ExcelExporter<>();
        excelExporter.setModelClass(modelClass);
        return excelExporter;
    }

    /**
     * 导出到指定输出流
     * @param data 数据
     * @param sheetName Sheet名称
     * @param os 输出流
     */
    public void export (List<T> data, String sheetName, OutputStream os) {
        SXSSFWorkbook sxssfWorkbook;
        try {
            sxssfWorkbook = new SXSSFWorkbook();
            Sheet sheet = sxssfWorkbook.createSheet(sheetName);
            // 创建列头
            sheet.createFreezePane(0, 1);
            Row header = sheet.createRow(0);
            List<ColumnInfo> columns = this.getColumns();
            for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
                ColumnInfo column = columns.get(columnIndex);
                Cell cell = header.createCell(columnIndex);
                cell.setCellValue(column.columnConfig.name());
                // 列宽设置
                if (column.columnConfig.width() == -1) {
                    sheet.setColumnWidth(columnIndex, column.columnConfig.name().length() * 2 * 256);
                } else {
                    sheet.setColumnWidth(columnIndex, column.columnConfig.width() * 2 * 256);
                }
                // 设置列头单元格
                configHeaderCell(sxssfWorkbook, cell, column.columnConfig);
            }
            // 创建数据记录
            for (int rowIndex = 0; rowIndex < data.size(); rowIndex++) {
                Row row = sheet.createRow(rowIndex + 1);
                for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
                    ColumnInfo column = columns.get(columnIndex);
                    Cell cell = row.createCell(columnIndex);
                    cell.setCellValue(getCellData(column, data.get(rowIndex)));
                    // 设置数据单元格
                    configDataCell(sxssfWorkbook, cell, column.columnConfig);
                }
            }
            sxssfWorkbook.write(os);
            os.close();
        } catch (Exception e) {
            throw new BusinessException(ResponseStatus.EXPORT_EXCEL_ERROR, e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 导出至响应流
     * @param data 数据
     * @param fileName Excel文件名
     * @param sheetName Sheet名称
     * @param response HttpServletResponse对象
     */
    public void export (List<T> data, String fileName, String sheetName, HttpServletResponse response) {
        try {
            String encodeFileName = URLEncoder.encode(fileName, Charset.forName("UTF-8").toString()) + ".xlsx";
            response.setHeader("Content-Disposition","attachment;filename=" + encodeFileName);
            response.setContentType("application/octet-stream");
            response.setHeader("eva-opera-type", "download");
            response.setHeader("eva-download-filename", encodeFileName);
            this.export(data, sheetName, response.getOutputStream());
        } catch (IOException e) {
            throw new BusinessException(ResponseStatus.EXPORT_EXCEL_ERROR, e);
        }
    }

    /**
     * 导出至响应流
     * @param data 数据
     * @param fileName Excel文件名
     * @param response HttpServletResponse对象
     */
    public void export (List<T> data, String fileName, HttpServletResponse response) {
        this.export(data, fileName, DEFAULT_SHEET_NAME, response);
    }

    /**
     * 获取列集合
     */
    private List<ColumnInfo> getColumns () {
        Map<Integer, ColumnInfo> sortedFields = new TreeMap<>();
        Field[] fields = modelClass.getDeclaredFields();
        int index = 0;
        for (Field field : fields) {
            ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
            if (excelColumn == null) {
                continue;
            }
            if (sortedFields.get(excelColumn.index()) != null) {
                throw new AnnotationConfigurationException("Excel column contains the same index.");
            }
            sortedFields.put(excelColumn.index() == -1 ? index : excelColumn.index(), new ColumnInfo(excelColumn, field));
            index++;
        }
        return new ArrayList<>(sortedFields.values());
    }

    /**
     * 配置数据单元格
     */
    private void configDataCell (SXSSFWorkbook workbook, Cell cell, ExcelColumn columnConfig) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(columnConfig.align());
        Font font = workbook.createFont();
        // 字体颜色
        font.setColor(columnConfig.color().getIndex());
        // 粗体
        font.setBold(columnConfig.bold());
        // 斜体
        font.setItalic(columnConfig.italic());
        style.setFont(font);
        // 边框
        configCellBorder(style);
        cell.setCellStyle(style);
    }

    /**
     * 配置列头单元格
     */
    private void configHeaderCell (SXSSFWorkbook workbook, Cell cell, ExcelColumn columnConfig) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(columnConfig.align());
        // 设置边框
        configCellBorder(style);
        cell.setCellStyle(style);
    }

    /**
     * 配置单元格边框
     */
    private void configCellBorder (CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
    }

    /**
     * 处理单元格数据
     */
    private String getCellData (ColumnInfo columnInfo, T row) throws Exception {
        columnInfo.field.setAccessible(Boolean.TRUE);
        Object value = columnInfo.field.get(row);
        columnInfo.field.setAccessible(Boolean.FALSE);
        if (value == null) {
            return "";
        }
        String stringValue = value.toString();
        // 存在自定义数据处理器
        if (!columnInfo.columnConfig.handler().equals(ExcelDataHandlerAdapter.class)) {
            try {
                Object instance = columnInfo.columnConfig.handler().newInstance();
                Method formatMethod = columnInfo.columnConfig.handler().getMethod("format", Object[].class);
                List<Object> args = new ArrayList<>();
                args.add(value);
                for (String arg : columnInfo.columnConfig.args()) {
                    args.add(arg);
                }
                value = formatMethod.invoke(instance, new Object[]{args.toArray()});
                stringValue = value.toString();
            } catch (Exception e) {
                throw new IllegalStateException("Can not format data by " + columnInfo.columnConfig.handler(), e);
            }
        }
        // 日期处理
        if (!"".equals(columnInfo.columnConfig.dateFormat()) && value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(columnInfo.columnConfig.dateFormat());
            stringValue = sdf.format((Date) value);
        }
        // 值映射
        if (!"".equals(columnInfo.columnConfig.valueMapping())) {
            String[] segs = columnInfo.columnConfig.valueMapping().split(";");
            for (String seg : segs) {
                String[] mapping = seg.split("=");
                if (value.toString().equals(mapping[0].trim())) {
                    stringValue = mapping[1].trim();
                }
            }
        }
        // 前缀处理
        stringValue = columnInfo.columnConfig.prefix() + stringValue;
        // 后缀处理
        stringValue = stringValue + columnInfo.columnConfig.suffix();
        return stringValue;
    }

    @Data
    @AllArgsConstructor
    private static class ColumnInfo {

        private ExcelColumn columnConfig;

        private Field field;
    }

}
