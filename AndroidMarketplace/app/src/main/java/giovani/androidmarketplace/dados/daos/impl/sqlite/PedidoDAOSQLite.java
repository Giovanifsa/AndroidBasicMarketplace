package giovani.androidmarketplace.dados.daos.impl.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import giovani.androidmarketplace.dados.conectores.impl.ConectorBancoSQLite;
import giovani.androidmarketplace.dados.daos.IPedidoDAO;
import giovani.androidmarketplace.dados.entidades.Pedido;
import giovani.androidmarketplace.exceptions.DAOException;
import giovani.androidmarketplace.exceptions.EnumExceptionsFixas;
import giovani.androidmarketplace.utils.DateUtil;

public class PedidoDAOSQLite extends AbstractDAOSQLite implements IPedidoDAO {
    public PedidoDAOSQLite(ConectorBancoSQLite conectorSQLite) {
        super(conectorSQLite);
    }

    @Override
    public Pedido salvar(Pedido entidade) throws DAOException {
        SQLiteDatabase database = iniciarEscrita();

        ContentValues valoresInsert = gerarConteudoInsertParaPedido(entidade);
        long idInserido = database.insert(Pedido.TABELA_PEDIDO, null, valoresInsert);

        if (idInserido == -1) {
            finalizarTransacao(database, false);

            throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_INSERIR_ENTIDADE, Pedido.TABELA_PEDIDO);
        }

        finalizarTransacao(database, true);

        return buscar(new Long(idInserido).intValue());
    }

    @Override
    public Pedido buscar(Integer id) {
        SQLiteDatabase database = iniciarLeitura();

        Cursor consulta = database.query(
                Pedido.TABELA_PEDIDO,
                new String[] {
                        Pedido.COLUNA_IDPEDIDO,
                        Pedido.COLUNA_CLIENTE,
                        Pedido.COLUNA_DATAPEDIDO,
                        Pedido.COLUNA_ENDERECO,
                        Pedido.COLUNA_IDUSUARIO,
                        Pedido.COLUNA_TOTALITENS,
                        Pedido.COLUNA_TOTALPRODUTOS,
                        Pedido.COLUNA_VALORTOTAL
                },
                Pedido.COLUNA_IDPEDIDO + " = ?",
                new String[] {id.toString()},
                null, null, null
        );

        if (consulta.moveToFirst()) {
            Pedido pedido = new Pedido();

            pedido.setIdPedido(getInt(consulta, Pedido.COLUNA_IDPEDIDO));
            pedido.setCliente(getString(consulta, Pedido.COLUNA_CLIENTE));
            pedido.setDataPedido(DateUtil.getDDMMYYYYDate(getString(consulta, Pedido.COLUNA_DATAPEDIDO)));
            pedido.setEndereco(getString(consulta, Pedido.COLUNA_ENDERECO));
            pedido.setIdUsuario(getInt(consulta, Pedido.COLUNA_IDUSUARIO));
            pedido.setTotalItens(new BigDecimal(getString(consulta, Pedido.COLUNA_TOTALITENS)));
            pedido.setTotalProdutos(new BigDecimal(getString(consulta, Pedido.COLUNA_TOTALPRODUTOS)));
            pedido.setValorTotal(new BigDecimal(getString(consulta, Pedido.COLUNA_VALORTOTAL)));

            return pedido;
        }

        return null;
    }

    @Override
    public void atualizar(Pedido entidade) throws DAOException {
        if (entidade.getId() != null && buscar(entidade.getId()) != null) {
            SQLiteDatabase database = iniciarEscrita();

            ContentValues valoresAtualizacao = gerarConteudoInsertParaPedido(entidade);

            if (database.update(Pedido.TABELA_PEDIDO, valoresAtualizacao,
                    Pedido.COLUNA_IDPEDIDO + " = ?", new String[] {entidade.getId().toString()}) == 0) {

                finalizarTransacao(database, false);

                throw new DAOException(EnumExceptionsFixas.DAO_FALHA_ATUALIZAR_ENTIDADE, Pedido.TABELA_PEDIDO, entidade.getId());
            }

            finalizarTransacao(database, true);
        }

        else {
            throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_ENCONTRAR_ENTIDADE, Pedido.TABELA_PEDIDO, entidade.getId());
        }
    }

    @Override
    public void deletar(Integer id) throws DAOException {
        if (id != null && buscar(id) != null) {
            SQLiteDatabase database = iniciarEscrita();

            if (database.delete(Pedido.TABELA_PEDIDO, Pedido.COLUNA_IDPEDIDO + " = ?",
                                                                new String[] {id.toString()}) == 0) {
                finalizarTransacao(database, false);

                throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_EXCLUIR_ENTIDADE, Pedido.TABELA_PEDIDO, id);
            }

            finalizarTransacao(database, true);
        }

        else {
            throw new DAOException(EnumExceptionsFixas.DAO_FALHA_AO_ENCONTRAR_ENTIDADE, Pedido.TABELA_PEDIDO, id);
        }
    }

    private ContentValues gerarConteudoInsertParaPedido(Pedido entidade) {
        ContentValues conteudoEscrita = new ContentValues();

        conteudoEscrita.put(Pedido.COLUNA_CLIENTE, entidade.getCliente());
        conteudoEscrita.put(Pedido.COLUNA_DATAPEDIDO, DateUtil.getDDMMYYYY(entidade.getDataPedido()));
        conteudoEscrita.put(Pedido.COLUNA_ENDERECO, entidade.getEndereco());
        conteudoEscrita.put(Pedido.COLUNA_IDUSUARIO, entidade.getIdUsuario());
        conteudoEscrita.put(Pedido.COLUNA_TOTALITENS, entidade.getTotalItens().toString());
        conteudoEscrita.put(Pedido.COLUNA_VALORTOTAL, entidade.getValorTotal().toString());
        conteudoEscrita.put(Pedido.COLUNA_TOTALPRODUTOS, entidade.getTotalProdutos().toString());

        return conteudoEscrita;
    }

    @Override
    public List<Pedido> getAllPedidos() {
        List<Pedido> listaPedidos = new ArrayList<>();

        SQLiteDatabase database = iniciarLeitura();

        Cursor consulta = database.query(
                Pedido.TABELA_PEDIDO,
                new String[] {
                        Pedido.COLUNA_IDPEDIDO
                },
                null, null,
                null, null, null
        );

        while (consulta.moveToNext()) {
            listaPedidos.add(buscar(getInt(consulta, Pedido.COLUNA_IDPEDIDO)));
        }

        return listaPedidos;
    }
}
