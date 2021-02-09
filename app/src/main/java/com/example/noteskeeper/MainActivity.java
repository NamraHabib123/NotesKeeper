package com.example.noteskeeper;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.noteskeeper.database.NoteEntity;
import com.example.noteskeeper.model.NotesAdapter;
import com.example.noteskeeper.viewmodels.ListActivityViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private List<NoteEntity> mNotesList = new ArrayList<>();
    private List<NoteEntity> mDataList = new ArrayList<>();
    private ListActivityViewModel mViewModel;
    @BindView(R.id.search_bar)
    EditText SearchBar;
    NotesAdapter mNotesAdapter;
    @BindView(R.id.notes_recyclerview)
    RecyclerView mRecyclerView;
    @OnClick(R.id.fab_add_note)
    void onFabClicked(){
        Intent intent = new Intent(MainActivity.this, EditorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViewModel();
        ButterKnife.bind(this);
        initRecyclerView();
        SearchData();
    }

    private void SearchData() {
        SearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    mNotesAdapter = new NotesAdapter(MainActivity.this,mNotesList);
                    mRecyclerView.setAdapter(mNotesAdapter);
                    mNotesAdapter.notifyDataSetChanged();
                } else {
                    ArrayList<NoteEntity> clone = new ArrayList<>();
                    for (NoteEntity element : mNotesList) {
                        if (element.getText().toLowerCase().contains(s.toString().toLowerCase())) {
                            clone.add(element);
                        }
                    }
                    mNotesAdapter = new NotesAdapter(MainActivity.this,clone);
                    mRecyclerView.setAdapter(mNotesAdapter);
                    mNotesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initViewModel() {
       Observer<List<NoteEntity>> notesObserver = new Observer<List<NoteEntity>>() {
           @Override
           public void onChanged(List<NoteEntity> noteEntities) {
                      mNotesList.clear();
                      mNotesList.addAll(noteEntities);
                      if(mNotesAdapter == null){
                          mNotesAdapter = new NotesAdapter(MainActivity.this,mNotesList);
                          mRecyclerView.setAdapter(mNotesAdapter);

                      }
                      else{
                          mNotesAdapter.notifyDataSetChanged();
                      }
           }
       };
mViewModel=ViewModelProviders.of(this)
        .get(ListActivityViewModel.class);
mViewModel.mNoteList.observe(MainActivity.this,notesObserver);
    }


    private void initRecyclerView() {
        mRecyclerView.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.add_sample_data:{
                addSampleData();
                return true;
            }
            case R.id.delete_all_data:{
                deleteAllData();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllData() {
        mViewModel.deleteAllData();
    }

    private void addSampleData() {
      mViewModel.addSampleData();

    }


}