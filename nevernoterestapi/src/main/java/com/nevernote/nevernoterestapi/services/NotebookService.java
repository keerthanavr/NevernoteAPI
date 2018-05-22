package com.nevernote.nevernoterestapi.services;

import com.nevernote.nevernoterestapi.model.Notebook;

public interface NotebookService {

    Notebook create();

    Notebook find(Long id);

    void delete(Long id);

    void deleteAll();

    void delete(Notebook notebook);

    Notebook findWithTags(Long id, String[] tags);
}
