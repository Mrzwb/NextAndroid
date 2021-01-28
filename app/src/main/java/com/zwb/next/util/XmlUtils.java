package com.zwb.next.util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zhouwb 2020-01-03
 */
public final class XmlUtils {

    private static final String ns = null;

    public static List parse(InputStream in) throws IOException {
        List list = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();

            list = readList(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
        return list;
    }

    public static List readList(XmlPullParser parser) {
        return null;
    }


}
