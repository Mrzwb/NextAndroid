package com.zwb.next.util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhouwb 2020-01-03
 */
public final class XmlUtils {

    private static final String INPUT_ENCODING = "UTF-8";

    private static final String ns = null;

    public static void parse(InputStream in, XMLParser xmlParser) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, INPUT_ENCODING);
            parser.nextTag();

            int eventType = parser.getEventType();
            String tag = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        xmlParser.dealWithStartTag(tag);
                        break;
                    case XmlPullParser.TEXT:
                        String text = parser.getText();
                        int depth = parser.getDepth();
                        xmlParser.dealWithText(tag, text, depth);
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        xmlParser.dealWithEndTag(tag);
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface XMLParser {

        void dealWithStartTag(String startTag);

        void dealWithText(String tag, String text, int depth);

        void dealWithEndTag(String endTag);
    }

}
