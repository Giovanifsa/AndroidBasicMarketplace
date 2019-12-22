package giovani.androidmarketplace.visao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.entidades.Pedido;
import giovani.androidmarketplace.dados.entidades.PedidoItem;
import giovani.androidmarketplace.exceptions.GerenciadorException;
import giovani.androidmarketplace.servico.ContextoAplicacao;
import giovani.androidmarketplace.utils.ActivityUtil;
import giovani.androidmarketplace.utils.DateUtil;
import giovani.androidmarketplace.utils.DecimalUtil;
import giovani.androidmarketplace.utils.ToastUtil;

public class EdicaoPedidoActivity extends AppCompatActivity {
    private static final int RESULT_CODE_LISTAGEM_ITEM_PEDIDO = 1;

    private ArrayList<PedidoItem> listaItensPedido;
    private Pedido pedidoAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_pedido);

        prepararListeners();
        carregarDadosPilhaActivity();
        prepararCampos();
    }

    private void carregarDadosPilhaActivity() {
        Bundle extras = getIntent().getExtras();

        if (extras != null && extras.containsKey(Pedido.COLUNA_IDPEDIDO)) {
            int idPedido = extras.getInt(Pedido.COLUNA_IDPEDIDO);

            pedidoAtual = ContextoAplicacao.getContextoAplicacao().getCriadorDAOs().getPedidoDAO().buscar(idPedido);
            listaItensPedido = new ArrayList<>(ContextoAplicacao.getContextoAplicacao().getCriadorDAOs().getPedidoItemDAO().getAllItensParaPedido(idPedido));
        }

        if (pedidoAtual == null) {
            pedidoAtual = new Pedido();
            pedidoAtual.setDataPedido(new Date());

            listaItensPedido = new ArrayList<>();
        }

        recalcularResumoPedido();
    }

    private void prepararListeners() {
        findViewById(R.id.itensPedidoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItensPedidoButton((Button) v);
            }
        });

        findViewById(R.id.salvarPedidoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSalvarPedidoButton((Button) v);
            }
        });
    }

    private void prepararCampos() {
        EditText campoNome = findViewById(R.id.nomeClienteEditText);
        EditText campoEndereco = findViewById(R.id.enderecoClienteEditText);
        EditText campoData = findViewById(R.id.dataPedidoEditText);

        campoNome.setText(pedidoAtual.getCliente());
        campoEndereco.setText(pedidoAtual.getEndereco());
        campoData.setText(DateUtil.getSlashedDDMMYYYY(pedidoAtual.getDataPedido()));
    }

    private void onClickItensPedidoButton(Button button) {
        Intent intentItensPedido = new Intent(this, ListagemItemPedidoActivity.class);
        intentItensPedido.putExtra("listaItensPedido", listaItensPedido);

        startActivityForResult(intentItensPedido, RESULT_CODE_LISTAGEM_ITEM_PEDIDO);
    }

    private void onClickSalvarPedidoButton(Button button) {
        EditText campoNome = findViewById(R.id.nomeClienteEditText);
        EditText campoEndereco = findViewById(R.id.enderecoClienteEditText);

        pedidoAtual.setCliente(campoNome.getText().toString());
        pedidoAtual.setEndereco(campoEndereco.getText().toString());
        pedidoAtual.setValorTotal(BigDecimal.ZERO);
        pedidoAtual.setTotalProdutos(BigDecimal.ZERO);
        pedidoAtual.setTotalItens(BigDecimal.ZERO);

        try {
            pedidoAtual = ContextoAplicacao.getContextoAplicacao().getCriadorGerenciadores().getGerenciadorPedido().salvarOuAtualizar(pedidoAtual);
            ContextoAplicacao.getContextoAplicacao().getCriadorGerenciadores().getGerenciadorPedidoItem().salvarItensDoPedido(pedidoAtual, listaItensPedido);

            ToastUtil.printShort(this, R.string.frase_alteracoes_salvas);

            finish();
        } catch (GerenciadorException e) {
            e.printStackTrace();
            ActivityUtil.exibirDialogMensagem(this, R.string.palavra_alerta, e.getMessage(), R.string.palavra_aceitar_alerta);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CODE_LISTAGEM_ITEM_PEDIDO && resultCode == RESULT_OK) {
            listaItensPedido = (ArrayList<PedidoItem>) data.getSerializableExtra("listaItensPedido");
            recalcularResumoPedido();
        }
    }

    private void recalcularResumoPedido() {
        BigDecimal resumoTotalProduto = BigDecimal.ZERO;
        BigDecimal resumoTotalItens = BigDecimal.ZERO;
        BigDecimal resumoTotalPedido = BigDecimal.ZERO;

        ArrayList<Integer> idsProdutosTotalizados = new ArrayList<>();

        for (PedidoItem item : listaItensPedido) {
            if (!idsProdutosTotalizados.contains(item.getIdProduto())) {
                idsProdutosTotalizados.add(item.getIdProduto());
                resumoTotalProduto = resumoTotalProduto.add(BigDecimal.ONE);
            }

            resumoTotalItens = resumoTotalItens.add(item.getQuantidade());
            resumoTotalPedido = resumoTotalPedido.add(item.getPrecoVenda().multiply(item.getQuantidade()));
        }

        EditText campoTotalProdutos = findViewById(R.id.totalProdutoEditText);
        EditText campoTotalItens = findViewById(R.id.totalItensEditText);
        EditText campoTotalPedido = findViewById(R.id.totalPedidoEditText);

        campoTotalProdutos.setText(DecimalUtil.formatarDuasCasasDecimais(resumoTotalProduto));
        campoTotalItens.setText(DecimalUtil.formatarDuasCasasDecimais(resumoTotalItens));
        campoTotalPedido.setText(DecimalUtil.formatarDuasCasasDecimais(resumoTotalPedido));
    }
}
