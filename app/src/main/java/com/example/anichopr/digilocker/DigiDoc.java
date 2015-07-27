package com.example.anichopr.digilocker;

import android.graphics.Bitmap;
import android.os.StrictMode;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by anichopr on 7/27/2015.
 */
public class DigiDoc {

    int serialNo;
    String documentName;
    Date uploadDate;
    Date expiryDate;
    String status;
    String documentURL;
    int shareId;
    Bitmap bitmap;

    static int noOfOtherDocs = 0;
    static int noOfEssentialDocs = 0;

    public DigiDoc(String documentName, String documentURL) {
        this.documentName = documentName;
        this.documentURL = documentURL;
    }
}