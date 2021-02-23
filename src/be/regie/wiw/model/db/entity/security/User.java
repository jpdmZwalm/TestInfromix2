package be.regie.wiw.model.db.entity.security;

import be.regie.wiw.model.db.entity.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="securityUser",
        indexes = {@Index(name = "usr_name_UI", columnList = "usr_userName", unique = true)})
                   //@Index(name = "usr_email_UI", columnList = "email", unique = true)}) //TODO

@Getter @Setter
public class User  {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pk_usr_id")
    private Integer id;

    @JsonIgnore
    @Column(name="usr_old_id")
    private Integer oldId;

    @JsonIgnore
    @Column(name="usr_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    //TODO van usersecurity.windowsaccount
    //@NotBlank @Size(max = 20)
    //usersecurity.userid is usersecurity.windowsaccount in kleine letters
    @Column(name="usr_userName", columnDefinition = "CHAR(20)")
    private String userName; //Is windowsaccount

    //@Size(max = 50) @Email
    @Column(name="usr_email", columnDefinition = "VARCHAR(255)")
    private String email;

    //@NotBlank @Size(max = 120)
    @Column(name="usr_password", columnDefinition = "VARCHAR(255)")
    private String password;

    //TODO nodig ? van usersecurity.group
    @Column(name="usr_group", nullable = true)
    private Integer group; //  : 1

    //TODO nodig ? van usersecurity.privacy
    @Column(name="usr_privacy", nullable = true)
    private Integer privacy; // : 0

    //TODO nodig ? van usersecurity.statistics
    @Column(name="usr_statistics", nullable = true)
    private Integer statistics; // : 0

    @OneToOne(optional = false)
    @JoinColumn(name = "fk_us_pe_id")
    //@JsonSerialize(using = ChildAsIdOnlySerializer.class)
    private Person person;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "securityUserRole",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(String userName,
                String email,
                String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(String userName, String email, Integer group,
                Integer privacy, Integer statistics, Person person) {
        this.userName = userName;
        this.email = email;
        this.group = group;
        this.privacy = privacy;
        this.statistics = statistics;
        this.person = person;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", oldId=" + oldId +
                ", oldSource='" + oldSource + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", group=" + group +
                ", privacy=" + privacy +
                ", statistics=" + statistics +
                ", person=" + person +
                ", roles=" + roles +
                '}';
    }
}
/*
select * from usersecurity us
join persons pe on pe.pe_id = us.uss_peid
where us.uss_windowsaccount like '%Mets%'
uss_userid : regie\de mets jp
uss_group  : 1
uss_privacy : 0
uss_statistics : 0
uss_windowsaccount : REGIE\De Mets JP
uss_peid : 4875
 */

