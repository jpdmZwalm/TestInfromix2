package be.regie.wiw.model.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "statue",
        indexes = {@Index(name="st_nl_UI",columnList = "st_descr_nl", unique = true),
                @Index(name="st_fr_UI",columnList = "st_descr_fr", unique = true)})
public class Statue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_st_id")
    private Integer id;

    @Column(name="st_old_id")
    private Integer oldId;

    @Column(name="st_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    @Column(name = "st_descr_nl", nullable = false, columnDefinition = "NCHAR(50)")
    private String descrNL;

    @Column(name = "st_descr_fr", nullable = false, columnDefinition = "NCHAR(50)")
    private String descrFR;

    public Statue() {
    }

    public Statue(String descrNL, String descrFR) {
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
}
