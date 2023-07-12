package com.binbin.binapiadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binbin.binapicommon.mode.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

/**
 * @author hongxiaobin
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
 * @createDate 2023-07-12 16:52:34
 */
@Service
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

}
