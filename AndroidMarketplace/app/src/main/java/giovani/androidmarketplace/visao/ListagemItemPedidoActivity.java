package giovani.androidmarketplace.visao;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.entidades.PedidoItem;
import giovani.androidmarketplace.dados.entidades.Produto;
import giovani.androidmarketplace.servico.ContextoAplicacao;
import giovani.androidmarketplace.utils.modelos.IListenerProcesso;
import giovani.androidmarketplace.visao.adaptadores.AdaptadorListagemItemPedido;

public class ListagemItemPedidoActivity extends AppCompatActivity {
    private ArrayList<PedidoItem> listaItens;
    private ArrayList<PedidoItem> listaItensRemovidos = new ArrayList<>();
    private List<Produto> produtosDisponiveis;

    private Integer idPedidoItemSendoEditado;
    private Integer idPedidoSendoEditado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_item_pedido);

        produtosDisponiveis = ContextoAplicacao.getContextoAplicacao().getCriadorDAOs().getProdutoDAO().getAllProdutos();

        lerParametrosPilhaActivity();
        prepararListeners();
        listarPedidoItens();
    }

    private void prepararListeners() {
        findViewById(R.id.inserirProdutoPedidoFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickInserirProdutoPedidoFAB((FloatingActionButton) view);
            }
        });
    }

    private void onClickInserirProdutoPedidoFAB(FloatingActionButton fab) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_inserir_item_pedido);

        List<String> listaSpinner = new ArrayList<>();

        for (Produto produto : produtosDisponiveis) {
            listaSpinner.add(produto.getDescricao());
        }

        Spinner spinner = findViewById(R.id.seletorProdutoSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaSpinner);
        spinner.setAdapter(adapter);

        findViewById(R.id.salvarItemButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText campoValorDesconto = findViewById(R.id.valorDescontoEditText);
                EditText campoPrecoVenda = findViewById(R.id.precoVendaEditText);

                PedidoItem item = new PedidoItem();
                item.setValorDesconto(new BigDecimal(campoValorDesconto.getText().toString()));
                item.setPrecoVenda(new BigDecimal(campoPrecoVenda.getText().toString()));
            }
        });

        findViewById(R.id.cancelarItemButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();

        getIntent().putExtra("listaItensPedido", listaItens);
    }

    private void lerParametrosPilhaActivity() {
        listaItens = (ArrayList<PedidoItem>) getIntent().getSerializableExtra("listaItensPedido");
        getIntent().putExtra("listaItensRemovidos", listaItensRemovidos);
    }

    private void listarPedidoItens() {
        RecyclerView recyclerView = findViewById(R.id.itemPedidoRecyclerView);
        recyclerView.setAdapter(new AdaptadorListagemItemPedido(this, listaItens, new IListenerProcesso<PedidoItem>() {
            @Override
            public void processarDado(PedidoItem dado) {
                listaItensRemovidos.add(dado);
                listaItens.remove(dado);

                getIntent().putExtra("listaItensPedido", listaItens);
                getIntent().putExtra("listaItensRemovidos", listaItensRemovidos);

                listarPedidoItens();
            }
        }));
    }
}
