package com.cricketscoringapp.criceasy.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cricketscoringapp.criceasy.Database.DatabaseHelper;
import com.cricketscoringapp.criceasy.model.BallDetails;
import com.cricketscoringapp.criceasy.repository.BallDetailsRepository;

import java.util.List;
public class BallDetailsViewModel extends AndroidViewModel {
    private static final String TAG = "BallDetailsViewModel";
    private BallDetailsRepository ballDetailsRepository;
    private MutableLiveData<List<BallDetails>> ballDetailsLiveData;
    public BallDetailsViewModel(Application application) {
        super(application);
        Log.d(TAG, "Initializing BallDetailsViewModel");
        // Initialize repository and LiveData
        ballDetailsRepository = new BallDetailsRepository(new DatabaseHelper(application));
        ballDetailsLiveData = new MutableLiveData<>();
        Log.d(TAG, "BallDetailsViewModel initialized successfully");
    }
    public LiveData<List<BallDetails>> getBallDetailsForInnings(long inningsId) {
        Log.d(TAG, "getBallDetailsForInnings called with inningsId: " + inningsId);
        new Thread(() -> {
            try {
                Log.d(TAG, "Fetching ball details from repository for inningsId: " + inningsId);
                List<BallDetails> ballDetailsList = ballDetailsRepository.getBallDetailsForInnings(inningsId);
                if (ballDetailsList == null || ballDetailsList.isEmpty()) {
                    Log.e(TAG, "No ball details found for inningsId: " + inningsId);
                } else {
                    Log.d(TAG, "Ball details fetched successfully for inningsId: " + inningsId);
                    Log.d(TAG, "Ball Details Count: " + ballDetailsList.size());
                    for (BallDetails ballDetails : ballDetailsList) {
                        Log.d(TAG, "Ball Details: " + ballDetails.toString());
                    }
                }
                ballDetailsLiveData.postValue(ballDetailsList);
            } catch (Exception e) {
                Log.e(TAG, "Error while fetching ball details for inningsId: " + inningsId, e);
            }
        }).start();
        Log.d(TAG, "Returning ballDetailsLiveData for inningsId: " + inningsId);
        return ballDetailsLiveData;
    }
}
