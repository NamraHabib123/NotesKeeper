package com.example.noteskeeper.viewmodels;


import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.noteskeeper.database.AppRepository;
import com.example.noteskeeper.database.NoteEntity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {
    public MutableLiveData<NoteEntity> mLiveNote = new MutableLiveData<>();
    private Executor mExecutor = Executors.newSingleThreadExecutor();
    private AppRepository mRepository;
    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application.getApplicationContext());
    }

    public void lodeNote(int noteId) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity noteEntity = mRepository.lodeNote(noteId);
                mLiveNote.postValue(noteEntity);
            }
        });
    }

    public void saveAndExit(String noteText) {
        NoteEntity noteEntity = mLiveNote.getValue();
        if (noteEntity == null) {
            if(TextUtils.isEmpty(noteText.trim())){
                return;
            }else {
                noteEntity = new NoteEntity(new Date(),noteText.trim());
            }

        }else {
            noteEntity.setText(noteText.trim());
            noteEntity.setDate(new Date());
        }
        mRepository.updateNote(noteEntity);
    }

    public void deleteNote() {
        mRepository.deleteNote(mLiveNote.getValue());
    }
}
