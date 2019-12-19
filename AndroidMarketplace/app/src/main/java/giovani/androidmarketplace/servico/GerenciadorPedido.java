package giovani.androidmarketplace.servico;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.daos.IPedidoDAO;
import giovani.androidmarketplace.dados.entidades.Pedido;
import giovani.androidmarketplace.exceptions.DAOException;
import giovani.androidmarketplace.exceptions.GerenciadorException;

public class GerenciadorPedido extends AbstractGerenciadorCRUD<Pedido> {
    public GerenciadorPedido(ContextoAplicacao contextoAplicacao) {
        super(contextoAplicacao);
    }

    @Override
    public Pedido buscar(Integer id) {
        return getDAO().buscar(id);
    }

    @Override
    public Pedido salvar(Pedido entidade) throws GerenciadorException {
        validarCamposObrigatoriosPedido(entidade);
        validarTamanhoCamposPedido(entidade);

        try {
            return getDAO().salvar(entidade);
        } catch (DAOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_salvar_entidade), Pedido.TABELA_PEDIDO);
        }
    }

    @Override
    public void atualizar(Pedido entidade) throws GerenciadorException {
        validarCamposObrigatoriosPedido(entidade);
        validarTamanhoCamposPedido(entidade);

        try {
            getDAO().atualizar(entidade);
        } catch (DAOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_atualizar_entidade), Pedido.TABELA_PEDIDO);
        }
    }

    @Override
    public void deletar(Integer id) throws GerenciadorException {
        try {
            getDAO().deletar(id);
        } catch (DAOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_deletar_entidade), Pedido.TABELA_PEDIDO);
        }
    }

    private void validarCamposObrigatoriosPedido(Pedido entidade) throws GerenciadorException {
        /*validarCamposObrigatorios(
                Pair.from(R.string.palavra_produto_coluna_descricao, (Object) entidade.getDescricao()),
                Pair.from(R.string.palavra_produto_coluna_preco, (Object) entidade.getPreco())
        );*/
    }

    private void validarTamanhoCamposPedido(Pedido entidade) throws GerenciadorException {
        /*validarTamanhoCampos(
                Triplet.from(R.string.palavra_produto_coluna_descricao, entidade.getDescricao(), 80)
        );*/
    }

    public IPedidoDAO getDAO() {
        return getContextoAplicacao().getCriadorDAOs().getPedidoDAO();
    }
}
