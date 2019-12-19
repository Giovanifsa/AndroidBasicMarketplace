package giovani.androidmarketplace.visao;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.entidades.Pedido;
import giovani.androidmarketplace.exceptions.GerenciadorException;
import giovani.androidmarketplace.servico.ContextoAplicacao;
import giovani.androidmarketplace.utils.ActivityUtil;
import giovani.androidmarketplace.utils.modelos.IListenerProcesso;
import giovani.androidmarketplace.visao.adaptadores.AdaptadorListagemPedidos;

public class ListagemPedidosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_pedidos);

        prepararViews();
        prepararListeners();
        listarPedidos();
    }

    private void prepararViews() {
        RecyclerView recyclerView = findViewById(R.id.pedidosRecyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void prepararListeners() {
        FloatingActionButton fab = findViewById(R.id.novoPedidoFAB);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNovoPedidoFAB((FloatingActionButton) v);
            }
        });

        findViewById(R.id.reloadPedidosFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarPedidos();
            }
        });
    }

    private void onClickNovoPedidoFAB(FloatingActionButton v) {
        ActivityUtil.iniciarActivity(this, EdicaoPedidoActivity.class);
    }

    private void listarPedidos() {
        List<Pedido> listaPedidos = ContextoAplicacao.getContextoAplicacao().getCriadorDAOs().getPedidoDAO().getAllPedidos();

        RecyclerView recyclerView = findViewById(R.id.pedidosRecyclerView);

        recyclerView.setAdapter(new AdaptadorListagemPedidos(this, listaPedidos, new IListenerProcesso<Pedido>() {
            @Override
            public void processarDado(Pedido dado) {
                try {
                    ContextoAplicacao.getContextoAplicacao().getCriadorGerenciadores().getGerenciadorPedido().deletar(dado.getId());
                    listarPedidos();
                } catch (GerenciadorException e) {
                    e.printStackTrace();
                }
            }
        }));
    }
}
