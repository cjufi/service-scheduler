CREATE TABLE IF NOT EXISTS client (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        phone VARCHAR(255) NOT NULL,
                                        address VARCHAR(255) NOT NULL,
                                        account_id BIGINT NOT NULL,
                                        CONSTRAINT fk_client_account
                                        FOREIGN KEY (account_id) REFERENCES account(id)
);