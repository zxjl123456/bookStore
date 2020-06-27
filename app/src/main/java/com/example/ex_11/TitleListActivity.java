package com.example.ex_11;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TitleListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_list);
        Toolbar toolbar = findViewById(R.id.titleActivity_toolBar);
        assert toolbar != null;
        toolbar.setTitle("书名列表");
        toolbar.setTitleTextColor(Color.WHITE);
        this.setSupportActionBar(toolbar);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        ArrayList<HashMap<String,Object>>  books = (ArrayList<HashMap<String, Object>>) bundle.getSerializable("books");
        ListView activityTitleList = findViewById(R.id.titleActivity_title_list);
        MySimpleAdapter1 mySimpleAdapter1=new MySimpleAdapter1(
                TitleListActivity.this,
                books,
                R.layout.title_list,
                new String[]{"title"},
                new int[]{R.id.title_list_item}
        );
        activityTitleList.setAdapter(mySimpleAdapter1);
        toolbar.findViewById(R.id.title_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TitleListActivity.this.finish();
            }
        });
    }
    class MySimpleAdapter1 extends SimpleAdapter {
        public MySimpleAdapter1(Context context,
                                List<? extends Map<String, ?>> data,
                                int resource, String[] from,
                                int[] to) {
            super(context, data, resource, from, to);
        }
    }
}


