package giovani.androidmarketplace.visao.adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import giovani.androidmarketplace.utils.ActivityUtil;
import giovani.androidmarketplace.utils.StringUtil;
import giovani.androidmarketplace.utils.modelos.IListenerProcesso;
import giovani.androidmarketplace.visao.EdicaoProdutoActivity;

public class AdaptadorListagemProdutos extends RecyclerView.Adapter<AdaptadorListagemProdutos.AdaptadorListagemProdutosViewHolder> {
    private Activity activityDona;
    private List<Produto> listaProdutos;
    private IListenerProcesso<Produto> listenerRemocaoProduto;

    public AdaptadorListagemProdutos(Activity activityDona, List<Produto> listaProdutos, IListenerProcesso<Produto> listenerRemocaoProduto) {
        this.activityDona = activityDona;
        this.listaProdutos = listaProdutos;
        this.listenerRemocaoProduto = listenerRemocaoProduto;
    }

    @NonNull
    @Override
    public AdaptadorListagemProdutosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textview_adaptador_listagens, parent, false);

        return new AdaptadorListagemProdutosViewHolder(v, activityDona, listenerRemocaoProduto);
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
        private IListenerProcesso<Produto> listenerRemocaoProduto;

        private TextView textView;
        private Produto produto;

        public AdaptadorListagemProdutosViewHolder(TextView textView, Activity activityDona, IListenerProcesso<Produto> listenerRemocaoProduto) {
            super(textView);

            this.activityDona = activityDona;
            this.textView = textView;
            this.listenerRemocaoProduto = listenerRemocaoProduto;

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
            AlertDialog.Builder builder = new AlertDialog.Builder(activityDona);

            String textoTitulo =
                    StringUtil.formatMensagem(activityDona.getString(R.string.frase_produto_selecionado), produto.getDescricao());

            builder.setTitle(textoTitulo);

            builder.setItems(new CharSequence[]{activityDona.getString(R.string.palavra_remover),
                                                activityDona.getString(R.string.palavra_visualizar)},

                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            switch (position) {
                                case 0: {
                                    listenerRemocaoProduto.processarDado(produto);

                                    break;
                                }

                                default: {
                                    Bundle dadosProduto = new Bundle();
                                    dadosProduto.putInt(Produto.COLUNA_IDPRODUTO, produto.getId());

                                    ActivityUtil.iniciarActivity(activityDona, EdicaoProdutoActivity.class, dadosProduto);

                                    break;
                                }
                            }
                        }
                    });

            builder.create().show();
        }
    }
}