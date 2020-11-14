package com.example.wordbook;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    private ContentResolver resolver;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        resolver = this.getContentResolver();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btnAdd = (Button)findViewById(R.id.btn_adddd);
        Button btnDelete = (Button)findViewById(R.id.btn_deleteeee);
        Button btnSearch = (Button)findViewById(R.id.btn_searchhhh);
        Button btnUpdate = (Button)findViewById(R.id.btn_updateeee);
        Button btnDeleteAll = (Button)findViewById(R.id.btn_deleteAll);

        final String AUTHORITY = "com.example.wordbook.provider";
        //uri1 = Uri.parse("content://com.example.words.provider/words");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Uri uri = Uri.parse("content://com.example.words.provider/words");
                System.out.println(uri.toString());*/
                ContentValues values = new ContentValues();
                values.put("_id","1");
                values.put("word", "lqf");
                values.put("sample", "huzhikui");
                values.put("meaning", "");
                System.out.println(uri);
                Uri newUri = getContentResolver().insert(uri, values);
                if (newUri != null) {
                    Toast.makeText(MainActivity2.this, "add successfully!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                //Log.v("TAG",uri.toString());
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String word = cursor.getString(cursor.getColumnIndex("word"));
                        String sample = cursor.getString(cursor.getColumnIndex("sample"));
                        String meaning = cursor.getString(cursor.getColumnIndex("meaning"));
                        Log.d("TAG", "onClick: word: " + word + " sample: " + sample + " meaning: " + meaning);
                    }
                    cursor.close();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("word", "laa");
                values.put("sample", "ddd 1");
                values.put("meaning", "");
                String sel1 =Word.class.getName() + " = ?";
                String[] sel1Args = {"3"};
                if (getContentResolver().update(uri, values, sel1, sel1Args) > 0) {
                    Toast.makeText(MainActivity2.this, "update suc" +
                            "cessfully!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id="3";//简单起见，这里指定ID，用户可在程序中设置id的实际值
                String sel = Word.class.getName() + " = ?";

                String[] selArgs = {"3"};
                if(getContentResolver().delete(uri, sel, selArgs) >0){
                    Toast.makeText(MainActivity2.this,"delete successfully!",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getContentResolver().delete(uri,null,null) >0){
                    Toast.makeText(MainActivity2.this,"deleteAll successfully!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
