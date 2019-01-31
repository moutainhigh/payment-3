-- MySQL dump 10.13  Distrib 5.7.23, for macos10.13 (x86_64)
--
-- Host: 127.0.0.1    Database: auth_server
-- ------------------------------------------------------
-- Server version	5.7.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbl_menu`
--

DROP TABLE IF EXISTS `tbl_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` tinytext,
  `name` tinytext,
  `type` int(11) DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_user_id` bigint(20) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_menu`
--

LOCK TABLES `tbl_menu` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `tbl_role_menu`
--

DROP TABLE IF EXISTS `tbl_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_role_menu`
--

LOCK TABLES `tbl_role_menu` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `tbl_sys_role`
--

DROP TABLE IF EXISTS `tbl_sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_sys_role`
--

LOCK TABLES `tbl_sys_role` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `tbl_user_role`
--

DROP TABLE IF EXISTS `tbl_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `sys_role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_user_role`
--

LOCK TABLES `tbl_user_role` WRITE;
UNLOCK TABLES;

--
-- Table structure for table `tbl_users`
--

DROP TABLE IF EXISTS `tbl_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `modify_user_id` bigint(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_account` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_users`
--

LOCK TABLES `tbl_users` WRITE;
UNLOCK TABLES;


DROP TABLE IF EXISTS `tbl_channel`;
CREATE TABLE tbl_channel
(
  id bigint(20) PRIMARY KEY AUTO_INCREMENT,
  channel_id varchar(255) COMMENT '渠道编码',
  channel_name varchar(255) COMMENT '渠道名称',
  create_time datetime default null COMMENT '创建时间',
  update_time datetime default null COMMENT '更新时间',
  create_user_id  bigint(20) COMMENT '创建人',
  modify_user_id  bigint(20) COMMENT '修改人',
  status int(1) DEFAULT 0 COMMENT '渠道状态  0 使用 1 停用'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE UNIQUE INDEX tbl_channel_channel_code_uindex ON tbl_channel (channel_id);
ALTER TABLE tbl_channel COMMENT = '渠道信息表';

LOCK TABLES `tbl_channel` WRITE;
UNLOCK TABLES;

drop table if exists `tbl_request`;
create table `tbl_request`(
  id bigint(20) PRIMARY KEY AUTO_INCREMENT,
  channel_id varchar(255) not null COMMENT '渠道编码',
  type  int(1)  DEFAULT 0 COMMENT '请求类型枚举 0 代付提现  1 账号余额查询  2 提现结果查询 3 下单',
  process_type int(1) default 0 COMMENT '处理类型 0实现类 1通用配置',
  request_method varchar(256) not null COMMENT '请求方法枚举',
  url varchar(256) not null COMMENT 'url ',
  parse_type varchar(256) not null COMMENT '返回值解析方式 枚举 正则 spel 脚本 html',
  create_time datetime default null COMMENT '创建时间',
  update_time datetime default null COMMENT '更新时间',
  create_user_id  bigint(20) COMMENT '创建人',
  modify_user_id  bigint(20) COMMENT '修改人',
  status int DEFAULT 0 COMMENT '状态 0 启用 1 停用',
  INDEX channel_index (channel_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tbl_request COMMENT = '请求配置表';

drop table if exists `tbl_callback`;
create table `tbl_callback`(
  id bigint(20) PRIMARY KEY AUTO_INCREMENT,
  channel_id varchar(255) not null COMMENT '渠道编码',
  type  int(1)  DEFAULT 0 COMMENT '回调类型枚举 0 代付提现 3 下单',
  process_type int(1) default 0 COMMENT '处理类型 0实现类 1通用配置',
  success_response varchar(63) not null COMMENT '成功返回字符串',
  error_response varchar(63) not null COMMENT '失败返回字符串',
  create_time datetime default null COMMENT '创建时间',
  update_time datetime default null COMMENT '更新时间',
  create_user_id  bigint(20) COMMENT '创建人',
  modify_user_id  bigint(20) COMMENT '修改人',
  status int DEFAULT 0 COMMENT '状态 0 启用 1 停用',
  INDEX channel_index (channel_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tbl_callback COMMENT = '回调配置表';

drop table if exists `tbl_callback_param`;
create table `tbl_callback_param`(
  id bigint(20) PRIMARY KEY AUTO_INCREMENT,
  callback_id bigint(20) not null COMMENT '回调id',
  field_key varchar(256) not null COMMENT '参数key',
  field_type varchar(256) not null COMMENT '参数类型 枚举 mchid orderid等',
  field_order int(1) not null COMMENT '参数顺序',
  assert_value varchar(256) COMMENT '期望值',
  update_time datetime default null COMMENT '更新时间',
  modify_user_id  bigint(20) COMMENT '修改人',
  status int DEFAULT 0 COMMENT '状态 0 启用 1 停用',
  INDEX callback_index (callback_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tbl_callback_param COMMENT = '回调参数表';


drop table if exists `tbl_sign_setting`;
create table `tbl_sign_setting`(
  id bigint(20) PRIMARY KEY AUTO_INCREMENT,
  type  int(1)  DEFAULT 0 COMMENT '配置类型 0渠道配置 1单个请求配置 2回调配置',
  foreign_key  varchar(255) not null COMMENT '外键 渠道id、请求id',
  sign_type varchar(63) not null COMMENT '签名类型 枚举',
  contains_key bool not null COMMENT '原串是否包含key',
  key_value_split varchar(10) not null COMMENT 'key与value间隔符',
  value_split varchar(10) not null COMMENT 'value之间间隔符',
  key_prefix varchar(10) not null COMMENT '商户key前缀',
  key_first bool not null COMMENT 'key是否在前面',
  upper_case bool not null COMMENT '转换大写',
  addition_setting varchar(256) COMMENT '额外配置 json',
  INDEX foreign_key_index (foreign_key)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tbl_sign_setting COMMENT = '签名配置表';

drop table if exists `tbl_request_param`;
create table `tbl_request_param`(
  id bigint(20) PRIMARY KEY AUTO_INCREMENT,
  request_id bigint(20) not null COMMENT '请求id',
  field_key varchar(256) not null COMMENT '参数key',
  field_type varchar(256) not null COMMENT '参数类型 枚举 mchid orderid等',
  default_value varchar(256) COMMENT '默认值',
  field_order int(1) not null COMMENT '参数顺序',
  update_time datetime default null COMMENT '更新时间',
  modify_user_id  bigint(20) COMMENT '修改人',
  status int DEFAULT 0 COMMENT '状态 0 启用 1 停用',
  INDEX request_index (request_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tbl_request_param COMMENT = '请求参数表';

drop table if exists `tbl_response_param`;
create table `tbl_response_param`(
  id bigint(20) PRIMARY KEY AUTO_INCREMENT,
  request_id bigint(20) not null COMMENT '请求id',
  field_type varchar(256) not null COMMENT '参数类型 枚举 mchid orderid等',
  parse_script varchar(256) COMMENT '解析脚本',
  assert_value varchar(256) COMMENT '成功返回值',
  update_time datetime default null COMMENT '更新时间',
  modify_user_id  bigint(20) COMMENT '修改人',
  status int DEFAULT 0 COMMENT '状态 0 启用 1 停用',
  INDEX request_index (request_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tbl_request_param COMMENT = '请求参数表';


DROP TABLE IF EXISTS `tbl_channel_merchant`;

CREATE TABLE  `tbl_channel_merchant`
(
  id bigint(20)  PRIMARY KEY AUTO_INCREMENT,
  channel_id  varchar(255) COMMENT '渠道编码',
  channel_name varchar(255) COMMENT '渠道名称',
  merchant_id varchar(255) not null  comment '商户号',
  merchant_name  varchar(128)  null     comment '商户名称（可以是公司的名字skline）',
  account_no  varchar(255) not null comment '收款账户号',
  account_name varchar(255) not null comment '收款账户名',
  appkey varchar(255) NOT NULL COMMENT '商户key',
  status int(1) DEFAULT 0 COMMENT '状态 0 启用 1 停用',
  INDEX channel_index (channel_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tbl_channel_merchant COMMENT = '渠道商户绑定表';




DROP TABLE IF EXISTS `tbl_transactionInfo`;
CREATE TABLE tbl_transactionInfo
(
  id bigint(20) PRIMARY KEY AUTO_INCREMENT,
  channel_id varchar(255) COMMENT '渠道编码',
  channle_name varchar(255) COMMENT '渠道名称',
  merchant_id varchar(255) not null  comment '商户号',
  merchant_name  varchar(128)  null  comment '商户名称（可以是公司的名字skline）',
  total_amt int(12) COMMENT '交易总额',
  total_fee_t1 int(12) COMMENT '昨日交易总额',
  total_fee_t0 int(12) COMMENT '今日交易总额',
  fee int(12) COMMENT '结算金额',
  tran_fee int(12) COMMENT '可结算金额',
  status int(1) COMMENT '领取状态',
  receive_time datetime default null COMMENT '领取时间',
  receive_user_id  bigint(20) COMMENT '领取人'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tbl_transactionInfo COMMENT = '交易信息表';


DROP TABLE IF EXISTS `tbl_order`;
create table tbl_order
(
  id                    bigint(20)    not null comment 'ID' primary key AUTO_INCREMENT,
  channel_id            varchar(255)  null     comment '渠道ID（渠道编码）',
  channel_name          varchar(255)  not null comment '渠道名称(第三方或者第四方渠道的名称，如：哇哈哈支付)',
  order_no              varchar(128)  not null comment '商户订单号（系统生成）',
  out_trade_no          varchar(128)           comment '第三方系统订单号（流水号）',
  merht_account         varchar(255)           comment '商户账号',
  merchant_id           varchar(128)  not null comment '商户ID(第三方渠道提供的商户号)',
  merchant_name         varchar(128)  null     comment '商户名称（可以是公司的名字skline）',
  card_no               bigint(24)    not null COMMENT '银行卡号',
  bank_code             varchar(255)  not null comment '银行编码',
  bank_name             VARCHAR(255)           comment '银行名称',
  account_name          VARCHAR(255)           comment '开户人',
  bank_branch           VARCHAR(255)           comment '开户行',
  amount                 int(12)      not null comment '交易金额(单位:分)',
  create_time           datetime      not null comment '订单创建时间',
  complete_time         datetime      null     comment '订单完成时间',
  req_json              text          null     comment '订单请求原始内容（记录请求的url）',
  res_json              text          null     comment '订单请求响应原始内容(记录同步返回的url,便于查问题)',
  status                int(1)        not null comment '订单状态(状态 1 提现中 2 成功 3 失败 )',
  note                  varchar(2048) null     comment '说明',
  channel_result        text          null     comment '渠道返回结果',
  constraint idx_order_no unique (order_no)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tbl_order COMMENT = '订单信息';

DROP TABLE IF EXISTS `tbl_account_info`;
CREATE TABLE tbl_account_info
(
  id bigint(20) PRIMARY KEY AUTO_INCREMENT,
  channel_id varchar(40) not null comment '渠道编码',
  account_id varchar(250) COMMENT '第三方后台登陆账号',
  password varchar(250) COMMENT '第三方登陆密码',
  create_time datetime default null COMMENT '创建时间',
  update_time datetime default null COMMENT '更新时间',
  create_user_id  bigint(20) COMMENT '创建人',
  modify_user_id  bigint(20) COMMENT '修改人',
  status int DEFAULT 0 COMMENT '状态 0 启用  1 停用'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE UNIQUE INDEX tbl_account_info_accountid_uindex ON tbl_account_info (account_id,channel_id);
ALTER TABLE tbl_account_info COMMENT = '第三方账号信息表';
LOCK TABLES `tbl_account_info` WRITE;
UNLOCK TABLES;

DROP TABLE IF EXISTS `tbl_bank`;
CREATE TABLE tbl_bank
(
  id bigint(20) PRIMARY KEY AUTO_INCREMENT,
  bank_code varchar(255) not null comment '银行编码',
  bank_name VARCHAR(255) COMMENT '银行名称',
  card_no bigint(24) not null COMMENT '银行卡号',
  name VARCHAR(255) COMMENT '开户人',
  bank_branch VARCHAR(255) COMMENT '开户行',
  province VARCHAR(255) COMMENT '省',
  city VARCHAR(255) COMMENT '市',
  county varchar(255) comment '区/县',
  create_time datetime COMMENT '创建时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tbl_order COMMENT = '提现银行卡信息';



DROP TABLE IF EXISTS `tbl_account_bank`;
CREATE TABLE tbl_account_bank
(
  acountid varchar(250) not null COMMENT '第三方平台账户id',
  card_no bigint(24) not null COMMENT '银行卡号',
  amount  int(12)      not null comment '交易金额(单位:分)',
  primary key (acountid,card_no )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
ALTER TABLE tbl_account_bank COMMENT = '银行账户关联信息表';

/**
1 银行卡信息

2、银行卡账号关联关系

3、提现订单信息表

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-25 12:23:42