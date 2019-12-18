package giovani.androidmarketplace.visao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.entidades.Produto;
import giovani.androidmarketplace.exceptions.GerenciadorException;
import giovani.androidmarketplace.servico.ContextoAplicacao;
import giovani.androidmarketplace.servico.GerenciadorProduto;
import giovani.androidmarketplace.utils.ActivityUtil;
import giovani.androidmarketplace.utils.ToastUtil;

public class EdicaoProdutoActivity extends AppCompatActivity {
    private Integer idProdutoSendoEditado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_produto);

        prepararListeners();
        lerDadosPilhaActivies();
    }

    private void prepararListeners() {
        findViewById(R.id.salvarProdutoButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBotaoSalvar((Button) v);
            }
        });
    }

    private void onClickBotaoSalvar(Button button) {
        EditText campoDescricaoProduto = findViewById(R.id.descricaoProdutoEditText);
        EditText campoPrecoProduto = findViewById(R.id.precoProdutoEditText);

        String descricaoProduto = campoDescricaoProduto.getText().toString();
        String precoProduto = campoPrecoProduto.getText().toString();

        Produto produto = new Produto();
        produto.setDescricao(descricaoProduto);
        produto.setPreco(new BigDecimal(precoProduto));

        GerenciadorProduto gerenciadorProduto = ContextoAplicacao.getContextoAplicacao().getCriadorGerenciadores().getGerenciadorProduto();

        try {
            gerenciadorProduto.salvarOuAtualizar(produto);

            ToastUtil.printShort(this, R.string.frase_alteracoes_salvas);

            finish();
        } catch (GerenciadorException ex) {
            ActivityUtil.exibirDialogMensagem(this, R.string.palavra_alerta, ex.getMessage(), R.string.palavra_aceitar_alerta);
        }
    }

    private void lerDadosPilhaActivies() {
        Bundle dadosRecebidos = getIntent().getExtras();

        if (dadosRecebidos != null) {
            if (dadosRecebidos.containsKey(Produto.COLUNA_IDPRODUTO)) {
                int idProduto = dadosRecebidos.getInt(Produto.COLUNA_IDPRODUTO);
                Produto produto = ContextoAplicacao.getContextoAplicacao().getCriadorGerenciadores().getGerenciadorProduto().buscar(idProduto);

                if (produto != null) {
                    EditText campoDescricaoProduto = findViewById(R.id.descricaoProdutoEditText);
                    EditText campoPrecoProduto = findViewById(R.id.precoProdutoEditText);

                    campoDescricaoProduto.setText(produto.getDescricao());
                    campoPrecoProduto.setText(produto.getPreco().toPlainString());

                    idProdutoSendoEditado = produto.getId();
                }
            }
        }
    }
}
