package com.ivanova.cinema.View.CinemaSessionsView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanova.cinema.Model.Entities.FilmSession;
import com.ivanova.cinema.R;

public class SessionsRecyclerViewAdapter extends RecyclerView.Adapter<SessionsRecyclerViewAdapter.MyViewHolder> {

    private final SessionsRecyclerViewInterface recyclerViewInterface;

    FilmSession filmSession;

    public SessionsRecyclerViewAdapter(FilmSession filmSession, SessionsRecyclerViewInterface recyclerViewInterface) {
        this.filmSession = filmSession;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_item, parent, false);
        SessionsRecyclerViewAdapter.MyViewHolder holder = new SessionsRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_sessionTime.setText(filmSession.getSessions().get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return filmSession.getSessions().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_sessionTime;

        public MyViewHolder(@NonNull View itemView, SessionsRecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tv_sessionTime = itemView.findViewById(R.id.tv_sessionTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAbsoluteAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onSessionItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
