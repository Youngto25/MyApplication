package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity3 extends AppCompatActivity {
    private InterstitialAd interstitialAd;
    private String TAG = "Activity4";
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        interstitialAd = new InterstitialAd(this, "IMG_16_9_APP_INSTALL#4523648584335513_4523653341001704");

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
//                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());

        Button bt = (Button)findViewById(R.id.show_ad_button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity3.this.showAdWithDelay();
            }
        });

        //是否支持google
//        int code = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(this);
//
//        Log.w("FcmReceiverService", String.valueOf(code));
//
//        Log.w("FcmReceiverService", String.valueOf(ConnectionResult.SUCCESS));
//
//        //
//        Log.w("FcmReceiverService", String.valueOf(GoogleApiAvailabilityLight.getInstance().isUserResolvableError(code)));

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FcmReceiverService", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("FcmReceiverService", token);
                    }
                });

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        AudienceNetworkAds.initialize(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "init");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "init");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "hello world");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public void showAdWithDelay() {
        Log.d(TAG, "showAdWithDelay");
        /**
         * Here is an example for displaying the ad with delay;
         * Please do not copy the Handler into your project
         */
        if(interstitialAd == null || !interstitialAd.isAdLoaded()) {
            return;
        }
        Log.d(TAG, "showAdWithDelay111111");
        // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
        if(interstitialAd.isAdInvalidated()) {
            return;
        }
        Log.d(TAG, "showAdWithDelay222222s");
        // Show the ad
        interstitialAd.show();
    }
}