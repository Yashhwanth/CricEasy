package com.cricketscoringapp.criceasy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketscoringapp.criceasy.R;
import com.cricketscoringapp.criceasy.model.Batsman;

import java.util.List;

public class Batter1Adapter extends RecyclerView.Adapter<Batter1Adapter.BatterViewHolder> {

    private Context context;
    private List<Batsman> batterStatsList;

    public Batter1Adapter(Context context, List<Batsman> batterStatsList) {
        this.context = context;
        this.batterStatsList = batterStatsList;
    }

    @NonNull
    @Override
    public BatterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for individual batter stats item
        View view = LayoutInflater.from(context).inflate(R.layout.item_batter_stats, parent, false);
        return new BatterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BatterViewHolder holder, int position) {
        // Get the current batter stats
        Batsman batterStats = batterStatsList.get(position);

        // Set the data into views
        holder.batterName.setText(batterStats.getPlayerName());
        holder.batterRuns.setText(String.valueOf(batterStats.getScore()));
        holder.batterBalls.setText(String.valueOf(batterStats.getBallsPlayed()));
        holder.batterFours.setText(String.valueOf(batterStats.getFours()));
        holder.batterSixes.setText(String.valueOf(batterStats.getSixes()));

        double strikeRate = batterStats.getBallsPlayed() > 0
                ? (double) batterStats.getScore() / batterStats.getBallsPlayed() * 100
                : 0.0;
        holder.batterStrikeRate.setText(String.format("%.2f", strikeRate));
    }

    @Override
    public int getItemCount() {
        return batterStatsList.size();
    }

    // Method to update the dataset and notify the RecyclerView
    public void updateBatterStats(List<Batsman> newBatterStatsList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return batterStatsList != null ? batterStatsList.size() : 0;
            }

            @Override
            public int getNewListSize() {
                return newBatterStatsList != null ? newBatterStatsList.size() : 0;
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                // Assuming Batsman has a unique identifier like ID
                return batterStatsList.get(oldItemPosition).getPlayerId() ==
                        newBatterStatsList.get(newItemPosition).getPlayerId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                // Compare content equality
                return batterStatsList.get(oldItemPosition).equals(newBatterStatsList.get(newItemPosition));
            }
        });

        // Update the dataset
        this.batterStatsList = newBatterStatsList;

        // Notify RecyclerView of changes
        diffResult.dispatchUpdatesTo(this);
    }

    // ViewHolder class to hold the views
    public static class BatterViewHolder extends RecyclerView.ViewHolder {

        TextView batterName, batterRuns, batterBalls, batterFours, batterSixes, batterStrikeRate;

        public BatterViewHolder(View itemView) {
            super(itemView);
            batterName = itemView.findViewById(R.id.tv_batter_name);
            batterRuns = itemView.findViewById(R.id.tv_batter_runs);
            batterBalls = itemView.findViewById(R.id.tv_batter_balls);
            batterFours = itemView.findViewById(R.id.tv_batter_fours);
            batterSixes = itemView.findViewById(R.id.tv_batter_sixes);
            batterStrikeRate = itemView.findViewById(R.id.tv_batter_sr);
        }
    }
}
