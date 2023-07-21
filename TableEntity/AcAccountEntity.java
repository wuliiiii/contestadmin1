package org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity;
import javax.persistence.*;

@Entity
@Table(name = "ac_account")
public class AcAccountEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "uid")
    private Long uid;
    @Basic
    @Column(name = "atcoder_id")
    private String atcoderId;
    @Basic
    @Column(name = "rating")
    private Long rating;
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

    public String getAtcoderId() {
        return atcoderId;
    }

    public void setAtcoderId(String atcoderId) {
        this.atcoderId = atcoderId;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
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

        AcAccountEntity that = (AcAccountEntity) o;

        if (rating != that.rating) return false;
        if (isMain != that.isMain) return false;
        if (isUsing != that.isUsing) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;
        if (atcoderId != null ? !atcoderId.equals(that.atcoderId) : that.atcoderId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (atcoderId != null ? atcoderId.hashCode() : 0);
        result = 31 * result + (int) (rating ^ (rating >>> 32));
        result = 31 * result + (int) isMain;
        result = 31 * result + (int) isUsing;
        return result;
    }
}
