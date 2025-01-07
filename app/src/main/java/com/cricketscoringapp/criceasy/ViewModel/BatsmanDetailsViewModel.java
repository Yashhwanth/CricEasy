package com.cricketscoringapp.criceasy.ViewModel;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.cricketscoringapp.criceasy.model.Batsman;
import com.cricketscoringapp.criceasy.repository.BatterDetailsRepository;
import java.util.List;

public class BatsmanDetailsViewModel extends AndroidViewModel {

    private static final String TAG = "BatterDetailsViewModel";
    private final BatterDetailsRepository batterDetailsRepository;

    // Separate LiveData for each team
    private final MutableLiveData<List<Batsman>> batterDetailsLiveDataTeam1 = new MutableLiveData<>();
    private final MutableLiveData<List<Batsman>> batterDetailsLiveDataTeam2 = new MutableLiveData<>();

    public BatsmanDetailsViewModel(Application application) {
        super(application);
        Log.d(TAG, "Initializing BatsmanDetailsViewModel");
        batterDetailsRepository = new BatterDetailsRepository(new DatabaseHelper(application));
        Log.d(TAG, "BatsmanDetailsViewModel initialized successfully");
    }

    // Fetch batter details for Team 1
    public LiveData<List<Batsman>> getBatterDetailsForTeam1(long inningsId) {
        Log.d(TAG, "getBatterDetailsForTeam1 called with inningsId: " + inningsId);
        fetchBatterDetails(inningsId, batterDetailsLiveDataTeam1);
        return batterDetailsLiveDataTeam1;
    }

    // Fetch batter details for Team 2
    public LiveData<List<Batsman>> getBatterDetailsForTeam2(long inningsId) {
        Log.d(TAG, "getBatterDetailsForTeam2 called with inningsId: " + inningsId);
        fetchBatterDetails(inningsId, batterDetailsLiveDataTeam2);
        return batterDetailsLiveDataTeam2;
    }

    // Common method to fetch batter details and update respective LiveData
    private void fetchBatterDetails(long inningsId, MutableLiveData<List<Batsman>> liveData) {
        new Thread(() -> {
            try {
                Log.d(TAG, "Fetching batter details from repository for inningsId: " + inningsId);
                List<Batsman> batterDetailsList = batterDetailsRepository.getBatterDetailsForInnings(inningsId);

                if (batterDetailsList == null || batterDetailsList.isEmpty()) {
                    Log.e(TAG, "No batter details found for inningsId: " + inningsId);
                } else {
                    Log.d(TAG, "Batter details fetched successfully for inningsId: " + inningsId);
                    Log.d(TAG, "Batter Details Count: " + batterDetailsList.size());
                    for (Batsman batterDetails : batterDetailsList) {
                        Log.d(TAG, "Batter Details: " + batterDetails.toString());
                    }
                }
                liveData.postValue(batterDetailsList);
            } catch (Exception e) {
                Log.e(TAG, "Error while fetching batter details for inningsId: " + inningsId, e);
            }
        }).start();
    }
}
