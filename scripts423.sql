SELECT students.name, students.age, faculties.name
FROM students
         LEFT JOIN faculties ON students.faculty_id = faculties.id;

SELECT students.name, students.age, faculties.name, avatars.file_path
FROM avatars
         LEFT JOIN students ON avatars.student_id = students.id
         LEFT JOIN faculties ON students.faculty_id = faculties.id;
