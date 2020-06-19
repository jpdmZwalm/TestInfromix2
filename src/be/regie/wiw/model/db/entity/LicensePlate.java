package be.regie.wiw.model.db.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({@NamedQuery(name = "LicensePlate.Exists",
                query = "SELECT l FROM LicensePlate l " +
                        "WHERE l.licenseplate = :licenseplate")
})
@Table(name = "licensePlate",
        indexes = {@Index(name="lp_UI",columnList = "lp_licenseplate", unique = true)})
public class LicensePlate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_lp_id")
    private Integer id;

    @Column(name="lp_old_id")
    private Integer oldId;

    @Column(name="lp_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    @Column(name = "lp_licenseplate", nullable = false, columnDefinition = "CHAR(16)")
    private String licenseplate;

    @Column(name = "lp_brand", nullable = false, columnDefinition = "NCHAR(20)")
    private String brand;

    @Column(name = "lp_type", nullable = false, columnDefinition = "NCHAR(20)")
    private String type;

    @Temporal(TemporalType.DATE)
    @Column(name = "lp_updated", nullable = true)
    private Date updated;

    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_lp_pe_id_upd",
            foreignKey = @ForeignKey(name="fk_lp_pe_id_upd_FK"))
    private Person updatedBy;

    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_lp_pe_id_owner",
                foreignKey = @ForeignKey(name="fk_lp_pe_id_owner_FK"))
    private Person owner;


    public LicensePlate() {
    }

    public LicensePlate(String licenseplate, String brand, String type, Date updated, Person updatedBy, Person owner) {
        this.licenseplate = licenseplate;
        this.brand = brand;
        this.type = type;
        this.updated = updated;
        this.updatedBy = updatedBy;
        this.owner = owner;
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

    public String getLicenseplate() {
        return licenseplate;
    }

    public void setLicenseplate(String licenseplate) {
        this.licenseplate = licenseplate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Person getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Person updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}