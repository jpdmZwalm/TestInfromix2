package be.regie.wiw.model.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "transport",
       indexes = {@Index(name="tra_nl_ui",columnList = "tra_descr_nl", unique = true),
                  @Index(name="tra_fr_ui",columnList = "tra_descr_fr", unique = true)})
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pk_tra_id")
    private Integer id;

    @Column(name="tra_old_id")
    private Integer oldId;

    @Column(name="ti_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    @Column(name = "tra_descr_nl", nullable = false, columnDefinition = "CHAR(40)")
    private String descrNL;

    @Column(name = "tra_descr_fr", nullable = false, columnDefinition = "CHAR(40)")
    private String descrFR;

    @Column(name = "tra_asSecond", nullable = false)
    private Boolean asSecond;

    public Transport() {
    }

    public Transport(String descrNL, String descrFR, Boolean asSecond) {
        this.descrNL = descrNL;
        this.descrFR = descrFR;
        this.asSecond = asSecond;
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
