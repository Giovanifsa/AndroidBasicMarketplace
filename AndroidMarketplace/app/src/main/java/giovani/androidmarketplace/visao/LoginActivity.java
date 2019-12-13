package giovani.androidmarketplace.visao;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.utils.ActivityUtil;
import giovani.androidmarketplace.utils.ToastUtil;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        ToastUtil.printShort(this, "Teste");
    }

    private void onClickSenhaEsquecida(TextView textView) {
        ToastUtil.printShort(this, "Teste");
    }

    private void onClickCriarConta(TextView textView) {
        ActivityUtil.iniciarActivity(this, CriarContaActivity.class);
    }
}
