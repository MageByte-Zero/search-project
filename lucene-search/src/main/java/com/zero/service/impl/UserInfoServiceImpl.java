package com.zero.service.impl;

import com.zero.common.exception.ResponseEnum;
import com.zero.common.exception.ServiceException;
import com.zero.common.model.SysPermission;
import com.zero.common.model.SysRole;
import com.zero.common.model.UserInfo;
import com.zero.dao.SysPermissionMapper;
import com.zero.dao.SysRoleMapper;
import com.zero.dao.UserInfoMapper;
import com.zero.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jianqing.li
 * @date 2018/3/15
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Transactional
    @Override
    public UserInfo getUserInfo(String userName) {
        //根据账号查询用户
        UserInfo userInfo = userInfoMapper.findByUsername(userName);
        if (userInfo == null) {
            throw new ServiceException(ResponseEnum.ACCOUNT_NOT_EXITS);
        }
        //根据uid查询角色列表
        List<SysRole> roleList = roleMapper.findByUid(userInfo.getUid());
        userInfo.setRoleList(roleList);
        //根据角色id查询权限
        for (SysRole role : roleList) {
            List<SysPermission> permissionList = permissionMapper.findByRoleId(role.getId());
            role.setPermissions(permissionList);
        }

        return userInfo;
    }
}

