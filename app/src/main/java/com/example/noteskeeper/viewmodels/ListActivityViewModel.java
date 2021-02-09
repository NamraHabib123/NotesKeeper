package com.example.noteskeeper.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.noteskeeper.database.AppRepository;
import com.example.noteskeeper.database.NoteEntity;
import com.example.noteskeeper.utils.SampleDataProvider;

import java.util.List;

public class ListActivityViewModel extends AndroidViewModel {
    public LiveData< List<NoteEntity>> mNoteList;
    private AppRepository mRepository;

    public ListActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mNoteList = mRepository.mNoteList;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllData() {
        mRepository.deleteAllData();
    }
}
