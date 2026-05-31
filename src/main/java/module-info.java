module com.example.projet_oop_rogue {
    requires javafx.controls;
    requires javafx.fxml;

    // Autorisations pour le dossier principal
    opens com.example.projet_oop_rogue to javafx.fxml;
    exports com.example.projet_oop_rogue;


    // !!! OBLIGATOIRE SINON PLEINS D'ERREURS AU LANCEMENT DU JEU !!!

    // Par défaut, un module ferme ses dossiers à clé.
    // Puisque nous avons créé un tout nouveau package (dossier) nommé controllers pour bien ranger notre architecture,
    // le framework JavaFX (qui s'occupe de lire les fichiers FXML) n'a tout simplement pas l'autorisation d'entrer
    // dans ce dossier pour instancier ton WelcomePageController.

    // NOUVELLES AUTORISATIONS POUR LE DOSSIER DES CONTROLLERS
    opens com.example.projet_oop_rogue.controllers to javafx.fxml;
    exports com.example.projet_oop_rogue.controllers;
}