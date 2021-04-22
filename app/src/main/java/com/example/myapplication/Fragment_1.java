package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

// Banner
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

// Insert
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

// App Open
import android.app.Application;

public class Fragment_1 extends AppCompatActivity {
    private int img[] = {R.id.image_1, R.id.image_2, R.id.image_3, R.id.image_4};
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private static AppOpenManager appOpenManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_1);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        appOpenManager = new AppOpenManager(this);

        // Banner
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Insert
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
//                Log.i(TAG, "onAdLoaded");
            }
        });

        // 获取屏幕宽高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getCurrentWindowMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        // 动态添加 Fragment
        FragmentManager fm = getSupportFragmentManager();

        Fragment frag = fm.findFragmentById(R.id.imageList);

        if (screenWidth > screenHeight) { // 横屏
            if (frag != null) {
                fm.beginTransaction().remove(frag).commitNow();
            }
        } else { //竖屏
            if (frag == null) {
                fm.beginTransaction().add(R.id.imageList, new SecondFragment()).commitNow();
            }
        }


        Button btn = findViewById(R.id.changeButton2);
//        btn.setOnClickListener(new myOnClick());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView t = findViewById(R.id.firstText);
                t.setText(R.string.hello_first_fragment);
                Fragment_1.this.setTitle(R.string.hello_first_fragment);
            }
        });
    }

    // 由Activity进行Fragment控件的事件处理
    @Override
    protected void onStart() {
        super.onStart();
        final ImageView image = (ImageView) findViewById(R.id.view_image);
        for (int i = 0; i < img.length; i++) {
            ImageView iv = (ImageView) findViewById(img[i]);
            if (iv != null) {
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.d("被点击了", v.toString());
                        ImageView view = (ImageView) v;
                        if (image.getDrawable() == view.getDrawable()) {
                            image.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_foreground));
                            return;
                        }
                        image.setImageDrawable(view.getDrawable());
                    }
                });
            }

        }
    }

    public class myOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            TextView t = findViewById(R.id.firstText);
            t.setText(R.string.hello_first_fragment);
            Fragment_1.this.setTitle(R.string.hello_first_fragment);
        }
    }

    public void changeTheText(View view) {
        TextView t = findViewById(R.id.firstText);
        t.setText(R.string.hello_first_fragment);
        this.setTitle(R.string.hello_first_fragment);

        if (mInterstitialAd != null) {
            mInterstitialAd.show(Fragment_1.this);
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }
}