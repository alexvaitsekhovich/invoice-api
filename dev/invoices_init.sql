CREATE TABLE `invoices` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `number` varchar(25) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `netto` decimal(8,2) NOT NULL DEFAULT '0.00',
  `brutto` decimal(8,2) NOT NULL DEFAULT '0.00',
  `balance` decimal(8,2) NOT NULL DEFAULT '0.00',
  `debtorId` int(11) unsigned NOT NULL,
  `serviceFrom` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `serviceUntil` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `state` tinyint NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `debtor_id_key` (`debtorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (1, 'R-201501-01', '2015-02-01 00:00:00', '77.20', '90.44', '0', '12', '2015-01-01 00:00:00', '2015-01-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (2, 'R-201502-01', '2015-03-01 00:00:00', '84.00', '99.96', '0', '12', '2015-02-01 00:00:00', '2015-02-28 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (3, 'R-201503-01', '2015-04-01 00:00:00', '50.00', '59.50', '0', '12', '2015-03-01 00:00:00', '2015-03-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (4, 'R-201504-01', '2015-05-01 00:00:00', '84.00', '99.96', '0', '12', '2015-04-01 00:00:00', '2015-04-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (5, 'R-201505-01', '2015-06-01 00:00:00', '94.00', '111.86', '0', '12', '2015-05-01 00:00:00', '2015-05-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (6, 'R-201506-01', '2015-07-01 00:00:00', '90.00', '107.10', '0', '12', '2015-06-01 00:00:00', '2015-06-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (7, 'R-201507-01', '2015-08-01 00:00:00', '66.00', '78.54', '0', '12', '2015-07-01 00:00:00', '2015-07-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (8, 'R-201508-01', '2015-09-01 00:00:00', '51.00', '60.69', '0', '12', '2015-08-01 00:00:00', '2015-08-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (9, 'R-201509-01', '2015-10-01 00:00:00', '74.00', '88.06', '0', '12', '2015-09-01 00:00:00', '2015-09-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (10, 'R-201510-01', '2015-11-01 00:00:00', '79.00', '94.01', '0', '12', '2015-10-01 00:00:00', '2015-10-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (11, 'R-201511-01', '2015-12-01 00:00:00', '67.00', '79.73', '0', '12', '2015-11-01 00:00:00', '2015-11-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (12, 'R-201512-01', '2016-01-01 00:00:00', '98.00', '116.62', '0', '12', '2015-12-01 00:00:00', '2015-12-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (13, 'R-201601-01', '2016-02-01 00:00:00', '103.67', '123.36', '0', '12', '2016-01-01 00:00:00', '2016-01-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (14, 'R-201602-01', '2016-03-01 00:00:00', '89.33', '106.31', '0', '12', '2016-02-01 00:00:00', '2016-02-29 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (15, 'R-201603-01', '2016-04-01 00:00:00', '127.00', '151.13', '0', '12', '2016-03-01 00:00:00', '2016-03-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (16, 'R-201604-01', '2016-05-01 00:00:00', '88.67', '105.51', '0', '12', '2016-04-01 00:00:00', '2016-04-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (17, 'R-201605-01', '2016-06-01 00:00:00', '115.67', '137.64', '0', '12', '2016-05-01 00:00:00', '2016-05-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (18, 'R-201606-01', '2016-07-01 00:00:00', '131.67', '156.68', '0', '12', '2016-06-01 00:00:00', '2016-06-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (19, 'R-201607-01', '2016-08-01 00:00:00', '118.67', '141.21', '0', '12', '2016-07-01 00:00:00', '2016-07-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (20, 'R-201608-01', '2016-09-01 00:00:00', '118.00', '140.42', '0', '12', '2016-08-01 00:00:00', '2016-08-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (21, 'R-201609-01', '2016-10-01 00:00:00', '107.33', '127.73', '0', '12', '2016-09-01 00:00:00', '2016-09-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (22, 'R-201610-01', '2016-11-01 00:00:00', '102.00', '121.38', '0', '12', '2016-10-01 00:00:00', '2016-10-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (23, 'R-201611-01', '2016-12-01 00:00:00', '93.00', '110.67', '0', '12', '2016-11-01 00:00:00', '2016-11-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (24, 'R-201612-01', '2017-01-01 00:00:00', '135.33', '161.05', '0', '12', '2016-12-01 00:00:00', '2016-12-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (25, 'R-201701-01', '2017-02-01 00:00:00', '169.67', '201.90', '0', '12', '2017-01-01 00:00:00', '2017-01-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (26, 'R-201702-01', '2017-03-01 00:00:00', '130.33', '155.10', '0', '12', '2017-02-01 00:00:00', '2017-02-28 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (27, 'R-201703-01', '2017-04-01 00:00:00', '149.67', '178.10', '0', '12', '2017-03-01 00:00:00', '2017-03-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (28, 'R-201704-01', '2017-05-01 00:00:00', '120.67', '143.59', '0', '12', '2017-04-01 00:00:00', '2017-04-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (29, 'R-201705-01', '2017-06-01 00:00:00', '151.00', '179.69', '0', '12', '2017-05-01 00:00:00', '2017-05-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (30, 'R-201706-01', '2017-07-01 00:00:00', '165.33', '196.75', '0', '12', '2017-06-01 00:00:00', '2017-06-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (31, 'R-201707-01', '2017-08-01 00:00:00', '162.67', '193.57', '0', '12', '2017-07-01 00:00:00', '2017-07-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (32, 'R-201708-01', '2017-09-01 00:00:00', '163.67', '194.76', '0', '12', '2017-08-01 00:00:00', '2017-08-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (33, 'R-201709-01', '2017-10-01 00:00:00', '150.33', '178.90', '0', '12', '2017-09-01 00:00:00', '2017-09-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (34, 'R-201710-01', '2017-11-01 00:00:00', '135.67', '161.44', '0', '12', '2017-10-01 00:00:00', '2017-10-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (35, 'R-201711-01', '2017-12-01 00:00:00', '138.67', '165.01', '0', '12', '2017-11-01 00:00:00', '2017-11-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (36, 'R-201712-01', '2018-01-01 00:00:00', '166.33', '197.94', '0', '12', '2017-12-01 00:00:00', '2017-12-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (37, 'R-201801-01', '2018-02-01 00:00:00', '217.00', '258.23', '0', '12', '2018-01-01 00:00:00', '2018-01-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (38, 'R-201802-01', '2018-03-01 00:00:00', '166.00', '197.54', '0', '12', '2018-02-01 00:00:00', '2018-02-28 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (39, 'R-201803-01', '2018-04-01 00:00:00', '170.00', '202.30', '0', '12', '2018-03-01 00:00:00', '2018-03-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (40, 'R-201804-01', '2018-05-01 00:00:00', '201.00', '239.19', '0', '12', '2018-04-01 00:00:00', '2018-04-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (41, 'R-201805-01', '2018-06-01 00:00:00', '209.00', '248.71', '0', '12', '2018-05-01 00:00:00', '2018-05-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (42, 'R-201806-01', '2018-07-01 00:00:00', '197.00', '234.43', '0', '12', '2018-06-01 00:00:00', '2018-06-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (43, 'R-201807-01', '2018-08-01 00:00:00', '212.00', '252.28', '0', '12', '2018-07-01 00:00:00', '2018-07-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (44, 'R-201808-01', '2018-09-01 00:00:00', '171.00', '203.49', '0', '12', '2018-08-01 00:00:00', '2018-08-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (45, 'R-201809-01', '2018-10-01 00:00:00', '162.00', '192.78', '0', '12', '2018-09-01 00:00:00', '2018-09-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (46, 'R-201810-01', '2018-11-01 00:00:00', '188.00', '223.72', '0', '12', '2018-10-01 00:00:00', '2018-10-31 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (47, 'R-201811-01', '2018-12-01 00:00:00', '178.00', '211.82', '0', '12', '2018-11-01 00:00:00', '2018-11-30 00:00:00', '1');
INSERT INTO `invoices` (`id`, `number`, `date`, `netto`, `brutto`, `balance`, `debtorId`, `serviceFrom`, `serviceUntil`, `state`) VALUES (48, 'R-201812-01', '2019-01-01 00:00:00', '196.00', '233.24', '0', '12', '2018-12-01 00:00:00', '2018-12-31 00:00:00', '1');

CREATE TABLE `invoice_positions` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `invoiceId` int(11) unsigned NOT NULL,
  `description` varchar(255) NOT NULL,
  `amount` int(11) unsigned NOT NULL DEFAULT 1,
  `netto` decimal(8,2) NOT NULL DEFAULT '0.00',
  `vat` decimal(8,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`),
  KEY `invoice_id_key` (`invoiceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `invoice_positions` (`invoiceId`, `description`, `amount`, `netto`, `vat`) VALUES ('1', 'Big black book', '1', '12', '7');
INSERT INTO `invoice_positions` (`invoiceId`, `description`, `amount`, `netto`, `vat`) VALUES ('1', 'Cat food in cans', '3', '1.4', '7');
INSERT INTO `invoice_positions` (`invoiceId`, `description`, `amount`, `netto`, `vat`) VALUES ('1', 'Old red hat', '2', '24', '19');
INSERT INTO `invoice_positions` (`invoiceId`, `description`, `amount`, `netto`, `vat`) VALUES ('1', 'Dead black chicken', '1', '13', '23');
