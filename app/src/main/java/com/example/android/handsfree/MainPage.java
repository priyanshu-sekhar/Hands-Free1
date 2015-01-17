package com.example.android.handsfree;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TimePicker;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import java.lang.Object.*;
//import com.google.android.gcm.server.Constants;
public class MainPage extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, View.OnClickListener {

    private static Button bUnplug;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private ListView mDrawerList;
    FragmentManager myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        /** Capture View elements */


        /** Listener for click event of the button */



        /** Get the current time of system */
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        //mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    /**
     * To do when button is clicked
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        //showDialog(TIME_DIALOG_ID);
        //com.example.android.effectivenavigation.datetimepicker.time.TimePickerDialog dialog = newInstance((OnTimeSetListener) mTimeSetListener, getHour, getMinute, true);
    }

    /**
     * Create a new dialog for time picker
     */
//	@Override
//	@Deprecated
//	protected Dialog onCreateDialog(int id)
//	{
//		// TODO Auto-generated method stub
//		switch(id)
//		{
//		case TIME_DIALOG_ID:
//			return new TimePickerDialog(this, mTimeSetListener, getHour, getMinute, true);
//		}
//		return null;
//	}
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment selectedFrag = null;
//        fragmentManager.beginTransaction()
//                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
//                .commit();
        switch (position + 1) {
            case 1:
//                Intent intent = new Intent(this,TestContacts.class);
//                startActivity(intent);
                selectedFrag = new Blacklist_main();
//                        ft.replace(R.id.container,PlaceholderFragment.newInstance(1),Constants.TAG_FRAGMENT)
//                        .commit();
//                Intent intent = new Intent(this,Blacklist.class);
//                startActivity(intent);
                break;
            case 2:
                selectedFrag = new Whitelist_main();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container,PlaceholderFragment.newInstance(2))
//                        .commit();
                break;
//            case 3:
//                selectedFrag = new PureSilence();
////                fragmentManager.beginTransaction()
////                        .replace(R.id.container,PlaceholderFragment.newInstance(3))
////                        .commit();
            default:
                break;
        }
        if (selectedFrag != null)
            ft.replace(R.id.container, selectedFrag).commit();

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
//            case 3:
//                mTitle = getString(R.string.title_section3);
//                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#848482")));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        DBHandler mHandler = new DBHandler(getApplicationContext());
        SQLiteDatabase db = mHandler.getWritableDatabase();
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.i("settings", new String("settngs"));
                Toast.makeText(getApplicationContext(), "run", Toast.LENGTH_LONG).show();

                break;


            case R.id.action_sync:

                Cursor mCursor = getContacts();
                startManagingCursor(mCursor);
                mCursor.moveToFirst();
                do {
                    String name = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    mHandler.SaveTable(DBReader.DBEntry.TABLE_NAME, name, number);
                } while (mCursor.moveToNext());
                break;
            case R.id.action_blacklist:
                mHandler.deleteAll(DBReader.DBEntry.BLACKLIST_TABLE);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.container, new Blacklist_main()).addToBackStack(null).commit();
                break;
            case R.id.action_synceddata:
                mHandler.deleteAll(DBReader.DBEntry.TABLE_NAME);
                FragmentManager fragmentManager1 = getSupportFragmentManager();
                FragmentTransaction ft1 = fragmentManager1.beginTransaction();
                ft1.replace(R.id.container, new Blacklist_main()).addToBackStack(null).commit();
                break;
        }
        db.close();
        return false;
    }

    private Cursor getContacts() {
        // Run query
        String ContactNumber, ContactName, ContactID;
        ContactNumber = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Log.v("number:", ContactNumber);
        ContactName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        Log.v("names:", ContactName);
        ContactID = ContactsContract.CommonDataKinds.Phone._ID;

        String[] projection;
        projection = new String[]{ContactID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactName,
                ContactNumber
        };

        String selection = ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER + " = '1'";

        String[] selectionArgs = null;
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        return managedQuery(uri, projection, selection, selectionArgs,
                sortOrder);
    }
    public InputStream openPhoto(long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = getContentResolver().query(photoUri,
                new String[] {ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return new ByteArrayInputStream(data);
                }
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainPage) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    @SuppressLint("ValidFragment")
    public class BlacklistFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        //protected RadioButton test=(RadioButton)findViewById(R.id.radioButton);

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public BlacklistFragment newInstance(int sectionNumber) {
            BlacklistFragment blackFrag = new BlacklistFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            blackFrag.setArguments(args);
            return blackFrag;
        }

        public BlacklistFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View rootView = inflater.inflate(R.layout.navigate_blacklist, container, false);

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
//            ((MainPage) activity).onSectionAttached(
//                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class WhitelistFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static WhitelistFragment newInstance(int sectionNumber) {
            WhitelistFragment whiteFrag = new WhitelistFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            whiteFrag.setArguments(args);
            return whiteFrag;
        }

        public WhitelistFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.navigation_whitelist, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainPage) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class PureSilenceFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PureSilenceFragment newInstance(int sectionNumber) {
            PureSilenceFragment pureFrag = new PureSilenceFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            pureFrag.setArguments(args);
            return pureFrag;
        }

        public PureSilenceFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.navigate_pure_silence, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainPage) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


}

