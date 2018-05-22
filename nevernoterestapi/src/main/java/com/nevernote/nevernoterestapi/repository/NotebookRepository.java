package com.nevernote.nevernoterestapi.repository;

import com.nevernote.nevernoterestapi.model.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook, Long> {
}
