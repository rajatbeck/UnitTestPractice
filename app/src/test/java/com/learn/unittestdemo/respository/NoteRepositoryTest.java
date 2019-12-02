package com.learn.unittestdemo.respository;

import com.learn.unittestdemo.models.Note;
import com.learn.unittestdemo.persistance.NoteDao;
import com.learn.unittestdemo.repository.NoteRepository;
import com.learn.unittestdemo.ui.Resource;
import com.learn.unittestdemo.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;

import static com.learn.unittestdemo.repository.NoteRepository.INSERT_FAILURE;
import static com.learn.unittestdemo.repository.NoteRepository.INSERT_SUCCESS;
import static com.learn.unittestdemo.repository.NoteRepository.NOTE_TITLE_NULL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NoteRepositoryTest {

    private static final Note NOTE1 = new Note(TestUtil.TEST_NOTE_1);

    // system under test
    private NoteRepository noteRepository;

    @Mock
    private NoteDao noteDao;

    @BeforeEach
    public void initEach(){
        MockitoAnnotations.initMocks(this); // OR Mockito.mock(NoteDao.class)
        noteRepository = new NoteRepository(noteDao);
    }

    /*
          insert note
          verify the correct method is called
          confirm observer is triggered
          confirm new rows inserted
     */
    @Test
    void insertNote_returnRow() throws Exception{

        //Arrange
        final Long insertedRow = 1L;
        final Single<Long> returnedData = Single.just(insertedRow);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst();

        //Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        System.out.println("Returned value: "+returnedValue.data);
        assertEquals(Resource.success(1,INSERT_SUCCESS),returnedValue);

        //or Test using RXJAVA
        /*noteRepository.insertNote(NOTE1)
                .test()
                .await()
                .assertValue(Resource.success(1,INSERT_SUCCESS));*/

    }

    /*
             Insert note
             Failure (return -1)
     */

    @Test
    void insertNote_returnFailure() throws Exception{

        //Arrange
        final Long failedInsert = -1L;
        final Single<Long> returnedData = Single.just(failedInsert);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        // Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst();

        //Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.error(null,INSERT_FAILURE),returnedValue);


    }

    /*
      insert note
      null title
      confirm throw exception

     */
    @Test
    void insertNote_nullTitle_throwException() throws Exception{

        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.insertNote(note);
            }
        });

        assertEquals(NOTE_TITLE_NULL,exception.getMessage());

    }


}
