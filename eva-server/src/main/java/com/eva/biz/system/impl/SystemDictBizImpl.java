package com.eva.biz.system.impl;

import com.eva.biz.system.SystemDictBiz;
import com.eva.core.exception.BusinessException;
import com.eva.core.constants.ResponseStatus;
import com.eva.dao.system.model.SystemDict;
import com.eva.service.system.SystemDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemDictBizImpl implements SystemDictBiz {

    @Autowired
    private SystemDictService systemDictService;

    @Override
    public Integer create(SystemDict systemDict) {
        SystemDict queryDto = new SystemDict();
        queryDto.setCode(systemDict.getCode());
        queryDto.setDeleted(Boolean.FALSE);
        SystemDict dict = systemDictService.findOne(queryDto);
        if (dict != null) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "字典编码已存在");
        }
        return systemDictService.create(systemDict);
    }

    @Override
    public void updateById(SystemDict systemDict) {
        SystemDict queryDto = new SystemDict();
        queryDto.setCode(systemDict.getCode());
        queryDto.setDeleted(Boolean.FALSE);
        SystemDict dict = systemDictService.findOne(queryDto);
        if (dict != null && !dict.getId().equals(systemDict.getId())) {
            throw new BusinessException(ResponseStatus.DATA_EXISTS.getCode(), "字典编码已存在");
        }
        systemDictService.updateById(systemDict);
    }
}
