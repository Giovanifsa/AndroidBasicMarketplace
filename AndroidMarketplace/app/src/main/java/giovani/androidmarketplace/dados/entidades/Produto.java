package giovani.androidmarketplace.dados.entidades;

public class Produto extends AbstractEntidade {
    private Integer idProduto;

    public Integer getIdProduto() {
        return idProduto;
    }

    @Override
    public Integer getId() {
        return getIdProduto();
    }
}
