package giovani.androidmarketplace.dados.daos.impl.sqlite;

import giovani.androidmarketplace.dados.conectores.impl.ConectorBancoSQLite;
import giovani.androidmarketplace.dados.daos.ICriadorDAOs;
import giovani.androidmarketplace.dados.daos.IUsuarioDAO;

public class CriadorDAOsSQLite implements ICriadorDAOs {
    private ConectorBancoSQLite conectorSQLite.

    public CriadorDAOsSQLite(ConectorBancoSQLite conectorSQLite) {
        this.conectorSQLite = conectorSQLite;
    }

    @Override
    public IUsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOSQLite(conectorSQLite);
    }
}
