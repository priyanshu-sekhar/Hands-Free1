    package com.example.android.handsfree;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.handsfree.R;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

    public class CallActionListener extends ActionBarActivity {
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_test);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            Log.i("onAct", "entered");
            if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == ActionBarActivity.RESULT_OK) {
                // Fill the list view with the strings the recognizer thought it could have heard
               ArrayList<String> matches = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);

                Log.i("Entered reply", matches.toString());
                //Toast.makeText(getApplicationContext(),matches.toString(),Toast.LENGTH_SHORT).show();
                if(matches.toString().equals("[yes]"))
                    connectCallAndroid();
                else
                disconnectCallAndroid();
//                Executor eS = Executors.newSingleThreadExecutor();
//                eS.execute(new Runnable() {
//                    //final String reply = matches.toString();
//
//                    @Override
//                    public void run() {
//                        connectCallAndroid();
//                    }
//                });// code formatting with tohtml.com/java/
                super.onActivityResult(requestCode, resultCode, data);

                finish();
            }

        }
        public int disconnectCallAndroid()
        {
            Runtime runtime = Runtime.getRuntime();
            int nResp = 0;
            try
            {
                Log.d("discon", "service call phone 5 \n");
                runtime.exec("service call phone 5 \n");
            }catch(Exception exc)
            {
                Log.e("discon", exc.getMessage());
                exc.printStackTrace();
            }
            return nResp;
        }

        public void connectCallAndroid()
        {
            Log.d("connect", "InSecond Method Ans Call");
            // froyo and beyond trigger on buttonUp instead of buttonDown
            Intent buttonUp = new Intent(Intent.ACTION_MEDIA_BUTTON);
            buttonUp.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(
                    KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
            sendOrderedBroadcast(buttonUp, "android.permission.CALL_PRIVILEGED");
            Intent headSetUnPluggedintent = new Intent(Intent.ACTION_HEADSET_PLUG);
            headSetUnPluggedintent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY);
            headSetUnPluggedintent.putExtra("state", 0);
            headSetUnPluggedintent.putExtra("name", "Headset");
            try {
                sendOrderedBroadcast(headSetUnPluggedintent, null);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
