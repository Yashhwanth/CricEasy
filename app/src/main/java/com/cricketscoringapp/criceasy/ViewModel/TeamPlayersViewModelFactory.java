package com.cricketscoringapp.criceasy.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.cricketscoringapp.criceasy.repository.TeamPlayersRepository;

public class TeamPlayersViewModelFactory implements ViewModelProvider.Factory {
    private final TeamPlayersRepository teamPlayersRepository;

    public TeamPlayersViewModelFactory(TeamPlayersRepository teamPlayersRepository) {
        this.teamPlayersRepository = teamPlayersRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TeamPlayersViewModel.class)) {
            return (T) new TeamPlayersViewModel(teamPlayersRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
