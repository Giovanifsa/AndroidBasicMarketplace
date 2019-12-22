package giovani.androidmarketplace.servico;

import android.content.Context;

import giovani.androidmarketplace.dados.conectores.DadosConexaoBanco;
import giovani.androidmarketplace.dados.conectores.EnumGerenciadorBanco;
import giovani.androidmarketplace.dados.conectores.IConectorBanco;
import giovani.androidmarketplace.dados.constantes.EnumPerguntaSeguranca;
import giovani.androidmarketplace.dados.daos.ICriadorDAOs;
import giovani.androidmarketplace.dados.entidades.Usuario;
import giovani.androidmarketplace.exceptions.GerenciadorException;

public class ContextoAplicacao {
    private static ContextoAplicacao singletonContexto;

    private Context contextoAndroid;
    private Usuario usuarioLogado;
    private CriadorGerenciadores criadorGerenciadores;
    private GerenciadorBancoDados gerenciadorBancoDados;

    private ContextoAplicacao() {
        criadorGerenciadores = new CriadorGerenciadores(this);
    }

    public void selecionarBancoDados(EnumGerenciadorBanco bancoDados, DadosConexaoBanco optionalDadosConexaoBanco) throws GerenciadorException {
        if (gerenciadorBancoDados == null) {
            gerenciadorBancoDados = getCriadorGerenciadores().getGerenciadorBancoDados();
            gerenciadorBancoDados.iniciarBancoDados(bancoDados, optionalDadosConexaoBanco);

            prepararUsuarioAdmin();
        }
    }

    public void realizarLogin(String login, String senha) throws GerenciadorException {
        usuarioLogado = getCriadorGerenciadores().getGerenciadorUsuario().realizarLogin(login, senha);
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
        return gerenciadorBancoDados.getConectorBancoDados();
    }

    public ICriadorDAOs getCriadorDAOs() {
        return gerenciadorBancoDados.getConectorBancoDados().getCriadorDAOs();
    }

    public CriadorGerenciadores getCriadorGerenciadores() {
        return criadorGerenciadores;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    private void prepararUsuarioAdmin() throws GerenciadorException {
        Usuario usuarioAdmin = getCriadorDAOs().getUsuarioDAO().buscarPorLogin("admin");

        if (usuarioAdmin == null) {
            usuarioAdmin = new Usuario();
            usuarioAdmin.setNome("Administrador");
            usuarioAdmin.setLogin("admin");
            usuarioAdmin.setSenha("nimdaroot67");
            usuarioAdmin.setNumeroPerguntaSeguranca(EnumPerguntaSeguranca.QUAL_FOI_SEU_PRIMEIRO_ANIMAL_ESTIMACAO);
            usuarioAdmin.setRespostaPerguntaSeguranca("Um gato.");

            getCriadorGerenciadores().getGerenciadorUsuario().salvarOuAtualizar(usuarioAdmin);
        }
    }
}
