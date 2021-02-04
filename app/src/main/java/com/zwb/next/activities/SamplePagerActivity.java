package com.zwb.next.activities;

import android.os.Bundle;

import com.zwb.next.activities.fragment.AbstractFragmentActivity;
import com.zwb.next.activities.fragment.SamplePagerFragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * 示例 - ViewPager
 */
public class SamplePagerActivity extends AbstractFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SamplePagerFragment();
    }

}
