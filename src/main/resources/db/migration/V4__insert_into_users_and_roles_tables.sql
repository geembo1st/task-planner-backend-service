INSERT INTO users (username, password, email) VALUES ('user', '$2a$12$T1pVayc6etqER9rVmC7j3uOvU3HSfm2pPUsFYe2fj7CBYnALPGm/K', 'user@mail.ru');
INSERT INTO users (username, password, email) VALUES ('admin', '$2a$12$X7cNq1k4bwyxVhs6Y4trzukwjp29xgPtI0S740PKBzJniTCVkrstC', 'admin@mail.ru');

INSERT INTO roles (name, description) VALUES ('ROLE_USER', 'simple user');
INSERT INTO roles (name, description) VALUES ('ROLE_ADMIN', 'user with extra rights');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);