CREATE TABLE Produto (
    idProduto INTEGER NOT NULL,
    descricao VARCHAR(80) NOT NULL,
    preco NUMERIC(18, 6) NOT NULL,

    PRIMARY KEY (idProduto)
);