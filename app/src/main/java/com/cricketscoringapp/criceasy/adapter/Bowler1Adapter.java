package com.cricketscoringapp.criceasy.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cricketscoringapp.criceasy.R;
import com.cricketscoringapp.criceasy.model.Bowler;

import java.util.List;

public class Bowler1Adapter extends RecyclerView.Adapter<Bowler1Adapter.BowlerViewHolder> {
    private Context context;
    private List<Bowler> bowlerStatsList;
    private SharedPreferences sharedPreferences;

    public Bowler1Adapter(Context context, List<Bowler> bowlerStatsList) {
        this.context = context;
        this.bowlerStatsList = bowlerStatsList;
    }

    @NonNull
    @Override
    public BowlerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for individual bowler stats item
        View view = LayoutInflater.from(context).inflate(R.layout.item_bowler_stats, parent, false);
        return new BowlerViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull BowlerViewHolder holder, int position) {
        Bowler bowlerStats = bowlerStatsList.get(position);
        holder.bowlerName.setText(bowlerStats.getPlayerName());
        int balls = bowlerStats.getBallsPlayed();
        int overs = balls / 6; // Calculate the number of full overs
        int remainingBalls = balls % 6; // Calculate the remaining balls
        String oversFormatted = overs + "." + remainingBalls; // Display format "overs.balls"
        holder.bowlerOvers.setText(oversFormatted);
        holder.bowlerMaidens.setText(String.valueOf(bowlerStats.getMaidens()));
        holder.bowlerRuns.setText(String.valueOf(bowlerStats.getRuns()));
        holder.bowlerWickets.setText(String.valueOf(bowlerStats.getWk()));
        double economyRate = balls > 0 ? (double) bowlerStats.getRuns() / (balls / 6.0) : 0.0;
        holder.bowlerEconomy.setText(String.format("%.2f", economyRate));
    }


    @Override
    public int getItemCount() {
        return bowlerStatsList.size();
    }

    // Method to update the dataset and notify the RecyclerView
    public void updateBowlerStats(List<Bowler> newBowlerStatsList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return bowlerStatsList != null ? bowlerStatsList.size() : 0;
            }

            @Override
            public int getNewListSize() {
                return newBowlerStatsList != null ? newBowlerStatsList.size() : 0;
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                // Assuming Bowler has a unique identifier like ID
                return bowlerStatsList.get(oldItemPosition).getPlayerId() ==
                        newBowlerStatsList.get(newItemPosition).getPlayerId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                // Compare content equality
                return bowlerStatsList.get(oldItemPosition).equals(newBowlerStatsList.get(newItemPosition));
            }
        });

        // Update the dataset
        this.bowlerStatsList = newBowlerStatsList;
        // Notify RecyclerView of changes
        diffResult.dispatchUpdatesTo(this);
    }

    // ViewHolder class to hold the views
    public static class BowlerViewHolder extends RecyclerView.ViewHolder {
        TextView bowlerName, bowlerOvers, bowlerMaidens, bowlerRuns, bowlerWickets, bowlerEconomy;

        public BowlerViewHolder(View itemView) {
            super(itemView);
            bowlerName = itemView.findViewById(R.id.tv_bowler_name);
            bowlerOvers = itemView.findViewById(R.id.tv_bowler_overs);
            bowlerMaidens = itemView.findViewById(R.id.tv_bowler_maidens);
            bowlerRuns = itemView.findViewById(R.id.tv_bowler_runs);
            bowlerWickets = itemView.findViewById(R.id.tv_bowler_wickets);
            bowlerEconomy = itemView.findViewById(R.id.tv_bowler_economy);
        }
    }
}
