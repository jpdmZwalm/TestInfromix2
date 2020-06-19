package be.regie.wiw.web.listener;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author De Mets JP
 */
public class DbConnect implements ServletContextListener {
    //Old Informix Server
    private String jdbcDriverOld;
    private String urlOld;
    private String userOld;
    private String passwordOld;
    private Connection dbConnectionOld;

    //New Server
    private String jdbcDriverNew;
    private String urlNew;
    private String userNew;
    private String passwordNew;
    private Connection dbConnectionNew;

    public Connection getDbConnectionOld() {
        return dbConnectionOld;
    }

    public Connection getDbConnectionNew() {
        return dbConnectionNew;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (sce != null)
            getParametersFromServletContext(sce);
        else
            getParametersFromCode();

        dbConnectionOld = createOldConnection();
        dbConnectionNew = createNewConnection();
        if (sce != null) {
            sce.getServletContext().setAttribute("databaseOld", dbConnectionOld);
            sce.getServletContext().setAttribute("databaseNew", dbConnectionNew);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Connection dbConnection = (Connection) sce.getServletContext().getAttribute("databaseOld");
        try {
            dbConnection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        dbConnection = (Connection) sce.getServletContext().getAttribute("databaseNew");
        try {
            dbConnection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void getParametersFromServletContext(ServletContextEvent sce) {
        //Init parameters ophalen
        ServletContext context = sce.getServletContext();
        jdbcDriverOld      = context.getInitParameter("jdbcDriverOld");
        urlOld             = context.getInitParameter("dbUrlOld");
        userOld            = context.getInitParameter("dbuser_nameOld");
        passwordOld        = context.getInitParameter("dbpasswordOld");
        urlOld = urlOld + ";user=" + userOld + ";password=" + passwordOld;

        jdbcDriverNew      = context.getInitParameter("jdbcDriverNew");
        urlNew             = context.getInitParameter("dbUrlNew");
        userNew            = context.getInitParameter("dbuser_nameNew");
        passwordNew        = context.getInitParameter("dbpasswordNew");
        urlNew = urlNew + ";user=" + userNew + ";password=" + passwordNew;
    }

    /**
     * Voor JUnit Test
     */
    private void getParametersFromCode() {
        jdbcDriverOld      = "com.informix.jdbc.IfxDriver";
        urlOld             = "jdbc:informix-sqli://acifxpro01:49031/wiw:INFORMIXSERVER=ifx_pro_01_i1";
        userOld            = "jpdemets";
        passwordOld        = "j5d3s4";
        urlOld = urlOld + ";user=" + userOld + ";password=" + passwordOld;

        jdbcDriverNew      = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        urlNew             = "jdbc:sqlserver://acmssqldev:1433;databaseName=WIW2_Dev";
        userNew            = "wiw2";
        passwordNew        = "wiw20200504#";
        urlNew = urlNew + ";user=" + userNew + ";password=" + passwordNew;
    }

    private Connection createOldConnection() {
        //Het database Object aanmaken
        Connection dbConnection = null;
        try {
            Class.forName(jdbcDriverOld);
            dbConnection = DriverManager.getConnection(urlOld);
            System.out.println("Created Old DB Connection....");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dbConnection;
    }

    private Connection createNewConnection() {
        //Het database Object aanmaken
        Connection dbConnection = null;
        try {
            Class.forName(jdbcDriverNew);
            dbConnection = DriverManager.getConnection(urlNew);
            System.out.println("Created New DB Connection....");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dbConnection;
    }

}
