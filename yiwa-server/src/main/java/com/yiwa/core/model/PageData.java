package com.yiwa.core.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * @author Caesar Liu
 * @date 2021/05/15 18:44
 */
@Data
public class PageData<T> implements Serializable {

    // 目标页
    private long page;

    // 一页多少行
    private long capacity;

    // 总记录数
    private long total;

    // 当前的数据
    private List<T> records;

    public PageData(long page, long capacity) {
        this.page = page;
        this.capacity = capacity;
    }
    
    public static <T> PageData<T> from(IPage<T> pageInfo) {
        PageData<T> pageData = new PageData<T>(pageInfo.getCurrent(), pageInfo.getSize());
        pageData.total = pageInfo.getTotal();
        pageData.records = pageInfo.getRecords();
        return pageData;
    }

    /**
     * 处理异常页容量
     * @author Caesar Liu
     * @date 2021/05/15 18:44
     */
    public long getCapacity () {
        return capacity <= 0 ? 10 : capacity;
    }

    /**
     * 计算总页码
     * @author Caesar Liu
     * @date 2021/05/15 18:44
     */
    public long getPageCount(){
        if(this.getTotal() % this.getCapacity() == 0){
            long pc = this.getTotal()/this.getCapacity();
            return pc == 0 ? 1 : pc;
        }
        return this.getTotal()/this.getCapacity() + 1;
    }

}