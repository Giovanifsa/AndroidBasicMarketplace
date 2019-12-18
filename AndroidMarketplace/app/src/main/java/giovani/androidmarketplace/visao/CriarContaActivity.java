package giovani.androidmarketplace.visao;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.constantes.EnumPerguntaSeguranca;
import giovani.androidmarketplace.dados.entidades.Usuario;
import giovani.androidmarketplace.exceptions.GerenciadorException;
import giovani.androidmarketplace.servico.ContextoAplicacao;
import giovani.androidmarketplace.servico.GerenciadorUsuario;
import giovani.androidmarketplace.utils.ActivityUtil;
import giovani.androidmarketplace.utils.ToastUtil;

public class CriarContaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        prepararSpinner();
        prepararListeners();
    }

    private void prepararSpinner() {
        List<String> listaSpinner = new ArrayList<>();

        for (EnumPerguntaSeguranca perguntaSeguranca : EnumPerguntaSeguranca.values()) {
            listaSpinner.add(getString(perguntaSeguranca.getChaveTraducao()));
        }

        Spinner spinner = findViewById(R.id.pergSegurancaSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaSpinner);
        spinner.setAdapter(adapter);
    }

    private void prepararListeners() {
        findViewById(R.id.criarContaButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCriarContaButton((Button) v);
            }
        });
    }

    private void onClickCriarContaButton(Button button) {
        salvarUsuario();
    }

    private void salvarUsuario() {
        EditText campoNomeConta = findViewById(R.id.nomeContaEditText);
        EditText campoNomeUsuario = findViewById(R.id.novoUsuarioEditText);
        EditText campoSenha = findViewById(R.id.novaSenhaEditText);
        Spinner campoTipoPergunta = findViewById(R.id.pergSegurancaSpinner);
        EditText campoRespostaPergunta = findViewById(R.id.respPergSegurancaTextView);

        Usuario usuario = new Usuario();
        usuario.setNome(campoNomeConta.getText().toString());
        usuario.setSenha(campoSenha.getText().toString());
        usuario.setLogin(campoNomeUsuario.getText().toString());
        usuario.setNumeroPerguntaSeguranca(EnumPerguntaSeguranca.values()[campoTipoPergunta.getSelectedItemPosition()]);
        usuario.setRespostaPerguntaSeguranca(campoRespostaPergunta.getText().toString());

        GerenciadorUsuario gerenciadorUsuario = ContextoAplicacao.getContextoAplicacao().getCriadorGerenciadores().getGerenciadorUsuario();

        try {
            gerenciadorUsuario.salvar(usuario);
            ToastUtil.printLong(this, R.string.frase_conta_criada_com_sucesso);

            finish();
        } catch (GerenciadorException ex) {
            ActivityUtil.exibirDialogMensagem(this, R.string.palavra_alerta, ex.getMessage(), R.string.palavra_aceitar_alerta);
        }
    }
}
