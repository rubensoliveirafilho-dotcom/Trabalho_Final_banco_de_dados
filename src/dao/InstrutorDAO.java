package dao;

import model.Instrutor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstrutorDAO {

    public void cadastrar(Instrutor instrutor) {
        String sql = "INSERT INTO Instrutor (nome, especialidade, email) VALUES (?, ?, ?)";
        try (Connection conn = Conecxao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, instrutor.getNome());
            stmt.setString(2, instrutor.getEspecialidade());
            stmt.setString(3, instrutor.getEmail());
            stmt.executeUpdate();
            System.out.println("[Sucesso] Instrutor cadastrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar instrutor: " + e.getMessage());
        }
    }

    public List<Instrutor> listarTodos() {
        String sql = "SELECT * FROM Instrutor";
        List<Instrutor> lista = new ArrayList<>();
        try (Connection conn = Conecxao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Instrutor i = new Instrutor();
                i.setIdInstrutor(rs.getInt("id_instrutor"));
                i.setNome(rs.getString("nome"));
                i.setEspecialidade(rs.getString("especialidade"));
                i.setEmail(rs.getString("email"));
                lista.add(i);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar instrutores: " + e.getMessage());
        }
        return lista;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM Instrutor WHERE id_instrutor = ?";
        try (Connection conn = Conecxao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("[Sucesso] Instrutor removido do sistema.");
        } catch (SQLException e) {
            System.err.println("Erro ao excluir instrutor (Verifique se ele possui cursos vinculados): " + e.getMessage());
        }
    }
}