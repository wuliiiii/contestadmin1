package org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "codeforces_problems", schema = "graduate")
public class CodeforcesProblemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "cid", nullable = true)
    private Long cid;
    @Basic
    @Column(name = "qindex", nullable = true, length = 255)
    private String qindex;
    @Basic
    @Column(name = "difficulty", nullable = true)
    private Integer difficulty;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    public CodeforcesProblemsEntity() {
    }

    public CodeforcesProblemsEntity(Long cid, String qindex, Integer difficulty, String name) {
        this.cid = cid;
        this.qindex = qindex;
        this.difficulty = difficulty;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }


    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getQindex() {
        return qindex;
    }

    public void setQindex(String index) {
        this.qindex = index;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CodeforcesProblemsEntity)) return false;
        CodeforcesProblemsEntity that = (CodeforcesProblemsEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(cid, that.cid) && Objects.equals(qindex, that.qindex) && Objects.equals(difficulty, that.difficulty) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cid, qindex, difficulty, name);
    }
}
