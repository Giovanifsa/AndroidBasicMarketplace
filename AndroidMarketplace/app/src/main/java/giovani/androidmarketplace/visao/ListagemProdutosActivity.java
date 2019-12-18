package giovani.androidmarketplace.visao;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.utils.ActivityUtil;
import giovani.androidmarketplace.visao.adaptadores.AdaptadorListagemProdutos;

public class ListagemProdutosActivity extends AppCompatActivity {

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
    }

    private void onClickNovoProduto(View v) {
        ActivityUtil.iniciarActivity(this, EdicaoProdutoActivity.class);
    }

    private void listarProdutos() {
        RecyclerView recyclerView = findViewById(R.id.listagemProdutosRecyclerView);
        recyclerView.setAdapter(new AdaptadorListagemProdutos(this));
    }
}


