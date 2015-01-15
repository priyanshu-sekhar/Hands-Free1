package com.example.android.handsfree;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.speech.RecognizerIntent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import static java.lang.Thread.sleep;

/**
 * Created by priyanshu on 13-Jan-15.
 */
public class ServiceReceiver extends BroadcastReceiver {
    DBHandler mHandler;
    AudioManager audio;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    public ServiceReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        switch (tm.getCallState()) {

            case TelephonyManager.CALL_STATE_RINGING:
                audio.setRingerMode(0);
                String phoneNr = intent.getStringExtra("incoming_number");
                mHandler = new DBHandler(context);
                Cursor pointer = mHandler.getQuery(DBReader.DBEntry.BLACKLIST_TABLE, phoneNr);
                pointer.moveToFirst();
                if (pointer.getCount() == 0) {
                    Cursor pointer1 = mHandler.getQuery(DBReader.DBEntry.TABLE_NAME, phoneNr);
                    pointer1.moveToFirst();
                    String text;
                    if (pointer1.getCount() != 0)
                        text = new String(pointer1.getString(pointer1.getColumnIndex(DBReader.DBEntry.COLUMN_NAME)));
                    else {
                        text = phoneNr.replace("", " ").substring(6).trim();
                    }
                    audio.setRingerMode(0);
                    final Activity activity = Blacklist_main.speakOut(text);


                    //startVoiceRecognitionActivity(activity);
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                sleep(400);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            } finally {
////                                final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
////                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
////                                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
////                                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition");
////                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
////                                intent.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
////                                Log.i("activity called", "voice recognition");
////
////                                activity.startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
//                                startVoiceRecognitionActivity(activity);
//                            }
//
//                        }
//                    }).start();
                    context.startActivity(new Intent(context, CallActionListener.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                    Thread thread = new Thread() {
//                        private int sleepTime = 400;
//
//                        @Override
//                        public void run() {
//                            super.run();
//                            try {
//                                int wait_Time = 0;
//
//                                while (wait_Time < sleepTime) {
//                                    sleep(100);
//                                    wait_Time += 100;
//                                }
//                            } catch (Exception e) {
//
//                            } finally {
//
//                            }
//                            //startVoiceRecognitionActivity(activity);
//
//                            context.startActivity(new Intent(context, TestActivity.class)
//                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                        }
//                    };
//                    thread.run();
                }
                    else {
                    String text = new String(pointer.getString(pointer.getColumnIndex(DBReader.DBEntry.COLUMN_NAME)));
                    audio.setRingerMode(0);
                    Blacklist_main.speakOut(text);
                    //new SpeechListener().startVoiceRecognitionActivity(new Splash());
                }

                break;
            case TelephonyManager.CALL_STATE_IDLE:
//                if (tts != null) {
//                    tts.stop();
//                    tts.shutdown();
//                }
                audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;


        }
    }

    public static void startVoiceRecognitionActivity(Activity activity) {

        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition");
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        intent.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
//        intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
        Log.i("activity called", "voice recognition");

        activity.startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);


    }
}
