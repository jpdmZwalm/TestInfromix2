package be.regie.wiw.model.db.entity;

import javax.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "Organisation.ExistsOldOrgId",
                query = "SELECT o FROM Organisation o " +
                        "WHERE o.oldOrgId = :oldOrgId"),
               @NamedQuery(name = "Organisation.ExistsNL",
                query = "SELECT o FROM Organisation o " +
                        "WHERE o.shortNL = :shortNL " +
                        "  AND o.longNL  = :longNL"),
               @NamedQuery(name = "Organisation.ExistsFR",
                query = "SELECT o FROM Organisation o " +
                        "WHERE o.shortFR = :shortFR " +
                        "  AND o.longFR  = :longFR"),
        @NamedQuery(name = "Organisation.org_old_id",
                query = "SELECT o FROM Organisation o " +
                        "WHERE o.oldOrgId = :org_old_id")
})
@Table(name = "organisation",
        indexes = {@Index(name="org_nl_UI",columnList = "org_short_nl", unique = true),
                @Index(name="org_fr_UI",columnList = "org_short_fr", unique = true)})
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_org_id")
    private Integer id;

    @Column(name="org_old_id")
    private Integer oldId;

    @Column(name="org_old_org_id")
    private Integer oldOrgId;

    @Column(name="org_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    @Column(name = "org_short_nl", nullable = false, columnDefinition = "NCHAR(10)")
    private String shortNL;

    @Column(name = "org_short_fr", nullable = false, columnDefinition = "NCHAR(10)")
    private String shortFR;

    @Column(name = "org_long_nl", nullable = false, columnDefinition = "NCHAR(50)")
    private String longNL;

    @Column(name = "org_long_fr", nullable = false, columnDefinition = "NCHAR(50)")
    private String longFR;

    public Organisation() {
    }

    public Organisation(String shortNL, String shortFR, String longNL, String longFR) {
        this.shortNL = shortNL;
        this.shortFR = shortFR;
        this.longNL = longNL;
        this.longFR = longFR;
    }

    public Integer getId() {
        return id;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public Integer getOldOrgId() {
        return oldOrgId;
    }

    public void setOldOrgId(Integer oldOrgId) {
        this.oldOrgId = oldOrgId;
    }

    public String getOldSource() {
        return oldSource;
    }

    public void setOldSource(String oldSource) {
        this.oldSource = oldSource;
    }

    public String getShortNL() {
        return shortNL;
    }

    public void setShortNL(String shortNL) {
        this.shortNL = shortNL;
    }

    public String getShortFR() {
        return shortFR;
    }

    public void setShortFR(String shortFR) {
        this.shortFR = shortFR;
    }

    public String getLongNL() {
        return longNL;
    }

    public void setLongNL(String longNL) {
        this.longNL = longNL;
    }

    public String getLongFR() {
        return longFR;
    }

    public void setLongFR(String longFR) {
        this.longFR = longFR;
    }
}
