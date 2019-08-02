CREATE TABLE `parking_lot` (
  `parking_lot_id` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255),
  `description` VARCHAR(255),
  `capacity` INT NOT NULL,
  `latitude` FLOAT,
  `longtitude` FLOAT,
  `create_by` VARCHAR(255) NOT NULL,
  `managed_by` VARCHAR(255),
  PRIMARY KEY (`parking_lot_id`));