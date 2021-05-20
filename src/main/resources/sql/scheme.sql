DROP TABLE IF EXISTS doctors;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS appointments;

CREATE TABLE doctors (
    id bigserial,
    full_name varchar(100),
    specialty varchar(100),
    office_number integer,
    schedule varchar(100)
);

CREATE TABLE patients (
    id bigserial,
    registration_number varchar(100),
    full_name varchar(100),
    birth_date varchar(100),
    address varchar(100),
    job varchar(100)
);

CREATE TABLE appointments (
    id bigserial,
    registration_number varchar(100),
    full_name varchar(100),
    date varchar(100),
    time varchar(100)
);
