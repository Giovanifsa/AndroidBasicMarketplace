package giovani.androidmarketplace.servico;

import giovani.androidmarketplace.dados.entidades.AbstractEntidade;
import giovani.androidmarketplace.dados.exceptions.GerenciadorException;

public interface IGerenciadorCRUD<E extends AbstractEntidade> {
    public E buscar(Integer id);
    public E salvarOuAtualizar(E entidade) throws GerenciadorException;
    public E salvar(E entidade) throws GerenciadorException;
    public void atualizar(E entidade) throws GerenciadorException;
    public void deletar(Integer id) throws GerenciadorException;
}
