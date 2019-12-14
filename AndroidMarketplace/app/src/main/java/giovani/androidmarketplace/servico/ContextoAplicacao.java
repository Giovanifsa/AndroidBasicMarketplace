package giovani.androidmarketplace.servico;

import android.content.Context;

import java.util.Optional;

import giovani.androidmarketplace.dados.conectores.EnumGerenciadorBanco;
import giovani.androidmarketplace.dados.conectores.IConectorBanco;
import giovani.androidmarketplace.dados.conectores.DadosConexaoBanco;
import giovani.androidmarketplace.dados.entidades.Usuario;

public class ContextoAplicacao {
    private static ContextoAplicacao singletonContexto;

    private Context contextoAndroid;
    private Usuario usuarioLogado;
    private IConectorBanco bancoDadosEmUso;

    public void selecionarBancoDados(EnumGerenciadorBanco bancoDados, Optional<DadosConexaoBanco> optionalDadosConexaoBanco) {

    }

    public static ContextoAplicacao getContextoAplicacao() {
        return singletonContexto;
    }

    public static ContextoAplicacao iniciarContextoAplicacao(Context contextoAndroid) {
        singletonContexto = new ContextoAplicacao();
        singletonContexto.contextoAndroid = contextoAndroid;

        return singletonContexto;
    }
}
