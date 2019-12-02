package com.learn.unittestdemo.ui.note;

import com.learn.unittestdemo.models.Note;
import com.learn.unittestdemo.repository.NoteRepository;
import com.learn.unittestdemo.ui.Resource;
import com.learn.unittestdemo.util.InstantExecutorExtension;
import com.learn.unittestdemo.util.LiveDataTestUtil;
import com.learn.unittestdemo.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Flowable;
import io.reactivex.internal.operators.single.SingleToFlowable;

import static com.learn.unittestdemo.repository.NoteRepository.INSERT_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(InstantExecutorExtension.class)
public class NoteViewModelTest {

    private NoteViewModel noteViewModel;

    @Mock
    private NoteRepository noteRepository;


    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        noteViewModel = new NoteViewModel(noteRepository);
    }

    /*
    can't observe a note that hasn't been set
     */
    @Test
    void observeEmptyNoteWhenNoteSet() throws Exception {
        //Arrange
        LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<>();

        //Act
        Note note = liveDataTestUtil.getValue(noteViewModel.observeNote());

        //Assert
        assertNull(note);
    }

    /*
    Observe a note has been set and onChange will trigger in activity
     */
    @Test
    public void observeNote_whenSet() throws Exception {
    //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<>();

        //Act
        noteViewModel.setNote(note);
        Note observerdNote = liveDataTestUtil.getValue(noteViewModel.observeNote());

        //Assert
        assertEquals(note,observerdNote);

    }

    /*
    Insert a new note and observe row returned
     */
    @Test
    public void insertNote_returnRow() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        final  int insertedRow =1;
        Flowable<Resource<Integer>> returnedData = SingleToFlowable.just(Resource.success(insertedRow,INSERT_SUCCESS));
        when(noteRepository.insertNote(any(Note.class))).thenReturn(returnedData);

        //Act
        noteViewModel.setNote(note);
        Resource<Integer> returnedValue = liveDataTestUtil.getValue(noteViewModel.insertNote());

        //Assert
        assertEquals(Resource.success(insertedRow,INSERT_SUCCESS),returnedValue);
    }

    /*
    insert: don't return a new row without observer
     */
    @Test
    public void dontReturnInsertRowWithoutObserver() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);

        //Act
        noteViewModel.setNote(note);

        //Assert
        verify(noteRepository,never()).insertNote(note);
    }


    /*

   set note, null title, throw exception
     */
    @Test
    public void setNot_nullTitle_throwException() throws Exception {

        //Arrange
        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);

        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                noteViewModel.setNote(note);
            }
        });

    }
}
