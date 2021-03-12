package be.regie.wiw.model.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "CClass.ExistsNL",
                           query = "SELECT c FROM CClass c " +
                                   "WHERE c.longNL  = :longNL"), //"WHERE c.shortNL = :shortNL AND c.longNL  = :longNL"),
               @NamedQuery(name = "CClass.ExistsFR",
                           query = "SELECT c FROM CClass c " +
                                   "WHERE c.longFR  = :longFR"), // "WHERE c.shortFR = :shortFR AND c.longFR  = :longFR"),
        @NamedQuery(name = "CClass.omschr",
                query = "SELECT c FROM CClass c " + //"WHERE c.shortNL = :shortNL AND c.longNL  = :longNL AND c.shortFR = :shortFR AND c.longFR  = :longFR"),
                        "WHERE c.longNL  = :longNL " + //c.shortNL c.shortFR
                        "  AND c.longFR  = :longFR"),
        @NamedQuery(name = "CClass.old_id",
                query = "SELECT c FROM CClass c " +
                        "WHERE c.oldId = :old_id")
              })

/* DELETED : cla_short_nl, cla_short_fr
@Table(name = "class",
        indexes = {@Index(name="cla_nl_UI",columnList = "cla_short_nl, cla_long_nl", unique = true),
                   @Index(name="cla_fr_UI",columnList = "cla_short_fr, cla_long_fr", unique = true)
        })
 */
@Table(name = "class",
        indexes = {@Index(name="cla_l_nl_UI",columnList = "cla_long_nl", unique = true),
                @Index(name="cla_l_fr_UI",columnList = "cla_long_fr", unique = true)
        })

public class CClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_cla_id")
    private Integer id;

    @JsonIgnore
    @Column(name="cla_old_id")
    private Integer oldId;

    @JsonIgnore
    @Column(name="cla_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    /* DELETED : cla_short_nl, cla_short_fr
    @Column(name = "cla_short_nl", nullable = false, columnDefinition = "NCHAR(5)")
    private String shortNL;

    @Column(name = "cla_short_fr", nullable = false, columnDefinition = "NCHAR(5)")
    private String shortFR;
     */

    @Column(name = "cla_long_nl", nullable = false, columnDefinition = "NCHAR(50)")
    private String longNL;

    @Column(name = "cla_long_fr", nullable = false, columnDefinition = "NCHAR(50)")
    private String longFR;

    public CClass() {
    }

    public CClass(String longNL, String longFR) { //(String shortNL, String shortFR, String longNL, String longFR)
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

    @Override
    public String toString() {
        return "CClass{" +
                "id=" + id +
                ", oldId=" + oldId +
                ", oldSource='" + oldSource + '\'' +
                ", longNL   ='" + longNL + '\'' +
                ", longFR   ='" + longFR + '\'' +
                '}';
    }
}
