package dao;

import model.Aluno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    // CREATE - Inserir Aluno
    public void cadastrar(Aluno aluno) {
        String sql = "INSERT INTO Aluno (nome, email, cpf) VALUES (?, ?, ?)";

        try (Connection conn = Conecxao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getCpf());
            stmt.executeUpdate();
            System.out.println("[Sucesso] Aluno " + aluno.getNome() + " cadastrado no banco.");

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar aluno: " + e.getMessage());
        }
    }

    // READ - Listar Alunos
    public List<Aluno> listarTodos() {
        String sql = "SELECT * FROM Aluno";
        List<Aluno> lista = new ArrayList<>();

        try (Connection conn = Conecxao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setIdAluno(rs.getInt("id_aluno"));
                aluno.setNome(rs.getString("nome"));
                aluno.setEmail(rs.getString("email"));
                aluno.setCpf(rs.getString("cpf"));
                lista.add(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
        }
        return lista;
    }


    public void atualizar(Aluno aluno) {
        String sql = "UPDATE Aluno SET nome = ?, email = ?, cpf = ? WHERE id_aluno = ?";

        try (Connection conn = Conecxao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getCpf());
            stmt.setInt(4, aluno.getIdAluno());
            stmt.executeUpdate();
            System.out.println("[Sucesso] Dados do aluno atualizados.");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
        }
    }


    public void excluir(int id) {
        String sql = "DELETE FROM Aluno WHERE id_aluno = ?";

        try (Connection conn = Conecxao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("[Sucesso] Aluno com ID " + id + " removido.");

        } catch (SQLException e) {
            System.err.println("Erro ao excluir aluno: " + e.getMessage());
        }
    }
}