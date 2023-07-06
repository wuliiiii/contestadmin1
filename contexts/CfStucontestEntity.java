package org.algotithmcontestdatacollect.displaybackend.Entities.CodeforcesEntities;

import javax.persistence.*;

@Entity
@Table(name = "cf_stucontest", schema = "graduate")
public class CfStucontestEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "cid")
    private Long cid;
    @Basic
    @Column(name = "cfid")
    private Long cfid;
    @Basic
    @Column(name = "t_rank")
    private int tRank;
    @Basic
    @Column(name = "solve")
    private int solve;
    @Basic
    @Column(name = "diff")
    private int diff;
    @Basic
    @Column(name = "rating")
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

    public int gettRank() {
        return tRank;
    }

    public void settRank(int tRank) {
        this.tRank = tRank;
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

        if (tRank != that.tRank) return false;
        if (solve != that.solve) return false;
        if (diff != that.diff) return false;
        if (rating != that.rating) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cid != null ? !cid.equals(that.cid) : that.cid != null) return false;
        if (cfid != null ? !cfid.equals(that.cfid) : that.cfid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cid != null ? cid.hashCode() : 0);
        result = 31 * result + (cfid != null ? cfid.hashCode() : 0);
        result = 31 * result + tRank;
        result = 31 * result + solve;
        result = 31 * result + diff;
        result = 31 * result + rating;
        return result;
    }
}
