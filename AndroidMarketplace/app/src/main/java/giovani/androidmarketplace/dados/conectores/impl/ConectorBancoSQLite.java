package giovani.androidmarketplace.dados.conectores.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

import giovani.androidmarketplace.dados.conectores.EnumGerenciadorBanco;
import giovani.androidmarketplace.dados.conectores.IConectorBanco;
import giovani.androidmarketplace.dados.daos.ICriadorDAOs;
import giovani.androidmarketplace.utils.BancoDadosUtil;

public class ConectorBancoSQLite extends SQLiteOpenHelper implements IConectorBanco {
    public ConectorBancoSQLite(Context contexto, String nomeBanco) {
        super(contexto, nomeBanco, null, BancoDadosUtil.getUltimaVersaoMigration(EnumGerenciadorBanco.SQLite));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.beginTransaction();

            for (int versaoMigration = (oldVersion + 1); versaoMigration <= newVersion; versaoMigration++) {
                String conteudoMigration = BancoDadosUtil.getMigrationBancoDadosPorVersao(EnumGerenciadorBanco.SQLite, versaoMigration);
                db.execSQL(conteudoMigration);
            }

            db.setTransactionSuccessful();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public ICriadorDAOs getCriadorDAOs() {
        return null;
    }
}
