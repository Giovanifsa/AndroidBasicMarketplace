package giovani.androidmarketplace.visao.adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import giovani.androidmarketplace.R;
import giovani.androidmarketplace.dados.entidades.PedidoItem;
import giovani.androidmarketplace.dados.entidades.Produto;
import giovani.androidmarketplace.servico.ContextoAplicacao;
import giovani.androidmarketplace.utils.DecimalUtil;
import giovani.androidmarketplace.utils.modelos.IListenerProcesso;

public class AdaptadorListagemItemPedido extends RecyclerView.Adapter<AdaptadorListagemItemPedido.AdaptadorListagemProdutosViewHolder> {
    private Activity activityDona;
    private List<PedidoItem> listaPedidoItens;
    private IListenerProcesso<PedidoItem> listenerRemocaoPedidoItem;
    private IListenerProcesso<Integer> listenerEdicaoPedidoItem;

    public AdaptadorListagemItemPedido(Activity activityDona, List<PedidoItem> listaPedidoItens,
                                       IListenerProcesso<PedidoItem> listenerRemocaoPedidoItem, IListenerProcesso<Integer> listenerEdicaoPedidoItem) {
        this.activityDona = activityDona;
        this.listaPedidoItens = listaPedidoItens;
        this.listenerRemocaoPedidoItem = listenerRemocaoPedidoItem;
        this.listenerEdicaoPedidoItem = listenerEdicaoPedidoItem;
    }

    @NonNull
    @Override
    public AdaptadorListagemProdutosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textview_adaptador_listagens, parent, false);

        return new AdaptadorListagemProdutosViewHolder(v, activityDona, listenerRemocaoPedidoItem, listenerEdicaoPedidoItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListagemProdutosViewHolder holder, int position) {
        holder.setDados(listaPedidoItens.get(position), position);
    }

    @Override
    public int getItemCount() {
        return listaPedidoItens.size();
    }

    public static class AdaptadorListagemProdutosViewHolder extends RecyclerView.ViewHolder {
        private Activity activityDona;
        private IListenerProcesso<PedidoItem> listenerRemocaoPedidoItem;
        private IListenerProcesso<Integer> listenerEdicaoPedidoItem;

        private TextView textView;
        private PedidoItem pedidoItem;
        private int posicao;

        public AdaptadorListagemProdutosViewHolder(TextView textView, Activity activityDona,
                                                   IListenerProcesso<PedidoItem> listenerRemocaoPedidoItem, IListenerProcesso<Integer> listenerEdicaoPedidoItem) {
            super(textView);

            this.activityDona = activityDona;
            this.textView = textView;
            this.listenerRemocaoPedidoItem = listenerRemocaoPedidoItem;
            this.listenerEdicaoPedidoItem = listenerEdicaoPedidoItem;

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListagem((TextView) v);
                }
            });
        }

        public void setDados(PedidoItem pedidoItem, int posicao) {
            this.pedidoItem = pedidoItem;
            this.posicao = posicao;

            Produto produto = ContextoAplicacao.getContextoAplicacao().getCriadorGerenciadores().getGerenciadorProduto().buscar(pedidoItem.getIdProduto());

            String texto = produto.getDescricao() + "\n" + "Qtd.: " + DecimalUtil.formatarDuasCasasDecimais(pedidoItem.getQuantidade()) + "\n";
            texto += "Preço Venda: " + DecimalUtil.formatarDuasCasasDecimais(pedidoItem.getPrecoVenda()) + "\n";
            texto += "Valor Desconto: " + DecimalUtil.formatarDuasCasasDecimais(pedidoItem.getValorDesconto()) + "\n";
            texto += "Valor Total: " + DecimalUtil.formatarDuasCasasDecimais(pedidoItem.getQuantidade().multiply(pedidoItem.getPrecoVenda()));

            textView.setText(texto);
        }

        private void onClickItemListagem(TextView textView) {
            listenerEdicaoPedidoItem.processarDado(posicao);
        }
    }
}