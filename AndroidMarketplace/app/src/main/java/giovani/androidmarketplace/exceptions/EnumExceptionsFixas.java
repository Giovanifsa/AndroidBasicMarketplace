package giovani.androidmarketplace.exceptions;

public enum EnumExceptionsFixas {
    DAO_FALHA_ATUALIZAR_ENTIDADE("Houve uma falha ao atualizar a entidade {0} com ID {1}."),
    DAO_FALHA_AO_INSERIR_ENTIDADE("Não foi possível inserir a entidade {0}."),
    DAO_FALHA_AO_ENCONTRAR_ENTIDADE("A entidade {0} com ID {1} não foi encontrada."),
    DAO_FALHA_AO_EXCLUIR_ENTIDADE("A entidade {0} com ID {1} não pôde ser removida."),
    ;

    private String format;

    EnumExceptionsFixas(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
