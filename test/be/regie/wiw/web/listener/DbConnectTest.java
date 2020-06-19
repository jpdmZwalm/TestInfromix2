package be.regie.wiw.web.listener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DbConnectTest {
    private static DbConnect dbConnect;
    private static Connection connectionOld;
    private static Connection connectionNew;

    @org.junit.jupiter.api.BeforeAll
    static void setUp() {
        dbConnect = new DbConnect();
        dbConnect.contextInitialized(null);
        connectionOld = dbConnect.getDbConnectionOld();
        connectionNew = dbConnect.getDbConnectionNew();
    }

    @org.junit.jupiter.api.AfterAll
    static void tearDown() {
        try {
            connectionOld.close();
            connectionNew.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*
    @org.junit.jupiter.api.Test
    private void test() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WIWPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        AddressDao dao = new AddressDao(em);
        Address address = new Address("buildingNameNL", "buildingNameFR",
                "addressNL", "addressFR", "BE-1000",
                "Brussel", "Bruxelles", "02");
        dao.persist(address);
        Integer id = address.getId();
        assertNotNull(id, "id is null");
    }

     */

    @org.junit.jupiter.api.Test
    void connectOldServer() {
        assertNotNull(connectionOld, "Connection is null");
        String sql = "SELECT * FROM persons WHERE pe_id = 4875";
        try {
            Statement stmt = connectionOld.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            assertTrue(rs.next(), "pe_id = 4875 not found");

            String naamExp  = "De Mets";
            String naamSrv  = rs.getString("pe_naam").trim();
            assertEquals(naamExp, naamSrv, "Naam is " + naamExp);

            String vnaamExp = "Jean-Pierre";
            String vnaamSrv = rs.getString("pe_vnaam").trim();
            assertEquals(vnaamExp, vnaamSrv, "VoorNaam is " + vnaamExp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void connectNewServer() {
        assertNotNull(connectionNew, "Connection is null");
        String sql = "SELECT * FROM person WHERE pe_id = 4875";

        /*
Insert into person (pe_id, pe_code, pe_name,   pe_fname,      pe_language, pe_telNr, pe_compTelNr, pe_mobile, pe_email, ro_id, ti_id, app_id, pe_lastchanged, adr_id_adm, adr_id_wrk, st_id)
            VALUES (4875,  '000',   'De Mets', 'Jean-Pierre', 'NL',        '055',    '02',         '0486',    'x@y',    0,     1,     2,      getdate(),      3,          4,          5)
*/


        try {
            Statement stmt = connectionNew.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            assertTrue(rs.next(), "pe_id = 4875 not found");

            String naamExp  = "De_Mets";
            String naamSrv  = rs.getString("pe_name").trim();
            assertEquals(naamExp, naamSrv, "Naam is " + naamExp);

            String vnaamExp = "Jean_Pierre";
            String vnaamSrv = rs.getString("pe_fname").trim();
            assertEquals(vnaamExp, vnaamSrv, "VoorNaam is " + vnaamExp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}