package com.habib.movie.tv;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.habib.movie.BuildConfig;
import com.habib.movie.R;
import com.habib.movie.model.Tv;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.habib.movie.db.DatabaseContract.TvColumns.CONTENT_URI_TV;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {
    private List<Tv> tvs;
    private Context context;

    public TvAdapter(Context context,List<Tv> tvs) {
        this.tvs = tvs;
        this.context = context;
    }
    @NonNull
    @Override
    public TvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tv,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TvAdapter.ViewHolder holder, int i) {
        final Tv data = tvs.get(i);
        holder.name.setText(data.getName());
        holder.ave.setText(data.getAverage());
        holder.date.setText(data.getRelease());
        holder.over.setText(data.getOverview());
        Picasso.with(context).load(BuildConfig.IMG+data.getPoster()).into(holder.poster, new Callback() {
            @Override
            public void onSuccess() {
                holder.bar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError() {

            }
        });
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context," "+data.getId(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailTvActivity.class);
                Uri uri = Uri.parse(CONTENT_URI_TV+"/"+data.getId());
                intent.setData(uri);
                intent.putExtra("id",data.getId());
                intent.putExtra("nama",data.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,date,over,ave;
        ImageView poster;
        CardView card;
        ProgressBar bar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.iv_poster_tv);
            name = itemView.findViewById(R.id.tv_title_tv);
            ave = itemView.findViewById(R.id.tv_ave_tv);
            date = itemView.findViewById(R.id.tv_date_tv);
            over = itemView.findViewById(R.id.tv_view_tv);
            card = itemView.findViewById(R.id.cv_tv);
            bar = itemView.findViewById(R.id.prog_tv);
        }
    }
}
