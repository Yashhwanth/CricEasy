package com.cricketscoringapp.criceasy.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProvider;
import com.cricketscoringapp.criceasy.R;
import com.cricketscoringapp.criceasy.model.Player;
import com.cricketscoringapp.criceasy.ViewModel.TeamViewModel;
import java.util.List;

public class TeamsFragment extends Fragment {
    private TextView team1Name, team2Name;
    private GridLayout team1Grid, team2Grid;
    private TeamViewModel teamViewModel;

    public TeamsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mp_teams, container, false);

        // Initialize views
        team1Name = view.findViewById(R.id.team1TextView);
        team2Name = view.findViewById(R.id.team2TextView);
        team1Grid = view.findViewById(R.id.team1PlayerGrid);
        team2Grid = view.findViewById(R.id.team2PlayerGrid);

        // Initialize ViewModel
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);

        // Observe changes in team data
        teamViewModel.getTeam1Players().observe(getViewLifecycleOwner(), players -> populateTeamGrid(team1Grid, players));
        teamViewModel.getTeam2Players().observe(getViewLifecycleOwner(), players -> populateTeamGrid(team2Grid, players));

        // Set team names (can be dynamic from repository or arguments)
        team1Name.setText("Team 1");
        team2Name.setText("Team 2");

        return view;
    }

    private void populateTeamGrid(GridLayout gridLayout, List<Player> players) {
        gridLayout.removeAllViews();
        for (Player player : players) {
            View playerView = createPlayerView(player);
            gridLayout.addView(playerView);
        }
    }

    private View createPlayerView(Player player) {
        View playerView = getLayoutInflater().inflate(R.layout.player_item, team1Grid, false);

        ImageView playerImage = playerView.findViewById(R.id.playerImageView);
        playerImage.setImageResource(R.drawable.user); // Placeholder icon

        TextView playerName = playerView.findViewById(R.id.playerNameView);
        playerName.setText(player.getName());

        return playerView;
    }
}
