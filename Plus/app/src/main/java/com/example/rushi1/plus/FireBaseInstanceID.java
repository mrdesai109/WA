package com.example.rushi1.plus;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by rushi1 on 27-09-2016.
 */
public class FireBaseInstanceID extends FirebaseInstanceIdService {
    private static final String reg_token = "REG_TOKEN";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(reg_token,token);
    }
}
