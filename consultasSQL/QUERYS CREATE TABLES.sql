CREATE TABLE curso (
    codigo SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(4000) NOT NULL,
    duracao INT NOT NULL
);

CREATE TABLE funcionario (
    codigo SERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    cpf CHAR(11) NOT NULL,
    nascimento DATE NOT NULL,
    cargo VARCHAR(200) NOT NULL,
    admissao DATE NOT NULL,
    status BOOLEAN NOT NULL
);

CREATE TABLE turma (
    codigo SERIAL PRIMARY KEY,
    inicio DATE NOT NULL,
    fim DATE NOT NULL,
    local VARCHAR(200),
    curso INT,
    CONSTRAINT fk_turma_curso FOREIGN KEY (curso) REFERENCES curso(codigo) ON DELETE CASCADE
);

CREATE TABLE turma_participante (
    codigo SERIAL PRIMARY KEY,
    turma INT NOT NULL,
    funcionario INT NOT NULL,
    CONSTRAINT fk_turma FOREIGN KEY (turma) REFERENCES turma(codigo),
    CONSTRAINT fk_funcionario FOREIGN KEY (funcionario) REFERENCES funcionario(codigo)
);

INSERT INTO curso (nome, descricao,duracao) VALUES ('Machine Learning','Curso de ML especial', 90)

INSERT INTO turma (inicio, fim, local,curso) VALUES ('2025-10-10','2025-11-10','Indaituba',2)

INSERT INTO funcionario (nome, cpf, nascimento, cargo, admissao, status) VALUES
('Bruno Costa', '23456789012', '1985-07-15', 'Analista', '2019-03-20', '1'),
('Carlos Dias', '34567890123', '1992-12-05', 'Coordenador', '2021-06-01', '1'),
('Daniela Freitas', '45678901234', '1995-03-22', 'Instrutora', '2022-02-01', '1'),
('Eduardo Silva', '56789012345', '1988-09-11', 'Desenvolvedor', '2018-10-10', '1'),
('Fernanda Rocha', '67890123456', '1993-01-19', 'Analista', '2019-05-05', '1'),
('Guilherme Nunes', '78901234567', '1987-08-08', 'Instrutor', '2017-07-07', '1'),
('Helena Souza', '89012345678', '1991-04-30', 'Consultora', '2020-04-04', '1'),
('Igor Martins', '90123456789', '1994-11-12', 'Instrutor', '2023-01-01', '1'),
('Juliana Alves', '01234567890', '1990-02-28', 'Desenvolvedora', '2021-08-08', '1');

SELECT * FROM funcionario

SELECT c.*, t.codigo as turma_codigo
FROM curso c INNER JOIN turma t ON t.curso = c.codigo ORDER BY c.codigo

SELECT * FROM turma

DELETE FROM turma_participante WHERE turma = 7 AND funcionario = 2

INSERT INTO curso (nome, descricao,duracao) VALUES ('curso teste','teste',700)

INSERT INTO turma (inicio, fim, local, curso)
VALUES ('2025-08-01', '2025-08-10', 'Sala 101', 2);

INSERT INTO turma (inicio, fim, local, curso)
VALUES ('2025-09-01', '2025-09-15', 'Sala 202', 3);

UPDATE funcionario SET status = '1' WHERE codigo = 4

SELECT 
		t.inicio,
        t.fim,
		t.local,
		t.curso,
COUNT(tp.funcionario) AS participantes
FROM turma t
LEFT JOIN turma_participante tp ON t.codigo = tp.turma
WHERE t.curso = 2 
GROUP BY t.codigo, t.inicio, t.fim, t.local, t.curso
ORDER BY t.inicio,t.fim

SELECT f.* FROM funcionario f INNER JOIN turma_participante tp ON f.codigo = tp.funcionario WHERE tp.turma = 7 ORDER BY nome

INSERT INTO turma_participante (turma, funcionario) VALUES
(7, 1),
(7, 2),
(7, 3),
(7, 4),
(7, 5);

INSERT INTO turma_participante (turma, funcionario) VALUES
(8, 6),
(8, 7),
(8, 8),
(8, 9),
(8, 10);

INSERT INTO turma_participante (turma, funcionario) VALUES (3,9)

SELECT 
	t.codigo,
    t.inicio,
    t.fim,
    t.local,
    t.curso,
    COUNT(tp.funcionario) AS participantes
FROM turma t
LEFT JOIN turma_participante tp ON t.codigo = tp.turma
WHERE t.curso = 2
GROUP BY t.codigo, t.inicio, t.fim, t.local, t.curso
ORDER BY t.inicio, t.fim;

DELETE FROM turma_participante WHERE turma = 3

DELETE FROM turma WHERE codigo = 3

INSERT INTO turma (inicio, fim, local,curso) VALUES ('2025-05-20','2025-09-20','SALA 999',2)

UPDATE TURMA SET curso = 2 WHERE codigo = 9

select * from FUNCIONARIO ORDER BY CODIGO

select * from turma_participante

SELECT f.* FROM funcionario f INNER JOIN turma_participante tp ON f.codigo = tp.funcionario WHERE tp.turma = 7 ORDER BY nome
