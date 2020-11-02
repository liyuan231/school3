//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.service.impl;

import com.school.dao.RoletoauthoritiesMapper;
import com.school.model.Roletoauthorities;
import com.school.model.Roletoauthorities.Column;
import com.school.model.RoletoauthoritiesExample;
import com.school.model.RoletoauthoritiesExample.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleToAuthoritiesServiceImpl {
    @Resource
    private RoletoauthoritiesMapper roletoauthoritiesMapper;

    public RoleToAuthoritiesServiceImpl() {
    }

    public List<Roletoauthorities> querySelective(Integer id, Integer roleId, String authority) {
        RoletoauthoritiesExample roletoauthoritiesExample = new RoletoauthoritiesExample();
        Criteria criteria = roletoauthoritiesExample.createCriteria();
        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(id);
        }

        if (!StringUtils.isEmpty(roleId)) {
            criteria.andRoleIdEqualTo(roleId);
        }

        if (StringUtils.hasText(authority)) {
            criteria.andAuthorityEqualTo(authority);
        }

        return this.roletoauthoritiesMapper.selectByExampleSelective(roletoauthoritiesExample, new Column[0]);
    }

    private void add(int roleId, String authority) {
        Roletoauthorities roletoauthorities = new Roletoauthorities();
        roletoauthorities.setRoleId(roleId);
        roletoauthorities.setAuthority(authority);
        this.roletoauthoritiesMapper.insertSelective(roletoauthorities);
    }

    private void removeAuthority(int roleId, String authority) {
        RoletoauthoritiesExample roletoauthoritiesExample = new RoletoauthoritiesExample();
        Criteria criteria = roletoauthoritiesExample.createCriteria();
        criteria.andRoleIdEqualTo(roleId).andAuthorityEqualTo(authority);
        this.roletoauthoritiesMapper.deleteByExample(roletoauthoritiesExample);
    }

    public void addAuthority(int roleId, String authority) {
        List<Roletoauthorities> roletoauthorities = this.querySelective((Integer) null, roleId, authority);
        if (roletoauthorities.size() == 0) {
            this.add(roleId, authority);
        }

    }

    public void removeAuthority(Integer roleId, String authority) {
        this.removeAuthority(roleId, authority);
    }
}
