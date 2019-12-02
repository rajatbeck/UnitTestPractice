package com.learn.unittestdemo.ui.note;

import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.learn.unittestdemo.models.Note;
import com.learn.unittestdemo.repository.NoteRepository;
import com.learn.unittestdemo.ui.Resource;

import javax.inject.Inject;

import static com.learn.unittestdemo.repository.NoteRepository.NOTE_TITLE_NULL;

public class NoteViewModel extends ViewModel {

    private static final String TAG = NoteViewModel.class.getSimpleName();

    private final NoteRepository noteRepository;

    private MutableLiveData<Note> note = new MutableLiveData<>();

    @Inject
    public NoteViewModel(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public LiveData<Note> observeNote(){
        return note;
    }

    public LiveData<Resource<Integer>> insertNote() throws Exception {
        return LiveDataReactiveStreams.fromPublisher(
                noteRepository.insertNote(note.getValue())
        );
    }



    public void setNote(Note note)throws Exception{
        if(note.getTitle()==null && note.getTitle().equals("")){
            throw new Exception(NOTE_TITLE_NULL);
        }
        this.note.setValue(note);
    }
}
