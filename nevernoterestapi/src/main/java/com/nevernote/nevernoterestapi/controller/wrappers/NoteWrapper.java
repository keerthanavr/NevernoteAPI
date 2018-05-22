package com.nevernote.nevernoterestapi.controller.wrappers;

import com.nevernote.nevernoterestapi.model.Note;
import com.nevernote.nevernoterestapi.model.Tag;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class NoteWrapper {
    private Long id;
    private String title;
    private String body;
    private Set<String> tags = new HashSet<>();
    private Long notebook;
    private Date createdAt;
    private Date lastModified;


    public Long getNotebook() {
        return notebook;
    }

    public void setNotebook(Long notebook) {
        this.notebook = notebook;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Note toNote() {
        Note note = new Note();
        note.setTitle(title);
        note.setBody(body);
        note.setId(id);

        note.addTags(tags);
        return note;
    }


    public static NoteWrapper buildFrom(Note note) {
        NoteWrapper noteWrapper = new NoteWrapper();
        noteWrapper.setId(note.getId());
        noteWrapper.setBody(note.getBody());
        noteWrapper.setTitle(note.getTitle());
        noteWrapper.setNotebook(note.getNotebook().getId());
        noteWrapper.setTags(note.getTags().stream().map(Tag::getTitle).collect(Collectors.toSet()));
        noteWrapper.setCreatedAt(note.getCreatedAt());
        noteWrapper.setLastModified(note.getLastModified());
        return noteWrapper;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
