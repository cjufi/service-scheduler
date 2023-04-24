CREATE TABLE IF NOT EXISTS role (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL,
                                    CONSTRAINT unique_name UNIQUE (name)
);

INSERT INTO role (name) VALUES ('ADMIN');
INSERT INTO role (name) VALUES ('PROVIDER_ADMIN');
INSERT INTO role (name) VALUES ('EMPLOYEE');
INSERT INTO role (name) VALUES ('CLIENT');