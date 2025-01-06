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
    private BowlerDetailsRepository bowlerDetailsRepository;
    private MutableLiveData<List<Bowler>> bowlerDetailsLiveData;

    public BowlerDetailsViewModel(Application application) {
        super(application);
        Log.d(TAG, "Initializing BowlerDetailsViewModel");
        bowlerDetailsRepository = new BowlerDetailsRepository(new DatabaseHelper(application));
        bowlerDetailsLiveData = new MutableLiveData<>();
        Log.d(TAG, "BowlerDetailsViewModel initialized successfully");
    }

    public LiveData<List<Bowler>> getBowlerDetailsForInnings(long inningsId) {
        Log.d(TAG, "getBowlerDetailsForInnings called with inningsId: " + inningsId);
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
                bowlerDetailsLiveData.postValue(bowlerDetailsList);
            } catch (Exception e) {
                Log.e(TAG, "Error while fetching bowler details for inningsId: " + inningsId, e);
            }
        }).start();
        Log.d(TAG, "Returning bowlerDetailsLiveData for inningsId: " + inningsId);
        return bowlerDetailsLiveData;
    }
}
