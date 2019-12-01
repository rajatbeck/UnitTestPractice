package com.learn.unittestdemo;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.learn.unittestdemo.persistance.NoteDao;
import com.learn.unittestdemo.persistance.NoteDatabase;

import org.junit.After;
import org.junit.Before;

public abstract class NoteDatabaseTest {

    //System under test
    private NoteDatabase noteDatabase;

    public NoteDao getNoteDao(){
        return noteDatabase.getNoteDao();
    }

    // Temporary database builder remains as long as application is alive
    @Before
    public void init(){
        noteDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                NoteDatabase.class
        ).build();
    }

    @After
    public void finish(){
        noteDatabase.close();
    }
}
