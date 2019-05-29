-- MySQL Script generated by MySQL Workbench
-- Sun May 19 23:32:58 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema CookBook
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema CookBook
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CookBook` DEFAULT CHARACTER SET utf8 ;
USE `CookBook` ;

-- -----------------------------------------------------
-- Table `CookBook`.`CookBook`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CookBook`.`CookBook` (
  `CookBookName` VARCHAR(80) NOT NULL,
  PRIMARY KEY (`CookBookName`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CookBook`.`recipe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CookBook`.`recipe` (
  `recipe_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  `servings` INT NULL,
  `preparationTime` INT UNSIGNED NULL,
  `cookingTime` INT UNSIGNED NULL,
  `description` VARCHAR(255) NULL,
  `CookBook_CookBookName` VARCHAR(80) NOT NULL,
  `recipePicture` VARCHAR(45) NULL,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC, `recipe_id` ASC) VISIBLE,
  PRIMARY KEY (`recipe_id`, `CookBook_CookBookName`),
  INDEX `fk_recipe_CookBook1_idx` (`CookBook_CookBookName` ASC) VISIBLE,
  CONSTRAINT `fk_recipe_CookBook1`
    FOREIGN KEY (`CookBook_CookBookName`)
    REFERENCES `CookBook`.`CookBook` (`CookBookName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CookBook`.`ingredient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CookBook`.`ingredient` (
  `recipe_id` INT UNSIGNED NOT NULL,
  `name` VARCHAR(80) NOT NULL,
  `quantity` DECIMAL NULL,
  `unit` VARCHAR(45) NULL,
  `description` VARCHAR(80) NULL,
  PRIMARY KEY (`recipe_id`, `name`),
  INDEX `fk_Recipes_idx` (`recipe_id` ASC) VISIBLE,
  CONSTRAINT `fk_ingredient_recipe`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `CookBook`.`recipe` (`recipe_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CookBook`.`preparation_step`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CookBook`.`preparation_step` (
  `recipe_id` INT UNSIGNED NOT NULL,
  `step` INT UNSIGNED NOT NULL,
  `description` VARCHAR(255) NULL,
  PRIMARY KEY (`recipe_id`, `step`),
  INDEX `fk_Recipes_idx` (`recipe_id` ASC) VISIBLE,
  CONSTRAINT `fk_preparation_step_recipe`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `CookBook`.`recipe` (`recipe_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CookBook`.`DefaultNotAllowedPair`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CookBook`.`DefaultNotAllowedPair` (
  `DefaultNotAllowedPair_id` INT NOT NULL,
  `ForbidIngredient1` VARCHAR(45) NULL,
  `ForbidIngredient2` VARCHAR(45) NULL,
  `CookBook_CookBookName` VARCHAR(80) NOT NULL,
  PRIMARY KEY (`DefaultNotAllowedPair_id`, `CookBook_CookBookName`),
  INDEX `fk_DefaultNotAllowedPair_CookBook1_idx` (`CookBook_CookBookName` ASC) VISIBLE,
  CONSTRAINT `fk_DefaultNotAllowedPair_CookBook1`
    FOREIGN KEY (`CookBook_CookBookName`)
    REFERENCES `CookBook`.`CookBook` (`CookBookName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;