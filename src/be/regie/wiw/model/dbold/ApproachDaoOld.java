package be.regie.wiw.model.dbold;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApproachDaoOld extends AbstractDaoOld {
    private static final String TABLENAME = "aformule";
    private static final String SQL_GETALL = "Select * from " + TABLENAME;

    public ApproachDaoOld(Connection connection) {
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
                String colName = "af_code"; //TODO dit is char in plaats van int
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                colName = "af_afk_nl";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "af_afk_fr";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "af_afk_du";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "af_omschr_nl";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "af_omschr_fr";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "af_omschr_du";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                resultList.add(item);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultList;
    }
}
