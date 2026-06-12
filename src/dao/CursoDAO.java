package dao;

import model.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public List<Curso> listarTodos() {
        String sql = "SELECT * FROM Curso";
        List<Curso> lista = new ArrayList<>();
        try (Connection conn = Conecxao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Curso curso = new Curso();
                curso.setIdCurso(rs.getInt("id_curso"));
                curso.setTitulo(rs.getString("titulo"));
                curso.setPreco(rs.getDouble("preco"));
                curso.setIdInstrutor(rs.getInt("id_instrutor"));
                lista.add(curso);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar cursos: " + e.getMessage());
        }
        return lista;
    }


    public void atribuirInstrutor(int idCurso, int idInstrutor) {
        String sql = "UPDATE Curso SET id_instrutor = ? WHERE id_curso = ?";
        try (Connection conn = Conecxao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idInstrutor);
            stmt.setInt(2, idCurso);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("[Sucesso] Instrutor vinculado ao curso com sucesso!");
            } else {
                System.out.println("[Aviso] Curso ou Instrutor não encontrado.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao vincular instrutor ao curso: " + e.getMessage());
        }
    }
}