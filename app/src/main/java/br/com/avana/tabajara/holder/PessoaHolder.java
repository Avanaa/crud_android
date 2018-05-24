package br.com.avana.tabajara.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.avana.tabajara.R;

public class ItemHolder extends RecyclerView.ViewHolder {

    public ItemHolder(View itemView) {
        super(itemView);

        TextView descricao = itemView.findViewById(R.id.item_lista_descricao);
    }
}
