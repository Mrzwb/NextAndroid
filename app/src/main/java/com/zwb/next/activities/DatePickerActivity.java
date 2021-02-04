package com.zwb.next.activities;

import android.os.Bundle;

import com.zwb.next.activities.fragment.DatePickerFragment;

import androidx.fragment.app.FragmentActivity;

/**
 * 示例 - DatePicker
 */
public class DatePickerActivity extends FragmentActivity {


    private static final String DIALOG_DATE = "date";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), DIALOG_DATE);

    }




}
