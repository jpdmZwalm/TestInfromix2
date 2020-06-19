package be.regie.wiw.model.db.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "checkInOut")
public class CheckInOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pk_cio_id")
    private Integer id;

    @Column(name="cio_old_id")
    private Integer oldId;

    @Column(name="cio_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    @Column(name = "cio_timeIn", nullable = false, columnDefinition="DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeIn;

    @Column(name = "cio_timeOut", nullable = true, columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOut;

    @Column(name="cio_firstName", nullable = false, columnDefinition = "NCHAR(40)")
    private String fname;

    @Column(name="cio_name", nullable = false, columnDefinition = "NCHAR(40)")
    private String name;

    @Column(name="cio_company", nullable = false, columnDefinition = "NCHAR(40)")
    private String company;

    @Column(name="cio_language", nullable = false, columnDefinition = "CHAR(2)")
    private String language;

    @Column(name="cio_parking", nullable = false)
    private Boolean parking;

    @Column(name="cio_licence", nullable = false, columnDefinition = "CHAR(10)")
    private String licence;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_cio_app_id")
    private Approach approach;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_cio_pe_id")
    private Person person;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_cio_tra_id_main")
    private Transport transportMain;

    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_cio_tra_id_second")
    private Transport transportSecond;

    public CheckInOut() {
    }

    public CheckInOut(Date timeIn,       Date timeOut,    String fname,    String name,
                      String company,    String language, Boolean parking, String licence,
                      Approach approach, Person person,
                      Transport transportMain, Transport transportSecond) {
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.fname = fname;
        this.name = name;
        this.company = company;
        this.language = language;
        this.parking = parking;
        this.licence = licence;
        this.approach = approach;
        this.person = person;
        this.transportMain = transportMain;
        this.transportSecond = transportSecond;
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

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public Approach getApproach() {
        return approach;
    }

    public void setApproach(Approach approach) {
        this.approach = approach;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Transport getTransportMain() {
        return transportMain;
    }

    public void setTransportMain(Transport transportMain) {
        this.transportMain = transportMain;
    }

    public Transport getTransportSecond() {
        return transportSecond;
    }

    public void setTransportSecond(Transport transportSecond) {
        this.transportSecond = transportSecond;
    }
}
