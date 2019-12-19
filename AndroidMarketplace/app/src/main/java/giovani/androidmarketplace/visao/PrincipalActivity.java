package giovani.androidmarketplace.visao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.utils.ActivityUtil;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        prepararListeners();
    }

    private void prepararListeners() {
        findViewById(R.id.exibirProdutosImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickExibirProdutos((ImageView) v);
            }
        });

        findViewById(R.id.exibirPedidosImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickExibirPedidos((ImageView) v);
            }
        });
    }

    private void onClickExibirProdutos(ImageView imageView) {
        ActivityUtil.iniciarActivity(this, ListagemProdutosActivity.class);
    }

    private void onClickExibirPedidos(ImageView imageView) {
        ActivityUtil.iniciarActivity(this, ListagemPedidosActivity.class);
    }
}
