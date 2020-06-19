package be.regie.wiw.model.dbold;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressDaoOld extends AbstractDaoOld {
    private static final String TABLENAME = "werkadres";
    private static final String SQL_GETALL = "Select * from " + TABLENAME;

    public AddressDaoOld(Connection connection) {
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
                String colName = "wrk_id";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                colName = "wrk_cmplxid";
                item.put(colName, new ColumnDescriptor("INT", intFromChar(rs, colName)));
                colName = "wrk_naam_nl";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "wrk_naam_fr";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "wrk_adres_nl";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "wrk_adres_fr";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "wrk_huisnr";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "wrk_postcode";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "wrk_gemeente_nl";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "wrk_gemeente_fr";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                resultList.add(item);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultList;
    }

}