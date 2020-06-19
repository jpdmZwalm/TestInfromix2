package be.regie.wiw.control;

import be.regie.wiw.model.db.dao.*;
import be.regie.wiw.model.db.entity.*;
import be.regie.wiw.model.dbold.*;
import be.regie.wiw.web.listener.DbConnect;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class ConvertMain {
    EntityManagerFactory entityManagerFactory;
    private Connection connWIWOld;

    public static void main(String[] args) {
        ConvertMain convertMain = new ConvertMain();
        convertMain.run();
        convertMain.close();
    }

    /**
     * Anders bijft de applicatie open staan.
     */
    private void close() {
        this.entityManagerFactory.close();
    }

    public ConvertMain() {
        DbConnect dbConnect = new DbConnect();
        dbConnect.contextInitialized(null);
        connWIWOld = dbConnect.getDbConnectionOld();
        entityManagerFactory = Persistence.createEntityManagerFactory("WIWPersistenceUnit");
    }

    private void run() {
        //convertWrkAdressFromPersons();      //TODO Planon?
        //convertAdmAdressFromPersons();
        //convertAdressFromService();
        //convertApproach(); //aFormule
        //makeTransport();
        //convertClass();
        //convertDegree();
        //convertFunction();
        //convertOrganisationFromPersons();
        //convertOrganisationFromService();
        ////convertRoom();
        //convertTitle();
        //makeStatue();
        //convertService();
        //makeHigherFunction();
        //convertPerson();
        //convertLicensePlate();
        //convertUserSecurity();
        //serviceSetAddress();
        //serviceSetHead();
        //serviceSetHigher();
        //personSetAdrAdmin();
        //personSetRoom();
    }

    private void personSetAdrAdmin() {
        //TODO Person : fk_pe_adr_id_adm : manueel doen
    }

    private void personSetRoom() {
        //TODO Person : fk_pe_adr_id_adm : manueel doen : lijst lokalen uit planon
        //pe_lokaal heeft geen link met tabel lokalen
        //fk_pe_ro_id
    }


    //TODO Planon? srv_adm_id, srv_adres_, srv_gemeente
    private void convertAdressFromService() {
    }

    //TODO lp_upd_pe_code is Pe_code, maar verwijst altijd naar hetzelfde als
    //     lp_pe_id is pe_id
    //Is dus niet de updater
    //select lp_pe_id, p.pe_id from licenseplates lp
    //left join persons p on pe_code = lp_upd_pe_code
    private int convertLicensePlate() {
        boolean error = false;
        AbstractDaoOld daoOld = new LicensePlateDaoOld(connWIWOld);
        EntityManager em = entityManagerFactory.createEntityManager();
        AbstractDao2<LicensePlate> daoNew = new LicensePlateDao(em);
        PersonDao personDao = new PersonDao(em);
        int aantal = 0;
        List<Map<String, ColumnDescriptor>> oldList = daoOld.getAll();
        System.out.println(String.format("%5d LicensePlate uit %10s gevonden", oldList.size(), daoOld.getTableName()));
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (Map<String, ColumnDescriptor> oldLicensePlate : oldList) {
                Person owner   = personDao.findField1("pe_old_id", oldLicensePlate.get("lp_pe_id").getIntValue());
                Person updater = personDao.findField1("pe_code", oldLicensePlate.get("lp_upd_pe_code").getIntValue());
                LicensePlate licensePlate = new LicensePlate(
                        oldLicensePlate.get("lp_licenseplate").getStrValue(),
                        oldLicensePlate.get("lp_brand").getStrValue(),
                        oldLicensePlate.get("lp_type").getStrValue(),
                        oldLicensePlate.get("lp_upd").getDateValue(),
                        updater, owner);
                licensePlate.setOldId(oldLicensePlate.get("lp_id").getIntValue());
                licensePlate.setOldSource(daoOld.getTableName());
                if (daoNew.exists(licensePlate)) {
                    System.out.print(".");
                    continue;
                }
                try {
                    daoNew.persist(licensePlate);
                    System.out.print("+");
                    aantal++;
                } catch (PersistenceException ex) {
                    System.out.println("Fout in titel oldId : " + licensePlate.getOldId());
                    System.out.println(ex.getMessage());
                    System.out.println(licensePlate.toString());
                    error = true;
                }
            }
            if (error) {
                tx.rollback();
                aantal = 0;
            } else {
                tx.commit();
                System.out.println(String.format("\n%5d LicensePlate geconverteerd naar %10s", aantal, daoNew.getTableName()));
            }
            return aantal;
        } catch (ColumnDescriptor.DataTypeException e) {
            e.printStackTrace();
            tx.rollback();
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
            return 0;
        } finally {
            em.close();
        }
    }

    private int convertPerson() {
            boolean error = false;
            AbstractDaoOld daoOld = PersonsDaoOld.getInstance(connWIWOld);
            EntityManager em = entityManagerFactory.createEntityManager();
            AbstractDao2<Person> daoNew = new PersonDao(em);
            int aantal = 0;
            List<Map<String, ColumnDescriptor>> oldList = daoOld.getAll();
            System.out.println(String.format("%5d person uit %10s gevonden", oldList.size(), daoOld.getTableName()));
            //OrganisationDao organisationDao = new OrganisationDao(em);
            AddressDao addressDao = new AddressDao(em);
            HigherFunctionDao higherFunctionDao = new HigherFunctionDao(em);
            ApproachDao approachDao = new ApproachDao(em);
            ClassDao classDao       = new ClassDao(em);
            FunctionDao functionDao = new FunctionDao(em);
            DegreeDao   degreeDao   = new DegreeDao(em);
            //RoomDao   roomDao     = new RoomDao(em); //TODO Planon
            StatueDao   statueDao   = new StatueDao(em);
            TitleDao    titleDao    = new TitleDao(em);
            ServiceDao  serviceDao  = new ServiceDao(em);
            OrganisationDao organisationDao = new OrganisationDao(em);

            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                for (Map<String, ColumnDescriptor> oldPerson : oldList) {
                    //Organisation organisation =
                    //        organisationDao.findField1("org_old_org_id", oldService.get("srv_org_id").getIntValue());

                    boolean fotoVisible = oldPerson.get("pe_foto_visible").getIntValue() == 1;

                    HigherFunction higherFunction = findHigherFunction(higherFunctionDao, oldPerson);

                    Address addressAdmin = findAddress(addressDao, oldPerson, true);
                    Address addressWork  = findAddress(addressDao, oldPerson, false);

                    Approach approach = approachDao.findField1("app_old_id", oldPerson.get("pe_as_vorm").getIntValue());

                    CClass clazz = findClass(classDao, oldPerson);

                    Function function = findFunction(functionDao, oldPerson);
//Inspecteur van Financiën stagiair
//Inspecteur des Finances stagiaire

                    Degree degree =  findDegree(degreeDao, oldPerson);

                    //Room room

                    Statue statue = statueDao.find(1); //Active

                    Title title = titleDao.findField1("ti_old_id", oldPerson.get("pe_titel").getIntValue());

                    Service service = serviceDao.findField1("srv_old_id", oldPerson.get("pe_srv_id").getIntValue());

                    Organisation organisation = organisationDao.findField1("org_old_id", oldPerson.get("pe_org_id").getIntValue());

                    Person person =
                            new Person(oldPerson.get("pe_code").getIntValue(),
                                       null, //nationalNumber
                                       oldPerson.get("pe_naam").getStrValue(),
                                       oldPerson.get("pe_vnaam").getStrValue(),
                                       oldPerson.get("pe_taal").getStrValue(),
                                       oldPerson.get("pe_telnr").getStrValue(),
                                       oldPerson.get("pe_gsm").getStrValue(),
                                       oldPerson.get("pe_email").getStrValue(),
                                       oldPerson.get("pe_werkgever").getStrValue(),
                                       oldPerson.get("pe_foto").getStrValue(),
                                       fotoVisible, higherFunction,
                                       null, //stamnr
                                       new Date(),
                                       addressAdmin, addressWork, approach, clazz, function, degree,
                                    null, //room
                                       statue, title, service, organisation);

                    person.setOldId(oldPerson.get("pe_id").getIntValue());
                    person.setOldSource(daoOld.getTableName());
                    if (daoNew.exists(person)) {
                        System.out.print(".");
                        continue;
                    }
                    try {
                        daoNew.persist(person);
                        System.out.print("+");
                        aantal++;
                    } catch (PersistenceException ex) {
                        System.out.println("Fout in person oldId : " + person.getOldId());
                        System.out.println(ex.getMessage());
                        System.out.println(person.toString());
                        error = true;
                    }
                }
                if (error) {
                    tx.rollback();
                    aantal = 0;
                } else {
                    tx.commit();
                    System.out.println(String.format("\n%5d person geconverteerd naar %10s", aantal, daoNew.getTableName()));
                }
                return aantal;
            } catch (ColumnDescriptor.DataTypeException e) {
                e.printStackTrace();
                tx.rollback();
                return 0;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                tx.rollback();
                return 0;
            } finally {
                em.close();
            }
        }

    private Degree findDegree(DegreeDao degreeDao, Map<String, ColumnDescriptor> oldPerson) throws ColumnDescriptor.DataTypeException, Exception {
        switch (oldPerson.get("pe_taal").getStrValue()) {
            case "N" :
                return degreeDao.findField1("deg_descr_nl", oldPerson.get("pe_grd_omschr_nl").getStrValue());
            case "F" :
                return degreeDao.findField1("deg_descr_fr", oldPerson.get("pe_grd_omschr_fr").getStrValue());
            default:
                return null;
        }
    }

    private Function findFunction(FunctionDao functionDao, Map<String, ColumnDescriptor> oldPerson) throws ColumnDescriptor.DataTypeException, Exception {
        switch (oldPerson.get("pe_taal").getStrValue()) {
            case "N" :
                return functionDao.findField1("fct_descr_nl", oldPerson.get("pe_fct_omschr_nl").getStrValue());
            case "F" :
                return functionDao.findField1("fct_descr_fr", oldPerson.get("pe_fct_omschr_fr").getStrValue());
            default:
                return null;
        }
    }

    private CClass findClass(ClassDao classDao, Map<String, ColumnDescriptor> oldPerson) throws ColumnDescriptor.DataTypeException, Exception {
        CClass clazz = classDao.find(oldPerson.get("pe_vak_komschr_nl").getStrValue(),
                                     oldPerson.get("pe_vak_omschr_nl").getStrValue(),
                                     oldPerson.get("pe_vak_komschr_fr").getStrValue(),
                                     oldPerson.get("pe_vak_omschr_fr").getStrValue());
        return clazz;
    }

    private HigherFunction findHigherFunction(HigherFunctionDao higherFunctionDao, Map<String, ColumnDescriptor> oldPerson)
            throws ColumnDescriptor.DataTypeException, Exception {
        HigherFunction higherFunction;
        if (oldPerson.get("pe_taal").getStrValue().equalsIgnoreCase("nl"))
            higherFunction =
                    higherFunctionDao.findField1("hf_omschr_nl", oldPerson.get("pe_hf_omschr_nl").getStrValue());
        else
            higherFunction =
                    higherFunctionDao.findField1("hf_omschr_fr", oldPerson.get("pe_hf_omschr_fr").getStrValue());
        return higherFunction;
    }

    private int convertService() {
                /* TODO :
                   select srv_omschr_nl, srv_omschr_fr, count(*) from service
	                    group by srv_omschr_nl, srv_omschr_fr
	                    having count(*) > 1
                        order by srv_omschr_nl
                   => 7 services zijn dubbel :
                                              : srv_id     : srv_dnst_id : srv_dnst_code  : srv_hogere_dienst
                    PO-Log Die                : 166 en 385 : 263 en 544  : 11240 en 11265 : 164 en 238
                    PO-Log Die-Alg Za-Bodes   : 184 en 305 : 193 en 465  : 11245 en 11284 : 181 en 386
                    PO-Log Die-Alg Za-Onth    : 180 en 308 : 191 en 468  : 11243 en 11287 : 181 en 386
                    PO-Log Die-Alg Za-Schoonm : 185 en 307 : 192 en 467  : 11244 en 11286 : 181 en 386
                    PO-Log Die-Alg Za-Verz    : 182 en 306 : 194 en 466  : 11246 en 11285 : 181 en 386
                    PO-Log Die-Econ           : 153 en 302 :  84 en 462  : 11249 en 11281 : 166 en 386
                    PO-Log Die-Vert           : 208 en 301 :  85 en 461  : 11248 en 11280 : 166 en 385
                   Deze gehouden met meest volledige info
                   Let wel srv_hogere_dienst is telkens verschillend
                 */
        Integer[] dubbels = {385, 305, 308, 307, 306, 302, 301};
        List<Integer> dubbelsList = Arrays.asList(dubbels);

        boolean error = false;
        AbstractDaoOld daoOld = new ServiceDaoOld(connWIWOld);
        EntityManager em = entityManagerFactory.createEntityManager();
        AbstractDao2<Service> daoNew = new ServiceDao(em);
        int aantal = 0;
        List<Map<String, ColumnDescriptor>> oldList = daoOld.getAll();
        System.out.println(String.format("%5d Service uit %10s gevonden", oldList.size(), daoOld.getTableName()));
        OrganisationDao organisationDao = new OrganisationDao(em);
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (Map<String, ColumnDescriptor> oldService : oldList) {
                Organisation organisation =
                        organisationDao.findField1("org_old_org_id", oldService.get("srv_org_id").getIntValue());
                Service service =
                        new Service(oldService.get("srv_dnst_id").getIntValue(),
                                    oldService.get("srv_dnst_code").getIntValue(),
                                    oldService.get("srv_omschr_nl").getStrValue(),
                                    oldService.get("srv_omschr_fr").getStrValue(),
                                    null, //TODO address uit Hydra/Planon
                                    organisation, //Organisation
                                    null, //Zie Later Person head,
                                    null); //Zie Later Service higherService

                service.setOldId(oldService.get("srv_id").getIntValue());
                service.setOldSource(daoOld.getTableName());
                if (dubbelsList.contains(service.getOldId())) {
                    continue;
                }
                if (daoNew.exists(service)) {
                    System.out.print(".");
                    continue;
                }
                try {
                    daoNew.persist(service);
                    System.out.print("+");
                    aantal++;
                } catch (PersistenceException ex) {
                    System.out.println("Fout in service oldId : " + service.getOldId());
                    System.out.println(ex.getMessage());
                    System.out.println(service.toString());
                    error = true;
                }
            }
            if (error) {
                tx.rollback();
                aantal = 0;
            } else {
                tx.commit();
                System.out.println(String.format("\n%5d service geconverteerd naar %10s", aantal, daoNew.getTableName()));
            }
            return aantal;
        } catch (ColumnDescriptor.DataTypeException e) {
            e.printStackTrace();
            tx.rollback();
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
            return 0;
        } finally {
            em.close();
        }
    }

    private int makeStatue() {
        EntityManager em = entityManagerFactory.createEntityManager();
        AbstractDao2<Statue> daoNew = new StatueDao(em);
        EntityTransaction tx = em.getTransaction();
        int aantal = 0;
        try {
            tx.begin();

            Statue statue = new Statue("Aktief", "Active");
            statue.setOldId(0); statue.setOldSource("");
            daoNew.persist(statue);
            aantal++;

            statue = new Statue("Langdurig Afwezig", "Absence prolongée");
            statue.setOldId(0); statue.setOldSource("");
            daoNew.persist(statue);
            aantal++;

            statue = new Statue("Op Pensioen", "Retraité");
            statue.setOldId(0); statue.setOldSource("");
            daoNew.persist(statue);
            aantal++;

            tx.commit();
            System.out.println(String.format("%5d statue toegevoegd aan %10s", aantal, daoNew.getTableName()));
            return aantal;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
            return 0;
        } finally {
            em.close();
        }
    }

        private int makeHigherFunction() {
            EntityManager em = entityManagerFactory.createEntityManager();
            AbstractDao2<HigherFunction> daoNew = new HigherFunctionDao(em);
            EntityTransaction tx = em.getTransaction();
            int aantal = 0;
            try {
                tx.begin();

                HigherFunction higherFunction = new HigherFunction("Wd.", "Ff.");
                daoNew.persist(higherFunction);
                aantal++;

                tx.commit();
                System.out.println(String.format("%5d higherFunction toegevoegd aan %10s", aantal, daoNew.getTableName()));
                return aantal;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                tx.rollback();
                return 0;
            } finally {
                em.close();
            }
        }

    private int convertTitle() {
        boolean error = false;
        AbstractDaoOld daoOld = new TitleDaoOld(connWIWOld);
        EntityManager em = entityManagerFactory.createEntityManager();
        AbstractDao2<Title> daoNew = new TitleDao(em);
        int aantal = 0;
        List<Map<String, ColumnDescriptor>> oldList = daoOld.getAll();
        System.out.println(String.format("%5d Title uit %10s gevonden", oldList.size(), daoOld.getTableName()));
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (Map<String, ColumnDescriptor> oldTitle : oldList) {
                Title title = new Title(
                        oldTitle.get("titel_komschr_nl").getStrValue(),
                        oldTitle.get("titel_komschr_fr").getStrValue(),
                        oldTitle.get("titel_omschr_nl").getStrValue(),
                        oldTitle.get("titel_omschr_fr").getStrValue());

                title.setOldId(oldTitle.get("titel_code").getIntValue());
                title.setOldSource(daoOld.getTableName());
                if (daoNew.exists(title)) {
                    System.out.print(".");
                    continue;
                }
                try {
                    daoNew.persist(title);
                    System.out.print("+");
                    aantal++;
                } catch (PersistenceException ex) {
                    System.out.println("Fout in titel oldId : " + title.getOldId());
                    System.out.println(ex.getMessage());
                    System.out.println(title.toString());
                    error = true;
                }
            }
            if (error) {
                tx.rollback();
                aantal = 0;
            } else {
                tx.commit();
                System.out.println(String.format("\n%5d title geconverteerd naar %10s", aantal, daoNew.getTableName()));
            }
            return aantal;
        } catch (ColumnDescriptor.DataTypeException e) {
            e.printStackTrace();
            tx.rollback();
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
            return 0;
        } finally {
            em.close();
        }
    }


    private void convertRoom() {
    /*
        //TODO geen enkele match
        select distinct pe_lokaal, lok_nr from persons
        left outer join lokalen on lok_nr = pe_lokaal

        //TODO alles null, behalve voor lokaal C004021
        select distinct srv_lokaal, lok_nr from service
        left outer join lokalen on srv_lokaal = lok_nr

        TODO : Tabel lokalen wordt niet gebruikt, enkel voor Extra TelefoonNummers
     */
    }

    private int convertOrganisationFromPersons() {
        boolean error = false;
        AbstractDaoOld daoOld = PersonsDaoOld.getInstance(connWIWOld);
        EntityManager em = entityManagerFactory.createEntityManager();
        AbstractDao2<Organisation> daoNew = new OrganisationDao(em);
        int aantal = 0;
        List<Map<String, ColumnDescriptor>> oldList = daoOld.getAll();
        System.out.println(String.format("%5d Organisation uit %10s gevonden", oldList.size(), daoOld.getTableName()));
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (Map<String, ColumnDescriptor> oldOrg : oldList) {
                Organisation organisation = new Organisation(
                        oldOrg.get("pe_org_komschr_nl").getStrValue(),
                        oldOrg.get("pe_org_komschr_fr").getStrValue(),
                        oldOrg.get("pe_org_omschr_nl").getStrValue(),
                        oldOrg.get("pe_org_omschr_fr").getStrValue()
                );

                if (organisation.getShortNL() == null && organisation.getShortFR() == null &&
                    organisation.getLongNL()  == null && organisation.getLongFR() == null) {
                    continue;
                }

                organisation.setOldId(oldOrg.get("pe_id").getIntValue());
                organisation.setOldSource(daoOld.getTableName());
                organisation.setOldOrgId(oldOrg.get("pe_org_id").getIntValue());
                if (daoNew.exists(organisation)) {
                    System.out.print(".");
                    continue;
                }
                try {
                    daoNew.persist(organisation);
                    System.out.print("+");
                    aantal++;
                } catch (PersistenceException ex) {
                    System.out.println("Fout in organisation oldId : " + organisation.getOldId());
                    System.out.println(ex.getMessage());
                    System.out.println(organisation.toString());
                    error = true;
                }
            }
            if (error) {
                tx.rollback();
                aantal = 0;
            } else {
                tx.commit();
                System.out.println(String.format("\n%5d organisation geconverteerd naar %10s", aantal, daoNew.getTableName()));
            }
            return aantal;
        } catch (ColumnDescriptor.DataTypeException e) {
            e.printStackTrace();
            tx.rollback();
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
            return 0;
        } finally {
            em.close();
        }
    }

    private int convertOrganisationFromService() {
        boolean error = false;
        AbstractDaoOld daoOld = new ServiceDaoOld(connWIWOld);
        EntityManager em = entityManagerFactory.createEntityManager();
        AbstractDao2<Organisation> daoNew = new OrganisationDao(em);
        int aantal = 0;
        List<Map<String, ColumnDescriptor>> oldList = daoOld.getAll();
        System.out.println(String.format("%5d Organisation uit %10s gevonden", oldList.size(), daoOld.getTableName()));
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (Map<String, ColumnDescriptor> oldOrg : oldList) {
                Organisation organisation = new Organisation(
                        oldOrg.get("srv_org_komschr_nl").getStrValue(),
                        oldOrg.get("srv_org_komschr_fr").getStrValue(),
                        oldOrg.get("srv_org_omschr_nl").getStrValue(),
                        oldOrg.get("srv_org_omschr_fr").getStrValue()
                );

                if (organisation.getShortNL() == null && organisation.getShortFR() == null &&
                        organisation.getLongNL()  == null && organisation.getLongFR() == null) {
                    continue;
                }

                organisation.setOldId(oldOrg.get("srv_id").getIntValue());
                organisation.setOldSource(daoOld.getTableName());
                organisation.setOldOrgId(oldOrg.get("srv_org_id").getIntValue());

                List<Organisation> organisationExisting =
                        daoNew.findField("org_old_org_id", oldOrg.get("srv_org_id").getIntValue());
                if (organisationExisting.size() != 0) { //Bestaat reeds
                    System.out.print(".");
                    continue;
                }

                if (daoNew.exists(organisation)) {
                    System.out.print(".");
                    continue;
                }
                try {
                    daoNew.persist(organisation);
                    System.out.print("+");
                    aantal++;
                } catch (PersistenceException ex) {
                    System.out.println("Fout in organisation oldId : " + organisation.getOldId());
                    System.out.println(ex.getMessage());
                    System.out.println(organisation.toString());
                    error = true;
                }
            }
            if (error) {
                tx.rollback();
                aantal = 0;
            } else {
                tx.commit();
                System.out.println(String.format("\n%5d organisation geconverteerd naar %10s", aantal, daoNew.getTableName()));
            }
            return aantal;
        } catch (ColumnDescriptor.DataTypeException e) {
            e.printStackTrace();
            tx.rollback();
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
            return 0;
        } finally {
            em.close();
        }
    }

    /**
     * V1. Kopieert adressen van tabel werkadres naar tabel address
     */
    private int convertWrkAdressFromPersons() {
        AbstractDaoOld daoOld = new AddressDaoOld(connWIWOld);
        EntityManager em = entityManagerFactory.createEntityManager();
        AbstractDao2<Address> daoNew = new AddressDao(em);
        int aantal = 0;
        List<Map<String, ColumnDescriptor>> oldList = daoOld.getAll();
        System.out.println(String.format("%5d adressen uit %10s gevonden", oldList.size(), daoOld.getTableName()));
        EntityTransaction tx = em.getTransaction();
        Address address = null;
        try {
            tx.begin();
            for (Map<String, ColumnDescriptor> oldAddress : oldList) {
                address = new Address(
                        //TODO wrk_naam is NULL omdat werk adres in Person enkel straat en gemeente is, geen gebouw
                        oldAddress.get("wrk_naam_nl").getStrValue("TODO"),
                        oldAddress.get("wrk_naam_fr").getStrValue("TODO"),
                        oldAddress.get("wrk_adres_nl").getStrValue(),
                        oldAddress.get("wrk_adres_fr").getStrValue(),
                        oldAddress.get("wrk_huisnr").getStrValue(),
                        oldAddress.get("wrk_postcode").getStrValue(),
                        oldAddress.get("wrk_gemeente_nl").getStrValue(),
                        oldAddress.get("wrk_gemeente_fr").getStrValue(),
                        "BE", null, null, //country, ctry2, ctry3,
                        null, null); //tel checked)
                address.setOldId(oldAddress.get("wrk_id").getIntValue());
                address.setOldSource(daoOld.getTableName());
                daoNew.persist(address);
                System.out.print("+");
                aantal++;
                System.out.println(aantal);
            }
            tx.commit();
            System.out.println(String.format("%5d adressen geconverteert naar %10s", aantal, daoNew.getTableName()));
            return aantal;
        } catch (ColumnDescriptor.DataTypeException | Exception e) {
            System.out.println(address);
            e.printStackTrace();
            tx.rollback();
            return 0;
        } finally {
            em.close();
        }
    }

    //TODO Planon? ZIE public Address findAdress(String adres, String gemeente)
    //TODO MANUEEL structuur is anders dan van werkAdres : enkel adres en gemeenten, geen taal.
    private int convertAdmAdressFromPersons() {
        return 0;
    }


    /**
     * V1. Kopieert data van tabel aformule naar tabel approch
     */
    private int convertApproach() {
        AbstractDaoOld daoOld = new ApproachDaoOld(connWIWOld);
        EntityManager em = entityManagerFactory.createEntityManager();
        AbstractDao2<Approach> daoNew = new ApproachDao(em);
        int aantal = 0;
        List<Map<String, ColumnDescriptor>> oldList = daoOld.getAll();
        System.out.println(String.format("%5d Approach uit %10s gevonden", oldList.size(), daoOld.getTableName()));
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (Map<String, ColumnDescriptor> oldApproach : oldList) {
                Approach approach = new Approach(
                        oldApproach.get("af_afk_nl").getStrValue(),
                        oldApproach.get("af_afk_fr").getStrValue(),
                        oldApproach.get("af_omschr_nl").getStrValue(),
                        oldApproach.get("af_omschr_fr").getStrValue());
                approach.setOldId(oldApproach.get("af_code").getIntValue());
                approach.setOldSource(daoOld.getTableName());
                daoNew.persist(approach);
                System.out.print("+");
                aantal++;
                System.out.println(aantal);
            }
            tx.commit();
            System.out.println(String.format("%5d approch geconverteert naar %10s", aantal, daoNew.getTableName()));
            return aantal;
        } catch (ColumnDescriptor.DataTypeException e) {
            e.printStackTrace();
            tx.rollback();
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
            return 0;
        } finally {
            em.close();
        }
    }

    /*
    Data volgens mail van "Mail 29/04/2020"
     */
    private int makeTransport() {
        EntityManager em = entityManagerFactory.createEntityManager();
        AbstractDao2<Transport> daoNew = new TransportDao(em);
        EntityTransaction tx = em.getTransaction();
        int aantal = 0;
        try {
            tx.begin();

            Transport transport = new Transport("Trein", "Train", false);
            transport.setOldId(1); transport.setOldSource("Mail 29/04/2020");
            daoNew.persist(transport);
            aantal++;

            transport = new Transport("MIVB", "STIB", true);
            transport.setOldId(2); transport.setOldSource("Mail 29/04/2020");
            daoNew.persist(transport);
            aantal++;

            transport = new Transport("De Lijn", "De Lijn", false);
            transport.setOldId(3); transport.setOldSource("Mail 29/04/2020");
            daoNew.persist(transport);
            aantal++;

            transport = new Transport("Tec", "Tec", false);
            transport.setOldId(4); transport.setOldSource("Mail 29/04/2020");
            daoNew.persist(transport);
            aantal++;

            transport = new Transport("Privé wagen", "Voiture privé", true);
            transport.setOldId(9); transport.setOldSource("Mail 29/04/2020");
            daoNew.persist(transport);
            aantal++;

            transport = new Transport("Bedrijfswagen", "Voiture de société", true);
            transport.setOldId(10); transport.setOldSource("Mail 29/04/2020");
            daoNew.persist(transport);
            aantal++;

            transport = new Transport("Fiets", "Vélo", true);
            transport.setOldId(11); transport.setOldSource("Mail 29/04/2020");
            daoNew.persist(transport);
            aantal++;

            transport = new Transport("Te voet", "a Pied", true);
            transport.setOldId(12); transport.setOldSource("Mail 29/04/2020");
            daoNew.persist(transport);
            aantal++;

            transport = new Transport("BromFiets, MotorFiets", "Motocyclette, Moto", true);
            transport.setOldId(13); transport.setOldSource("Mail 29/04/2020");
            daoNew.persist(transport);
            aantal++;

            transport = new Transport("Electrische Step", "Trottinette Electrique", true);
            transport.setOldId(14); transport.setOldSource("Mail 29/04/2020");
            daoNew.persist(transport);
            aantal++;

            /*
            5 : Trein + MIVB
            6 : Trein + Fiets
            7 : Trein + Te Voet
            8 : Trein + El Step
            */

            tx.commit();
            System.out.println(String.format("%5d transport toegevoegd aan %10s", aantal, daoNew.getTableName()));
            return aantal;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
            return 0;
        } finally {
            em.close();
        }
    }

    /**
     * V1. Kopieert pe_vak_komschr_nl, pe_vak_omschr_nl, pe_vak_komschr_fr, pe_vak_omschr_fr
     * van tabel persons naar tabel class
     */
    private int convertClass() {
        boolean error = false;
        AbstractDaoOld daoOld = PersonsDaoOld.getInstance(connWIWOld);
        EntityManager em = entityManagerFactory.createEntityManager();
        AbstractDao2<CClass> daoNew = new ClassDao(em);
        int aantal = 0;
        List<Map<String, ColumnDescriptor>> oldList = daoOld.getAll();
        System.out.println(String.format("%5d Class uit %10s gevonden", oldList.size(), daoOld.getTableName()));
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (Map<String, ColumnDescriptor> oldClass : oldList) {
                CClass aClass = new CClass(
                        oldClass.get("pe_vak_komschr_nl").getStrValue(),
                        oldClass.get("pe_vak_komschr_fr").getStrValue(),
                        oldClass.get("pe_vak_omschr_nl").getStrValue(),
                        oldClass.get("pe_vak_omschr_fr").getStrValue()
                );
                if (aClass.getShortNL() == null && aClass.getShortFR() == null &&
                    aClass.getLongNL()  == null && aClass.getLongFR() == null) {
                    continue;
                }
                aClass.setOldId(oldClass.get("pe_id").getIntValue());
                aClass.setOldSource(daoOld.getTableName());
                if (daoNew.exists(aClass)) {
                    System.out.print(".");
                    continue;
                }
                try {
                    daoNew.persist(aClass);
                    System.out.print("+");
                    aantal++;
                } catch (PersistenceException ex) {
                    System.out.println("Fout in aClass oldId : " + aClass.getOldId());
                    System.out.println(ex.getMessage());
                    System.out.println(aClass.toString());
                    error = true;
                }
            }
            if (error) {
                tx.rollback();
                aantal = 0;
            } else {
                tx.commit();
                System.out.println(String.format("\n%5d Class geconverteerd naar %10s", aantal, daoNew.getTableName()));
            }
            return aantal;
        } catch (ColumnDescriptor.DataTypeException e) {
            e.printStackTrace();
            tx.rollback();
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
            return 0;
        } finally {
            em.close();
        }
    }

    private int convertDegree() {
        boolean error = false;
        AbstractDaoOld daoOld = PersonsDaoOld.getInstance(connWIWOld);
        EntityManager em = entityManagerFactory.createEntityManager();
        AbstractDao2<Degree> daoNew = new DegreeDao(em);
        int aantal = 0;
        List<Map<String, ColumnDescriptor>> oldList = daoOld.getAll();
        System.out.println(String.format("%5d Degree uit %10s gevonden", oldList.size(), daoOld.getTableName()));
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (Map<String, ColumnDescriptor> oldClass : oldList) {
                Degree degree = new Degree(
                        oldClass.get("pe_grd_omschr_nl").getStrValue(),
                        oldClass.get("pe_grd_omschr_fr").getStrValue()
                );
                if (degree.getDescrNL() == null && degree.getDescrFR() == null) {
                    continue;
                }
                degree.setOldId(oldClass.get("pe_id").getIntValue());
                degree.setOldSource(daoOld.getTableName());
                if (daoNew.exists(degree)) {
                    System.out.print(".");
                    continue;
                }
                try {
                    daoNew.persist(degree);
                    System.out.print("+");
                    aantal++;
                } catch (PersistenceException ex) {
                    System.out.println("\nFout in Degree oldId : " + degree.getOldId());
                    System.out.println(ex.getMessage());
                    System.out.println(degree.toString());
                    error = true;
                }
            }
            if (error) {
                tx.rollback();
                aantal = 0;
            } else {
                tx.commit();
                System.out.println(String.format("\n%5d degree geconverteerd naar %10s", aantal, daoNew.getTableName()));
            }
            return aantal;
        } catch (ColumnDescriptor.DataTypeException e) {
            e.printStackTrace();
            tx.rollback();
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
            return 0;
        } finally {
            em.close();
        }
    }

    private int convertFunction() {
        boolean error = false;
        AbstractDaoOld daoOld = PersonsDaoOld.getInstance(connWIWOld);
        EntityManager em = entityManagerFactory.createEntityManager();
        AbstractDao2<Function> daoNew = new FunctionDao(em);
        int aantal = 0;
        List<Map<String, ColumnDescriptor>> oldList = daoOld.getAll();
        System.out.println(String.format("%5d Function uit %10s gevonden", oldList.size(), daoOld.getTableName()));
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            for (Map<String, ColumnDescriptor> oldClass : oldList) {
                Function function = new Function(
                        oldClass.get("pe_fct_omschr_nl").getStrValue(),
                        oldClass.get("pe_fct_omschr_fr").getStrValue()
                );
                if (function.getDescrNL() == null && function.getDescrFR() == null) {
                    continue;
                }
                function.setOldId(oldClass.get("pe_id").getIntValue());
                function.setOldSource(daoOld.getTableName());
                if (daoNew.exists(function)) {
                    System.out.print(".");
                    continue;
                }
                try {
                    daoNew.persist(function);
                    System.out.print("+");
                    aantal++;
                } catch (PersistenceException ex) {
                    System.out.println("\nFout in Function oldId : " + function.getOldId());
                    System.out.println(ex.getMessage());
                    System.out.println(function.toString());
                    error = true;
                }
            }
            if (error) {
                tx.rollback();
                aantal = 0;
            } else {
                tx.commit();
                System.out.println(String.format("\n%5d function geconverteerd naar %10s", aantal, daoNew.getTableName()));
            }
            return aantal;
        } catch (ColumnDescriptor.DataTypeException e) {
            e.printStackTrace();
            tx.rollback();
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
            return 0;
        } finally {
            em.close();
        }
    }

    private void serviceSetHigher() {
        /*TODO Services die er dubbel in stonden
            PO-Log Die                  : 166   385
            PO-Log Die-Alg Za-Verz      : 182   306
            PO-Log Die-Vert             : 208   301
            PO-Log Die-Econ             : 153   302
            PO-Log Die-Alg Za-Onth      : 180   308
            PO-Log Die-Alg Za-Bodes     : 184   305
            PO-Log Die-Alg Za-Schoonm   : 185   307
         */
        Map<Integer, Integer> vertaalService = new HashMap<>();
        vertaalService.put(385, 166);
        vertaalService.put(306, 182);
        vertaalService.put(301, 208);
        vertaalService.put(302, 153);
        vertaalService.put(308, 180);
        vertaalService.put(305, 184);
        vertaalService.put(307, 185);

        EntityManager em = entityManagerFactory.createEntityManager();
        ServiceDao serviceDao = new ServiceDao(em);
        ServiceDaoOld oldSServiceDao = null;
        System.out.println("Set Service Higher");
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            oldSServiceDao = new ServiceDaoOld(connWIWOld);
            for (Service service : serviceDao.findAll()) {
                int oldServiceId = service.getOldId();
                Integer oldHigherId = oldSServiceDao.findOldXXXId("srv_hogere_dienst", oldServiceId);
                oldHigherId = vertaalService.getOrDefault(oldHigherId, oldHigherId);
                Service higher = serviceDao.findField1("srv_old_id", oldHigherId);
                service.setHigherService(higher);
                em.persist(service);
                System.out.print(".");
            }
            tx.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
        }
    }

    private void serviceSetHead() {
        EntityManager em = entityManagerFactory.createEntityManager();
        ServiceDao serviceDao = new ServiceDao(em);
        PersonDao  personDao  = new PersonDao(em);
        ServiceDaoOld oldSServiceDao = null;
        System.out.println("Set Service Head");
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            oldSServiceDao = new ServiceDaoOld(connWIWOld);
            for (Service service : serviceDao.findAll()) {
                int oldServiceId = service.getOldId();
                Integer oldHeadId = oldSServiceDao.findOldXXXId("srv_hoofd_pe_id",  oldServiceId);
                Person head = personDao.findField1("pe_old_id", oldHeadId);
                service.setHead(head);
                em.persist(service);
                System.out.print(".");
            }
            tx.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
        }
    }

    private void serviceSetAddress() {
        //TODO adressen uit planon + manueel doen
    }

    private Address findAddress(AddressDao addressDao, Map<String, ColumnDescriptor> oldPerson, boolean admin)
            throws ColumnDescriptor.DataTypeException, Exception {
        if (admin) {
            return addressDao.findAdress(oldPerson.get("pe_adm_adres").getStrValue(),
                                         oldPerson.get("pe_adm_gemeente").getStrValue());
        } else {
            return addressDao.findField1("adr_old_id", oldPerson.get("pe_wrk_id").getIntValue());
        }
    }

}