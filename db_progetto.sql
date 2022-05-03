DROP DATABASE SWENG_MILLEFANTI_BORRELLI IF EXISTS;
CREATE DATABASE SWENG_MILLEFANTI_BORRELLI;
USE SWENG_MILLEFANTI_BORRELLI;


CREATE TABLE user(
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  surname VARCHAR(50) NOT NULL,
  birthDate date NOT NULL,
  cf VARCHAR(20) NOT NULL,
  isElector BOOLEAN DEFAULT TRUE NOT NULL,
)engine=innoDB;

CREATE TABLE credential(
  idUser INT NOT NULL,
  email VARCHAR(50) NOT NULL PRIMARY KEY,
  password VARCHAR(150) NOT NULL,
  FOREIGN KEY idUser REFERENCES user(id)
)engine=innoDB;

CREATE TABLE voteType(
  id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  referendum BOOLEAN NOT NULL
)engine=innoDB;

CREATE TABLE session(
  id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  isActive BOOLEAN NOT NULL,
  idVoteType INT NOT NULL,
  FOREIGN KEY idVoteType REFERENCES voteType(id)
)engine=innoDB;

CREATE TABLE vote(
  id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  faction INT NOT NULL,
  idSession INT NOT NULL,
  FOREIGN KEY idSession REFERENCES session(id)
)engine=innoDB;

CREATE TABLE voteRef(
  id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  isPositive BOOLEAN DEFAULT NULL,
  idSession INT NOT NULL,
  FOREIGN KEY idSession REFERENCES session(id)
)engine=innoDB;

CREATE TABLE verVote(
  id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  idUser INT NOT NULL,
  idSession INT NOT NULL,
  FOREIGN KEY idSession REFERENCES session(id),
  FOREIGN KEY idUser REFERENCES user(id)
)engine=innoDB;

CREATE TABLE faction(
  id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  isPositive BOOLEAN DEFAULT NULL,
  idSession INT NOT NULL,
  FOREIGN KEY idSession REFERENCES session(id)
)engine=innoDB;

CREATE TABLE candidate(
  id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  surname VARCHAR(50) NOT NULL
)engine=innoDB;

CREATE TABLE candidate_vote(
  idCandidate INT NOT NULL,
  idVote INT NOT NULL,
  FOREIGN KEY idCandidate REFERENCES candidate(id),
  FOREIGN KEY idVote REFERENCES vote(id)
)engine=innoDB;

CREATE TABLE faction_session(
  idFaction INT NOT NULL,
  idSession INT NOT NULL,
  PRIMARY KEY(idFaction,idSession),
  FOREIGN KEY idFaction REFERENCES faction(id),
  FOREIGN KEY idSession REFERENCES session(id)
)engine=innoDB;

CREATE TABLE candidate_session(
  idCandidate INT NOT NULL,
  idSession INT NOT NULL,
  PRIMARY KEY(idCandidate,idSession),
  FOREIGN KEY idCandidate REFERENCES candidate(id),
  FOREIGN KEY idSession REFERENCES session(id)
)engine=innoDB;
