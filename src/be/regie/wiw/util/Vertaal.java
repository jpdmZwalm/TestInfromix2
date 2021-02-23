package be.regie.wiw.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Vertaal {
    private static Vertaal instance = null;

    private Map<String, String> mapGraadNL;
    private Map<String, String> mapGraadFR;
    private Map<String, String> mapFctNL;
    private Map<String, String> mapFctFR;
    private Map<String, String> mapOrgShortNL;
    private Map<String, String> mapOrgShortFR;

    private Vertaal() {
        mapGraadNL = null;
        mapGraadFR = null;
        mapFctNL = null;
        mapFctFR = null;
    }

    public static Vertaal getInstance() {
        if (instance == null) {
            instance = new Vertaal();
        }
        return instance;
    }

    /**
     * select distinct pe_grd_omschr_nl, pe_grd_omschr_fr from persons
     * where pe_grd_omschr_fr = 'Frans' order by pe_grd_omschr_nl
     *
     * select distinct pe_grd_omschr_nl, pe_grd_omschr_fr from persons
     * where pe_grd_omschr_fr = 'Nederlands' order by pe_grd_omschr_nl
     * @return
     */
    public Map<String, String> getMapGraadNL() {
        if (mapGraadNL == null) {
            Map<String, String> map = new HashMap<>();
            map.put("Chancellerie", "Kanselarij");
            map.put("Chauffeur Ministre", "Chauffeur van de Minister");
            map.put("Collaborateur", "Medewerker");
            map.put("Collaborateur AGRI", "Medewerker AGRI");
            map.put("Collaborateur Politique Générale", "Algemeen Politiek Medewerker");
            map.put("Collaborateur PME", "Medewerker PME");
            map.put("Collaboratrice PME", "Medewerkster PME");
            map.put("Collaboratrice Press -Com", "Medewerkster Pers -Com");
            map.put("Directeur DE Cabinet", "CabinetsDirecteur");
            map.put("Expert Statut Soicial Ind", "Expert Sociaal Statuut Ind");
            map.put("Huissier", "Deurwaarder");
            map.put("Responsable Presse -Com", "Verantwoordelijke Pers -Com");
            map.put("Secrétaire Ministre", "Secretaris van de Minister");
            map.put("Traducteur", "Vertaler");
            map.put("Expert ICT", "ICT deskundige");
            map.put("Communication", "Cel « Communicatie »");
            map.put("Statut social", "Sociaal Statuut");
            mapGraadNL = map;
        }
        return Collections.unmodifiableMap(mapGraadNL);
    }

    public Map<String, String> getMapGraadFR() {
        if (mapGraadFR == null) {
            Map<String, String> map = new HashMap<>();
            map.put("Chauffeur de la Ministre", "Chauffeur Ministre");
            map.put("Expert Statut Soicial Ind", "Expert Statut Social Ind");
            map.put("Programmeur-analist", "Programmeur-analyste");
            map.put("Inspecteur général des Fiannces", "Inspecteur général des Finances");
            map.put("Schoolstagiair", "Stagiaire scolaire");
            mapGraadFR = map;
        }
        return Collections.unmodifiableMap(mapGraadFR);
    }

    public Map<String, String> getMapFctNL() {
        if (mapFctNL == null) {
            Map<String, String> map = new HashMap<>();
            map.put("Accueil", "Onthaal");
            map.put("Chancellerie", "Kanselarij");
            map.put("Chauffeur Ministre", "Chauffeur van de Minister");
            map.put("Collaborateur", "Medewerker");
            map.put("Collaborateur AGRI", "Medewerker AGRI");
            map.put("Collaborateur PME", "Medewerker PME");
            map.put("Collaborateur Politique Générale", "Medewerker Politiek Algemeen");
            map.put("Collaborateur statut social", "Medewerker Sociaal Statuut");
            map.put("Collaboratrice PME", "Medewerkster PME");
            map.put("Collaboratrice PME-Classes Moyennes", "Medewerkster PME-Middenstand");
            map.put("Collaboratrice Press -Com", "Medewerkster Pers -Com");
            map.put("Collaboratrice Presse-Com", "Medewerkster Pers -Com");
            map.put("Collborateur Pol générale", "Medewerker Politiek Algemeen");
            map.put("Communication", "Communicatie");
            map.put("Cel « Communicatie »", "Communicatie");
            map.put("Directeur DE Cabinet", "CabinetsDirecteur");
            map.put("Docteur", "Dokter");
            map.put("Expert PM -Classes moyennes", "Expert PM -Middenstand");
            map.put("Expert Statut Soicial Ind", "Expert Sociaal Statuut Ind");
            map.put("Extern - Consultant", "Externe Consultant");
            map.put("Externe consultant", "Externe Consultant");
            map.put("Huissier", "Deurwaarder");
            map.put("Responsable Presse -Com", "Verantwoordelijke Pers -Com");
            map.put("Secrétaire Ministre", "Secretaris van de Minister");
            map.put("Secrétaire PME -Classes Moyennes", "Secretaris PME -Middenstand");
            map.put("Secrétaire de Cabinet", "CabinetsSecretaris");
            map.put("Statut social", "Sociaal Statuut");
            map.put("TECHNISCH BEHEERDER", "Technisch Beheerder");
            map.put("Traducteur", "Vertaler");
            map.put("Traductrice", "Vertaalster");
            mapFctNL = map;
        }
        return Collections.unmodifiableMap(mapFctNL);
    }

    public Map<String, String> getMapFctFR() {
        if (mapFctFR == null) {
            Map<String, String> map = new HashMap<>();
            map.put("Chauffeur de la Ministre", "Chauffeur Ministre");
            map.put("Collaborateur statut social", "Collaborateur Statut Social");
            map.put("Collaboratrice Presse-Com", "Collaboratrice Press -Com");
            map.put("Collaborateur Pol générale", "Collaborateur Politique Générale");
            map.put("Dokter", "Docteur");
            map.put("Eerste controleur", "Premier Controleur");
            map.put("Expert Statut Soicial Ind", "Expert Statut Social Ind");
            map.put("Externe - Consultant", "Consultant Externe");
            map.put("Consultant externe",  "Consultant Externe");
            map.put("Programmeur-analist", "Programmeur-analyste");
            map.put("Statut social", "Statut Social");
            map.put("GESTIONNAIRE TECHNIQUE", "Gestionnaire Technique");
            map.put("Eerste-auditeur-revisor", "Premier-auditeur-revisor");
            map.put("Inspecteur général des Fiannces", "Inspecteur général des Finances");
            map.put("Schoolstagiair", "Stagiaire scolaire");
            mapFctFR = map;
        }
        return Collections.unmodifiableMap(mapFctFR);
    }

    public Map<String, String> getMapOrgShortNL() {
        if (mapOrgShortNL == null) {
            Map<String, String> map = new HashMap<>();
            map.put("BE2000", "BE-2000");
            mapOrgShortNL = map;
        }
        return Collections.unmodifiableMap(mapOrgShortNL);
    }

    public Map<String, String> getMapOrgShortFR() {
        if (mapOrgShortFR == null) {
            Map<String, String> map = new HashMap<>();
            map.put("BE2000", "BE-2000");
            mapOrgShortFR = map;
        }
        return Collections.unmodifiableMap(mapOrgShortFR);
    }

}
