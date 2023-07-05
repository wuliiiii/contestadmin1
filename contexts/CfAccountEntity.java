package org.algotithmcontestdatacollect.displaybackend.Entities.CodeforcesEntities;

import javax.persistence.*;

@Entity
@Table(name = "cf_account", schema = "graduate")
public class CfAccountEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "uid")
    private Long uid;
    @Basic
    @Column(name = "codeforces_id")
    private String codeforcesId;
    @Basic
    @Column(name = "rating")
    private Integer rating;
    @Basic
    @Column(name = "is_main")
    private byte isMain;
    @Basic
    @Column(name = "is_using")
    private byte isUsing;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getCodeforcesId() {
        return codeforcesId;
    }

    public void setCodeforcesId(String codeforcesId) {
        this.codeforcesId = codeforcesId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public byte getIsMain() {
        return isMain;
    }

    public void setIsMain(byte isMain) {
        this.isMain = isMain;
    }

    public byte getIsUsing() {
        return isUsing;
    }

    public void setIsUsing(byte isUsing) {
        this.isUsing = isUsing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfAccountEntity that = (CfAccountEntity) o;

        if (isMain != that.isMain) return false;
        if (isUsing != that.isUsing) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;
        if (codeforcesId != null ? !codeforcesId.equals(that.codeforcesId) : that.codeforcesId != null) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (codeforcesId != null ? codeforcesId.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (int) isMain;
        result = 31 * result + (int) isUsing;
        return result;
    }
}
