package giovani.androidmarketplace.visao;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.constantes.EnumPerguntaSeguranca;

public class CriarContaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        prepararSpinner();
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
}
