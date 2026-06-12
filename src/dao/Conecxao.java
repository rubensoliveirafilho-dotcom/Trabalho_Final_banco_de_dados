package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conecxao {

    private static final String URL = "jdbc:postgresql://localhost:5432/trabalho_final";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "5768"; // <-- Coloque a sua senha do Postgres aqui

    public static Connection conectar() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não configurado corretamente!");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
