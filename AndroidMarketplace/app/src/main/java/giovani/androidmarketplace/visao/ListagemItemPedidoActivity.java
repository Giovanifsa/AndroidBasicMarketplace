package giovani.androidmarketplace.visao;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.entidades.PedidoItem;
import giovani.androidmarketplace.dados.entidades.Produto;
import giovani.androidmarketplace.servico.ContextoAplicacao;
import giovani.androidmarketplace.utils.DecimalUtil;
import giovani.androidmarketplace.utils.modelos.IListenerProcesso;
import giovani.androidmarketplace.visao.adaptadores.AdaptadorListagemItemPedido;

public class ListagemItemPedidoActivity extends AppCompatActivity {
    private ArrayList<PedidoItem> listaItens;
    private ArrayList<PedidoItem> listaItensRemovidos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_item_pedido);

        prepararViews();
        prepararListeners();
        lerParametrosPilhaActivity();
        listarPedidoItens();
    }

    private void prepararViews() {
        RecyclerView recyclerView = findViewById(R.id.itemPedidoRecyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_inserir_item_pedido);

        prepararListenersDialog(dialog);
        prepararDadosDialog(dialog);

        dialog.show();
    }

    private void prepararDadosDialog(Dialog dialog) {
        List<String> listaSpinner = new ArrayList<>();

        for (Produto produto : ContextoAplicacao.getContextoAplicacao().getCriadorDAOs().getProdutoDAO().getAllProdutos()) {
            listaSpinner.add(produto.getDescricao());
        }

        Spinner spinner = dialog.findViewById(R.id.seletorProdutoSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaSpinner);
        spinner.setAdapter(adapter);
    }

    private void prepararListenersDialog(final Dialog dialog) {
        dialog.findViewById(R.id.salvarItemButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOnClickSalvarItem(dialog, (Button) v);
            }
        });

        dialog.findViewById(R.id.cancelarItemButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void dialogOnClickSalvarItem(Dialog dialog, Button button) {
        EditText campoValorDesconto = dialog.findViewById(R.id.valorDescontoEditText);
        EditText campoPrecoVenda = dialog.findViewById(R.id.precoVendaEditText);
        EditText campoQuantidade = dialog.findViewById(R.id.quantidadeEditText);

        BigDecimal valorDesconto = DecimalUtil.formatarDuasCasasDecimais(campoValorDesconto.getText().toString());
        BigDecimal precoVenda = DecimalUtil.formatarDuasCasasDecimais(campoPrecoVenda.getText().toString());
        BigDecimal quantidade = DecimalUtil.formatarDuasCasasDecimais(campoQuantidade.getText().toString());

        dialog.cancel();
    }

    private void lerParametrosPilhaActivity() {
        listaItens = (ArrayList<PedidoItem>) getIntent().getSerializableExtra("listaItensPedido");

        if (listaItens == null) {
            listaItens = new ArrayList<>();
        }
    }

    private void listarPedidoItens() {
        RecyclerView recyclerView = findViewById(R.id.itemPedidoRecyclerView);
        recyclerView.setAdapter(new AdaptadorListagemItemPedido(this, listaItens, new IListenerProcesso<PedidoItem>() {
            @Override
            public void processarDado(PedidoItem dado) {
                listaItensRemovidos.add(dado);
                listaItens.remove(dado);

                atualizarRetornoDados();
                listarPedidoItens();
            }
        }));
    }

    private void atualizarRetornoDados() {
        getIntent().putExtra("listaItensPedido", listaItens);
        getIntent().putExtra("listaItensRemovidos", listaItensRemovidos);
    }
}
