package giovani.androidmarketplace.servico;

import android.content.Intent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.daos.IPedidoItemDAO;
import giovani.androidmarketplace.dados.entidades.Pedido;
import giovani.androidmarketplace.dados.entidades.PedidoItem;
import giovani.androidmarketplace.dados.entidades.Produto;
import giovani.androidmarketplace.exceptions.GerenciadorException;
import giovani.androidmarketplace.utils.DecimalUtil;
import giovani.androidmarketplace.utils.containers.Pair;
import giovani.androidmarketplace.utils.containers.Triplet;

public class GerenciadorPedidoItem extends AbstractGerenciadorCRUD<PedidoItem, IPedidoItemDAO> {
    public GerenciadorPedidoItem(ContextoAplicacao contextoAplicacao) {
        super(contextoAplicacao, PedidoItem.TABELA_PEDIDOITEM);
    }

    @Override
    protected void onPreSalvar(PedidoItem entidade) throws GerenciadorException {
        validarCamposObrigatoriosPedidoItem(entidade);
        preencherCamposNaoObrigatorios(entidade);
        corrigirValoresDecimaisPedidoItem(entidade);
        validarCamposValores(entidade);
        validarTamanhoCamposPedidoItem(entidade);
    }

    @Override
    protected void onPreAtualizar(PedidoItem entidade) throws GerenciadorException {
        validarCamposObrigatoriosPedidoItem(entidade);
        preencherCamposNaoObrigatorios(entidade);
        corrigirValoresDecimaisPedidoItem(entidade);
        validarCamposValores(entidade);
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

    public void apagarItensPorProduto(Integer idProduto) throws GerenciadorException {
        List<PedidoItem> itensContendoProduto = getDAO().getAllItensContendoProduto(idProduto);

        for (PedidoItem item : itensContendoProduto) {
            deletar(item.getId());
        }
    }

    public void apagarItensPorPedido(Integer idPedido) throws GerenciadorException {
        List<PedidoItem> itensDoPedido = getDAO().getAllItensParaPedido(idPedido);

        for (PedidoItem item : itensDoPedido) {
            deletar(item.getId());
        }
    }

    private void validarCamposValores(PedidoItem entidade) throws GerenciadorException {
        if (entidade.getPrecoVenda().compareTo(BigDecimal.ZERO) < 0) {
            throw new GerenciadorException(getString(R.string.frase_pedido_item_coluna_preco_venda_abaixo_zero));
        }

        if (entidade.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new GerenciadorException(getString(R.string.frase_pedido_item_coluna_quantidade_abaixo_zero));
        }
    }

    private void preencherCamposNaoObrigatorios(PedidoItem entidade) {
        Produto produtoDoItem = getContextoAplicacao().getCriadorDAOs().getProdutoDAO().buscar(entidade.getIdProduto());
        entidade.setPrecoOriginal(DecimalUtil.formatarBDDuasCasasDecimais(produtoDoItem.getPreco()));
        entidade.setValorDesconto(DecimalUtil.formatarBDDuasCasasDecimais(produtoDoItem.getPreco().subtract(entidade.getPrecoVenda())));
    }

    private void corrigirValoresDecimaisPedidoItem(PedidoItem entidade) {
        entidade.setPrecoOriginal(DecimalUtil.formatarBDDuasCasasDecimais(entidade.getPrecoOriginal()));
        entidade.setPrecoVenda(DecimalUtil.formatarBDDuasCasasDecimais(entidade.getPrecoVenda()));
        entidade.setQuantidade(DecimalUtil.formatarBDDuasCasasDecimais(entidade.getQuantidade()));
        entidade.setPrecoVenda(DecimalUtil.formatarBDDuasCasasDecimais(entidade.getPrecoVenda()));
        entidade.setValorDesconto(DecimalUtil.formatarBDDuasCasasDecimais(entidade.getValorDesconto()));
    }

    public void salvarItensDoPedido(Pedido pedidoPai, List<PedidoItem> listaItens) throws GerenciadorException {
        IPedidoItemDAO pedidoItemDAO = getContextoAplicacao().getCriadorDAOs().getPedidoItemDAO();

        List<PedidoItem> itensPersistidos = pedidoItemDAO.getAllItensParaPedido(pedidoPai.getId());
        List<PedidoItem> itensRemovidos = new ArrayList<>();

        loopItemPersistido : for (PedidoItem itemPersistido : itensPersistidos) {
            for (PedidoItem itemParaSalvar : listaItens) {
                if (itemParaSalvar.equals(itemPersistido)) {
                    continue loopItemPersistido;
                }
            }

            itensRemovidos.add(itemPersistido);
        }

        for (PedidoItem itemRemovido : itensRemovidos) {
            deletar(itemRemovido.getId());
        }

        for (int idxPedidoSalvar = 0; idxPedidoSalvar < listaItens.size(); idxPedidoSalvar++) {
            PedidoItem pedidoItem = listaItens.get(idxPedidoSalvar);
            pedidoItem.setIdPedido(pedidoPai.getId());

            pedidoItem = salvarOuAtualizar(pedidoItem);
            listaItens.set(idxPedidoSalvar, pedidoItem);
        }
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
                Pair.from(R.string.frase_pedido_item_coluna_quantidade, (Object) entidade.getQuantidade())
        );
    }

    private void validarTamanhoCamposPedidoItem(PedidoItem entidade) throws GerenciadorException {
        validarTamanhoCampos(
                Triplet.from(R.string.frase_pedido_item_coluna_preco_venda, DecimalUtil.formatarDuasCasasDecimais(entidade.getPrecoVenda()), 18),
                Triplet.from(R.string.frase_pedido_item_coluna_quantidade, DecimalUtil.formatarDuasCasasDecimais(entidade.getQuantidade()), 18),
                Triplet.from(R.string.frase_pedido_item_coluna_valor_desconto, DecimalUtil.formatarDuasCasasDecimais(entidade.getValorDesconto()), 18),
                Triplet.from(R.string.frase_pedido_item_coluna_preco_original, DecimalUtil.formatarDuasCasasDecimais(entidade.getPrecoOriginal()), 18)
        );
    }

    public IPedidoItemDAO getDAO() {
        return getContextoAplicacao().getCriadorDAOs().getPedidoItemDAO();
    }
}
