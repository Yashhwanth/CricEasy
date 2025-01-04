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
    private BatterDetailsRepository batterDetailsRepository;
    private MutableLiveData<List<Batsman>> batterDetailsLiveData;

    public BatsmanDetailsViewModel(Application application) {
        super(application);
        Log.d(TAG, "Initializing BatterDetailsViewModel");
        batterDetailsRepository = new BatterDetailsRepository(new DatabaseHelper(application));
        batterDetailsLiveData = new MutableLiveData<>();
        Log.d(TAG, "BatterDetailsViewModel initialized successfully");
    }
    public LiveData<List<Batsman>> getBatterDetailsForInnings(long inningsId) {
        Log.d(TAG, "getBatterDetailsForInnings called with inningsId: " + inningsId);
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
                batterDetailsLiveData.postValue(batterDetailsList);
            } catch (Exception e) {
                Log.e(TAG, "Error while fetching batter details for inningsId: " + inningsId, e);
            }
        }).start();
        Log.d(TAG, "Returning batterDetailsLiveData for inningsId: " + inningsId);
        return batterDetailsLiveData;
    }
}
