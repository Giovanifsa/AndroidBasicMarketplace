package giovani.androidmarketplace.utils;

import android.net.Uri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import giovani.androidmarketplace.dados.conectores.EnumGerenciadorBanco;

public class BancoDadosUtil {
    private BancoDadosUtil() {

    }

    public static int getUltimaVersaoMigration(EnumGerenciadorBanco bancoDados) {
        Uri uriRawResources = Uri.parse("android.resource://giovani.androidmarketplace/raw/");
        File diretorioRaw = new File(uriRawResources.getPath());

        String regexBuscaMigration = "^V(\\d+)_" + bancoDados.getNomeSGBD() + "_(\\S+).sql$";
        Pattern patternCompilado = Pattern.compile(regexBuscaMigration);

        int ultimaVersaoEncontrado = 0;

        for (File arquivo : diretorioRaw.listFiles()) {
            if (arquivo.isFile()) {
                Matcher matcher = patternCompilado.matcher(arquivo.getName());

                if (matcher.matches()) {
                    int versaoArquivoProcessado = Integer.valueOf(matcher.group(1));

                    if (versaoArquivoProcessado > ultimaVersaoEncontrado) {
                        ultimaVersaoEncontrado = versaoArquivoProcessado;
                    }
                }
            }
        }

        return ultimaVersaoEncontrado;
    }

    public static String getMigrationBancoDadosPorVersao(EnumGerenciadorBanco bancoDados, int versaoMigration) throws IOException {
        Uri uriRawResources = Uri.parse("android.resource://giovani.androidmarketplace/raw/");
        File diretorioRaw = new File(uriRawResources.getPath());

        String regexBuscaMigration = "^V(\\d+)_" + bancoDados.getNomeSGBD() + "_(\\S+).sql$";
        Pattern patternCompilado = Pattern.compile(regexBuscaMigration);

        for (File arquivo : diretorioRaw.listFiles()) {
            if (arquivo.isFile()) {
                Matcher matcher = patternCompilado.matcher(arquivo.getName());

                if (matcher.matches()) {
                    int versaoArquivoProcessado = Integer.valueOf(matcher.group(1));

                    if (versaoArquivoProcessado == versaoMigration) {
                        return ArquivoUtil.lerConteudoArquivoParaString(arquivo);
                    }
                }
            }
        }

        throw new FileNotFoundException("A versão de Migration " + versaoMigration + " não foi encontrada para o SGBD " + bancoDados.getNomeSGBD());
    }
}
