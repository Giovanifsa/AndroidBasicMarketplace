package giovani.androidmarketplace.dados.daos;

import giovani.androidmarketplace.dados.entidades.AbstractEntidade;
import giovani.androidmarketplace.exceptions.DAOException;

public interface IDatabaseAccessObject<E extends AbstractEntidade> {
    E salvar(E entidade) throws DAOException;
    E buscar(Integer id);

    void atualizar(E entidade) throws DAOException;
    void deletar(Integer id) throws DAOException;
}
