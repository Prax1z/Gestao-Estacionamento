# Sistema de Estacionamento — Java + JDBC + Access

## Estrutura do Projeto

```
estacionamento/
├── src/
│   ├── model/
│   │   └── Carro.java          ← Classe de modelo
│   ├── dao/
│   │   ├── Conexao.java        ← Gerenciamento de conexão JDBC
│   │   └── CarroDAO.java       ← Operações CRUD na tabela
│   └── view/
│       └── TelaPrincipal.java  ← Interface gráfica (JFrame + abas)
└── sql/
    └── CriarTabela.sql         ← Script de criação da tabela e dados de teste
```

---

## Pré-requisitos

| Requisito         | Versão mínima |
|-------------------|---------------|
| Java (JDK)        | 8+            |
| UCanAccess        | 5.0+          |
| Microsoft Access  | 2010+         |

---

## Configuração do Banco de Dados

### 1. Criar o arquivo Access

1. Abra o Microsoft Access.
2. Crie um novo banco de dados em branco chamado **Estacionamento.accdb**.
3. Salve-o em **C:\Estacionamento\Estacionamento.accdb**.

### 2. Criar a tabela Carro

No Access, abra a janela SQL (Criar → Design de Consulta → SQL) e execute:

```sql
CREATE TABLE Carro (
    Placa       TEXT(10)  NOT NULL,
    Marca       TEXT(50),
    Cor         TEXT(30),
    HoraEntrada INTEGER,
    HoraSaida   INTEGER,
    CONSTRAINT PK_Carro PRIMARY KEY (Placa)
);
```

Ou execute o arquivo `sql/CriarTabela.sql`.

### 3. Inserir os cinco carros de teste

```sql
INSERT INTO Carro VALUES ('ABC-1234', 'Toyota',     'Prata',  7,  18);
INSERT INTO Carro VALUES ('DEF-5678', 'Honda',      'Preto',  8,  17);
INSERT INTO Carro VALUES ('GHI-9012', 'Ford',       'Branco', 9,  19);
INSERT INTO Carro VALUES ('JKL-3456', 'Chevrolet',  'Azul',   7,  16);
INSERT INTO Carro VALUES ('MNO-7890', 'Volkswagen', 'Cinza',  8,  18);
```

---

## Configuração do Driver JDBC — UCanAccess

Baixe os JARs em: https://ucanaccess.sourceforge.net/site.html

Adicione ao **classpath** do projeto:

```
ucanaccess-5.0.1.jar
lib/commons-lang3-3.8.1.jar
lib/commons-logging-1.2.jar
lib/hsqldb-2.5.0.jar
lib/jackcess-4.0.1.jar
```

No **Eclipse/IntelliJ**: clique com o botão direito no projeto →
Build Path / Module Settings → Add JARs → selecione todos os JARs acima.

---

## Compilação e Execução

### Via linha de comando

```bash
# Compilar (ajuste o separador de classpath: ; no Windows, : no Linux/Mac)
javac -cp ".;ucanaccess-5.0.1.jar;lib/*" -d bin src/model/Carro.java src/dao/Conexao.java src/dao/CarroDAO.java src/view/TelaPrincipal.java

# Executar
java -cp ".;bin;ucanaccess-5.0.1.jar;lib/*" view.TelaPrincipal
```

### Via IDE (Eclipse / IntelliJ)

1. Importe o projeto.
2. Adicione os JARs ao classpath (veja acima).
3. Execute a classe `view.TelaPrincipal`.

---

## Funcionalidades da Interface

| Operação | Como realizar |
|----------|--------------|
| **Incluir** | Preencha todos os campos e clique em ➕ Incluir |
| **Buscar**  | Digite a placa e clique em 🔍 Buscar |
| **Alterar** | Busque o veículo, edite os campos e clique em ✏ Alterar |
| **Excluir** | Busque o veículo pela placa e clique em 🗑 Excluir |
| **Relatório** | Clique na aba 📋 Relatório de Carros |
| **Atualizar lista** | Clique em 🔄 Atualizar Lista na aba de relatório |

> **Dica:** Clique em qualquer linha da tabela de relatório para carregar automaticamente os dados no formulário.

---

## Observações

- A **Placa** é a chave primária e não pode ser alterada após o cadastro.
- **HoraEntrada** e **HoraSaida** aceitam valores inteiros de **0 a 23** (formato 24h).
- A string de conexão em `Conexao.java` pode ser ajustada para outro caminho:
  ```java
  private static final String URL =
      "jdbc:ucanaccess://C:/Estacionamento/Estacionamento.accdb";
  ```
