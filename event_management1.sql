create database event_management1;

USE event_management1;

CREATE TABLE IF NOT EXISTS Event (event_id INT PRIMARY KEY,event_name VARCHAR(255) NOT NULL,event_date DATE NOT NULL,event_location VARCHAR(255) NOT NULL,UNIQUE (event_name, event_date)  );

ALTER TABLE Session ADD COLUMN session_title VARCHAR(255) NOT NULL;

SHOW TABLES LIKE 'Speaker';

DESCRIBE Speaker;

CREATE TABLE IF NOT EXISTS Session (session_id INT PRIMARY KEY,session_title VARCHAR(255) NOT NULL,start_time TIME NOT NULL,end_time TIME NOT NULL,speaker_id INT,
    event_id INT,FOREIGN KEY (speaker_id) REFERENCES Speaker(speaker_id),FOREIGN KEY (event_id) REFERENCES Event(event_id),UNIQUE (event_id, session_title));
    
CREATE TABLE IF NOT EXISTS Speaker (speaker_id INT AUTO_INCREMENT PRIMARY KEY,speaker_name VARCHAR(255) NOT NULL,speaker_bio TEXT,UNIQUE (speaker_name));

CREATE TABLE IF NOT EXISTS Participant (participant_id INT PRIMARY KEY,participant_name VARCHAR(255) NOT NULL,participant_email VARCHAR(255) UNIQUE NOT NULL);

CREATE TABLE IF NOT EXISTS Feedback (feedback_id INT PRIMARY KEY,rating INT CHECK (rating BETWEEN 1 AND 5),comments TEXT,participant_id INT,session_id INT,feedback_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (participant_id) REFERENCES Participant(participant_id),FOREIGN KEY (session_id) REFERENCES Session(session_id),UNIQUE (participant_id, session_id) );
    
CREATE TABLE IF NOT EXISTS AnalyticsReport (report_id INT PRIMARY KEY,event_id INT,average_rating DECIMAL(3, 2),common_feedback_topics TEXT,report_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (event_id) REFERENCES Event(event_id));
    
CREATE TABLE IF NOT EXISTS FeedbackLog (log_id SERIAL PRIMARY KEY,feedback_id INT,participant_id INT,session_id INT,submission_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,action VARCHAR(50),
    FOREIGN KEY (feedback_id) REFERENCES Feedback(feedback_id));
    
SELECT e.event_name,s.session_title,AVG(f.rating) AS average_rating
FROM Feedback f
JOIN Session s ON f.session_id = s.session_id JOIN Event e ON s.event_id = e.event_id GROUP BY e.event_name, s.session_title;

CREATE VIEW avg_event_ratings AS
SELECT e.event_name,AVG(f.rating) AS average_rating
FROM Feedback f JOIN     Session s ON f.session_id = s.session_id JOIN Event e ON s.event_id = e.event_id
GROUP BY e.event_name;

CREATE VIEW common_feedback_topics AS
SELECT e.event_name,GROUP_CONCAT(SUBSTRING(f.comments, 1, 100) SEPARATOR ', ') AS feedback_snippets
FROM Feedback f
JOIN Session s ON f.session_id = s.session_id JOIN Event e ON s.event_id = e.event_id
GROUP BY e.event_name;

SHOW TABLES;

DESCRIBE Event;

DESCRIBE Session;

SELECT * FROM Event;

SELECT * FROM Session;

SHOW CREATE TABLE Event;

SHOW INDEX FROM Session;
