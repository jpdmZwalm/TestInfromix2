package be.regie.wiw.model.dbold;

import java.util.Date;

public class ColumnDescriptor {
    private String colType;
    private Object colValue;

    public ColumnDescriptor(String colType, Object colValue) {
        this.colType = colType;
        if (colValue instanceof String) {
            colValue = ((String) colValue).trim();
        }
        this.colValue = colValue;
    }

    public Integer getIntValue() throws DataTypeException {
        if (colValue == null) {
            return null;
        }
        if (colType.equals("INT")) {
            return (Integer) (colValue);
        }
        throw new DataTypeException("INT Value expected : " + colValue.toString());
    }

    public String getStrValue() throws DataTypeException {
        if (colValue == null) {
            return null;
        }
        if (colType.equals("STR")) {
            return colValue.toString();
        }
        throw new DataTypeException("STR Value expected : " + colValue.toString());
    }

    public String getStrValue(String defaultStr) throws DataTypeException {
        if (colValue == null) {
            return defaultStr;
        }
        if (colType.equals("STR")) {
            return colValue.toString();
        }
        throw new DataTypeException("STR Value expected : " + colValue.toString());
    }

    public Date getDateValue() throws DataTypeException {
        if (colValue == null) {
            return null;
        }
        if (colType.equals("DATE")) {
            return (Date) colValue;
        }
        throw new DataTypeException("STR Value expected : " + colValue.toString());
    }


    public class DataTypeException extends Throwable {
        public DataTypeException(String message) {
            super(message);
        }
    }

}