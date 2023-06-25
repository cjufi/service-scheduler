CREATE TABLE IF NOT EXISTS appointment
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_date  DATETIME   NOT NULL,
    end_date    DATETIME   NOT NULL,
    employee_id BIGINT NOT NULL,
    client_id   BIGINT NOT NULL,
    constraint fk_appointment_employee FOREIGN KEY (employee_id) references employee (id),
    constraint fk_appointment_client FOREIGN KEY (client_id) references client (id)
);

CREATE TABLE IF NOT EXISTS appointment_activities
(
    appointment_id BIGINT NOT NULL,
    activity_id BIGINT NOT NULL,
    constraint fk_appointment_id
        FOREIGN KEY (appointment_id) references appointment (id) ON DELETE CASCADE,
    constraint fk_activity_appointment_id
        FOREIGN KEY (activity_id) references activity(id) ON DELETE CASCADE
);