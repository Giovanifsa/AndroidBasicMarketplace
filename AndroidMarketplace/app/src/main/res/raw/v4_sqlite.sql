CREATE TABLE PedidoItem (
    idPedidoItem INTEGER NOT NULL,
    idProduto INTEGER NOT NULL,
    idPedido INTEGER NOT NULL,
    quantidade VARCHAR(18) NOT NULL,
    precoOriginal VARCHAR(18) NOT NULL,
    precoVenda VARCHAR(18) NOT NULL,
    valorDesconto VARCHAR(18) NOT NULL,

    PRIMARY KEY (idPedidoItem),
    FOREIGN KEY (idProduto) REFERENCES Produto(idProduto),
    FOREIGN KEY (idPedido) REFERENCES Pedido(idPedido)
);