package giovani.androidmarketplace.servico;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.constantes.EnumPerguntaSeguranca;
import giovani.androidmarketplace.dados.daos.IUsuarioDAO;
import giovani.androidmarketplace.dados.entidades.Usuario;
import giovani.androidmarketplace.exceptions.DAOException;
import giovani.androidmarketplace.exceptions.GerenciadorException;
import giovani.androidmarketplace.utils.HashUtil;
import giovani.androidmarketplace.utils.containers.Pair;
import giovani.androidmarketplace.utils.containers.Triplet;

public class GerenciadorUsuario extends AbstractGerenciadorCRUD<Usuario> {
    public GerenciadorUsuario(ContextoAplicacao contextoAplicacao) {
        super(contextoAplicacao);
    }

    @Override
    public Usuario buscar(Integer id) {
        return getDAO().buscar(id);
    }

    @Override
    public Usuario salvar(Usuario entidade) throws GerenciadorException {
        validarCamposObrigatoriosUsuario(entidade);
        validarPerguntaSeguranca(entidade);
        validarTamanhoCamposUsuario(entidade);
        validarExistenciaUsuarioComLoginIgual(entidade);

        try {
            Usuario usuarioParaPersistir = copiarUsuario(entidade);
            usuarioParaPersistir.setSenha(new String(HashUtil.aplicarMD5(usuarioParaPersistir.getSenha().getBytes())));

            Usuario salvo = getDAO().salvar(usuarioParaPersistir);
            salvo.setSenha(entidade.getSenha());

            return salvo;
        } catch (DAOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_salvar_entidade), "Usuario");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_aplicar_algoritmo_assinatura), "MD5");
        }
    }

    @Override
    public void atualizar(Usuario entidade) throws GerenciadorException {
        validarCamposObrigatoriosUsuario(entidade);
        validarPerguntaSeguranca(entidade);
        validarTamanhoCamposUsuario(entidade);

        Usuario usuarioCorrigido = corrigirCamposAtualizaveis(entidade);

        try {
            getDAO().atualizar(usuarioCorrigido);
        } catch (DAOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_atualizar_entidade), "Usuario");
        }
    }

    @Override
    public void deletar(Integer id) throws GerenciadorException {
        try {
            getDAO().deletar(id);
        } catch (DAOException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_deletar_entidade), "Usuario");
        }
    }

    private void validarPerguntaSeguranca(Usuario entidade) throws GerenciadorException {
        if (entidade.getNumeroPerguntaSeguranca() == EnumPerguntaSeguranca.NENHUMA_PERGUNTA_SELECIONADA) {
            throw new GerenciadorException(getString(R.string.frase_obrigatorio_selecionar_tipo_pergunta_seguranca));
        }
    }

    public Usuario realizarLogin(String login, String senha) throws GerenciadorException {
        try {
            Usuario usuarioEncontrado = getDAO().buscarPorLogin(login);

            if (usuarioEncontrado != null) {
                String senhaHasheada = new String(HashUtil.aplicarMD5(senha.getBytes()));

                if (usuarioEncontrado.getSenha().equals(senhaHasheada)) {
                    return usuarioEncontrado;
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            throw new GerenciadorException(getString(R.string.frase_falha_ao_fazer_login));
        }

        throw new GerenciadorException(getString(R.string.frase_login_usuario_nao_encontrado), login);
    }

    private Usuario copiarUsuario(Usuario entidade) {
        Usuario usuario = new Usuario();

        usuario.setIdUsuario(entidade.getIdUsuario());
        usuario.setNome(entidade.getNome());
        usuario.setLogin(entidade.getLogin());
        usuario.setNumeroPerguntaSeguranca(entidade.getNumeroPerguntaSeguranca());
        usuario.setRespostaPerguntaSeguranca(entidade.getRespostaPerguntaSeguranca());
        usuario.setSenha(entidade.getSenha());

        return usuario;
    }

    private Usuario corrigirCamposAtualizaveis(Usuario entidade) throws GerenciadorException {
        try {
            Usuario usuarioPersistido = getDAO().buscar(entidade.getId());

            Usuario usuario = copiarUsuario(entidade);
            usuario.setLogin(usuarioPersistido.getLogin());

            if (!entidade.getSenha().equals(usuarioPersistido.getSenha())) {
                usuario.setSenha(new String(HashUtil.aplicarMD5(entidade.getSenha().getBytes())));
            }

            return usuario;
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            throw new GerenciadorException(getString(R.string.frase_falha_ao_aplicar_algoritmo_assinatura), "MD5");
        }
    }

    private void validarExistenciaUsuarioComLoginIgual(Usuario entidade) throws GerenciadorException {
        if (getDAO().buscarPorLogin(entidade.getLogin()) != null) {
            throw new GerenciadorException(getString(R.string.frase_usuario_com_login_ja_existe));
        }
    }

    private void validarTamanhoCamposUsuario(Usuario entidade) throws GerenciadorException {
        validarTamanhoCampos(
                Triplet.from(R.string.frase_usuario_coluna_nome, entidade.getNome(), 80),
                Triplet.from(R.string.frase_usuario_coluna_login, entidade.getLogin(), 80),
                Triplet.from(R.string.frase_usuario_resposta_perg_seguranca, entidade.getRespostaPerguntaSeguranca(), 40),
                Triplet.from(R.string.palavra_usuario_coluna_senha, entidade.getSenha(), 80));
    }

    private void validarCamposObrigatoriosUsuario(Usuario entidade) throws GerenciadorException {
        validarCamposObrigatorios(
                Pair.from(R.string.frase_usuario_coluna_nome, (Object) entidade.getNome()),
                Pair.from(R.string.frase_usuario_coluna_login, (Object) entidade.getLogin()),
                Pair.from(R.string.palavra_usuario_coluna_senha, (Object) entidade.getSenha()),
                Pair.from(R.string.frase_usuario_tipo_pergunta_seguranca, (Object) entidade.getNumeroPerguntaSeguranca()),
                Pair.from(R.string.frase_usuario_resposta_perg_seguranca, (Object) entidade.getRespostaPerguntaSeguranca()));
    }

    public IUsuarioDAO getDAO() {
        return getContextoAplicacao().getCriadorDAOs().getUsuarioDAO();
    }
}
