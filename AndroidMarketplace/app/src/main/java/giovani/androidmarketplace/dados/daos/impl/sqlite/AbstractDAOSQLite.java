package giovani.androidmarketplace.dados.daos.impl.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import giovani.androidmarketplace.dados.conectores.impl.ConectorBancoSQLite;

public abstract class AbstractDAOSQLite {
    private ConectorBancoSQLite conectorSQLite;

    public AbstractDAOSQLite(ConectorBancoSQLite conectorSQLite) {
        this.conectorSQLite = conectorSQLite;
    }

    public SQLiteDatabase iniciarEscrita() {
        SQLiteDatabase database = getConectorSQLite().getWritableDatabase();
        database.beginTransaction();

        return database;
    }

    public SQLiteDatabase iniciarLeitura() {
        return getConectorSQLite().getReadableDatabase();
    }

    public void finalizarTransacao(SQLiteDatabase database, boolean sucesso) {
        if (sucesso) {
            database.setTransactionSuccessful();
        }

        database.endTransaction();
    }

    public int getInt(Cursor consulta, String nomeColuna) {
        return consulta.getInt(consulta.getColumnIndex(nomeColuna));
    }

    public String getString(Cursor consulta, String nomeColuna) {
        return consulta.getString(consulta.getColumnIndex(nomeColuna));
    }

    public long getLong(Cursor consulta, String nomeColuna) {
        return consulta.getLong(consulta.getColumnIndex(nomeColuna));
    }

    public double getDouble(Cursor consulta, String nomeColuna) {
        return consulta.getDouble(consulta.getColumnIndex(nomeColuna));
    }

    public ConectorBancoSQLite getConectorSQLite() {
        return conectorSQLite;
    }
}
