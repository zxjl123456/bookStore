package com.example.ex_11;

import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public MyBroadcastReceiver() {
        super();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"received in ex_11 MyBroadcastReceiver", Toast.LENGTH_SHORT).show();
        MainActivity.getInstance().flashBookList();
    }

}
