package giovani.androidmarketplace.servico;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.entidades.Usuario;
import giovani.androidmarketplace.dados.exceptions.GerenciadorException;
import giovani.androidmarketplace.utils.containers.Pair;

public class GerenciadorUsuario extends AbstractGerenciadorCRUD<Usuario> {
    public GerenciadorUsuario(ContextoAplicacao contextoAplicacao) {
        super(contextoAplicacao);
    }

    @Override
    public Usuario buscar(Integer id) {
        return getContextoAplicacao().getCriadorDAOs().getUsuarioDAO().buscar(id);
    }

    @Override
    public Usuario salvar(Usuario entidade) throws GerenciadorException {
        validarCamposObrigatorios(
                Pair.from(R.string.frase_usuario_coluna_nome, (Object) entidade.getNome()),
                Pair.from(R.string.frase_usuario_coluna_login, (Object) entidade.getLogin())
        );

        return null;
    }

    @Override
    public void atualizar(Usuario entidade) throws GerenciadorException {

    }

    @Override
    public void deletar(Integer id) throws GerenciadorException {

    }
}
