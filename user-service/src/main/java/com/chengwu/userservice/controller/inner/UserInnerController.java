package com.chengwu.userservice.controller.inner;

import chengwu.model.model.entity.User;
import com.chengwu.serviceclient.service.UserFeignClient;
import com.chengwu.userservice.service.UserService;
import feign.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {
    @Resource
    private UserService userService;

    /**
     * 根据用户 id 获取用户信息
     *
     * @param id
     * @return
     */
    @Override
    @GetMapping("/get/id")
    public User getById(@RequestParam("id") Long id) {
        return userService.getById(id);
    }

    /**
     * 根据用户 id列表 获取用户信息
     *
     * @param userIds
     * @return
     */
    @Override
    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("userIds") Collection<Long> userIds) {
        return userService.listByIds(userIds);
    }
}
