package giovani.androidmarketplace.dados.entidades;

public class Produto extends AbstractEntidade {
    public static final String TABELA_PRODUTO = "Produto";
    public static final String COLUNA_IDPRODUTO = "idProduto";
    public static final String COLUNA_DESCRICAO = "descricao";
    public static final String COLUNA_PRECO = "preco";

    private Integer idProduto;
    private String descricao;
    private String preco;

    public Integer getIdProduto() {
        return idProduto;
    }

    @Override
    public Integer getId() {
        return getIdProduto();
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPreco() {
        return preco;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
}
