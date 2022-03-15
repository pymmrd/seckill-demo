create table t_user(
 `id` bigint(20) NOT NULL COMMENT '用户ID',
 `nickname` VARCHAR(255) NOT NULL,
 `password` VARCHAR(32) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt)',
 `salt` VARCHAR(10) DEFAULT NULL,
 `head` VARCHAR(128) DEFAULT NULL COMMENT 'Logo',
 `register_date` datetime DEFAULT NULL COMMENT '',
 `last_login_date` datetime DEFAULT NULL COMMENT '',
 `login_count` int(11) DEFAULT '0' COMMENT '',
 PRIMARY KEY(`id`)
);


create table `t_goods`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
    `goods_name` VARCHAR(16) DEFAULT NULL COMMENT '商品名称',
    `good_title` VARCHAR(64) DEFAULT NULL COMMENT '商品标题',
    `goods_img`  VARCHAR(64) DEFAULT NULL COMMENT '商品图片',
    `goods_detail` LONGTEXT COMMENT '商品详情',
    `goods_price` DECIMAL(10, 2) DEFAULT '0.00' COMMENT '商品价格',
    `goods_stock` INT(11) DEFAULT '0' COMMENT '商品库存， -1表示没有限制',
    PRIMARY KEY(`id`)
)ENGINE=INNODB  AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;



create table `t_order`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    `user_id` bigint(20) NOT NULL  COMMENT '用户ID',
    `goods_id` bigint(20) NOT NULL  COMMENT '商品ID',
    `delivery_addr_id` bigint(20) NOT NULL COMMENT '收获地址ID',
    `goods_name` VARCHAR(16) DEFAULT NULL COMMENT '商品名称',
    `goods_count` INT(11) DEFAULT '0' COMMENT '商品数量',
    `goods_price` DECIMAL(10, 2) DEFAULT '0.00' COMMENT '商品价格',
    `order_channel` TINYINT(4) DEFAULT '0' COMMENT '1-PC, 2-Android, 3-ios',
    `status` TINYINT(4)  DEFAULT '0' COMMENT '0-NEW, 1-CHECKOUT, 2-SHIPPING, 3-RECEIVER, 4-REFUND, 5-FINISHED',
    `create_date` datetime DEFAULT NULL COMMENT '创建时间',
    `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
    PRIMARY KEY(`id`)
)ENGINE=INNODB  AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;


create table `t_seckill_goods`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀商品ID',
    `goods_id` bigint(20) NOT NULL  COMMENT '商品ID',
    `seckill_price` DECIMAL(10, 2) DEFAULT '0.00' COMMENT '商品价格',
    `stock_count` INT(11) DEFAULT '0' COMMENT '库存数量',
    `start_date` datetime DEFAULT NULL COMMENT '开始时间',
    `end_date` datetime DEFAULT NULL COMMENT '结束时间',
    PRIMARY KEY(`id`)
)ENGINE=INNODB  AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

create table `t_seckill_order`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀订单ID',
    `user_id` bigint(20) NOT NULL  COMMENT '用户ID',
    `goods_id` bigint(20) NOT NULL  COMMENT '商品ID',
    `order_id` bigint(20) NOT NULL COMMENT '订单ID',
    PRIMARY KEY(`id`)
)ENGINE=INNODB  AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;


INSERT INTO `t_goods` VALUES (1, 'IPhone12', 'Iphone12 64GB', '/img/iphone12.png', 'iphon12','6299.00', 100),
 (2, 'IPhone12PRO', 'Iphone12PRO 64GB', '/img/iphone12pro.png',  'iphon12pro', '9299.00', 100);

INSERT INTO `t_seckill_goods` VALUES (1, 1, '629.00', 10, '2022-3-11 00:00:00', '2022-3-17 00:00:00'),
 (2, 2, '929.00', 10, '2022-3-11 00:00:00', '2022-3-17 00:00:00');