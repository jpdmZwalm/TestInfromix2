package be.regie.wiw.model.dbold;

import be.regie.wiw.model.db.entity.Service;
import be.regie.wiw.util.Vertaal;

import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceDaoOld extends AbstractDaoOld {
    private static final String TABLENAME = "service";
    private static final String SQL_GETALL = "Select * from " + TABLENAME;
    private static final String SQL_GETBY_ID = "Select * from " + TABLENAME + " WHERE srv_id = ?";
    private PreparedStatement stmtGetById;

    public ServiceDaoOld(Connection connection)  {
        super(connection);
        try {
            stmtGetById = connection.prepareStatement(SQL_GETBY_ID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public String getTableName() {
        return TABLENAME;
    }

    @Override
    public List<Map<String, ColumnDescriptor>> getAll() {
        Vertaal vertaal = Vertaal.getInstance();
        Map<String, String> vertaalOrgMapNL = vertaal.getMapOrgShortNL();
        Map<String, String> vertaalOrgMapFR = vertaal.getMapOrgShortFR();

        List<Map<String, ColumnDescriptor>> resultList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SQL_GETALL);
            while (rs.next())  {
                Map<String, ColumnDescriptor> item = new HashMap<>();
                String colName = "srv_id";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                colName = "srv_dnst_id";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                colName = "srv_dnst_code";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                colName = "srv_omschr_nl";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "srv_omschr_fr";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));

                colName = "srv_org_id";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));

                colName = "srv_org_komschr_nl";
                String org_komschr_nl = rs.getString(colName).trim();
                org_komschr_nl = vertaalOrgMapNL.getOrDefault(org_komschr_nl, org_komschr_nl);
                item.put(colName, new ColumnDescriptor("STR", org_komschr_nl));

                colName = "srv_org_komschr_fr";
                String org_komschr_fr = rs.getString(colName).trim();
                org_komschr_fr = vertaalOrgMapNL.getOrDefault(org_komschr_fr, org_komschr_fr);
                item.put(colName, new ColumnDescriptor("STR", org_komschr_fr));

                colName = "srv_org_omschr_nl";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "srv_org_omschr_fr";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));

                colName = "srv_hoofd_pe_id";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));


                //TODO is tabel werkAdres
                colName = "srv_adm_id";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));

                colName = "srv_telnr";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "srv_lokaal";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "srv_hogere_dienst";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                resultList.add(item);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultList;
    }

    public Integer findOldXXXId(String oldIdName, int serviceId) throws Exception {
        stmtGetById.setInt(1, serviceId);
        ResultSet resultSet = stmtGetById.executeQuery();
        Integer oldXXXId =  null;
        if (resultSet.next()) {
            oldXXXId =  resultSet.getInt(oldIdName); //"srv_hoofd_pe_id",  srv_hogere_dienst
            if (oldXXXId == 0)
                oldXXXId = null;
            if (resultSet.next())
                throw new Exception("Too many results");
        } else {
            throw new Exception("Not found");
        }
        return oldXXXId;
    }

}


