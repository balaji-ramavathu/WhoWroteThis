package com.example.balu.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String,Void,String>
{
    private WeakReference<TextView> tvTitle,tvAuthor;

     public FetchBook(TextView tv_Title,TextView tv_Author)
     {
         this.tvTitle=new WeakReference<>(tv_Title);
         this.tvAuthor=new WeakReference<>(tv_Author);

     }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try
        {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("items");
            int i=0;
            String title=null;
            String authors=null;
            while(i<jsonArray.length()&&title==null&&authors==null)
            {
                JSONObject book=jsonArray.getJSONObject(i);
                JSONObject volumeInfo=book.getJSONObject("volumeInfo");
                try
                {
                    title=volumeInfo.getString("title");
                    authors=volumeInfo.getString("authors");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                i++;
            }
            if(title!=null && authors!=null)
            {
                tvTitle.get().setText(title);
                tvAuthor.get().setText(authors);
            }
            else
            {
                tvTitle.get().setText(R.string.no_results);
                tvAuthor.get().setText("");
            }

        }
        catch (JSONException e)
        {
            tvTitle.get().setText(R.string.no_results);
            tvAuthor.get().setText("");
            e.printStackTrace();
        }
    }
}
