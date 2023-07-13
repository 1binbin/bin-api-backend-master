/*
 * com.bin.binclientsdk.utils.SignUtil, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.bin.binclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.bin.binclientsdk.constant.HostConstant;

/**
 * 生成签名工具类
 *
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-16:31:18
 */
public class SignUtil {
    public static String genSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        // TODO: 2023/7/13 0013 解决body中文解析不统一,乱码问题,暂时先使用盐值代替
        // String content = body + "." + secretKey;
        String content = HostConstant.SIGN_SALT + "." + secretKey;
        return md5.digestHex(content);
    }
}
