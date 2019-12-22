package giovani.androidmarketplace.visao;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.conectores.EnumGerenciadorBanco;
import giovani.androidmarketplace.exceptions.GerenciadorException;
import giovani.androidmarketplace.servico.ContextoAplicacao;
import giovani.androidmarketplace.utils.ActivityUtil;
import giovani.androidmarketplace.utils.StringUtil;
import giovani.androidmarketplace.utils.ToastUtil;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            ContextoAplicacao.iniciarContextoAplicacao(getApplicationContext()).selecionarBancoDados(EnumGerenciadorBanco.SQLite, null);
        } catch (GerenciadorException e) {
            ActivityUtil.exibirDialogMensagem(this, R.string.palavra_alerta, e.getMessage(), R.string.palavra_aceitar_alerta);
        }

        prepararListeners();
    }

    private void prepararListeners() {
        findViewById(R.id.entrarButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEntrar((Button) v);
            }
        });

        findViewById(R.id.senhaEsquecidaTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSenhaEsquecida((TextView) v);
            }
        });

        findViewById(R.id.criandoContaTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCriarConta((TextView) v);
            }
        });
    }

    private void onClickEntrar(Button button) {
        realizarLogin();
    }

    private void realizarLogin() {
        EditText campoUsuario = findViewById(R.id.campoUsuarioEditText);
        EditText campoSenha = findViewById(R.id.campoSenhaEditText);

        String usuario = campoUsuario.getText().toString();
        String senha = campoSenha.getText().toString();

        if (StringUtil.isBlank(usuario) || StringUtil.isBlank(senha)) {
            ToastUtil.printShort(this, R.string.frase_preencha_campos_login);
        }

        else {
            try {
                ContextoAplicacao.getContextoAplicacao().realizarLogin(usuario, senha);

                ToastUtil.printShort(this, R.string.frase_sessao_iniciada_com_sucesso);

                ActivityUtil.iniciarActivityRaiz(this, PrincipalActivity.class);
            } catch (GerenciadorException ex) {
                ActivityUtil.exibirDialogMensagem(this, R.string.palavra_alerta, ex.getMessage(), R.string.palavra_aceitar_alerta);
            }
        }
    }

    private void onClickSenhaEsquecida(TextView textView) {
        ActivityUtil.iniciarActivity(this, RecuperarContaActivity.class);
    }

    private void onClickCriarConta(TextView textView) {
        ActivityUtil.iniciarActivity(this, CriarContaActivity.class);
    }
}
