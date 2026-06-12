package model;

import java.util.Date;

public class RelatorioVendas {
    private int idMatricula;
    private String nomeAluno;
    private String nomeCurso;
    private double valorPago;
    private String statusPagamento;
    private Date dataMatricula;

    public RelatorioVendas() {}

    public int getIdMatricula() { return idMatricula; }
    public void setIdMatricula(int idMatricula) { this.idMatricula = idMatricula; }

    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }

    public String getNomeCurso() { return nomeCurso; }
    public void setNomeCurso(String nomeCurso) { this.nomeCurso = nomeCurso; }

    public double getValorPago() { return valorPago; }
    public void setValorPago(double valorPago) { this.valorPago = valorPago; }

    public String getStatusPagamento() { return statusPagamento; }
    public void setStatusPagamento(String statusPagamento) { this.statusPagamento = statusPagamento; }

    public Date getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(Date dataMatricula) { this.dataMatricula = dataMatricula; }
}