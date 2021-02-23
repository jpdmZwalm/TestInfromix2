package be.regie.wiw.model.db.entity;

import javax.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "Service.ExistsNL",
                query = "SELECT s FROM Service s " +
                        "WHERE s.descrNL = :descrNL"),
               @NamedQuery(name = "Service.ExistsFR",
                query = "SELECT s FROM Service s " +
                        "WHERE s.descrFR = :descrFR"),
        @NamedQuery(name = "Service.ExistsCode",
                query = "SELECT s FROM Service s " +
                        "WHERE s.dnstCode = :dnstCode"),
        @NamedQuery(name = "Service.ExistsId",
                query = "SELECT s FROM Service s " +
                        "WHERE s.dnstId = :dnstId"),
        @NamedQuery(name = "Service.oldId",
                query = "SELECT s FROM Service s " +
                        "WHERE s.oldId = :oldId")

})
@Table(name = "service")
//TODO SQL Server aanvaard geen duplicat null in unique
//       indexes = {@Index(name="srv_id_ui",columnList = "srv_dnst_id", unique = true),
//                  @Index(name="srv_code_ui",columnList = "srv_dnst_code", unique = true)})
//CREATE UNIQUE NONCLUSTERED INDEX srv_id_ui
//ON dbo.service (srv_dnst_id)
//WHERE srv_dnst_id IS NOT NULL;

//CREATE UNIQUE NONCLUSTERED INDEX srv_code_ui
//ON dbo.service (srv_code_ui)
//WHERE srv_dnst_code IS NOT NULL;

public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pk_srv_id")
    private Integer id;

    @Column(name="srv_old_id")
    private Integer oldId;

    @Column(name="srv_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    @Column(name="srv_dnst_id", nullable = true)
    private Integer dnstId;

    @Column(name="srv_dnst_code", nullable = true, columnDefinition = "CHAR(6)") //CHANGED : 5 => 6
    private Integer dnstCode;

    //TODO was 50 => 70
    @Column(name = "srv_descr_nl", nullable = false, columnDefinition = "NCHAR(70)")
    private String descrNL;

    @Column(name = "srv_descr_fr", nullable = false, columnDefinition = "NCHAR(70)")
    private String descrFR;

    //TODO is tijdelijk optional = true
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_srv_adr_id",
                foreignKey = @ForeignKey(name="srv_adr_id_FK"))
    private Address address;

    //TODO is tijdelijk optional = true
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_srv_org_id",
                foreignKey = @ForeignKey(name="srv_org_id_FK"))
    private Organisation org;

    //TODO Service-PersonHead en Person-Service : 2 * not null
    //Foreign Key nodig omdat er een wederzijdes foreign key kan gedropt worden
    //TODO JPA 2.1
    //TODO is tijdelijk optional = true
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_srv_pe_id_head",
                foreignKey = @ForeignKey(name="srv_pe_hoofd_FK"))
    private Person head;

    //TODO is tijdelijk optional = true
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_srv_srv_id_higher",
                foreignKey = @ForeignKey(name="srv_srv_id_higher_FK"))
    private Service higherService;

    public Service() {
    }

    public Service(Integer dnstId,  Integer dnstCode, String descrNL, String descrFR,
                   Address address, Organisation org, Person head,    Service higherService) {
        if (dnstId == 0)
            dnstId = null;
        this.dnstId = dnstId;
        if (dnstCode == 0)
            dnstCode = null;
        this.dnstCode = dnstCode;
        this.descrNL = descrNL;
        this.descrFR = descrFR;
        this.address = address;
        this.org = org;
        this.head = head;
        this.higherService = higherService;
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

    public Integer getDnstId() {
        return dnstId;
    }

    public void setDnstId(Integer dnstId) {
        if ((dnstId != null) && dnstId.equals(0))
            dnstId = null;
        this.dnstId = dnstId;
    }

    public Integer getDnstCode() {
        return dnstCode;
    }

    public void setDnstCode(Integer dnstCode) {
        if ((dnstCode != null) && dnstCode.equals(0))
            dnstCode = null;
        this.dnstCode = dnstCode;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Organisation getOrg() {
        return org;
    }

    public void setOrg(Organisation org) {
        this.org = org;
    }

    public Person getHead() {
        return head;
    }

    public void setHead(Person head) {
        this.head = head;
    }

    public Service getHigherService() {
        return higherService;
    }

    public void setHigherService(Service higherService) {
        this.higherService = higherService;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", oldId=" + oldId +
                ", oldSource='" + oldSource + '\'' +
                ", dnstId=" + dnstId +
                ", dnstCode=" + dnstCode +
                ", descrNL='" + descrNL + '\'' +
                ", descrFR='" + descrFR + '\'' +
                ", address=" + address +
                ", org=" + org +
                ", head=" + head +
                ", higherService=" + higherService +
                '}';
    }
}
