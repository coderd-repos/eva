package com.yiwa.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiwa.core.model.PageData;
import com.yiwa.core.model.PageWrap;
import com.yiwa.dao.user.UserMapper;
import com.yiwa.dao.user.model.User;
import com.yiwa.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 示例Service实现
 * @author 懒猴子CG
 * @date 2021/05/14 09:55
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer create(User user) {
        userMapper.insert(user);
        return user.getId();
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public void deleteByIdInBatch(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) return;
        for (Integer id: ids) {
            this.deleteById(id);
        }
    }

    @Override
    public void updateById(User user) {
        userMapper.updateById(user);
    }

    @Override
    public void updateByIdInBatch(List<User> users) {
        if (CollectionUtils.isEmpty(users)) return;
        for (User user: users) {
            this.updateById(user);
        }
    }

    @Override
    public User findById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public User findOne(User user) {
        Wrapper<User> wrapper = new QueryWrapper(user);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public List<User> findList(User user) {
        Wrapper<User> wrapper = new QueryWrapper(user);
        return userMapper.selectList(wrapper);
    }

    @Override
    public PageData<User> findPage(PageWrap<User> pageWrap) {
        IPage<User> page = new Page<>(pageWrap.getPage(), pageWrap.getCapacity());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(pageWrap.getModel());
        for(PageWrap.SortData sortData: pageWrap.getSorts()) {
            if (sortData.getDirection().equalsIgnoreCase("DESC")) {
                queryWrapper.orderByDesc(sortData.getProperty());
            } else {
                queryWrapper.orderByAsc(sortData.getProperty());
            }
        }
        return PageData.from(userMapper.selectPage(page, queryWrapper));
    }

    @Override
    public long count(User user) {
        Wrapper<User> wrapper = new QueryWrapper(user);
        return userMapper.selectCount(wrapper);
    }
}