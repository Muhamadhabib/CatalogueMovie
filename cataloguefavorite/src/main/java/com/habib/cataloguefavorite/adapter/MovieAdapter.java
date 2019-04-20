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
import com.habib.cataloguefavorite.DetailMovieActivity;
import com.habib.cataloguefavorite.R;
import com.habib.cataloguefavorite.entity.Movies;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.habib.cataloguefavorite.db.DatabaseContract.MovieColumns.CONTENT_URI_MOVIE;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<Movies> movies = new ArrayList<>();
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movies> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movies> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.ViewHolder holder, final int i) {
        holder.title.setText(movies.get(i).getTitles());
        holder.ave.setText(movies.get(i).getRate());
        holder.date.setText(movies.get(i).getDate());
        holder.over.setText(movies.get(i).getOver());
        Picasso.with(context).load(BuildConfig.IMG+movies.get(i).getPosters()).into(holder.poster, new Callback() {
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
                Intent intent = new Intent(context, DetailMovieActivity.class);
                Uri uri = Uri.parse(CONTENT_URI_MOVIE + "/" + getMovies().get(i).getIds());
                intent.setData(uri);
                intent.putExtra("id",movies.get(i).getIds());
                intent.putExtra("nama",movies.get(i).getTitles());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,date,over,ave;
        ImageView poster;
        CardView card;
        ProgressBar bar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.iv_poster_movie);
            title = itemView.findViewById(R.id.tv_title_movie);
            ave = itemView.findViewById(R.id.tv_ave);
            date = itemView.findViewById(R.id.tv_date_movie);
            over = itemView.findViewById(R.id.tv_view_movie);
            card = itemView.findViewById(R.id.cv_movie);
            bar = itemView.findViewById(R.id.prog_movie);
        }
    }
}
