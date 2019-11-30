package ifpr.services;

import ifpr.models.Filme;
import ifpr.models.Locacao;
import ifpr.models.Usuario;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Date;

import static ifpr.utils.DataUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class LocacaoServiceTest {
    Usuario usuario;
    LocacaoService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException expected;

    @Before
    public void setup(){
        /*é executado antes de cada método de teste*/
        System.out.println("before");
        usuario = new Usuario("Usuario1");
        service = new LocacaoService();
    }

    @After
    public void tearDown(){
        /*é executado após de cada método de teste*/
        System.out.println("after");
    }

    @Test
    public void testeLocacao(){

        ArrayList<Filme> filmes = new ArrayList<>();
        //cenario
        Filme filme = new Filme("Filme 1", 10.0, 2);
        Filme filme2 = new Filme("Filme 2", 10.0, 2);
        Filme filme3 = new Filme("Filme 3", 10.0, 2);
        Filme filme4 = new Filme("Filme 4", 10.0, 2);
        Filme filme5 = new Filme("Filme 5", 10.0, 2);
        Filme filme6 = new Filme("Filme 6", 10.0, 2);

        filmes.add(filme);
        filmes.add(filme2);
        filmes.add(filme3);
        filmes.add(filme4);
        filmes.add(filme5);
        filmes.add(filme6);

        //acao
        Locacao locacao = null;
        try {
            locacao = service.alugarFilme(usuario, filmes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //verificação
        error.checkThat(locacao.getValor(), is(5.0));
        error.checkThat(locacao.getValor(), is(not(99.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataDevolucao(), adicionarDias(new Date(), 1)), is(true));
        error.checkThat(verificaDormindo(locacao.getDataDevolucao()), CoreMatchers.equalTo(true));
    }

    @Test
    public void naoPodeAlugarFilmeSemEstoque(){

        //cenario
        Usuario usuario = new Usuario("usuario 1");
        ArrayList<Filme> filmes = new ArrayList<>();

        Filme filme = new Filme("Filme 1", 10.0, 2);
        Filme filme2 = new Filme("Filme 2", 10.0, 2);
        Filme filme3 = new Filme("Filme 3", 10.0, 2);
        Filme filme4 = new Filme("Filme 4", 10.0, 2);
        Filme filme5 = new Filme("Filme 5", 10.0, 2);
        Filme filme6 = new Filme("Filme 6", 10.0, 2);

        filmes.add(filme);
        filmes.add(filme2);
        filmes.add(filme3);
        filmes.add(filme4);
        filmes.add(filme5);
        filmes.add(filme6);

        //acao
        try {
            service.alugarFilme(usuario, filmes);
            fail("deveria ter lançado uma excecao");
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("filme sem estoque"));
        }
    }

    @Test(expected = Exception.class)
    public void naoPodeAlugarFilmeSemEstoque2() throws Exception {

        //cenario
        Usuario usuario = new Usuario("usuario 1");
        ArrayList<Filme> filmes = new ArrayList<>();

        Filme filme = new Filme("Filme 1", 10.0, 2);
        Filme filme2 = new Filme("Filme 2", 10.0, 2);
        Filme filme3 = new Filme("Filme 3", 10.0, 2);
        Filme filme4 = new Filme("Filme 4", 10.0, 2);
        Filme filme5 = new Filme("Filme 5", 10.0, 2);
        Filme filme6 = new Filme("Filme 6", 10.0, 2);

        filmes.add(filme);
        filmes.add(filme2);
        filmes.add(filme3);
        filmes.add(filme4);
        filmes.add(filme5);
        filmes.add(filme6);
        //acao
        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void naoPodeAlugarFilmeSemEstoque3() throws Exception {

        //cenario
        Usuario usuario = new Usuario("usuario 1");
        ArrayList<Filme> filmes = new ArrayList<>();

        Filme filme = new Filme("Filme 1", 10.0, 2);
        Filme filme2 = new Filme("Filme 2", 10.0, 2);
        Filme filme3 = new Filme("Filme 3", 10.0, 2);
        Filme filme4 = new Filme("Filme 4", 10.0, 2);
        Filme filme5 = new Filme("Filme 5", 10.0, 2);
        Filme filme6 = new Filme("Filme 6", 10.0, 2);

        filmes.add(filme);
        filmes.add(filme2);
        filmes.add(filme3);
        filmes.add(filme4);
        filmes.add(filme5);
        filmes.add(filme6);

        expected.expect(Exception.class);
        expected.expectMessage("filme sem estoque");

        //acao
        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void aplicarDescontos() throws Exception{
        Usuario usuario = new Usuario("usuario 1");
        ArrayList<Filme> filmes = new ArrayList<>();

        Filme filme = new Filme("Filme 1", 10.0, 2);
        Filme filme2 = new Filme("Filme 2", 10.0, 2);
        Filme filme3 = new Filme("Filme 3", 10.0, 2);
        Filme filme4 = new Filme("Filme 4", 10.0, 2);
        Filme filme5 = new Filme("Filme 5", 10.0, 2);
        Filme filme6 = new Filme("Filme 6", 10.0, 2);

        filmes.add(filme);
        filmes.add(filme2);
        filmes.add(filme3);
        filmes.add(filme4);
        filmes.add(filme5);
        filmes.add(filme6);

        try {
            filmes = service.aplicarDesconto(filmes);
            fail("deveria ter lançado uma excecao");
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Não possu filmes o suficiente porra. Ja falei. Sai da minha loja."));
        }finally{
            for(int i = 0; i < filmes.size(); i++) {
                System.out.println("Filme = "+filmes.get(i).getNome());
                System.out.println("PrRF = "+filmes.get(i).getPreco());
            }
        }


    }
}
