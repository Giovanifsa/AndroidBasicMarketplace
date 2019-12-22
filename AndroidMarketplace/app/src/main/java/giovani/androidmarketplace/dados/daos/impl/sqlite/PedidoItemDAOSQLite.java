package giovani.androidmarketplace.dados.daos.impl.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import giovani.androidmarketplace.dados.conectores.impl.ConectorBancoSQLite;
import giovani.androidmarketplace.dados.daos.IPedidoItemDAO;
import giovani.androidmarketplace.dados.entidades.Pedido;
import giovani.androidmarketplace.dados.entidades.PedidoItem;
import giovani.androidmarketplace.exceptions.DAOException;
import giovani.androidmarketplace.exceptions.EnumExceptionsFixas;

public class PedidoItemDAOSQLite extends AbstractDAOSQLite implements IPedidoItemDAO {
    public PedidoItemDAOSQLite(ConectorBancoSQLite conectorSQLite) {
        super(conectorSQLite);
    }

    @Override
    public PedidoItem salvar(PedidoItem entidade) throws DAOException {
        SQLiteDatabase database = iniciarEscrita();

        ContentValues valoresInsert = gerarConteudoInsertParaPedidoItem(entidade);
        long idInserido = database.insert(PedidoItem.TABELA_PEDIDOITEM, null, valoresInsert);

        if (idInserido == -1) {
            finalizarTransacao(database, false);

            throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_INSERIR_ENTIDADE, PedidoItem.TABELA_PEDIDOITEM);
        }

        finalizarTransacao(database, true);

        return buscar(new Long(idInserido).intValue());
    }

    @Override
    public PedidoItem buscar(Integer id) {
        SQLiteDatabase database = iniciarLeitura();

        Cursor consulta = database.query(
                PedidoItem.TABELA_PEDIDOITEM,
                new String[] {
                        PedidoItem.COLUNA_IDPEDIDOITEM,
                        PedidoItem.COLUNA_IDPEDIDO,
                        PedidoItem.COLUNA_IDPRODUTO,
                        PedidoItem.COLUNA_PRECOORIGINAL,
                        PedidoItem.COLUNA_PRECOVENDA,
                        PedidoItem.COLUNA_QUANTIDADE,
                        PedidoItem.COLUNA_VALORDESCONTO
                },
                PedidoItem.COLUNA_IDPEDIDOITEM + " = ?",
                new String[] {id.toString()},
                null, null, null
        );

        if (consulta.moveToFirst()) {
            PedidoItem item = new PedidoItem();

            item.setIdPedidoItem(getInt(consulta, PedidoItem.COLUNA_IDPEDIDOITEM));
            item.setIdPedido(getInt(consulta, PedidoItem.COLUNA_IDPEDIDO));
            item.setIdProduto(getInt(consulta, PedidoItem.COLUNA_IDPRODUTO));
            item.setPrecoOriginal(new BigDecimal(getString(consulta, PedidoItem.COLUNA_PRECOORIGINAL)));
            item.setPrecoVenda(new BigDecimal(getString(consulta, PedidoItem.COLUNA_PRECOVENDA)));
            item.setQuantidade(new BigDecimal(getString(consulta, PedidoItem.COLUNA_QUANTIDADE)));
            item.setValorDesconto(new BigDecimal(getString(consulta, PedidoItem.COLUNA_VALORDESCONTO)));

            return item;
        }

        return null;
    }

    @Override
    public void atualizar(PedidoItem entidade) throws DAOException {
        if (entidade.getId() != null && buscar(entidade.getId()) != null) {
            SQLiteDatabase database = iniciarEscrita();

            ContentValues valoresAtualizacao = gerarConteudoInsertParaPedidoItem(entidade);

            if (database.update(PedidoItem.TABELA_PEDIDOITEM, valoresAtualizacao,
                    PedidoItem.COLUNA_IDPEDIDOITEM + " = ?", new String[] {entidade.getId().toString()}) == 0) {

                finalizarTransacao(database, false);

                throw new DAOException(EnumExceptionsFixas.DAO_FALHA_ATUALIZAR_ENTIDADE, PedidoItem.TABELA_PEDIDOITEM, entidade.getId());
            }

            finalizarTransacao(database, true);
        }

        else {
            throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_ENCONTRAR_ENTIDADE, PedidoItem.TABELA_PEDIDOITEM, entidade.getId());
        }
    }

    @Override
    public void deletar(Integer id) throws DAOException {
        if (id != null && buscar(id) != null) {
            SQLiteDatabase database = iniciarEscrita();

            if (database.delete(PedidoItem.TABELA_PEDIDOITEM, PedidoItem.COLUNA_IDPEDIDOITEM + " = ?",
                                                                new String[] {id.toString()}) == 0) {
                finalizarTransacao(database, false);

                throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_EXCLUIR_ENTIDADE, PedidoItem.TABELA_PEDIDOITEM, id);
            }

            finalizarTransacao(database, true);
        }

        else {
            throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_ENCONTRAR_ENTIDADE, PedidoItem.TABELA_PEDIDOITEM, id);
        }
    }

    private ContentValues gerarConteudoInsertParaPedidoItem(PedidoItem entidade) {
        ContentValues conteudoEscrita = new ContentValues();

        conteudoEscrita.put(PedidoItem.COLUNA_IDPEDIDO, entidade.getIdPedido());
        conteudoEscrita.put(PedidoItem.COLUNA_IDPRODUTO, entidade.getIdProduto());
        conteudoEscrita.put(PedidoItem.COLUNA_PRECOORIGINAL, entidade.getPrecoOriginal().toString());
        conteudoEscrita.put(PedidoItem.COLUNA_PRECOVENDA, entidade.getPrecoVenda().toString());
        conteudoEscrita.put(PedidoItem.COLUNA_QUANTIDADE, entidade.getQuantidade().toString());
        conteudoEscrita.put(PedidoItem.COLUNA_VALORDESCONTO, entidade.getValorDesconto().toString());

        return conteudoEscrita;
    }

    @Override
    public List<PedidoItem> getAllItensParaPedido(Integer idPedido) {
        SQLiteDatabase database = iniciarLeitura();

        Cursor consulta = database.query(
                PedidoItem.TABELA_PEDIDOITEM,
                new String[] {
                        PedidoItem.COLUNA_IDPEDIDOITEM
                },
                PedidoItem.COLUNA_IDPEDIDO + " = ?",
                new String[] {idPedido.toString()},
                null, null, null
        );

        List<PedidoItem> listaItensEncontrados = new ArrayList<>();

        while (consulta.moveToNext()) {
            listaItensEncontrados.add(buscar(getInt(consulta, PedidoItem.COLUNA_IDPEDIDOITEM)));
        }

        return listaItensEncontrados;
    }

    @Override
    public List<PedidoItem> getAllItensContendoProduto(Integer idProduto) {
        SQLiteDatabase database = iniciarLeitura();

        Cursor consulta = database.query(
                PedidoItem.TABELA_PEDIDOITEM,
                new String[] {
                        PedidoItem.COLUNA_IDPEDIDOITEM
                },
                PedidoItem.COLUNA_IDPRODUTO + " = ?",
                new String[] {idProduto.toString()},
                null, null, null
        );

        List<PedidoItem> listaItensEncontrados = new ArrayList<>();

        while (consulta.moveToNext()) {
            listaItensEncontrados.add(buscar(getInt(consulta, PedidoItem.COLUNA_IDPEDIDOITEM)));
        }

        return listaItensEncontrados;
    }
}
