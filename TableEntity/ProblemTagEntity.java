package org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity;

import javax.persistence.*;

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

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (tagName != null ? !tagName.equals(that.tagName) : that.tagName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tagName != null ? tagName.hashCode() : 0);
        return result;
    }

    public ProblemTagEntity(String tagName) {
        this.tagName = tagName;
    }

    public ProblemTagEntity() {
    }

    public ProblemTagEntity(Integer id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }
}
