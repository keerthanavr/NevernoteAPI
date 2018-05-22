package com.nevernote.nevernoterestapi.services;

import com.nevernote.nevernoterestapi.model.Note;
import com.nevernote.nevernoterestapi.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;


@Service
public class NoteService {

	@Autowired
	NoteRepository noteRepository;
	
	/* Save a note */
	
	public Note save(Note not) {
		not.setLastModified(Date.from(Instant.now()));
		return noteRepository.save(not);
	}
	
	/*Search all notes */
	
	public List<Note> findAll(){
		return noteRepository.findAll();
	}
	
	
	/*Get a note*/
	
	public Note findOne(Long noteid) {
		return noteRepository.findOne(noteid);
	}
	
	/*Delete a note*/
	
	public void delete(Note not) {
		noteRepository.delete(not);
	}

	public void deleteAll() {
		noteRepository.deleteAll();
	}
}
