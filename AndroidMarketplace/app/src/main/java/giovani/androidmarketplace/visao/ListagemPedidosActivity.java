package giovani.androidmarketplace.visao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
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
    private int ACTIVITY_CODIGO_RETORNO_EDICAO_PEDIDO = 1;

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
        ActivityUtil.iniciarActivityAguardandoResultado(ListagemPedidosActivity.this, EdicaoPedidoActivity.class, ACTIVITY_CODIGO_RETORNO_EDICAO_PEDIDO);
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
        }, new IListenerProcesso<Pedido>() {
            @Override
            public void processarDado(Pedido dado) {
                Bundle dadosPedido = new Bundle();
                dadosPedido.putInt(Pedido.COLUNA_IDPEDIDO, dado.getId());

                ActivityUtil.iniciarActivityAguardandoResultado(ListagemPedidosActivity.this, EdicaoPedidoActivity.class,
                                                                                ACTIVITY_CODIGO_RETORNO_EDICAO_PEDIDO, dadosPedido);
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ACTIVITY_CODIGO_RETORNO_EDICAO_PEDIDO) {
            listarPedidos();
        }
    }

    @Override
    protected void onDestroy() {
        setResult(RESULT_OK);
        super.onDestroy();
    }
}
