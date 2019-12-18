CREATE TABLE Pedido (
    idPedido INTEGER NOT NULL,
    idUsuario INTEGER NOT NULL,
    cliente VARCHAR(200) NOT NULL,
    endereco VARCHAR(200) NOT NULL,
    dataPedido DATE NOT NULL,
    totalItens NUMERIC(18, 6) NOT NULL,
    totalProdutos NUMERIC(18, 6) NOT NULL,
    valorTotal NUMERIC(18, 6) NOT NULL,

    PRIMARY KEY (idPedido),
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
);