DROP TABLE IF EXISTS doctors;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS usr;

CREATE TABLE doctors (
     id bigint PRIMARY KEY,
     full_name varchar(100),
     specialty varchar(100),
     office_number integer,
     schedule varchar(100)
);

CREATE TABLE patients (
    id bigint PRIMARY KEY,
    registration_number varchar(100),
    full_name varchar(100),
    birth_date varchar(100),
    address varchar(100),
    job varchar(100)
);

CREATE TABLE appointments (
    id bigint PRIMARY KEY,
    registration_number varchar(100),
    full_name varchar(100),
    date varchar(100),
    time varchar(100)
);

CREATE TABLE usr (
    id bigint PRIMARY KEY,
    username varchar(255),
    password varchar(255),
    active boolean,
    roles text,
    authorities text
);

