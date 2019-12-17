package giovani.androidmarketplace.dados.conectores.impl;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileNotFoundException;
import java.io.IOException;

import giovani.androidmarketplace.dados.conectores.EnumGerenciadorBanco;
import giovani.androidmarketplace.dados.conectores.IConectorBanco;
import giovani.androidmarketplace.dados.daos.ICriadorDAOs;
import giovani.androidmarketplace.dados.daos.impl.sqlite.CriadorDAOsSQLite;
import giovani.androidmarketplace.servico.ContextoAplicacao;
import giovani.androidmarketplace.utils.BancoDadosUtil;
import giovani.androidmarketplace.utils.Logger;

public class ConectorBancoSQLite extends SQLiteOpenHelper implements IConectorBanco {
    private ContextoAplicacao contextoAplicacao;

    public ConectorBancoSQLite(ContextoAplicacao contextoAplicacao, String nomeBanco) throws FileNotFoundException {
        super(contextoAplicacao.getContextoAndroid(), nomeBanco, null, BancoDadosUtil.getUltimaVersaoMigration(contextoAplicacao, EnumGerenciadorBanco.SQLite));
        this.contextoAplicacao = contextoAplicacao;

        Logger.info(this, "Banco de Dados SQLite iniciado com sucesso.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Logger.info(this, "Iniciando primeiro migration SQLite.");

        try {
            db.beginTransaction();

            String conteudoMigration = BancoDadosUtil.getMigrationBancoDadosPorVersao(contextoAplicacao, EnumGerenciadorBanco.SQLite, 1);
            db.execSQL(conteudoMigration);

            Logger.info(this, "Aplicado Migration inicial com sucesso.");

            db.setTransactionSuccessful();
        } catch (IOException ex) {
            Logger.error(this, "Falha ao executar migration SQLite.");

            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Logger.info(this, "O Banco de Dados SQLite requisitou um upgrade de versão.");

        try {
            db.beginTransaction();

            for (int versaoMigration = (oldVersion + 1); versaoMigration <= newVersion; versaoMigration++) {
                Logger.info(this, "Buscando versão {0} de migration.", versaoMigration);

                String conteudoMigration = BancoDadosUtil.getMigrationBancoDadosPorVersao(contextoAplicacao, EnumGerenciadorBanco.SQLite, versaoMigration);
                db.execSQL(conteudoMigration);

                Logger.info(this, "Aplicado Migration {0} com sucesso.", versaoMigration);
            }

            db.setTransactionSuccessful();
        } catch (IOException ex) {
            Logger.error(this, "Falha ao executar migration SQLite.");

            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public ICriadorDAOs getCriadorDAOs() {
        return new CriadorDAOsSQLite(this);
    }
}
