package be.regie.wiw.model.db.entity;

import javax.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "Function.ExistsNL",
                query = "SELECT f FROM Function f " +
                        "WHERE f.descrNL = :descrNL"),
        @NamedQuery(name = "Function.ExistsFR",
                query = "SELECT f FROM Function f " +
                        "WHERE f.descrFR = :descrFR")
})
@Table(name = "function_",
        indexes = {@Index(name="fct_nl_UI",columnList = "fct_descr_nl", unique = true),
                @Index(name="fct_fr_UI",columnList = "fct_descr_fr", unique = true)})

public class Function {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_fct_id")
    private Integer id;

    @Column(name="fct_old_id")
    private Integer oldId;

    @Column(name="fct_old_source", columnDefinition = "CHAR(20)")
    private String oldSource;

    @Column(name = "fct_descr_nl", nullable = false, columnDefinition = "NCHAR(50)")
    private String descrNL;

    @Column(name = "fct_descr_fr", nullable = false, columnDefinition = "NCHAR(50)")
    private String descrFR;

    public Function() {
    }

    public Function(String descrNL, String descrFR) {
        this.descrNL = descrNL;
        this.descrFR = descrFR;
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
}
