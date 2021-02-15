

INSERT INTO ROLES(role_id, name) VALUES (1, 'ROLE_SYSTEM_MANAGER');
INSERT INTO ROLES(role_id, name) VALUES (2, 'ROLE_MANAGER');
INSERT INTO ROLES(role_id, name) VALUES (3, 'ROLE_EMPLOYEE');

INSERT INTO COMPANY(company_id, name) VALUES(100, 'DELTA');
INSERT INTO COMPANY(company_id, name) VALUES(101, 'Google');
INSERT INTO COMPANY(company_id, name) VALUES(102, 'Apple');



INSERT INTO USERS(user_id, username, password,enabled, first_name, last_name, role_id, department, company_id) VALUES
    (123123, 'emirhanoguz', '$2y$10$GS9RE/ogpwUynaGLPvHiwubdLx0/EBsgnzn1ijQdK6F7InSUv6zBy', 1,  'Emirhan', 'Oğuz', 1, 'IT', 100);




INSERT INTO USERS(user_id, username, password,enabled, first_name, last_name, role_id, department, company_id) VALUES
(123456, 'testManager1', '$2y$10$GS9RE/ogpwUynaGLPvHiwubdLx0/EBsgnzn1ijQdK6F7InSUv6zBy', 1,  'TestManager1', 'Test1', 2, 'IT', 100);

INSERT INTO USERS(user_id, username, password,enabled, first_name, last_name, role_id, department, company_id) VALUES
(123457, 'testManager2', '$2y$10$GS9RE/ogpwUynaGLPvHiwubdLx0/EBsgnzn1ijQdK6F7InSUv6zBy', 1,  'TestManager2', 'Test2', 2, 'HR', 100);



INSERT INTO USERS(user_id, username, password,enabled, first_name, last_name, role_id, department, company_id) VALUES
(123458, 'testEmployee1', '$2y$10$GS9RE/ogpwUynaGLPvHiwubdLx0/EBsgnzn1ijQdK6F7InSUv6zBy', 1,  'TestEmployee1', 'Test3', 3, 'IT', 100);

INSERT INTO USERS(user_id, username, password,enabled, first_name, last_name, role_id, department, company_id) VALUES
(123459, 'testEmployee2', '$2y$10$GS9RE/ogpwUynaGLPvHiwubdLx0/EBsgnzn1ijQdK6F7InSUv6zBy', 1,  'TestEmployee2', 'Test4', 3, 'IT', 100);

INSERT INTO USERS(user_id, username, password,enabled, first_name, last_name, role_id, department, company_id) VALUES
(123460, 'differentEmployee', '$2y$10$GS9RE/ogpwUynaGLPvHiwubdLx0/EBsgnzn1ijQdK6F7InSUv6zBy', 1,  'DiffEmployee', 'Different', 3, 'HR', 100);



