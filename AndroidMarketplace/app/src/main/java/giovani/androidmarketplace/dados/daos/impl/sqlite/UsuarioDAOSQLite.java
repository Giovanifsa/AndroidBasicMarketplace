package giovani.androidmarketplace.dados.daos.impl.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import giovani.androidmarketplace.dados.conectores.impl.ConectorBancoSQLite;
import giovani.androidmarketplace.dados.constantes.EnumPerguntaSeguranca;
import giovani.androidmarketplace.dados.daos.IUsuarioDAO;
import giovani.androidmarketplace.dados.entidades.Usuario;
import giovani.androidmarketplace.exceptions.DAOException;
import giovani.androidmarketplace.exceptions.EnumExceptionsFixas;

public class UsuarioDAOSQLite extends AbstractDAOSQLite implements IUsuarioDAO {
    public UsuarioDAOSQLite(ConectorBancoSQLite conectorSQLite) {
        super(conectorSQLite);
    }

    @Override
    public Usuario salvar(Usuario entidade) throws DAOException {
        SQLiteDatabase database = iniciarEscrita();

        ContentValues valoresInsercao = gerarConteudoInsertParaUsuario(entidade);
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

            ContentValues valoresAtualizacao = gerarConteudoInsertParaUsuario(entidade);

            if (database.update(Usuario.TABELA_USUARIO, valoresAtualizacao,
                    Usuario.COLUNA_IDUSUARIO + " = ?", new String[] {entidade.getId().toString()}) == 0) {
                finalizarTransacao(database, false);

                throw new DAOException(EnumExceptionsFixas.DAO_FALHA_ATUALIZAR_ENTIDADE, Usuario.TABELA_USUARIO, entidade.getId());
            }

            finalizarTransacao(database, true);
        }

        else {
            throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_ENCONTRAR_ENTIDADE, Usuario.TABELA_USUARIO, entidade.getId());
        }
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

            finalizarTransacao(database, true);
        }

        else {
            throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_ENCONTRAR_ENTIDADE, Usuario.TABELA_USUARIO, id);
        }
    }

    @Override
    public Usuario buscarPorLogin(String login) {
        if (login != null) {
            SQLiteDatabase database = iniciarLeitura();

            Cursor consulta = database.query(
                    Usuario.TABELA_USUARIO,
                    new String[] { Usuario.COLUNA_IDUSUARIO },
                    Usuario.COLUNA_LOGIN + " = ?",
                    new String[] { login },
                    null, null, null
            );

            if (consulta.moveToFirst()) {
                return buscar(getInt(consulta, Usuario.COLUNA_IDUSUARIO));
            }
        }

        return null;
    }

    private ContentValues gerarConteudoInsertParaUsuario(Usuario entidade) {
        ContentValues conteudoInsert = new ContentValues();

        conteudoInsert.put(Usuario.COLUNA_NOME, entidade.getNome());
        conteudoInsert.put(Usuario.COLUNA_LOGIN, entidade.getLogin());
        conteudoInsert.put(Usuario.COLUNA_SENHA, entidade.getSenha());
        conteudoInsert.put(Usuario.COLUNA_NUMEROPERGUNTASEGURANCA, entidade.getNumeroPerguntaSeguranca().ordinal());
        conteudoInsert.put(Usuario.COLUNA_RESPOSTAPERGUNTASEGURANCA, entidade.getRespostaPerguntaSeguranca());

        return conteudoInsert;
    }
}
