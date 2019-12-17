package giovani.androidmarketplace.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import giovani.androidmarketplace.servico.ContextoAplicacao;

public class ArquivoUtil {
    private ArquivoUtil() {

    }

    public static String lerConteudoArquivoParaString(File arquivo) throws FileNotFoundException, IOException {
        String conteudoArquivo = "";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(arquivo))) {
            String tempLinhaArquivo = null;

            while ((tempLinhaArquivo = bufferedReader.readLine()) != null) {
                conteudoArquivo += tempLinhaArquivo;
            }
        }

        return conteudoArquivo;
    }

    public static String lerConteudoArquivoParaString(ContextoAplicacao contextoAplicacao, int resId) throws IOException {
        InputStream inputStream = contextoAplicacao.getContextoAndroid().getResources().openRawResource(resId);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String dadosArquivo = "";
        String linhaLida = "";

        while ((linhaLida = bufferedReader.readLine()) != null) {
            dadosArquivo += linhaLida;
        }

        return dadosArquivo;
    }
}
