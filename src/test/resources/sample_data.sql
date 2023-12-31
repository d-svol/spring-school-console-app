INSERT INTO groups (group_name)
VALUES ('AA-01'), ('BB-02'), ('CC-03');

-- Insert sample data into the 'students' table
INSERT INTO students (group_id, first_name, last_name)
VALUES (1, 'NameA', 'LastnameA'),
	   (1, 'NameB', 'LastnameB'),
	   (1, 'NameC', 'LastnameC'),
	   (2, 'NameD', 'LastnameD'),
	   (3, 'NameE', 'LastnameE');

-- Insert sample data into the 'courses' table
INSERT INTO courses (course_name, course_description)
VALUES ('Math', 'Basic math skills'),
	   ('History', 'World history overview'),
	   ('English', 'Language and literature');

INSERT INTO student_course (student_id, course_id)
VALUES (3, 3);