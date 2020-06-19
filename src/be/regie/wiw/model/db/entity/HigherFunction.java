package be.regie.wiw.model.db.entity;

import javax.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "HigherFunction.hf_omschr_nl",
        query = "SELECT h FROM HigherFunction h " +
                "WHERE h.shortNL = :hf_omschr_nl"),
        @NamedQuery(name = "HigherFunction.hf_omschr_fr",
        query = "SELECT h FROM HigherFunction h " +
        "WHERE h.shortFR = :hf_omschr_fr"),
})
@Table(name = "higherFunction")
public class HigherFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_hf_id")
    private Integer id;

    @Column(name = "hf_short_nl", nullable = false, columnDefinition = "NCHAR(5)")
    private String shortNL;

    @Column(name = "hf_short_fr", nullable = false, columnDefinition = "NCHAR(5)")
    private String shortFR;

    public HigherFunction() {
    }

    public HigherFunction(String shortNL, String shortFR) {
        this.shortNL = shortNL;
        this.shortFR = shortFR;
    }

    public Integer getId() {
        return id;
    }

    public String getShortNL() {
        return shortNL;
    }

    public void setShortNL(String shortNL) {
        this.shortNL = shortNL;
    }

    public String getShortFR() {
        return shortFR;
    }

    public void setShortFR(String shortFR) {
        this.shortFR = shortFR;
    }

}
