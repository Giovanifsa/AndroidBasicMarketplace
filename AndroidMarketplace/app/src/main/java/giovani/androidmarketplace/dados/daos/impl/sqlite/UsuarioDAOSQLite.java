package giovani.androidmarketplace.dados.daos.impl.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteStatement;

import giovani.androidmarketplace.dados.conectores.impl.ConectorBancoSQLite;
import giovani.androidmarketplace.dados.daos.IUsuarioDAO;
import giovani.androidmarketplace.dados.entidades.Usuario;

public class UsuarioDAOSQLite extends DAOSQLite implements IUsuarioDAO {
    public UsuarioDAOSQLite(ConectorBancoSQLite conectorSQLite) {
        super(conectorSQLite);
    }

    @Override
    public Usuario salvar(Usuario entidade) {
        SQLiteDatabase database = iniciarEscrita();

        StringBuilder sb = new StringBuilder();
        sb.append(" INSERT INTO Usuario (");
        sb.append("     nome, ");
        sb.append("     login, ");
        sb.append("     senha, ");
        sb.append("     numeroPerguntaSeguranca, ");
        sb.append("     respostaPerguntaSeguranca ");
        sb.append(" ) VALUES (?, ?, ?, ?, ?) ");

        SQLiteStatement statement = database.compileStatement(sb.toString());
        statement.bindString(1, entidade.getNome());
        statement.bindString(2, entidade.getLogin());
        statement.bindString(3, entidade.getSenha());
        statement.bindLong(4, entidade.getNumeroPerguntaSeguranca().ordinal());
        statement.bindString(5, entidade.getRespostaPerguntaSeguranca());

        Integer idInserido = new Long(statement.executeInsert()).intValue();
        finalizarTransacao(database, true);

        return buscar(idInserido);
    }

    @Override
    public Usuario buscar(Integer id) {
        if (id != null) {
            SQLiteDatabase database = iniciarLeitura();

            Cursor consulta = database.query(
                    "Usuario",
                    new String[] {
                            "idUsuario",
                            "nome",
                            "login",
                            "senha",
                            "numeroPerguntaSeguranca",
                            "respostaPerguntaSeguranca"
                    },
                    "idUsuario = ?",
                    new String[] {id.toString()},
                    null, null, null
            );

            if (consulta.moveToFirst()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(getInt(consulta, "idUsuario"));
            }
        }

        return null;
    }
}
