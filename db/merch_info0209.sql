/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : PostgreSQL
 Source Server Version : 120003
 Source Host           : localhost:5432
 Source Catalog        : wetakeout
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 120003
 File Encoding         : 65001

 Date: 09/02/2021 00:45:14
*/


-- ----------------------------
-- Table structure for merch_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."merch_info";
CREATE TABLE "public"."merch_info" (
  "merch_id" int4 NOT NULL DEFAULT nextval('merch_info_merch_id_seq'::regclass),
  "merch_name" varchar(255) COLLATE "pg_catalog"."default",
  "merch_addr" varchar(255) COLLATE "pg_catalog"."default",
  "take_out_type" varchar(6) COLLATE "pg_catalog"."default",
  "merch_phone" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."merch_info"."merch_id" IS '商户编号';
COMMENT ON COLUMN "public"."merch_info"."merch_name" IS '商户名称';
COMMENT ON COLUMN "public"."merch_info"."merch_addr" IS '商户地址';
COMMENT ON COLUMN "public"."merch_info"."take_out_type" IS '外卖类型（mt,美团；el:饿了吗）';
COMMENT ON COLUMN "public"."merch_info"."merch_phone" IS '商户电话';

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."order_info";
CREATE TABLE "public"."order_info" (
  "order_id" int4 NOT NULL,
  "user_id" varchar(64) COLLATE "pg_catalog"."default",
  "product_id" int4,
  "buy_pic" varchar(255) COLLATE "pg_catalog"."default",
  "praise_pic" varchar(255) COLLATE "pg_catalog"."default",
  "effective" varchar(255) COLLATE "pg_catalog"."default",
  "fail_reason" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."order_info"."order_id" IS '订单编号';
COMMENT ON COLUMN "public"."order_info"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."order_info"."product_id" IS '商品id';
COMMENT ON COLUMN "public"."order_info"."buy_pic" IS '购买截图';
COMMENT ON COLUMN "public"."order_info"."praise_pic" IS '好评截图';
COMMENT ON COLUMN "public"."order_info"."effective" IS '是否有效（y有效，n无效）';
COMMENT ON COLUMN "public"."order_info"."fail_reason" IS '失败原因';
COMMENT ON COLUMN "public"."order_info"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."order_info"."update_time" IS '修改时间';

-- ----------------------------
-- Table structure for pic_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."pic_info";
CREATE TABLE "public"."pic_info" (
  "pic_id" int4 NOT NULL,
  "pic_url" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."pic_info"."pic_id" IS 'id';
COMMENT ON COLUMN "public"."pic_info"."pic_url" IS '图片url';

-- ----------------------------
-- Table structure for point_notes
-- ----------------------------
DROP TABLE IF EXISTS "public"."point_notes";
CREATE TABLE "public"."point_notes" (
  "id" int4 NOT NULL,
  "user_id" varchar COLLATE "pg_catalog"."default",
  "point" float8,
  "prodcut_id" int4,
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."point_notes"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."point_notes"."point" IS '积分';
COMMENT ON COLUMN "public"."point_notes"."prodcut_id" IS '商品id';

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."product_info";
CREATE TABLE "public"."product_info" (
  "product_id" int4 NOT NULL,
  "url" varchar(255) COLLATE "pg_catalog"."default",
  "product_name" varchar(255) COLLATE "pg_catalog"."default",
  "product_type" varchar(255) COLLATE "pg_catalog"."default",
  "merch_id" int4,
  "limit" int4,
  "limit_time" timestamp(6),
  "return_point" float8,
  "left" int4,
  "recommend" varchar(2) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."product_info"."url" IS '链接';
COMMENT ON COLUMN "public"."product_info"."product_name" IS '产品名称';
COMMENT ON COLUMN "public"."product_info"."product_type" IS '商品类型（mt：美团，el:饿了吗）';
COMMENT ON COLUMN "public"."product_info"."merch_id" IS '商户id';
COMMENT ON COLUMN "public"."product_info"."limit" IS '商品限额';
COMMENT ON COLUMN "public"."product_info"."limit_time" IS '商品有效期';
COMMENT ON COLUMN "public"."product_info"."return_point" IS '返回积分';
COMMENT ON COLUMN "public"."product_info"."left" IS '剩余数量';
COMMENT ON COLUMN "public"."product_info"."recommend" IS '推荐';

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS "public"."user_info";
CREATE TABLE "public"."user_info" (
  "user_id" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "phone" varchar(255) COLLATE "pg_catalog"."default",
  "member" varchar(2) COLLATE "pg_catalog"."default",
  "point" float8,
  "address" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "public"."user_info"."member" IS '是否会员（0，否，1是）';
COMMENT ON COLUMN "public"."user_info"."point" IS '积分';
COMMENT ON COLUMN "public"."user_info"."address" IS '地址';

-- ----------------------------
-- Primary Key structure for table pic_info
-- ----------------------------
ALTER TABLE "public"."pic_info" ADD CONSTRAINT "pic_url_pkey" PRIMARY KEY ("pic_id");

-- ----------------------------
-- Primary Key structure for table user_info
-- ----------------------------
ALTER TABLE "public"."user_info" ADD CONSTRAINT "user_info_pkey" PRIMARY KEY ("user_id");

INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (83, 79, '删除', NULL, 'user:pointnotes:delete', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (82, 79, '修改', NULL, 'user:pointnotes:update', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (81, 79, '新增', NULL, 'user:pointnotes:save', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (80, 79, '查看', NULL, 'user:pointnotes:list,user:pointnotes:info', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (79, 0, '积分记录', 'points/pointnotes', NULL, 1, 'config', 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (77, 73, '删除', NULL, 'user:productinfo:delete', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (76, 73, '修改', NULL, 'user:productinfo:update', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (75, 73, '新增', NULL, 'user:productinfo:save', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (74, 73, '查看', NULL, 'user:productinfo:list,user:productinfo:info', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (73, 0, '产品信息', 'product/productinfo', NULL, 1, 'config', 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (72, 68, '删除', NULL, 'user:merchinfo:delete', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (71, 68, '修改', NULL, 'user:merchinfo:update', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (70, 68, '新增', NULL, 'user:merchinfo:save', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (69, 68, '查看', NULL, 'user:merchinfo:list,user:merchinfo:info', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (68, 0, '商户信息', 'merch/merchinfo', NULL, 1, 'config', 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (67, 63, '删除', NULL, 'user:orderinfo:delete', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (66, 63, '修改', NULL, 'user:orderinfo:update', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (65, 63, '新增', NULL, 'user:orderinfo:save', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (64, 63, '查看', NULL, 'user:orderinfo:list,user:orderinfo:info', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (63, 0, '订单信息', 'order/orderinfo', NULL, 1, 'config', 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (62, 58, '删除', NULL, 'user:info:delete', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (61, 58, '修改', NULL, 'user:info:update', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (60, 58, '新增', NULL, 'user:info:save', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (59, 58, '查看', NULL, 'user:info:list,user:info:info', 2, NULL, 6);
INSERT INTO "public"."sys_menu"("menu_id", "parent_id", "name", "url", "perms", "type", "icon", "order_num") VALUES (58, 0, '小程序用户', 'appuser/info', NULL, 1, 'config', 6);

P