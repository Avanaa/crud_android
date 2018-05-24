package br.com.avana.tabajara.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import br.com.avana.tabajara.R;
import br.com.avana.tabajara.holder.PessoaHolder;
import br.com.avana.tabajara.model.Pessoa;

public class PessoaAdapter extends RecyclerView.Adapter<PessoaHolder> {

    private List<Pessoa> pessoas;

    public PessoaAdapter(List<Pessoa> pessoas){
        this.pessoas = pessoas;
    }

    @NonNull
    @Override
    public PessoaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PessoaHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_lista_pessoas, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PessoaHolder holder, int position) {
        holder.getDescricao().setText(pessoas.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return pessoas != null ? pessoas.size() : 0;
    }
}
