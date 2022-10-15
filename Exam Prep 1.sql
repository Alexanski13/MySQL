CREATE DATABASE exam_prep;

USE exam_prep;

CREATE TABLE countries (
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(45) NOT NULL
);

CREATE TABLE towns (
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(45) NOT NULL,
country_id INT NOT NULL,
CONSTRAINT fk_towns_countries
FOREIGN KEY (country_id)
REFERENCES countries (id)
);

CREATE TABLE stadiums (
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(45) NOT NULL,
capacity INT NOT NULL,
town_id INT NOT NULL,
CONSTRAINT fk_stadiums_towns
FOREIGN KEY (town_id)
REFERENCES towns (id)
);

CREATE TABLE teams (
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(45) NOT NULL,
established DATE NOT NULL,
fan_base BIGINT(20) NOT NULL DEFAULT(0),
stadium_id INT NOT NULL,
CONSTRAINT fk_teams_stadiums
FOREIGN KEY (stadium_id)
REFERENCES stadiums (id)
);

CREATE TABLE skills_data (
id INT PRIMARY KEY AUTO_INCREMENT,
dribbling INT DEFAULT(0),
pace INT DEFAULT(0),
passing INT DEFAULT(0),
shooting INT DEFAULT(0),
speed INT DEFAULT(0),
strength INT DEFAULT(0)
);

CREATE TABLE coaches (
id INT PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(10) NOT NULL,
last_name VARCHAR(20) NOT NULL,
salary DECIMAL(10, 2) NOT NULL DEFAULT(0),
coach_level INT NOT NULL DEFAULT(0)
);

CREATE TABLE players (
id INT PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(10) NOT NULL,
last_name VARCHAR(20) NOT NULL,
age INT NOT NULL DEFAULT(0),
position CHAR(1) NOT NULL,
salary DECIMAL(10, 2) NOT NULL DEFAULT(0),
hire_date DATETIME,
skills_data_id INT NOT NULL,
team_id INT,
CONSTRAINT fk_players_skills_data
FOREIGN KEY (skills_data_id)
REFERENCES skills_data(id),
CONSTRAINT fk_players_teams
FOREIGN KEY (team_id)
REFERENCES teams(id)
);

CREATE TABLE players_coaches (
player_id INT,
coach_id INT,
CONSTRAINT fk_players_coaches_players
FOREIGN KEY (player_id)
REFERENCES players(id),
CONSTRAINT fk_players_coaches_coaches
FOREIGN KEY (coach_id)
REFERENCES coaches(id)
);

INSERT INTO coaches (first_name, last_name, salary, coach_level) 
(
SELECT p.first_name, p.last_name, p.salary, char_length(p.first_name) AS coach_level FROM players AS p
WHERE p.age >= 45
);

UPDATE coaches AS c 
SET 
    c.coach_level = c.coach_level + 1
WHERE
    c.id IN (SELECT 
            coach_id
        FROM
            players_coaches)
        AND first_name LIKE 'A%';
        
DELETE FROM players
WHERE age >= 45;

SELECT first_name, age, salary FROM players ORDER BY salary DESC;

SELECT p.id,  CONCAT_WS(' ', first_name, last_name) AS  full_name, p.age, p.position, p.hire_date FROM players AS p JOIN skills_data AS sd ON p.skills_data_id = sd.id
WHERE p.age < 23 AND
sd.strength > 50 AND
p.hire_date IS NULL AND
p.position = 'A'
ORDER BY p.salary, p.age;

SELECT t.name AS team_name, t.established, t.fan_base, COUNT(p.id) AS players_count FROM teams AS t LEFT JOIN players AS p ON p.team_id = t.id
GROUP BY t.id
ORDER BY players_count DESC, t.fan_base DESC;

SELECT MAX(sd.speed) AS max_speed, tw.name FROM skills_data AS sd
RIGHT JOIN players AS p ON sd.id = p.skills_data_id
RIGHT JOIN teams AS t ON t.id = p.team_id
JOIN stadiums AS s ON s.id = t.stadium_id
RIGHT  JOIN towns AS tw ON tw.id = s.town_id
WHERE t.name != 'Devify'
GROUP BY tw.id
ORDER BY max_speed DESC, tw.name;

SELECT c.name, COUNT(p.id) AS total_count_of_players, SUM(p.salary) AS total_sum_of_salaries FROM countries AS c
LEFT JOIN towns AS tw ON c.id = tw.country_id
LEFT JOIN stadiums AS s ON s.town_id = tw.id
LEFT JOIN teams AS t ON t.stadium_id = s.id
LEFT JOIN players AS p ON p.team_id = t.id
GROUP BY c.id
ORDER BY total_count_of_players DESC, c.name;

DELIMITER $$

CREATE FUNCTION udf_stadium_players_count (stadium_name VARCHAR(30)) 
RETURNS INT
DETERMINISTIC
BEGIN
RETURN(
SELECT COUNT(p.id) FROM players AS p
RIGHT JOIN teams AS t ON t.id = p.team_id
RIGHT JOIN stadiums AS s ON s.id = t.stadium_id
WHERE s.name = stadium_name
GROUP BY s.id);
END$$

CREATE PROCEDURE udp_find_playmaker (min_dribble_points INT, team_name VARCHAR(45))
BEGIN
SELECT CONCAT_WS(' ', p.first_name, p.last_name) AS full_name,
p.age, p.salary, sd.dribbling, sd.speed, t.name AS team_name FROM players AS p
JOIN skills_data AS sd ON p.skills_data_id = sd.id
JOIN teams AS t ON p.team_id = t.id
WHERE sd.dribbling > min_dribble_points
AND t.name = team_name AND
sd.speed > (SELECT AVG(speed)
FROM skills_data)
ORDER BY sd.speed DESC
LIMIT 1;
END$$

CALL udp_find_playmaker (20, 'Skyble');