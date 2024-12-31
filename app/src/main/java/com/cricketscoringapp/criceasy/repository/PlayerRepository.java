package com.cricketscoringapp.criceasy.repository;

import android.content.Context;
import com.cricketscoringapp.criceasy.dao.PlayerDao;
import com.cricketscoringapp.criceasy.Database.database;
import com.cricketscoringapp.criceasy.entities.Player;

public class PlayerRepository {

    private final PlayerDao playerDao;

    public PlayerRepository(Context context) {
        database db = database.getInstance(context);
        this.playerDao = db.playerDao();
    }
    public Long getPlayerIdByName(String playerName) {
        return playerDao.getPlayerIdByName(playerName);
    }
    public long insertPlayer(Player player) {
        return playerDao.insertPlayer(player);
    }
}
