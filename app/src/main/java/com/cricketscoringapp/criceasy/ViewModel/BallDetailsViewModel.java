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

    // Separate LiveData for Team 1 and Team 2
    private final MutableLiveData<List<BallDetails>> ballDetailsLiveDataTeam1 = new MutableLiveData<>();
    private final MutableLiveData<List<BallDetails>> ballDetailsLiveDataTeam2 = new MutableLiveData<>();

    public BallDetailsViewModel(Application application) {
        super(application);
        Log.d(TAG, "Initializing BallDetailsViewModel");

        // Initialize repository and LiveData
        ballDetailsRepository = new BallDetailsRepository(new DatabaseHelper(application));
        Log.d(TAG, "BallDetailsViewModel initialized successfully");
    }

    // Fetch ball details for Team 1
    public LiveData<List<BallDetails>> getBallDetailsForTeam1(long inningsId) {
        Log.d(TAG, "getBallDetailsForTeam1 called with inningsId: " + inningsId);
        fetchBallDetails(inningsId, ballDetailsLiveDataTeam1);
        return ballDetailsLiveDataTeam1;
    }

    // Fetch ball details for Team 2
    public LiveData<List<BallDetails>> getBallDetailsForTeam2(long inningsId) {
        Log.d(TAG, "getBallDetailsForTeam2 called with inningsId: " + inningsId);
        fetchBallDetails(inningsId, ballDetailsLiveDataTeam2);
        return ballDetailsLiveDataTeam2;
    }

    // Common method to fetch ball details and update respective LiveData
    private void fetchBallDetails(long inningsId, MutableLiveData<List<BallDetails>> liveData) {
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

                // Post the fetched ball details to the respective LiveData
                liveData.postValue(ballDetailsList);
            } catch (Exception e) {
                Log.e(TAG, "Error while fetching ball details for inningsId: " + inningsId, e);
            }
        }).start();
    }
}
