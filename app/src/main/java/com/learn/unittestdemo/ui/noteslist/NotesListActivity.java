package com.learn.unittestdemo.ui.noteslist;

import android.os.Bundle;

import com.learn.unittestdemo.R;
import com.learn.unittestdemo.repository.NoteRepository;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class NotesListActivity extends DaggerAppCompatActivity {

    private static final String TAG = NotesListActivity.class.getSimpleName();

    @Inject
    NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
