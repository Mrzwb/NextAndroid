package com.zwb.next.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zwb.next.R;

import java.io.File;

public class FilesActivity extends Activity {

    private ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);

        Button button = (Button) findViewById(R.id.v_btn);
        listView = (ListView) findViewById(R.id.v_list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final File[] files  = getDiskCacheDir(FilesActivity.this);

                BaseAdapter baseAdapter = new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return files.length;
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

                        LinearLayout ll = new LinearLayout(FilesActivity.this);
                        ll.setOrientation(LinearLayout.VERTICAL);
                        ll.setPadding(5,5,5,5);

                        TextView tv = new TextView(FilesActivity.this);
                        tv.setText(files[position].getAbsolutePath());
                        tv.setTextColor(Color.BLACK);
                        tv.setGravity(Gravity.LEFT);
                        tv.setTextSize(16);

                        ll.addView(tv);
                        return ll;
                    }
                };

                listView.setAdapter(baseAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FilesActivity.this, AlertDialog.THEME_TRADITIONAL);
                        builder.setTitle("提示")
                                .setMessage("列表项目")
                                .show();
                    }
                });
            }
        });

    }


    public File[] getDiskCacheDir(Context context) {
        File[]  cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalFilesDirs(null);
        } else {
            cachePath = context.getExternalCacheDirs();
        }
        return cachePath;
    }

}
