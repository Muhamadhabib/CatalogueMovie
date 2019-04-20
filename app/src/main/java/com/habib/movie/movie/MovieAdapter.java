package com.habib.movie.movie;

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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.habib.movie.R;
import com.habib.movie.model.Movie;

import java.util.List;

import static com.habib.movie.db.DatabaseContract.MovieColumns.CONTENT_URI_MOVIE;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> movies;
    private Context context;

    public MovieAdapter(Context context,List<Movie> movies){
        this.movies = movies;
        this.context = context;
    }
    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.ViewHolder holder, int i) {
        final Movie data = movies.get(i);
        holder.title.setText(data.getTitle());
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
                Intent intent = new Intent(context, DetailMovieActivity.class);
                Uri uri = Uri.parse(CONTENT_URI_MOVIE+"/"+data.getId());
                intent.setData(uri);
                intent.putExtra("id",data.getId());
                intent.putExtra("nama",data.getTitle());
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
