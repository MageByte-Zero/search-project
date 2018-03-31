package com.zero.controller;

import com.zero.common.model.UserInfo;
import com.zero.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author jianqing.li
 * @date 2017/12/5
 */
@Api(description = "用户管理")
@RestController
@RequestMapping("/users")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);

    /**
     * 用户查询.
     *
     * @return
     */
    @ApiOperation(value = "查询用户列表")
    @GetMapping
    public UserInfo listUserInfo(@RequestParam("userName") String userName) {
        LOGGER.info("param is {}", userName);
        LOGGER.error("param is {}", userName);
        LOGGER.warn("param is {}", userName);
        UserInfo userInfo = userInfoService.getUserInfo(userName);
        LOGGER.info("result is {}", userInfo.toString());
        return userInfo;
    }

    /**
     * 用户添加;
     *
     * @return
     */
    @ApiOperation(value = "新增用户")
    @PostMapping
    public String userInfoAdd() {
        return "userInfoAdd";
    }

    /**
     * 用户删除;
     *
     * @return
     */
    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(name = "id", value = "用户id", dataType = "long", paramType = "path")
    @DeleteMapping(value = "/{id}")
    public String userDel(@PathVariable long id) {
//        Subject subject = SecurityUtils.getSubject();
        return "userInfoDel";
    }
}
