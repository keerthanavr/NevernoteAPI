package com.nevernote.nevernoterestapi.services;

import com.nevernote.nevernoterestapi.NoteApplication;
import com.nevernote.nevernoterestapi.model.Note;
import com.nevernote.nevernoterestapi.model.Notebook;
import com.nevernote.nevernoterestapi.model.Tag;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = NoteApplication.class)
public class NotebookServiceTest {

    @Autowired
    private NotebookService notebookService;

    @Autowired
    private NoteService noteDAO;

    @Test
    public void testCreate() throws Exception {
        //when
        Notebook notebook = notebookService.create();

        //then
        assertThat(notebook).isNotNull();
        assertThat(notebook.getId()).isNotNull();
        assertThat(notebook.getId()).isGreaterThan(0l);
    }

    @Test
    public void testFind() throws Exception {
        //given
        Notebook notebook = notebookService.create();

        //when
        Notebook savedNotebook = notebookService.find(notebook.getId());

        //then
        assertThat(notebook).isEqualTo(savedNotebook);
    }

    @Test
    public void testDelete() throws Exception {
        //given
        Notebook notebook = notebookService.create();

        //when
        notebookService.delete(notebook);

        //then
        assertThat(notebookService.find(notebook.getId())).isNull();

    }

    @Test
    public void testFilterNotesWithTags() throws Exception {
        //given
        Notebook notebook = notebookService.create();

        Note note = new Note();
        note.setTitle("title");
        note.setBody("some note");
        note.addTag("tag3");
        note.addTag("tag4");
        note.setNotebook(notebook);
        noteDAO.save(note);


        Note note2 = new Note();
        note2.setTitle("title2");
        note2.setBody("some note2");
        note2.addTag("tag2");
        note.addTag("tag5");
        note2.setNotebook(notebook);
        noteDAO.save(note2);

        //when
        Notebook savedNotebook = notebookService.findWithTags(notebook.getId(), new String[]{"tag1", "tag2"});


        //then
        assertThat(savedNotebook.getNotes()).isNotEmpty();
        assertThat(savedNotebook.getNotes()).hasSize(1);
        Tag expectedTag = new Tag();
        expectedTag.setTitle("tag2");
        assertThat(savedNotebook.getNotes().stream().findFirst().get().getTags()).contains(expectedTag);

    }

    @After
    public void tearDown() throws Exception {
        noteDAO.deleteAll();
        notebookService.deleteAll();
    }
}