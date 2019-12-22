package giovani.androidmarketplace.dados.daos;

import java.util.List;

import giovani.androidmarketplace.dados.entidades.PedidoItem;

public interface IPedidoItemDAO extends IDatabaseAccessObject<PedidoItem> {
    List<PedidoItem> getAllItensParaPedido(Integer idPedido);
    List<PedidoItem> getAllItensContendoProduto(Integer idProduto);
}
