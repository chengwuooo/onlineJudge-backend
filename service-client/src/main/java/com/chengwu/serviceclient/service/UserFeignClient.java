package com.chengwu.serviceclient.service;

import javax.servlet.http.HttpServletRequest;

import chengwu.model.model.entity.User;
import chengwu.model.model.enums.UserRoleEnum;
import chengwu.vo.UserVO;
import com.chengwu.common.common.ErrorCode;
import com.chengwu.common.constant.UserConstant;
import com.chengwu.common.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Collection;
import java.util.List;

/**
 * 用户服务
 */
@FeignClient(name = "user-service", path = "/api/user/inner")
public interface UserFeignClient {
    /**
     * 根据用户 id 获取用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/get/id")
    User getById(@RequestParam("userId") long userId);

    /**
     * 根据用户 id列表 获取用户信息
     *
     * @param userIds
     * @return
     */
    @GetMapping("/get/ids")
    List<User> listByIds(Collection<Long> userIds);


    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    default User getLoginUser(HttpServletRequest request) {
        // 从 session 中获取
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        // 从数据库查询（追求性能的话可以注释，直接走缓存）
//        long userId = currentUser.getId();
//        currentUser = this.getById(userId);
//        if (currentUser == null) {
//            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
//        }
        return currentUser;
    }

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    default boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    default UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }


}
