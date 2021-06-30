-- urmatoarele modificari la baza de date vor fi
-- efectuate la pornirea aplicatiei
-- aici vom insera in baza de date informatiile initiale
-- deoarece lucram cu o baza de date in memorie
-- si avem nevoie in special de tabelul cu useri
-- adaugam, de asemenea, si constragerile referitoare
-- la cheile straine

-- lucrand cu o baza de date in memorie, backup poate fi considerat
-- acest fisier
ALTER TABLE angajati ADD
  CONSTRAINT fk1
    FOREIGN KEY (user_id)
    REFERENCES USER_CREDENTIALS(id_user)
    ON DELETE CASCADE;

ALTER TABLE echipa ADD
  CONSTRAINT fk11
    FOREIGN KEY (id_firma)
    REFERENCES FIRMA (id_firma);

ALTER TABLE echipa ADD
  CONSTRAINT fk12
    FOREIGN KEY (id_team_leader)
    REFERENCES ANGAJATI (id_ang);

ALTER TABLE angajat_echipa ADD
  CONSTRAINT fk13
    FOREIGN KEY (id_ang)
    REFERENCES ANGAJATI (id_ang);

-- ALTER TABLE angajati ADD
--   CONSTRAINT fk10
--     FOREIGN KEY (id_firma)
--     REFERENCES FIRMA (id_firma);

ALTER TABLE alterari_timp_lucru ADD
  CONSTRAINT fk20
    FOREIGN KEY (id_ang)
    REFERENCES ANGAJATI (id_ang);

ALTER TABLE concediu ADD
  CONSTRAINT fk5
    FOREIGN KEY (id_ang)
    REFERENCES ANGAJATI (id_ang);

ALTER TABLE pontaj ADD
  CONSTRAINT fk16
    FOREIGN KEY (id_ang)
    REFERENCES ANGAJATI (id_ang);

INSERT INTO USER_CREDENTIALS (id_user, username, password) VALUES
('820e7f90-4d0c-481f-ae13-db6874f84173', 'marian', '$2a$10$/YPDhrEQF8wXeJemffI7muvpQ4TYQDhCI00DKc95/2bfZSbBx6slK'),-- username "marian" , password "marian"
('7e882609-a2b9-4876-b241-02095b204781', 'stefan', '$2a$10$CVxaJweLpGf1sGvNfZcEkeV0YZ.qYE7xwzFgTRE/UqB7ufWE2Jaze'),-- username "stefan" , password "stefan"
('3cf14d7c-9472-4aae-a94e-34852c5c7a93', 'cristian', '$2a$10$aiogJvDSU8dKMoHbNSyImO4IOORXLA37gJs7kLGEesUzgdtk2mBWK'),-- username "cristian" , password "cristian"
('51015983-fea8-45be-8806-57c57162fd2c', 'alexandru', '$2a$10$7yhKDr72gVp7PD634CU/Peu9PQ4.NCQ.47MKyrY8rs3SeYl4bqodi'),-- username "alexandru" , password "alexandru"
('c5627725-0305-4f18-858c-299daddba2f1', 'admin', '$2a$10$ffQHm.m826UVSueQI5/s1upzjmjX3/pw0sJmcMcHo7ayJ5GyxS/fS');-- username "admin" , password "admin"

INSERT INTO Angajati (id_ang, nume, prenume, cnp, functie, strada, numar, localitate, judet, tara, stare, user_id, id_firma, email, data_nasterii, serie, nr, is_manager, salariu, norma_de_lucru, data_angajarii) VALUES
('dd60294b-6f21-4449-8796-646ce0c5a98a', 'Anca', 'Marian', '1234567891234', 'Asistent Manager', 'Sperantei', '15', 'Bucuresti', 'Bucuresti', 'Romania', 1, '820e7f90-4d0c-481f-ae13-db6874f84173', '0aef44ec-40b8-44d8-afa5-de506e4ab78a', 'test@gmail.com', '1995-10-02', 'XZ', '1234','0', '3000', '8','2018-02-15'),
('cbe39faf-7a0b-4479-a03f-a6467ed59af2', 'Anca', 'Stefan', '1233345678912', 'Manager', 'Fericirii', '11', 'Bucuresti', 'Bucuresti', 'Romania', 1, '7e882609-a2b9-4876-b241-02095b204781', '0aef44ec-40b8-44d8-afa5-de506e4ab78a', 'test1@gmail.com', '1982-12-01', 'XZ', '1234','1', '5000', '8','2017-02-10'),
('c7bcfe6a-9c10-4a06-9de3-03aef3b366bf', 'Tanase', 'Cristian', '1212456789423', 'Tehnician', 'Victoriei', '10', 'Bucuresti', 'Bucuresti', 'Romania', 1, '3cf14d7c-9472-4aae-a94e-34852c5c7a93', '0aef44ec-40b8-44d8-afa5-de506e4ab78a', 'test2@gmail.com', '1990-10-15', 'XZ', '1234','0', '1500', '4','2020-01-01'),
('8a61a513-7b1d-4705-9328-266ef6f1a6c4', 'Ion', 'Alexandru', '1457892450456', 'Asistent Manager', 'Sperantei', '15', 'Bucuresti', 'Bucuresti', 'Romania', 1, '51015983-fea8-45be-8806-57c57162fd2c', '956433b7-4110-4331-b258-d6799679b7aa', 'test3@gmail.com', '1991-05-02', 'XZ', '1234','0', '2500', '8','2019-02-15'),
('1b170bc3-b739-41d6-bbdf-81b1beac7853', 'Tudor', 'Elena', '2568947892456', 'Manager', 'Sperantei', '15', 'Bucuresti', 'Bucuresti', 'Romania', 1, 'c5627725-0305-4f18-858c-299daddba2f1', '9caedaa9-d89a-495f-a35d-e27aedb5a0b6', 'test4@gmail.com', '2000-01-29', 'XZ', '1234','0', '5000', '8','2017-02-02');

INSERT INTO firma (id_firma, nume_firma, cui, cif, adresa_sediu_social) VALUES
('0aef44ec-40b8-44d8-afa5-de506e4ab78a', 'Optimus', '123466798965', '65656565', 'Buzau'),
('956433b7-4110-4331-b258-d6799679b7aa', 'Plusivo', '5454545','56565565','Bucuresti nr 5656'),
('9caedaa9-d89a-495f-a35d-e27aedb5a0b6', 'OD', '54545454','56565655', 'craiova str uitarii');


INSERT INTO punct_de_lucru (id_punct, adresa, nume, id_firma, ora_inceput_program, ora_sfarsit_program, ora_minima_recuperare, ora_maxima_recuperare) VALUES
('6b01837c-03d7-4248-bf93-0d8a5e5c6d5ca', 'Buzau, Bulevardul Unirii nr. 5', 'Punct Buzau', '0aef44ec-40b8-44d8-afa5-de506e4ab78a', '10:00:00', '19:00:00','08:00:00','21:00:00'),
('f109aa41-ffd5-45c8-8532-bcbffb001c11', 'Bucuresti, Splaiul Unirii', 'Punct Bucuresti','0aef44ec-40b8-44d8-afa5-de506e4ab78a','10:00:00', '19:00:00','08:00:00','21:00:00'),
('b63eed0d-2825-4eae-86df-eb6e3632057e', 'Craiova centru', 'Punct Craiova','9caedaa9-d89a-495f-a35d-e27aedb5a0b6', '10:00:00', '19:00:00','08:00:00','21:00:00'),
('bc8309d3-74d3-45c9-b083-5d9031af56fb', 'Craiova centru2', 'Punct Craiova 2','956433b7-4110-4331-b258-d6799679b7aa', '10:00:00', '19:00:00','08:00:00','21:00:00'),
('250e5850-7c85-4648-adf5-54c89a8d648d', 'Bucurestii Noi', 'Punct Bucurestii Noi','9caedaa9-d89a-495f-a35d-e27aedb5a0b6', '10:00:00', '19:00:00','08:00:00','21:00:00');

INSERT INTO echipa (id_echipa, nume, id_firma, id_team_leader) VALUES
('5adaad8a-6aad-4813-85f2-7e5eba55e19f', 'asistenti', '0aef44ec-40b8-44d8-afa5-de506e4ab78a', 'dd60294b-6f21-4449-8796-646ce0c5a98a'),
('6eea07d1-4771-4ab2-a6f5-ec44af235838', 'tehnica', '956433b7-4110-4331-b258-d6799679b7aa','dd60294b-6f21-4449-8796-646ce0c5a98a'),
('b8f0d5c1-6fd8-49fe-82f0-97bfbe7b4bdc', 'manageri', '0aef44ec-40b8-44d8-afa5-de506e4ab78a', 'c7bcfe6a-9c10-4a06-9de3-03aef3b366bf');

INSERT INTO angajat_echipa (id, id_ang, id_echipa) VALUES
('0c924db3-8561-4ad7-987f-f341e82b8acf', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '6eea07d1-4771-4ab2-a6f5-ec44af235838'),
('ba153220-73c3-4f2a-9790-3ee58bf6eee4', 'dd60294b-6f21-4449-8796-646ce0c5a98a', 'b8f0d5c1-6fd8-49fe-82f0-97bfbe7b4bdc'),
('cc1aab4d-cb97-407a-b9ee-bd8960551d7f', 'cbe39faf-7a0b-4479-a03f-a6467ed59af2', '5adaad8a-6aad-4813-85f2-7e5eba55e19f'),
('f5b560c0-5cc1-43c9-9b1b-f60bfc6842f3', 'c7bcfe6a-9c10-4a06-9de3-03aef3b366bf', 'b8f0d5c1-6fd8-49fe-82f0-97bfbe7b4bdc');

INSERT INTO alterari_timp_lucru (id_inreg, id_ang, start, sfarsit, data, tip_alterare) VALUES
('e951c949-cb14-4b46-9ac3-28734d4b78c5', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '10:00:00', '12:00:00', '2019-12-01', 'invoire'),
('b10bcf33-59d9-4e17-a55c-ba47ce2d9465', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '10:00:00', '12:00:00', '2019-12-02', 'recuperare'),
('80536519-690f-4ec2-92d4-be7fef63d0d0', 'c7bcfe6a-9c10-4a06-9de3-03aef3b366bf', '10:00:00', '12:00:00', '2019-12-02', 'invoire'),
('949a149e-13bb-4df9-b711-fe94aee0f94d', 'c7bcfe6a-9c10-4a06-9de3-03aef3b366bf', '10:00:00', '12:00:00', '2019-12-07', 'recuperare'),
('6858ee33-73d1-4bfd-ba6c-357a6b69154c', 'cbe39faf-7a0b-4479-a03f-a6467ed59af2', '12:00:00', '14:00:00', '2019-12-03', 'invoire'),
('2d83576b-3e9a-4de1-8adb-09aa57ff74ba', 'cbe39faf-7a0b-4479-a03f-a6467ed59af2', '12:00:00', '14:00:00', '2019-12-08', 'recuperare'),
('6e4d91a8-fe98-42a3-a6f4-96274855ea03', 'c7bcfe6a-9c10-4a06-9de3-03aef3b366bf', '12:00:00', '14:00:00', '2019-12-04', 'invoire'),
('124cbfdc-f8d9-4a23-85ff-cf86e39b92b6', 'c7bcfe6a-9c10-4a06-9de3-03aef3b366bf', '12:00:00', '14:00:00', '2019-12-07', 'recuperare'),
('94ab9b4d-620b-4d17-8a49-f94054a2ffb9', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '12:00:00', '14:00:00', '2019-12-05', 'invoire'),
('1664cb03-3d5f-4cb8-aeff-00654abcd997', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '12:00:00', '14:00:00', '2019-12-15', 'recuperare'),
('d7460829-7b7c-4aab-8722-d87b8f137a94', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '12:00:00', '14:00:00', '2019-12-08', 'invoire'),
('87de5c05-05b6-48a5-8a93-cfbe36d3c47b', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '12:00:00', '14:00:00', '2019-12-09', 'recuperare'),
('9143895d-1c05-474e-9cd1-3640a2330424', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '12:00:00', '14:00:00', '2019-12-09', 'invoire'),
('db294a2b-7c9a-4dfe-9e11-75b0f7a4f5f7', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '12:00:00', '14:00:00', '2019-12-17', 'recuperare'),
('6e60be40-f50c-4675-abdf-40e88b323bd4', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '10:00:00', '12:00:00', '2019-12-20', 'invoire'),
('cf84d8b9-e203-4ba7-bbd2-17ee743bd927', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '10:00:00', '12:00:00', '2019-12-15', 'recuperare'),
('5a518b79-976f-404e-aa5f-bde1b3c34f67', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '12:00:00', '14:00:00', '2019-12-17', 'invoire'),
('a1b617be-7d08-4d4b-bce0-4d6ddd3110ad', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '12:00:00', '14:00:00', '2019-12-30', 'recuperare'),
('312e1fee-ccb7-4c6a-a4e3-117d82801043', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '11:00:00', '14:00:00', '2020-01-03', 'invoire'),
('f6cf2a15-5f13-4cea-b70b-fb2024b1aabc', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '11:00:00', '14:00:00', '2020-01-05', 'recuperare'),
('8f00d6bb-20c5-4ea7-a83f-0cf785b75aff', 'cbe39faf-7a0b-4479-a03f-a6467ed59af2', '12:00:00', '13:00:00', '2020-01-07', 'invoire'),
('785064d4-49cd-421f-bc6e-4b80ebddf9f5', 'cbe39faf-7a0b-4479-a03f-a6467ed59af2', '11:00:00', '12:00:00', '2020-01-12', 'recuperare'),
('a3e8e991-aa18-4278-9ebb-135e80f24000', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '11:00:00', '12:00:00', '2020-01-04', 'invoire'),
('3297f120-7d3e-4359-89ce-359961028277', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '12:00:00', '13:00:00', '2020-01-03', 'recuperare'),
('d6511363-c7df-4378-8d2e-5557bb4be2cc', 'cbe39faf-7a0b-4479-a03f-a6467ed59af2', '10:00:00', '12:00:00', '2020-01-17', 'invoire'),
('25536a4d-f4da-41e1-8dfa-2525c12ee938', 'cbe39faf-7a0b-4479-a03f-a6467ed59af2', '10:00:00', '12:00:00', '2020-01-12', 'recuperare'),
('6816b170-4e21-4300-ae3d-c472a5507228', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '11:00:00', '12:00:00', '2020-01-10', 'invoire'),
('df5ad8c2-75d9-4a01-934e-12074fc8f1ca', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '11:00:00', '12:00:00', '2020-01-11', 'recuperare'),
('1dec0b37-aa2e-421d-9c41-7745f9daf013', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '11:00:00', '12:00:00', '2020-01-17', 'invoire'),
('868804ef-5049-4fe9-a514-0e83c9602b84', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '11:00:00', '12:00:00', '2020-01-09', 'recuperare');

INSERT INTO concediu (id_inreg, id_ang, start, sfarsit, tip_alterare, numar_zile) VALUES
('103fdc57-5141-426d-bc70-af31dc877ba2', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '2019-12-01', '2019-12-08', 'cu plata', '7'),
('e1fec6d5-97ee-4e92-9a54-0b2416663de2', 'cbe39faf-7a0b-4479-a03f-a6467ed59af2', '2019-12-01', '2019-12-04', 'cu plata', '3'),
('7ef77b60-4685-47d2-b85e-f860b56ee8bc', 'cbe39faf-7a0b-4479-a03f-a6467ed59af2', '2019-12-04', '2019-12-05', 'fara plata', '2'),
('5f92b910-f038-4b25-9eac-b207f5006b15', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '2019-12-07', '2019-12-16', 'fara plata', '9'),
('3021599a-954e-4998-ad74-fef0971860b3', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '2019-12-19', '2019-12-19', 'cu plata', '1'),
('268a5b10-dd44-48fb-804c-997d15f016d5', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '2019-12-24', '2019-12-30', 'fara plata', '7'),
('7f37a440-dae2-42a4-b01f-c742b49b2b90', 'c7bcfe6a-9c10-4a06-9de3-03aef3b366bf', '2019-12-01', '2019-12-09', 'fara plata', '9'),
('c0be9e83-eda6-4a21-9de5-113d11c313b2', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '2019-12-27', '2019-12-29', 'cu plata', '3'),
('26e88093-aeda-4a56-8340-c49030019ec2', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '2020-01-03', '2020-01-04', 'fara plata', '2'),
('53c0628f-3f11-4683-a3c6-f8682d929862', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '2020-01-03', '2020-01-03', 'cu plata', '1');

INSERT INTO pontaj (id_pontaj, id_ang, ora_start, ora_end, data) VALUES
('687e40b7-7bbb-4f7b-8950-0b409c4aa4ff', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '08:00:00', '17:00:00', '2019-12-01'),
('3af3d9d4-7a0f-4c30-9725-34184d3278ce', 'cbe39faf-7a0b-4479-a03f-a6467ed59af2', '08:00:00', '19:00:00', '2019-12-01'),
('5749b729-3db6-411e-8e70-870496529dbf', 'c7bcfe6a-9c10-4a06-9de3-03aef3b366bf', '08:00:00', '17:00:00', '2019-12-01'),
('98127560-6ec9-4f5c-9924-b32260569488', '8a61a513-7b1d-4705-9328-266ef6f1a6c4', '11:00:00', '17:00:00', '2019-12-01'),
('bb3b5a74-f60f-49fd-a6e5-35a5c3254a70', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '08:00:00', '11:00:00', '2019-12-01'),
('dc881bda-f055-4bfc-bfab-9d097d83e848', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '08:00:00', '17:00:00', '2019-12-02'),
('84435150-f749-4a51-b756-d70f33a4c667', 'cbe39faf-7a0b-4479-a03f-a6467ed59af2', '08:00:00', '15:00:00', '2019-12-02'),
('345b4be3-0d94-4885-9309-6d1a4a5fc42f', 'c7bcfe6a-9c10-4a06-9de3-03aef3b366bf', '10:00:00', '17:00:00', '2019-12-02'),
('81c46b06-7239-48d9-8046-1b5fbfeb0415', '8a61a513-7b1d-4705-9328-266ef6f1a6c4', '08:00:00', '11:00:00', '2019-12-02'),
('cfade19a-3104-4fee-9b07-2f05b52a7cd5', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '10:00:00', '17:00:00', '2019-12-02'),
('58c2ecc3-39af-4072-b5f1-fee00fc2124d', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '08:00:00', '17:00:00', '2019-12-03'),
('4eba7897-eb97-4701-91f0-5195551778d9', 'cbe39faf-7a0b-4479-a03f-a6467ed59af2', '08:00:00', '15:00:00', '2019-12-03'),
('fd06e3af-8d46-4c8d-945f-12cb46c0c576', 'c7bcfe6a-9c10-4a06-9de3-03aef3b366bf', '10:00:00', '17:00:00', '2019-12-03'),
('a41cd64f-fc93-473d-92b4-69d2a81b34c4', '8a61a513-7b1d-4705-9328-266ef6f1a6c4', '08:00:00', '11:00:00', '2019-12-03'),
('44d4119d-ecb9-4849-b4df-b925bf4e6c69', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '10:00:00', '17:00:00', '2019-12-03'),
('ee8247e7-e118-420c-a846-77f2c0e05f15', 'dd60294b-6f21-4449-8796-646ce0c5a98a', '08:00:00', '17:00:00', '2019-12-04'),
('46baa84e-336e-4f4b-8391-bb105017428d', 'cbe39faf-7a0b-4479-a03f-a6467ed59af2', '08:00:00', '15:00:00', '2019-12-04'),
('941580fb-8fef-439d-8349-55eb05987329', 'c7bcfe6a-9c10-4a06-9de3-03aef3b366bf', '10:00:00', '17:00:00', '2019-12-04'),
('07b3d88d-e561-4d7d-bd07-3ef7aacb6a90', '8a61a513-7b1d-4705-9328-266ef6f1a6c4', '08:00:00', '11:00:00', '2019-12-04'),
('c47b8c86-fafc-48d8-83c4-f7eb736c5178', '1b170bc3-b739-41d6-bbdf-81b1beac7853', '10:00:00', '17:00:00', '2019-12-04');