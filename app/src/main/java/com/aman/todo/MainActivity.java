package com.aman.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements ToDoListFragment.Listener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onShowDetails(View view) {



    }

    @Override
    public void itemClicked(long id) {


        View fragmentContainer = findViewById(R.id.fragment_container);
        if(fragmentContainer != null){
            //add to layout

            ToDoListDetailFragment details = new ToDoListDetailFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            details.setId((int)id);
            ft.replace(R.id.fragment_container, details);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();

        }
        else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_LIST_ID, (int) id);
            startActivity(intent);
        }
    }
}
