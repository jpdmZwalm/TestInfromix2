package be.regie.wiw.model.db.entity;

import javax.persistence.*;

@Table(name = "speciality",
        indexes = {@Index(name="spe_nl_UI",columnList = "spe_descr_nl", unique = true),
                @Index(name="spe_fr_UI",columnList = "spe_descr_fr", unique = true)})

@Entity
public class Speciality { //CHANGED : NEW CLASS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_spe_id")
    private Integer id;

    @Column(name = "spe_descr_nl", nullable = false, columnDefinition = "NCHAR(50)")
    private String descrNL;

    @Column(name = "spe_descr_fr", nullable = false, columnDefinition = "NCHAR(50)")
    private String descrFR;

    public Speciality() {
    }

    public Speciality(String descrNL, String descrFR) {
        this.descrNL = descrNL;
        this.descrFR = descrFR;
    }

    public Integer getId() {
        return id;
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
