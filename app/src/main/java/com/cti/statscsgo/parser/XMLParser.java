package com.cti.statscsgo.parser;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.cti.statscsgo.database.Coder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class XMLParser {

    private int RTN_CONNECTION = 0, RTN_NOT_PUBLIC = 1, RTN_ALL_RIGHT = 2, RTN_DONT_PLAYED = 3,
    RTN_NOT_FOUND = 4;

    private String API_URL_STATS = "http://api.steampowered.com/ISteamUserStats/GetUserStatsForGame/v0002/?appid=730&key=***OD&steamid=";

    //Read full document in url and return it like String
    public static String getDocument(URL url) {
        String str;
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8") );
            int connectionSuccess = connection.getResponseCode();
            if (connectionSuccess != HttpURLConnection.HTTP_OK) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            char ch[] = new char[2048];
			/*while ( (line = bf.readLine()) != null ) {
				sb.append(line);
			}*/
            int n;
            while ((n = bf.read(ch))>=0) {
                sb.append(ch, 0, n);
            }
            str = sb.toString();
            bf.close();
            connection.disconnect();
            return str;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    //Parse it and add in DB the results
    public int setStats(String str, SQLiteDatabase db, Context context,
                            String loginSteam, boolean flag_CODED) {
        Document doc = Jsoup.parse(str, "UTF-8", Parser.xmlParser());
        ContentValues cv = new ContentValues();
        if (doc.select("error").first() != null)
            return RTN_NOT_FOUND;
        if (!doc.select("privacyState").first().text().equals("public"))
            return RTN_NOT_PUBLIC;
        if (doc.select("steamID").first()==null)
            return RTN_CONNECTION;
        //Log.d("TAG", loginSteam);
        if (loginSteam != null) {
            db.delete(loginSteam, null, null);
        }
        cv.put("name", "steamID"); cv.put("value", doc.select("steamID").first().text());
        db.insert(loginSteam, null, cv);
        cv.put("name", "avatarFull"); cv.put("value", doc.select("avatarFull").first().text());
        db.insert(loginSteam, null, cv);
        cv.put("name", "steamID64"); cv.put("value", doc.select("steamID64").first().text());
        db.insert(loginSteam, null, cv);
        cv.put("name", "realname"); cv.put("value", doc.select("realname").first().text());
        db.insert(loginSteam, null, cv);
        cv.put("name", "customURL"); cv.put("value", doc.select("customURL").first().text());
        db.insert(loginSteam, null, cv);
        try {
            loadImage(new URL(doc.select("avatarFull").first().text()), context, doc.select("customURL").text());
            String loginSteam_coded = loginSteam;
            if (flag_CODED) {
                Coder code = new Coder();
                loginSteam_coded = code.deCode(loginSteam);
            }
            loadImage(new URL(doc.select("avatarMedium").first().text()), context, loginSteam_coded+"_m");
        } catch (MalformedURLException e1) {
            Log.e("INTERNET", "Problem with INTERNET CONNECTION...");
            return RTN_CONNECTION;
        }
        try {
            //Вместо CV было stats
            Log.d("TAG", API_URL_STATS+doc.select("steamID64").first().text()+"&format=xml");
            if (( str = getDocument(new URL(API_URL_STATS+doc.select("steamID64").first().text()+"&format=xml")) ) == null)
                return RTN_CONNECTION;
        } catch (MalformedURLException e) {
           return RTN_CONNECTION;
        }
        doc = Jsoup.parse(str, "UTF-8", Parser.xmlParser());
        Elements elements = doc.select("stat");
        int ii = 0;
        for (Element i : elements) {
            cv.put("name", i.select("name").text());
            cv.put("value", i.select("value").text());
            db.insert(loginSteam, null, cv);
            ++ii;
        }

        if (ii > 0) return RTN_ALL_RIGHT;
        return RTN_DONT_PLAYED;
    }

    //Loading images/avatar by url and save it in according folder
    private boolean loadImage(URL url, Context context, String name) {
        try {
            int buffer_size = 1024;
            int count = 0;
            FileOutputStream fout = new FileOutputStream(context.getDir(Environment.DIRECTORY_PICTURES, Context.MODE_PRIVATE).getAbsolutePath()+"/"+name.toLowerCase()+".jpg");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if (HttpURLConnection.HTTP_OK != connection.getResponseCode())
                return false;
            InputStream sin = connection.getInputStream();
            byte buf[] = new byte[1024];
            while (( count = sin.read(buf, 0, buffer_size))>-1) {
                fout.write(buf, 0, count);
            }
            fout.close();
            sin.close();
            connection.disconnect();

        } catch (IOException e) {
            Log.e("INTERNET", "Error with loading from INTERNET");
        }

        return true;
    }
}