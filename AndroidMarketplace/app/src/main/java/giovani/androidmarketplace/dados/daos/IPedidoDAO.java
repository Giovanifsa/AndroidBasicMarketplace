package giovani.androidmarketplace.dados.daos;

import java.util.List;

import giovani.androidmarketplace.dados.entidades.Pedido;

public interface IPedidoDAO extends IDatabaseAccessObject<Pedido> {
    List<Pedido> getAllPedidos();
}
