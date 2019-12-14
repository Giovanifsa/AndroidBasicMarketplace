package giovani.androidmarketplace.dados.conectores;

public enum EnumGerenciadorBanco {
    SQLite("SQLite");

    private String nomeSGBD;

    EnumGerenciadorBanco(String nomeSGBD) {
        this.nomeSGBD = nomeSGBD;
    }

    public String getNomeSGBD() {
        return nomeSGBD;
    }
}
