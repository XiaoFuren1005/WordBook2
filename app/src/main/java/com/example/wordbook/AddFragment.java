package com.example.wordbook;

import android.view.View;
import android.widget.EditText;

public class AddFragment extends Fragment {
    private EditText edit_word;
    private EditText edit_translate;
    private EditText edit_example;

    private String word;
    private String translate;
    private String example;
    public AddFragment(){

    }
    public AddFragment(String word, String translate, String example){
        this.word = word;
        this.translate = translate;
        this.example = example;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        Configuration cf = this.getResources().getConfiguration();
        int ori = cf.orientation;

        if (ori == cf.ORIENTATION_LANDSCAPE){
            View view = inflater.inflate(R.layout.add_land_layout, container, false);
            edit_word = view.findViewById(R.id.land_edit_word);
            edit_translate = view.findViewById(R.id.land_edit_translate);
            edit_example = view.findViewById(R.id.land_edit_example);

            edit_word.setText(word);
            edit_translate.setText(translate);
            edit_example.setText(example);

            return view;
        }else{
            return null;
        }
    }

    public EditText getEdit_word(){
        return edit_word;
    }

    public EditText getEdit_translate(){
        return edit_translate;
    }

    public EditText getEdit_example(){
        return edit_example;
    }

    public String getWord(){
        word = edit_translate.getText().toString();
        return translate;
    }

    public String getExample(){
        example = edit_example.getText().toString();
        return example;
    }
}
