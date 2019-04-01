-- MySQL Script generated by MySQL Workbench
-- Mon Apr  1 23:07:41 2019
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
CREATE SCHEMA IF NOT EXISTS `kobalttown` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin ;
SHOW WARNINGS;
USE `kobalttown` ;

-- -----------------------------------------------------
-- Table `kobalttown`.`user_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kobalttown`.`user_account` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nickname` VARCHAR(45) NOT NULL,
  `created_at` BIGINT NOT NULL,
  `updated_at` BIGINT NOT NULL,
  `modify_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UQ_ACCOUNT_NICKNAME` (`nickname` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `kobalttown`.`user_credential`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kobalttown`.`user_credential` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `account` INT UNSIGNED NOT NULL,
  `public_key` VARCHAR(45) NOT NULL,
  `secret_hash` VARCHAR(60) NOT NULL,
  `created_at` BIGINT NOT NULL,
  `modify_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `FK_CREDENTIAL_PK_ACCOUNT` (`account` ASC),
  UNIQUE INDEX `UQ_CREDENTIAL_PUBLIC_KEY` (`public_key` ASC),
  CONSTRAINT `FK_CREDENTIAL_PK_ACCOUNT`
    FOREIGN KEY (`account`)
    REFERENCES `kobalttown`.`user_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `kobalttown`.`log_credential`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kobalttown`.`log_credential` (
  `lid` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `crud` ENUM('C', 'R', 'U', 'D') NOT NULL,
  `log_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id` BIGINT UNSIGNED NOT NULL,
  `account` INT NOT NULL,
  `public_key` VARCHAR(45) NOT NULL,
  `secret_hash` VARCHAR(60) NOT NULL,
  `created_at` BIGINT NOT NULL,
  `modify_ts` DATETIME NOT NULL,
  PRIMARY KEY (`lid`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `kobalttown`.`log_account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kobalttown`.`log_account` (
  `lid` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `crud` ENUM('C', 'R', 'U', 'D') NOT NULL,
  `log_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id` INT UNSIGNED NOT NULL,
  `nickname` VARCHAR(45) NOT NULL,
  `created_at` BIGINT NOT NULL,
  `updated_at` BIGINT NOT NULL,
  `modify_ts` TIMESTAMP NOT NULL,
  PRIMARY KEY (`lid`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `kobalttown`.`user_article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kobalttown`.`user_article` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(64) NOT NULL,
  `summary` VARCHAR(256) NOT NULL,
  `body` TEXT NOT NULL,
  `creator` INT UNSIGNED NOT NULL,
  `created_at` BIGINT NOT NULL,
  `modify_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `FK_ARTICLE_CREATOR_PK_ACCOUNT` (`creator` ASC),
  CONSTRAINT `FK_ARTICLE_CREATOR_PK_ACCOUNT`
    FOREIGN KEY (`creator`)
    REFERENCES `kobalttown`.`user_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;
USE `kobalttown` ;

-- -----------------------------------------------------
-- View `kobalttown`.`view_account_credential`
-- -----------------------------------------------------
USE `kobalttown`;
CREATE  OR REPLACE VIEW `view_account_credential` AS
    SELECT 
        `a`.*,
        `c`.`id` AS `cid`,
        `c`.`public_key`,
        `c`.`secret_hash`,
        `c`.`created_at` AS `credential_created_at`,
        `c`.`modify_ts` AS `creadential_modify_ts`
    FROM
        `user_account` AS `a`,
        `user_credential` AS `c`
    WHERE
        `a`.`id` = `c`.`account`
    ORDER BY `a`.`id` ASC , `c`.`id` ASC
;
SHOW WARNINGS;

-- -----------------------------------------------------
-- View `kobalttown`.`view_article`
-- -----------------------------------------------------
USE `kobalttown`;
CREATE  OR REPLACE VIEW `view_article` AS
    SELECT 
        `a`.`id`,
        `a`.`title`,
        `a`.`summary`,
        `a`.`body`,
        `c`.`id` AS `creator_id`,
        `c`.`nickname` AS `creator`,
        `a`.`created_at`,
        `a`.`modify_ts`
    FROM
        `user_article` AS `a`,
        `user_account` AS `c`
    WHERE
        `a`.`creator` = `c`.`id`
    ORDER BY `a`.`id` ASC
;
SHOW WARNINGS;
USE `kobalttown`;

DELIMITER $$
SHOW WARNINGS$$
USE `kobalttown`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kobalttown`.`trigger_account_update` BEFORE UPDATE ON `user_account` FOR EACH ROW
BEGIN
	INSERT INTO `kobalttown`.`log_account` ( `crud`, `id`, `nickname`, `created_at`, `updated_at`, `modify_ts`)
	VALUES ('U', OLD.`id`, OLD.`nickname`, OLD.`created_at`, OLD.`updated_at`, OLD.`modify_ts`);
END$$

SHOW WARNINGS$$
SHOW WARNINGS$$
USE `kobalttown`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kobalttown`.`trigger_account_delete` BEFORE DELETE ON `user_account` FOR EACH ROW
BEGIN
	INSERT INTO `kobalttown`.`log_account` ( `crud`, `id`, `nickname`, `created_at`, `updated_at`, `modify_ts`)
	VALUES ('D', OLD.`id`, OLD.`nickname`, OLD.`created_at`, OLD.`updated_at`, OLD.`modify_ts`);
END$$

SHOW WARNINGS$$
SHOW WARNINGS$$
USE `kobalttown`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kobalttown`.`trigger_credential_update` BEFORE UPDATE ON `user_credential` FOR EACH ROW
BEGIN
	INSERT INTO `kobalttown`.`log_credential`(`crud`, `id`, `account`, `public_key`, `secret_hash`, `created_at`, `modify_ts`)
    VALUES ('U', OLD.`id`, OLD.`account`, OLD.`public_key`, OLD.`secret_hash`, OLD.`created_at`, OLD.`modify_ts`);
END$$

SHOW WARNINGS$$
SHOW WARNINGS$$
USE `kobalttown`$$
CREATE DEFINER = CURRENT_USER TRIGGER `kobalttown`.`trigger_credential_delete` BEFORE DELETE ON `user_credential` FOR EACH ROW
BEGIN
	INSERT INTO `kobalttown`.`log_credential`(`crud`, `id`, `account`, `public_key`, `secret_hash`, `created_at`, `modify_ts`)
    VALUES ('D', OLD.`id`, OLD.`account`, OLD.`public_key`, OLD.`secret_hash`, OLD.`created_at`, OLD.`modify_ts`);
END$$

SHOW WARNINGS$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
