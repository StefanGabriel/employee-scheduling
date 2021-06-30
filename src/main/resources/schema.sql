-- definim schema bazei de date
CREATE TABLE FIRMA (
  id_firma UUID PRIMARY KEY NOT NULL,
  nume_firma VARCHAR(255) NOT NULL,
  cui VARCHAR(30) NOT NULL,
  cif VARCHAR(30) NOT NULL,
  adresa_sediu_social VARCHAR(255) NOT NULL
);

CREATE TABLE USER_CREDENTIALS (
  id_user UUID PRIMARY KEY NOT NULL,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(100) NOT NULL
);

CREATE TABLE ANGAJATI (
  id_ang UUID PRIMARY KEY NOT NULL,
  nume VARCHAR(50) NOT NULL,
  prenume VARCHAR(50) NOT NULL,
  cnp VARCHAR(13) NOT NULL UNIQUE,
  functie VARCHAR(100),
  strada VARCHAR(50) NOT NULL,
  numar int NOT NULL,
  localitate VARCHAR(50) NOT NULL,
  judet VARCHAR(50) NOT NULL,
  tara VARCHAR(50) NOT NULL,
  stare bit,
  user_id UUID NOT NULL,
  id_firma UUID,
  email VARCHAR(50) NOT NULL,
  data_nasterii date NOT NULL,
  serie VARCHAR(5),
  nr int,
  is_manager bit,
  salariu INT,
  norma_de_lucru INT,
  data_angajarii DATE
);

CREATE TABLE PUNCT_DE_LUCRU (
  id_punct UUID PRIMARY KEY NOT NULL,
  adresa VARCHAR(255) NOT NULL,
  nume VARCHAR(100) NOT NULL,
  id_firma UUID NOT NULL,
  ora_inceput_program time NOT NULL,
  ora_sfarsit_program time NOT NULL,
  ora_minima_recuperare time NOT NULL,
  ora_maxima_recuperare time NOT NULL,
  CONSTRAINT fk4
    FOREIGN KEY (id_firma)
    REFERENCES FIRMA(id_firma)
);

CREATE TABLE ECHIPA (
  id_echipa UUID PRIMARY KEY NOT NULL,
  nume VARCHAR(100) NOT NULL,
  id_firma UUID NOT NULL,
  id_team_leader UUID NOT NULL
);

CREATE TABLE ANGAJAT_ECHIPA (
  id UUID PRIMARY KEY NOT NULL,
  id_ang UUID NOT NULL,
  id_echipa UUID NOT NULL,
  CONSTRAINT fk3
    FOREIGN KEY (id_echipa)
    REFERENCES echipa(id_echipa)
    ON DELETE CASCADE
);

CREATE TABLE ALTERARI_TIMP_LUCRU (
  id_inreg UUID PRIMARY KEY NOT NULL,
  id_ang UUID NOT NULL,
  start TIME NOT NULL,
  sfarsit TIME NOT NULL,
  data DATE NOT NULL,
  tip_alterare VARCHAR(50) NOT NULL,
  fisier VARCHAR(255)
);

CREATE TABLE CONCEDIU (
  id_inreg UUID PRIMARY KEY NOT NULL,
  id_ang UUID NOT NULL,
  start DATE NOT NULL,
  sfarsit DATE NOT NULL,
  tip_alterare VARCHAR(50) NOT NULL,
  fisier VARCHAR(255),
  numar_zile INT
);

CREATE TABLE PONTAJ (
  id_pontaj UUID PRIMARY KEY NOT NULL,
  id_ang UUID NOT NULL,
  ora_start TIME NOT NULL,
  ora_end TIME NOT NULL,
  data DATE NOT NULL
);