package com.example.ex_11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Toolbar toolbar=findViewById(R.id.book_details_toolBar);
        assert toolbar!=null;
        toolbar.setTitle("书籍信息");
        toolbar.setTitleTextColor(Color.WHITE);
        this.setSupportActionBar(toolbar);
        Intent intent=this.getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        Book book= (Book) bundle.getSerializable("book");
        assert book!=null;

        String[] bookInfo=new String[]{
                "书名："+book.getTitle(),
                "作者："+book.getAuthor(),
                "简介："+book.getSummary()};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(
                BookDetailsActivity.this,
                R.layout.book_details_item,
                R.id.details_tv,
               bookInfo
        );
        ListView detailsList=findViewById(R.id.book_details_lv);
        detailsList.setAdapter(arrayAdapter);
        TextView return_home=findViewById(R.id.return_home_tv);
        return_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailsActivity.this.finish();
            }
        });
        }
}
