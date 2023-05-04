ALTER TABLE employee
ADD COLUMN provider_id BIGINT NOT NULL;

ALTER TABLE employee
ADD CONSTRAINT fk_employee_provider
FOREIGN KEY (provider_id) REFERENCES provider(id);