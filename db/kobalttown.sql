-- MySQL Script generated by MySQL Workbench
-- Sat Mar 14 17:02:40 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema kobalttown
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `kobalttown` ;

-- -----------------------------------------------------
-- Schema kobalttown
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `kobalttown` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci ;
SHOW WARNINGS;
USE `kobalttown` ;

-- -----------------------------------------------------
-- Table `kobalttown`.`user_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kobalttown`.`user_account` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nickname` VARCHAR(64) NOT NULL,
  `enabled` TINYINT NOT NULL DEFAULT 0,
  `created_at` BIGINT NOT NULL,
  `updated_at` BIGINT NOT NULL,
  `modify_ts` TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uq_account_nickname` (`nickname` ASC))
ENGINE = InnoDB
COMMENT = 'kr.lul.kobalttown.account.data.entity.AccountEntity';

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `kobalttown`.`user_credential`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kobalttown`.`user_credential` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `account` BIGINT UNSIGNED NOT NULL,
  `public_key` VARCHAR(255) NOT NULL,
  `secret_hash` VARCHAR(60) NOT NULL,
  `created_at` BIGINT NOT NULL,
  `modify_ts` TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  INDEX `fk_credential_pk_account` (`account` ASC),
  UNIQUE INDEX `uq_credential_public_key` (`public_key` ASC),
  CONSTRAINT `fk_credential_pk_account`
    FOREIGN KEY (`account`)
    REFERENCES `kobalttown`.`user_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'kr.lul.kobalttown.account.data.entity.CredentialEntity';

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `kobalttown`.`log_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kobalttown`.`log_account` (
  `lid` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `crud` ENUM('C', 'R', 'U', 'D') NOT NULL,
  `log_ts` TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `id` BIGINT UNSIGNED NOT NULL,
  `nickname` VARCHAR(45) NOT NULL,
  `enabled` TINYINT NOT NULL DEFAULT 0,
  `created_at` BIGINT NOT NULL,
  `updated_at` BIGINT NOT NULL,
  `modify_ts` DATETIME(3) NOT NULL,
  PRIMARY KEY (`lid`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `kobalttown`.`log_credential`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kobalttown`.`log_credential` (
  `lid` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `crud` ENUM('C', 'R', 'U', 'D') NOT NULL,
  `log_ts` TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `id` BIGINT UNSIGNED NOT NULL,
  `account` BIGINT UNSIGNED NOT NULL,
  `public_key` VARCHAR(255) NOT NULL,
  `secret_hash` VARCHAR(60) NOT NULL,
  `created_at` BIGINT NOT NULL,
  `modify_ts` DATETIME(3) NOT NULL,
  PRIMARY KEY (`lid`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `kobalttown`.`cfg_enable_code_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kobalttown`.`cfg_enable_code_status` (
  `id` INT UNSIGNED NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `valid` TINYINT NOT NULL DEFAULT 0,
  `description` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uq_validation_code_status_name` (`name` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `kobalttown`.`user_enable_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kobalttown`.`user_enable_code` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `account` BIGINT UNSIGNED NOT NULL,
  `email` VARCHAR(320) NOT NULL,
  `token` CHAR(64) NOT NULL,
  `expire_at` BIGINT NOT NULL,
  `status` INT UNSIGNED NOT NULL,
  `status_at` BIGINT NOT NULL,
  `created_at` BIGINT NOT NULL,
  `updated_at` BIGINT NOT NULL,
  `modify_ts` TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  INDEX `fk_enable_code_pk_account` (`account` ASC),
  UNIQUE INDEX `uq_enable_code_token` (`token` ASC),
  INDEX `idx_enable_code_email` (`email` ASC),
  INDEX `idx_enable_code_status` (`status` ASC, `status_at` DESC),
  CONSTRAINT `fk_enable_code_pk_account`
    FOREIGN KEY (`account`)
    REFERENCES `kobalttown`.`user_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_enable_code_pk_enable_code_status`
    FOREIGN KEY (`status`)
    REFERENCES `kobalttown`.`cfg_enable_code_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `kobalttown`.`log_enable_code`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kobalttown`.`log_enable_code` (
  `lid` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `crud` ENUM('C', 'R', 'U', 'D') NOT NULL,
  `log_ts` TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `id` BIGINT UNSIGNED NOT NULL,
  `account` BIGINT UNSIGNED NOT NULL,
  `email` VARCHAR(320) NOT NULL,
  `token` CHAR(64) NOT NULL,
  `expire_at` BIGINT NOT NULL,
  `status` INT NOT NULL,
  `status_at` BIGINT NOT NULL,
  `created_at` BIGINT NOT NULL,
  `updated_at` BIGINT NOT NULL,
  `modify_ts` DATETIME(3) NOT NULL,
  PRIMARY KEY (`lid`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `kobalttown`.`user_note`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kobalttown`.`user_note` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `version` INT UNSIGNED NOT NULL,
  `author` BIGINT UNSIGNED NOT NULL,
  `body` VARCHAR(1000) NOT NULL,
  `created_at` BIGINT NOT NULL,
  `updated_at` BIGINT NOT NULL,
  `deleted_at` BIGINT NULL DEFAULT NULL,
  `modify_ts` TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  INDEX `fk_note_pk_account` (`author` ASC),
  INDEX `idx_note_deleted` (`deleted_at` ASC),
  CONSTRAINT `fk_note_pk_account`
    FOREIGN KEY (`author`)
    REFERENCES `kobalttown`.`user_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `kobalttown`.`user_note_snapshot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kobalttown`.`user_note_snapshot` (
  `note` BIGINT UNSIGNED NOT NULL,
  `version` INT UNSIGNED NOT NULL,
  `body` VARCHAR(1000) NOT NULL,
  `created_at` BIGINT NOT NULL,
  `modify_ts` TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`note`, `version`),
  CONSTRAINT `fk_note_snapshot_pk_note`
    FOREIGN KEY (`note`)
    REFERENCES `kobalttown`.`user_note` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `kobalttown`.`user_note_comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kobalttown`.`user_note_comment` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `note` BIGINT UNSIGNED NOT NULL COMMENT '(note, note_version) -> user_note_snapshot',
  `note_version` INT UNSIGNED NOT NULL COMMENT '(note, note_version) -> user_note_snapshot',
  `account` BIGINT UNSIGNED NOT NULL COMMENT 'author',
  `body` TEXT NOT NULL,
  `created_at` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_note_comment_target` (`note` ASC, `note_version` ASC),
  INDEX `fk_note_comment_pk_account` (`account` ASC),
  CONSTRAINT `fk_note_comment_pk_note`
    FOREIGN KEY (`note`)
    REFERENCES `kobalttown`.`user_note` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_note_comment_pk_account`
    FOREIGN KEY (`account`)
    REFERENCES `kobalttown`.`user_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
USE `kobalttown` ;

-- -----------------------------------------------------
-- View `kobalttown`.`view_account`
-- -----------------------------------------------------
USE `kobalttown`;
CREATE  OR REPLACE VIEW `view_account` AS
    SELECT 
        `a`.`id`,
        `a`.`nickname`,
        `a`.`enabled`,
        `a`.`created_at` AS `account_created_at`,
        `a`.`updated_at` AS `account_updated_at`,
        `a`.`modify_ts` AS `account_modify_ts`,
        `c`.`id` AS `cid`,
        `c`.`public_key`,
        `c`.`secret_hash`,
        `c`.`created_at`,
        `c`.`modify_ts`
    FROM
        `user_account` AS `a`
            LEFT JOIN
        `user_credential` AS `c` ON `a`.`id` = `c`.`account`
;
SHOW WARNINGS;

-- -----------------------------------------------------
-- View `kobalttown`.`view_account_enable_code`
-- -----------------------------------------------------
USE `kobalttown`;
CREATE  OR REPLACE VIEW `view_account_enable_code` AS
    SELECT 
        `a`.`id`,
        `a`.`nickname`,
        `a`.`enabled`,
        `a`.`created_at` AS `account_created_at`,
        `a`.`updated_at` AS `account_updated_at`,
        `a`.`modify_ts` AS `account_modify_ts`,
        `c`.`id` AS `cid`,
        `c`.`email`,
        `c`.`token`,
        `c`.`expire_at`,
        `s`.`name` AS `status`,
        `c`.`status_at`,
        `c`.`created_at`,
        `c`.`updated_at`,
        `c`.`modify_ts`
    FROM
        `user_account` AS `a`
            LEFT JOIN
        `user_enable_code` AS `c` ON `a`.`id` = `c`.`account`
            LEFT JOIN
        `cfg_enable_code_status` AS `s` ON `c`.`status` = `s`.`id`
;
SHOW WARNINGS;

-- -----------------------------------------------------
-- View `kobalttown`.`view_note`
-- -----------------------------------------------------
USE `kobalttown`;
CREATE  OR REPLACE VIEW `view_note` AS
    SELECT 
        `n`.`id`,
        `s`.`version`,
        `n`.`author`,
        `a`.`nickname`,
        `s`.`body`,
        `n`.`created_at`,
        `s`.`created_at` AS `updated_at`,
        `n`.`deleted_at`
    FROM
        kobalttown.user_note AS n
            LEFT JOIN
        kobalttown.user_note_snapshot AS s ON n.id = s.note
            LEFT JOIN
        kobalttown.user_account AS a ON n.author = a.id
    ORDER BY n.id ASC , s.version ASC
;
SHOW WARNINGS;
USE `kobalttown`;

DELIMITER $$
SHOW WARNINGS$$
USE `kobalttown`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kobalttown`.`log_account_update` AFTER UPDATE ON `user_account` FOR EACH ROW
BEGIN
	INSERT INTO `kobalttown`.`log_account` (`CRUD`, `id`, `nickname`, `enabled`, `created_at`, `updated_at`, `modify_ts`)
	VALUES ('U', OLD.`id`, OLD.`nickname`, OLD.`enabled`, OLD.`created_at`, OLD.`updated_at`, OLD.`modify_ts`);
END$$

SHOW WARNINGS$$
SHOW WARNINGS$$
USE `kobalttown`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kobalttown`.`log_account_delete` AFTER DELETE ON `user_account` FOR EACH ROW
BEGIN
	INSERT INTO `kobalttown`.`log_account` (`CRUD`, `id`, `nickname`, `enabled`, `created_at`, `updated_at`, `modify_ts`)
	VALUES
		('D', OLD.`id`, OLD.`nickname`, OLD.`enabled`, OLD.`created_at`, OLD.`updated_at`, OLD.`modify_ts`);
END$$

SHOW WARNINGS$$
SHOW WARNINGS$$
USE `kobalttown`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kobalttown`.`log_credential_update` BEFORE UPDATE ON `user_credential` FOR EACH ROW
BEGIN
	INSERT INTO `kobalttown`.`log_credential` (`crud`, `id`, `account`, `public_key`, `secret_hash`, `created_at`, `modify_ts`)
	VALUES ('U', OLD.`id`, OLD.`account`, OLD.`public_key`, OLD.`secret_hash`, OLD.`created_at`, OLD.`modify_ts`);
END$$

SHOW WARNINGS$$
SHOW WARNINGS$$
USE `kobalttown`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kobalttown`.`log_credential_delete` BEFORE DELETE ON `user_credential` FOR EACH ROW
BEGIN
	INSERT INTO `kobalttown`.`log_credential` (`crud`, `id`, `account`, `public_key`, `secret_hash`, `created_at`, `modify_ts`)
	VALUES ('D', OLD.`id`, OLD.`account`, OLD.`public_key`, OLD.`secret_hash`, OLD.`created_at`, OLD.`modify_ts`);
END$$

SHOW WARNINGS$$
SHOW WARNINGS$$
USE `kobalttown`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kobalttown`.`log_enable_code_update` AFTER UPDATE ON `user_enable_code` FOR EACH ROW
BEGIN
    INSERT INTO `kobalttown`.`log_enable_code` (`crud`, `id`, `account`, `email`, `token`, `expire_at`, `status`, `status_at`, `created_at`, `updated_at`, `modify_ts`)
    VALUES ('U', OLD.`id`, OLD.`account`, OLD.`email`, OLD.`token`, OLD.`expire_at`, OLD.`status`, OLD.`status_at`, OLD.`created_at`, OLD.`updated_at`, OLD.`modify_ts`);
END$$

SHOW WARNINGS$$
SHOW WARNINGS$$
USE `kobalttown`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kobalttown`.`log_enable_code_delete` AFTER DELETE ON `user_enable_code` FOR EACH ROW
BEGIN
    INSERT INTO `kobalttown`.`log_enable_code` (`crud`, `id`, `account`, `email`, `token`, `expire_at`, `status`, `status_at`, `created_at`, `updated_at`, `modify_ts`)
    VALUES ('D', OLD.`id`, OLD.`account`, OLD.`email`, OLD.`token`, OLD.`expire_at`, OLD.`status`, OLD.`status_at`, OLD.`created_at`, OLD.`updated_at`, OLD.`modify_ts`);
END$$

SHOW WARNINGS$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `kobalttown`.`cfg_enable_code_status`
-- -----------------------------------------------------
START TRANSACTION;
USE `kobalttown`;
INSERT INTO `kobalttown`.`cfg_enable_code_status` (`id`, `name`, `valid`, `description`) VALUES (0, 'ISSUED', 1, '기본 상태. 사용 가능함.');
INSERT INTO `kobalttown`.`cfg_enable_code_status` (`id`, `name`, `valid`, `description`) VALUES (1, 'USED', 0, '계정 인증에 사용됨.');
INSERT INTO `kobalttown`.`cfg_enable_code_status` (`id`, `name`, `valid`, `description`) VALUES (2, 'EXPIRED', 0, '유효기간이 지나 만료됨.');
INSERT INTO `kobalttown`.`cfg_enable_code_status` (`id`, `name`, `valid`, `description`) VALUES (3, 'INACTIVE', 0, '사용하지 않은 코드이지만 재발급 등의 이유로 무효화됨.');

COMMIT;

