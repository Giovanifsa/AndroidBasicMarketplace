package giovani.androidmarketplace.servico;

import android.content.Context;

import java.util.Optional;

import giovani.androidmarketplace.dados.conectores.EnumGerenciadorBanco;
import giovani.androidmarketplace.dados.conectores.IConectorBanco;
import giovani.androidmarketplace.dados.conectores.DadosConexaoBanco;
import giovani.androidmarketplace.dados.daos.ICriadorDAOs;
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

    public Context getContextoAndroid() {
        return contextoAndroid;
    }

    public String getString(int resId) {
        return contextoAndroid.getString(resId);
    }

    public IConectorBanco getBancoDadosEmUso() {
        return bancoDadosEmUso;
    }

    public ICriadorDAOs getCriadorDAOs() {
        return bancoDadosEmUso.getCriadorDAOs();
    }

    public CriadorGerenciadores getCriadorGerenciadores() {
        return new CriadorGerenciadores(this);
    }
}
