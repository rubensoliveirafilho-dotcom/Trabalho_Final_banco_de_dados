package dao;

import java.sql.*;

public class MatriculaDAO {


    public void matricular(int idAluno, int idCurso) {
        String sqlPreco = "SELECT preco FROM Curso WHERE id_curso = ?";
        String sqlMat = "INSERT INTO Matricula (id_aluno, id_curso, status_matricula) VALUES (?, ?, 'Ativa')";
        String sqlPag = "INSERT INTO Pagamento (id_matricula, valor_pago, status_pagamento) VALUES (?, ?, 'Aprovado')";

        try (Connection conn = Conecxao.conectar()) {
            conn.setAutoCommit(false); // Transação ativa

            double preco = 0;
            try (PreparedStatement stmtP = conn.prepareStatement(sqlPreco)) {
                stmtP.setInt(1, idCurso);
                try (ResultSet rs = stmtP.executeQuery()) {
                    if (rs.next()) preco = rs.getDouble("preco");
                    else { conn.rollback(); return; }
                }
            }

            int idMatricula = -1;
            try (PreparedStatement stmtM = conn.prepareStatement(sqlMat, Statement.RETURN_GENERATED_KEYS)) {
                stmtM.setInt(1, idAluno);
                stmtM.setInt(2, idCurso);
                stmtM.executeUpdate();
                try (ResultSet rsK = stmtM.getGeneratedKeys()) {
                    if (rsK.next()) idMatricula = rsK.getInt(1);
                }
            }

            try (PreparedStatement stmtPag = conn.prepareStatement(sqlPag)) {
                stmtPag.setInt(1, idMatricula);
                stmtPag.setDouble(2, preco);
                stmtPag.executeUpdate();
            }

            conn.commit();
            System.out.println("[Sucesso] Aluno matriculado com sucesso no curso escolhido!");
        } catch (SQLException e) {
            System.err.println("Erro ao efetuar matrícula: " + e.getMessage());
        }
    }

    public void cancelarMatricula(int idMatricula) {
        String sqlDelPag = "DELETE FROM Pagamento WHERE id_matricula = ?";
        String sqlDelMat = "DELETE FROM Matricula WHERE id_matricula = ?";

        try (Connection conn = Conecxao.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtP = conn.prepareStatement(sqlDelPag)) {
                stmtP.setInt(1, idMatricula);
                stmtP.executeUpdate();
            }

            try (PreparedStatement stmtM = conn.prepareStatement(sqlDelMat)) {
                stmtM.setInt(1, idMatricula);
                int afetadas = stmtM.executeUpdate();
                if (afetadas > 0) {
                    System.out.println("[Sucesso] Matrícula cancelada e removida do sistema!");
                } else {
                    System.out.println("[Aviso] Matrícula de ID " + idMatricula + " não foi encontrada.");
                }
            }
            conn.commit();
        } catch (SQLException e) {
            System.err.println("Erro ao cancelar matrícula: " + e.getMessage());
        }
    }


    public void puxarMatriculas() {
        String sql = "SELECT m.id_matricula, a.nome AS aluno, c.titulo AS curso, m.status_matricula " +
                "FROM Matricula m " +
                "INNER JOIN Aluno a ON m.id_aluno = a.id_aluno " +
                "INNER JOIN Curso c ON m.id_curso = c.id_curso";

        try (Connection conn = Conecxao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("---------------------------------------------------------");
            System.out.printf("%-7s | %-20s | %-25s", "ID MAT", "ALUNO", "CURSO");
            System.out.println("---------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-7d | %-20s | %-25s",
                        rs.getInt("id_matricula"),
                        rs.getString("aluno"),
                        rs.getString("curso")
                );
            }
            System.out.println("---------------------------------------------------------");
        } catch (SQLException e) {
            System.err.println("Erro ao puxar dados de matrícula: " + e.getMessage());
        }
    }
}