package com.learn.unittestdemo.di;

import com.learn.unittestdemo.NotesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract NotesListActivity contributesNotesListActivity();
}
