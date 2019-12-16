package giovani.androidmarketplace.dados.daos;

import giovani.androidmarketplace.dados.entidades.AbstractEntidade;
import giovani.androidmarketplace.dados.exceptions.DAOException;

public interface IDatabaseAccessObject<E extends AbstractEntidade> {
    public E salvar(E entidade) throws DAOException;
    public void atualizar(E entidade) throws DAOException;
    public E buscar(Integer id);
    public void deletar(Integer id) throws DAOException;
}
