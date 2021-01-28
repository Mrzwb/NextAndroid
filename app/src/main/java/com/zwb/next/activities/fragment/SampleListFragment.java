package com.zwb.next.activities.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zwb.next.R;
import com.zwb.next.model.Sample;
import com.zwb.next.model.SampleLab;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class SampleListFragment extends ListFragment {

    private ArrayList<Sample> mSamples;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.sample_tile);

        SampleLab lab = SampleLab.newInstance();
        SampleLab.generateData(lab);
        mSamples = lab.getSamples();

        //ArrayAdapter<Sample> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_sample_list, mSamples);
        SampleAdapter sampleAdapter = new SampleAdapter(mSamples);
        setListAdapter(sampleAdapter);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Sample sample = (Sample)getListAdapter().getItem(position);
        Toast.makeText(this.getContext(),sample.getTitle(),Toast.LENGTH_SHORT).show();
    }

    /**
     * 定制列表
     */
    class SampleAdapter extends ArrayAdapter<Sample> {

        public SampleAdapter(@NonNull ArrayList<Sample> objects) {
            super(getActivity(), 0, objects);
        }

        @Override
        public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_sample, null);
            }
            Sample sample = getItem(position);
            TextView titleText = convertView.findViewById(R.id.sample_title);
            titleText.setText(sample.getTitle());
            TextView descText = convertView.findViewById(R.id.sample_desc);
            descText.setText(sample.getDescription());

            return convertView;
        }
    }
}
