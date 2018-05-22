package com.nevernote.nevernoterestapi.controller;

import com.nevernote.nevernoterestapi.controller.wrappers.NoteWrapper;
import com.nevernote.nevernoterestapi.services.NoteService;
import com.nevernote.nevernoterestapi.model.Note;
import com.nevernote.nevernoterestapi.model.Notebook;
import com.nevernote.nevernoterestapi.services.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/company")
public class NoteController {


    @Autowired
    NoteService noteDAO;

    @Autowired
    NotebookService notebookService;

    /*save a note*/
    @PostMapping("/notes")
    public ResponseEntity<NoteWrapper> createNote(@Valid @RequestBody NoteWrapper not) {
        Notebook notebook = notebookService.find(not.getNotebook());
        if (notebook != null) {
            Note note = not.toNote();
            note.setNotebook(notebook);
            note = noteDAO.save(note);
            return ResponseEntity.ok(NoteWrapper.buildFrom(note));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    /*get all notes*/
    @GetMapping("/notes")
    public List<NoteWrapper> getAllNotes() {
        return noteDAO.findAll().stream().map(NoteWrapper::buildFrom).collect(Collectors.toList());
    }


    /*get note by noteid*/
    @GetMapping("/notes/{id}")
    public ResponseEntity<NoteWrapper> getNoteById(@PathVariable(value = "id") Long noteid) {

        Note not = noteDAO.findOne(noteid);

        if (not == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(NoteWrapper.buildFrom(not));
    }

    /*Update a note by noteid */
    @PutMapping("/notes/{id}")
    public ResponseEntity<NoteWrapper> updateNote(@PathVariable(value = "id") Long noteid, @Valid @RequestBody
            NoteWrapper noteDetails) {

        Note not = noteDAO.findOne(noteid);
        if (not == null) {
            return ResponseEntity.notFound().build();
        }

        not.clearTags();
        noteDAO.save(not);

        not.setTitle(noteDetails.getTitle());
        not.setBody(noteDetails.getBody());
        not.addTags(noteDetails.getTags());

        Note updateNote = noteDAO.save(not);
        return ResponseEntity.ok().body(NoteWrapper.buildFrom(updateNote));

    }

    /*Delete a note*/
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<NoteWrapper> deleteNote(@PathVariable(value = "id") Long noteid) {

        Note not = noteDAO.findOne(noteid);
        if (not == null) {
            return ResponseEntity.notFound().build();

        }
        noteDAO.delete(not);

        return ResponseEntity.ok().build();
    }
}
