CREATE TABLE IF NOT EXISTS appointment
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_date  DATE   NOT NULL,
    end_date    DATE   NOT NULL,
    employee_id BIGINT NOT NULL,
    client_id   BIGINT NOT NULL,
    activity_id BIGINT NOT NULL,
    constraint fk_appointment_employee FOREIGN KEY (employee_id) references employee (id),
    constraint fk_appointment_client FOREIGN KEY (client_id) references client (id),
    constraint fk_appointment_activity FOREIGN KEY (activity_id) references activity (id)
);