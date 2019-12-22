package giovani.androidmarketplace.dados.entidades;

import giovani.androidmarketplace.dados.constantes.EnumPerguntaSeguranca;

public class Usuario extends AbstractEntidade {
    public static final String TABELA_USUARIO = "Usuario";
    public static final String COLUNA_IDUSUARIO = "idUsuario";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_LOGIN = "login";
    public static final String COLUNA_SENHA = "senha";
    public static final String COLUNA_NUMEROPERGUNTASEGURANCA = "numeroPerguntaSeguranca";
    public static final String COLUNA_RESPOSTAPERGUNTASEGURANCA = "respostaPerguntaSeguranca";

    private Integer idUsuario;
    private String nome;
    private String login;
    private String senha;
    private EnumPerguntaSeguranca numeroPerguntaSeguranca;
    private String respostaPerguntaSeguranca;

    @Override
    public Integer getId() {
        return getIdUsuario();
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public EnumPerguntaSeguranca getNumeroPerguntaSeguranca() {
        return numeroPerguntaSeguranca;
    }

    public String getRespostaPerguntaSeguranca() {
        return respostaPerguntaSeguranca;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumeroPerguntaSeguranca(EnumPerguntaSeguranca numeroPerguntaSeguranca) {
        this.numeroPerguntaSeguranca = numeroPerguntaSeguranca;
    }

    public void setRespostaPerguntaSeguranca(String respostaPerguntaSeguranca) {
        this.respostaPerguntaSeguranca = respostaPerguntaSeguranca;
    }
}
