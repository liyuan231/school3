//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.service.impl;

import com.school.dao.RoleMapper;
import com.school.model.Role;
import com.school.model.Role.Column;
import com.school.model.RoleExample;
import com.school.model.RoleExample.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class RoleServiceImpl {
    @Resource
    RoleMapper roleMapper;

    public RoleServiceImpl() {
    }

    public Role querySelective(Integer id, String name) {
        RoleExample roleExample = new RoleExample();
        Criteria criteria = roleExample.createCriteria();
        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(id);
        }

        if (StringUtils.hasText(name)) {
            criteria.andNameEqualTo(name);
        }
        return this.roleMapper.selectOneByExampleSelective(roleExample, new Column[0]);
    }
}
