package com.eva.core.annotation.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.core.annotation.AnnotationConfigurationException;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel导出实现
 * @author Caesar Liu
 * @date 2021-06-19 00:09
 */
@Data
public class ExcelExporter<T> {

    private Class<T> modelClass;

    private ExcelExporter(){}

    /**
     * 构造器
     */
    public static <T> ExcelExporter<T> build(Class<T> modelClass) {
        ExcelExporter<T> excelExporter = new ExcelExporter<>();
        excelExporter.setModelClass(modelClass);
        return excelExporter;
    }

    /**
     * 导出
     */
    public void export (List<T> data, String sheetName, OutputStream os) throws Exception {
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook();
        Sheet sheet = sxssfWorkbook.createSheet(sheetName);
        // 创建列头
        sheet.createFreezePane(0, 1);
        Row header = sheet.createRow(0);
        List<ColumnInfo> columns = this.getColumns();
        for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
            ColumnInfo column = columns.get(columnIndex);
            Cell cell = header.createCell(columnIndex);
            cell.setCellValue(column.columnConfig.name());
            //设置每列固定宽度
            sheet.setColumnWidth(column.columnConfig.width(), 20 * 256);
        }
        // 创建数据记录
        for (int rowIndex = 0; rowIndex < data.size(); rowIndex++) {
            Row row = sheet.createRow(rowIndex + 1);
            for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
                ColumnInfo column = columns.get(columnIndex);
                Cell cell = row.createCell(columnIndex);
                cell.setCellValue(getCellData(column, data.get(rowIndex)));
                sheet.setColumnWidth(column.columnConfig.width(), 20 * 256);
            }
        }
        sxssfWorkbook.write(os);
        os.close();
    }

    /**
     * 导出
     */
    public void export (List<T> data, String fileName, String sheetName, HttpServletResponse response) throws Exception {
        String encodeFileName = URLEncoder.encode(fileName, Charset.forName("UTF-8").toString()) + ".xlsx";
        response.setHeader("Content-Disposition","attachment;filename=" + encodeFileName);
        response.setContentType("application/octet-stream");
        response.setHeader("eva-opera-type", "download");
        response.setHeader("eva-download-filename", encodeFileName);
        this.export(data, sheetName, response.getOutputStream());
    }

    /**
     * 获取列集合
     */
    private List<ColumnInfo> getColumns () {
        Map<Integer, ColumnInfo> sortedMethods = new TreeMap<>();
        Field[] fields = modelClass.getDeclaredFields();
        int index = 0;
        for (Field field : fields) {
            ExcelColumn excelColumn = field.getAnnotation(ExcelColumn.class);
            if (excelColumn == null) {
                continue;
            }
            if (sortedMethods.get(excelColumn.index()) != null) {
                throw new AnnotationConfigurationException("Excel column contains the same index.");
            }
            sortedMethods.put(excelColumn.index() == -1 ? index : excelColumn.index(), new ColumnInfo(excelColumn, field));
            index++;
        }
        return new ArrayList<>(sortedMethods.values());
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
        // 日期处理
        if (!"".equals(columnInfo.columnConfig.dateFormat()) && columnInfo.field.getType().equals(Date.class)) {
            SimpleDateFormat sdf = new SimpleDateFormat(columnInfo.columnConfig.dateFormat());
            return sdf.format((Date) value);
        }
        // 值映射
        if (!"".equals(columnInfo.columnConfig.valueMapping())) {
            String[] segs = columnInfo.columnConfig.valueMapping().split(";");
            for (String seg : segs) {
                String[] mapping = seg.split("=");
                if (stringValue.equals(mapping[0].trim())) {
                    stringValue = mapping[1].trim();
                    return stringValue;
                }
            }
        }
        return stringValue;
    }

    @Data
    @AllArgsConstructor
    private static class ColumnInfo {

        private ExcelColumn columnConfig;

        private Field field;
    }

}
