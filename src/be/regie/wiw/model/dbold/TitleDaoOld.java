package be.regie.wiw.model.dbold;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TitleDaoOld extends AbstractDaoOld {
    private static final String TABLENAME = "titel";
    private static final String SQL_GETALL = "Select * from " + TABLENAME;

    public TitleDaoOld(Connection connection) {
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
                String colName = "titel_code";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                colName = "titel_omschr_nl";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "titel_omschr_fr";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "titel_komschr_nl";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "titel_komschr_fr";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                resultList.add(item);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultList;
    }
}
