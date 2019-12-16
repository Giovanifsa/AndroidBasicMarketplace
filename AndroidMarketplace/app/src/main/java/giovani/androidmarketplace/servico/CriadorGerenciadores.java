package giovani.androidmarketplace.servico;

public class CriadorGerenciadores {
    private ContextoAplicacao contextoAplicacao;

    public CriadorGerenciadores(ContextoAplicacao contextoAplicacao) {
        this.contextoAplicacao = contextoAplicacao;
    }

    public GerenciadorUsuario getGerenciadorUsuario() {
        return new GerenciadorUsuario(contextoAplicacao);
    }
}
