CREATE TABLE people (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR (100),
                        age INTEGER,
                        drivingLicense BOOLEAN,
                        car_id INTEGER
);

CREATE TABLE cars (
                      id SERIAL PRIMARY KEY,
                      brand TEXT,
                      model TEXT,
                      price NUMERIC,
                      person_id INTEGER REFERENCES people (id)
);

ALTER TABLE people
    ADD CONSTRAINT people_cars_id_fk FOREIGN KEY (car_id)
        REFERENCES cars (id)

