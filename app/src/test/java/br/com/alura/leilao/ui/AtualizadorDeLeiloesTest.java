package br.com.alura.leilao.ui;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class AtualizadorDeLeiloesTest {

    @Mock
    private LeilaoWebClient client;

    @Mock
    private ListaLeilaoAdapter adapter;

    @Mock
    private AtualizadorDeLeiloes.ErroCarregaLeiloesListener listener;


    @Test
    public void deve_AtualizarListaDeLeiloes_QuandoBuscarLeiloesDaApi() throws InterruptedException {
        AtualizadorDeLeiloes atualizador = new AtualizadorDeLeiloes();
        // Mockito.doNothing().when(adapter).atualizaLista();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                //o index é a posição do argumento recebido
                RespostaListener<List<Leilao>> argument = invocation.getArgument(0);
                argument.sucesso(new ArrayList<Leilao>(Arrays.asList(
                        new Leilao("cheesecake"),
                        new Leilao("Banoffe")
                )));
                return null;
            }
        }).when(client).todos(any(RespostaListener.class));

        atualizador.buscaLeiloes(adapter, client, listener);
        Thread.sleep(2000);
        int quantidadeDeLeiloesDevolvidas = adapter
                .getItemCount();

        verify(client).todos(any(RespostaListener.class));
        verify(adapter).atualiza(new ArrayList<Leilao>(Arrays.asList(
                new Leilao("cheesecake"),
                new Leilao("Banoffe")
        )));

    }

    @Test
    public void deve_ApresentarMensagemDeFalha_QuandoFalharBuscaDeLEiloes() {

        AtualizadorDeLeiloes atualizador = new AtualizadorDeLeiloes();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                RespostaListener<List<Leilao>> argument = invocation.getArgument(0);
                argument.falha(Mockito.anyString());
                return null;
            }
        }).when(client).todos(any(RespostaListener.class));

        atualizador.buscaLeiloes(adapter, client, listener);

        verify(listener).erroAoCarregar(Mockito.anyString());

    }

}