CREATE TABLE `parking_lot_order` (
  `order_id` VARCHAR(255) NOT NULL,
  `clerk_id` VARCHAR(255),
  `parking_lot_id` VARCHAR(255),
  `user_id` VARCHAR(255) NOT NULL,
  `car_license` VARCHAR(128) NOT NULL,
  `created_time` BIGINT(64) NOT NULL,
  `end_time` BIGINT(64),
  `strategy_id` VARCHAR(255),
  `status` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`order_id`));