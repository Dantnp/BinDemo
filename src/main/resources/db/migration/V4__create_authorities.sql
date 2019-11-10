CREATE TABLE authorities (
    id bigint PRIMARY KEY AUTO_INCREMENT,
    user_id bigint NOT NULL,
    username varchar(250) NOT NULL UNIQUE,
    authority varchar(50) NOT NULL,

    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE INDEX idx_authorities_username ON authorities(username);
CREATE INDEX idx_authorities_user_id ON authorities(user_id);