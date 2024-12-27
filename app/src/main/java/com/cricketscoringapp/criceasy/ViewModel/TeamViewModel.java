package com.cricketscoringapp.criceasy.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cricketscoringapp.criceasy.model.Player;
import com.cricketscoringapp.criceasy.model.PlayerTeam;
import com.cricketscoringapp.criceasy.repository.TeamRepository;
import com.cricketscoringapp.criceasy.Database.DatabaseHelper;

public class TeamViewModel extends AndroidViewModel {
    private static final String TAG = "TeamViewModel";

    private TeamRepository teamRepository;
    private MutableLiveData<PlayerTeam> playersLiveData;

    public TeamViewModel(Application application) {
        super(application);
        Log.d(TAG, "Initializing TeamViewModel");

        // Initialize repository and LiveData
        teamRepository = new TeamRepository(new DatabaseHelper(application));
        playersLiveData = new MutableLiveData<>();

        Log.d(TAG, "TeamViewModel initialized successfully");
    }

    public LiveData<PlayerTeam> getPlayersForMatch(int matchId) {
        Log.d(TAG, "getPlayersForMatch called with matchId: " + matchId);

        // Fetch players from the repository in a background thread
        new Thread(() -> {
            try {
                Log.d(TAG, "Fetching players from repository for matchId: " + matchId);
                PlayerTeam playerTeam = teamRepository.getPlayersForMatch(matchId);

                if (playerTeam == null) {
                    Log.e(TAG, "PlayerTeam fetched is null for matchId: " + matchId);
                } else {
                    Log.d(TAG, "PlayerTeam fetched successfully for matchId: " + matchId);
                    Log.d(TAG, "Team 1 Players Count: " + playerTeam.getTeam1Players().size());
                    for (Player player : playerTeam.getTeam1Players()) {
                        Log.d(TAG, "Team 1 Player: " + player.toString());
                    }

                    Log.d(TAG, "Team 2 Players Count: " + playerTeam.getTeam2Players().size());
                    for (Player player : playerTeam.getTeam2Players()) {
                        Log.d(TAG, "Team 2 Player: " + player.toString());
                    }
                }
                // Post the fetched data to LiveData
                playersLiveData.postValue(playerTeam);
            } catch (Exception e) {
                Log.e(TAG, "Error while fetching players for matchId: " + matchId, e);
            }
        }).start();

        Log.d(TAG, "Returning playersLiveData for matchId: " + matchId);
        return playersLiveData;
    }
}
