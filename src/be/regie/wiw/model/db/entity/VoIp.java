package be.regie.wiw.model.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "voip")
public class VoIp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pk_voi_id")
    private Integer id;

    @Column(name="voi_old_id")
    private Integer oldId;

    @Column(name="voi_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    @Column(name="voi_visible")
    private Boolean visible;

    @Column(name = "voi_group", nullable = false)
    private Integer group;

    @Column(name = "voi_profile", nullable = false)
    private Integer profile;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_voi_pe_id")
    private Person person;

    public VoIp() {    }

    public VoIp(Boolean visible, Integer group, Integer profile, Person person) {
        this.visible = visible;
        this.group = group;
        this.profile = profile;
        this.person = person;
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

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getProfile() {
        return profile;
    }

    public void setProfile(Integer profile) {
        this.profile = profile;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
