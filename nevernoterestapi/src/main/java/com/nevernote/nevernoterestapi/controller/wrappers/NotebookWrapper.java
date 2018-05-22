package com.nevernote.nevernoterestapi.controller.wrappers;

import com.nevernote.nevernoterestapi.model.Notebook;

import java.util.Set;
import java.util.stream.Collectors;

public class NotebookWrapper {
    private Long id;

    private Set<NoteWrapper> notes;

    public static NotebookWrapper buildFromNotebook(Notebook notebook) {
        NotebookWrapper notebookWrapper = new NotebookWrapper();
        notebookWrapper.id = notebook.getId();
        notebookWrapper.notes = notebook.getNotes()
                                      .stream()
                                      .map(NoteWrapper::buildFrom)
                                      .collect(Collectors.toSet());
        return notebookWrapper;
    }

    public Set<NoteWrapper> getNotes() {
        return notes;
    }

    public void setNotes(Set<NoteWrapper> notes) {
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
