package be.regie.wiw.model.dbold;

import be.regie.wiw.util.Vertaal;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class PersonsDaoOld extends AbstractDaoOld {
    private static PersonsDaoOld instance = null;
    private static final String TABLENAME = "persons";
    private static final String SQL_GETALL =
            "Select * from " + TABLENAME;
    private List<Map<String, ColumnDescriptor>> data = null;

    public static PersonsDaoOld getInstance(Connection connection) {
        if (instance == null) {
            instance = new PersonsDaoOld(connection);
        }
        return instance;
    }

    private PersonsDaoOld(Connection connection) {
        super(connection);
    }

    @Override
    public String getTableName() {
        return TABLENAME;
    }

    @Override
    public List<Map<String, ColumnDescriptor>> getAll() {
        String[] stringCols = {"pe_naam",      "pe_vnaam",     "pe_taal",         "pe_telnr",        "pe_gsm",
                               "pe_email",     "pe_werkgever", "pe_hf_omschr_nl", "pe_hf_omschr_fr", "pe_foto",
                               "pe_adm_adres", "pe_adm_gemeente"};
        String[] intCols    = {"pe_id", "pe_foto_visible", "pe_as_vorm", "pe_titel", "pe_srv_id", "pe_code",
                               "pe_wrk_id"};
        if (data == null) {
            data = new ArrayList<>();
            Vertaal vertaal = Vertaal.getInstance();
            Map<String, String> vertaalGraadMapNL = vertaal.getMapGraadNL();
            Map<String, String> vertaalGraadMapFR = vertaal.getMapGraadFR();
            Map<String, String> vertaalFctMapNL = vertaal.getMapFctNL();
            Map<String, String> vertaalFctMapFR = vertaal.getMapFctFR();
            try {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(SQL_GETALL);
                while (rs.next()) {
                    Map<String, ColumnDescriptor> item = new HashMap<>();
                    for (String colName : intCols) {
                        item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                    }
                    for (String colName : stringCols) {
                        item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                    }

                    processVak(null, null, rs, item);
                    processDegree(vertaalGraadMapNL, vertaalGraadMapFR, rs, item);
                    processFunction(vertaalFctMapNL, vertaalFctMapFR, rs, item);
                    processOrganisation(null, null, rs, item);
                    data.add(item);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return data;
    }

    private void processVak(Map<String, String> vertaalMapNL, Map<String, String> vertaalMapFR,
                            ResultSet rs, Map<String, ColumnDescriptor> item) throws SQLException {
        //TODO Vak => Klasse
        String colName = "pe_vak_komschr_nl";
        item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
        colName = "pe_vak_omschr_nl";
        item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
        colName = "pe_vak_komschr_fr";
        item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
        colName = "pe_vak_omschr_fr";
        item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
    }

    private void processOrganisation(Map<String, String> vertaalMapNL, Map<String, String> vertaalMapFR,
                                     ResultSet rs, Map<String, ColumnDescriptor> item) throws SQLException {
        String colName = "pe_org_id";
        item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
        colName = "pe_org_komschr_nl";
        item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
        colName = "pe_org_omschr_nl";
        item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
        colName = "pe_org_komschr_fr";
        item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
        colName = "pe_org_omschr_fr";
        item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
    }


    private void processFunction(Map<String, String> vertaalMapNL, Map<String, String> vertaalMapFR,
                                 ResultSet rs, Map<String, ColumnDescriptor> item) throws SQLException {
        //Fct => Function
        String colNameNL = "pe_fct_omschr_nl";
        String colNameFR = "pe_fct_omschr_fr";
        String dataNL = rs.getString(colNameNL);
        String dataFR = rs.getString(colNameFR);
        if ((dataNL != null) && (dataFR != null)) {
            dataNL = convertString(dataNL, true, true);
            dataFR = convertString(dataFR, true, true);

            if (dataNL.equals("Collaboratrice Presse-Com") && dataFR.equals("Collaboratrice")) {
                dataFR = dataNL;
            }

            if (dataFR.equalsIgnoreCase("Frans") || dataFR.equalsIgnoreCase("Nederlands")) {
                dataFR = dataNL;
            }
            dataNL = vertaalMapNL.getOrDefault(dataNL, dataNL);
            dataFR = vertaalMapFR.getOrDefault(dataFR, dataFR);
        }
        item.put(colNameNL, new ColumnDescriptor("STR", dataNL));
        item.put(colNameFR, new ColumnDescriptor("STR", dataFR));
    }

    /*
     *  Als pe_grd_omschr_fr = "Frans"
     * => Nederlands is eigenlijk Frans
     */
    private void processDegree(Map<String, String> vertaalMapNL, Map<String, String> vertaalMapFR,
                               ResultSet rs, Map<String, ColumnDescriptor> item) throws SQLException {
        String colNameNL = "pe_grd_omschr_nl";
        String colNameFR = "pe_grd_omschr_fr";
        String dataNL = rs.getString(colNameNL);
        String dataFR = rs.getString(colNameFR);
        dataNL = convertString(dataNL, true, true);
        dataFR = convertString(dataFR, true, true);
        if (dataFR.equalsIgnoreCase("Frans") || dataFR.equalsIgnoreCase("Nederlands")) {
            dataFR = dataNL;
        }
        dataNL = vertaalMapNL.getOrDefault(dataNL, dataNL);
        dataFR = vertaalMapFR.getOrDefault(dataFR, dataFR);
        item.put(colNameNL, new ColumnDescriptor("STR", dataNL));
        item.put(colNameFR, new ColumnDescriptor("STR", dataFR));
    }

    /**
     * Trim en Capitalize
     * @param string
     * @param trim
     * @param cap
     * @return
     */
    private String convertString(String string, boolean trim, boolean cap) {
        if (string == null) {
            return string;
        }
        if (trim) {
            string = string.trim();
        }
        if (cap && !string.startsWith("wd. ")) {
            string = StringUtils.capitalize(string);
        }
        return string;
    }

}


