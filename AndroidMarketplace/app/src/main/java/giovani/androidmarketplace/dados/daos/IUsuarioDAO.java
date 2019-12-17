package giovani.androidmarketplace.dados.daos;

import giovani.androidmarketplace.dados.entidades.Usuario;

public interface IUsuarioDAO extends IDatabaseAccessObject<Usuario> {
    Usuario buscarPorLogin(String login);
}
