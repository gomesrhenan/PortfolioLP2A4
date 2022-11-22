CREATE DATABASE AlunoDB;
USE AlunoDB;

CREATE TABLE Aluno (
	matricula INT PRIMARY KEY,
	nome VARCHAR(55),
	endereco VARCHAR(255),
	data_inicio DATE,
	data_termino DATE
);