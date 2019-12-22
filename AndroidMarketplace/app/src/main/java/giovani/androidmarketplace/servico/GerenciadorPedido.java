package giovani.androidmarketplace.servico;

import java.math.BigDecimal;
import java.util.List;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.daos.IPedidoDAO;
import giovani.androidmarketplace.dados.entidades.Pedido;
import giovani.androidmarketplace.dados.entidades.PedidoItem;
import giovani.androidmarketplace.dados.entidades.Usuario;
import giovani.androidmarketplace.exceptions.DAOException;
import giovani.androidmarketplace.exceptions.GerenciadorException;
import giovani.androidmarketplace.utils.containers.Pair;
import giovani.androidmarketplace.utils.containers.Triplet;

public class GerenciadorPedido extends AbstractGerenciadorCRUD<Pedido, IPedidoDAO> {
    public GerenciadorPedido(ContextoAplicacao contextoAplicacao) {
        super(contextoAplicacao, Pedido.TABELA_PEDIDO);
    }

    @Override
    protected void onPreSalvar(Pedido entidade) throws GerenciadorException{
        validarCamposObrigatoriosPedido(entidade);
        validarTamanhoCamposPedido(entidade);
        preencherCamposInsercao(entidade);
    }

    @Override
    protected void onPreAtualizar(Pedido entidade) throws GerenciadorException {
        validarCamposObrigatoriosPedido(entidade);
        validarTamanhoCamposPedido(entidade);
    }

    @Override
    protected void onPreDeletar(Integer id) throws GerenciadorException {
        getContextoAplicacao().getCriadorGerenciadores().getGerenciadorPedidoItem().apagarItensPorPedido(id);
    }

    private void preencherCamposInsercao(Pedido pedido) throws GerenciadorException {
        Usuario usuario = getContextoAplicacao().getUsuarioLogado();
        validarUsuarioLogado(usuario);

        pedido.setIdUsuario(usuario.getIdUsuario());
    }

    public void recalcularValoresPedido(Integer idPedido) throws GerenciadorException {
        Pedido pedido = buscar(idPedido);

        if (pedido == null) {
            throw new GerenciadorException(getString(R.string.frase_pedido_nao_encontrado));
        }

        pedido.setTotalItens(BigDecimal.ZERO);
        pedido.setTotalProdutos(BigDecimal.ZERO);
        pedido.setValorTotal(BigDecimal.ZERO);

        List<PedidoItem> itensDoPedido = getContextoAplicacao().getCriadorDAOs().getPedidoItemDAO().getAllItensParaPedido(pedido.getIdPedido());

        for (PedidoItem item : itensDoPedido) {
            pedido.setTotalProdutos(pedido.getTotalProdutos().add(BigDecimal.ONE));
            pedido.setTotalItens(pedido.getTotalItens().add(item.getQuantidade()));

            pedido.setValorTotal(pedido.getValorTotal().add(item.getQuantidade().multiply(item.getPrecoVenda())));
        }

        atualizar(pedido);
    }

    private void validarCamposObrigatoriosPedido(Pedido entidade) throws GerenciadorException {
        validarCamposObrigatorios(
                Pair.from(R.string.frase_pedido_coluna_cliente, (Object) entidade.getCliente()),
                Pair.from(R.string.frase_pedido_coluna_data_pedido, (Object) entidade.getDataPedido()),
                Pair.from(R.string.frase_pedido_coluna_endereco, (Object) entidade.getEndereco())
        );
    }

    private void validarTamanhoCamposPedido(Pedido entidade) throws GerenciadorException {
        validarTamanhoCampos(
                Triplet.from(R.string.frase_pedido_coluna_endereco, entidade.getEndereco(), 200),
                Triplet.from(R.string.frase_pedido_coluna_cliente, entidade.getCliente(), 200)

        );
    }

    private void validarUsuarioLogado(Usuario usuario) throws GerenciadorException {
        if (usuario == null) {
            throw new GerenciadorException(getString(R.string.frase_operacao_requer_usuario_logado));
        }
    }

    public IPedidoDAO getDAO() {
        return getContextoAplicacao().getCriadorDAOs().getPedidoDAO();
    }
}
