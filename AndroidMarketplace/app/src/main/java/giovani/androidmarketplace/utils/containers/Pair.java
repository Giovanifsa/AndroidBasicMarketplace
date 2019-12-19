package giovani.androidmarketplace.utils.containers;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Pair<E, D> implements Serializable {
    private E objetoEsquerda;
    private D objetoDireita;

    public static <E, D> Pair<E, D> from(E objetoEsquerda, D objetoDireita) {
        return new Pair(objetoEsquerda, objetoDireita);
    }

    public Pair(E objetoEsquerda, D objetoDireita) {
        this.objetoEsquerda = objetoEsquerda;
        this.objetoDireita = objetoDireita;
    }

    public D getObjetoDireita() {
        return objetoDireita;
    }

    public E getObjetoEsquerda() {
        return objetoEsquerda;
    }

    @Override
    public int hashCode() {
        return getObjetoDireita().hashCode() + getObjetoEsquerda().hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Pair && getClass().equals(obj.getClass())) {
            Pair objetoEquals = (Pair) obj;

            return getObjetoEsquerda().equals(objetoEquals.getObjetoEsquerda()) &&
                        getObjetoDireita().equals(objetoEquals.getObjetoDireita());
        }

        return false;
    }
}
