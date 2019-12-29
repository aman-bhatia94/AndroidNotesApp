package com.aman.todo;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoListFragment extends ListFragment {


    static interface Listener{
        void itemClicked(long id);
    }

    private Listener listener;

    public ToDoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] listNames = new String[ToDoList.lists.length];

        for(int i = 0; i < listNames.length; i++){
            listNames[i] = ToDoList.lists[i].getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                inflater.getContext(),android.R.layout.simple_list_item_1,listNames
        );

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.listener = (Listener)context;
    }

    @Override
    public void onListItemClick(ListView listView, View itemView, int position, long id) {
        if (listener != null) {
            listener.itemClicked(id);
        }
    }

}
