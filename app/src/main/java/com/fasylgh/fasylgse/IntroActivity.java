package com.fasylgh.fasylgse;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;


import com.ramotion.paperonboarding.PaperOnboardingEngine;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnChangeListener;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {


    ArrayList<PaperOnboardingPage> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_main_layout);
//                     Toast.makeText(getApplicationContext(), "color "+elements.get(newElementIndex).getBgColor(), Toast.LENGTH_SHORT).show();

        getSupportActionBar().hide();

        PaperOnboardingEngine engine = new PaperOnboardingEngine(findViewById(R.id.onboardingRootView), getDataForOnboarding(), getApplicationContext());
        engine.setOnChangeListener(new PaperOnboardingOnChangeListener() {
            @Override
            public void onPageChanged(int oldElementIndex, int newElementIndex) {
//                Toast.makeText(getApplicationContext(), "Swiped from " + oldElementIndex + " to " + newElementIndex, Toast.LENGTH_SHORT).show();
//                ActionBar bar = getActionBar();
//                bar.setBackgroundDrawable(new ColorDrawable(elements.get(newElementIndex).getBgColor()));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(elements.get(newElementIndex).getBgColor());

                }

            }
        });

        engine.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                // Probably here will be your exit action
//                Toast.makeText(getApplicationContext(), "Swiped out right", Toast.LENGTH_SHORT).show();
                loadDigitsActivity();
            }
        });


    }


    // Just example data for Onboarding
    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {
        // prepare data
        PaperOnboardingPage scr1 = new PaperOnboardingPage("FasylGSE", "Welcome, \n. Easily Check Stocks of the Ghana Storks Exchangee",
                Color.parseColor("#678FB4"), R.drawable.ic_show_chart_black_24dp,  R.drawable.ic_show_chart_black_24dp);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Charts ", "View Charts easily and seemlessly.",
                Color.parseColor("#65B0B4"), R.drawable.ic_insert_chart_black_24dp, R.drawable.ic_insert_chart_black_24dp);


        elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);

        return elements;
    }

    private void loadDigitsActivity() {
        Intent intent = new Intent(this, StocksActivity.class);
        startActivity(intent);
        finish();
    }
}
