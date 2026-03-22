package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private static final String URL =
            "jdbc:h2:file:C:/Estacionamento/Estacionamento;AUTO_SERVER=TRUE";
    private static final String USUARIO = "sa";
    private static final String SENHA   = "";

    private static Connection conexao = null;

    public static Connection getConexao() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            try {
                Class.forName("org.h2.Driver");
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
                criarTabelaSeNaoExistir();
                System.out.println("Conexão H2 estabelecida.");
            } catch (ClassNotFoundException e) {
                throw new SQLException(
                    "Driver H2 não encontrado. Adicione h2-*.jar ao classpath.\n" + e.getMessage());
            }
        }
        return conexao;
    }

    private static void criarTabelaSeNaoExistir() throws SQLException {
        String sql =
            "CREATE TABLE IF NOT EXISTS Carro (" +
            "  Placa       VARCHAR(10)  NOT NULL PRIMARY KEY," +
            "  Marca       VARCHAR(50)," +
            "  Cor         VARCHAR(30)," +
            "  HoraEntrada INT," +
            "  HoraSaida   INT" +
            ")";
        try (Statement stmt = conexao.createStatement()) {
            stmt.execute(sql);
        }
    }

    public static void fecharConexao() {
        if (conexao != null) {
            try { conexao.close(); conexao = null; }
            catch (SQLException e) { System.err.println("Erro ao fechar: " + e.getMessage()); }
        }
    }
}