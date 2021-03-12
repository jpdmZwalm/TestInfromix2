package be.regie.wiw.model.db.entity;

import be.regie.wiw.model.db.entity.security.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = "Person.ExistsCode",
                query = "SELECT p FROM Person p " +
                        "WHERE p.code = :code"),
        @NamedQuery(name = "Person.ExistsName",
                query = "SELECT p FROM Person p " +
                        "WHERE p.name = :name " +
                        "  AND p.fname = :fname"),
        @NamedQuery(name = "Person.oldId",
                query = "SELECT p FROM Person p " +
                        "WHERE p.oldId = :oldId"),
        @NamedQuery(name = "Person.serviceHead",
                query = "SELECT p FROM Person p " +
                        "JOIN Service s ON p.id = s.head.id " +
                        "WHERE s.id = :serviceId"),
        @NamedQuery(name = "Person.email",
                query = "SELECT p FROM Person p " +
                        "WHERE p.email = :email")
})
@Table(name = "person",
        indexes = {@Index(name = "pe_name_UI", columnList = "pe_name, pe_fname", unique = true)})
//TODO SQL Server aanvaard geen duplicate null in unique
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

    @Column(name="pe_room", columnDefinition = "NVARCHAR(60)")
    private String room; //CHANGED : NEW

    @Column(name="pe_photo", columnDefinition = "NVARCHAR(70)")
    private String photo; //was BLOB,

    @Column(name="pe_photoVisible")
    private boolean photoVisible;
    
    @Column(name="pe_external") //CHANGED : NEW
    private boolean external;

    //TODO Nodig om dit nog in code te doen?
    @Generated(GenerationTime.ALWAYS)
    @Column(name="pe_updated", nullable = false, columnDefinition="DATETIME DEFAULT GETDATE()")
    @Temporal(TemporalType.DATE)
    private Date updated;


    //TODO DATABASE :  pe_stamnr moet weg bij hergenereren database
    //TODO DATABASE :  fk_pe_adr_id_adm moet weg bij hergenereren database, is het adres van de service
    //TODO DATABASE :  alter table "dbo"."person" alter column fk_pe_adr_id_adm int not null
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_pe_adr_id_adm")
    //TODO DATABASE :  is adres van de service
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

    /*CHANGED : IS WEG
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_pe_ro_id")
    private Room room;
     */

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_pe_st_id")
    private Statute statute;

    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_pe_ti_id")
    private Title title;


    //TODO Service-PersonHead en Person-Service : 2 * not null
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_pe_srv_id")
    private Service service;

    // TODO bepaald door de service, maar veel werk in de client
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_pe_org_id")
    private Organisation org;

    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_pe_spe_id")
    private Speciality speciality;  //CHANGED : NEW

    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_pe_hf_id")
    private HigherFunction higherFunction;  //CHANGED : NEW

    @JsonIgnore
    @OneToOne(optional = true, mappedBy = "person")
    private User user;

    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_pe_dh")
    private Person dienstHoofd;

    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_pe_fc")
    private Person functieChef;

    @ManyToMany()
    @JoinTable(
            name = "person_secgrp",
            joinColumns = @JoinColumn(name = "fk_psg_pe_id"),
            inverseJoinColumns = @JoinColumn(name = "fk_psg_sg_id"))
    Set<SecurityGroup> securityGroups;

    public Person() {
        this.securityGroups = new HashSet<>();  //CHANGED : NEW
    	}



    public Person(Integer code, String nationalNumber, String name, String fname, String language,
                  String tel, String mobile, String email, String employer, String photo,
                  boolean photoVisible,
                  //CHANGED : WEG : HigherFunction higherFunction,
                  //CHANGED : WEG : String stamnr,
                  Date updated,
                  Address addressAdmin, Address addressWork,
                  Approach approach, CClass clazz, Function function, Degree degree,
                  String room, //CHANGED : NEW
                  //CHANGED : WEG : Room room,
                  Statute statute, Title title, Service service, Organisation org,
                  Speciality speciality, //CHANGED : NEW
                  HigherFunction higherFunction) { //CHANGED : NEW
        this(); //CHANGED : NEW
        setCode(code); //CHANGED : NEW
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
        this.updated = updated;
        this.addressAdmin = addressAdmin;
        this.addressWork = addressWork;
        this.approach = approach;
        this.clazz = clazz;
        this.function = function;
        this.degree = degree;
        this.room = room;
        this.statute = statute;
        this.title = title;
        this.service = service;
        this.org = org;
        this.speciality = speciality; //CHANGED : NEW
        this.higherFunction = higherFunction; //CHANGED : NEW
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

    public boolean isExternal() { //CHANGED : NEW
        return external;
    }

    public void setExternal(boolean external) { //CHANGED : NEW
        this.external = external;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Statute getStatute() {
        return statute;
    }

    public void setStatute(Statute statute) {
        this.statute = statute;
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

    public Speciality getSpeciality() { //CHANGED : NEW
        return speciality;
    }

    public void setSpeciality(Speciality speciality) { //CHANGED : NEW
        this.speciality = speciality;
    }

    public HigherFunction getHigherFunction() { //CHANGED : NEW
        return higherFunction;
    }

    public void setHigherFunction(HigherFunction higherFunction) { //CHANGED : NEW
        this.higherFunction = higherFunction;
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

    public User getUser() {
        return user;
    }
}

