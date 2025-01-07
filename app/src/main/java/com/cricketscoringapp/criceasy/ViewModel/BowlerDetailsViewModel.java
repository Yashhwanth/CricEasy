package com.cricketscoringapp.criceasy.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.cricketscoringapp.criceasy.model.Bowler;
import com.cricketscoringapp.criceasy.repository.BowlerDetailsRepository;

import java.util.List;

public class BowlerDetailsViewModel extends AndroidViewModel {

    private static final String TAG = "BowlerDetailsViewModel";
    private final BowlerDetailsRepository bowlerDetailsRepository;

    // Separate LiveData for each team
    private final MutableLiveData<List<Bowler>> bowlerDetailsLiveDataTeam1 = new MutableLiveData<>();
    private final MutableLiveData<List<Bowler>> bowlerDetailsLiveDataTeam2 = new MutableLiveData<>();

    public BowlerDetailsViewModel(Application application) {
        super(application);
        Log.d(TAG, "Initializing BowlerDetailsViewModel");
        bowlerDetailsRepository = new BowlerDetailsRepository(new DatabaseHelper(application));
        Log.d(TAG, "BowlerDetailsViewModel initialized successfully");
    }

    // Fetch bowler details for Team 1
    public LiveData<List<Bowler>> getBowlerDetailsForTeam1(long inningsId) {
        Log.d(TAG, "getBowlerDetailsForTeam1 called with inningsId: " + inningsId);
        fetchBowlerDetails(inningsId, bowlerDetailsLiveDataTeam1);
        return bowlerDetailsLiveDataTeam1;
    }

    // Fetch bowler details for Team 2
    public LiveData<List<Bowler>> getBowlerDetailsForTeam2(long inningsId) {
        Log.d(TAG, "getBowlerDetailsForTeam2 called with inningsId: " + inningsId);
        fetchBowlerDetails(inningsId, bowlerDetailsLiveDataTeam2);
        return bowlerDetailsLiveDataTeam2;
    }

    // Common method to fetch bowler details and update respective LiveData
    private void fetchBowlerDetails(long inningsId, MutableLiveData<List<Bowler>> liveData) {
        new Thread(() -> {
            try {
                Log.d(TAG, "Fetching bowler details from repository for inningsId: " + inningsId);
                List<Bowler> bowlerDetailsList = bowlerDetailsRepository.getBowlerDetailsForInnings(inningsId);
                if (bowlerDetailsList == null || bowlerDetailsList.isEmpty()) {
                    Log.e(TAG, "No bowler details found for inningsId: " + inningsId);
                } else {
                    Log.d(TAG, "Bowler details fetched successfully for inningsId: " + inningsId);
                    Log.d(TAG, "Bowler Details Count: " + bowlerDetailsList.size());
                    for (Bowler bowlerDetails : bowlerDetailsList) {
                        Log.d(TAG, "Bowler Details: " + bowlerDetails.toString());
                    }
                }
                liveData.postValue(bowlerDetailsList);
            } catch (Exception e) {
                Log.e(TAG, "Error while fetching bowler details for inningsId: " + inningsId, e);
            }
        }).start();
    }
}
