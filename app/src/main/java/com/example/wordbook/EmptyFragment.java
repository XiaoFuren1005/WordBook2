package com.example.wordbook;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EmptyFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        Configuration cf = this.getResources().getConfiguration();
        int ori = cf.orientation;

        if (ori == cf.ORIENTATION_LANDSCAPE){
            View view = inflater.inflate(R.layout.empty_layout, container, false);
            return view;
        }else{
            return null;
        }
    }
}
