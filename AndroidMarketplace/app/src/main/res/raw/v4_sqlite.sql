CREATE TABLE PedidoItem (
    idPedidoItem INTEGER NOT NULL,
    idProduto INTEGER NOT NULL,
    idPedido INTEGER NOT NULL,
    quantidade NUMERIC(18,6) NOT NULL,
    precoOriginal NUMERIC(18,6) NOT NULL,
    precoVenda NUMERIC(18,6) NOT NULL,
    valorDesconto NUMERIC(18,6) NOT NULL,

    PRIMARY KEY (idPedidoItem),
    FOREIGN KEY (idProduto) REFERENCES Produto(idProduto),
    FOREIGN KEY (idPedido) REFERENCES Pedido(idPedido)
);