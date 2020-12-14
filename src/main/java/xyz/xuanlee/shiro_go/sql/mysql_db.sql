/*
 Navicat Premium Data Transfer

 Source Server         : self
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : javaee_lesson

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 14/12/2020 09:47:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_department
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `d_name` char(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_official_supply_class_operation_record
-- ----------------------------
DROP TABLE IF EXISTS `t_official_supply_class_operation_record`;
CREATE TABLE `t_official_supply_class_operation_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `u_id` bigint(20) NOT NULL,
  `b_id` bigint(20) NOT NULL,
  `b_name` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `offi_sup_cls_rec_usr_fk`(`u_id`) USING BTREE,
  INDEX `offi_sup_cls_rec_offi_sup_sec_cls_fk`(`b_id`) USING BTREE,
  INDEX `offi_sup_cls_rec_offi_sup_sec_cls_name_fk`(`b_name`) USING BTREE,
  CONSTRAINT `offi_sup_cls_rec_offi_sup_sec_cls_fk` FOREIGN KEY (`b_id`) REFERENCES `t_official_supply_second_class` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `offi_sup_cls_rec_offi_sup_sec_cls_name_fk` FOREIGN KEY (`b_name`) REFERENCES `t_official_supply_second_class` (`s_c_name`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `offi_sup_cls_rec_usr_fk` FOREIGN KEY (`u_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_official_supply_first_class
-- ----------------------------
DROP TABLE IF EXISTS `t_official_supply_first_class`;
CREATE TABLE `t_official_supply_first_class`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `f_c_name` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_official_supply_second_class
-- ----------------------------
DROP TABLE IF EXISTS `t_official_supply_second_class`;
CREATE TABLE `t_official_supply_second_class`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `s_c_name` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `s_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sec_fir_fk`(`s_id`) USING BTREE,
  INDEX `s_c_name`(`s_c_name`) USING BTREE,
  CONSTRAINT `sec_fir_fk` FOREIGN KEY (`s_id`) REFERENCES `t_official_supply_first_class` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `u_name` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `u_pswd` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `isLock` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `lastLoginDateTime` datetime(0) NULL DEFAULT NULL,
  `d_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_dep_fk`(`d_id`) USING BTREE,
  CONSTRAINT `user_dep_fk` FOREIGN KEY (`d_id`) REFERENCES `t_department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
