package com.nevernote.nevernoterestapi.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Notes")
@EntityListeners(AuditingEntityListener.class)
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @OneToMany(mappedBy = "note", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Tag> tags = new HashSet<>();


    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date lastModified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notebook_id", nullable = false)
    private Notebook notebook;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setNotebook(Notebook notebook) {
        this.notebook = notebook;
    }

    public Notebook getNotebook() {
        return notebook;
    }

    public Note addTag(String tagName) {
        Tag tag = new Tag();
        tag.setTitle(tagName);
        tag.setNote(this);
        this.tags.add(tag);
        return this;
    }

    public void addTags(Set<String> tags) {
        if (tags != null) {
            for (String tag : tags) {
                addTag(tag);
            }
        }
    }

    public boolean equals(Object object) {
        if (object == null) return false;
        if (!(object instanceof Note)) return false;

        Note other = (Note) object;
        return this.id.equals(other.id) && this.title.equals(other.title);
    }

    public int hashCode() {
        return id.hashCode() * title.hashCode();
    }

    public void clearTags() {
        this.tags.clear();
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
