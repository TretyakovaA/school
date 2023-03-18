-- liquibase formatted sql

-- changeset nastya:1
CREATE INDEX student_name_index ON students (name);

-- changeset nastya:2
CREATE INDEX faculty_name_color_index ON faculties (name, color);