package giovani.androidmarketplace.servico;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.daos.IPedidoItemDAO;
import giovani.androidmarketplace.dados.entidades.Pedido;
import giovani.androidmarketplace.dados.entidades.PedidoItem;
import giovani.androidmarketplace.exceptions.GerenciadorException;
import giovani.androidmarketplace.utils.containers.Pair;

public class GerenciadorPedidoItem extends AbstractGerenciadorCRUD<PedidoItem, IPedidoItemDAO> {
    public GerenciadorPedidoItem(ContextoAplicacao contextoAplicacao) {
        super(contextoAplicacao, PedidoItem.TABELA_PEDIDOITEM);
    }

    @Override
    protected void onPreSalvar(PedidoItem entidade) throws GerenciadorException {
        validarCamposObrigatoriosPedidoItem(entidade);
        validarTamanhoCamposPedidoItem(entidade);
    }

    @Override
    protected void onPreAtualizar(PedidoItem entidade) throws GerenciadorException {
        validarCamposObrigatoriosPedidoItem(entidade);
        validarTamanhoCamposPedidoItem(entidade);
    }

    @Override
    protected void onPostSalvar(PedidoItem entidade) throws GerenciadorException {
        atualizarValoresPedido(entidade);
    }

    @Override
    protected void onPostAtualizar(PedidoItem entidade) throws GerenciadorException {
        atualizarValoresPedido(entidade);
    }

    private void atualizarValoresPedido(PedidoItem entidade) throws GerenciadorException {
        Pedido pedidoDoItem = getContextoAplicacao().getCriadorDAOs().getPedidoDAO().buscar(entidade.getIdPedido());

        if (pedidoDoItem == null) {
            throw new GerenciadorException(getString(R.string.frase_pedido_do_item_nao_encontrado));
        }

        getContextoAplicacao().getCriadorGerenciadores().getGerenciadorPedido().recalcularValoresPedido(pedidoDoItem.getId());
    }

    private void validarCamposObrigatoriosPedidoItem(PedidoItem entidade) throws GerenciadorException {
        validarCamposObrigatorios(
                Pair.from(R.string.frase_pedido_item_coluna_idpedido, (Object) entidade.getIdPedido()),
                Pair.from(R.string.frase_pedido_item_coluna_idproduto, (Object) entidade.getIdProduto()),
                Pair.from(R.string.frase_pedido_item_coluna_preco_venda, (Object) entidade.getPrecoVenda()),
                Pair.from(R.string.frase_pedido_item_coluna_quantidade, (Object) entidade.getQuantidade()),
                Pair.from(R.string.frase_pedido_item_coluna_valor_desconto, (Object) entidade.getValorDesconto())
        );
    }

    private void validarTamanhoCamposPedidoItem(PedidoItem entidade) throws GerenciadorException {
        /*validarTamanhoCampos(
                Triplet.from(R.string.palavra_produto_coluna_descricao, entidade.getDescricao(), 80)
        );*/
    }

    public IPedidoItemDAO getDAO() {
        return getContextoAplicacao().getCriadorDAOs().getPedidoItemDAO();
    }
}
