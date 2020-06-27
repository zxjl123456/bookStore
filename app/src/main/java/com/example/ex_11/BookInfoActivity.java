package com.example.ex_11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookInfoActivity extends AppCompatActivity {
private TabHost tabHost;
private ListView titleList;
private ListView authorList;
private List<Map<String, Object>> books;
private MyAdapter myAdapter;
private Dao dao;
private Toolbar toolbar;
private TextView exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        init();
        Intent intent=getIntent();
        books = new ArrayList<Map<String, Object>>();
        dao = new Dao(this);
        getData();
        //获取设置菜单条
        getToolBar();
      this.setSupportActionBar(toolbar);
        //创建TabHost
        tabHost.setup();
        //添加标签  tab1:标识   书名标签：标签名称  tab1:容器（这里是一个线性布局）
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("书名标签").setContent(R.id.tab1));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("作者标签").setContent(R.id.tab2));
        tabHost.setCurrentTab(0);
        setTitleAdapter();
        myTab(tabHost);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            // tabId是newTabSpec第一个参数设置的tab页名，并不是layout里面的w标识符id
            public void onTabChanged(String tabId) {
                myTab(tabHost);
                if (tabId.equals("tab1")) {   //第一个标签
                 myAdapter=new MyAdapter(BookInfoActivity.this,
                        books,
                        R.layout.title_list,
                        new String[]{"title"},
                       new int[] {R.id.title_list_item});
                setTitleAdapter();
                }
                if (tabId.equals("tab2")) {   //第二个标签

                    setAuthorAdapter();
                }
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookInfoActivity.this.finish();
            }
        });
    }
        void myTab(TabHost tabHost){
            for (int i=0;i<tabHost.getTabWidget().getChildCount();i++){
                View view=tabHost.getTabWidget().getChildAt(i);
                if (i==tabHost.getCurrentTab()){
                    view.setBackgroundColor(Color.rgb(92,192,212));
                }
                else {
                    view.setBackgroundColor(Color.rgb(245,245,245));
                }
            }
        }
        public void init(){
            tabHost=(TabHost) findViewById(R.id.bookInfo_tabHost);
            titleList=findViewById(R.id.title_list_tabHost);
            authorList=findViewById(R.id.author_list_tabHost);
        }
    public void setTitleAdapter(){
        if (!books.isEmpty()){
            MyAdapter mySAdapter=new MyAdapter(this,
                    books,
                    R.layout.title_list,
                    new String[]{"title"},
                    new int[]{R.id.title_list_item});
            titleList.setAdapter(mySAdapter);
        }
    }
    public void setAuthorAdapter(){
        if (!books.isEmpty()){
            MyAdapter mySAdapter=new MyAdapter(BookInfoActivity.this,
                    books,
                    R.layout.author_list_item,
                    new String[]{"author"},
                    new int[]{R.id.author_list_item});
            authorList.setAdapter(mySAdapter);
        }
    }
    public void getData(){
        String title,author,summary;
        Cursor cursor=dao.query("bookTab");
        while (cursor.moveToNext()) {
            HashMap<String,Object> bookHashMap=new HashMap<String, Object>();
            title=cursor.getString(1);
            author=cursor.getString(2);
            summary=cursor.getString(3);
            bookHashMap.put("title",title);
            bookHashMap.put("author",author);
            bookHashMap.put("summary",summary);
            books.add(bookHashMap);
        }
        cursor.close();
    }
    public void getToolBar(){
        toolbar = (Toolbar) findViewById(R.id.bookInfo_toolBar);
        assert toolbar != null;
        toolbar.setTitle("书籍信息");
        toolbar.setTitleTextColor(Color.WHITE);
        exit=findViewById(R.id.bookInfo_exit);
    }
}
