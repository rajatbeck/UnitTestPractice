package com.learn.unittestdemo;

import android.database.sqlite.SQLiteConstraintException;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.learn.unittestdemo.models.Note;
import com.learn.unittestdemo.util.LiveDataTestUtil;
import com.learn.unittestdemo.util.TestUtil;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class NoteDaoTest extends NoteDatabaseTest {

    public static final String TEST_TITLE = "This is a test title";
    public static final String TEST_CONTENT = "This is some test content";
    public static final String TEST_TIMESTAMP = "10-2018";

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    /*
    Insert, Read , Delete
     */

    @Test
    public void insertReadDelete() throws Exception{

        Note note = new Note(TestUtil.TEST_NOTE_1);

        //insert
        getNoteDao().insertNote(note).blockingGet();//wait unit inserted

        //read
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNote = liveDataTestUtil.getValue(getNoteDao().getNotes());

        assertNotNull(insertedNote);

        assertEquals(note.getContent(),insertedNote.get(0).getContent());
        assertEquals(note.getTimestamp(),insertedNote.get(0).getTimestamp());
        assertEquals(note.getTitle(),insertedNote.get(0).getTitle());

        note.setId(insertedNote.get(0).getId());
        assertEquals(note,insertedNote.get(0));

        //delete
        getNoteDao().deleteNote(note).blockingGet();
        insertedNote = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(0,insertedNote.size());
    }

    /*
    Insert, Read, Update, Read, Delete
     */

    @Test
    public void insertReadUpdateReadDelete() throws Exception {

        Note note = new Note(TestUtil.TEST_NOTE_1);

        //insert
        getNoteDao().insertNote(note).blockingGet();//wait unit inserted
        //read
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNote = liveDataTestUtil.getValue(getNoteDao().getNotes());

        assertNotNull(insertedNote);

        assertEquals(note.getContent(), insertedNote.get(0).getContent());
        assertEquals(note.getTimestamp(), insertedNote.get(0).getTimestamp());
        assertEquals(note.getTitle(), insertedNote.get(0).getTitle());

        note.setId(insertedNote.get(0).getId());
        assertEquals(note, insertedNote.get(0));

        //update
        note.setTitle(TEST_TITLE);
        note.setContent(TEST_CONTENT);
        note.setTimestamp(TEST_TIMESTAMP);
        getNoteDao().updateNote(note).blockingGet();

        //read
        insertedNote = liveDataTestUtil.getValue(getNoteDao().getNotes());

        assertEquals(TEST_TITLE, insertedNote.get(0).getTitle());
        assertEquals(TEST_CONTENT, insertedNote.get(0).getContent());
        assertEquals(TEST_TIMESTAMP, insertedNote.get(0).getTimestamp());

        //delete
        getNoteDao().deleteNote(note).blockingGet();
        insertedNote = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertEquals(0, insertedNote.size());
    }

    /*
    Insert note with null title. throw exception
     */
    @Test(expected = SQLiteConstraintException.class)
    public void insert_nulltitle_throwSQLiteConstraintException() throws Exception{

        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);

        //insert
        getNoteDao().insertNote(note).blockingGet();

    }

    /*
    Insert,Update note with null title, throw exception
     */

    @Test(expected = SQLiteConstraintException.class)
    public void updateNote_nullTitle_throwSQLiteConstraintException() throws Exception{

        Note note = new Note(TestUtil.TEST_NOTE_1);

        //insert
        getNoteDao().insertNote(note).blockingGet();

        //read
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        List<Note> insertedNotes = liveDataTestUtil.getValue(getNoteDao().getNotes());
        assertNotNull(insertedNotes);

        //update
        note = new Note(insertedNotes.get(0));
        note.setTitle(null);

        getNoteDao().updateNote(note).blockingGet();

    }
}
