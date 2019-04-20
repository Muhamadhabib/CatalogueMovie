package com.habib.cataloguefavorite.adapter;

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

import com.habib.cataloguefavorite.BuildConfig;
import com.habib.cataloguefavorite.DetailTvActivity;
import com.habib.cataloguefavorite.R;
import com.habib.cataloguefavorite.entity.Tvs;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.habib.cataloguefavorite.db.DatabaseContract.TvColumns.CONTENT_URI_TV;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.ViewHolder> {
    private ArrayList<Tvs> tvs = new ArrayList<>();
    private Context context;

    public TvAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Tvs> getTvs() {
        return tvs;
    }

    public void setTvs(ArrayList<Tvs> tvs) {
        this.tvs.clear();
        this.tvs.addAll(tvs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tv,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvAdapter.ViewHolder holder, final int i) {
        holder.name.setText(tvs.get(i).getName());
        holder.ave.setText(tvs.get(i).getAverage());
        holder.date.setText(tvs.get(i).getRelease());
        holder.over.setText(tvs.get(i).getOverview());
        Picasso.with(context).load(BuildConfig.IMG+tvs.get(i).getPoster()).into(holder.poster, new Callback() {
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
                Intent intent = new Intent(context, DetailTvActivity.class);
                Uri uri = Uri.parse(CONTENT_URI_TV + "/" + getTvs().get(i).getId());
                intent.setData(uri);
                intent.putExtra("id",tvs.get(i).getId());
                intent.putExtra("nama",tvs.get(i).getName());
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
