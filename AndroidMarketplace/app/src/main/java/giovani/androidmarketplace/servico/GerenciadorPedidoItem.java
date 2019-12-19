package giovani.androidmarketplace.servico;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.daos.IPedidoItemDAO;
import giovani.androidmarketplace.dados.entidades.PedidoItem;
import giovani.androidmarketplace.exceptions.DAOException;
import giovani.androidmarketplace.exceptions.GerenciadorException;

public class GerenciadorPedidoItem extends AbstractGerenciadorCRUD<PedidoItem> {
    public GerenciadorPedidoItem(ContextoAplicacao contextoAplicacao) {
        super(contextoAplicacao);
    }

    @Override
    public PedidoItem buscar(Integer id) {
        return getDAO().buscar(id);
    }

    @Override
    public PedidoItem salvar(PedidoItem entidade) throws GerenciadorException {
        validarCamposObrigatoriosPedidoItem(entidade);
        validarTamanhoCamposPedidoItem(entidade);

        try {
            return getDAO().salvar(entidade);
        } catch (DAOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_salvar_entidade), PedidoItem.TABELA_PEDIDOITEM);
        }
    }

    @Override
    public void atualizar(PedidoItem entidade) throws GerenciadorException {
        validarCamposObrigatoriosPedidoItem(entidade);
        validarTamanhoCamposPedidoItem(entidade);

        try {
            getDAO().atualizar(entidade);
        } catch (DAOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_atualizar_entidade), PedidoItem.TABELA_PEDIDOITEM);
        }
    }

    @Override
    public void deletar(Integer id) throws GerenciadorException {
        try {
            getDAO().deletar(id);
        } catch (DAOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_deletar_entidade), PedidoItem.TABELA_PEDIDOITEM);
        }
    }

    private void validarCamposObrigatoriosPedidoItem(PedidoItem entidade) throws GerenciadorException {
        /*validarCamposObrigatorios(
                Pair.from(R.string.palavra_produto_coluna_descricao, (Object) entidade.getDescricao()),
                Pair.from(R.string.palavra_produto_coluna_preco, (Object) entidade.getPreco())
        );*/
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
