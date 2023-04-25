CREATE TABLE IF NOT EXISTS account (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    email VARCHAR(255) NOT NULL UNIQUE,
                                    full_name VARCHAR(255) NOT NULL,
                                    password VARCHAR(255) NOT NULL,
                                    role_id BIGINT NOT NULL,
                                    CONSTRAINT fk_account_role FOREIGN KEY (role_id) REFERENCES role(id)
);