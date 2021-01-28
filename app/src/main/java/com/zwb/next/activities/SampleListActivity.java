package com.zwb.next.activities;

import com.zwb.next.activities.fragment.SampleListFragment;
import com.zwb.next.activities.fragment.AbstractFragmentActivity;

import androidx.fragment.app.Fragment;

public class SampleListActivity extends AbstractFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SampleListFragment();
    }
}
