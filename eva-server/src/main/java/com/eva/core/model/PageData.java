package com.eva.core.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Eva.Caesar Liu
 * @date 2021/07/13 22:37
 */
@Data
@ApiModel("分页对象")
public class PageData<T> implements Serializable {

    @ApiModelProperty("目标页")
    private long page;

    @ApiModelProperty("页容量")
    private long capacity;

    @ApiModelProperty("总记录数")
    private long total;

    @ApiModelProperty("当前页的数据")
    private List<T> records;

    public PageData(long page, long capacity) {
        this.page = page;
        this.capacity = capacity;
    }

    /**
     * 根据MyBatis Plus分页对象组装
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    public static <T> PageData<T> from(IPage<T> pageInfo) {
        PageData<T> pageData = new PageData<T>(pageInfo.getCurrent(), pageInfo.getSize());
        pageData.total = pageInfo.getTotal();
        pageData.records = pageInfo.getRecords();
        return pageData;
    }

    /**
     * 根据MyBatis原生分页对象组装
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    public static <T> PageData<T> from(PageInfo<T> pageInfo) {
        PageData<T> pageData = new PageData<>(pageInfo.getPageNum(), pageInfo.getPageSize());
        pageData.total = pageInfo.getTotal();
        pageData.records = pageInfo.getList();
        return pageData;
    }

    /**
     * 处理异常页容量
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    public long getCapacity () {
        return capacity <= 0 ? 10 : capacity;
    }

    /**
     * @author Eva.Caesar Liu
     * @date 2021/07/13 22:37
     */
    @ApiModelProperty("总页数")
    public long getPageCount(){
        if(this.getTotal() % this.getCapacity() == 0){
            long pc = this.getTotal()/this.getCapacity();
            return pc == 0 ? 1 : pc;
        }
        return this.getTotal()/this.getCapacity() + 1;
    }

}
