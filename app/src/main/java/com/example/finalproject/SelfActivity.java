package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class SelfActivity extends AppCompatActivity {

    public String KEY_TYPE_FROM = "type_from";

    ImageView blurImageView;
    ImageView avatarImageView;
    TextView userNameTextView;

    public ImageButton mHomeBtn;
    public ImageButton mSearchBtn;
    public ImageButton mAddBtn;
    public ImageButton mStoreBtn;
    public ImageButton mSelfBtn;

    public FrameLayout self_item_1;
    public ImageView img1;
    public Button btn1;
    public FrameLayout self_item_2;
    public ImageView img2;
    public Button btn2;
    public FrameLayout self_item_3;
    public ImageView img3;
    public Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_self);

        blurImageView = findViewById(R.id.h_back);
        avatarImageView = findViewById(R.id.h_head);
        userNameTextView = findViewById(R.id.user_name);

        mHomeBtn = findViewById(R.id.btn_home);
        mSearchBtn = findViewById(R.id.btn_search);
        mAddBtn = findViewById(R.id.btn_add);
        mStoreBtn = findViewById(R.id.btn_store);
        mSelfBtn = findViewById(R.id.btn_me);

        self_item_1 = findViewById(R.id.component_self_item_1);
        self_item_2 = findViewById(R.id.component_self_item_2);
        self_item_3 = findViewById(R.id.component_self_item_3);

        btn1 = self_item_1.findViewById(R.id.btn_self_item);
        btn2 = self_item_2.findViewById(R.id.btn_self_item);
        img2 = self_item_2.findViewById(R.id.img_ic_item);
        btn3 = self_item_3.findViewById(R.id.btn_self_item);
        img3 = self_item_3.findViewById(R.id.img_ic_item);

        img2.setBackgroundResource(R.drawable.ic_beta);
        btn2.setText("版本更新");
        img3.setBackgroundResource(R.drawable.ic_help);
        btn3.setText("关于");

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog1 = new AlertDialog.Builder(SelfActivity.this)
                        .setTitle(btn1.getText())//标题
                        .setMessage("\n\n\n\n\n                             收藏为空                   \n\n\n\n\n\n\n")//内容
                        .setIcon(R.drawable.ic_star)//图标
                        .create();
                alertDialog1.show();
            }
        });

        btn2.setOnClickListener(v -> {
            AlertDialog alertDialog1 = new AlertDialog.Builder(SelfActivity.this)
                    .setTitle(btn2.getText())//标题
                    .setMessage("当前版本： 18.0.0.2\n\n\n为当前最新版本，无需更新")//内容
                    .setIcon(R.drawable.ic_beta)//图标
                    .create();
            alertDialog1.show();
        });

        btn3.setOnClickListener(v -> {
            AlertDialog alertDialog1 = new AlertDialog.Builder(SelfActivity.this)
                    .setTitle(btn3.getText())//标题
                    .setMessage("开发团队为二人，若有任何疑问请寄邮件至:\n\n\naaaaaaaa@gmail.com\n\n\n感谢配合！")//内容
                    .setIcon(R.drawable.ic_help)//图标
                    .create();
            alertDialog1.show();
        });
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelfActivity.this, SearchActivity.class));
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });

        mHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfActivity.this, VideoActivity.class);
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
                Intent intent = new Intent(SelfActivity.this, StoreActivity.class);
                startActivity(intent);
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });

        mSelfBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfActivity.this, SelfActivity.class);
                startActivity(intent);
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });


        Glide.with(this).load(R.drawable.ic_avatar)
                .bitmapTransform(new BlurTransformation(this, 25), new CenterCrop(this))
                .into(blurImageView);

        Glide.with(this).load(R.drawable.ic_avatar)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(avatarImageView);

        userNameTextView.setText("用户A");
    }

}
