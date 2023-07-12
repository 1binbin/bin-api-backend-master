/*
 * com.binbin.binapicommon.mode.dto.file.UploadFileRequest, 2023-07-12
 * Copyright© 2023 hongxiaobin(1binbin),Inc. All rights reserved.
 * Github link : http://github.com/1binbin
 */

package com.binbin.binapicommon.mode.dto.file;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传请求
 *
 * @Author hongxiaobin
 * @Time 2023/7/12 0012-16:05:20
 */
@Data
public class UploadFileRequest implements Serializable {
    /**
     * 业务类型
     */
    private String biz;
    private static final long serialVersionUID = -6015142584897251820L;
}
