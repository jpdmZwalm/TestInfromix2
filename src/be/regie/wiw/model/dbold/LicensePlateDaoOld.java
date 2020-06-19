package be.regie.wiw.model.dbold;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LicensePlateDaoOld extends AbstractDaoOld {
    private static final String TABLENAME = "licenseplates";
    private static final String SQL_GETALL = "Select * from " + TABLENAME;

    public LicensePlateDaoOld(Connection connection) {
        super(connection);
    }

    @Override
    public String getTableName() {
        return TABLENAME;
    }
    @Override
    public List<Map<String, ColumnDescriptor>> getAll() {
        List<Map<String, ColumnDescriptor>> resultList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SQL_GETALL);
            while (rs.next())  {
                Map<String, ColumnDescriptor> item = new HashMap<>();
                String colName = "lp_id";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                colName = "lp_pe_id";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                colName = "lp_licenseplate";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "lp_brand";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "lp_type";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "lp_upd";
                item.put(colName, new ColumnDescriptor("DATE", rs.getDate(colName)));
                colName = "lp_upd_pe_code";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                resultList.add(item);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultList;
    }
}
