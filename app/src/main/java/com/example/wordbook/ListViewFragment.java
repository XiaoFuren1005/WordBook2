package com.example.wordbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListViewFragment extends Fragment {
    private List<Word> words = new ArrayList<>();
    private MyDataBaseHelper dbHelper;
    private ListView listView;
    private WordAdapter adapter;
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.textview_fragment, container, false);

        mActivity = getActivity();

        dbHelper = new MyDataBaseHelper(mActivity, "WordBook.db",
                null, 2);

        listView = view.findViewById(R.id.list_view);

        initFruits();
        adapter = new WordAdapter(mActivity,
                R.layout.word_item, words);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                showWord(adapterView, view, i, l);
            }
        });
        return view;
    }
    //展示单词的详细信息
    public void showWord(AdapterView<?> adapterView, View view, int i, long l){
        final Word word = words.get(i);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View layout = inflater.inflate(R.layout.show_layout, null);
        alertDialog.setView(layout);

        //通过数据库精准查询，获取点击单词的详细信息
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Book", null, "word = '" + word.getName() + "'",
                null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                String translate = cursor.getString(cursor.getColumnIndex("translate"));
                String example = cursor.getString(cursor.getColumnIndex("example"));
                word.setTranslate(translate);
                word.setExample(example);
            }while(cursor.moveToNext());
        }
        cursor.close();
        TextView view_word = layout.findViewById(R.id.view_name);
        TextView view_translate = layout.findViewById(R.id.view_translate);
        TextView view_example = layout.findViewById(R.id.view_example);

        view_word.setText(word.getName());
        view_translate.setText(word.getTranslate());
        view_example.setText(word.getExample());
        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialogInterface, int i){
                //null
            }
        });
    }
    alertDialog.setNegativeButton("Change", new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface dialogInterface, int i){
            showChange(word);
        }
    });
    alertDialog.setNeutralButton("Delete", new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface dialogInterface, int i){
            Toast.makeText(mActivity, "Next Time to see you ", Toast.LENGTH_SHORT).show();
        }
    });
    AlertDialog dialog = alertDialog.create();

        dialog.show();

    Window dialogWindow = dialog.getWindow();
    WindowManager m = mActivity.getWindowManager();
    Display d = m.getDefaultDisplay();
    WindowManager.LayoutParams p = dialogWindow.getAttributes();
    p.height = (int) (d.getHeight() * 0.4);
    p.width = (int) (d.getWidth() * 1.0);

        dialogWindow.setAttributes(p);
}
    //change
    private void showChange(Word word){
        final String name = word.getName();
        AlertDialog.Builder change_dialog = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View layout = inflater.inflate(R.layout.add_layout, null);
        change_dialog.setView(layout);

        final EditText edit_word = layout.findViewById(R.id.add_name);
        final EditText edit_translate = layout.findViewById(R.id.add_translate);
        final EditText edit_example = layout.findViewById(R.id.add_example);

        edit_word.setText(word.getName());
        edit_translate.setText(word.getTranslate());
        edit_example.setText(word.getExample());

        change_dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //数据库操作，更改
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                String new_word = edit_word.getText().toString();
                String new_translate = edit_translate.getText().toString();
                String new_example = edit_example.getText().toString();

                values.put("word", new_word);
                values.put("translate", new_translate);
                values.put("example", new_example);

                db.update("Book", values, "word=?", new String[] {name});
                values.clear();
            }
        });

        change_dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(mActivity, "You Cancel this action",
                        Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog change = change_dialog.create();

        change.show();

        Window dialogWindow = change.getWindow();
        WindowManager m = mActivity.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();

        p.height = (int) (d.getHeight() * 0.5);
        p.width = (int) (d.getWidth() * 1.0);

        dialogWindow.setAttributes(p);
    }

    private void initFruits(){
        words.clear();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Book", null, null,
                null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex("word"));
                Word hello = new Word(name);
                words.add(hello);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }
}