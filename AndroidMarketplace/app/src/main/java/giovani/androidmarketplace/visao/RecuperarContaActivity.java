package giovani.androidmarketplace.visao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.constantes.EnumPerguntaSeguranca;
import giovani.androidmarketplace.dados.entidades.Usuario;
import giovani.androidmarketplace.exceptions.GerenciadorException;
import giovani.androidmarketplace.servico.ContextoAplicacao;
import giovani.androidmarketplace.utils.ActivityUtil;
import giovani.androidmarketplace.utils.StringUtil;
import giovani.androidmarketplace.utils.ToastUtil;

public class RecuperarContaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_conta);

        prepararListeners();
    }

    private void prepararListeners() {
        findViewById(R.id.recContaButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRecuperarContaButton();
            }
        });

        findViewById(R.id.loginRecEditText).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    buscarUsuario();
                }
            }
        });
    }

    private void onClickRecuperarContaButton() {
        EditText campoLogin = findViewById(R.id.loginRecEditText);
        String login = campoLogin.getText().toString();

        if (!StringUtil.isBlank(login)) {
            EditText campoResposta = findViewById(R.id.respostaSegurancaEditText);
            EditText campoNovaSenha = findViewById(R.id.senhaRecEditText);
            String resposta = campoResposta.getText().toString();
            String senha = campoNovaSenha.getText().toString();

            try {
                ContextoAplicacao.getContextoAplicacao().getCriadorGerenciadores().getGerenciadorUsuario().recuperarConta(login, resposta, senha);

                ToastUtil.printShort(this, R.string.frase_senha_alterada_com_sucesso);

                finish();
            } catch (GerenciadorException e) {
                ActivityUtil.exibirDialogMensagem(this, R.string.palavra_alerta, e.getMessage(), R.string.palavra_aceitar_alerta);
            }
        }

        else {
            ActivityUtil.exibirDialogMensagem(this, R.string.palavra_alerta, getString(R.string.frase_login_conta_deve_ser_fornecido), R.string.palavra_aceitar_alerta);
        }
    }

    private void buscarUsuario() {
        EditText campoLogin = findViewById(R.id.loginRecEditText);
        String login = campoLogin.getText().toString();

        if (!StringUtil.isBlank(login)) {
            Usuario usuarioRecuperacao = ContextoAplicacao.getContextoAplicacao().getCriadorDAOs().getUsuarioDAO().buscarPorLogin(login);

            if (usuarioRecuperacao != null) {
                TextView textViewPergSeguranca = findViewById(R.id.pergSegRecTextView);
                textViewPergSeguranca.setText(usuarioRecuperacao.getNumeroPerguntaSeguranca().getChaveTraducao());
            }

            else {
                String mensagemUsuarioNaoEncontrado = StringUtil.formatMensagem(getString(R.string.frase_login_usuario_nao_encontrado), login);
                ActivityUtil.exibirDialogMensagem(this, R.string.palavra_alerta, mensagemUsuarioNaoEncontrado, R.string.palavra_aceitar_alerta);
            }
        }
    }
}
