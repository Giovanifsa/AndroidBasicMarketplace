package giovani.androidmarketplace.dados.daos;

import java.util.List;

import giovani.androidmarketplace.dados.entidades.Produto;

public interface IProdutoDAO extends IDatabaseAccessObject<Produto> {
    List<Produto> getAllProdutos();
}
