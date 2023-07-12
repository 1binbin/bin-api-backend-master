package com.binbin.binapiadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.binbin.binapicommon.mode.dto.user.UserQueryRequest;
import com.binbin.binapicommon.mode.entity.User;
import com.binbin.binapicommon.mode.vo.LoginUserVO;
import com.binbin.binapicommon.mode.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author hongxiaobin
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2023-07-12 16:52:34
 */
@Service
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   账号
     * @param userPassword  密码
     * @param checkPassword 确认密码
     * @return 用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  账号
     * @param userPassword 密码
     * @param request      域对象
     * @return 用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取登录对象
     *
     * @param request 域对象
     * @return 用户信息
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 判断是否为管理员
     *
     * @param request 域对象
     * @return true——是
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request 域对象
     * @return true——正常注销
     */
    boolean userLoginOut(HttpServletRequest request);

    /**
     * 获取脱敏已登录用户信息
     *
     * @param user 未脱敏用户信息
     * @return 已脱敏用户信息
     */
    LoginUserVO getLoginUserVo(User user);

    /**
     * 获取脱敏用户信息
     *
     * @param user 用户信息
     * @return 已脱敏用户信息
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏用户信息列表
     *
     * @param userList 未脱敏用户信息列表
     * @return 已脱敏用户信息列表
     */
    List<UserVO> getUserVO(List<User> userList);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest 用户查询条件
     * @return 用户查询条件
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 更新 secretKey
     *
     * @param id 用户id
     * @return boolean 是否正常更新
     */
    boolean updateSecretKey(Long id);
}
