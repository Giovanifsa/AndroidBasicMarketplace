package giovani.androidmarketplace.visao.adaptadores;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.entidades.Produto;
import giovani.androidmarketplace.servico.ContextoAplicacao;
import giovani.androidmarketplace.utils.ActivityUtil;
import giovani.androidmarketplace.visao.EdicaoProdutoActivity;

public class AdaptadorListagemProdutos extends RecyclerView.Adapter<AdaptadorListagemProdutos.AdaptadorListagemProdutosViewHolder> {
    private Activity activityDona;
    private List<Produto> listaProdutos;

    public AdaptadorListagemProdutos(Activity activityDona) {
        this.activityDona = activityDona;
        listaProdutos = ContextoAplicacao.getContextoAplicacao().getCriadorDAOs().getProdutoDAO().getAllProdutos();
    }

    @NonNull
    @Override
    public AdaptadorListagemProdutosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textview_adaptador_listagem_produtos, parent, false);

        return new AdaptadorListagemProdutosViewHolder(v, activityDona);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListagemProdutosViewHolder holder, int position) {
        holder.setDados(listaProdutos.get(position));
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public static class AdaptadorListagemProdutosViewHolder extends RecyclerView.ViewHolder {
        private Activity activityDona;

        private TextView textView;
        private Produto produto;

        public AdaptadorListagemProdutosViewHolder(TextView textView, Activity activityDona) {
            super(textView);

            this.activityDona = activityDona;
            this.textView = textView;

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListagem((TextView) v);
                }
            });
        }

        public void setDados(Produto produto) {
            this.produto = produto;
            textView.setText(produto.getDescricao());
        }

        private void onClickItemListagem(TextView textView) {
            Bundle dadosProduto = new Bundle();
            dadosProduto.putInt(Produto.COLUNA_IDPRODUTO, produto.getId());

            ActivityUtil.iniciarActivity(activityDona, EdicaoProdutoActivity.class, dadosProduto);
        }
    }
}