-- =====================================================
-- Script de criação das tabelas para SQLite
-- =====================================================

-- Limpar tabelas existentes
DROP TABLE IF EXISTS Telemetria;
DROP TABLE IF EXISTS Simulacao;
DROP TABLE IF EXISTS InvestimentoHistorico;
DROP TABLE IF EXISTS Produto;
DROP TABLE IF EXISTS Cliente;

-- =====================================================
-- Tabela: Cliente
-- =====================================================
CREATE TABLE Cliente (
    id INTEGER PRIMARY KEY,
    nome VARCHAR(255),
    perfil VARCHAR(255),
    pontuacao INTEGER
);

-- =====================================================
-- Tabela: Produto
-- =====================================================
CREATE TABLE Produto (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    rentabilidade DOUBLE NOT NULL,
    risco VARCHAR(255) NOT NULL,
    perfil_recomendado VARCHAR(255) NOT NULL
);

-- =====================================================
-- Tabela: InvestimentoHistorico
-- =====================================================
CREATE TABLE InvestimentoHistorico (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    cliente_id INTEGER NOT NULL,
    produto_id INTEGER NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    valor DOUBLE NOT NULL,
    rentabilidade DOUBLE,
    data VARCHAR(255) NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
    FOREIGN KEY(produto_id) REFERENCES Produto(id)
);

-- =====================================================
-- Tabela: Simulacao
-- =====================================================
CREATE TABLE Simulacao (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    cliente_id INTEGER NOT NULL,
    produto_nome VARCHAR(255) NOT NULL,
    valor_investido DOUBLE NOT NULL,
    valor_final DOUBLE NOT NULL,
    prazo_meses INTEGER NOT NULL,
    data_simulacao VARCHAR(255) NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);

-- =====================================================
-- Tabela: Telemetria
-- =====================================================

CREATE TABLE Telemetria (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    servico_nome        VARCHAR(255) NOT NULL,
    tempo_resposta_ms   INTEGER NOT NULL,
    data_chamada        VARCHAR(255) NOT NULL,
    http_status         INTEGER NOT NULL,
    http_method         VARCHAR(20) NOT NULL,
    path                VARCHAR(500) NOT NULL
);


-- ============================
-- PRODUTOS BASE + NOVOS
-- ============================
INSERT INTO Produto (id, nome, tipo, rentabilidade, risco, perfil_recomendado)
VALUES
(1, 'CDB Caixa 12% a.a.', 'CDB', 0.12, 'Moderado', 'CONSERVADOR'),
(2, 'LCI Caixa 10% a.a.', 'LCI', 0.10, 'Baixo', 'CONSERVADOR'),
(3, 'Fundo Rendimento Agressivo', 'Fundo', 0.18, 'Alto', 'AGRESSIVO'),

(4, 'Tesouro Selic 2029', 'Tesouro', 0.093, 'Baixo', 'CONSERVADOR'),
(5, 'CDB Banco XPTO 14% a.a.', 'CDB', 0.14, 'Médio', 'MODERADO'),
(6, 'Fundo Multimercado Alpha', 'Fundo', 0.22, 'Alto', 'AGRESSIVO');






INSERT INTO Cliente (id, nome, perfil, pontuacao) VALUES
(1, 'João Silva', 'CONSERVADOR', 20),
(2, 'Maria Souza', 'MODERADO', 50),
(3, 'Carlos Pereira', 'AGRESSIVO', 90);



INSERT INTO InvestimentoHistorico (cliente_id, produto_id, tipo, valor, rentabilidade, data) VALUES
(1, 4, 'Tesouro', 3000, 0.093, '2024-01-10'),
(1, 2, 'LCI', 2500, 0.10, '2024-01-28'),
(1, 1, 'CDB', 4000, 0.12, '2024-02-05'),
(1, 4, 'Tesouro', 3500, 0.093, '2024-02-18'),
(1, 2, 'LCI', 2600, 0.10, '2024-03-04'),
(1, 1, 'CDB', 4500, 0.12, '2024-03-19'),
(1, 4, 'Tesouro', 3800, 0.093, '2024-04-02'),
(1, 2, 'LCI', 2700, 0.10, '2024-04-17'),
(1, 1, 'CDB', 5000, 0.12, '2024-05-01'),
(1, 4, 'Tesouro', 3300, 0.093, '2024-05-16'),
(1, 2, 'LCI', 2900, 0.10, '2024-06-03'),
(1, 1, 'CDB', 5200, 0.12, '2024-06-20'),
(1, 4, 'Tesouro', 3100, 0.093, '2024-07-08'),
(1, 2, 'LCI', 2800, 0.10, '2024-07-25'),
(1, 1, 'CDB', 5400, 0.12, '2024-08-12'),
(1, 4, 'Tesouro', 3000, 0.093, '2024-08-29'),
(1, 2, 'LCI', 3100, 0.10, '2024-09-10'),
(1, 1, 'CDB', 5600, 0.12, '2024-09-27'),
(1, 4, 'Tesouro', 3600, 0.093, '2024-10-13'),
(1, 2, 'LCI', 3300, 0.10, '2024-10-29'),
(1, 1, 'CDB', 5800, 0.12, '2024-11-14'),
(1, 4, 'Tesouro', 3700, 0.093, '2024-11-30'),
(1, 2, 'LCI', 3500, 0.10, '2024-12-15'),
(1, 1, 'CDB', 6000, 0.12, '2024-12-29'),
(1, 4, 'Tesouro', 3900, 0.093, '2025-01-05'),
(1, 2, 'LCI', 3600, 0.10, '2025-01-22'),
(1, 1, 'CDB', 6200, 0.12, '2025-02-03'),
(1, 4, 'Tesouro', 4000, 0.093, '2025-02-20'),
(1, 2, 'LCI', 3700, 0.10, '2025-03-01'),
(1, 1, 'CDB', 6500, 0.12, '2025-03-15');


INSERT INTO InvestimentoHistorico (cliente_id, produto_id, tipo, valor, rentabilidade, data) VALUES
(2, 5, 'CDB', 7000, 0.14, '2024-01-12'),
(2, 1, 'CDB', 6500, 0.12, '2024-01-30'),
(2, 6, 'Fundo', 8000, 0.22, '2024-02-15'),
(2, 5, 'CDB', 7200, 0.14, '2024-02-28'),
(2, 1, 'CDB', 6700, 0.12, '2024-03-10'),
(2, 6, 'Fundo', 8200, 0.22, '2024-03-28'),
(2, 5, 'CDB', 7400, 0.14, '2024-04-12'),
(2, 1, 'CDB', 6800, 0.12, '2024-04-29'),
(2, 6, 'Fundo', 8500, 0.22, '2024-05-14'),
(2, 5, 'CDB', 7600, 0.14, '2024-05-30'),
(2, 1, 'CDB', 6900, 0.12, '2024-06-16'),
(2, 6, 'Fundo', 8700, 0.22, '2024-06-29'),
(2, 5, 'CDB', 7800, 0.14, '2024-07-10'),
(2, 1, 'CDB', 7000, 0.12, '2024-07-28'),
(2, 6, 'Fundo', 9000, 0.22, '2024-08-13'),
(2, 5, 'CDB', 8000, 0.14, '2024-08-30'),
(2, 1, 'CDB', 7200, 0.12, '2024-09-12'),
(2, 6, 'Fundo', 9100, 0.22, '2024-09-29'),
(2, 5, 'CDB', 8200, 0.14, '2024-10-10'),
(2, 1, 'CDB', 7300, 0.12, '2024-10-28'),
(2, 6, 'Fundo', 9400, 0.22, '2024-11-15'),
(2, 5, 'CDB', 8400, 0.14, '2024-11-30'),
(2, 1, 'CDB', 7500, 0.12, '2024-12-17'),
(2, 6, 'Fundo', 9600, 0.22, '2024-12-30'),
(2, 5, 'CDB', 8600, 0.14, '2025-01-10'),
(2, 1, 'CDB', 7700, 0.12, '2025-01-29'),
(2, 6, 'Fundo', 9800, 0.22, '2025-02-14'),
(2, 5, 'CDB', 8800, 0.14, '2025-02-27'),
(2, 1, 'CDB', 7900, 0.12, '2025-03-10'),
(2, 6, 'Fundo', 10000, 0.22, '2025-03-27');



INSERT INTO InvestimentoHistorico (cliente_id, produto_id, tipo, valor, rentabilidade, data) VALUES
(3, 3, 'Fundo', 15000, 0.18, '2024-01-05'),
(3, 6, 'Fundo', 20000, 0.22, '2024-01-25'),
(3, 5, 'CDB', 12000, 0.14, '2024-02-12'),
(3, 3, 'Fundo', 16000, 0.18, '2024-02-29'),
(3, 6, 'Fundo', 21000, 0.22, '2024-03-13'),
(3, 5, 'CDB', 13000, 0.14, '2024-03-28'),
(3, 3, 'Fundo', 17000, 0.18, '2024-04-09'),
(3, 6, 'Fundo', 22000, 0.22, '2024-04-30'),
(3, 5, 'CDB', 14000, 0.14, '2024-05-14'),
(3, 3, 'Fundo', 18000, 0.18, '2024-05-29'),
(3, 6, 'Fundo', 23000, 0.22, '2024-06-12'),
(3, 5, 'CDB', 15000, 0.14, '2024-06-27'),
(3, 3, 'Fundo', 19000, 0.18, '2024-07-15'),
(3, 6, 'Fundo', 24000, 0.22, '2024-07-30'),
(3, 5, 'CDB', 16000, 0.14, '2024-08-11'),
(3, 3, 'Fundo', 20000, 0.18, '2024-08-26'),
(3, 6, 'Fundo', 25000, 0.22, '2024-09-12'),
(3, 5, 'CDB', 17000, 0.14, '2024-09-29'),
(3, 3, 'Fundo', 21000, 0.18, '2024-10-10'),
(3, 6, 'Fundo', 26000, 0.22, '2024-10-29'),
(3, 5, 'CDB', 18000, 0.14, '2024-11-11'),
(3, 3, 'Fundo', 22000, 0.18, '2024-11-27'),
(3, 6, 'Fundo', 27000, 0.22, '2024-12-12'),
(3, 5, 'CDB', 19000, 0.14, '2024-12-29'),
(3, 3, 'Fundo', 23000, 0.18, '2025-01-12'),
(3, 6, 'Fundo', 28000, 0.22, '2025-01-31'),
(3, 5, 'CDB', 20000, 0.14, '2025-02-10'),
(3, 3, 'Fundo', 24000, 0.18, '2025-02-28'),
(3, 6, 'Fundo', 29000, 0.22, '2025-03-15'),
(3, 5, 'CDB', 21000, 0.14, '2025-03-30');


