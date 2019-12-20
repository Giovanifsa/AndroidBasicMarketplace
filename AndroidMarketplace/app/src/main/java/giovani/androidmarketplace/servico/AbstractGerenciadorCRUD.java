package giovani.androidmarketplace.servico;

import java.util.ArrayList;
import java.util.List;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.daos.IDatabaseAccessObject;
import giovani.androidmarketplace.dados.entidades.AbstractEntidade;
import giovani.androidmarketplace.exceptions.DAOException;
import giovani.androidmarketplace.exceptions.GerenciadorException;
import giovani.androidmarketplace.utils.StringUtil;
import giovani.androidmarketplace.utils.containers.Pair;
import giovani.androidmarketplace.utils.containers.Triplet;

public abstract class AbstractGerenciadorCRUD<E extends AbstractEntidade, D extends IDatabaseAccessObject<E>> implements IGerenciadorCRUD<E> {
    private ContextoAplicacao contextoAplicacao;
    private String nomeEntidade;

    public AbstractGerenciadorCRUD(ContextoAplicacao contextoAplicacao, String nomeEntidade) {
        this.contextoAplicacao = contextoAplicacao;
        this.nomeEntidade = nomeEntidade;
    }

    @Override
    public E salvarOuAtualizar(E entidade) throws GerenciadorException {
        if (entidade.getId() != null) {
            atualizar(entidade);
            return entidade;
        }

        return salvar(entidade);
    }

    @Override
    public E buscar(Integer id) {
        return rotinaBuscarPadrao(id);
    }

    @Override
    public E salvar(E entidade) throws GerenciadorException {
        onPreSalvar(entidade);

        E entidadeSalva = rotinaSalvarPadrao(entidade);

        onPostSalvar(entidadeSalva);

        return entidadeSalva;
    }

    @Override
    public void atualizar(E entidade) throws GerenciadorException {
        onPreAtualizar(entidade);

        rotinaAtualizarPadrao(entidade);

        onPostAtualizar(entidade);
    }

    @Override
    public void deletar(Integer id) throws GerenciadorException {
        rotinaDeletarPadrao(id);
    }

    protected E rotinaBuscarPadrao(Integer id) {
        return getDAO().buscar(id);
    }

    protected E rotinaSalvarPadrao(E entidade) throws GerenciadorException {
        try {
            return getDAO().salvar(entidade);
        } catch (DAOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_salvar_entidade), getNomeEntidade());
        }
    }

    protected void rotinaDeletarPadrao(Integer id) throws GerenciadorException {
        try {
            getDAO().deletar(id);
        } catch (DAOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_deletar_entidade), getNomeEntidade());
        }
    }

    protected void rotinaAtualizarPadrao(E entidade) throws GerenciadorException {
        try {
            getDAO().atualizar(entidade);
        } catch (DAOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_atualizar_entidade), getNomeEntidade());
        }
    }

    protected void validarCamposObrigatorios(Pair<Integer, Object>... camposObrigatorios) throws GerenciadorException {
        List<String> camposEmFalta = new ArrayList<>();

        for (Pair<Integer, Object> campoObrigatorio : camposObrigatorios) {
            if (campoObrigatorio.getObjetoDireita() == null ||
                    (campoObrigatorio.getObjetoDireita() instanceof String &&
                            StringUtil.isBlank((String) campoObrigatorio.getObjetoDireita()))) {

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

    protected void validarTamanhoCampos(Triplet<Integer, String, Integer>... camposStrings) throws GerenciadorException {
        List<String> camposGrandes = new ArrayList<>();

        for (Triplet<Integer, String, Integer> campoValidando : camposStrings) {
            if (campoValidando.getObjetoMeio().length() > campoValidando.getObjetoDireita()) {
                camposGrandes.add(getString(campoValidando.getObjetoEsquerda()));
            }
        }

        if (!camposGrandes.isEmpty()) {
            String mensagem = contextoAplicacao.getString(R.string.frase_exception_campos_muito_grandes);

            for (String campo : camposGrandes) {
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

    public String getNomeEntidade() {
        return nomeEntidade;
    }

    protected void onPreSalvar(E entidade) throws GerenciadorException {

    }

    protected void onPostSalvar(E entidade) throws GerenciadorException {

    }

    protected void onPreAtualizar(E entidade) throws GerenciadorException {

    }

    protected void onPostAtualizar(E entidade) throws GerenciadorException {

    }

    public abstract D getDAO();
}
