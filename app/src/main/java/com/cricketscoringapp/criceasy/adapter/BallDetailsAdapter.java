package com.cricketscoringapp.criceasy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.cricketscoringapp.criceasy.R;
import com.cricketscoringapp.criceasy.model.BallDetails;
import java.util.List;

public class BallDetailsAdapter extends RecyclerView.Adapter<BallDetailsAdapter.BallDetailsViewHolder> {
    private List<BallDetails> ballDetailsList;
    public BallDetailsAdapter(List<BallDetails> ballDetailsList) {
        this.ballDetailsList = ballDetailsList;
    }
    @NonNull
    @Override
    public BallDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ball_detail, parent, false);
        return new BallDetailsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull BallDetailsViewHolder holder, int position) {
        BallDetails ballDetails = ballDetailsList.get(position);
        // Build the ball details string
        StringBuilder details = new StringBuilder();
        details.append(ballDetails.getBowlerName())
                .append(" to ")
                .append(ballDetails.getStrikerName())
                .append(", ")
                .append(ballDetails.getRuns())
                .append(" runs");

        if (!"NORMAL".equals(ballDetails.getBallType())) {
            details.append(", ").append(ballDetails.getBallType());
        }
        if (ballDetails.isWicket()) {
            details.append(", Out");
        }
        holder.ballDetailTextView.setText(details.toString());
    }

    @Override
    public int getItemCount() {
        return ballDetailsList != null ? ballDetailsList.size() : 0;
    }
    // Method to update the dataset and notify the RecyclerView
    public void updateBallDetailsList(List<BallDetails> newBallDetailsList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return ballDetailsList != null ? ballDetailsList.size() : 0;
            }
            @Override
            public int getNewListSize() {
                return newBallDetailsList != null ? newBallDetailsList.size() : 0;
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                // Assuming BallDetails has a unique identifier (e.g., ball ID)
                return ballDetailsList.get(oldItemPosition).getBallId() == newBallDetailsList.get(newItemPosition).getBallId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                // Compare content equality
                return ballDetailsList.get(oldItemPosition).equals(newBallDetailsList.get(newItemPosition));
            }
        });

        // Update the dataset
        this.ballDetailsList = newBallDetailsList;
        // Notify RecyclerView of changes
        diffResult.dispatchUpdatesTo(this);
    }

    // ViewHolder class
    static class BallDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView overDetailTextView;
        TextView ballDetailTextView;

        public BallDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            //overDetailTextView = itemView.findViewById(R.id.overNumberTextView);
            ballDetailTextView = itemView.findViewById(R.id.ballDetailTextView);
        }
    }
}
