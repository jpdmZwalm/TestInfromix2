package be.regie.wiw.model.dbold;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DienstHoofdDaoOld extends AbstractDaoOld {
    private static final String TABLENAME = "pp_diensthoofd";
    private static final String SQL_GETALL = "Select * from " + TABLENAME;
    private static final String SQL_GETBYID =
            SQL_GETALL + " WHERE pe_id = ?";

    private PreparedStatement stmtGetById;

    public DienstHoofdDaoOld(Connection connection) {
        super(connection);
        try {
            stmtGetById = connection.prepareStatement(SQL_GETBYID);
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
        List<Map<String, ColumnDescriptor>> resultList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(SQL_GETALL);
            while (rs.next())  {
                Map<String, ColumnDescriptor> item = new HashMap<>();
                String colName = "pe_id";
                item.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                colName = "diensthoofd_email";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "functchef_email";
                item.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                resultList.add(item);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultList;
    }

    public Map<String, ColumnDescriptor> getById(Integer pe_id) {
        Map<String, ColumnDescriptor> result = new HashMap<>();
        try {
            stmtGetById.setInt(1, pe_id);
            ResultSet rs = stmtGetById.executeQuery();
            if (rs.next())  {
                String colName = "pe_id";
                result.put(colName, new ColumnDescriptor("INT", rs.getInt(colName)));
                colName = "diensthoofd_email";
                result.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
                colName = "functchef_email";
                result.put(colName, new ColumnDescriptor("STR", rs.getString(colName)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

}
