DELIMITER $$
CREATE FUNCTION ufn_count_employees_by_town (town_name VARCHAR(50))
RETURNS INT 
DETERMINISTIC
BEGIN
DECLARE id_for_town INT;
DECLARE count_by_town INT;

SET id_for_town := (SELECT town_id FROM towns WHERE name = town_name); 

SET count_by_town := (SELECT COUNT(*) FROM employees AS e
				WHERE e.address_id IN (
				SELECT address_id FROM addresses WHERE town_id = id_for_town
	)
);
RETURN count_by_town;
END$$

SELECT ufn_count_employees_by_town('Sofia') AS count$$

CREATE PROCEDURE usp_raise_salaries(department_name VARCHAR(50))
BEGIN
UPDATE employees SET salary = salary * 1.05
WHERE department_id = (SELECT department_id FROM departments WHERE name = department_name);
END$$

CALL usp_raise_salaries('Engineering')$$

CREATE PROCEDURE usp_raise_salary_by_id (id INT)
BEGIN
DECLARE count_by_id INT;
START TRANSACTION;

SET count_by_id := (SELECT COUNT(*) FROM employees WHERE employee_id = id);

UPDATE employees SET salary = salary * 1.05 WHERE employee_id = id;

IF (count_by_id < 1) THEN
ROLLBACK;
ELSE
COMMIT;
END IF;
END$$

CREATE TABLE deleted_employees(
employee_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
first_name varchar(50) NOT NULL,
last_name varchar(50) NOT NULL,
middle_name varchar(50) DEFAULT NULL,
job_title varchar(50) NOT NULL,
department_id int NOT NULL,
salary decimal(19, 4) NOT NULL
)$$

CREATE TRIGGER tr_deleted_employees
AFTER DELETE
ON employees
	FOR EACH ROW
    BEGIN
    INSERT INTO deleted_employees (first_name, last_name, middle_name, job_title, department_id, salary)
    VALUES(OLD.first_name, OLD.last_name, OLD.middle_name, OLD.job_title, OLD.department_id, OLD.salary);
    END$$
    
SELECT * FROM employees AS e WHERE (SELECT * FROM employees_projects AS ep WHERE ep.employee_id = e.employee_id) = 0$$