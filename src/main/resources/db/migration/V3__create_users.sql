CREATE TABLE users (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    tenant_id bigint NOT NULL,
    username varchar(250) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    email varchar(250) NOT NULL UNIQUE,
    enabled boolean NOT NULL DEFAULT TRUE,
    create_date timestamp NOT NULL,

    FOREIGN KEY (tenant_id) REFERENCES tenant(id)
);

CREATE INDEX idx_users_tenant_id ON users (tenant_id);