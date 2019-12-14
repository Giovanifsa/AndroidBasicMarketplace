package giovani.androidmarketplace.dados.entidades;

import androidx.annotation.Nullable;

import java.io.Serializable;

public abstract class AbstractEntidade implements Serializable {
    public abstract Integer getId();

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj != null && getClass().equals(obj.getClass())) {
            AbstractEntidade outraEntidade = (AbstractEntidade) obj;

            return outraEntidade.getId().equals(getId());
        }

        return false;
    }
}
