//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.service.impl;

import com.school.dao.UsertoroleMapper;
import com.school.model.Usertorole;
import com.school.model.UsertoroleExample;
import com.school.model.Usertorole.Column;
import com.school.model.UsertoroleExample.Criteria;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserToRoleServiceImpl {
    @Resource
    private UsertoroleMapper usertoroleMapper;

    public UserToRoleServiceImpl() {
    }

    public List<Usertorole> querySelective(Integer id, Integer userId, Integer roleId) {
        UsertoroleExample usertoroleExample = new UsertoroleExample();
        Criteria criteria = usertoroleExample.createCriteria();
        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(id);
        }

        if (!StringUtils.isEmpty(userId)) {
            criteria.andUseridEqualTo(userId);
        }

        if (!StringUtils.isEmpty(roleId)) {
            criteria.andRoleidEqualTo(roleId);
        }

        return this.usertoroleMapper.selectByExampleSelective(usertoroleExample, new Column[0]);
    }

    public void updateByUserId(Integer userId, Integer level) {
        UsertoroleExample usertoroleExample = new UsertoroleExample();
        Criteria criteria = usertoroleExample.createCriteria();
        criteria.andUseridEqualTo(userId);
        Usertorole usertorole = new Usertorole();
        usertorole.setRoleid(level);
        this.usertoroleMapper.updateByExampleSelective(usertorole, usertoroleExample);
    }

    public List<Usertorole> getUserToRoleByUserId(Integer userId) {
        UsertoroleExample usertoroleExample = new UsertoroleExample();
        Criteria criteria = usertoroleExample.createCriteria();
        criteria.andUseridEqualTo(userId);
        return this.usertoroleMapper.selectByExample(usertoroleExample);
    }

    public void add(Integer userId, Integer roleId) {
        Usertorole usertorole = new Usertorole();
        usertorole.setUserid(userId);
        usertorole.setRoleid(roleId);
        this.usertoroleMapper.insertSelective(usertorole);
    }
}
