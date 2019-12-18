CREATE TABLE Usuario (
    idUsuario INTEGER NOT NULL,
    nome VARCHAR(80) NOT NULL,
    login VARCHAR(80) NOT NULL,
    senha VARCHAR(80) NOT NULL,
    numeroPerguntaSeguranca INTEGER NOT NULL,
    respostaPerguntaSeguranca VARCHAR(40) NOT NULL,

    PRIMARY KEY (idUsuario)
);