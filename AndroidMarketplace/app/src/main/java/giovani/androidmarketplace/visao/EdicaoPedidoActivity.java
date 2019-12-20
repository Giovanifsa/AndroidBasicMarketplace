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
import giovani.androidmarketplace.utils.ToastUtil;

public class EdicaoPedidoActivity extends AppCompatActivity {
    private ArrayList<PedidoItem> listaItensPedido;
    private ArrayList<PedidoItem> listaItensRemovidos = new ArrayList<>();

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

        startActivityForResult(intentItensPedido, 123);
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
            ContextoAplicacao.getContextoAplicacao().getCriadorGerenciadores().getGerenciadorPedido().salvar(pedidoAtual);

            ToastUtil.printShort(this, R.string.frase_alteracoes_salvas);

            finish();
        } catch (GerenciadorException e) {
            e.printStackTrace();
            ActivityUtil.exibirDialogMensagem(this, R.string.palavra_alerta, e.getMessage(), R.string.palavra_aceitar_alerta);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                listaItensPedido = (ArrayList<PedidoItem>) data.getSerializableExtra("listaItensPedido");
                listaItensRemovidos = (ArrayList<PedidoItem>) data.getSerializableExtra("listaItensRemovidos");
            }
        }
    }
}
