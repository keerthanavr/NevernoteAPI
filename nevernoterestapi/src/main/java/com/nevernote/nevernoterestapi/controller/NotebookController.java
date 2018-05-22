package com.nevernote.nevernoterestapi.controller;


import com.nevernote.nevernoterestapi.controller.wrappers.NotebookWrapper;
import com.nevernote.nevernoterestapi.model.Notebook;
import com.nevernote.nevernoterestapi.services.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notebooks")
public class NotebookController {

    @Autowired
    private NotebookService notebookService;

    @GetMapping("/{id}")
    public ResponseEntity<NotebookWrapper> getNotebook(@PathVariable(name = "id") Long id) {
        Notebook notebook = notebookService.find(id);

        return notebook != null ? ResponseEntity.ok(NotebookWrapper.buildFromNotebook(notebook)) : ResponseEntity
                .notFound().build();
    }

    @GetMapping("/{id}/tags/{tags}")
    public ResponseEntity<NotebookWrapper> getFilteredNotebook(@PathVariable(name = "id") Long id, @PathVariable(name =
            "tags") String[] tags) {
        Notebook notebook = notebookService.findWithTags(id, tags);

        if (notebook == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(NotebookWrapper.buildFromNotebook(notebook));
        }
    }


    @PostMapping("/")
    public ResponseEntity<NotebookWrapper> addNotebook() {
        return ResponseEntity.ok(NotebookWrapper.buildFromNotebook(notebookService.create()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NotebookWrapper> deleteNotebook(@PathVariable(name = "id") long id) {
        notebookService.delete(id);

        return ResponseEntity.ok().build();
    }

}
