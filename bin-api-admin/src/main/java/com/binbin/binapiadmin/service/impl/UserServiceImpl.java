package com.binbin.binapiadmin.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binbin.binapiadmin.exception.BusinessException;
import com.binbin.binapiadmin.mapper.UserMapper;
import com.binbin.binapiadmin.service.UserService;
import com.binbin.binapiadmin.utils.SqlUtils;
import com.binbin.binapicommon.common.ErrorCode;
import com.binbin.binapicommon.constant.CommonConstant;
import com.binbin.binapicommon.constant.UserConstant;
import com.binbin.binapicommon.mode.dto.user.UserQueryRequest;
import com.binbin.binapicommon.mode.entity.User;
import com.binbin.binapicommon.mode.enums.UserRoleEnum;
import com.binbin.binapicommon.mode.vo.LoginUserVO;
import com.binbin.binapicommon.mode.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hongxiaobin
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2023-07-12 17:02:53
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 普通用户注册
     *
     * @param userAccount   账号
     * @param userPassword  密码
     * @param checkPassword 确认密码
     * @param isAdmin       是否为管理员
     * @return 用户id
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, Boolean isAdmin) {
        // 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < UserConstant.USER_ACCOUNT_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < UserConstant.USER_PASSWORD_LENGHT || checkPassword.length() < UserConstant.USER_PASSWORD_LENGHT) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不相同");
        }
        // 异步注册
        synchronized (userAccount.intern()) {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("userAccount", userAccount);
            Long aLong = this.baseMapper.selectCount(userQueryWrapper);
            if (aLong > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 加密
            String encrtPassword = DigestUtils.md5DigestAsHex((UserConstant.USER_SALT + userPassword).getBytes());
            // 分配ak，sk
            String accessKey = DigestUtil.md5Hex(UserConstant.USER_SALT + userAccount + RandomUtil.randomNumbers(5));
            String secretKey = DigestUtil.md5Hex(UserConstant.USER_SALT + userAccount + RandomUtil.randomNumbers(8));
            // 注册
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserName(StringUtils.upperCase(userAccount));
            user.setUserPassword(encrtPassword);
            user.setAccessKey(accessKey);
            user.setSecretKey(secretKey);
            if (isAdmin) {
                user.setUserRole("admin");
            }
            // TODO: 2023/7/12 0012 替换默认照片
            user.setUserAvatar(UserConstant.USER_AVTURAL);
            boolean save = this.save(user);
            if (!save) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，请重试");
            }
            return user.getId();
        }
    }

    /**
     * 用户登录
     *
     * @param userAccount  账号
     * @param userPassword 密码
     * @param request      域对象
     * @return 用户信息
     */
    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < UserConstant.USER_ACCOUNT_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < UserConstant.USER_PASSWORD_LENGHT) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 加密
        String password = DigestUtils.md5DigestAsHex((UserConstant.USER_SALT + userPassword).getBytes());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        userQueryWrapper.eq("userPassword", password);
        User user = this.baseMapper.selectOne(userQueryWrapper);
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, user);
        // 脱密
        return this.getLoginUserVo(user);
    }

    /**
     * 获取登录对象
     *
     * @param request 域对象
     * @return 用户信息
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 判断是否登录
        Object userObj = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user == null || user.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // TODO: 2023/7/12 0012 可以添加缓存
        Long userId = user.getId();
        user = this.getById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return user;
    }

    /**
     * 判断是否为管理员
     *
     * @param request 域对象
     * @return true——是
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = (User) attribute;
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * 用户注销
     *
     * @param request 域对象
     * @return true——正常注销
     */
    @Override
    public boolean userLoginOut(HttpServletRequest request) {
        if (request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return true;
    }

    /**
     * 获取脱敏已登录用户信息
     *
     * @param user 未脱敏用户信息
     * @return 已脱敏用户信息
     */
    @Override
    public LoginUserVO getLoginUserVo(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    /**
     * 获取脱敏用户信息
     *
     * @param user 用户信息
     * @return 已脱敏用户信息
     */
    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO loginUserVO = new UserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    /**
     * 获取脱敏用户信息列表
     *
     * @param userList 未脱敏用户信息列表
     * @return 已脱敏用户信息列表
     */
    @Override
    public List<UserVO> getUserVO(List<User> userList) {
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    /**
     * 获取查询条件
     *
     * @param userQueryRequest 用户查询条件
     * @return 用户查询条件
     */
    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String unionId = userQueryRequest.getUnionId();
        String mpOpenId = userQueryRequest.getMpOpenId();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(unionId), "unionId", unionId);
        queryWrapper.eq(StringUtils.isNotBlank(mpOpenId), "mpOpenId", mpOpenId);
        queryWrapper.eq(StringUtils.isNotBlank(userRole), "userRole", userRole);
        queryWrapper.like(StringUtils.isNotBlank(userProfile), "userProfile", userProfile);
        queryWrapper.like(StringUtils.isNotBlank(userName), "userName", userName);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 更新 secretKey
     *
     * @param id 用户id
     * @return boolean 是否正常更新
     */
    @Override
    public boolean updateSecretKey(Long id) {
        User user = this.getById(id);
        String accessKey = DigestUtil.md5Hex(UserConstant.USER_SALT + user.getUserAccount() + RandomUtil.randomNumbers(5));
        String secretKey = DigestUtil.md5Hex(UserConstant.USER_SALT + user.getUserAccount() + RandomUtil.randomNumbers(8));
        user.setSecretKey(secretKey);
        user.setAccessKey(accessKey);
        return this.updateById(user);
    }
}




