package giovani.androidmarketplace.dados.daos;

public interface ICriadorDAOs {
    IUsuarioDAO getUsuarioDAO();
    IProdutoDAO getProdutoDAO();
    IPedidoDAO getPedidoDAO();
    IPedidoItemDAO getPedidoItemDAO();
}
