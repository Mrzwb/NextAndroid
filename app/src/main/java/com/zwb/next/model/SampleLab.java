package com.zwb.next.model;

import android.content.Context;
import com.zwb.next.util.AssetsUtils;
import com.zwb.next.util.StringUtils;
import com.zwb.next.util.XmlUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class SampleLab {

    private ArrayList<Sample> mSamples;

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

    public static void generateData(final Context context, SampleLab lab) {
        final ArrayList<Sample> arrayList = lab.getSamples();
        DataFactory.loadXmlData(context, arrayList);
    }

    private static class DataFactory {

        static final String XML_FILE = "sample_list.xml";

        // 深度为3时为文本
        static final int XML_TEXT_DEPTH = 3;

        static void loadXmlData(final Context context, final ArrayList<Sample> arrayList) {
            InputStream is = AssetsUtils.getFile(context, XML_FILE);
            final HashMap<String, Sample> map = new HashMap<>(1);
            XmlUtils.parse(is, new XmlUtils.XMLParser() {
                @Override
                public void dealWithStartTag(String startTag) {
                    if ("sample".equals(startTag)) {
                        Sample sample = new Sample();
                        map.put("newKey",sample);
                    }
                }

                @Override
                public void dealWithText(String tag, String text, int depth) {
                    if (XML_TEXT_DEPTH == depth) {
                        if ("id".equals(tag)) {
                            Sample sample = map.get("newKey");
                            sample.setId(text);
                        }
                        if ("title".equals(tag)) {
                            Sample sample = map.get("newKey");
                            sample.setTitle(text);
                        }

                        if ("desc".equals(tag)) {
                            Sample sample = map.get("newKey");
                            sample.setDescription(text);
                        }
                        if ("activity".equals(tag)) {
                            Sample sample = map.get("newKey");
                            sample.setClassName(text);
                        }
                    }
                }
                @Override
                public void dealWithEndTag(String endTag) {
                    if ("sample".equals(endTag)) {
                        arrayList.add(map.get("newKey"));
                    }
                }
            });
        }
    }
}
