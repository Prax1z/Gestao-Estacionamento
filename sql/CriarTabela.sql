
CREATE TABLE Carro (
    Placa       TEXT(10)  NOT NULL,
    Marca       TEXT(50),
    Cor         TEXT(30),
    HoraEntrada INTEGER,
    HoraSaida   INTEGER,
    CONSTRAINT PK_Carro PRIMARY KEY (Placa)
);

INSERT INTO Carro (Placa, Marca, Cor, HoraEntrada, HoraSaida)
VALUES ('ABC-1234', 'Toyota',  'Prata',   7,  18);

INSERT INTO Carro (Placa, Marca, Cor, HoraEntrada, HoraSaida)
VALUES ('DEF-5678', 'Honda',   'Preto',   8,  17);

INSERT INTO Carro (Placa, Marca, Cor, HoraEntrada, HoraSaida)
VALUES ('GHI-9012', 'Ford',    'Branco',  9,  19);

INSERT INTO Carro (Placa, Marca, Cor, HoraEntrada, HoraSaida)
VALUES ('JKL-3456', 'Chevrolet','Azul',   7,  16);

INSERT INTO Carro (Placa, Marca, Cor, HoraEntrada, HoraSaida)
VALUES ('MNO-7890', 'Volkswagen','Cinza', 8,  18);
