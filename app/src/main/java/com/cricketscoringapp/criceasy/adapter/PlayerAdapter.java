package com.cricketscoringapp.criceasy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.cricketscoringapp.criceasy.R;
import com.cricketscoringapp.criceasy.model.Player;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private List<Player> players;

    // Constructor to accept List<Player>
    public PlayerAdapter(List<Player> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_player, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = players.get(position);
        holder.bind(player);
    }

    @Override
    public int getItemCount() {
        return players != null ? players.size() : 0;
    }

    // Method to update the dataset and notify the RecyclerView
    public void updatePlayers(List<Player> newPlayers) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return players != null ? players.size() : 0;
            }

            @Override
            public int getNewListSize() {
                return newPlayers != null ? newPlayers.size() : 0;
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                // Assuming Player has a unique identifier like ID
                return players.get(oldItemPosition).getPlayerId() == newPlayers.get(newItemPosition).getPlayerId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                // Compare content equality
                return players.get(oldItemPosition).equals(newPlayers.get(newItemPosition));
            }
        });

        // Update the dataset
        this.players = newPlayers;
        // Notify RecyclerView of changes
        diffResult.dispatchUpdatesTo(this);
    }

    // ViewHolder class
    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        private TextView playerName;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerNameView);
        }

        public void bind(Player player) {
            playerName.setText(player.getName());
        }
    }
}
