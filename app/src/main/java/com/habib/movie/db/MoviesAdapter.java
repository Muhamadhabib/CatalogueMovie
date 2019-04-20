package com.habib.movie.db;

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
import com.habib.movie.movie.DetailMovieActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import static com.habib.movie.db.DatabaseContract.MovieColumns.CONTENT_URI_MOVIE;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private ArrayList<Movies> movies = new ArrayList<>();
    private Context context;

    public MoviesAdapter(Context context) {
        this.context = context;
    }

    public void setFavMovies(ArrayList<Movies> movie) {
        if(movie.size() > 0){
            this.movies.clear();
        }
        this.movies.addAll(movie);
        notifyDataSetChanged();
    }

    public ArrayList<Movies> getFavMovies() {
        return movies;
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_fav,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesAdapter.ViewHolder holder, final int i) {
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
                Uri uri = Uri.parse(CONTENT_URI_MOVIE + "/" + getFavMovies().get(i).getIds());
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
