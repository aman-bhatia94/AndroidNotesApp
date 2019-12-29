package com.aman.todo;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ToDoListDetailFragment extends Fragment {

    private int id; //the list id
    private int noteToModify;
    private boolean isNote = false; //to check if note is selected for operations like delete
    ContentValues contentValues;
    private Cursor cursor;
    private SimpleCursorAdapter listAdapter;
    private DatabaseHelper databaseHelper;




    public ToDoListDetailFragment() {
        // Required empty public constructor
        //contentValues = new ContentValues();
    }

    public String getListName(){
        if(id == 0)
            return "list1";
        else if(id == 1)
            return "list2";
        else
            return "list3";
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);




        if (savedInstanceState != null){
            id = savedInstanceState.getInt("listId");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do_list_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            /*TextView title = (TextView) view.findViewById(R.id.textTitle);
            ToDoList list = ToDoList.lists[(int) id];
            title.setText(String.valueOf(list.getId()));
            TextView description = (TextView) view.findViewById(R.id.textDescription);
            description.setText(list.getName());*/

            final EditText noteDescription = (EditText)view.findViewById(R.id.note);
            final EditText noteStatus = (EditText)view.findViewById(R.id.status);
            final EditText noteChangeStatus = (EditText)view.findViewById(R.id.changeStatus);
            final TextView showStatus = (TextView)view.findViewById(R.id.statusDisplay);
            final EditText searchText = (EditText) view.findViewById(R.id.searchText);
            Button addNoteButton = (Button)view.findViewById(R.id.addNoteButton);
            Button removeNoteButton = (Button)view.findViewById(R.id.removeNoteButton);
            Button statusButton = (Button)view.findViewById(R.id.getNoteStatus);
            Button changeStatusButton = (Button)view.findViewById(R.id.changeStatusButton);
            Button sortButton = (Button)view.findViewById(R.id.sort);
            Button sortAlphaButton = (Button)view.findViewById(R.id.sortAlpha);
            Button searchWordButton = (Button)view.findViewById(R.id.searchWord);

            //final List<ToDoList.Notes> notes = ToDoList.lists[id].getNotesList(); //getting the notes list
            //final List<String> notesList = new ArrayList<>(); //note list with only description of notes
            final ListView listView = (ListView) view.findViewById(R.id.notesList);



            //Cursor adapter on the list
            databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
            try{

                //get cursor here
                cursor = databaseHelper.getCursor(getListName());
                listAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext()
                                                    ,android.R.layout.simple_list_item_1
                                                    ,cursor
                                                    ,new String[]{"noteDesc"}
                                                    ,new int[]{android.R.id.text1}
                                                    ,0);
                listView.setAdapter(listAdapter);
            }catch (SQLiteException e){
                e.printStackTrace();
            }

            //To add the notes descriptions to the  list and make it display using an adapter
            /*for(int i = 0; i < notes.size(); i++){
                notesList.add(notes.get(i).getNoteDescription());
            }

            final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext()
                                           ,android.R.layout.simple_list_item_1,notesList);

            listView.setAdapter(adapter);*/



            //Following lines of code will get the adapter item focused, so other ops operation can be performed on it
            AdapterView.OnItemClickListener adapterListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //noteToModify = position;
                    noteToModify = (int)id;
                    for(int i = 0; i < listView.getChildCount();i++){
                        if(i == position){
                            listView.getChildAt(i);
                            isNote = true;
                        }
                    }

                }
            };
            listView.setOnItemClickListener(adapterListener);


            //Following lines of code enable the add note operation

            View.OnClickListener addButtonListener = new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    contentValues = new ContentValues();

                    //notes.add(new ToDoList.Notes(noteDescription.getText().toString(),noteStatus.getText().toString()));
                    //notesList.add(noteDescription.getText().toString());

                    //db method adding
                    contentValues.put("noteDesc",noteDescription.getText().toString());
                    //contentValues.put("noteId",notesList.size()-1);
                    contentValues.put("noteStatus",noteStatus.getText().toString());

                    //call the add method in the
                    String listName = getListName();
                    databaseHelper.insertIntoList(listName,contentValues);
                    //adapter.notifyDataSetChanged();
                    cursor = databaseHelper.getCursor(getListName());
                    listAdapter.changeCursor(cursor);
                }
            };

            addNoteButton.setOnClickListener(addButtonListener);


            //Following lines of code enable the delete note operation

            View.OnClickListener removeButtonListener = new View.OnClickListener(){

                @Override
                public void onClick(View v) {

                    if(isNote){
                        //notes.remove(noteToModify);
                        //notesList.remove(noteToModify);
                        databaseHelper.deleteList(getListName(),noteToModify);
                        isNote = false;
                        //adapter.notifyDataSetChanged();
                        cursor = databaseHelper.getCursor(getListName());
                        listAdapter.changeCursor(cursor);
                    }

                }
            };
            removeNoteButton.setOnClickListener(removeButtonListener);


            //Get the status of the selected note

            View.OnClickListener getStatusButtonListener = new View.OnClickListener(){

                public void onClick(View v){
                    if(isNote){
                        //String status = notes.get(noteToModify).getStatus();
                        //System.out.println("check"+status);
                        //showStatus.setText(status);
                        //showStatus.setText(notes.get(noteToModify).getStatus());
                        String status = databaseHelper.getStatus(getListName(),noteToModify);
                        showStatus.setText(status);
                        isNote = false;
                        //cursor = databaseHelper.getCursor(getListName());
                        listAdapter.changeCursor(cursor);

                    }
                }

            };
            statusButton.setOnClickListener(getStatusButtonListener);

            //Change the status of the note
            View.OnClickListener changeStatusButtonListener = new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if(isNote){
                        //String status = notes.get(noteToModify).getStatus();
                        //System.out.println("check"+status);
                        //showStatus.setText(status);
                        //showStatus.setText(notes.get(noteToModify).getStatus());
                        String status = noteChangeStatus.getText().toString();
                        contentValues = new ContentValues();
                        contentValues.put("noteStatus",status);
                        String listName = getListName();
                        databaseHelper.updateList(listName,contentValues,noteToModify); //change this noteToModify
                        cursor = databaseHelper.getCursor(getListName());
                        listAdapter.changeCursor(cursor);
                        isNote = false;

                    }
                }
            };
            changeStatusButton.setOnClickListener(changeStatusButtonListener);


            //Retrieve using sort
            View.OnClickListener getSortButtonListener = new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    String listName = getListName();
                    cursor = databaseHelper.sort(listName);
                    listAdapter.changeCursor(cursor);
                }
            };
            sortButton.setOnClickListener(getSortButtonListener);

            //Retrieve using sortAlpha
            View.OnClickListener getSortAlphaButtonListener = new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    String listName = getListName();
                    cursor = databaseHelper.sortAlpha(listName);
                    listAdapter.changeCursor(cursor);
                }
            };
            sortAlphaButton.setOnClickListener(getSortAlphaButtonListener);


            //retrieve search
            View.OnClickListener getSearchedResultsListener = new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    String listName = getListName();
                    String textToSearch = searchText.getText().toString();
                    cursor = databaseHelper.searchWords(listName,textToSearch);
                    listAdapter.changeCursor(cursor);
                }

            };
            searchWordButton.setOnClickListener(getSearchedResultsListener);


        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("listId", id);

    }

    //method to set the list id, tells us which list to fetch
    public void setId(int id){
        this.id = id;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
    }

}
