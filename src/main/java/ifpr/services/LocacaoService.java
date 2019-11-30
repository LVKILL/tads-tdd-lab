package ifpr.services;

import ifpr.exceptions.NaoPodeTerEstoqueVazioException;
import ifpr.models.Filme;
import ifpr.models.Locacao;
import ifpr.models.Usuario;
import ifpr.utils.DataUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocacaoService {

    public Locacao alugarFilme(Usuario usuario, ArrayList<Filme> filmes) throws Exception {

        Locacao locacao = new Locacao();
        locacao.setUsuario(usuario);
        Double valor = 0.0;
        //A iteração abaixo irá remover da lista aqueles filmes que não estão disponíveis em estoque
        for(int i = 0; i < filmes.size(); i++) {
            Filme filme = filmes.get(i);
            valor += filme.getPreco();
            if (filme.getEstoque() == 0) {
                filmes.remove(filme);
                throw new Exception("filme sem estoque");
            }
        }
        Date dataSabatica = new Date();

        //persistir
        locacao.setFilme(filmes);
        locacao.setDataLocacao(new Date());
        locacao.setDataDevolucao(DataUtils.adicionarDias(new Date(), 1));

        if(DataUtils.verificaDormindo(locacao.getDataDevolucao())){
            locacao.setDataDevolucao(DataUtils.adicionarDias(new Date(), 2));
            throw new Exception("Doomingo ta fechado");
        }

        locacao.setValor(valor);

        return locacao;
    }

    public ArrayList<Filme> aplicarDesconto(ArrayList<Filme> filmes)throws Exception{

        if(filmes.size() >= 3) {

            for (int i = 2; i < filmes.size(); i++) {
                Filme filme = filmes.get(i);

                if(i == 2){
                    double novoValor = filme.getPreco() - (0.25 * filme.getPreco());
                    filme.aplicarDesconto(novoValor);
                }
                if(i == 3){
                    double novoValor = filme.getPreco() - (0.5 * filme.getPreco());
                    filme.aplicarDesconto(novoValor);
                }
                if(i == 4){
                    double novoValor = filme.getPreco() - (0.75 * filme.getPreco());
                    filme.aplicarDesconto(novoValor);
                }
                if(i == 5){
                    double novoValor = filme.getPreco() - filme.getPreco();
                    filme.aplicarDesconto(novoValor);
                }

            }

        }else{
            throw new Exception("Não possu filmes o suficiente para aplicar desconto.");
        }
        return filmes;
    }

}
