package be.regie.wiw.model.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "room",
       indexes = {@Index(name="ro_code_ui",columnList = "ro_code", unique = true)})
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pk_ro_id")
    private Integer id;

    @Column(name="ro_old_id")
    private Integer oldId;

    @Column(name="ro_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    @Column(name = "ro_code", nullable = false, columnDefinition = "CHAR(10)")
    private String code;

    @Column(name = "ro_descr_nl", nullable = false, columnDefinition = "CHAR(40)")
    private String descrNL;

    @Column(name = "ro_descr_fr", nullable = false, columnDefinition = "CHAR(40)")
    private String descrFR;

    @Column(name = "ro_tel", columnDefinition = "CHAR(10)")
    private String tel;

    @Column(name = "ro_showTel", nullable = false)
    private Boolean showTel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_ro_adr_id")
    private Address address;

    public Room() {
    }

    public Room(String code, String descrNL, String descrFR, String tel, Boolean showTel,
                Address address) {
        this.code = code;
        this.descrNL = descrNL;
        this.descrFR = descrFR;
        this.tel = tel;
        this.showTel = showTel;
        this.address = address;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Boolean getShowTel() {
        return showTel;
    }

    public void setShowTel(Boolean showTel) {
        this.showTel = showTel;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
