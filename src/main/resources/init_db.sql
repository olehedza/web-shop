CREATE SCHEMA `webshop` DEFAULT CHARACTER SET utf8;

CREATE TABLE `webshop`.`products` (
    `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `price` DECIMAL(15,5) UNSIGNED NULL,
    PRIMARY KEY (`id`));

CREATE TABLE `webshop`.`users` (
    `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(256) NOT NULL,
    `first_name` VARCHAR(256) NULL DEFAULT '',
    `last_name` VARCHAR(256) NULL DEFAULT '',
    `email` VARCHAR(256) NULL DEFAULT '',
    `password` VARCHAR(256) NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);

ALTER TABLE `webshop`.`products`
    CHANGE COLUMN `id` `product_id` BIGINT(11) NOT NULL AUTO_INCREMENT ,
    CHANGE COLUMN `price` `price` DECIMAL(15,5) UNSIGNED NOT NULL;

CREATE TABLE `webshop`.`roles` (
    `role_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(256) NOT NULL,
    PRIMARY KEY (`role_id`),
    UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE);

CREATE TABLE `webshop`.`orders` (
    `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(11) NULL,
    PRIMARY KEY (`order_id`),
    INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `orders_users_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `webshop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `webshop`.`orders_products` (
    `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `order_id` BIGINT(11) NULL,
    `product_id` BIGINT(11) NULL,
    PRIMARY KEY (`id`),
    INDEX `orders_products_orders_fk_idx` (`order_id` ASC) VISIBLE,
    INDEX `orders_products_products_fk_idx` (`product_id` ASC) VISIBLE,
    CONSTRAINT `orders_products_orders_fk`
        FOREIGN KEY (`order_id`)
        REFERENCES `webshop`.`orders` (`order_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION,
    CONSTRAINT `orders_products_products_fk`
        FOREIGN KEY (`product_id`)
        REFERENCES `webshop`.`products` (`product_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION);

CREATE TABLE `webshop`.`shopping_carts` (
    `cart_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT(11) NULL,
    PRIMARY KEY (`cart_id`),
    INDEX `shopping_carts_users_fk_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `shopping_carts_users_fk`
        FOREIGN KEY (`user_id`)
        REFERENCES `webshop`.`users` (`user_id`)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION);

ALTER TABLE `webshop`.`shopping_carts`
    ADD UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE;

CREATE TABLE `webshop`.`shopping_carts_products` (
     `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
     `cart_id` BIGINT(11) NULL,
     `product_id` BIGINT(11) NULL,
     PRIMARY KEY (`id`),
     INDEX `shopping_carts_products_carts_fk_idx` (`cart_id` ASC) VISIBLE,
     INDEX `shopping_carts_products_products_fk_idx` (`product_id` ASC) VISIBLE,
     CONSTRAINT `shopping_carts_products_carts_fk`
         FOREIGN KEY (`cart_id`)
             REFERENCES `webshop`.`shopping_carts` (`cart_id`)
             ON DELETE NO ACTION
             ON UPDATE NO ACTION,
     CONSTRAINT `shopping_carts_products_products_fk`
         FOREIGN KEY (`product_id`)
             REFERENCES `webshop`.`products` (`product_id`)
             ON DELETE NO ACTION
             ON UPDATE NO ACTION);

CREATE TABLE `webshop`.`users_roles` (
     `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
     `user_id` BIGINT(11) NULL,
     `role_id` BIGINT(11) NULL,
     PRIMARY KEY (`id`),
     INDEX `users_roles_users_fk_idx` (`user_id` ASC) VISIBLE,
     INDEX `users_roles_roles_fk_idx` (`role_id` ASC) VISIBLE,
     CONSTRAINT `users_roles_users_fk`
         FOREIGN KEY (`user_id`)
             REFERENCES `webshop`.`users` (`user_id`)
             ON DELETE NO ACTION
             ON UPDATE NO ACTION,
     CONSTRAINT `users_roles_foles_fk`
         FOREIGN KEY (`role_id`)
             REFERENCES `webshop`.`roles` (`role_id`)
             ON DELETE NO ACTION
             ON UPDATE NO ACTION);

INSERT INTO `webshop`.`roles` (`role_name`) VALUES ('USER');
INSERT INTO `webshop`.`roles` (`role_name`) VALUES ('ADMIN');
