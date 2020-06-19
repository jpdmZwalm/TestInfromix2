package be.regie.wiw.model.db.entity;

import javax.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "Title.ExistsNL",
                query = "SELECT t FROM Title t " +
                        "WHERE t.shortNL = :shortNL " +
                        "  AND t.longNL  = :longNL"),
               @NamedQuery(name = "Title.ExistsFR",
                       query = "SELECT t FROM Title t " +
                               "WHERE t.shortFR = :shortFR " +
                               "  AND t.longFR  = :longFR"),
        @NamedQuery(name = "Title.oldId",
                query = "SELECT t FROM Title t " +
                        "WHERE t.oldId = :oldId")
})
@Table(name = "title",
        indexes = {@Index(name="ti_nl_UI",columnList = "ti_short_nl", unique = true),
                   @Index(name="ti_fr_UI",columnList = "ti_short_fr", unique = true)})
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_ti_id")
    private Integer id;

    @Column(name="ti_old_id")
    private Integer oldId;

    @Column(name="ti_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    @Column(name = "ti_short_nl", nullable = false, columnDefinition = "NCHAR(5)")
    private String shortNL;

    @Column(name = "ti_short_fr", nullable = false, columnDefinition = "NCHAR(5)")
    private String shortFR;

    @Column(name = "ti_long_nl", nullable = false, columnDefinition = "NCHAR(50)")
    private String longNL;

    @Column(name = "ti_long_fr", nullable = false, columnDefinition = "NCHAR(50)")
    private String longFR;

    public Title() {
    }

    public Title(String shortNL, String shortFR, String longNL, String longFR) {
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