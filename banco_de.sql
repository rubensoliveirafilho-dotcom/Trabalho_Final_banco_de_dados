CREATE TABLE IF NOT EXISTS Instrutor (
    id_instrutor SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    especialidade VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Aluno (
    id_aluno SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS Curso (
    id_curso SERIAL PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    id_instrutor INT NOT NULL,
  
    CONSTRAINT chk_preco_positivo CHECK (preco >= 0),
  
    CONSTRAINT fk_curso_instrutor FOREIGN KEY (id_instrutor) 
        REFERENCES Instrutor(id_instrutor) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Matricula (
    id_matricula SERIAL PRIMARY KEY,
    id_aluno INT NOT NULL,
    id_curso INT NOT NULL,
    data_matricula DATE NOT NULL DEFAULT CURRENT_DATE,
    status_matricula VARCHAR(20) DEFAULT 'Inativa',
    
    CONSTRAINT chk_status_matricula CHECK (status_matricula IN ('Ativa', 'Inativa')),
    CONSTRAINT fk_matricula_aluno FOREIGN KEY (id_aluno) REFERENCES Aluno(id_aluno),
    CONSTRAINT fk_matricula_curso FOREIGN KEY (id_curso) REFERENCES Curso(id_curso)
);

CREATE TABLE IF NOT EXISTS Pagamento (
    id_pagamento SERIAL PRIMARY KEY,
    id_matricula INT NOT NULL,
    valor_pago DECIMAL(10, 2) NOT NULL,
    status_pagamento VARCHAR(20) DEFAULT 'Pendente',
    
    CONSTRAINT chk_valor_positivo CHECK (valor_pago >= 0),
    CONSTRAINT chk_status_pagamento CHECK (status_pagamento IN ('Pendente', 'Aprovado', 'Cancelado')),
    CONSTRAINT fk_pagamento_matricula FOREIGN KEY (id_matricula) REFERENCES Matricula(id_matricula)
);


CREATE OR REPLACE VIEW View_PainelVendas AS
SELECT 
    m.id_matricula,
    a.nome AS nome_aluno,
    c.titulo AS nome_curso,
    p.valor_pago,
    p.status_pagamento,
    m.data_matricula
FROM Matricula m
JOIN Aluno a ON m.id_aluno = a.id_aluno
JOIN Curso c ON m.id_curso = c.id_curso
JOIN Pagamento p ON p.id_matricula = m.id_matricula;


SELECT * FROM View_PainelVendas


CREATE OR REPLACE FUNCTION CalcularRepasseInstrutor(preco_curso DECIMAL(10,2))
RETURNS DECIMAL(10,2) AS $$
BEGIN

RETURN preco_curso * 0.85;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE AprovarPagamento(p_id_pagamento INT)
AS $$
DECLARE
    v_id_matricula INT;
BEGIN

UPDATE Pagamento 
    SET status_pagamento = 'Aprovado' 
    WHERE id_pagamento = p_id_pagamento;

	SELECT id_matricula INTO v_id_matricula 
    FROM Pagamento 
    WHERE id_pagamento = p_id_pagamento;

	UPDATE Matricula 
    SET status_matricula = 'Ativa' 
    WHERE id_matricula = v_id_matricula;
    
    COMMIT;
END;

$$ LANGUAGE plpgsql;

INSERT INTO Instrutor (nome, especialidade, email) VALUES
('Alexandre', 'Desenvolvimento Java e Spring Boot', 'alexandre@email.com'),
('Mariana', 'Banco de Dados e SQL', 'mariana@email.com'),
('Carlos ', 'Front-end com React', 'carlos@email.com');

INSERT INTO Aluno (nome, email, cpf) VALUES
('Bruno ', 'bruno@email.com', '111.222.333-44'),
('Ana ', 'ana@email.com', '555.666.777-88'),
('Diego ', 'diego@email.com', '999.888.777-66');

INSERT INTO Curso (titulo, preco, id_instrutor) VALUES
('Java Completo: Do Zero ao Profissional', 499.90, 1),
('Mastering SQL e Modelagem de Dados', 299.00, 2),
('React Produtivo com TypeScript', 350.00, 3);

INSERT INTO Matricula (id_aluno, id_curso, status_matricula) VALUES
(1, 1, 'Inativa'), -- Bruno no curso de Java	
(2, 2, 'Inativa'), -- Ana no curso de SQL
(3, 1, 'Inativa'); -- Diego no curso de Java

INSERT INTO Pagamento (id_matricula, valor_pago, status_pagamento) VALUES
(1, 499.90, 'Pendente'),
(2, 299.00, 'Pendente'),
(3, 499.90, 'Pendente');


UPDATE Curso 
SET preco = 399.90 
WHERE id_curso = 1;

UPDATE Aluno 
SET email = 'bruno.silva.novo@email.com' 
WHERE id_aluno = 1;

DELETE FROM Pagamento 
WHERE id_pagamento = 3;

DELETE FROM Matricula 
WHERE id_matricula = 3;



SELECT 
    c.id_curso, 
    c.titulo AS nome_curso, 
    c.preco, 
    i.nome AS nome_instrutor
FROM Curso c
INNER JOIN Instrutor i ON c.id_instrutor = i.id_instrutor;


SELECT 
    m.id_matricula, 
    a.nome AS nome_aluno, 
    c.titulo AS nome_curso, 
    m.status_matricula
FROM Matricula m
INNER JOIN Aluno a ON m.id_aluno = a.id_aluno
INNER JOIN Curso c ON m.id_curso = c.id_curso;


SELECT 
    p.id_pagamento,
    a.nome AS nome_aluno,
    c.titulo AS nome_curso,
    p.valor_pago,
    p.status_pagamento
FROM Pagamento p
INNER JOIN Matricula m ON p.id_matricula = m.id_matricula
INNER JOIN Aluno a ON m.id_aluno = a.id_aluno
INNER JOIN Curso c ON m.id_curso = c.id_curso;

SELECT * FROM View_PainelVendas;

SELECT titulo, preco, CalcularRepasseInstrutor(preco) AS ganho_instrutor 
FROM Curso;

CALL AprovarPagamento(1);

SELECT * FROM View_PainelVendas WHERE id_matricula = 1;
