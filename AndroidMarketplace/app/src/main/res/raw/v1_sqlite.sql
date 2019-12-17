CREATE TABLE Usuario (
    idUsuario INTEGER NOT NULL,
    nome VARCHAR(80) NOT NULL,
    login VARCHAR(80) NOT NULL,
    senha VARCHAR(80) NOT NULL,
    numeroPerguntaSeguranca INTEGER NOT NULL,
    respostaPerguntaSeguranca VARCHAR(40) NOT NULL,

    PRIMARY KEY (idUsuario)
);

CREATE TABLE Produto (
    idProduto INTEGER NOT NULL,
    descricao VARCHAR(80) NOT NULL,
    preco NUMERIC(18, 6) NOT NULL,

    PRIMARY KEY (idProduto)
);

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