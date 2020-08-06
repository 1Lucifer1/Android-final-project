package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * @author Owen
 */
public class SearchActivity extends AppCompatActivity{


    private String TAG = "SearchActivity: ";
    public String KEY_TYPE_FROM = "type_from";
    private ArrayList<String> fit_list = new ArrayList<String>();
    private ArrayList<String> cor_list = new ArrayList<String>();

    public ImageButton mHomeBtn;
    public ImageButton mSearchBtn;
    public ImageButton mAddBtn;
    public ImageButton mStoreBtn;
    public ImageButton mSelfBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_search);

        mHomeBtn = findViewById(R.id.btn_home);
        mSearchBtn = findViewById(R.id.btn_search);
        mAddBtn = findViewById(R.id.btn_add);
        mStoreBtn = findViewById(R.id.btn_store);
        mSelfBtn = findViewById(R.id.btn_me);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, SearchActivity.class));
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });

        mHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, VideoActivity.class);
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
                Intent intent = new Intent(SearchActivity.this, StoreActivity.class);
                startActivity(intent);
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });

        mSelfBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, SelfActivity.class);
                startActivity(intent);
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });

        // ListView
        final ListView listView = (ListView) findViewById(R.id.list_view_search);
        mListAdapter adapter = new mListAdapter(this);
        listView.setAdapter(adapter);
        listView.setDivider(null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, VideoActivity.class);
                for(int i = 0 ; i < Videos.videos.size() ; ++i){
                    VideoData videoData = Videos.videos.get(i);
                    if(videoData.getNickname().equals(fit_list.get(position))){
                        Videos.curVideoId = i;
                        break;
                    }
                }
//                intent.putExtra("nickname", fit_list.get(position));
////                intent.putExtra("song", so_list.get(position));
//                intent.putExtra("description", cor_list.get(position));
                intent.putExtra(KEY_TYPE_FROM, 1);
                startActivity(intent);
            }
        });
        adapter.notifyDataSetChanged();

        // SearchView
        SearchView searchView = findViewById(R.id.search_view);
        //设置该SearchView默认是否自动缩小为图标
        searchView.setIconifiedByDefault(false);
        //设置该SearchView显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("搜索用户名、影片名等");
        //为该SearchView组件设置事件监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //单机搜索按钮时激发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {

//                for (String s: nickname_list){
//                    if (fitString(query, s)){
//                        fit_list.add(s);
//                    }
//                }
//
//                for (String s: description_list){
//                    if (fitString(query, s)){
//                        String desToNick = nickname_list[Arrays.binarySearch(description_list, s)];
//                        if (!fit_list.contains(desToNick)){
//                            fit_list.add(desToNick);
//                        }
//                    }
//                }
//                assert Videos.videos != null;
                for(VideoData videoData:Videos.videos){
                    if(fitString(query, videoData.getNickname())){
                        fit_list.add(videoData.getNickname());
                        Log.d(TAG + "nick", videoData.getNickname());
                    }else if(fitString(query, videoData.getDescription())){
                        String desToNick = videoData.getNickname();
                        if (!fit_list.contains(desToNick)){
                            fit_list.add(desToNick);
                            Log.d(TAG + "des", desToNick);
                        }
                    }
                }
                Log.d(TAG + "fit_list", String.valueOf(fit_list.size()));
                if (fit_list.isEmpty()){
//                    Toast.makeText(SearchActivity.this,"有匹配的内容",Toast.LENGTH_SHORT).show();
                    Toast.makeText(SearchActivity.this, "无匹配的内容", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return false;
            }

            //用户输入字符时激发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                // 清空上次搜索的记录
                fit_list.clear();
                cor_list.clear();
                //so_list.clear();
                return true;
            }
        });
    }

    private boolean fitString(String src, String dest){
        // src = query
        if (dest.equals(src.substring(1)) || dest.equals(src.substring(0, src.length()-1))){
            return true;
        }else if (dest.equals(src)){
            return true;
        }else if (src.equals(dest.substring(1)) || src.equals(dest.substring(0, dest.length()-1))){
            return true;
        }else if (dest.indexOf(src)!=-1){
            return true;
        }else{
            return false;
        }
    }

    public final class ViewHolder{
        public TextView nickname;
        public TextView description;
    }

    public class mListAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public mListAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return fit_list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.component_search_list, null);
                holder.nickname = (TextView) convertView.findViewById(R.id.list_nickname);
                holder.description = (TextView) convertView.findViewById(R.id.list_description);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder)convertView.getTag();
            }

            for (String s: fit_list){
                for(VideoData videoData :Videos.videos){
                    if(videoData.getNickname().equals(s)){
                        cor_list.add(videoData.getDescription());
                    }
                }
            }
            holder.nickname.setText("用户名："+(String)fit_list.get(position));
            holder.description.setText("视频名： "+(String)cor_list.get(position));

            return convertView;
        }
    }
}
