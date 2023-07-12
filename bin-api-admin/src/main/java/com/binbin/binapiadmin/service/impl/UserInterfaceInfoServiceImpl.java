/*
 * com.binbin.binapiadmin.service.impl.UserInterfaceInfoServiceImpl, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapiadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binbin.binapiadmin.mapper.UserInterfaceInfoMapper;
import com.binbin.binapiadmin.service.UserInterfaceInfoService;
import com.binbin.binapicommon.mode.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

/**
* @author hongxiaobin
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
* @createDate 2023-07-12 17:02:53
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

}




