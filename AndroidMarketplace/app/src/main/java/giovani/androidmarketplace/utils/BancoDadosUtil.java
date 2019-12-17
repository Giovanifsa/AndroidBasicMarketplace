package giovani.androidmarketplace.utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import giovani.androidmarketplace.dados.conectores.EnumGerenciadorBanco;
import giovani.androidmarketplace.servico.ContextoAplicacao;

public class BancoDadosUtil {
    private BancoDadosUtil() {

    }

    public static int getUltimaVersaoMigration(ContextoAplicacao contextoAplicacao, EnumGerenciadorBanco bancoDados) throws FileNotFoundException {
        int ultimaVersaoEncontrado = 0;
        boolean continuarBusca = true;

        do {
            int versaoSendoBuscada = ultimaVersaoEncontrado + 1;

            int idResource = contextoAplicacao.getContextoAndroid().getResources().getIdentifier(
                    prepararNomeArquivoMigration(bancoDados, versaoSendoBuscada), "raw", contextoAplicacao.getContextoAndroid().getPackageName());

            if ((continuarBusca = idResource != 0)) {
                ultimaVersaoEncontrado = versaoSendoBuscada;
            }
        } while (continuarBusca);

        if (ultimaVersaoEncontrado == 0) {
            throw new FileNotFoundException("Nenhuma versão de migration foi encontrada para o SGBD " + bancoDados.getNomeSGBD());
        }

        return ultimaVersaoEncontrado;
    }

    public static String getMigrationBancoDadosPorVersao(ContextoAplicacao contextoAplicacao, EnumGerenciadorBanco bancoDados, int versaoMigration) throws IOException {
        int idResource = contextoAplicacao.getContextoAndroid().getResources().getIdentifier(
                prepararNomeArquivoMigration(bancoDados, versaoMigration), "raw", contextoAplicacao.getContextoAndroid().getPackageName());

        if (idResource == 0) {
            throw new FileNotFoundException("A versão de Migration " + versaoMigration + " não foi encontrada para o SGBD " + bancoDados.getNomeSGBD());
        }

        return ArquivoUtil.lerConteudoArquivoParaString(contextoAplicacao, idResource);
    }

    private static String prepararNomeArquivoMigration(EnumGerenciadorBanco gerenciadorBanco, int versao) {
        return "v" + versao + "_" + gerenciadorBanco.getNomeSGBD().toLowerCase();
    }
}
