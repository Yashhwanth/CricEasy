package com.cricketscoringapp.criceasy.repository;

import com.cricketscoringapp.criceasy.model.Player;
import java.util.ArrayList;
import java.util.List;

public class TeamRepository {
    public List<Player> getPlayersForTeam(int teamId) {
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            Player player = new Player();
            player.setPlayerId(i + teamId * 100);
            player.setName("Player " + i);
            player.setRole("Batsman");
            players.add(player);
        }
        return players;
    }
}
