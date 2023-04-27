CREATE TABLE IF NOT EXISTS employee (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL,
                                    phone VARCHAR(255) NOT NULL,
                                    rate_per_hour DOUBLE NOT NULL,
                                    hire_date DATE NOT NULL
);