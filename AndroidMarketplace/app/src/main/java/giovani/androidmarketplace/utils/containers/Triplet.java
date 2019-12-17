package giovani.androidmarketplace.utils.containers;

import androidx.annotation.Nullable;

public class Triplet<E, M, D> {
    private E objetoEsquerda;
    private M objetoMeio;
    private D objetoDireita;

    public static <E, M, D> Triplet<E, M, D> from(E objetoEsquerda, M objetoMeio, D objetoDireita) {
        return new Triplet(objetoEsquerda, objetoMeio, objetoDireita);
    }

    public Triplet(E objetoEsquerda, M objetoMeio, D objetoDireita) {
        this.objetoEsquerda = objetoEsquerda;
        this.objetoMeio = objetoMeio;
        this.objetoDireita = objetoDireita;
    }

    public D getObjetoDireita() {
        return objetoDireita;
    }

    public E getObjetoEsquerda() {
        return objetoEsquerda;
    }

    public M getObjetoMeio() {
        return objetoMeio;
    }

    @Override
    public int hashCode() {
        return getObjetoDireita().hashCode() + getObjetoMeio().hashCode() + getObjetoEsquerda().hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Triplet && getClass().equals(obj.getClass())) {
            Triplet objetoEquals = (Triplet) obj;

            return getObjetoEsquerda().equals(objetoEquals.getObjetoEsquerda()) &&
                    getObjetoMeio().equals(objetoEquals.getObjetoMeio()) &&
                    getObjetoDireita().equals(objetoEquals.getObjetoDireita());
        }

        return false;
    }
}
