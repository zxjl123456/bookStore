package com.example.ex_11;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    private final int requestCode=1;
    private final int resultCode=1;
    private ListView myListView;
    private ArrayList<HashMap<String,Object>> books;
    private static final String TAG  ="ManiActivity";
    private Dao dao;
    private SQLiteDatabase db;
    private static MainActivity ins;
    private TextView flashBook, show;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show=findViewById(R.id.book_show);
        ins=this;
        Toolbar toolbar= (Toolbar) this.findViewById(R.id.myToolbar);
        assert toolbar!=null;
        toolbar.setTitle("我的小图书馆");
        toolbar.setTitleTextColor(Color.WHITE);
        //将toolBar加入到活动中
        this.setSupportActionBar(toolbar);
        //创建数据库
        dao=new Dao(this);
        //为添加书籍textView控件添加监听事件
        TextView addBook=findViewById(R.id.add_book_tv);
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddBookActivity.class);
                startActivity(intent);

            }
        });
        //为突出textView添加监听事件
        toolbar.findViewById(R.id.exit_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });
        books=new ArrayList<HashMap<String, Object>>();
        myListView=findViewById(R.id.myListView);
        flashBook=findViewById(R.id.flash_book_tv);
        flashBookList();

    }
    public void flashBookList(){
        flashBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAdapter();
            }

        });
    }
    public void setAdapter(){
        books.clear();
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

        if (!books.isEmpty()){
         MySimpleAdapter mySimpleAdapter=new MySimpleAdapter(this,
                 books,
                 R.layout.add_book_list_item,
                 new String[]{"title"},
                 new int[]{R.id.book_title_tv});
            myListView.setAdapter(mySimpleAdapter);
        }
    }
    class MySimpleAdapter extends SimpleAdapter {
        public MySimpleAdapter(Context context,
                               List<? extends Map<String, ?>> data,
                               int resource, String[] from,
                               int[] to) {
            super(context, data, resource, from, to);
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            View view=super.getView(position,convertView,parent);
            Button details=view.findViewById(R.id.book_details_btn);
            Button book_read=view.findViewById(R.id.book_read_btn);
            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    HashMap<String,Object> bookHasMap=books.get(position);
                    Book book=new Book(bookHasMap.get("title").toString(),bookHasMap.get("author").toString(),bookHasMap.get("summary").toString());
                    bundle.putSerializable("book",book);
                    Intent intent=new Intent(MainActivity.this,BookDetailsActivity.class);
                    intent.putExtra("bundle",bundle);
                    MainActivity.this.startActivity(intent);
                }
            });
            book_read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendRequestWithHttpURLConnection();
                }
            });
            return view;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(final MenuItem item){
        Bundle bundle=new Bundle();
        switch (item.getItemId()){
            case R.id.title_list:
                bundle.putSerializable("books", (Serializable) books);
               // Intent intent=new Intent(MainActivity.this,TitleListActivity.class);
                Intent intent=new Intent("zxjl.ex11_toolBar.TITLE");
                intent.putExtra("bundle",bundle);
                startActivity(intent);
                break;
            case R.id.author_list:
                bundle.putSerializable("books", (Serializable) books);
               // Intent intent1=new Intent(MainActivity.this,AuthorActivity.class);
                Intent intent1=new Intent("zxjl.ex11_toolBar.AUTHOR");
                intent1.putExtra("bundle",bundle);
                startActivity(intent1);
                break;
            case R.id.bookInfo:
                Intent intent2=new Intent("zxjl.ex11.intent.BookInfo");
                startActivity(intent2);
                break;
             default:
                 break;
        }
        return true;
    }
    public static  MainActivity getInstance(){
        return ins;
    }
    private void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader reader=null;
                HttpURLConnection connection=null;
                try{
                    //服务器地址不可以用回环地址，否则连接失败，ipconfig 命令查看ipv4地址
                    URL url=new URL("http://10.85.56.171:8080/books/java.txt");
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);//设置连接属性
                    connection.setRequestMethod("GET");//希望从服务器获取数据
                    connection.setConnectTimeout(12000);
                    connection.setReadTimeout(12000);
                    int code=connection.getResponseCode();
                    if (code==200){
                        InputStream in=connection.getInputStream();//获取服务器返回的输入流
                        reader=new BufferedReader(new InputStreamReader(in));//读取获取的输入流
                        StringBuilder response=new StringBuilder();
                        String line;
                        while ((line=reader.readLine())!=null){
                            response.append(line+"\n");
                        }
                        //利用handler发送信息
                        Message message=Message.obtain();
                        message.what=1;
                        message.obj=response;
                        handler.sendMessage(message);
                        //showResponse(response.toString());
                        if(reader!=null){
                            try{
                                reader.close();
                            }catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if(connection!=null){
                            connection.disconnect();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(reader!=null){
                        try{
                            reader.close();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    private Handler handler=new Handler(){

        @Override
        public void handleMessage(android.os.Message msg){
            //处理子线程发送的消息
            Object result=msg.obj;
            show.setText(result.toString());
        }
    };
}
