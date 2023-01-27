CREATE TABLE users (
userID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
username varchar (30) NOT NULL,
password varchar (30) NOT NULL,
FullName varchar (100) NOT NULL,
DateOfBirth varchar (10) NOT NULL
);

INSERT INTO users VALUES (1, 'ken123', 'benson','Ken Benson','1988-06-01' );

SELECT * FROM users;

CREATE TABLE doctors(
DoctorsID int NOT NULL PRIMARY KEY,
DoctorsFullName varchar (100) NOT NULL
);

INSERT INTO doctors VALUES (1, 'Sid Newman');
INSERT INTO doctors VALUES (2, 'Peter Smith');
INSERT INTO doctors VALUES (3, 'Nelson Kan');
INSERT INTO doctors VALUES (4, 'Rita Ora');

SELECT * FROM doctors;

CREATE TABLE Appointments(
doctor int NOT NULL REFERENCES doctors(DoctorsID),
Date date,
9_AM varchar(100),
10_AM varchar(100),
2_PM varchar(100),
3_PM varchar(100),
4_PM varchar(100),
PRIMARY KEY(doctor, date)
);

INSERT INTO Appointments(doctor,Date,10_AM) VALUES (2, '2023-02-03','1', );

DROP TABLE appointments;

SELECT * FROM Appointments;