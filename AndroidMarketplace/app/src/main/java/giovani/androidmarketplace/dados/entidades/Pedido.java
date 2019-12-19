package giovani.androidmarketplace.dados.entidades;

import java.math.BigDecimal;
import java.util.Date;

public class Pedido extends AbstractEntidade {
    public static final String COLUNA_IDPEDIDO = "idPedido";
    public static final String COLUNA_IDUSUARIO = "idUsuario";
    public static final String COLUNA_CLIENTE = "cliente";
    public static final String COLUNA_ENDERECO = "endereco";
    public static final String COLUNA_DATAPEDIDO = "dataPedido";
    public static final String COLUNA_TOTALITENS = "totalItens";
    public static final String COLUNA_TOTALPRODUTOS = "totalProdutos";
    public static final String COLUNA_VALORTOTAL = "valorTotal";
    public static final String TABELA_PEDIDO = "Pedido";

    private Integer idPedido;
    private Integer idUsuario;
    private String cliente;
    private String endereco;
    private Date dataPedido;
    private BigDecimal totalItens;
    private BigDecimal totalProdutos;
    private BigDecimal valorTotal;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public BigDecimal getTotalItens() {
        return totalItens;
    }

    public BigDecimal getTotalProdutos() {
        return totalProdutos;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public void setTotalItens(BigDecimal totalItens) {
        this.totalItens = totalItens;
    }

    public void setTotalProdutos(BigDecimal totalProdutos) {
        this.totalProdutos = totalProdutos;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public Integer getId() {
        return getIdPedido();
    }
}
