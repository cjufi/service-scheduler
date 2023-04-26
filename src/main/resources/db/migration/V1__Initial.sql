CREATE TABLE IF NOT EXISTS provider (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
                                        website VARCHAR(255) NOT NULL,
                                        business_domain VARCHAR(255) NOT NULL,
                                        phone VARCHAR(255) NOT NULL,
                                        start_time_of_working_day TIME NOT NULL,
                                        end_time_of_working_day TIME NOT NULL,
                                        CONSTRAINT unique_name UNIQUE (name),
                                        CONSTRAINT unique_business_domain UNIQUE (business_domain)
);

CREATE TABLE IF NOT EXISTS working_days (
                                            provider_id BIGINT NOT NULL,
                                            working_day SMALLINT NOT NULL,
                                            CONSTRAINT fk_provider_id
                                                FOREIGN KEY (provider_id)
                                                    REFERENCES provider (id)
                                                    ON DELETE CASCADE
);