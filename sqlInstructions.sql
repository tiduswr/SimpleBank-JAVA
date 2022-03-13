CREATE TABLE IF NOT EXISTS pessoas (
id                          INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
nome                        TEXT NOT NULL,
cpf                         TEXT(14) NOT NULL,
dtNascimento                TEXT(10) NOT NULL,
email                       TEXT NOT NULL,
cidade                      TEXT NOT NULL,
numCasa                     INTEGER NOT NULL,
rua                         TEXT NOT NULL;
bairro                      TEXT NOT NULL,
cidade                      TEXT NOT NULL,
estado                      TEXT NOT NULL,
dddTelefone                 INTEGER NOT NULL,
numeroTelefone              TEXT NOT NULL);

CREATE TABLE IF NOT EXISTS clientes (
idCliente              INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
dtCadastro             TEXT(10) NOT NULL,
idPessoa               INTEGER NOT NULL,
FOREIGN KEY(idPessoa) REFERENCES pessoas(id) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS administradores (
idAdm                  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
dtAdmissao             TEXT(10) NOT NULL,
cargo                  TEXT NOT NULL,
idPessoa               INTEGER NOT NULL,
FOREIGN KEY(idPessoa) REFERENCES pessoas(id) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS usuarios (
idUser                 INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
senha                  TEXT NOT NULL,
tipo                   INTEGER NOT NULL,
idPessoa               INTEGER NOT NULL,
FOREIGN KEY(idPessoa) REFERENCES pessoas(id) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS contas (
idConta                INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
agencia                TEXT NOT NULL,
numeroConta            TEXT NOT NULL,
dtCreation             TEXT NOT NULL,
saldo                  REAL NOT NULL,
idPessoa               INTEGER NOT NULL,
FOREIGN KEY(idPessoa) REFERENCES pessoas(id) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE IF NOT EXISTS transacoes (
idTransacao            INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
idContaOrigem          TEXT NOT NULL,
idContaDestino         TEXT,
tipoTransacao          INTEGER NOT NULL,
valMovimentado         REAL NOT NULL,
dtMovimento            TEXT(10) NOT NULL,
FOREIGN KEY(idContaOrigem) REFERENCES contas(idConta) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(idContaDestino) REFERENCES contas(idConta) ON DELETE CASCADE ON UPDATE CASCADE);


