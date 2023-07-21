package org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CodeforcesProblemsTagMapEntityPK implements Serializable {
    @Column(name = "codeforces_problem_id", nullable = false)
    @Id
    private Integer codeforcesProblemId;
    @Column(name = "tag_id", nullable = false)
    @Id
    private Integer tagId;

    public Integer getCodeforcesProblemId() {
        return codeforcesProblemId;
    }

    public void setCodeforcesProblemId(Integer codeforcesProblemId) {
        this.codeforcesProblemId = codeforcesProblemId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeforcesProblemsTagMapEntityPK that = (CodeforcesProblemsTagMapEntityPK) o;

        if (codeforcesProblemId != null ? !codeforcesProblemId.equals(that.codeforcesProblemId) : that.codeforcesProblemId != null)
            return false;
        if (tagId != null ? !tagId.equals(that.tagId) : that.tagId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codeforcesProblemId != null ? codeforcesProblemId.hashCode() : 0;
        result = 31 * result + (tagId != null ? tagId.hashCode() : 0);
        return result;
    }

    public CodeforcesProblemsTagMapEntityPK(Integer codeforcesProblemId, Integer tagId) {
        this.codeforcesProblemId = codeforcesProblemId;
        this.tagId = tagId;
    }

    public CodeforcesProblemsTagMapEntityPK() {
    }
}
