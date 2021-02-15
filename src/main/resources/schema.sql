
DROP TABLE IF EXISTS `users` CASCADE ;
DROP TABLE IF EXISTS `roles` CASCADE ;
DROP TABLE IF EXISTS `company` CASCADE ;
DROP SEQUENCE IF EXISTS  `user_pk_seq`;
DROP SEQUENCE IF EXISTS  `company_pk_seq`;

CREATE TABLE `company`(
                        company_id NUMBER(8) PRIMARY KEY,
                        name VARCHAR(25)
);


CREATE TABLE  `users`(
                       user_id NUMBER(8) PRIMARY KEY,
                       username VARCHAR(25),
                       password VARCHAR(150),
                       enabled NUMBER(4),
                       first_name VARCHAR(15),
                       last_name VARCHAR(15),
                       role_id INTEGER(2),
                       department VARCHAR(15),
                       company_id NUMBER(8)
);


CREATE TABLE `roles`(
                      role_id NUMBER(8) PRIMARY KEY,
                      name VARCHAR(35)
);


CREATE SEQUENCE `user_pk_seq` MINVALUE 100000 START WITH 100000 INCREMENT BY 10;
CREATE SEQUENCE `company_pk_seq` MINVALUE 103 START WITH 103 INCREMENT BY 1;
