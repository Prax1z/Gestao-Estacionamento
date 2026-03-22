package dao;

import model.Carro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CarroDAO {

    public boolean incluir(Carro carro) {
        String sql = "INSERT INTO Carro (Placa, Marca, Cor, HoraEntrada, HoraSaida) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, carro.getPlaca());
            ps.setString(2, carro.getMarca());
            ps.setString(3, carro.getCor());
            ps.setInt(4, carro.getHoraEntrada());
            ps.setInt(5, carro.getHoraSaida());

            int linhas = ps.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao incluir carro: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Carro carro) {
        String sql = "UPDATE Carro SET Marca = ?, Cor = ?, HoraEntrada = ?, HoraSaida = ? "
                   + "WHERE Placa = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, carro.getMarca());
            ps.setString(2, carro.getCor());
            ps.setInt(3, carro.getHoraEntrada());
            ps.setInt(4, carro.getHoraSaida());
            ps.setString(5, carro.getPlaca());

            int linhas = ps.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao alterar carro: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(String placa) {
        String sql = "DELETE FROM Carro WHERE Placa = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, placa);
            int linhas = ps.executeUpdate();
            return linhas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir carro: " + e.getMessage());
            return false;
        }
    }

    public Carro buscarPorPlaca(String placa) {
        String sql = "SELECT * FROM Carro WHERE Placa = ?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, placa);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearResultSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar carro: " + e.getMessage());
        }
        return null;
    }

    public List<Carro> listarTodos() {
        List<Carro> lista = new ArrayList<>();
        String sql = "SELECT * FROM Carro ORDER BY Placa";

        try (Connection con = Conexao.getConexao();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar carros: " + e.getMessage());
        }
        return lista;
    }

    private Carro mapearResultSet(ResultSet rs) throws SQLException {
        Carro c = new Carro();
        c.setPlaca(rs.getString("Placa"));
        c.setMarca(rs.getString("Marca"));
        c.setCor(rs.getString("Cor"));
        c.setHoraEntrada(rs.getInt("HoraEntrada"));
        c.setHoraSaida(rs.getInt("HoraSaida"));
        return c;
    }
}
