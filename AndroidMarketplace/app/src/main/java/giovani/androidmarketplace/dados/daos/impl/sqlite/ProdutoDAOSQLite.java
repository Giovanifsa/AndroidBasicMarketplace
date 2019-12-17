package giovani.androidmarketplace.dados.daos.impl.sqlite;

import giovani.androidmarketplace.dados.conectores.impl.ConectorBancoSQLite;
import giovani.androidmarketplace.dados.daos.IProdutoDAO;
import giovani.androidmarketplace.dados.entidades.Produto;
import giovani.androidmarketplace.exceptions.DAOException;

public class ProdutoDAOSQLite extends AbstractDAOSQLite implements IProdutoDAO {
    public ProdutoDAOSQLite(ConectorBancoSQLite conectorSQLite) {
        super(conectorSQLite);
    }

    @Override
    public Produto salvar(Produto entidade) throws DAOException {
        return null;
    }

    @Override
    public Produto buscar(Integer id) {
        return null;
    }

    @Override
    public void atualizar(Produto entidade) throws DAOException {

    }

    @Override
    public void deletar(Integer id) throws DAOException {

    }
}
