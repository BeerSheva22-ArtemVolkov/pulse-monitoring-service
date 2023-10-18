delete from visits;
delete from doctors_patients;
insert into doctors_patients (id, name, email, dtype) values(100, 'Doctor1', 'd1.100@gmail.com', 'Doctor');
insert into doctors_patients (id, name, email, dtype) values(101, 'Doctor2', 'd2.101@gmail.com', 'Doctor');
insert into doctors_patients (id, name, email, dtype) values(102, 'Doctor3', 'd3.102@gmail.com', 'Doctor');
insert into doctors_patients (id, name, email, dtype) values(103, 'Doctor4', 'd4.103@gmail.com', 'Doctor');
insert into doctors_patients (id, name, email, dtype) values(200, 'Patient1', 'p1.200@gmail.com', 'Patient');
insert into doctors_patients (id, name, email, dtype) values(201, 'Patient2', 'p2.201@gmail.com', 'Patient');
insert into doctors_patients (id, name, email, dtype) values(202, 'Patient3', 'p3.202@gmail.com', 'Patient');
insert into doctors_patients (id, name, email, dtype) values(203, 'Patient4', 'p4.203@gmail.com', 'Patient');
insert into doctors_patients (id, name, email, dtype) values(204, 'Patient5', 'p5.204@gmail.com', 'Patient');
insert into visits (id, doctor_id, patient_id, date) values(1, 100, 200, '2023-02-02');
insert into visits (id, doctor_id, patient_id, date) values(2, 100, 201, '2023-12-12');
insert into visits (id, doctor_id, patient_id, date) values(3, 100, 202, '2023-03-03');
insert into visits (id, doctor_id, patient_id, date) values(4, 101, 200, '2023-03-11');
insert into visits (id, doctor_id, patient_id, date) values(5, 101, 200, '2023-03-10');
insert into visits (id, doctor_id, patient_id, date) values(6, 101, 200, '2023-03-01');
insert into visits (id, doctor_id, patient_id, date) values(7, 102, 201, '2023-11-11');
insert into visits (id, doctor_id, patient_id, date) values(8, 101, 201, '2023-03-04');
insert into visits (id, doctor_id, patient_id, date) values(9, 102, 203, '2023-05-08');
insert into visits (id, doctor_id, patient_id, date) values(10, 103, 203, '2023-02-02');
insert into visits (id, doctor_id, patient_id, date) values(11, 103, 203, '2023-03-03');
insert into visits (id, doctor_id, patient_id, date) values(12, 103, 202, '2023-11-11');