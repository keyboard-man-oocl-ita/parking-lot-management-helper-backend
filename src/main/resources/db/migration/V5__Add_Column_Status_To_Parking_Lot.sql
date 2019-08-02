ALTER TABLE `parking_lot`
ADD COLUMN `status` INT NULL DEFAULT 1 AFTER `managed_by`;