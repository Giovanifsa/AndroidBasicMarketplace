package giovani.androidmarketplace.visao;

import android.app.Dialog;
import android.content.Intent;
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
import giovani.androidmarketplace.dados.entidades.Pedido;
import giovani.androidmarketplace.dados.entidades.PedidoItem;
import giovani.androidmarketplace.dados.entidades.Produto;
import giovani.androidmarketplace.exceptions.ActivityException;
import giovani.androidmarketplace.servico.ContextoAplicacao;
import giovani.androidmarketplace.utils.ActivityUtil;
import giovani.androidmarketplace.utils.DecimalUtil;
import giovani.androidmarketplace.utils.modelos.IListenerProcesso;
import giovani.androidmarketplace.visao.adaptadores.AdaptadorListagemItemPedido;

public class ListagemItemPedidoActivity extends AppCompatActivity {
    private ArrayList<PedidoItem> listaItens;
    private List<Produto> listaProdutosDialog;

    private boolean flagProcessandoAlteracoesValoresDialog = false;
    private int posicaoListaPedidoSendoEditado = -1;

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
        mostrarDialogInsercaoAlteracao();
    }

    private void mostrarDialogInsercaoAlteracao() {
        Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_inserir_item_pedido);

        prepararListenersDialog(dialog);
        prepararDadosDialog(dialog);

        if (isEstadoEdicao()) {
            PedidoItem pedidoItem = listaItens.get(posicaoListaPedidoSendoEditado);
            popularDialogAPartirDoPedidoItem(dialog, pedidoItem);
        }


        dialog.show();
    }

    private void popularDialogAPartirDoPedidoItem(Dialog dialog, PedidoItem item) {
        Spinner seletorProduto = dialog.findViewById(R.id.seletorProdutoSpinner);
        EditText campoQuantidade = dialog.findViewById(R.id.quantidadeEditText);
        EditText campoPrecoVenda = dialog.findViewById(R.id.precoVendaEditText);
        EditText campoValorDesconto = dialog.findViewById(R.id.valorDescontoEditText);
        EditText campoPrecoOriginal = dialog.findViewById(R.id.precoOriginalEditText);
        EditText campoTotalItem = dialog.findViewById(R.id.totalItemEditText);

        campoQuantidade.setText(DecimalUtil.formatarDuasCasasDecimais(item.getQuantidade()));
        campoPrecoVenda.setText(DecimalUtil.formatarDuasCasasDecimais(item.getPrecoVenda()));
        campoValorDesconto.setText(DecimalUtil.formatarDuasCasasDecimais(item.getValorDesconto()));
        campoPrecoOriginal.setText(DecimalUtil.formatarDuasCasasDecimais(item.getPrecoOriginal()));
        campoTotalItem.setText(DecimalUtil.formatarDuasCasasDecimais(item.getPrecoVenda().multiply(item.getQuantidade())));

        Produto produto = ContextoAplicacao.getContextoAplicacao().getCriadorDAOs().getProdutoDAO().buscar(item.getIdProduto());
        seletorProduto.setSelection(listaProdutosDialog.indexOf(produto));
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
                dialogOnClickCancelar(dialog, (Button) v);
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
        try {
            PedidoItem pedidoItem;

            if (isEstadoEdicao())
                pedidoItem = listaItens.get(posicaoListaPedidoSendoEditado);
            else
                pedidoItem = new PedidoItem();

            popularPedidoItemAPartirDialog(dialog, pedidoItem);

            realizarValidacoesAoSalvar(dialog);

            salvarPedidoItem(pedidoItem);

            dialog.cancel();
            finalizarEstadoDeEdicao();
            listarPedidoItens();
        } catch (ActivityException ex) {
            ActivityUtil.exibirDialogMensagem(this, R.string.palavra_alerta, ex.getMessage(), R.string.palavra_aceitar_alerta);
        }
    }

    private void dialogOnClickCancelar(Dialog dialog, Button button) {
        dialog.cancel();
        finalizarEstadoDeEdicao();
        listarPedidoItens();
    }

    private void popularPedidoItemAPartirDialog(Dialog dialog, PedidoItem pedidoItem) {
        Spinner campoProduto = dialog.findViewById(R.id.seletorProdutoSpinner);
        EditText campoQuantidade = dialog.findViewById(R.id.quantidadeEditText);
        EditText campoPrecoVenda = dialog.findViewById(R.id.precoVendaEditText);
        EditText campoValorDesconto = dialog.findViewById(R.id.valorDescontoEditText);
        EditText campoPrecoOriginal = dialog.findViewById(R.id.precoOriginalEditText);

        int posProdutoSelecionado = campoProduto.getSelectedItemPosition();
        BigDecimal precoVenda = DecimalUtil.formatarDuasCasasDecimais(campoPrecoVenda.getText().toString());
        BigDecimal quantidade = DecimalUtil.formatarDuasCasasDecimais(campoQuantidade.getText().toString());
        BigDecimal valorDesconto = DecimalUtil.formatarDuasCasasDecimais(campoValorDesconto.getText().toString());
        BigDecimal precoOriginal = DecimalUtil.formatarDuasCasasDecimais(campoPrecoOriginal.getText().toString());

        Produto produtoSelecionado = listaProdutosDialog.get(posProdutoSelecionado);

        pedidoItem.setPrecoVenda(precoVenda);
        pedidoItem.setQuantidade(quantidade);
        pedidoItem.setIdProduto(produtoSelecionado.getId());
        pedidoItem.setValorDesconto(valorDesconto);
        pedidoItem.setPrecoOriginal(precoOriginal);
    }

    private void salvarPedidoItem(PedidoItem pedidoItem) {
        if (!isEstadoEdicao())
            listaItens.add(pedidoItem);
    }

    private boolean isEstadoEdicao() {
        return posicaoListaPedidoSendoEditado >= 0;
    }

    private void finalizarEstadoDeEdicao() {
        posicaoListaPedidoSendoEditado = -1;
    }

    private void iniciarEstadoDeEdicao(int posicaoSendoEditada) {
        posicaoListaPedidoSendoEditado = posicaoSendoEditada;
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
                listarPedidoItens();
            }
        }, new IListenerProcesso<Integer>() {
            @Override
            public void processarDado(Integer dado) {
                posicaoListaPedidoSendoEditado = dado;
                mostrarDialogInsercaoAlteracao();
            }
        }));
    }

    @Override
    public void onBackPressed() {
        Intent intentRetorno = new Intent();
        intentRetorno.putExtra("listaItensPedido", listaItens);

        setResult(RESULT_OK, intentRetorno);

        super.onBackPressed();
    }

    public void realizarValidacoesAoSalvar(Dialog dialog) throws ActivityException {
        Spinner campoProduto = dialog.findViewById(R.id.seletorProdutoSpinner);
        EditText campoQuantidade = dialog.findViewById(R.id.quantidadeEditText);
        EditText campoPrecoVenda = dialog.findViewById(R.id.precoVendaEditText);

        if (campoProduto.getSelectedItemPosition() < 0) {
            throw new ActivityException(getString(R.string.frase_pedido_item_produto_necessario));
        }

        BigDecimal quantidade = DecimalUtil.formatarDuasCasasDecimais(campoQuantidade.getText().toString());

        if (quantidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ActivityException(getString(R.string.frase_pedido_item_quantidade_necessario));
        }

        BigDecimal precoVenda = DecimalUtil.formatarDuasCasasDecimais(campoPrecoVenda.getText().toString());
        if (precoVenda.compareTo(BigDecimal.ZERO) < 0) {
            throw new ActivityException(getString(R.string.frase_pedido_item_coluna_preco_venda_abaixo_zero));
        }
    }
}
