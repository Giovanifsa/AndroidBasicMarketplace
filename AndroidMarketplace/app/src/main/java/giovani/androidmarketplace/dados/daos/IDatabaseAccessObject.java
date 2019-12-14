package giovani.androidmarketplace.dados.daos;

import giovani.androidmarketplace.dados.entidades.AbstractEntidade;

public interface IDatabaseAccessObject<E extends AbstractEntidade> {
    public E salvar(E entidade);
    public E atualizar(E entidade);
    public E buscar(Integer id);
    public void deletar(Integer id);
}
