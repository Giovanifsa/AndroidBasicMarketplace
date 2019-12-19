package giovani.androidmarketplace.dados.daos.impl.sqlite;

import giovani.androidmarketplace.dados.conectores.impl.ConectorBancoSQLite;
import giovani.androidmarketplace.dados.daos.ICriadorDAOs;
import giovani.androidmarketplace.dados.daos.IPedidoDAO;
import giovani.androidmarketplace.dados.daos.IPedidoItemDAO;
import giovani.androidmarketplace.dados.daos.IProdutoDAO;
import giovani.androidmarketplace.dados.daos.IUsuarioDAO;

public class CriadorDAOsSQLite implements ICriadorDAOs {
    private ConectorBancoSQLite conectorSQLite;

    public CriadorDAOsSQLite(ConectorBancoSQLite conectorSQLite) {
        this.conectorSQLite = conectorSQLite;
    }

    @Override
    public IUsuarioDAO getUsuarioDAO() {
        return new UsuarioDAOSQLite(conectorSQLite);
    }

    @Override
    public IProdutoDAO getProdutoDAO() {
        return new ProdutoDAOSQLite(conectorSQLite);
    }

    @Override
    public IPedidoDAO getPedidoDAO() {
        return new PedidoDAOSQLite(conectorSQLite);
    }

    @Override
    public IPedidoItemDAO getPedidoItemDAO() {
        return new PedidoItemDAOSQLite(conectorSQLite);
    }
}
