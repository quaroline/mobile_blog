package padilha.carolynne.ex_4all.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import padilha.carolynne.ex_4all.R;
import padilha.carolynne.ex_4all.controller.TelaPrincipal;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.TarefaHolder> {

    private Context context;
    private List<String> data;

    public MenuAdapter(Context context, List<String> data) {
        this.data = data;
        this.context = context;
    }

    public MenuAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TarefaHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //Criação da parte visual da RecyclerView.
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_menu, viewGroup, false);

        final TarefaHolder viewHolder = new TarefaHolder(view);

        //Listener "OnClick" para troca de tela ao clique em qualquer componente da Recycler View.
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TelaPrincipal.class);
                context.startActivity(i);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TarefaHolder holder, int i) {
        //Atribuição de valores (textos, imagens, estilos) para cada posição achada em "lista" List<String>.
        holder.tvRecyclerViewText.setText(data.get(i));

        Random random = new Random();
        int idIcon = random.nextInt(50) * 10;

        Picasso.get()
                .load("https://picsum.photos/" + idIcon)
                .transform(new RoundImage())
                .placeholder(R.drawable.placeholder_logo)
                .into(holder.ivRecyclerViewIcon);
    }

    @Override
    public int getItemCount() { return data.size(); }

    public static class TarefaHolder extends RecyclerView.ViewHolder {

        ImageView ivRecyclerViewIcon;
        TextView tvRecyclerViewText;
        LinearLayout view_container;

        public TarefaHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.ti_linearlayout);
            ivRecyclerViewIcon = itemView.findViewById(R.id.menu_iv_rv_image);
            tvRecyclerViewText = itemView.findViewById(R.id.menu_tv_rv_text);
        }
    }
}
