package br.com.alura.leilao.ui;

import android.support.v7.widget.RecyclerView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.database.dao.UsuarioDAO;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter;

@RunWith(MockitoJUnitRunner.class)
public class AtualizadorDeUsuarioTest {

    @Mock
    private UsuarioDAO dao;

    @Mock
    private ListaUsuarioAdapter adapter;

    @Mock
    private RecyclerView recyclerView;

    @Test
    public void deve_AtulizarListaDeUsuario_QuandoSalvarUsuario() {
        AtualizadorDeUsuario atualizadorDeUsuario = new AtualizadorDeUsuario(dao, adapter, recyclerView);

        Usuario chico = new Usuario("Chico");

        Mockito.when(dao.salva(chico)).thenReturn(new Usuario(1, "Chico"));
        Mockito.when(adapter.getItemCount()).thenReturn(1);
        atualizadorDeUsuario.salva(chico);
        Mockito.verify(dao).salva(new Usuario("Chico"));

        Mockito.verify(adapter).adiciona(new Usuario(1, "Chico"));
        Mockito.verify(recyclerView).smoothScrollToPosition(0);
    }

}