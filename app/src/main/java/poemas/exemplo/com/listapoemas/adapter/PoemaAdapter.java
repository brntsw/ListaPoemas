package poemas.exemplo.com.listapoemas.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import poemas.exemplo.com.listapoemas.R;
import poemas.exemplo.com.listapoemas.model.Poema;

/**
 * Created by BPardini on 09/11/2016.
 */

public class PoemaAdapter extends RecyclerView.Adapter<PoemaAdapter.ViewHolder> {

    private List<Poema> poemas;

    public PoemaAdapter(List<Poema> poemas){
        this.poemas = poemas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poema, parent, false);
        return new PoemaAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Poema poema = poemas.get(position);
        if(poema != null){
            holder.apply(poema);
        }
    }

    @Override
    public int getItemCount() {
        return poemas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTituloPoema, tvData, tvAutor;

        public ViewHolder(View v){
            super(v);

            tvTituloPoema = (TextView) v.findViewById(R.id.tv_titulo_poema);
            tvData = (TextView) v.findViewById(R.id.tv_data);
            tvAutor = (TextView) v.findViewById(R.id.tv_autor);
        }

        void apply(Poema poema){
            tvTituloPoema.setText(poema.getTitulo());
            tvData.setText(poema.getData());
            tvAutor.setText(poema.getAutor());
        }
    }

}
