package org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity;

import javax.persistence.*;

@Entity
@Table(name = "cf_submitcode", schema = "graduate")
public class CfSubmitcodeEntity {
    @Id
    @Column(name = "sid")
    private Long sid;
    @Basic
    @Column(name = "code")
    private String code;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CfSubmitcodeEntity that = (CfSubmitcodeEntity) o;

        if (sid != null ? !sid.equals(that.sid) : that.sid != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sid != null ? sid.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }
}
