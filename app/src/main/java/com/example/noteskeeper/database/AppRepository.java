package com.example.noteskeeper.database;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.noteskeeper.utils.SampleDataProvider;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static   AppRepository ourInstance;
    private AppDatabase mDatabase;
    public LiveData <List<NoteEntity>> mNoteList;

    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context){
        return ourInstance = new AppRepository(context);
    }
    private AppRepository(Context context){
        mDatabase = AppDatabase.getInstance(context);
        mNoteList =  getAllNotes();
    }


    public void addSampleData() {

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
             mDatabase.notesDao().insertAll(SampleDataProvider.getSampleData());
            }

        });

    }
    private  LiveData <List<NoteEntity>> getAllNotes(){
        return mDatabase.notesDao().getAllNotes();
    }

    public void deleteAllData() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
               int notes = mDatabase.notesDao().deleteAllNotes();

            }
        });
    }

    public NoteEntity lodeNote(int noteId) {
        return mDatabase.notesDao().getNoteById(noteId);
    }

    public void updateNote(NoteEntity noteEntity) {
mExecutor.execute(new Runnable() {
    @Override
    public void run() {
        mDatabase.notesDao().insertNote(noteEntity);
    }
});

    }

    public void deleteNote(NoteEntity entity) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.notesDao().deleteNote(entity);
            }
        });
    }
}
