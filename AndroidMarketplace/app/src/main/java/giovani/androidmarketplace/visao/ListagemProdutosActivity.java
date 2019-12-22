package giovani.androidmarketplace.visao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.entidades.Produto;
import giovani.androidmarketplace.exceptions.GerenciadorException;
import giovani.androidmarketplace.servico.ContextoAplicacao;
import giovani.androidmarketplace.utils.ActivityUtil;
import giovani.androidmarketplace.utils.modelos.IListenerProcesso;
import giovani.androidmarketplace.visao.adaptadores.AdaptadorListagemProdutos;

public class ListagemProdutosActivity extends AppCompatActivity {
    private static final int ACTIVITY_PRODUTOS_CODIGO_RESULTADO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_produtos);

        prepararViews();
        prepararListeners();
        listarProdutos();
    }

    private void prepararViews() {
        RecyclerView recyclerView = findViewById(R.id.listagemProdutosRecyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void prepararListeners() {
        findViewById(R.id.novoFloatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNovoProduto(v);
            }
        });

        findViewById(R.id.reloadFloatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarProdutos();
            }
        });
    }

    private void onClickNovoProduto(View v) {
        ActivityUtil.iniciarActivityAguardandoResultado(ListagemProdutosActivity.this,
                                EdicaoProdutoActivity.class, ACTIVITY_PRODUTOS_CODIGO_RESULTADO);
    }

    private void listarProdutos() {
        List<Produto> listaProdutos = ContextoAplicacao.getContextoAplicacao().getCriadorDAOs().getProdutoDAO().getAllProdutos();

        RecyclerView recyclerView = findViewById(R.id.listagemProdutosRecyclerView);

        recyclerView.setAdapter(new AdaptadorListagemProdutos(this, listaProdutos, new IListenerProcesso<Produto>() {
            @Override
            public void processarDado(Produto dado) {
                try {
                    ContextoAplicacao.getContextoAplicacao().getCriadorGerenciadores().getGerenciadorProduto().deletar(dado.getId());
                    listarProdutos();
                } catch (GerenciadorException e) {
                    e.printStackTrace();
                }
            }
        }, new IListenerProcesso<Produto>() {
            @Override
            public void processarDado(Produto dado) {
                Bundle dadosProduto = new Bundle();
                dadosProduto.putInt(Produto.COLUNA_IDPRODUTO, dado.getId());

                ActivityUtil.iniciarActivityAguardandoResultado(ListagemProdutosActivity.this,
                            EdicaoProdutoActivity.class, ACTIVITY_PRODUTOS_CODIGO_RESULTADO, dadosProduto);
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ACTIVITY_PRODUTOS_CODIGO_RESULTADO) {
            listarProdutos();
        }
    }
}


