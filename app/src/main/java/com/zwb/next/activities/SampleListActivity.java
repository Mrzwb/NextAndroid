package com.zwb.next.activities;

import com.zwb.next.activities.fragment.SampleListFragment;
import com.zwb.next.activities.fragment.AbstractFragmentActivity;

import androidx.fragment.app.Fragment;

/**
 * 示例 - demo列表活动
 */
public class SampleListActivity extends AbstractFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SampleListFragment();
    }

}
