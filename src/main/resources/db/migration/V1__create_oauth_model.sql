CREATE TABLE auth_client (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    client_id varchar(250) NOT NULL UNIQUE,
    secret varchar(250) NOT NULL
);

CREATE TABLE auth_client_authority (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    client_id bigint NOT NULL,
    name varchar(250) NOT NULL,

    FOREIGN KEY (client_id) REFERENCES auth_client(id)
);

CREATE TABLE auth_client_grant_type (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    client_id bigint NOT NULL,
    name varchar(250) NOT NULL,

    FOREIGN KEY (client_id) REFERENCES auth_client(id)
);

CREATE TABLE auth_client_scope (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    client_id bigint NOT NULL,
    name varchar(250) NOT NULL,

    FOREIGN KEY (client_id) REFERENCES auth_client(id)
);

-- INSERT INTO auth_client (id, client_id, secret) VALUES (1, 'test', '$2a$10$ZrbYZbtDkHcNBn1HtnvjCectGjOW1QEvMYr9XWK59bzt5PxDx6pq.');

-- INSERT INTO auth_client_authority (id, client_id, name) VALUES (1, 1, 'ROLE_TRUSTED_CLIENT');
-- INSERT INTO auth_client_authority (id, client_id, name) VALUES (2, 1, 'ROLE_CLIENT');
--
-- INSERT INTO auth_client_grant_type (id, client_id, name) VALUES (1, 1, 'password');
-- INSERT INTO auth_client_grant_type (id, client_id, name) VALUES (2, 1, 'authorization_code');
-- INSERT INTO auth_client_grant_type (id, client_id, name) VALUES (3, 1, 'refresh_token');
-- INSERT INTO auth_client_grant_type (id, client_id, name) VALUES (4, 1, 'implicit');
--
-- INSERT INTO auth_client_scope (id, client_id, name) VALUES (1, 1, 'read');
-- INSERT INTO auth_client_scope (id, client_id, name) VALUES (2, 1, 'write');
-- INSERT INTO auth_client_scope (id, client_id, name) VALUES (3, 1, 'trust');