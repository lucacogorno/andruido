package com.example.cogor.lpaaproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    //Button addItemButton;
    ListView lv;
    ArrayList<Item> items;
    ArrayList<String> stringItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.itemsView);
        stringItems = new ArrayList<>();
        try {
            items = new GetItems().execute().get();
            Log.d("AFTER GET", items.get(0).name);

            for(int i=0; i<items.size(); i++)
            {

                stringItems.add(items.get(i).toString());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringItems);
            lv.setAdapter(arrayAdapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ItemFragment itemFragment = (ItemFragment) getFragmentManager().findFragmentById(R.id.itemfragment);
                        getFragmentManager().beginTransaction().replace(R.id.itemfragment, itemFragment, itemFragment.getClass().getSimpleName()).addToBackStack(null).commit();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

     //Rimosso per ora perchè copriva la listview

    /*
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
*/

    }

}
