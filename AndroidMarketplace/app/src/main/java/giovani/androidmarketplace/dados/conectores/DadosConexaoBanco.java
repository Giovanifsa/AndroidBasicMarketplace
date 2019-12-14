package giovani.androidmarketplace.dados.conectores;

public class DadosConexaoBanco {
    private String usuario;
    private String senha;
    private String urlConexao;

    public DadosConexaoBanco(String usuario, String senha, String urlConexao) {
        this.usuario = usuario;
        this.senha = senha;
        this.urlConexao = urlConexao;
    }

    public String getSenha() {
        return senha;
    }

    public String getUrlConexao() {
        return urlConexao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setUrlConexao(String urlConexao) {
        this.urlConexao = urlConexao;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
