//package com.nectux.mizan.hyban.securite;
//
//// CarboneConfig.java
//
//
//import io.carbone.CarboneException;
//import io.carbone.ICarboneServices;
//import io.carbone.CarboneServicesFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class CarboneConfig {
//
//    /**
//     * Pour Carbone Cloud : passe ton API_KEY en argument create("API_KEY")
//     * Pour Carbone On-Premise : appelle SetCarboneUrl(...) puis create("")
//     */
//    @Bean
//    public ICarboneServices carboneServices() throws CarboneException {
//        // Exemple : Cloud (API_KEY)
//        // return CarboneServicesFactory.CARBONE_SERVICES_FACTORY_INSTANCE.create("YOUR_API_KEY");
//
//        // Exemple : On-Premise (serveur local) - si tu as un server, définis l'URL puis create("")
//         CarboneServicesFactory.CARBONE_SERVICES_FACTORY_INSTANCE.SetCarboneUrl("http://localhost:4000");
//         return CarboneServicesFactory.CARBONE_SERVICES_FACTORY_INSTANCE.create("");
//
//        // Si tu veux tester sans clef et sans URL (cloud), tu peux laisser vide — mais pour Cloud il faut une clé.
//        //return CarboneServicesFactory.CARBONE_SERVICES_FACTORY_INSTANCE.create("");
//    }
//}
//
