package giovani.androidmarketplace.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
}
