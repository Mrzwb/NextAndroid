package com.zwb.next.activities.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.zwb.next.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {

    private static final String DIALOG_DAtE = "date";

    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Activity mActivity = getActivity();
        DatePicker dp = new DatePicker(mActivity);
        return new AlertDialog.Builder(mActivity)
                .setTitle("日期选择")
                .setView(dp)
                .setPositiveButton("OK",null).create();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.show(getFragmentManager(), DIALOG_DAtE);
    }


}
