ALTER TABLE employee
ADD COLUMN account_id BIGINT NOT NULL;

ALTER TABLE employee
ADD CONSTRAINT fk_employee_account
FOREIGN KEY (account_id) REFERENCES account(id);