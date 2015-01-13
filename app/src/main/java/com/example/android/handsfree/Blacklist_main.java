package com.example.android.handsfree;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by priyanshu on 12-Jan-15.
 */

public class Blacklist_main extends ListFragment implements View.OnClickListener {
    public ListView myListView;
    Cursor pointer;
    public String[] Contacts = {};
    View rootView;
    public ListView blackListView;
    public String[] blackListContacts = {};
    private FragmentActivity myContext;
    public int[] to = {};
    Button Add,Remove;
    DBHandler mHandler;
    ListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.navigate_blacklist, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Add = (Button) getView().findViewById(R.id.add);
        Add.setOnClickListener(this);
        Remove=(Button)getView().findViewById(R.id.remove);
        Remove.setOnClickListener(this);
        mHandler = new DBHandler(getActivity().getApplicationContext());
        pointer = mHandler.getTablePointer(DBReader.DBEntry.BLACKLIST_TABLE);
        AudioManager audio =(AudioManager)getActivity().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(0);
//        if(pointer.getInt(0)>0){
        pointer.moveToFirst();
        //Log.i("values", pointer.getString(1));
         adapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_list_item_multiple_choice,
                pointer,
                Contacts = new String[]{DBReader.DBEntry.COLUMN_NAME,
                        DBReader.DBEntry.COLUMN_PHONE
                        // ContactsContract.CommonDataKinds.Phone.NUMBER
                },
                to = new int[]{android.R.id.text1});

        setListAdapter(adapter);
        myListView = getListView();
        myListView.setItemsCanFocus(false);
        //myListView.setFastScrollEnabled(true);
        myListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                FragmentManager fragmentManager = myContext.getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.container, new Blacklist()).addToBackStack(null).commit();
                break;
            case R.id.remove:
               // mHandler=new DBHandler(getActivity().getApplicationContext());
                SQLiteDatabase db = mHandler.getWritableDatabase();
                SparseBooleanArray checkedPositions = myListView
                        .getCheckedItemPositions();


                if (checkedPositions != null) {
                    for (int k = 0; k < checkedPositions.size(); k++) {
//                        Log.i("value->", checkedPositions.keyAt(k) + "");
                        if (checkedPositions.valueAt(k)) {
                            int position = checkedPositions.keyAt(k);
                            pointer.moveToPosition(position);
                            //db.delete(DBReader.DBEntry.BLACKLIST_TABLE, DBReader.DBEntry._ID + "='" + pointer.getString(pointer.getColumnIndex(DBReader.DBEntry._ID)) + "'", null);
                            mHandler.deleteRowFromDatabase(DBReader.DBEntry.BLACKLIST_TABLE,
                                    DBReader.DBEntry._ID,
                                    pointer.getString(pointer.getColumnIndex(DBReader.DBEntry._ID))

                            );

                        }


                    }
                    FragmentManager fragmentManager2 = myContext.getSupportFragmentManager();
                    FragmentTransaction ft2 = fragmentManager2.beginTransaction();
                    ft2.replace(R.id.container, new Blacklist_main()).commit();
                }
                break;
        }
    }
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity)activity;
        super.onAttach(activity);
    }


}