- 2021-08-11
-- 增加菜单
INSERT INTO `sys_menu`(`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (118, 36, 0, 1, '表结构文档生成', 'TableDoc', 'tableDoc/index', 38, 'database', 'tableDoc', b'0', b'0', b'0', NULL, 'admin', 'admin', '2021-08-09 15:29:22', '2021-08-09 16:48:57');
INSERT INTO `sys_roles_menus` VALUES (118, 1);
COMMIT;

DROP TABLE IF EXISTS `code_screw_config`;
CREATE TABLE `code_screw_config`  (
                                      `config_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                      `version` varchar(255) DEFAULT NULL  COMMENT '版本号',
                                      `description` varchar(255) DEFAULT NULL  COMMENT '描述',
                                      `ignore_table_name` varchar(255) DEFAULT NULL COMMENT '忽略表名',
                                      `ignore_table_prefix` varchar(255) DEFAULT NULL COMMENT '忽略表前缀',
                                      `ignore_table_suffix` varchar(255) DEFAULT NULL COMMENT '忽略表后缀',
                                      `designated_table_name` varchar(255) DEFAULT NULL COMMENT '指定表名生成',
                                      `designated_table_prefix` varchar(255) DEFAULT NULL COMMENT '指定表前缀生成',
                                      `designated_table_suffix` varchar(255) DEFAULT NULL COMMENT '指定表后缀生成',
                                      PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT = 'screw生成数据库表结构文档 配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of code_screw_config
-- ----------------------------
BEGIN;
INSERT INTO `code_screw_config` VALUES (1, '1.0.0', '数据库文档生成说明', '', NULL, NULL, NULL, NULL, NULL);
COMMIT;
