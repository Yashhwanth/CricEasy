package com.cricketscoringapp.criceasy.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cricketscoringapp.criceasy.model.Player;
import com.cricketscoringapp.criceasy.model.PlayerTeam;
import com.cricketscoringapp.criceasy.repository.TeamRepository;
import com.cricketscoringapp.criceasy.Database.DatabaseHelper;

public class TeamViewModel extends AndroidViewModel {

    private TeamRepository teamRepository;
    private MutableLiveData<PlayerTeam> playersLiveData;
    public TeamViewModel(Application application) {
        super(application);
        teamRepository = new TeamRepository(new DatabaseHelper(application));
        playersLiveData = new MutableLiveData<>();
    }
    public LiveData<PlayerTeam> getPlayersForMatch(int matchId) {
        // Fetch players from repository and post the result to LiveData
        new Thread(() -> {
            PlayerTeam playerTeam = teamRepository.getPlayersForMatch(matchId);
            playersLiveData.postValue(playerTeam);
        }).start();
        return playersLiveData;
    }
}
