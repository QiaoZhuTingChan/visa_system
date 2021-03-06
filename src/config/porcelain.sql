/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : porcelain

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-10-16 23:08:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ba_account_type
-- ----------------------------
DROP TABLE IF EXISTS `ba_account_type`;
CREATE TABLE `ba_account_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ba_account_type` varchar(255) DEFAULT NULL COMMENT '账户名称',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '更新用户',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='账户信息';

-- ----------------------------
-- Records of ba_account_type
-- ----------------------------
INSERT INTO `ba_account_type` VALUES ('16', '微信', '2018-10-15 22:58:21', 'admin', '2018-10-15 22:58:21', 'admin', '');
INSERT INTO `ba_account_type` VALUES ('17', '支付宝', '2018-10-15 22:58:25', 'admin', '2018-10-15 22:58:25', 'admin', '');
INSERT INTO `ba_account_type` VALUES ('18', '公帐', '2018-10-15 22:58:37', 'admin', '2018-10-15 22:58:37', 'admin', '');
INSERT INTO `ba_account_type` VALUES ('19', '私账银行', '2018-10-15 22:58:45', 'admin', '2018-10-15 22:58:45', 'admin', '');

-- ----------------------------
-- Table structure for ba_admissible_area
-- ----------------------------
DROP TABLE IF EXISTS `ba_admissible_area`;
CREATE TABLE `ba_admissible_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ba_admissible_area` varchar(255) DEFAULT NULL COMMENT '签证类型',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='受理区域';

-- ----------------------------
-- Records of ba_admissible_area
-- ----------------------------
INSERT INTO `ba_admissible_area` VALUES ('2', '上海领区', '', '2018-10-13 00:11:05', 'admin', '2018-10-13 00:11:05', 'admin');
INSERT INTO `ba_admissible_area` VALUES ('3', '北京领馆', '', '2018-10-13 00:11:16', 'admin', '2018-10-13 00:11:16', 'admin');

-- ----------------------------
-- Table structure for ba_data_template
-- ----------------------------
DROP TABLE IF EXISTS `ba_data_template`;
CREATE TABLE `ba_data_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `src` varchar(255) DEFAULT NULL COMMENT '模板文档',
  `ba_data_template` varchar(255) DEFAULT NULL COMMENT '名称',
  `ba_model_type_id` int(11) DEFAULT NULL COMMENT '模板分类id',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='资料模板';

-- ----------------------------
-- Records of ba_data_template
-- ----------------------------
INSERT INTO `ba_data_template` VALUES ('1', 'f449909153994d249dd3a89163171922.xls', '证件', '11', '', '2018-10-15 20:31:03', 'admin', '2018-10-15 20:31:03', 'admin');
INSERT INTO `ba_data_template` VALUES ('2', '8f8ac9daf84b4840a45c13026a157c76.xls', '护照', '11', '', '2018-10-15 20:38:57', 'admin', '2018-10-15 20:38:57', 'admin');
INSERT INTO `ba_data_template` VALUES ('3', '9bc7199552ed42f09985b41af4a3847f.xls', '照片', '15', '', '2018-10-15 20:46:30', 'admin', '2018-10-15 20:48:01', 'admin');

-- ----------------------------
-- Table structure for ba_model_type
-- ----------------------------
DROP TABLE IF EXISTS `ba_model_type`;
CREATE TABLE `ba_model_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ba_model_type` varchar(255) DEFAULT NULL COMMENT '职位名称',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '更新用户',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of ba_model_type
-- ----------------------------
INSERT INTO `ba_model_type` VALUES ('11', '在职人员', '2018-10-14 19:56:47', 'admin', '2018-10-14 19:56:47', 'admin', '');
INSERT INTO `ba_model_type` VALUES ('12', '自由职业', '2018-10-14 19:57:29', 'admin', '2018-10-14 19:57:29', 'admin', '');
INSERT INTO `ba_model_type` VALUES ('13', '退休人员', '2018-10-14 19:57:42', 'admin', '2018-10-14 19:57:42', 'admin', '');
INSERT INTO `ba_model_type` VALUES ('14', '在校学生', '2018-10-14 19:57:58', 'admin', '2018-10-14 19:57:58', 'admin', '');
INSERT INTO `ba_model_type` VALUES ('15', '学龄前儿童', '2018-10-14 19:58:38', 'admin', '2018-10-14 19:58:38', 'admin', '');

-- ----------------------------
-- Table structure for ba_position
-- ----------------------------
DROP TABLE IF EXISTS `ba_position`;
CREATE TABLE `ba_position` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ba_position` varchar(255) DEFAULT NULL COMMENT '职位名称',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '更新用户',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ba_position
-- ----------------------------
INSERT INTO `ba_position` VALUES ('1', '程序开发', '2018-10-11 21:24:34', 'admin', '2018-10-11 21:24:40', 'admin', null);
INSERT INTO `ba_position` VALUES ('2', '总经理', '2018-10-11 21:24:55', 'admin', '2018-10-11 21:24:59', 'admin', null);
INSERT INTO `ba_position` VALUES ('3', '产品经理', '2018-10-11 23:20:49', 'admin', '2018-10-11 23:20:49', 'admin', '');
INSERT INTO `ba_position` VALUES ('4', '销售经理', '2018-10-11 23:20:57', 'admin', '2018-10-11 23:20:57', 'admin', '');
INSERT INTO `ba_position` VALUES ('5', '销售员', '2018-10-11 23:21:06', 'admin', '2018-10-11 23:21:06', 'admin', '');
INSERT INTO `ba_position` VALUES ('6', '签证总操作', '2018-10-11 23:21:17', 'admin', '2018-10-11 23:21:17', 'admin', '');
INSERT INTO `ba_position` VALUES ('7', '签证专员(欧美澳新加)', '2018-10-11 23:21:55', 'admin', '2018-10-11 23:21:55', 'admin', '');
INSERT INTO `ba_position` VALUES ('8', '签证专员(其他贴纸)', '2018-10-11 23:22:15', 'admin', '2018-10-11 23:22:15', 'admin', '');
INSERT INTO `ba_position` VALUES ('9', '签证专员(电子签证)', '2018-10-11 23:22:29', 'admin', '2018-10-11 23:22:29', 'admin', '');
INSERT INTO `ba_position` VALUES ('10', '财务', '2018-10-11 23:22:34', 'admin', '2018-10-11 23:22:34', 'admin', '');

-- ----------------------------
-- Table structure for ba_product_category
-- ----------------------------
DROP TABLE IF EXISTS `ba_product_category`;
CREATE TABLE `ba_product_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '产品分类名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '父类id',
  `sort_index` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ba_product_category
-- ----------------------------
INSERT INTO `ba_product_category` VALUES ('1', '全部', null, '0', null, '2018-10-13 15:55:37', 'admin', '2018-10-13 15:55:43', 'admin');
INSERT INTO `ba_product_category` VALUES ('2', '亚洲', '1', '0', null, '2018-10-13 15:56:06', 'admin', '2018-10-13 15:56:09', 'admin');
INSERT INTO `ba_product_category` VALUES ('3', '日本', '2', '0', null, '2018-10-13 15:56:26', 'admin', '2018-10-13 15:56:31', 'admin');
INSERT INTO `ba_product_category` VALUES ('4', '香港', '2', '0', '', '2018-10-13 16:57:44', 'admin', '2018-10-13 16:57:44', 'admin');
INSERT INTO `ba_product_category` VALUES ('5', '非洲', '1', '0', '', '2018-10-13 16:57:58', 'admin', '2018-10-13 16:57:58', 'admin');
INSERT INTO `ba_product_category` VALUES ('6', '巴马', '5', '0', '', '2018-10-13 16:58:09', 'admin', '2018-10-13 16:58:09', 'admin');

-- ----------------------------
-- Table structure for ba_product_para
-- ----------------------------
DROP TABLE IF EXISTS `ba_product_para`;
CREATE TABLE `ba_product_para` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ba_product_para` varchar(255) DEFAULT NULL COMMENT '职位名称',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '更新用户',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of ba_product_para
-- ----------------------------

-- ----------------------------
-- Table structure for ba_settlement_method
-- ----------------------------
DROP TABLE IF EXISTS `ba_settlement_method`;
CREATE TABLE `ba_settlement_method` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ba_settlement_method` varchar(255) DEFAULT NULL COMMENT '结算方式名称',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '更新用户',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='结算方式';

-- ----------------------------
-- Records of ba_settlement_method
-- ----------------------------
INSERT INTO `ba_settlement_method` VALUES ('16', '月结', '2018-10-15 22:57:00', 'admin', '2018-10-15 22:57:00', 'admin', '');
INSERT INTO `ba_settlement_method` VALUES ('17', '周结', '2018-10-15 22:57:09', 'admin', '2018-10-15 22:57:09', 'admin', '');
INSERT INTO `ba_settlement_method` VALUES ('18', '现结', '2018-10-15 22:57:18', 'admin', '2018-10-15 22:57:18', 'admin', '');

-- ----------------------------
-- Table structure for ba_visa_type
-- ----------------------------
DROP TABLE IF EXISTS `ba_visa_type`;
CREATE TABLE `ba_visa_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ba_visa_type` varchar(255) DEFAULT NULL COMMENT '签证类型',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `create_user` varchar(255) NOT NULL COMMENT '创建人',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `update_user` varchar(255) NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ba_visa_type
-- ----------------------------
INSERT INTO `ba_visa_type` VALUES ('1', '探亲访友签证', null, '2018-10-12 23:22:03', 'admin', '2018-10-12 23:22:05', 'admin');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_type_id` int(11) DEFAULT NULL,
  `emp_id` int(11) DEFAULT NULL,
  `message_content` varchar(200) DEFAULT NULL,
  `message_key` varchar(45) DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '状态，0已读，1未读',
  `remark` varchar(45) NOT NULL DEFAULT '' COMMENT '备注',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  `create_user` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_user` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '修改用户',
  PRIMARY KEY (`id`),
  KEY `index_message_message_type_id` (`message_type_id`),
  KEY `index_message_emp_id` (`emp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息';

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for message_data
-- ----------------------------
DROP TABLE IF EXISTS `message_data`;
CREATE TABLE `message_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_key` varchar(45) DEFAULT NULL,
  `data_value` text,
  PRIMARY KEY (`id`),
  KEY `index_message_data_data_key` (`data_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息数据';

-- ----------------------------
-- Records of message_data
-- ----------------------------

-- ----------------------------
-- Table structure for message_type
-- ----------------------------
DROP TABLE IF EXISTS `message_type`;
CREATE TABLE `message_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_type` varchar(45) DEFAULT NULL,
  `proceess_class` varchar(45) DEFAULT NULL,
  `remark` varchar(45) NOT NULL DEFAULT '' COMMENT '备注',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  `create_user` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '创建用户',
  `update_user` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '修改用户',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='消息类型';

-- ----------------------------
-- Records of message_type
-- ----------------------------
INSERT INTO `message_type` VALUES ('1', '工作流审批', 'com.jyd.bms.message.WorkflowMessage', '', '2017-11-27 00:00:00', '2017-11-26 00:00:00', '', '');
INSERT INTO `message_type` VALUES ('2', '公告', 'com.jyd.bms.message.PublicMessage', '', '2017-12-20 00:00:00', '2017-12-20 00:00:00', '', '');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) DEFAULT NULL COMMENT '产品名称',
  `line_customer_price` decimal(10,2) DEFAULT NULL COMMENT '直客价',
  `peer_price` decimal(10,2) DEFAULT NULL COMMENT '同行价',
  `market_price` decimal(10,2) DEFAULT NULL COMMENT '市场价',
  `img` varchar(255) DEFAULT NULL COMMENT '产品图',
  `ba_visa_type_id` int(11) DEFAULT NULL COMMENT '签证类型',
  `ba_admissible_area_id` int(11) DEFAULT NULL COMMENT '受理领区',
  `admissible_area_type` varchar(255) DEFAULT NULL COMMENT '领区划分',
  `out_cycle` varchar(255) DEFAULT NULL COMMENT '出签周期',
  `residence_time` varchar(255) DEFAULT NULL COMMENT '停留时间',
  `effective_time` varchar(255) DEFAULT NULL COMMENT '有效时间',
  `entry_times` int(11) DEFAULT NULL COMMENT '入境次数',
  `face_sign` bit(1) DEFAULT NULL COMMENT '是否面签',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------

-- ----------------------------
-- Table structure for pro_supplier
-- ----------------------------
DROP TABLE IF EXISTS `pro_supplier`;
CREATE TABLE `pro_supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL COMMENT '产品',
  `supplier_rate` double DEFAULT NULL COMMENT '供应商提成利率',
  `employee_rate` double DEFAULT NULL COMMENT '员工提成比率',
  `supplier_id` int(11) DEFAULT NULL COMMENT '供应商',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品供应商';

-- ----------------------------
-- Records of pro_supplier
-- ----------------------------

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact_name` varchar(255) DEFAULT NULL COMMENT '联系人',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `company` varchar(255) DEFAULT NULL COMMENT '所属公司',
  `ba_settlement_method_id` int(11) DEFAULT NULL COMMENT '结算方式',
  `ba_account_type_id` int(11) DEFAULT NULL COMMENT '账户信息',
  `contact_phone` varchar(255) DEFAULT NULL COMMENT '联系方式',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` varchar(255) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商';

-- ----------------------------
-- Records of supplier
-- ----------------------------

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `create_user` varchar(50) CHARACTER SET utf8 NOT NULL,
  `update_user` varchar(50) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COMMENT='组';

-- ----------------------------
-- Records of sys_group
-- ----------------------------
INSERT INTO `sys_group` VALUES ('1', '超级管理员', '最高权限，例如：总经理', '2018-10-11 21:28:12', '2018-10-11 23:28:43', 'admin', 'admin');
INSERT INTO `sys_group` VALUES ('2', '产品经理', '产品管理，供应商', '2018-10-11 23:25:52', '2018-10-11 23:27:27', 'admin', 'admin');
INSERT INTO `sys_group` VALUES ('3', '销售经理', '有产品管理，客户管理，应用管理，同行管理', '2018-10-11 23:25:59', '2018-10-11 23:27:18', 'admin', 'admin');
INSERT INTO `sys_group` VALUES ('4', '销售员', '订单管理，客户管理，同行管理', '2018-10-11 23:26:05', '2018-10-11 23:28:24', 'admin', 'admin');
INSERT INTO `sys_group` VALUES ('5', '签证', '订单管理，签证处管理，供应商查询', '2018-10-11 23:26:13', '2018-10-11 23:28:09', 'admin', 'admin');
INSERT INTO `sys_group` VALUES ('6', '财务', '', '2018-10-11 23:26:16', '2018-10-11 23:26:16', 'admin', 'admin');

-- ----------------------------
-- Table structure for sys_group_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_menu`;
CREATE TABLE `sys_group_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` varchar(100) CHARACTER SET utf8 NOT NULL,
  `group_id` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `create_user` varchar(50) CHARACTER SET utf8 NOT NULL,
  `update_user` varchar(50) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_c6x8r5rrbtrqovqumf3awcifd` (`group_id`),
  KEY `index_sys_group_menu_menu_id` (`menu_id`),
  KEY `index_sys_group_menu_group_id` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1 COMMENT='组菜单';

-- ----------------------------
-- Records of sys_group_menu
-- ----------------------------
INSERT INTO `sys_group_menu` VALUES ('1', 'sys', '1', '2018-10-11 21:28:44', '2018-10-11 21:28:46', 'admin', 'admin');
INSERT INTO `sys_group_menu` VALUES ('2', 'ba', '1', '2018-10-11 21:29:45', '2018-10-11 21:29:47', 'admin', 'admin');
INSERT INTO `sys_group_menu` VALUES ('3', 'sys_user', '1', '2018-10-11 21:50:19', '2018-10-11 21:50:21', 'admin', 'admin');
INSERT INTO `sys_group_menu` VALUES ('4', 'sys_group', '1', '2018-10-11 21:50:36', '2018-10-11 21:50:38', 'admin', 'admin');
INSERT INTO `sys_group_menu` VALUES ('5', 'sys_menu', '1', '2018-10-11 21:51:03', '2018-10-11 21:51:05', 'admin', 'admin');
INSERT INTO `sys_group_menu` VALUES ('6', 'bu', '1', '2018-10-11 22:52:42', '2018-10-11 22:52:42', 'admin', 'admin');
INSERT INTO `sys_group_menu` VALUES ('7', 'ba_position', '1', '2018-10-11 22:54:43', '2018-10-11 22:54:43', 'admin', 'admin');
INSERT INTO `sys_group_menu` VALUES ('8', 'ba_admissible_area', '1', '2018-10-13 00:10:09', '2018-10-13 00:10:09', 'admin', 'admin');
INSERT INTO `sys_group_menu` VALUES ('10', 'ba_visa_type', '1', '2018-10-13 00:10:09', '2018-10-13 00:10:09', 'admin', 'admin');
INSERT INTO `sys_group_menu` VALUES ('11', 'product_category', '1', '2018-10-13 15:59:27', '2018-10-13 15:59:27', 'admin', 'admin');
INSERT INTO `sys_group_menu` VALUES ('12', 'ba_model_type', '1', '2018-10-14 19:56:31', '2018-10-14 19:56:31', 'admin', 'admin');
INSERT INTO `sys_group_menu` VALUES ('13', 'ba_data_template', '1', '2018-10-14 22:23:06', '2018-10-14 22:23:06', 'admin', 'admin');
INSERT INTO `sys_group_menu` VALUES ('14', 'supplier', '1', '2018-10-15 22:55:54', '2018-10-15 22:55:54', 'admin', 'admin');
INSERT INTO `sys_group_menu` VALUES ('15', 'ba_account_type', '1', '2018-10-15 22:55:54', '2018-10-15 22:55:54', 'admin', 'admin');
INSERT INTO `sys_group_menu` VALUES ('16', 'ba_settlement_method', '1', '2018-10-15 22:55:54', '2018-10-15 22:55:54', 'admin', 'admin');

-- ----------------------------
-- Table structure for sys_group_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_user`;
CREATE TABLE `sys_group_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `create_user` varchar(50) CHARACTER SET utf8 NOT NULL,
  `update_user` varchar(50) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_nu447cecdqk34qfamyafmtomp` (`user_id`),
  KEY `fk_sik7qnj491u5dxkwkt8ygcjl9` (`group_id`),
  KEY `index_sys_group_user_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COMMENT='组用户';

-- ----------------------------
-- Records of sys_group_user
-- ----------------------------
INSERT INTO `sys_group_user` VALUES ('1', '1', '1', '2018-10-11 21:54:00', '2018-10-11 21:54:03', 'admin', 'admin');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` varchar(100) CHARACTER SET utf8 NOT NULL,
  `parent_id` varchar(100) CHARACTER SET utf8 NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 NOT NULL,
  `type` int(11) NOT NULL,
  `icon_name` varchar(40) DEFAULT NULL,
  `menu_url` varchar(200) NOT NULL,
  `call_method` varchar(50) DEFAULT NULL,
  `language_id` varchar(100) NOT NULL,
  `display` bit(1) NOT NULL,
  `sort` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_sys_menu_menu_id` (`menu_id`),
  KEY `index_sys_menu_parent_id` (`parent_id`),
  KEY `index_sys_menu_language_id` (`language_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1 COMMENT='系统菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', 'sys', '', '系统设置', '2', '', '', '', 'sys', '', '0');
INSERT INTO `sys_menu` VALUES ('2', 'ba', '', '基本资料', '2', '', '', '', 'ba', '', '0');
INSERT INTO `sys_menu` VALUES ('3', 'bu', '', '业务管理', '2', '', '', '', 'bu', '', '0');
INSERT INTO `sys_menu` VALUES ('7', 'sys_user', 'sys', '用户管理', '3', '', '/admin/user.zul', '', 'sys_user', '', '0');
INSERT INTO `sys_menu` VALUES ('8', 'sys_group', 'sys', '群组管理', '3', '', '/admin/group.zul', '', 'sys_group', '', '0');
INSERT INTO `sys_menu` VALUES ('9', 'sys_menu', 'sys', '菜单管理', '3', '', '/admin/menu.zul', '', 'sys_menu', '', '0');
INSERT INTO `sys_menu` VALUES ('10', 'ba_position', 'ba', '职位', '3', null, '/basedata/baPosition.zul', null, 'ba_position', '', '0');
INSERT INTO `sys_menu` VALUES ('11', 'ba_visa_type', 'ba', '签证类型', '3', null, '/basedata/baVisaType.zul', null, 'ba_visa_type', '', '0');
INSERT INTO `sys_menu` VALUES ('14', 'ba_admissible_area', 'ba', '受理区域', '3', null, '/basedata/baAdmissibleArea.zul', null, 'ba_admissible_area', '', '0');
INSERT INTO `sys_menu` VALUES ('15', 'product_category', 'ba', '产品类型', '3', null, '/basedata/productCategory.zul', null, 'product_category', '', '0');
INSERT INTO `sys_menu` VALUES ('16', 'ba_model_type', 'ba', '模板分类', '3', null, '/basedata/baModelType.zul', null, 'ba_model_type', '', '0');
INSERT INTO `sys_menu` VALUES ('17', 'ba_data_template', 'ba', '资料模板', '3', null, '/basedata/baDataTemplate.zul', null, 'ba_data_template', '', '0');
INSERT INTO `sys_menu` VALUES ('18', 'ba_settlement_method', 'ba', '结算方式', '3', null, '/basedata/baSettlementMethod.zul', null, 'ba_settlement_method', '', '0');
INSERT INTO `sys_menu` VALUES ('19', 'ba_account_type', 'ba', '账户信息', '3', null, '/basedata/baAccountType.zul', null, 'ba_account_type', '', '0');
INSERT INTO `sys_menu` VALUES ('20', 'supplier', 'ba', '供应商', '3', null, '/basedata/supplier.zul', null, 'supplier', '', '0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `user_name` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `ba_position_id` int(11) DEFAULT NULL COMMENT '职位id',
  `email` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `admin` bit(1) DEFAULT NULL,
  `enable` bit(1) DEFAULT NULL,
  `remark` varchar(250) CHARACTER SET utf8 DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `create_user` varchar(50) CHARACTER SET utf8 NOT NULL,
  `update_user` varchar(50) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COMMENT='用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '米久业', '62ef34f4e7b271b4ccdb5cbeb1546fd5', '2', '296629801@qq.com', '\0', '', '', '2018-10-11 21:20:06', '2018-10-11 23:09:14', 'admin', 'admin');

-- ----------------------------
-- Table structure for sys_user_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_menu`;
CREATE TABLE `sys_user_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` varchar(100) CHARACTER SET utf8 NOT NULL,
  `user_id` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `create_user` varchar(50) CHARACTER SET utf8 NOT NULL,
  `update_user` varchar(50) CHARACTER SET utf8 NOT NULL,
  `enable` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_i603208t25aj6fll2bb4h36en` (`user_id`),
  KEY `index_sys_user_menu_menu_id` (`menu_id`),
  KEY `index_sys_user_menu_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='用户菜单';

-- ----------------------------
-- Records of sys_user_menu
-- ----------------------------
