package giovani.androidmarketplace.dados.daos.impl.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import giovani.androidmarketplace.dados.conectores.impl.ConectorBancoSQLite;
import giovani.androidmarketplace.dados.constantes.EnumPerguntaSeguranca;
import giovani.androidmarketplace.dados.daos.IUsuarioDAO;
import giovani.androidmarketplace.dados.entidades.Usuario;
import giovani.androidmarketplace.dados.exceptions.DAOException;
import giovani.androidmarketplace.dados.exceptions.EnumExceptionsFixas;

public class UsuarioDAOSQLite extends AbstractDAOSQLite implements IUsuarioDAO {
    public UsuarioDAOSQLite(ConectorBancoSQLite conectorSQLite) {
        super(conectorSQLite);
    }

    @Override
    public Usuario salvar(Usuario entidade) throws DAOException {
        SQLiteDatabase database = iniciarEscrita();

        ContentValues valoresInsercao = new ContentValues();
        valoresInsercao.put(Usuario.COLUNA_NOME, entidade.getNome());
        valoresInsercao.put(Usuario.COLUNA_LOGIN, entidade.getLogin());
        valoresInsercao.put(Usuario.COLUNA_SENHA, entidade.getSenha());
        valoresInsercao.put(Usuario.COLUNA_NUMEROPERGUNTASEGURANCA, entidade.getNumeroPerguntaSeguranca().ordinal());
        valoresInsercao.put(Usuario.COLUNA_RESPOSTAPERGUNTASEGURANCA, entidade.getRespostaPerguntaSeguranca());

        long idInsercao = database.insert(Usuario.TABELA_USUARIO, null, valoresInsercao);

        if (idInsercao == -1) {
            finalizarTransacao(database, false);

            throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_INSERIR_ENTIDADE, Usuario.TABELA_USUARIO);
        }

        finalizarTransacao(database, true);

        return buscar(new Long(idInsercao).intValue());
    }

    @Override
    public Usuario buscar(Integer id) {
        if (id != null) {
            SQLiteDatabase database = iniciarLeitura();

            Cursor consulta = database.query(
                    Usuario.TABELA_USUARIO,
                    new String[] {
                            Usuario.COLUNA_IDUSUARIO,
                            Usuario.COLUNA_NOME,
                            Usuario.COLUNA_LOGIN,
                            Usuario.COLUNA_SENHA,
                            Usuario.COLUNA_NUMEROPERGUNTASEGURANCA,
                            Usuario.COLUNA_RESPOSTAPERGUNTASEGURANCA
                    },
                    Usuario.COLUNA_IDUSUARIO + " = ?",
                    new String[] {id.toString()},
                    null, null, null
            );

            if (consulta.moveToFirst()) {
                Usuario usuario = new Usuario();

                usuario.setIdUsuario(getInt(consulta, Usuario.COLUNA_IDUSUARIO));
                usuario.setLogin(getString(consulta, Usuario.COLUNA_LOGIN));
                usuario.setNome(getString(consulta, Usuario.COLUNA_NOME));
                usuario.setSenha(getString(consulta, Usuario.COLUNA_SENHA));
                usuario.setNumeroPerguntaSeguranca(EnumPerguntaSeguranca.values()[getInt(consulta, Usuario.COLUNA_NUMEROPERGUNTASEGURANCA)]);
                usuario.setRespostaPerguntaSeguranca(getString(consulta, Usuario.COLUNA_RESPOSTAPERGUNTASEGURANCA));

                return usuario;
            }
        }

        return null;
    }

    @Override
    public void atualizar(Usuario entidade) throws DAOException {
        if (entidade.getId() != null && buscar(entidade.getId()) != null) {
            SQLiteDatabase database = iniciarEscrita();

            ContentValues valoresAtualizacao = new ContentValues();
            valoresAtualizacao.put(Usuario.COLUNA_NOME, entidade.getNome());
            valoresAtualizacao.put(Usuario.COLUNA_LOGIN, entidade.getLogin());
            valoresAtualizacao.put(Usuario.COLUNA_SENHA, entidade.getSenha());
            valoresAtualizacao.put(Usuario.COLUNA_RESPOSTAPERGUNTASEGURANCA, entidade.getRespostaPerguntaSeguranca());
            valoresAtualizacao.put(Usuario.COLUNA_NUMEROPERGUNTASEGURANCA, entidade.getNumeroPerguntaSeguranca().ordinal());

            if (database.update(Usuario.TABELA_USUARIO, valoresAtualizacao,
                    Usuario.COLUNA_IDUSUARIO + " = ?", new String[] {entidade.getId().toString()}) == 0) {
                finalizarTransacao(database, false);

                throw new DAOException(EnumExceptionsFixas.DAO_FALHA_ATUALIZAR_ENTIDADE, Usuario.TABELA_USUARIO, entidade.getId());
            }

            finalizarTransacao(database, true);
        }

        throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_ENCONTRAR_ENTIDADE, Usuario.TABELA_USUARIO, entidade.getId());
    }

    @Override
    public void deletar(Integer id) throws DAOException {
        if (id != null && buscar(id) != null) {
            SQLiteDatabase database = iniciarEscrita();

            if (database.delete(Usuario.TABELA_USUARIO, Usuario.COLUNA_IDUSUARIO + " = ?",
                    new String[] {id.toString()}) == 0) {
                finalizarTransacao(database, false);

                throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_EXCLUIR_ENTIDADE, Usuario.TABELA_USUARIO, id);
            }
        }

        throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_ENCONTRAR_ENTIDADE, Usuario.TABELA_USUARIO, id);
    }
}
