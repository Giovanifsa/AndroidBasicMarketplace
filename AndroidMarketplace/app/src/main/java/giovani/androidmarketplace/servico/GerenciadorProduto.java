package giovani.androidmarketplace.servico;

import java.math.BigDecimal;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.daos.IProdutoDAO;
import giovani.androidmarketplace.dados.entidades.Produto;
import giovani.androidmarketplace.exceptions.DAOException;
import giovani.androidmarketplace.exceptions.GerenciadorException;
import giovani.androidmarketplace.utils.DecimalUtil;
import giovani.androidmarketplace.utils.containers.Pair;
import giovani.androidmarketplace.utils.containers.Triplet;

public class GerenciadorProduto extends AbstractGerenciadorCRUD<Produto, IProdutoDAO> {
    public GerenciadorProduto(ContextoAplicacao contextoAplicacao) {
        super(contextoAplicacao, Produto.TABELA_PRODUTO);
    }

    @Override
    protected void onPreSalvar(Produto entidade) throws GerenciadorException {
        validarCamposObrigatoriosProduto(entidade);
        corrigirCamposDecimais(entidade);
        validarTamanhoCamposProduto(entidade);
        validarPrecoMinimo(entidade);
    }

    @Override
    protected void onPreAtualizar(Produto entidade) throws GerenciadorException {
        validarCamposObrigatoriosProduto(entidade);
        corrigirCamposDecimais(entidade);
        validarTamanhoCamposProduto(entidade);
        validarPrecoMinimo(entidade);
    }

    private void corrigirCamposDecimais(Produto entidade) {
        entidade.setPreco(DecimalUtil.formatarBDDuasCasasDecimais(entidade.getPreco()));
    }

    private void validarCamposObrigatoriosProduto(Produto entidade) throws GerenciadorException {
        validarCamposObrigatorios(
                Pair.from(R.string.palavra_produto_coluna_descricao, (Object) entidade.getDescricao()),
                Pair.from(R.string.palavra_produto_coluna_preco, (Object) entidade.getPreco())
        );
    }

    private void validarTamanhoCamposProduto(Produto entidade) throws GerenciadorException {
        validarTamanhoCampos(
                Triplet.from(R.string.palavra_produto_coluna_descricao, entidade.getDescricao(), 80),
                Triplet.from(R.string.palavra_produto_coluna_preco, DecimalUtil.formatarDuasCasasDecimais(entidade.getPreco()), 18)
        );
    }

    private void validarPrecoMinimo(Produto entidade) throws GerenciadorException {
        if (entidade.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new GerenciadorException(getString(R.string.frase_impossivel_cadastrar_produtos_sem_preco));
        }
    }

    public IProdutoDAO getDAO() {
        return getContextoAplicacao().getCriadorDAOs().getProdutoDAO();
    }
}
