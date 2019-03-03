package padilha.carolynne.ex_4all.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import padilha.carolynne.ex_4all.R;
import padilha.carolynne.ex_4all.model.Comentario;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ComentarioHolder> {

    private Context context;
    private List<Comentario> data;
    LinearLayout layoutNotas;

    public ComentarioAdapter(Context context, List<Comentario> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ComentarioHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //Criação da parte visual da RecyclerView.
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_comentario, viewGroup, false);
        layoutNotas = view.findViewById(R.id.comentario_linearlayout_nota);

        final ComentarioHolder viewHolder = new ComentarioHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioHolder holder, int i) {
        //Atribuição de valores (textos, imagens, estilos) para cada posição achada na List<Comentario>.
        holder.tvRecyclerViewName.setText(data.get(i).getNome());
        holder.tvRecyclerViewTitle.setText(data.get(i).getTitulo());
        holder.tvRecyclerViewComment.setText(data.get(i).getComentario());

        Picasso.get()
                .load(data.get(i).getUrlFoto())
                .placeholder(R.drawable.placeholder_user)
                .transform(new RoundImage())
                .into(holder.ivRecyclerViewIcon);

        //Criação de ImageView referente ao número de nota que o usuário deu no comentário.
        for (int x = 0; x < data.get(i).getNota(); x++) {

            ImageView ivNota = new ImageView(context);
            LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(40, 40);

            ivNota.setImageDrawable(context.getResources().getDrawable(R.drawable.favoritos));
            ivNota.setVisibility(View.VISIBLE);
            ivNota.setLayoutParams(ivParams);

            layoutNotas.addView(ivNota);
        }
    }

    @Override
    public int getItemCount() { return data.size(); }

    public static class ComentarioHolder extends RecyclerView.ViewHolder {

        ImageView ivRecyclerViewIcon;
        TextView tvRecyclerViewName, tvRecyclerViewTitle, tvRecyclerViewComment;
        LinearLayout view_container;

        public ComentarioHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.comentario_linearlayout);
            ivRecyclerViewIcon = itemView.findViewById(R.id.comentario_rv_iv_user);
            tvRecyclerViewName = itemView.findViewById(R.id.comentario_rv_tv_name);
            tvRecyclerViewTitle = itemView.findViewById(R.id.comentario_rv_tv_title);
            tvRecyclerViewComment = itemView.findViewById(R.id.comentario_rv_tv_comment);
        }
    }
}
