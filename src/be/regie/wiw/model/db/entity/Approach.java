package be.regie.wiw.model.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "approach",
        indexes = {@Index(name = "app_nl_UI", columnList = "app_short_nl", unique = true),
                @Index(name = "app_fr_UI", columnList = "app_short_fr", unique = true)})
@NamedQueries({@NamedQuery(name = "Approach.old_id",
        query = "SELECT a FROM Approach a " +
                "WHERE a.oldId = :old_id")
})
public class Approach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_app_id")
    private Integer id;

    @Column(name="app_old_id")
    private Integer oldId;

    @Column(name="app_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    @Column(name = "app_short_nl", nullable = false, columnDefinition = "NCHAR(5)")
    private String shortNL;

    @Column(name = "app_short_fr", nullable = false, columnDefinition = "NCHAR(5)")
    private String shortFR;

    @Column(name = "app_long_nl", nullable = false, columnDefinition = "NCHAR(50)")
    private String longNL;

    @Column(name = "app_long_fr", nullable = false, columnDefinition = "NCHAR(50)")
    private String longFR;

    public Approach() {
    }

    public Approach(String shortNL, String shortFR, String longNL, String longFR) {
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
