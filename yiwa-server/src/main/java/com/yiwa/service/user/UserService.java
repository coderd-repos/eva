package com.yiwa.service.user;

import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.user.model.User;
import java.util.List;

/**
 * 示例Service定义
 * @author Caesar Liu
 * @date 2021/05/15 18:44
 */
public interface UserService {

    /**
     * 创建
     * @author Caesar Liu
     * @date 2021/05/15 18:44
     */
    Integer create(User user);

    /**
     * 主键删除
     * @author Caesar Liu
     * @date 2021/05/15 18:44
     */
    void deleteById(Integer id);

    /**
     * 批量主键删除
     * @author Caesar Liu
     * @date 2021/05/15 18:44
     */
    void deleteByIdInBatch(List<Integer> ids);

    /**
     * 主键更新
     * @author Caesar Liu
     * @date 2021/05/15 18:44
     */
    void updateById(User user);

    /**
     * 批量主键更新
     * @author Caesar Liu
     * @date 2021/05/15 18:44
     */
    void updateByIdInBatch(List<User> users);

    /**
     * 主键查询
     * @author Caesar Liu
     * @date 2021/05/15 18:44
     */
    User findById(Integer id);

    /**
     * 条件查询单条记录
     * @author Caesar Liu
     * @date 2021/05/15 18:44
     */
    User findOne(User user);

    /**
     * 条件查询
     * @author Caesar Liu
     * @date 2021/05/15 18:44
     */
    List<User> findList(User user);

    /**
     * 分页查询
     * @author Caesar Liu
     * @date 2021/05/15 18:44
     */
    PageData<User> findPage(PageWrap<User> pageWrap);
    
    /**
     * 条件统计
     * @author Caesar Liu
     * @date 2021/05/15 18:44
     */
    long count(User user);
}