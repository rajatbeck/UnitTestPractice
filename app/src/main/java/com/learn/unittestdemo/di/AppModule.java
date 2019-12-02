package com.learn.unittestdemo.di;

import android.app.Application;

import androidx.room.Room;

import com.learn.unittestdemo.persistance.NoteDao;
import com.learn.unittestdemo.persistance.NoteDatabase;
import com.learn.unittestdemo.repository.NoteRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.learn.unittestdemo.persistance.NoteDatabase.DATABASE_NAME;

@Module
public abstract class AppModule {

    @Singleton
    @Provides
    static NoteDatabase provideNoteDatabase(Application application){
        return Room.databaseBuilder(application,
                NoteDatabase.class,
                DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    static NoteDao providesNotesDao(NoteDatabase noteDatabase){
        return noteDatabase.getNoteDao();
    }

    @Singleton
    @Provides
    static NoteRepository provideNoteRepository(NoteDao noteDao){
        return new NoteRepository(noteDao);
    }
}
