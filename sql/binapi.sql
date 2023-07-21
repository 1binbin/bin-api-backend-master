/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : binapi

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 21/07/2023 10:34:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for interface_info
-- ----------------------------
DROP TABLE IF EXISTS `interface_info`;
CREATE TABLE `interface_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `description` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接口地址',
  `host` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主机名',
  `requestParams` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `requestParamsRemark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数说明',
  `responseParamsRemark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '响应参数说明',
  `requestHeader` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求头',
  `responseHeader` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '响应头',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '接口状态（0-关闭，1-开启）',
  `method` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求类型',
  `userId` bigint(0) NOT NULL COMMENT '创建人',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `isDelete` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删, 1-已删)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '接口信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of interface_info
-- ----------------------------
INSERT INTO `interface_info` VALUES (1, '短网址生成', '将长网址进行缩短，支持百度、新浪、腾讯短网址等等', '/api/long2dwz', 'https://api.uomg.com', 'dwzapi=urlcn&url=http://grpay.uomg.com', '[{\"id\":1689320703434,\"name\":\"url\",\"isRequired\":\"yes\",\"type\":\"string\",\"remark\":\"需要进行缩短的长网址\"},{\"id\":1689320814880,\"name\":\"dwzapi\",\"isRequired\":\"no\",\"type\":\"string\",\"remark\":\"选择缩短接口tcn|dwzcn|urlcn|suoim|mrwsol\"}]', '[{\"id\":1689320851147,\"name\":\"code\",\"type\":\"string\",\"remark\":\"返回的状态码\"},{\"id\":1689320864635,\"name\":\"ae_url\",\"type\":\"string\",\"remark\":\"返回的缩短后的短网址\"},{\"id\":1689320881591,\"name\":\"msg\",\"type\":\"string\",\"remark\":\"返回错误提示信息\"}]', NULL, NULL, 1, 'GET/POST', 2, '2023-07-14 15:48:13', '2023-07-14 16:08:26', 0);
INSERT INTO `interface_info` VALUES (2, '毒鸡汤', '随机生成一句毒鸡汤语录', '/yan/api.php', 'http://api.btstu.cn/', 'charset=utf-8&encode=json', '[{\"id\":1689320966449,\"name\":\"charset\",\"isRequired\":\"no\",\"type\":\"string\",\"remark\":\"返回编码类型\"},{\"id\":1689321051706,\"name\":\"encode\",\"isRequired\":\"no\",\"type\":\"string\",\"remark\":\"返回数据类型\"}]', '[{\"id\":1689321060638,\"name\":\"code\",\"type\":\"string\",\"remark\":\"code\"}]', NULL, NULL, 1, 'GET', 2, '2023-07-14 15:51:09', '2023-07-14 16:08:26', 0);
INSERT INTO `interface_info` VALUES (3, '随机壁纸', '随机获取一个壁纸地址', '/sjbz/api.php', 'https://api.btstu.cn', 'lx=dongman&format=json', '[{\"id\":1689321088753,\"name\":\"method\",\"isRequired\":\"no\",\"type\":\"string\",\"remark\":\"输出壁纸端，默认为pc\"},{\"id\":1689321158974,\"name\":\"lx\",\"isRequired\":\"no\",\"type\":\"string\",\"remark\":\"选择输出分类\"},{\"id\":1689321169344,\"name\":\"format\",\"isRequired\":\"no\",\"type\":\"string\",\"remark\":\"输出壁纸格式\"}]', '[{\"id\":1689321182698,\"name\":\"code\",\"type\":\"string\",\"remark\":\"返回的状态码\"},{\"id\":1689321215391,\"name\":\"imgurl\",\"type\":\"string\",\"remark\":\"返回的图片地址\"},{\"id\":1689321227641,\"name\":\"width\",\"type\":\"string\",\"remark\":\"返回的图片宽度\"},{\"id\":1689321240738,\"name\":\"height\",\"type\":\"string\",\"remark\":\"返回的图片高度\"}]', NULL, NULL, 1, 'GET', 2, '2023-07-14 15:54:12', '2023-07-14 17:02:44', 0);
INSERT INTO `interface_info` VALUES (4, '网易云音乐热门评论', '网易云音乐热门评论随机API接口', '/api/comments.163', 'https://api.uomg.com', 'format=json', '[{\"id\":1689321276372,\"name\":\"mid\",\"isRequired\":\"no\",\"type\":\"string\",\"remark\":\"网易云歌单ID\"},{\"id\":1689321342924,\"name\":\"format\",\"isRequired\":\"no\",\"type\":\"string\",\"remark\":\"选择输出格式\"}]', '[{\"id\":1689321354511,\"name\":\"code\",\"type\":\"string\",\"remark\":\"返回的状态码\"},{\"id\":1689321363778,\"name\":\"data\",\"type\":\"string\",\"remark\":\"返回评论数据\"},{\"id\":1689321372063,\"name\":\"msg\",\"type\":\"string\",\"remark\":\"返回错误提示信息\"}]', NULL, NULL, 1, 'GET', 3, '2023-07-14 15:56:21', '2023-07-14 16:08:26', 0);
INSERT INTO `interface_info` VALUES (5, '网易云音乐随机歌曲', '网易云音乐，随机歌曲输出', '/api/rand.music', 'https://api.uomg.com', 'sort=热歌榜&format=json', '[{\"id\":1689321389353,\"name\":\"sort\",\"isRequired\":\"no\",\"type\":\"string\",\"remark\":\"选择输出分类\"},{\"id\":1689321456769,\"name\":\"mid\",\"isRequired\":\"no\",\"type\":\"string\",\"remark\":\"网易云歌单ID\"},{\"id\":1689321468633,\"name\":\"format\",\"isRequired\":\"no\",\"type\":\"string\",\"remark\":\"选择输出格式\"}]', '[{\"id\":1689321477241,\"name\":\"code\",\"type\":\"string\",\"remark\":\"返回的状态码\"},{\"id\":1689321484498,\"name\":\"data\",\"type\":\"string\",\"remark\":\"返回歌曲数据\"},{\"id\":1689321495967,\"name\":\"msg\",\"type\":\"string\",\"remark\":\"返回错误提示信息\"}]', NULL, NULL, 1, 'GET', 3, '2023-07-14 15:58:24', '2023-07-14 16:08:26', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `unionId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信开放平台id',
  `mpOpenId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公众号openId',
  `userName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `userAvatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `userProfile` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户简介',
  `userRole` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
  `accessKey` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'accessKey',
  `secretKey` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'secretKey',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `isDelete` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_unionId`(`unionId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'binuser', '7fc5a0dabf69cfd83708c2179faa3a06', NULL, NULL, 'BINUSER', 'http://rxqlbkl6h.hn-bkt.clouddn.com/openapi/common/commonAva.png', NULL, 'user', '9cad926d35a08e98c9697941dc275131', 'c41bed67b3bbbfea1e38b3a4bd71ae93', '2023-07-14 15:44:25', '2023-07-14 15:44:25', 0);
INSERT INTO `user` VALUES (2, 'binadmin1', '7fc5a0dabf69cfd83708c2179faa3a06', NULL, NULL, 'BINADMIN1', 'http://rxqlbkl6h.hn-bkt.clouddn.com/openapi/common/commonAva.png', NULL, 'admin', '98f686eb10078458545bf2d37eb24669', '730b5c5c266ddc9b644850488d4e747c', '2023-07-14 15:44:41', '2023-07-14 15:44:41', 0);
INSERT INTO `user` VALUES (3, 'binadmin2', '7fc5a0dabf69cfd83708c2179faa3a06', NULL, NULL, 'BINADMIN2', 'http://rxqlbkl6h.hn-bkt.clouddn.com/openapi/common/commonAva.png', NULL, 'admin', '61c86d1830e9673009a8a068a275bfc3', 'b7ccc1c639713310f8796cd68cdad992', '2023-07-14 15:44:45', '2023-07-14 15:44:45', 0);

-- ----------------------------
-- Table structure for user_interface_info
-- ----------------------------
DROP TABLE IF EXISTS `user_interface_info`;
CREATE TABLE `user_interface_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` bigint(0) NOT NULL COMMENT '调用用户 id',
  `interfaceInfoId` bigint(0) NOT NULL COMMENT '接口 id',
  `totalNum` int(0) NOT NULL DEFAULT 0 COMMENT '总调用次数',
  `leftNum` int(0) NOT NULL DEFAULT 0 COMMENT '剩余调用次数',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '0-正常，1-禁用',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `isDelete` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删, 1-已删)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户调用接口关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_interface_info
-- ----------------------------
INSERT INTO `user_interface_info` VALUES (1, 1, 5, 4, 99999995, 0, '2023-07-14 16:24:40', '2023-07-21 10:18:25', 0);
INSERT INTO `user_interface_info` VALUES (2, 1, 3, 5, 99999994, 0, '2023-07-14 16:58:12', '2023-07-14 17:04:00', 0);
INSERT INTO `user_interface_info` VALUES (3, 1, 1, 1, 99999998, 0, '2023-07-14 17:00:38', '2023-07-14 17:00:47', 0);

SET FOREIGN_KEY_CHECKS = 1;
