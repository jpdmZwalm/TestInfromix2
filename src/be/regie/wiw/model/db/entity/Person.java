package be.regie.wiw.model.db.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Entity
@NamedQueries({@NamedQuery(name = "Person.ExistsCode",
        query = "SELECT p FROM Person p " +
                "WHERE p.code = :code"),
        @NamedQuery(name = "Person.ExistsName",
                query = "SELECT p FROM Person p " +
                        "WHERE p.name = :name " +
                        "  AND p.fname = :fname"),
        @NamedQuery(name = "Person.oldId",
                query = "SELECT p FROM Person p " +
                        "WHERE p.oldId = :oldId")
})
@Table(name = "person",
        indexes = {@Index(name = "pe_name_UI", columnList = "pe_name, pe_fname", unique = true)})
//TODO SQL Server aanvaard geen duplicat null in unique
//       @Index(name = "pe_code_UI", columnList = "pe_code", unique = true)
//CREATE UNIQUE NONCLUSTERED INDEX pe_code_UI
//ON dbo.person
//WHERE pe_code IS NOT NULL;

public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pk_pe_id")
    private Integer id;

    @Column(name="pe_old_id")
    private Integer oldId;

    @Column(name="pe_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    @Column(name="pe_code", nullable = true)
    private Integer code;

    //TODO alter table "dbo"."person" alter column  pe_nationalNumber int not null
    @Column(name="pe_nationalNumber", nullable = true, columnDefinition = "CHAR(20)")
    private String nationalNumber;

    @Column(name="pe_name", nullable = false, columnDefinition = "NCHAR(40)")
    private String name;

    @Column(name="pe_fname", nullable = false, columnDefinition = "NCHAR(40)")
    private String fname;

    @Column(name="pe_language", nullable = false,
            columnDefinition = "CHAR(2) CHECK (pe_language IN ('F', 'N'))")
    private String language;

    //TODO wat met 2 tel nummers
    @Column(name="pe_tel", columnDefinition = "CHAR(30)")
    private String tel;

    //TODO wat met 2 tel nummers
    @Column(name="pe_mobile", columnDefinition = "CHAR(30)")
    private String mobile;

    @Column(name="pe_email", columnDefinition = "NVARCHAR(50)")
    private String email;

    @Column(name="pe_employer", columnDefinition = "NVARCHAR(70)")
    private String employer;

    @Column(name="pe_photo", columnDefinition = "NVARCHAR(70)")
    private String photo; //was BLOB,

    @Column(name="pe_photoVisible")
    private boolean photoVisible;

    @Column(name="pe_stamnr", columnDefinition = "CHAR(20)")
    private String stamnr;

    @Column(name="pe_updated", nullable = false, columnDefinition="DATETIME DEFAULT GETDATE()")
    @Temporal(TemporalType.DATE)
    private Date updated;


    //TODO pe_higherFunction moet weg

    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_pe_hf_id")
    private HigherFunction higherFunction;

    //TODO alter table "dbo"."person" alter column fk_pe_adr_id_adm int not null
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_pe_adr_id_adm")
    private Address addressAdmin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_pe_adr_id_wrk")
    private Address addressWork;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_pe_app_id")
    private Approach approach;

    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_pe_cla_id")
    private CClass clazz;

    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_pe_fct_id")
    private Function function;

    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_pe_deg_id")
    private Degree degree;

    //TODO alter table "dbo"."person" alter column  fk_pe_ro_id int not null
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_pe_ro_id")
    private Room room;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_pe_st_id")
    private Statue statue;

    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_pe_ti_id")
    private Title title;

    //TODO Service-PersonHead en Person-Service : 2 * not null
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_pe_srv_id")
    private Service service;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_pe_org_id")
    private Organisation org;

    @ManyToMany()
    @JoinTable(
            name = "person_secgrp",
            joinColumns = @JoinColumn(name = "fk_psg_pe_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_psg_sg_id"))
    Set<SecurityGroup> securityGroups;

    public Person() {}

    public Person(Integer code,  String nationalNumber, String name,   String fname,    String language,
                  String tel,    String mobile,         String email,  String employer, String photo,
                  boolean photoVisible, HigherFunction higherFunction, String stamnr,   Date updated,
                  Address addressAdmin, Address addressWork,   Approach approach, CClass clazz,
                  Function function,    Degree degree,         Room room,         Statue statue,
                  Title title,          Service service,       Organisation org) {
        setCode(code);
        setNationalNumber(nationalNumber);
        this.name = name;
        this.fname = fname;
        this.language = language;
        this.tel = tel;
        this.mobile = mobile;
        this.email = email;
        this.employer = employer;
        this.photo = photo;
        this.photoVisible = photoVisible;
        this.higherFunction = higherFunction;
        this.stamnr = stamnr;
        this.updated = updated;
        this.addressAdmin = addressAdmin;
        this.addressWork = addressWork;
        this.approach = approach;
        this.clazz = clazz;
        this.function = function;
        this.degree = degree;
        this.room = room;
        this.statue = statue;
        this.title = title;
        this.service = service;
        this.org = org;
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

    public Integer getCode() {
        return code;
    }

    //TODO zo voor alle null cols in alle tables
    public void setCode(Integer code) {
        if (code == 0)
            code = null;
        this.code = code;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public void setNationalNumber(String nationalNumber) {
        //TODO Check
        this.nationalNumber = nationalNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isPhotoVisible() {
        return photoVisible;
    }

    public void setPhotoVisible(boolean photoVisible) {
        this.photoVisible = photoVisible;
    }

    public HigherFunction getHigherFunction() {
        return higherFunction;
    }

    public void setHigherFunction(HigherFunction higherFunction) {
        this.higherFunction = higherFunction;
    }

    public String getStamnr() {
        return stamnr;
    }

    public void setStamnr(String stamnr) {
        this.stamnr = stamnr;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Address getAddressAdmin() {
        return addressAdmin;
    }

    public void setAddressAdmin(Address addressAdmin) {
        this.addressAdmin = addressAdmin;
    }

    public Address getAddressWork() {
        return addressWork;
    }

    public void setAddressWork(Address addressWork) {
        this.addressWork = addressWork;
    }

    public Approach getApproach() {
        return approach;
    }

    public void setApproach(Approach approach) {
        this.approach = approach;
    }

    public CClass getClazz() {
        return clazz;
    }

    public void setClazz(CClass clazz) {
        this.clazz = clazz;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Statue getStatue() {
        return statue;
    }

    public void setStatue(Statue statue) {
        this.statue = statue;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Organisation getOrg() {
        return org;
    }

    public void setOrg(Organisation org) {
        this.org = org;
    }

    public void addSecurityGroup(SecurityGroup securityGroup) {
        this.securityGroups.add(securityGroup);
    }

    public void removeSecurityGroup(SecurityGroup securityGroup) {
        this.securityGroups.remove(securityGroup);
    }

    public Set<SecurityGroup> getSecurityGroups() {
        return Collections.unmodifiableSet(securityGroups);
    }

    public Boolean hasSecurityGroup(SecurityGroup securityGroup) {
        return this.securityGroups.contains(securityGroup);
    }

}

