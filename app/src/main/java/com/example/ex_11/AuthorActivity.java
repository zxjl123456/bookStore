package com.example.ex_11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
        Toolbar toolbar = findViewById(R.id.authorActivity_toolBar);
        assert toolbar != null;
        toolbar.setTitle("作者列表");
        toolbar.setTitleTextColor(Color.WHITE);
        this.setSupportActionBar(toolbar);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        ArrayList<HashMap<String,Object>> books = (ArrayList<HashMap<String, Object>>) bundle.getSerializable("books");
        ListView activityAuthorList = findViewById(R.id.author_list_activity);
        MySimpleAdapter2 mySimpleAdapter2=new MySimpleAdapter2(
                AuthorActivity.this,
                books,
                R.layout.author_list_item,
                new String[]{"author"},
                new int[]{R.id.author_list_item}
        );
        activityAuthorList.setAdapter(mySimpleAdapter2);
        toolbar.findViewById(R.id.author_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthorActivity.this.finish();
            }
        });
    }
    class MySimpleAdapter2 extends SimpleAdapter {
        public MySimpleAdapter2(Context context,
                                List<? extends Map<String, ?>> data,
                                int resource, String[] from,
                                int[] to) {
            super(context, data, resource, from, to);
        }
    }
}
