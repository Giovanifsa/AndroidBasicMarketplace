package giovani.androidmarketplace.dados.daos.impl.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import giovani.androidmarketplace.dados.conectores.impl.ConectorBancoSQLite;
import giovani.androidmarketplace.dados.daos.IProdutoDAO;
import giovani.androidmarketplace.dados.entidades.Produto;
import giovani.androidmarketplace.exceptions.DAOException;
import giovani.androidmarketplace.exceptions.EnumExceptionsFixas;

public class ProdutoDAOSQLite extends AbstractDAOSQLite implements IProdutoDAO {
    public ProdutoDAOSQLite(ConectorBancoSQLite conectorSQLite) {
        super(conectorSQLite);
    }

    @Override
    public Produto salvar(Produto entidade) throws DAOException {
        SQLiteDatabase database = iniciarEscrita();

        ContentValues valoresInsert = gerarConteudoInsertParaProduto(entidade);
        long idInserido = database.insert(Produto.TABELA_PRODUTO, null, valoresInsert);

        if (idInserido == -1) {
            finalizarTransacao(database, false);

            throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_INSERIR_ENTIDADE, Produto.TABELA_PRODUTO);
        }

        finalizarTransacao(database, true);

        return buscar(new Long(idInserido).intValue());
    }

    @Override
    public Produto buscar(Integer id) {
        SQLiteDatabase database = iniciarLeitura();

        Cursor consulta = database.query(
                Produto.TABELA_PRODUTO,
                new String[] {
                        Produto.COLUNA_IDPRODUTO,
                        Produto.COLUNA_DESCRICAO,
                        Produto.COLUNA_PRECO
                },
                Produto.COLUNA_IDPRODUTO + " = ?",
                new String[] {id.toString()},
                null, null, null
        );

        if (consulta.moveToFirst()) {
            Produto produto = new Produto();

            produto.setIdProduto(getInt(consulta, Produto.COLUNA_IDPRODUTO));
            produto.setDescricao(getString(consulta, Produto.COLUNA_DESCRICAO));
            produto.setPreco(new BigDecimal(getDouble(consulta, Produto.COLUNA_PRECO)));

            return produto;
        }

        return null;
    }

    @Override
    public void atualizar(Produto entidade) throws DAOException {
        if (entidade.getId() != null && buscar(entidade.getId()) != null) {
            SQLiteDatabase database = iniciarEscrita();

            ContentValues valoresAtualizacao = gerarConteudoInsertParaProduto(entidade);

            if (database.update(Produto.TABELA_PRODUTO, valoresAtualizacao,
                    Produto.COLUNA_IDPRODUTO + " = ?", new String[] {entidade.getId().toString()}) == 0) {

                finalizarTransacao(database, false);

                throw new DAOException(EnumExceptionsFixas.DAO_FALHA_ATUALIZAR_ENTIDADE, Produto.TABELA_PRODUTO, entidade.getId());
            }

            finalizarTransacao(database, true);
        }

        else {
            throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_ENCONTRAR_ENTIDADE, Produto.TABELA_PRODUTO, entidade.getId());
        }
    }

    @Override
    public void deletar(Integer id) throws DAOException {
        if (id != null && buscar(id) != null) {
            SQLiteDatabase database = iniciarEscrita();

            if (database.delete(Produto.TABELA_PRODUTO, Produto.COLUNA_IDPRODUTO + " = ?",
                                                                new String[] {id.toString()}) == 0) {
                finalizarTransacao(database, false);

                throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_EXCLUIR_ENTIDADE, Produto.TABELA_PRODUTO, id);
            }

            finalizarTransacao(database, true);
        }

        else {
            throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_ENCONTRAR_ENTIDADE, Produto.TABELA_PRODUTO, id);
        }
    }

    private ContentValues gerarConteudoInsertParaProduto(Produto entidade) {
        ContentValues conteudoEscrita = new ContentValues();

        conteudoEscrita.put(Produto.COLUNA_DESCRICAO, entidade.getDescricao());
        conteudoEscrita.put(Produto.COLUNA_PRECO, entidade.getPreco().doubleValue());

        return conteudoEscrita;
    }

    @Override
    public List<Produto> getAllProdutos() {
        List<Produto> listaProdutos = new ArrayList<>();

        SQLiteDatabase database = iniciarLeitura();

        Cursor consulta = database.query(
                Produto.TABELA_PRODUTO,
                new String[] {
                        Produto.COLUNA_IDPRODUTO
                },
                null, null,
                null, null, null
        );

        while (consulta.moveToNext()) {
            listaProdutos.add(buscar(getInt(consulta, Produto.COLUNA_IDPRODUTO)));
        }

        return listaProdutos;
    }
}
