package com.example.balu.whowroteit;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String LOG_TAG=NetworkUtils.class.getSimpleName();
    // Base URL for Books API.
    private static final String BOOK_BASE_URL =  "https://www.googleapis.com/books/v1/volumes?";
    // Parameter for the search string.
    private static final String QUERY_PARAM = "q";
    // Parameter that limits search results.
    private static final String MAX_RESULTS = "maxResults";
    // Parameter to filter by print type.
    private static final String PRINT_TYPE = "printType";


    static String getBookInfo(String query)
    {
        HttpURLConnection urlConnection=null;
        BufferedReader reader=null;
        String bookJSONString=null;

        try
        {
            Uri builtUri=Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM,query)
                    .appendQueryParameter(MAX_RESULTS,"10")
                    .appendQueryParameter(PRINT_TYPE,"books").build();
            URL requestURL=new URL(builtUri.toString());
            urlConnection=(HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream=urlConnection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder=new StringBuilder();

            String line;
            while ((line=reader.readLine())!=null)
            {
                builder.append(line);
                builder.append("\n");
            }
            if(builder.length()==0)
            {
                return null;
            }
            bookJSONString=builder.toString();


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            if(urlConnection!=null)
            {
                urlConnection.disconnect();
            }
            if(reader!=null)
            {
                try {
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        }
        Log.d(LOG_TAG,bookJSONString);

        return bookJSONString;
    }
}
