package model;

public class Curso {
    private int idCurso;
    private String titulo;
    private double preco;
    private int idInstrutor;

    public Curso() {}

    public Curso(String titulo, double preco, int idInstrutor) {
        this.titulo = titulo;
        this.preco = preco;
        this.idInstrutor = idInstrutor;
    }

    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getIdInstrutor() { return idInstrutor; }
    public void setIdInstrutor(int idInstrutor) { this.idInstrutor = idInstrutor; }
}