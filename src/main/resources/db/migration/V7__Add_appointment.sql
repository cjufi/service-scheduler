CREATE TABLE IF NOT EXISTS appointment
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_date  DATETIME   NOT NULL,
    end_date    DATETIME   NOT NULL,
    employee_id BIGINT NOT NULL,
    client_id   BIGINT NOT NULL,
    activity_id BIGINT NOT NULL,
    constraint fk_appointment_employee FOREIGN KEY (employee_id) references employee (id) ON DELETE CASCADE,
    constraint fk_appointment_client FOREIGN KEY (client_id) references client (id) ON DELETE CASCADE,
    constraint fk_appointment_activity FOREIGN KEY (activity_id) references activity (id) ON DELETE CASCADE
);