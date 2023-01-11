package com.ivanova.cinema.View.CinemaSessionsView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanova.cinema.Model.Entities.FilmSession;
import com.ivanova.cinema.Model.Entities.Session;
import com.ivanova.cinema.R;
import com.ivanova.cinema.View.CinemaListView.CinemaListRecyclerViewAdapter;
import com.ivanova.cinema.View.CinemaListView.CinemaListRecyclerViewInterface;

import java.util.ArrayList;

public class CinemaSessionsRecyclerViewAdapter extends RecyclerView.Adapter<CinemaSessionsRecyclerViewAdapter.MyViewHolder> {

    private final CinemaSessionsRecyclerViewInterface recyclerViewInterface;

    ArrayList<FilmSession> filmSessions;
    Context context;

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public CinemaSessionsRecyclerViewAdapter(ArrayList<FilmSession> filmSessions, Context context, CinemaSessionsRecyclerViewInterface recyclerViewInterface) {
        this.filmSessions = filmSessions;
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cinema_sessions_item, parent, false);
        CinemaSessionsRecyclerViewAdapter.MyViewHolder holder = new CinemaSessionsRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_filmName.setText(filmSessions.get(position).getFilmName());

        holder.recView_sessions.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new GridLayoutManager(holder.recView_sessions.getContext(), 2);
        FilmSession filmSession = filmSessions.get(position);

        RecyclerView.Adapter recyclerViewAdapter = new SessionsRecyclerViewAdapter(filmSession, (SessionsRecyclerViewInterface) context);
        holder.recView_sessions.setLayoutManager(layoutManager);
        holder.recView_sessions.setAdapter(recyclerViewAdapter);
        holder.recView_sessions.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return filmSessions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_filmName;
        RecyclerView recView_sessions;

        public MyViewHolder(@NonNull View itemView, CinemaSessionsRecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tv_filmName = itemView.findViewById(R.id.tv_filmName);
            recView_sessions = itemView.findViewById(R.id.sessionsRecyclerView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAbsoluteAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onFilmItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
