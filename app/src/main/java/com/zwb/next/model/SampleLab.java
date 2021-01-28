package com.zwb.next.model;

import android.content.Context;

import com.zwb.next.util.StringUtils;

import java.util.ArrayList;

public class SampleLab {

    private ArrayList<Sample> mSamples;

    private static SampleLab mSampleLab;

    public static void generateData(SampleLab lab) {
        ArrayList<Sample> arrayList = lab.getSamples();
        for (int i=0; i<100; i++) {
            Sample sample = new Sample();
            sample.setId("sample_"+i);
            sample.setDescription("示例描述-"+ "序号-"+ i);
            sample.setTitle("示例-"+i);
            arrayList.add(sample);
        }
    }

    private SampleLab() {
        mSamples = new ArrayList<>();
    }

    public static SampleLab newInstance() {
        return new SampleLab();
    }

    public Sample getSample(String id) {
        Sample sample = null;
        for (Sample s : mSamples) {
            if (StringUtils.equal(id, s.getId())) {
                sample = s;
                break;
            }
        }
        return sample;
    }

    public ArrayList<Sample> getSamples() {
        return mSamples;
    }

    public void setSamples(ArrayList<Sample> mSamples) {
        this.mSamples = mSamples;
    }

}
