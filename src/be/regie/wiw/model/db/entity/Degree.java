package be.regie.wiw.model.db.entity;

import javax.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "Degree.ExistsNL",
                query = "SELECT d FROM Degree d " +
                        "WHERE d.descrNL = :descrNL"),
               @NamedQuery(name = "Degree.ExistsFR",
                query = "SELECT d FROM Degree d " +
                        "WHERE d.descrFR = :descrFR")})
@Table(name = "degree",
        indexes = {@Index(name="deg_nl_UI",columnList = "deg_descr_nl", unique = true),
                   @Index(name="deg_fr_UI",columnList = "deg_descr_fr", unique = true)})
public class Degree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_deg_id")
    private Integer id;

    @Column(name="deg_old_id")
    private Integer oldId;

    @Column(name="deg_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    @Column(name = "deg_descr_nl", nullable = false, columnDefinition = "NCHAR(60)")
    private String descrNL;

    @Column(name = "deg_descr_fr", nullable = false, columnDefinition = "NCHAR(60)")
    private String descrFR;

    public Degree() {
    }

    public Degree(String descrNL, String descrFR) {
        this.descrNL = descrNL;
        this.descrFR = descrFR;
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

    public String getDescrNL() {
        return descrNL;
    }

    public void setDescrNL(String descrNL) {
        this.descrNL = descrNL;
    }

    public String getDescrFR() {
        return descrFR;
    }

    public void setDescrFR(String descrFR) {
        this.descrFR = descrFR;
    }

    @Override
    public String toString() {
        return "Degree{" +
                "id=" + id +
                ", oldId=" + oldId +
                ", oldSource='" + oldSource + '\'' +
                ", descrNL='" + descrNL + '\'' +
                ", descrFR='" + descrFR + '\'' +
                '}';
    }
}
