package giovani.androidmarketplace.servico;

import java.util.ArrayList;
import java.util.List;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.entidades.AbstractEntidade;
import giovani.androidmarketplace.dados.exceptions.GerenciadorException;
import giovani.androidmarketplace.utils.containers.Pair;

public abstract class AbstractGerenciadorCRUD<E extends AbstractEntidade> implements IGerenciadorCRUD<E> {
    private ContextoAplicacao contextoAplicacao;

    public AbstractGerenciadorCRUD(ContextoAplicacao contextoAplicacao) {
        this.contextoAplicacao = contextoAplicacao;
    }

    protected void validarCamposObrigatorios(Pair<Integer, Object>... camposObrigatorios) throws GerenciadorException {
        List<String> camposEmFalta = new ArrayList<>();

        for (Pair<Integer, Object> campoObrigatorio : camposObrigatorios) {
            if (campoObrigatorio.getObjetoDireita() == null) {
                camposEmFalta.add(getString(campoObrigatorio.getObjetoEsquerda()));
            }
        }

        if (!camposEmFalta.isEmpty()) {
            String mensagem = contextoAplicacao.getString(R.string.frase_exception_campos_obrigatorios_em_falta);

            for (String campo : camposEmFalta) {
                mensagem += "\n" + campo;
            }

            throw new GerenciadorException(mensagem);
        }
    }

    public ContextoAplicacao getContextoAplicacao() {
        return contextoAplicacao;
    }

    public String getString(int resId) {
        return contextoAplicacao.getString(resId);
    }

    @Override
    public E salvarOuAtualizar(E entidade) throws GerenciadorException {
        if (entidade.getId() != null) {
            atualizar(entidade);
            return entidade;
        }

        return salvar(entidade);
    }
}
