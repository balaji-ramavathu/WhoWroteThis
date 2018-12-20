package com.example.balu.whowroteit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    EditText etBookInput;
    TextView tvTitle,tvAuthor;
    String bookInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etBookInput=findViewById(R.id.et_bookinput);
        tvTitle=findViewById(R.id.tv_title);
        tvAuthor=findViewById(R.id.tv_author);

    }


    public void searchButton(View view) {
        bookInput=etBookInput.getText().toString();
        InputMethodManager methodManager=(InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if(methodManager!=null)
        {
            methodManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        ConnectivityManager connectivityManager=
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=null;
        if(connectivityManager!=null)
        {
            networkInfo=connectivityManager.getActiveNetworkInfo();
        }
        if(networkInfo!=null&&networkInfo.isConnected()&&bookInput.length()!=0)
        {
            new FetchBook(tvTitle,tvAuthor).execute(bookInput);
            tvTitle.setText(R.string.loading);
            tvAuthor.setText("");
        }
        else
        {
            if(bookInput.length()==0)
            {
                tvAuthor.setText("");
                tvTitle.setText(R.string.no_search_term);
            }
            else
            {
                tvTitle.setText(R.string.no_network);
                tvAuthor.setText("");
            }
        }

    }
}
