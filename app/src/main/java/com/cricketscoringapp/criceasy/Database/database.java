package com.cricketscoringapp.criceasy.Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.cricketscoringapp.criceasy.dao.*;
import com.cricketscoringapp.criceasy.entities.*;

@Database(
        entities = {
                Ball.class,
                Batsman.class,
                Bowler.class,
                Extra.class,
                Innings.class,
                Matches.class,
                MatchTeams.class,
                Over.class,
                Partnership.class,
                Place.class,
                Player.class,
                Team.class,
                TeamPlayers.class,
                TeamStatistics.class,
                Toss.class,
                Wicket.class
        },
        version = 19,
        exportSchema = false
)
public abstract class database extends RoomDatabase {
    private static volatile database INSTANCE;

    // DAO methods
    public abstract BallDao ballDao();
    public abstract BatsmanDao batsmanDao();
    public abstract BowlerDao bowlerDao();
    public abstract ExtraDao extraDao();
    public abstract InningsDao inningsDao();
    public abstract MatchDao matchDao();
    public abstract MatchTeamsDao matchTeamsDao();
    public abstract OverDao overDao();
    public abstract PartnershipDao partnershipDao();
    public abstract PlaceDao placeDao();
    public abstract PlayerDao playerDao();
    public abstract TeamDao teamDao();
    public abstract TeamPlayersDao teamPlayersDao();
    public abstract TeamStatisticsDao teamStatisticsDao();
    public abstract TossDao tossDao();
    public abstract WicketDao wicketDao();

    // Singleton pattern
    public static database getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    database.class,
                                    "CricketDB"
                            )
                            // Allow destructive migration, which will reset the database when needed
                            .fallbackToDestructiveMigration() // This will destroy the database on schema changes
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
