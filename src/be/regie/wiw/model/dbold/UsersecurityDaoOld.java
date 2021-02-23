package be.regie.wiw.model.dbold;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersecurityDaoOld extends AbstractDaoOld {
    private static final String TABLENAME = "usersecurity";
    private static final String SQL_GETALL = "Select * from " + TABLENAME;

    public UsersecurityDaoOld(Connection connection) {
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
                String colName = "uss_userid";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "uss_group";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "uss_privacy";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                colName = "uss_statistics";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                colName = "uss_windowsaccount";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "uss_peid";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                resultList.add(item);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultList;
    }
}
