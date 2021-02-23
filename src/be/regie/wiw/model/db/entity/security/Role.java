package be.regie.wiw.model.db.entity.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="securityRole")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pk_ro_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name="ro_name", columnDefinition = "VARCHAR(20)")
    private ERole name;

    public Role() {}

    public Role(ERole name) {
        this.name = name;
    }
}


