package giovani.androidmarketplace.dados.constantes;

import giovani.androidmarketplace.R;

public enum EnumPerguntaSeguranca {
    NENHUMA_PERGUNTA_SELECIONADA(R.string.frase_nenhuma_pergunta_selecionada),
    QUAL_FOI_SEU_PRIMEIRO_ANIMAL_ESTIMACAO(R.string.frase_qual_foi_seu_primeiro_animal_estimacao);

    private int chaveTraducao;

    EnumPerguntaSeguranca(int chaveTraducao) {
        this.chaveTraducao = chaveTraducao;
    }

    public int getChaveTraducao() {
        return chaveTraducao;
    }
}
