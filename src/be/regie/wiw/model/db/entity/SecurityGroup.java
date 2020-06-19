package be.regie.wiw.model.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "securityGroup",
        indexes = {@Index(name="sg_nl_UI",columnList = "sg_descr_nl", unique = true),
                @Index(name="sg_fr_UI",columnList = "sg_descr_fr", unique = true)})
public class SecurityGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_sg_id")
    private Integer id;

    @Column(name="sg_old_id")
    private Integer oldId;

    @Column(name="ro_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    @Column(name = "sg_descr_nl", nullable = false, columnDefinition = "NCHAR(50)")
    private String descrNL;

    @Column(name = "sg_descr_fr", nullable = false, columnDefinition = "NCHAR(50)")
    private String descrFR;

    public SecurityGroup() {
    }

    public SecurityGroup(String descrNL, String descrFR) {
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
