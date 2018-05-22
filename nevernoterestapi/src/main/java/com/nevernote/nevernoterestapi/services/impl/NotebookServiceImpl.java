package com.nevernote.nevernoterestapi.services.impl;

import com.nevernote.nevernoterestapi.model.Note;
import com.nevernote.nevernoterestapi.model.Notebook;
import com.nevernote.nevernoterestapi.repository.NotebookRepository;
import com.nevernote.nevernoterestapi.services.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class NotebookServiceImpl implements NotebookService {

    @Autowired
    private NotebookRepository notebookRepository;

    @Override
    public Notebook create() {
        return notebookRepository.save(new Notebook());
    }

    @Override
    public Notebook find(Long id) {
        return notebookRepository.findOne(id);
    }

    @Override
    public void delete(Long id) {
        notebookRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        notebookRepository.deleteAll();
    }

    @Override
    public void delete(Notebook notebook) {
        notebookRepository.delete(notebook);
    }

    @Override
    public Notebook findWithTags(Long id, final String[] tags) {
        Notebook notebook = notebookRepository.findOne(id);

        if (notebook != null) {

            Set<Note> notes = notebook.getNotes().stream().filter(filterNoteWithTags(tags)).collect(Collectors.toSet());
            notebook.setNotes(notes);
        }
        return notebook;
    }

    private Predicate<Note> filterNoteWithTags(String[] tags) {
        return note -> {
            for (String tag : tags) {
                //note contains tag from the list
                if (note.getTags().stream().anyMatch(tag1 -> tag1.getTitle().equals(tag))) {
                    return true;
                }
            }
            //filter note by default if no tags present
            return false;
        };
    }
}
