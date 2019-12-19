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
import giovani.androidmarketplace.utils.modelos.IListenerProcesso;

public class AdaptadorListagemItemPedido extends RecyclerView.Adapter<AdaptadorListagemItemPedido.AdaptadorListagemProdutosViewHolder> {
    private Activity activityDona;
    private List<PedidoItem> listaPedidoItens;
    private IListenerProcesso<PedidoItem> listenerRemocaoPedidoItem;

    public AdaptadorListagemItemPedido(Activity activityDona, List<PedidoItem> listaPedidoItens, IListenerProcesso<PedidoItem> listenerRemocaoPedidoItem) {
        this.activityDona = activityDona;
        this.listaPedidoItens = listaPedidoItens;
        this.listenerRemocaoPedidoItem = listenerRemocaoPedidoItem;
    }

    @NonNull
    @Override
    public AdaptadorListagemProdutosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textview_adaptador_listagens, parent, false);

        return new AdaptadorListagemProdutosViewHolder(v, activityDona, listenerRemocaoPedidoItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListagemProdutosViewHolder holder, int position) {
        holder.setDados(listaPedidoItens.get(position));
    }

    @Override
    public int getItemCount() {
        return listaPedidoItens.size();
    }

    public static class AdaptadorListagemProdutosViewHolder extends RecyclerView.ViewHolder {
        private Activity activityDona;
        private IListenerProcesso<PedidoItem> listenerRemocaoPedidoItem;

        private TextView textView;
        private PedidoItem pedidoItem;

        public AdaptadorListagemProdutosViewHolder(TextView textView, Activity activityDona, IListenerProcesso<PedidoItem> listenerRemocaoPedidoItem) {
            super(textView);

            this.activityDona = activityDona;
            this.textView = textView;
            this.listenerRemocaoPedidoItem = listenerRemocaoPedidoItem;

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListagem((TextView) v);
                }
            });
        }

        public void setDados(PedidoItem pedidoItem) {
            this.pedidoItem = pedidoItem;

            Produto produto = ContextoAplicacao.getContextoAplicacao().getCriadorGerenciadores().getGerenciadorProduto().buscar(pedidoItem.getIdProduto());

            String texto = produto.getDescricao() + "\n" + "Qtd.: " + pedidoItem.getQuantidade() + "\n";
            texto += "Preço Venda: " + pedidoItem.getPrecoVenda().toPlainString() + "\n";
            texto += "Valor Desconto: " + pedidoItem.getValorDesconto() + "\n";
            texto += "Valor Total: " + pedidoItem.getQuantidade().multiply(pedidoItem.getPrecoVenda()).toPlainString();

            textView.setText(texto);
        }

        private void onClickItemListagem(TextView textView) {
            /*AlertDialog.Builder builder = new AlertDialog.Builder(activityDona);

            String textoTitulo =
                    StringUtil.formatMensagem(activityDona.getString(R.string.frase_pedido_selecionado),
                                pedido.getCliente() + " - " + pedido.getValorTotal().toPlainString());

            builder.setTitle(textoTitulo);

            builder.setItems(new CharSequence[]{activityDona.getString(R.string.palavra_remover),
                                                activityDona.getString(R.string.palavra_visualizar)},

                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {
                            switch (position) {
                                case 0: {
                                    listenerRemocaoPedido.processarDado(pedido);

                                    break;
                                }

                                default: {
                                    Bundle dadosPedido = new Bundle();
                                    dadosPedido.putInt(PedidoItem.COLUNA_IDPEDIDOITEM, pedido.getId());

                                    ActivityUtil.iniciarActivity(activityDona, EdicaoPedidoActivity.class, dadosPedido);

                                    break;
                                }
                            }
                        }
                    });

            builder.create().show();*/
        }
    }
}