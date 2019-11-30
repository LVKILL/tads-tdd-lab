package ifpr.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Locacao {

    private ArrayList<Filme> filme;
    private Usuario usuario;
    private Date dataLocacao;
    private Date dataDevolucao;
    private Double valor;

    public Locacao(){}

    public Locacao(ArrayList<Filme> filmes, Usuario usuario, Date dataLocacao, Date dataDevolucao, Double valor) {
        this.filme = filmes;
        this.usuario = usuario;
        this.dataLocacao = dataLocacao;
        this.dataDevolucao = dataDevolucao;
        this.valor = valor;
    }

    public ArrayList<Filme> getFilme() {
        return filme;
    }

    public void setFilme(ArrayList<Filme> filme) {
        this.filme = filme;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataLocacao() {
        return dataLocacao;
    }

    public void setDataLocacao(Date dataLocacao) {
        this.dataLocacao = dataLocacao;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Double getValor() {
        for(int i = 0; i<filme.size(); i++){
            valor += filme.get(i).getPreco();
        }
        return this.valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
