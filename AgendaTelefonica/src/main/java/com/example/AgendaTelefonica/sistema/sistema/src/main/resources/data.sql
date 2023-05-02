INSERT INTO engenheiros(nome, CREA, Especialidade) VALUES('Manoel Neto', '25432', 'CIVIL');
INSERT INTO engenheiros(nome, CREA, Especialidade) VALUES('Maria Menezes', '12345', 'QUÌMICO');
INSERT INTO engenheiros(nome, CREA, Especialidade) VALUES('Ricardo Souto', '87904', 'ELETRICISTA');

INSERT INTO projetos(nome, custo, categoria) VALUES('Nova Moradia', 27000, 'PRIVADO');
INSERT INTO projetos(nome, custo, categoria) VALUES('Lago artificial', 32000, 'PARCERIA');
INSERT INTO projetos(nome, custo, categoria) VALUES('Quadra de Futebol', 10000, 'PÚBLICO');

INSERT INTO atuacoes(engenheiro_id, projeto_id, duracao) VALUES(1, 3, 150);
INSERT INTO atuacoes(engenheiro_id, projeto_id, duracao) VALUES(2, 1, 270);
INSERT INTO atuacoes(engenheiro_id, projeto_id, duracao) VALUES(3, 2, 400);
