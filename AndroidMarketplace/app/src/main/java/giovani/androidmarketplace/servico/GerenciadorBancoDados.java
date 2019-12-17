package giovani.androidmarketplace.servico;

import java.io.IOException;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.conectores.DadosConexaoBanco;
import giovani.androidmarketplace.dados.conectores.EnumGerenciadorBanco;
import giovani.androidmarketplace.dados.conectores.IConectorBanco;
import giovani.androidmarketplace.dados.conectores.impl.ConectorBancoSQLite;
import giovani.androidmarketplace.exceptions.GerenciadorException;

public class GerenciadorBancoDados {
    private static final String NOME_BANCO_DADOS_APLICACAO = "AndroidMarketplace";

    private final ContextoAplicacao contextoAplicacao;
    private IConectorBanco conectorBancoDados;

    public GerenciadorBancoDados(ContextoAplicacao contextoAplicacao) {
        this.contextoAplicacao = contextoAplicacao;
    }

    public void iniciarBancoDados(EnumGerenciadorBanco gerenciadorBanco, DadosConexaoBanco dadosConexaoBanco) throws GerenciadorException {
        try {
            if (conectorBancoDados == null) {
                switch (gerenciadorBanco) {
                    case SQLite: {
                        conectorBancoDados = new ConectorBancoSQLite(contextoAplicacao, NOME_BANCO_DADOS_APLICACAO);
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(contextoAplicacao.getString(R.string.frase_falha_ao_conectar_ao_banco_de_dados));
        }
    }

    public IConectorBanco getConectorBancoDados() {
        return conectorBancoDados;
    }
}
