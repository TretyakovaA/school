insert into students (id, age, name) values (17, 10, 'Ира') ON CONFLICT DO NOTHING;
insert into students (id, age, name) values (18, 15, 'Настя') ON CONFLICT DO NOTHING;
insert into students (id, age, name) values (19, 18, 'Вася') ON CONFLICT DO NOTHING;
insert into students (id, age, name) values (20, 25, 'Маша') ON CONFLICT DO NOTHING;
insert into students (id, age, name) values (21, 20, 'Даша') ON CONFLICT DO NOTHING;
insert into students (id, age, name) values (22, 35, 'Костя')ON CONFLICT DO NOTHING;


/*select * from students;
    */
SELECT COUNT(*) FROM students;
SELECT AVG(age) from students;

SELECT * FROM students ORDER BY id DESC limit 5;

