package com.cricketscoringapp.criceasy.ViewModel;

import androidx.lifecycle.ViewModel;
import com.cricketscoringapp.criceasy.repository.TeamPlayersRepository;

public class TeamPlayersViewModel extends ViewModel {

    private final TeamPlayersRepository repository;

    public TeamPlayersViewModel(TeamPlayersRepository repository) {
        this.repository = repository;
    }

    public void addPlayerToTeam(int teamId, int playerId, int inningsId) {
        repository.addPlayerToTeam(teamId, playerId, inningsId);
    }
}
