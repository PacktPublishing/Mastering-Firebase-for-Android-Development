package com.packt.firebase.mastering.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, DashBoardActivity.class));
            finish();

        } else {
            Authenticate();
        }
    }

    private void Authenticate() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(getAuthProviderList())
                        .setIsSmartLockEnabled(false)
                        .build(),
                REQUEST_CODE);
    }

    private List<AuthUI.IdpConfig> getAuthProviderList() {
        List<AuthUI.IdpConfig> providers = new ArrayList<>();
        providers.add(
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());
        providers.add(new
                AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());
        providers.add(new
                AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build());
        providers.add(new
                AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build());
        providers.add(new
                AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build());
        return providers;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == ResultCodes.OK) {
                startActivity(new Intent(this, DashBoardActivity.class));
                return;
            }
        } else {
            if (response == null) {
               // User cancelled Sign-in
                return;
            }
            if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
               // Device has no network connection
                return;
            }
            if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
               // Unknown error occurred
                return;
            }
        }
    }


}



