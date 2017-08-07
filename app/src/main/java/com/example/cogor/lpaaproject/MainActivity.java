package com.example.cogor.lpaaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button addItemButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addItemButton = (Button) findViewById(R.id.button);

        addItemButton.setOnClickListener(new View.OnClickListener()
                                         {


                                             @Override
                                             public void onClick(View v) {
                                                 Intent i = new Intent(getApplicationContext(), NewProductActivity.class);
                                                 startActivity(i);
                                             }
                                         }
        );

    }

}
