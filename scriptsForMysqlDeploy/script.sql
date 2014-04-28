DROP SCHEMA IF EXISTS issuetracker;
CREATE SCHEMA issuetracker DEFAULT CHARACTER SET utf8;

DROP TABLE IF EXISTS issuetracker.builds;
CREATE TABLE issuetracker.builds (
	id INT NOT NULL AUTO_INCREMENT ,
	project INT NULL,
	version VARCHAR(45) NULL,
	PRIMARY KEY (id),
	UNIQUE INDEX id_UNIQUE (id ASC)
);

INSERT INTO `issuetracker`.`builds` (`id`, `project`, `version`) VALUES ('1', '1', '0.6');
INSERT INTO `issuetracker`.`builds` (`id`, `project`, `version`) VALUES ('2', '2', '0.4');
INSERT INTO `issuetracker`.`builds` (`id`, `project`, `version`) VALUES ('3', '3', '1.0');
INSERT INTO `issuetracker`.`builds` (`id`, `project`, `version`) VALUES ('4', '4', '0.8');
INSERT INTO `issuetracker`.`builds` (`id`, `project`, `version`) VALUES ('5', '5', '0.2');


DROP TABLE IF EXISTS issuetracker.issues;
CREATE TABLE issuetracker.issues (
	id INT NOT NULL AUTO_INCREMENT,
	createdate VARCHAR(45) NULL,
	createdby INT NULL,
	modifydate VARCHAR(45) NULL,
	modifiedby INT NULL,
	summary TEXT NULL,
	description TEXT NULL,
	status VARCHAR(45) NULL,
	resolution VARCHAR(45) NULL,
	issuetype VARCHAR(45) NULL,
	priority VARCHAR(45) NULL,
	projectid INT NULL,
	buildfound INT NULL,
	assignee INT NULL,
	PRIMARY KEY (id),
	UNIQUE INDEX id_UNIQUE (id ASC)
);
  
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('1', '01.01.2014', '3', 'Progress bar is blinking', 'When a user tries to download a file, the progress bar is blinking for some strange reason.', 'assigned', 'worksforme', 'cosmetic', 'important', '1', '1', '5');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `modifydate`, `modifiedby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('2', '01.01.2014', '4', '02.01.2014', '5', 'Incorrect title on the main page', 'The title in the main page should be exactly as in the requirements of the customer.', 'in_progress', 'worksforme', 'cosmetic', 'major', '2', '2', '5');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `modifydate`, `modifiedby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('3', '02.01.2014', '2', '03.01.2014', '7', 'Invalid transaction', 'Payment does not come into the account. There is a serious bug with some transactions.', 'resolved', 'fixed', 'bug', 'critical', '2', '2', '7');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('4', '02.01.2014', '1', 'Window doesn\'t close', 'The window, that shows a list of transactions doesn\'t close, when you click on the cross.', 'assigned', 'worksforme', 'bug', 'important', '2', '2', '2');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `modifydate`, `modifiedby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('5', '02.01.2014', '5', '03.01.2014', '8', 'Images processing too slow', 'When a user upload an image to process, it takes too long. Need to check process\'s perfomance.', 'closed', 'invalid', 'performance', 'important', '1', '1', '8');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`) VALUES ('6', '03.01.2014', '5', 'The style for the button doesn\'t made', 'In our android app button for a checking updates yet without a style.', 'new', 'worksforme', 'feature', 'minor', '3', '3');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `modifydate`, `modifiedby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('7', '03.01.2014', '2', '04.01.2014', '2', 'Runtime error while checking updates', 'When the button for checking updates is pressed there an error occurs.', 'resolved', 'fixed', 'bug', 'major', '3', '3', '8');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('8', '04.01.2014', '1', 'Need to create a menu for administrator', 'When an administrator is authorised in the top left corner should be the link to special menu.', 'assigned', 'worksforme', 'feature', 'critical', '4', '4', '2');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `modifydate`, `modifiedby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('9', '04.01.2014', '1', '04.01.2014', '3', 'Link for the admin centre is incorrect', 'Link for the admin centre (in the top right corner) is incorrect. It leads to the profile.', 'reopened', 'worksforme', 'bug', 'minor', '4', '4', '2');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('10', '05.01.2014', '3', 'The table should be with a 4px border-radius', 'The table should be with a 4px border-radius', 'closed', 'fixed', 'cosmetic', 'important', '4', '4', '5');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `modifydate`, `modifiedby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('11', '05.01.2014', '2', '05.01.2014', '3', 'Hangs while opening frame with the logs', 'Application hangs while opening frame with the logs.', 'resolved', 'fixed', 'bug', 'critical', '1', '1', '3');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('12', '05.01.2014', '6', 'Scroll into the frame should be twice as bigger', 'The scroll in the frame, which shows the processing progress, should be twice as bigger. That is requirement of the client.', 'assigned', 'worksforme', 'bug', 'important', '1', '1', '2');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('13', '05.01.2014', '6', 'Vertical alignment should be in the each block', 'Vertical alignment should be in the each block (cell of the table), because it\'s look not much esthetical', 'assigned', 'worksforme', 'bug', 'important', '2', '2', '3');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `modifydate`, `modifiedby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('14', '06.01.2014', '6', '06.01.2014', '6', 'Activity does not save it\'s state', 'The Activity that shows the list of images, when minimized and then recreated - the state lost.', 'resolved', 'invalid', 'bug', 'major', '4', '4', '4');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `modifydate`, `modifiedby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('15', '06.01.2014', '1', '07.01.2014', '5', 'Animation lasts too long', 'Animation of the search-fragment opening lasts too long', 'assigned', 'worksforme', 'cosmetic', 'minor', '3', '3', '5');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('16', '06.01.2014', '3', 'Runtime error during network connecting', 'Runtime error during network connecting, when you try to open the list with images', 'assigned', 'worksforme', 'bug', 'critical', '3', '3', '2');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `modifydate`, `modifiedby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('17', '06.01.2014', '8', '07.01.2014', '8', 'Russian is not supported', 'Russian is not supported, check out the properties', 'closed', 'invalid', 'bug', 'minor', '5', '5', '3');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `modifydate`, `modifiedby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('18', '07.01.2014', '8', '07.01.2014', '4', 'Eye motion capture working not good for user with glasses.', 'Algorithm of the eye motion capture should be improved, because reflections impede.', 'in_progress', 'worksforme', 'feature', 'critical', '5', '5', '4');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `modifydate`, `modifiedby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('19', '07.01.2014', '8', '08.01.2014', '4', 'While scrolling with eye motion, sometimes application hangs.', 'While scrolling with eye motion, sometimes application hangs.', 'resolved', 'worksforme', 'feature', 'critical', '5', '5', '4');
INSERT INTO `issuetracker`.`issues` (`id`, `createdate`, `createdby`, `modifydate`, `modifiedby`, `summary`, `description`, `status`, `resolution`, `issuetype`, `priority`, `projectid`, `buildfound`, `assignee`) VALUES ('20', '07.01.2014', '8', '08.01.2014', '2', 'Sweden should be supported', 'In Sweden we have some customers, so it\'s required to support Swedish language.', 'resolved', 'wontfix', 'feature', 'important', '5', '5', '2');

UPDATE `issuetracker`.`issues` SET `status`='2', `resolution`='4', `issuetype`='1', `priority`='3' WHERE `id`='1';
UPDATE `issuetracker`.`issues` SET `status`='2', `resolution`='4', `issuetype`='2', `priority`='3' WHERE `id`='4';
UPDATE `issuetracker`.`issues` SET `status`='2', `resolution`='4', `issuetype`='3', `priority`='1' WHERE `id`='8';
UPDATE `issuetracker`.`issues` SET `status`='2', `resolution`='4', `issuetype`='2', `priority`='3' WHERE `id`='12';
UPDATE `issuetracker`.`issues` SET `status`='2', `resolution`='4', `issuetype`='2', `priority`='3' WHERE `id`='13';
UPDATE `issuetracker`.`issues` SET `status`='2', `resolution`='4', `issuetype`='1', `priority`='4' WHERE `id`='15';
UPDATE `issuetracker`.`issues` SET `status`='2', `resolution`='4', `issuetype`='2', `priority`='1' WHERE `id`='16';
UPDATE `issuetracker`.`issues` SET `status`='1', `resolution`='4', `issuetype`='3', `priority`='4' WHERE `id`='6';
UPDATE `issuetracker`.`issues` SET `status`='3', `resolution`='4', `issuetype`='1', `priority`='2' WHERE `id`='2';
UPDATE `issuetracker`.`issues` SET `status`='3', `resolution`='4', `issuetype`='3', `priority`='1' WHERE `id`='18';
UPDATE `issuetracker`.`issues` SET `status`='4', `resolution`='1', `issuetype`='2', `priority`='1' WHERE `id`='3';
UPDATE `issuetracker`.`issues` SET `status`='4', `resolution`='1', `issuetype`='2', `priority`='2' WHERE `id`='7';
UPDATE `issuetracker`.`issues` SET `status`='4', `resolution`='1', `issuetype`='2', `priority`='1' WHERE `id`='11';
UPDATE `issuetracker`.`issues` SET `status`='4', `resolution`='2', `issuetype`='2', `priority`='2' WHERE `id`='14';
UPDATE `issuetracker`.`issues` SET `status`='4', `resolution`='4', `issuetype`='3', `priority`='1' WHERE `id`='19';
UPDATE `issuetracker`.`issues` SET `status`='4', `resolution`='3', `issuetype`='3', `priority`='3' WHERE `id`='20';
UPDATE `issuetracker`.`issues` SET `status`='5', `resolution`='2', `issuetype`='4', `priority`='3' WHERE `id`='5';
UPDATE `issuetracker`.`issues` SET `status`='5', `resolution`='1', `issuetype`='1', `priority`='3' WHERE `id`='10';
UPDATE `issuetracker`.`issues` SET `status`='5', `resolution`='2', `issuetype`='2', `priority`='4' WHERE `id`='17';
UPDATE `issuetracker`.`issues` SET `status`='6', `resolution`='4', `issuetype`='2', `priority`='4' WHERE `id`='9';

ALTER TABLE issuetracker.issues
	CHANGE COLUMN status status INT NULL DEFAULT NULL,
	CHANGE COLUMN resolution resolution INT NULL DEFAULT NULL,
	CHANGE COLUMN issuetype issuetype INT NULL DEFAULT NULL,
	CHANGE COLUMN priority priority INT NULL DEFAULT NULL,
	CHANGE COLUMN projectid project INT(11) NULL DEFAULT NULL;

UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='1';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='2';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='3';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='4';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='6';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='7';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='8';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='9';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='11';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='12';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='13';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='14';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='15';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='16';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='18';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='19';
UPDATE `issuetracker`.`issues` SET `resolution`=NULL WHERE `id`='20';


DROP TABLE IF EXISTS issuetracker.projects;
CREATE TABLE issuetracker.projects (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(45) NULL,
	description TEXT NULL,
	manager INT NULL,
	PRIMARY KEY (id)
);
  
INSERT INTO `issuetracker`.`projects` (`id`, `name`, `description`, `manager`) VALUES ('1', 'IMAGEnarium', 'Web-application for upload and processing photos in a great variety of ways.', '3');
INSERT INTO `issuetracker`.`projects` (`id`, `name`, `description`, `manager`) VALUES ('2', 'MTBank Linux-client', 'Java application for Linux to interact with MTBank server', '4');
INSERT INTO `issuetracker`.`projects` (`id`, `name`, `description`, `manager`) VALUES ('3', 'Drive2 for Android', 'Android client for Drive2.ru', '5');
INSERT INTO `issuetracker`.`projects` (`id`, `name`, `description`, `manager`) VALUES ('4', 'BugTracker', 'Web-application for our company for tracking bugs in our projects.', '1');
INSERT INTO `issuetracker`.`projects` (`id`, `name`, `description`, `manager`) VALUES ('5', 'EYEkey', 'Application for using eye-movements for typing without keyboard or mouse.', '8');


DROP TABLE IF EXISTS issuetracker.users;
CREATE TABLE issuetracker.users (
	id INT NOT NULL AUTO_INCREMENT,
	firstName VARCHAR(45) NULL,
	lastName VARCHAR(45) NULL,
	email VARCHAR(45) NULL,
	role VARCHAR(45) NULL,
	password VARCHAR(45) NULL,
	PRIMARY KEY (id)
);
  
INSERT INTO `issuetracker`.`users` (`id`, `firstName`, `lastName`, `email`, `role`, `password`) VALUES ('1', 'Dzmitry', 'Salnikau', 'dzmitry_salnikau@epam.com', '2', '123456');
INSERT INTO `issuetracker`.`users` (`id`, `firstName`, `lastName`, `email`, `role`, `password`) VALUES ('2', 'Sergey', 'German', 'sergey_german@epam.com', '1', '222222');
INSERT INTO `issuetracker`.`users` (`id`, `firstName`, `lastName`, `email`, `role`, `password`) VALUES ('3', 'Viktor', 'Potko', 'viktor_potko@epam.com', '1', '333333');
INSERT INTO `issuetracker`.`users` (`id`, `firstName`, `lastName`, `email`, `role`, `password`) VALUES ('4', 'Robin', 'Loowert', 'robin_loowert@epam.com', '1', '444444');
INSERT INTO `issuetracker`.`users` (`id`, `firstName`, `lastName`, `email`, `role`, `password`) VALUES ('5', 'Kyle', 'Foster', 'kyle_foster@epam.com', '1', '555555');
INSERT INTO `issuetracker`.`users` (`id`, `firstName`, `lastName`, `email`, `role`, `password`) VALUES ('6', 'Bob', 'Hunington', 'bob_hunington@epam.com', '1', '666666');
INSERT INTO `issuetracker`.`users` (`id`, `firstName`, `lastName`, `email`, `role`, `password`) VALUES ('7', 'Fred', 'Ostins', 'fred_ostins@epam.com', '1', '777777');
INSERT INTO `issuetracker`.`users` (`id`, `firstName`, `lastName`, `email`, `role`, `password`) VALUES ('8', 'Samuele', 'Dopez', 'samuele_dopez@epam.com', '1', '888888');
INSERT INTO `issuetracker`.`users` (`id`, `firstName`, `lastName`, `email`, `role`, `password`) VALUES ('9', 'George', 'Sallivan', 'george_sallivan@epam.com', '1', '999999');
INSERT INTO `issuetracker`.`users` (`id`, `firstName`, `lastName`, `email`, `role`, `password`) VALUES ('10', 'Ruslan', 'Trotsky', 'ruslan_trotsky@epam.com', '1', '101010');

ALTER TABLE issuetracker.users CHANGE COLUMN role role INT NULL DEFAULT NULL;

DROP TABLE IF EXISTS issuetracker.statuses;
CREATE TABLE issuetracker.statuses (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(45) NULL,
	PRIMARY KEY (id)
);

INSERT INTO `issuetracker`.`statuses` (`id`, `name`) VALUES ('1', 'New');
INSERT INTO `issuetracker`.`statuses` (`id`, `name`) VALUES ('2', 'Assigned');
INSERT INTO `issuetracker`.`statuses` (`id`, `name`) VALUES ('3', 'In progress');
INSERT INTO `issuetracker`.`statuses` (`id`, `name`) VALUES ('4', 'Resolved');
INSERT INTO `issuetracker`.`statuses` (`id`, `name`) VALUES ('5', 'Closed');
INSERT INTO `issuetracker`.`statuses` (`id`, `name`) VALUES ('6', 'Reopened');


DROP TABLE IF EXISTS issuetracker.resolutions;
CREATE TABLE issuetracker.resolutions (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(45) NULL,
	PRIMARY KEY (id)
);

INSERT INTO `issuetracker`.`resolutions` (`id`, `name`) VALUES ('1', 'Fixed');
INSERT INTO `issuetracker`.`resolutions` (`id`, `name`) VALUES ('2', 'Invalid');
INSERT INTO `issuetracker`.`resolutions` (`id`, `name`) VALUES ('3', 'Wontfix');
INSERT INTO `issuetracker`.`resolutions` (`id`, `name`) VALUES ('4', 'Worksforme');


DROP TABLE IF EXISTS issuetracker.priorities;
CREATE TABLE issuetracker.priorities (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(45) NULL,
	PRIMARY KEY (id)
);
  
INSERT INTO `issuetracker`.`priorities` (`id`, `name`) VALUES ('1', 'Critical');
INSERT INTO `issuetracker`.`priorities` (`id`, `name`) VALUES ('2', 'Major');
INSERT INTO `issuetracker`.`priorities` (`id`, `name`) VALUES ('3', 'Important');
INSERT INTO `issuetracker`.`priorities` (`id`, `name`) VALUES ('4', 'Minor');


DROP TABLE IF EXISTS issuetracker.types;
CREATE TABLE issuetracker.types (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(45) NULL,
	PRIMARY KEY (id)
);
  
INSERT INTO `issuetracker`.`types` (`id`, `name`) VALUES ('1', 'Cosmetic');
INSERT INTO `issuetracker`.`types` (`id`, `name`) VALUES ('2', 'Bug');
INSERT INTO `issuetracker`.`types` (`id`, `name`) VALUES ('3', 'Feature');
INSERT INTO `issuetracker`.`types` (`id`, `name`) VALUES ('4', 'Performance');

DROP TABLE IF EXISTS issuetracker.roles;
CREATE TABLE issuetracker.roles (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(45) NULL,
	PRIMARY KEY (id),
	UNIQUE INDEX name_UNIQUE (name ASC),
	UNIQUE INDEX id_UNIQUE (id ASC)
);

INSERT INTO `issuetracker`.`roles` (`id`, `name`) VALUES ('1', 'User');
INSERT INTO `issuetracker`.`roles` (`id`, `name`) VALUES ('2', 'Administrator');

DROP TABLE IF EXISTS issuetracker.comments;
CREATE TABLE issuetracker.comments (
  `id` INT NOT NULL AUTO_INCREMENT,
  `issue` INT NULL,
  `user` INT NULL,
  `time` VARCHAR(45) NULL,
  `date` VARCHAR(45) NULL,
  `text` TEXT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idcomments_UNIQUE` (`id` ASC));

INSERT INTO `issuetracker`.`comments` (`id`, `issue`, `user`, `time`, `date`, `text`) VALUES ('1', '4', '1', '10:23', '04.02.2014', 'It seems all right. Try to check once again.');
INSERT INTO `issuetracker`.`comments` (`id`, `issue`, `user`, `time`, `date`, `text`) VALUES ('2', '4', '2', '11:15', '25.02.2014', 'It still doen\'t close. You should try to test it more attentively.');
INSERT INTO `issuetracker`.`comments` (`id`, `issue`, `user`, `time`, `date`, `text`) VALUES ('3', '4', '3', '09:34', '27.02.2014', 'Ok, I see the problem and have already assigned this task to Sergey.');
INSERT INTO `issuetracker`.`comments` (`id`, `issue`, `user`, `time`, `date`, `text`) VALUES ('4', '8', '4', '17:12', '29.02.2014', 'We need to resolve this problem as much quicker!');
INSERT INTO `issuetracker`.`comments` (`id`, `issue`, `user`, `time`, `date`, `text`) VALUES ('5', '8', '5', '15:55', '05.03.2014', 'All\'ve been done. Check it please.');
INSERT INTO `issuetracker`.`comments` (`id`, `issue`, `user`, `time`, `date`, `text`) VALUES ('6', '9', '6', '12:01', '07.03.2014', 'Who can fix it? I don\'t know on which developer to assign.');
INSERT INTO `issuetracker`.`comments` (`id`, `issue`, `user`, `time`, `date`, `text`) VALUES ('7', '9', '7', '13:45', '11.03.2014', 'I can do that, but only in a few days. ');
INSERT INTO `issuetracker`.`comments` (`id`, `issue`, `user`, `time`, `date`, `text`) VALUES ('8', '12', '8', '09:13', '17.03.2014', 'Try to connect with Kate, she is our UI specialist. ');
INSERT INTO `issuetracker`.`comments` (`id`, `issue`, `user`, `time`, `date`, `text`) VALUES ('9', '12', '9', '10:11', '18.04.2014', 'Kate Donnigton?');
INSERT INTO `issuetracker`.`comments` (`id`, `issue`, `user`, `time`, `date`, `text`) VALUES ('10', '12', '10', '16:40', '23.04.2014', 'Yes, I wrote her and we discussed this problem. Connect with her ans she\'ll  explain how it should be done.');

DROP TABLE IF EXISTS issuetracker.attachments;
CREATE TABLE issuetracker.attachments (
  `id` INT NOT NULL AUTO_INCREMENT,
  `issue` INT NULL,
  `user` INT NULL,
  `time` VARCHAR(45) NULL,
  `date` VARCHAR(45) NULL,
  `reference` VARCHAR(255) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));

commit;
