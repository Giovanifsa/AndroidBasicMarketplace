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
import giovani.androidmarketplace.dados.entidades.Pedido;
import giovani.androidmarketplace.utils.ActivityUtil;
import giovani.androidmarketplace.utils.DecimalUtil;
import giovani.androidmarketplace.utils.StringUtil;
import giovani.androidmarketplace.utils.modelos.IListenerProcesso;
import giovani.androidmarketplace.visao.EdicaoPedidoActivity;

public class AdaptadorListagemPedidos extends RecyclerView.Adapter<AdaptadorListagemPedidos.AdaptadorListagemPedidosViewHolder> {
    private Activity activityDona;
    private List<Pedido> listaPedidos;
    private IListenerProcesso<Pedido> listenerRemocaoPedido;
    private IListenerProcesso<Pedido> listenerVisualizarPedido;

    public AdaptadorListagemPedidos(Activity activityDona, List<Pedido> listaPedidos,
                                    IListenerProcesso<Pedido> listenerRemocaoPedido, IListenerProcesso<Pedido> listenerVisualizarPedido) {
        this.activityDona = activityDona;
        this.listaPedidos = listaPedidos;
        this.listenerRemocaoPedido = listenerRemocaoPedido;
        this.listenerVisualizarPedido = listenerVisualizarPedido;
    }

    @NonNull
    @Override
    public AdaptadorListagemPedidosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.textview_adaptador_listagens, parent, false);

        return new AdaptadorListagemPedidosViewHolder(v, activityDona, listenerRemocaoPedido, listenerVisualizarPedido);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListagemPedidosViewHolder holder, int position) {
        holder.setDados(listaPedidos.get(position));
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public static class AdaptadorListagemPedidosViewHolder extends RecyclerView.ViewHolder {
        private Activity activityDona;
        private IListenerProcesso<Pedido> listenerRemocaoPedido;
        private IListenerProcesso<Pedido> listenerVisualizarPedido;

        private TextView textView;
        private Pedido pedido;

        public AdaptadorListagemPedidosViewHolder(TextView textView, Activity activityDona,
                                                  IListenerProcesso<Pedido> listenerRemocaoPedido, IListenerProcesso<Pedido> listenerVisualizarPedido) {
            super(textView);

            this.activityDona = activityDona;
            this.textView = textView;
            this.listenerRemocaoPedido = listenerRemocaoPedido;
            this.listenerVisualizarPedido = listenerVisualizarPedido;

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListagem((TextView) v);
                }
            });
        }

        public void setDados(Pedido pedido) {
            this.pedido = pedido;
            textView.setText(pedido.getCliente() + " - " + DecimalUtil.formatarDuasCasasDecimais(pedido.getValorTotal()));
        }

        private void onClickItemListagem(TextView textView) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activityDona);

            String textoTitulo =
                    StringUtil.formatMensagem(activityDona.getString(R.string.frase_pedido_selecionado),
                                pedido.getCliente() + " - " + DecimalUtil.formatarDuasCasasDecimais(pedido.getValorTotal()));

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
                                    listenerVisualizarPedido.processarDado(pedido);
                                    break;
                                }
                            }
                        }
                    });

            builder.create().show();
        }
    }
}