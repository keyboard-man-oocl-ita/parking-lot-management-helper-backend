CREATE TABLE `user` (
  `user_id` VARCHAR(255) NOT NULL,
  `phone_number` VARCHAR(16) NOT NULL,
  `password` VARCHAR(255),
  `name` VARCHAR(255),
  `car_license` VARCHAR(128) NOT NULL,
  PRIMARY KEY (`user_id`));