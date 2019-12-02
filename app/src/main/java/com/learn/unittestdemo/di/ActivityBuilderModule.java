package com.learn.unittestdemo.di;

import com.learn.unittestdemo.ui.noteslist.NotesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract NotesListActivity contributesNotesListActivity();
}
