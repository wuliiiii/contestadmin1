package org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cf_account", schema = "graduate", catalog = "")
public class CfAccountEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "uid", nullable = false)
    private Long uid;
    @Basic
    @Column(name = "codeforces_id", nullable = false, length = 255)
    private String codeforcesId;
    @Basic
    @Column(name = "rating", nullable = true)
    private Integer rating;
    @Basic
    @Column(name = "is_main", nullable = false)
    private Short isMain;
    @Basic
    @Column(name = "is_using", nullable = false)
    private Short isUsing;

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

    public Short getIsMain() {
        return isMain;
    }

    public void setIsMain(Short isMain) {
        this.isMain = isMain;
    }

    public Short getIsUsing() {
        return isUsing;
    }

    public void setIsUsing(Short isUsing) {
        this.isUsing = isUsing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CfAccountEntity that = (CfAccountEntity) o;
        return isMain == that.isMain && isUsing == that.isUsing && Objects.equals(id, that.id) && Objects.equals(uid, that.uid) && Objects.equals(codeforcesId, that.codeforcesId) && Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uid, codeforcesId, rating, isMain, isUsing);
    }
}
