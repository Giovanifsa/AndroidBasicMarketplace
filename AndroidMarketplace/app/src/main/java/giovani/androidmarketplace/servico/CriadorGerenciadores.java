package giovani.androidmarketplace.servico;

public class CriadorGerenciadores {
    private final ContextoAplicacao contextoAplicacao;

    public CriadorGerenciadores(ContextoAplicacao contextoAplicacao) {
        this.contextoAplicacao = contextoAplicacao;
    }

    public GerenciadorUsuario getGerenciadorUsuario() {
        return new GerenciadorUsuario(contextoAplicacao);
    }

    public GerenciadorBancoDados getGerenciadorBancoDados() {
        return new GerenciadorBancoDados(contextoAplicacao);
    }

    public GerenciadorProduto getGerenciadorProduto() {
        return new GerenciadorProduto(contextoAplicacao);
    }

    public GerenciadorPedido getGerenciadorPedido() {
        return new GerenciadorPedido(contextoAplicacao);
    }

    public GerenciadorPedidoItem getGerenciadorPedidoItem() {
        return new GerenciadorPedidoItem(contextoAplicacao);
    }
}
