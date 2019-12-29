package com.aman.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_LIST_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ToDoListDetailFragment fragment =
                (ToDoListDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);

        int listId = (int)getIntent().getExtras().get(EXTRA_LIST_ID);
        fragment.setId(listId);
    }


}
