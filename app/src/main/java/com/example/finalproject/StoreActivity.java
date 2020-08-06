package com.example.finalproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class StoreActivity extends AppCompatActivity {

    public String KEY_TYPE_FROM = "type_from";

    private Toast mToast;
    private String[] args = {"亲爱的用户您好，\n注册成功将会看到本则消息，\n也代表您可以继续使用本APP，\n请放心使用，\n再次祝您使用愉快！",
            "亲爱的用户您好，\n以下帮助可以协助您使用本APP,\n1........\n2.......\n3........"};
    private String[] title = {"注册成功提示", "使用说明"};

    public ImageButton mHomeBtn;
    public ImageButton mSearchBtn;
    public ImageButton mAddBtn;
    public ImageButton mStoreBtn;
    public ImageButton mSelfBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_store);

        mHomeBtn = findViewById(R.id.btn_home);
        mSearchBtn = findViewById(R.id.btn_search);
        mAddBtn = findViewById(R.id.btn_add);
        mStoreBtn = findViewById(R.id.btn_store);
        mSelfBtn = findViewById(R.id.btn_me);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StoreActivity.this, SearchActivity.class));
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });

        mHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivity.this, VideoActivity.class);
                intent.putExtra(KEY_TYPE_FROM, 0);
                startActivity(intent);
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });

        mStoreBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivity.this, StoreActivity.class);
                startActivity(intent);
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });

        mSelfBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreActivity.this, SelfActivity.class);
                startActivity(intent);
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });

        ListView listView = (ListView) findViewById(R.id.list_numbers);
        listView.setAdapter(new ListBaseAdapter());
        listView.setDivider(null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (mToast != null) {
//                    mToast.cancel();
//                }
//                String toastMessage = "Item #" + position + " clicked.";
//                mToast = Toast.makeText(StoreActivity.this, toastMessage, Toast.LENGTH_LONG);
//                mToast.show();
                AlertDialog alertDialog1 = new AlertDialog.Builder(StoreActivity.this)
                        .setTitle(title[position])//标题
                        .setMessage(args[position])//内容
                        .setIcon(R.mipmap.ic_launcher)//图标
                        .create();
                alertDialog1.show();
            }
        });

    }
}