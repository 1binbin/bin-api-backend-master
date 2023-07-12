/*
 * com.binbin.binapicommon.utils.URLUtil, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.utils;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * URL解析工具
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-16:16:57
 */
public class URLUtil {
    public static String decode(String body,String charset){
        try {
            body = URLDecoder.decode(body,charset);
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
        return body;
    }

    public static String encode(String body, String charset) {
        try {
            body = URLEncoder.encode(body, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return body;
    }
}
