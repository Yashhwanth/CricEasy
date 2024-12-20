package com.cricketscoringapp.criceasy.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cricketscoringapp.criceasy.model.Player;
import com.cricketscoringapp.criceasy.repository.TeamRepository;
import java.util.List;

public class TeamViewModel extends ViewModel {
    private final MutableLiveData<List<Player>> team1Players = new MutableLiveData<>();
    private final MutableLiveData<List<Player>> team2Players = new MutableLiveData<>();
    private final TeamRepository repository;

    public TeamViewModel() {
        this.repository = new TeamRepository();
        loadTeams();
    }

    private void loadTeams() {
        team1Players.setValue(repository.getPlayersForTeam(1));
        team2Players.setValue(repository.getPlayersForTeam(2));
    }

    public LiveData<List<Player>> getTeam1Players() {
        return team1Players;
    }

    public LiveData<List<Player>> getTeam2Players() {
        return team2Players;
    }
}
