package com.example.cogor.lpaaproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * Created by cogor on 06/07/2017.
 */

public class NewProductActivity extends Activity {

    private ProgressDialog progressDialog;
    EditText prodName;
    EditText prodQuant;
    EditText prodPrice;
    EditText prodDescr;
    Button addButton;

    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/create_product.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_prod_layout);

        prodName = (EditText) findViewById(R.id.inputName);
        prodQuant = (EditText) findViewById(R.id.inputQuantity);
        prodPrice = (EditText) findViewById(R.id.inputPrice);
        prodDescr = (EditText) findViewById(R.id.prod_descr);

        addButton = (Button) findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CreateProduct createProduct = new CreateProduct();
                createProduct.execute();
                try {
                   String result = createProduct.get();
                    if(result != null) {
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        Log.d("CONNECTION", result);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
        });

    }
    class CreateProduct extends AsyncTask<String, String, String>
    {
        String query;
        String charset = "UTF-8";
        String name;
        String quantity;
        String price;
        String description;
        URL reqURL;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            name = prodName.getText().toString();
            quantity = prodQuant.getText().toString();
            price = prodPrice.getText().toString();
            description = prodDescr.getText().toString();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                query = String.format("name=%s&quantity=%s&price=%s&description=%s",
                        URLEncoder.encode(name, charset),
                        URLEncoder.encode(quantity, charset),
                        URLEncoder.encode(price, charset),
                        URLEncoder.encode(description, charset));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                URL reqURL = new URL(requestURL);
                HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Accept-Charset", charset);
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
                OutputStream outputStream = urlConnection.getOutputStream();
                outputStream.write(query.getBytes(charset));
                InputStream response = urlConnection.getInputStream();
                Scanner s = new Scanner(response).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
