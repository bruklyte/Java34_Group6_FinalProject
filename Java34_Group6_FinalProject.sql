CREATE TABLE users (
userID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
username varchar (30) NOT NULL,
password varchar (30) NOT NULL,
FullName varchar (100) NOT NULL,
DateOfBirth varchar (10) NOT NULL
);


CREATE TABLE doctors(
DoctorsID int NOT NULL PRIMARY KEY,
DoctorsFullName varchar (100) NOT NULL
);

INSERT INTO doctors VALUES (1, 'Sid Newman');
INSERT INTO doctors VALUES (2, 'Peter Smith');
INSERT INTO doctors VALUES (3, 'Nelson Kan');


CREATE TABLE appointments(
doctor int NOT NULL  REFERENCES doctors(DoctorsID),
Date varchar (10) PRIMARY KEY,
9_AM varchar(100),
10_AM varchar(100),
2_PM varchar(100),
3_PM varchar(100),
4_PM varchar(100)
);