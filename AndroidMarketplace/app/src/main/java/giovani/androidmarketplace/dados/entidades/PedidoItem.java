package giovani.androidmarketplace.dados.entidades;

import java.math.BigDecimal;

public class PedidoItem extends AbstractEntidade {
    public static final String COLUNA_IDPEDIDOITEM = "idPedidoItem";
    public static final String COLUNA_IDPRODUTO = "idProduto";
    public static final String COLUNA_IDPEDIDO = "idPedido";
    public static final String COLUNA_QUANTIDADE = "quantidade";
    public static final String COLUNA_PRECOORIGINAL = "precoOriginal";
    public static final String COLUNA_PRECOVENDA = "precoVenda";
    public static final String COLUNA_VALORDESCONTO = "valorDesconto";
    public static final String TABELA_PEDIDOITEM = "PedidoItem";

    private Integer idPedidoItem;
    private Integer idProduto;
    private Integer idPedido;
    private BigDecimal quantidade;
    private BigDecimal precoOriginal;
    private BigDecimal precoVenda;
    private BigDecimal valorDesconto;

    public Integer getIdPedido() {
        return idPedido;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public BigDecimal getPrecoOriginal() {
        return precoOriginal;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public Integer getIdPedidoItem() {
        return idPedidoItem;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public void setIdPedidoItem(Integer idPedidoItem) {
        this.idPedidoItem = idPedidoItem;
    }

    public void setPrecoOriginal(BigDecimal precoOriginal) {
        this.precoOriginal = precoOriginal;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    @Override
    public Integer getId() {
        return getIdPedidoItem();
    }
}
