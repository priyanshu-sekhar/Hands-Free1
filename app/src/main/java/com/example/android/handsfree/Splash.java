package com.example.android.handsfree;

/**
 * Created by priyanshu on 09-Jan-15.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;


public class Splash extends Activity
{
    private View mDecorView;
    public static final String MyPrefs = "MyPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        mDecorView=getWindow().getDecorView();
        hideSystemUI();
        // Runs for 2 secs then goes to next activity
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                } finally{

                    SharedPreferences sp = getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
                    if(!sp.getBoolean("First", false))
                    {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("First", true);
                        editor.commit();
                        Intent intent = new Intent(Splash.this, LaunchTutorial.class);
                        startActivity(intent);
                    }

                    else
                    {
                        Intent intent = new Intent(Splash.this, MainPage.class);
                        startActivity(intent);
                    }


                }
            }
        };
        timer.start();
    }
    private void hideSystemUI(){
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_LOW_PROFILE
                        |View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }


}