package org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cf_stucontest", schema = "graduate")
public class CfStucontestEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "cid", nullable = false)
    private Long cid;
    @Basic
    @Column(name = "cfid", nullable = false)
    private Long cfid;
    @Basic
    @Column(name = "t_rank", nullable = false)
    private int rank;
    @Basic
    @Column(name = "solve", nullable = false)
    private int solve;
    @Basic
    @Column(name = "diff", nullable = false)
    private int diff;
    @Basic
    @Column(name = "rating", nullable = false)
    private int rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getCfid() {
        return cfid;
    }

    public void setCfid(Long cfid) {
        this.cfid = cfid;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getSolve() {
        return solve;
    }

    public void setSolve(int solve) {
        this.solve = solve;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CfStucontestEntity that = (CfStucontestEntity) o;
        return rank == that.rank && solve == that.solve && diff == that.diff && rating == that.rating && Objects.equals(id, that.id) && Objects.equals(cid, that.cid) && Objects.equals(cfid, that.cfid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cid, cfid, rank, solve, diff, rating);
    }
}
