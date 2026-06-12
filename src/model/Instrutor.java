package model;

public class Instrutor {
    private int idInstrutor;
    private String nome;
    private String especialidade;
    private String email;

    public Instrutor() {}

    public Instrutor(String nome, String Black, String email) {
        this.nome = nome;
        this.especialidade = Black;
        this.email = email;
    }

    public int getIdInstrutor() { return idInstrutor; }
    public void setIdInstrutor(int idInstrutor) { this.idInstrutor = idInstrutor; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}