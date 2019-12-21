package giovani.androidmarketplace.visao;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
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
    private List<Produto> listaProdutosDialog;

    private boolean flagProcessandoAlteracoesValoresDialog = false;
    private PedidoItem pedidoItemSendoEditado;

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
        listaProdutosDialog = ContextoAplicacao.getContextoAplicacao().getCriadorDAOs().getProdutoDAO().getAllProdutos();
        List<String> listaSpinner = new ArrayList<>();

        for (Produto produto : listaProdutosDialog) {
            listaSpinner.add(produto.getDescricao());
        }

        Spinner spinner = dialog.findViewById(R.id.seletorProdutoSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaSpinner);
        spinner.setAdapter(adapter);
    }

    private void prepararListenersDialog(final Dialog dialog) {
        Spinner seletorProduto = dialog.findViewById(R.id.seletorProdutoSpinner);
        seletorProduto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recalcularValoresDialog(dialog, false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

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

        dialog.findViewById(R.id.quantidadeEditText).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    recalcularValoresDialog(dialog, false);
                }
            }
        });

        dialog.findViewById(R.id.precoVendaEditText).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    recalcularValoresDialog(dialog, false);
                }
            }
        });

        dialog.findViewById(R.id.valorDescontoEditText).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    recalcularValoresDialog(dialog, true);
                }
            }
        });
    }

    private void recalcularValoresDialog(final Dialog dialog, boolean basearCalculoSobreDesconto) {
        if (!flagProcessandoAlteracoesValoresDialog) {
            flagProcessandoAlteracoesValoresDialog = true;

            Spinner campoProduto = dialog.findViewById(R.id.seletorProdutoSpinner);

            int posProdutoSelecionado = campoProduto.getSelectedItemPosition();

            if (posProdutoSelecionado >= 0) {
                Produto produtoSelecionado = listaProdutosDialog.get(posProdutoSelecionado);

                EditText campoQuantidade = dialog.findViewById(R.id.quantidadeEditText);
                EditText campoPrecoVenda = dialog.findViewById(R.id.precoVendaEditText);
                EditText campoValorDesconto = dialog.findViewById(R.id.valorDescontoEditText);
                EditText campoPrecoOriginal = dialog.findViewById(R.id.precoOriginalEditText);
                EditText campoTotalItem = dialog.findViewById(R.id.totalItemEditText);

                BigDecimal precoProduto = produtoSelecionado.getPreco();
                BigDecimal quantidade = DecimalUtil.formatarDuasCasasDecimais(campoQuantidade.getText().toString());
                BigDecimal precoVenda = DecimalUtil.formatarDuasCasasDecimais(campoPrecoVenda.getText().toString());
                BigDecimal valorDesconto = DecimalUtil.formatarDuasCasasDecimais(campoValorDesconto.getText().toString());

                if (!basearCalculoSobreDesconto)
                    valorDesconto = precoProduto.subtract(precoVenda);

                else
                    precoVenda = precoProduto.subtract(valorDesconto);

                BigDecimal valorItem = precoVenda.multiply(quantidade);

                campoPrecoOriginal.setText(DecimalUtil.formatarDuasCasasDecimais(precoProduto));
                campoQuantidade.setText(DecimalUtil.formatarDuasCasasDecimais(quantidade));
                campoPrecoVenda.setText(DecimalUtil.formatarDuasCasasDecimais(precoVenda));
                campoValorDesconto.setText(DecimalUtil.formatarDuasCasasDecimais(valorDesconto));
                campoTotalItem.setText(DecimalUtil.formatarDuasCasasDecimais(valorItem));
            }

            flagProcessandoAlteracoesValoresDialog = false;
        }
    }

    private void dialogOnClickSalvarItem(Dialog dialog, Button button) {
        Spinner campoProduto = dialog.findViewById(R.id.seletorProdutoSpinner);
        EditText campoQuantidade = dialog.findViewById(R.id.quantidadeEditText);
        EditText campoPrecoVenda = dialog.findViewById(R.id.precoVendaEditText);

        int posProdutoSelecionado = campoProduto.getSelectedItemPosition();
        BigDecimal precoVenda = DecimalUtil.formatarDuasCasasDecimais(campoPrecoVenda.getText().toString());
        BigDecimal quantidade = DecimalUtil.formatarDuasCasasDecimais(campoQuantidade.getText().toString());

        Produto produtoSelecionado = listaProdutosDialog.get(posProdutoSelecionado);

        pedidoItemSendoEditado.setPrecoVenda(precoVenda);
        pedidoItemSendoEditado.setQuantidade(quantidade);
        pedidoItemSendoEditado.setIdProduto(produtoSelecionado.getId());

        //for ()

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
                listaItens.remove(dado);

                atualizarRetornoDados();
                listarPedidoItens();
            }
        }));
    }

    private void atualizarRetornoDados() {
        getIntent().putExtra("listaItensPedido", listaItens);
    }
}
