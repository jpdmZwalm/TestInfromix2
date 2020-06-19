package be.regie.wiw.model.db.entity;

//import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "address")
/*
TODO Building name is niet uniek
Bijvoorbeeld CENTRE ADMINISTRATIF : CINEY, EUPEN, HUY, MALMEDY, ....
@Table(name = "addressx",
        indexes = {@Index(name="adr_buildingNameNL_UI",columnList = "adr_buildingNameNL", unique = true),
                   @Index(name="adr_buildingNameFR_UI",columnList = "adr_buildingNameFR", unique = true)})
*/
@NamedQueries({@NamedQuery(name = "Address.old_id",
        query = "SELECT a FROM Address a " +
                "WHERE a.oldId = :old_id")
})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pk_adr_id")
    private Integer id;

    @Column(name="adr_old_id")
    private Integer oldId;

    @Column(name="adr_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    @Column(name="adr_buildingNameNL", columnDefinition = "NVARCHAR(70)")
    private String buildingNameNL;

    @Column(name="adr_buildingNameFR", columnDefinition = "NVARCHAR(70)")
    private String buildingNameFR;

    @Column(name="adr_streetNL", nullable = false, columnDefinition = "NVARCHAR(70)")
    private String streetNL;

    @Column(name="adr_streetFR", nullable = false, columnDefinition = "NVARCHAR(70)")
    private String streetFR;

    @Column(name="adr_nr", columnDefinition = "CHAR(10)")
    private String nr;

    @Column(name="adr_zip", nullable = false, columnDefinition = "CHAR(8)")
    private String zip;

    @Column(name="adr_city_nl", nullable = false, columnDefinition = "NVARCHAR(40)")
    private String cityNL;

    @Column(name="adr_city_fr", nullable = false, columnDefinition = "NVARCHAR(40)")
    private String cityFR;

    @Column(name="adr_country", nullable = false, columnDefinition = "NVARCHAR(40)")
    private String country;

    @Column(name="adr_ctry2", columnDefinition = "CHAR(2)")
    private String ctry2;

    @Column(name="adr_ctry3", columnDefinition = "CHAR(3)")
    private String ctry3;

    @Column(name="adr_tel", columnDefinition = "CHAR(20)")
    private String tel;

    @Temporal(TemporalType.DATE)
    @Column(name="adr_checked")
    private Date checked;

    public Address() {}

    public Address(String buildingNameNL, String buildingNameFR,
                   String streetNL, String streetFR, String nr, String zip,
                   String cityNL, String cityFR, String country, String ctry2, String ctry3,
                   String tel, Date checked) {
        this.buildingNameNL = buildingNameNL;
        this.buildingNameFR = buildingNameFR;
        this.streetNL = streetNL;
        this.streetFR = streetFR;
        this.nr = nr;
        this.zip = zip;
        this.cityNL = cityNL;
        this.cityFR = cityFR;
        this.country = country;
        this.ctry2 = ctry2;
        this.ctry3 = ctry3;
        this.tel = tel;
        this.checked = checked;
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

    public String getBuildingNameNL() {
        return buildingNameNL;
    }

    public void setBuildingNameNL(String buildingNameNL) {
        this.buildingNameNL = buildingNameNL;
    }

    public String getBuildingNameFR() {
        return buildingNameFR;
    }

    public void setBuildingNameFR(String buildingNameFR) {
        this.buildingNameFR = buildingNameFR;
    }

    public String getStreetNL() {
        return streetNL;
    }

    public void setStreetNL(String streetNL) {
        this.streetNL = streetNL;
    }

    public String getStreetFR() {
        return streetFR;
    }

    public void setStreetFR(String streetFR) {
        this.streetFR = streetFR;
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCityNL() {
        return cityNL;
    }

    public void setCityNL(String cityNL) {
        this.cityNL = cityNL;
    }

    public String getCityFR() {
        return cityFR;
    }

    public void setCityFR(String cityFR) {
        this.cityFR = cityFR;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCtry2() {
        return ctry2;
    }

    public void setCtry2(String ctry2) {
        this.ctry2 = ctry2;
    }

    public String getCtry3() {
        return ctry3;
    }

    public void setCtry3(String ctry3) {
        this.ctry3 = ctry3;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Date getChecked() {
        return checked;
    }

    public void setChecked(Date checked) {
        this.checked = checked;
    }
}
