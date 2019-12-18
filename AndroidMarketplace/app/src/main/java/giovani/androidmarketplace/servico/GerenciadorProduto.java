package giovani.androidmarketplace.servico;

import java.math.BigDecimal;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.daos.IProdutoDAO;
import giovani.androidmarketplace.dados.entidades.Produto;
import giovani.androidmarketplace.exceptions.DAOException;
import giovani.androidmarketplace.exceptions.GerenciadorException;
import giovani.androidmarketplace.utils.containers.Pair;
import giovani.androidmarketplace.utils.containers.Triplet;

public class GerenciadorProduto extends AbstractGerenciadorCRUD<Produto> {
    public GerenciadorProduto(ContextoAplicacao contextoAplicacao) {
        super(contextoAplicacao);
    }

    @Override
    public Produto buscar(Integer id) {
        return getDAO().buscar(id);
    }

    @Override
    public Produto salvar(Produto entidade) throws GerenciadorException {
        validarCamposObrigatoriosProduto(entidade);
        validarTamanhoCamposProduto(entidade);
        validarPrecoMinimo(entidade);

        try {
            return getDAO().salvar(entidade);
        } catch (DAOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_salvar_entidade), "Produto");
        }
    }

    @Override
    public void atualizar(Produto entidade) throws GerenciadorException {
        validarCamposObrigatoriosProduto(entidade);
        validarTamanhoCamposProduto(entidade);
        validarPrecoMinimo(entidade);

        try {
            getDAO().atualizar(entidade);
        } catch (DAOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_atualizar_entidade), "Produto");
        }
    }

    @Override
    public void deletar(Integer id) throws GerenciadorException {
        try {
            getDAO().deletar(id);
        } catch (DAOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_deletar_entidade), "Produto");
        }
    }

    private void validarCamposObrigatoriosProduto(Produto entidade) throws GerenciadorException {
        validarCamposObrigatorios(
                Pair.from(R.string.palavra_produto_coluna_descricao, (Object) entidade.getDescricao()),
                Pair.from(R.string.palavra_produto_coluna_preco, (Object) entidade.getPreco())
        );
    }

    private void validarTamanhoCamposProduto(Produto entidade) throws GerenciadorException {
        validarTamanhoCampos(
                Triplet.from(R.string.palavra_produto_coluna_descricao, entidade.getDescricao(), 80)
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
