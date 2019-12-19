CREATE TABLE Pedido (
    idPedido INTEGER NOT NULL,
    idUsuario INTEGER NOT NULL,
    cliente VARCHAR(200) NOT NULL,
    endereco VARCHAR(200) NOT NULL,
    dataPedido VARCHAR(30) NOT NULL,
    totalItens VARCHAR(18) NOT NULL,
    totalProdutos VARCHAR(18) NOT NULL,
    valorTotal VARCHAR(18) NOT NULL,

    PRIMARY KEY (idPedido),
    FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
);