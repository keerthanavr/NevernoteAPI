package com.nevernote.nevernoterestapi.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Notebooks")
@EntityListeners(AuditingEntityListener.class)
public class Notebook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "notebook", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Set<Note> notes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof Notebook)) return false;

        Notebook other = (Notebook) object;
        return this.id.equals(other.id);
    }

    public int hashCode() {
        return id.hashCode();
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }
}
