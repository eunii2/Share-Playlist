package com.msp.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HtmlParser {
    /*public String getImageUrl(String url){
        try {
            Document doc = Jsoup.connect(url).get();
            Element head = doc.head();
            return head.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public String getOgImageUrlFromUrl(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements metaTags = doc.select("meta[property=og:image]");

            for (Element metaTag : metaTags) {
                String content = metaTag.attr("content");
                if (content != null && !content.isEmpty()) {
                    return content;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
