CREATE TABLE tenant (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar(250) NOT NULL UNIQUE,
    create_date timestamp NOT NULL
);