package be.regie.wiw.model.dbold;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class AbstractDaoOld {
    protected Connection connection;

    public AbstractDaoOld(Connection connection) {
        this.connection = connection;
    }

    public abstract String getTableName();
    public abstract List<Map<String, ColumnDescriptor>> getAll();

    protected Integer intFromChar(ResultSet rs, String colName) throws SQLException {
        String tmp = rs.getString(colName);
        if (tmp == null)
            return null;
        else if (tmp.isBlank())
            return null;
        else
            return Integer.parseInt(tmp);
    }

}