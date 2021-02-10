
DROP TABLE USERS CASCADE ;
DROP TABLE ROLES CASCADE ;
DROP TABLE COMPANY CASCADE ;
DROP SEQUENCE user_pk_seq;

CREATE TABLE COMPANY(
    company_id NUMBER(8) PRIMARY KEY,
    name VARCHAR(25)
);


CREATE TABLE  USERS(
    user_id NUMBER(8) PRIMARY KEY,
    username VARCHAR(15),
    password VARCHAR(150),
    enabled NUMBER(4),
    first_name VARCHAR(15),
    last_name VARCHAR(15),
    role_id INTEGER(2),
    department VARCHAR(15),
    company_id NUMBER(8)
    );


CREATE TABLE ROLES(
    role_id NUMBER(8) PRIMARY KEY,
    name VARCHAR(35)
);

INSERT INTO ROLES VALUES (1, 'ROLE_SYSTEM_MANAGER');
INSERT INTO ROLES VALUES (2, 'ROLE_MANAGER');
INSERT INTO ROLES VALUES (3, 'ROLE_EMPLOYEE');

INSERT INTO COMPANY VALUES(111111, 'DELTA SMART TECH.');

INSERT INTO USERS(user_id, username, password,enabled, first_name, last_name, role_id, department, company_id) VALUES
    (123123, 'emirhanoguz', '$2y$10$GS9RE/ogpwUynaGLPvHiwubdLx0/EBsgnzn1ijQdK6F7InSUv6zBy', 1,  'Emirhan', 'Oğuz', 1, 'IT', 111111);



CREATE SEQUENCE user_pk_seq MINVALUE 100000 START WITH 100000 INCREMENT BY 10;



