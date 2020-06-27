package com.example.ex_11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class AddBookActivity extends AppCompatActivity {

    private EditText title_edt;
    private EditText author_edt;
    private EditText summary_edt;
    private TextView finish_btn;
    private Toolbar toolbar;
    private Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        init();
        this.setSupportActionBar(toolbar);
        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=title_edt.getText().toString().trim();
                String author=author_edt.getText().toString().trim();
                String summary=summary_edt.getText().toString().trim();
                ContentValues values=new ContentValues();//创建ContentValues封装记录信息
                values.put("title",title);
                values.put("author",author);
                values.put("summary",summary);
                //创建数据库工具类
                dao=new Dao(getApplicationContext());
                dao.insert("bookTab",values);
                Intent intent=new Intent(AddBookActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

   public  void init(){
       title_edt = findViewById(R.id.book_title_edt);
       author_edt = findViewById(R.id.book_author_edt);
       summary_edt = findViewById(R.id.book_summary_edt);
       finish_btn = findViewById(R.id.add_finish);
       toolbar=findViewById(R.id.add_book_toolbar);
       toolbar.setTitle("添加图书");
       toolbar.setTitleTextColor(Color.WHITE);
   }
}
