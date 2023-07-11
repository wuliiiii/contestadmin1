package org.algotithmcontestdatacollect.displaybackend.Entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "problem_tag", schema = "graduate")
public class ProblemTagEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "tag_name", nullable = true, length = 255)
    private String tagName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProblemTagEntity that = (ProblemTagEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(tagName, that.tagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tagName);
    }
}
