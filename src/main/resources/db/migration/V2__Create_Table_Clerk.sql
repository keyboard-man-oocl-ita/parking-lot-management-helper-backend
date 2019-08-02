CREATE TABLE `clerk` (
  `clerk_id` VARCHAR(255) NOT NULL,
  `phone_number` VARCHAR(16) NOT NULL,
  `password` VARCHAR(255) DEFAULT 'password1',
  `name` VARCHAR(255),
  `email` VARCHAR(255),
  `role` INT NOT NULL,
  `status` INT NOT NULL DEFAULT 1,
  `managed_by` VARCHAR(255),
  PRIMARY KEY (`clerk_id`));