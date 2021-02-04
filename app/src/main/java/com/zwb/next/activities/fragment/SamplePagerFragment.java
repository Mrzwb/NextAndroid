package com.zwb.next.activities.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.zwb.next.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SamplePagerFragment extends Fragment {

    private SamplePagerAdapter samplePagerAdapter;

    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sample_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        samplePagerAdapter = new SamplePagerAdapter(this.getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(samplePagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class SamplePagerAdapter extends FragmentStatePagerAdapter {

        public SamplePagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new SampleObjectFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(SampleObjectFragment.ARG_OBJECT, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return SampleObjectFragment.ARG_OBJECT + (position + 1);
        }
    }

    public static class SampleObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "标签";

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_sample_object, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            Bundle args = getArguments();
            ImageView imageView =  ((ImageView) view.findViewById(R.id.iv_sample_obj));
            Drawable drawable = getDrawable(args.getInt(ARG_OBJECT));
            imageView.setImageDrawable(drawable);
        }

        public Drawable getDrawable(int argId) {
            int id =  R.mipmap.iv_lol_icon1;
            switch (argId) {
                case 1:
                    break;
                case 2 :
                    id = R.mipmap.iv_lol_icon2;
                    break;
                case 3 :
                    id = R.mipmap.iv_lol_icon3;
                    break;
                case 4 :
                    id = R.mipmap.iv_lol_icon4;
                    break;
                default:
                    break;
            }

            return getActivity().getDrawable(id);
        }
    }


}
