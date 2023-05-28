CREATE TABLE IF NOT EXISTS activity
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    price       DOUBLE       NOT NULL,
    duration    BIGINT       NOT NULL,
    provider_id BIGINT       NOT NULL,
    constraint fk_activity_provider
        FOREIGN KEY (provider_id) references provider(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS activity_employees
(
    activity_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    constraint fk_activity_id
        FOREIGN KEY (activity_id) references activity (id) ON DELETE CASCADE,
    constraint fk_employee_id
        FOREIGN KEY (employee_id) references employee(id) ON DELETE CASCADE
);