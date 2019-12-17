package giovani.androidmarketplace.servico;

import giovani.androidmarketplace.dados.entidades.Produto;
import giovani.androidmarketplace.exceptions.GerenciadorException;

public class GerenciadorProduto extends AbstractGerenciadorCRUD<Produto> {
    public GerenciadorProduto(ContextoAplicacao contextoAplicacao) {
        super(contextoAplicacao);
    }

    @Override
    public Produto buscar(Integer id) {
        return null;
    }

    @Override
    public Produto salvar(Produto entidade) throws GerenciadorException {
        return null;
    }

    @Override
    public void atualizar(Produto entidade) throws GerenciadorException {

    }

    @Override
    public void deletar(Integer id) throws GerenciadorException {

    }
}
