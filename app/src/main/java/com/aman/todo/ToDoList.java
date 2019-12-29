package com.aman.todo;

import java.util.ArrayList;
import java.util.List;

public class ToDoList {

    private int id;
    private String name;
    //private List<Notes> notesList;

    /*public static class Notes{
        String noteDescription;
        String status;

        Notes(){
            noteDescription = "default";
            status = "doing";
        }

        Notes(String noteDescription, String status){
            this.noteDescription  = noteDescription;
            this.status = status;
        }

        public String getNoteDescription() {
            return noteDescription;
        }

        public void setNoteDescription(String noteDescription) {
            this.noteDescription = noteDescription;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }*/

    /*public static final ToDoList[] lists = {
            new ToDoList(1,"List1", new ArrayList<Notes>()),
            new ToDoList(2,"List2", new ArrayList<Notes>()),
            new ToDoList(3,"List3", new ArrayList<Notes>())
    };*/

    public static final ToDoList[] lists = {
            new ToDoList(1,"List1"),
            new ToDoList(2,"List2"),
            new ToDoList(3,"List3")
    };

    /*public ToDoList(int id, String name, ArrayList<Notes> list){
        this.id = id;
        this.name = name;
        this.notesList = list;
    }*/

    public ToDoList(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    /*public void addNote(String noteDescription){
        Notes note = new Notes();
        note.setNoteDescription(noteDescription);
        note.status = "doing";

        notesList.add(note);
    }

    public List<Notes> getNotesList(){

        return this.notesList;

    }*/
}
