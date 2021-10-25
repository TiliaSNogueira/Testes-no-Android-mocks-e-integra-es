package br.com.alura.leilao.ui.recyclerview.adapter;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.alura.leilao.model.Leilao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ListaLeilaoAdapterTest {

    //injeção de dependencia com o Mockito (mock e spy)

    @Mock
    private Context contextMock;
    @Spy
    private ListaLeilaoAdapter adapter = new ListaLeilaoAdapter(contextMock);

    @Test
    public void deve_AtualizarListaLeiloes_QuandoReceberListaDeLeiloes() {
        //diz que vai usar a injeção
        MockitoAnnotations.initMocks(this);

        doNothing().when(adapter).atualizaLista();
        adapter.atualiza(new ArrayList<>(Arrays.asList(
                new Leilao("Bolo"),
                new Leilao("Mil folhas")
        )));

        int quantidadeDeLeiloesDevolvida = adapter.getItemCount();

        verify(adapter).atualizaLista();

        assertThat(quantidadeDeLeiloesDevolvida, is(2));
    }


}