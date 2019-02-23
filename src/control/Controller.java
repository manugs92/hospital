package control;


import alerts.Alerts;
import checks.Checks;
import config.Config;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import model.Hospital;
import model.Patient;
import strings.StringController;

public class Controller {

    @FXML
    MenuItem loadCSV;

    @FXML
    TabPane selector;

    @FXML
    Tab tab1, tab2, tab3, tab4, tab5;

    @FXML
    AnchorPane patientsList,patientsFilter,patientsStats,manageAwaitList,seeAwaitList;

    private Checks ck = new Checks();
    private Config config = new Config();

    private Hospital hospital = new Hospital();
    private static File configFile;

    /*
    * TODO: Crear todos los FXML de cada tab. (No resizable, misma medida .. etc.)
    * TODO: Crear sus respectivos Controller.
    * TODO: Definir cada uno de los FXML en sus respectivas zonas.
    * TODO: Programar controller's de cada uno de los tabs.
    * */

    /*
     * Method that runs automatically when the application starts.
     * It checks if the config file exist, if it exist, and have valid data, we load it and create the hospital.
     * Else, disable  all the program, until the user loads a valid CSV file, and ask to the user
     * it he wants to create the config file.
     * */
    public void initialize() throws IOException {
        patientsList.getChildren().add(FXMLLoader.load(getClass().getResource("../fxml/patientsList.fxml")));
        patientsFilter.getChildren().add(FXMLLoader.load(getClass().getResource("../fxml/patientsList.fxml")));
        try {
            configFile = ck.ifConfigFileExist();
            String[] content = config.ReadContent();
            if (!config.ReadValueOfContent(content[0]).isEmpty() && !config.ReadValueOfContent(content[1]).isEmpty()) {
                hospital.SetName(config.ReadValueOfContent(content[0]));
                CreateHospital(config.ReadValueOfContent(content[1]));
            }else {
                selector.getTabs().forEach(tab -> { tab.setDisable(true);tab.getContent().setVisible(false); });
            }
        } catch (IOException ioex) {
            selector.getTabs().forEach(tab -> { tab.setDisable(true);tab.getContent().setVisible(false); });
            boolean response = Alerts.ConfigFileNotFound();
            if (response) {
                config.CreateConfigFile();
            }
        }
    }

    /*
     * Method that unlock all the tabs from the GUI. (It's not necesary to be unlocked, if any hospital it's loaded).
     * */
    public void unlockAll() {
        selector.getTabs().forEach(tab -> {
            tab.setDisable(false);
            tab.getContent().setVisible(true);
        });
    }

    /*
     * Method that will be executed when you press load CSV.
     * */
    public void selectCSVToLoad() throws IOException {
        Stage primaryStage = null;
        FileChooser fc = new FileChooser();
        fc.setTitle(StringController.FILE_CHOOSER_TITLE);
        File defaultDirectory = new File(StringController.INITIAL_DIRECTORY);
        fc.setInitialDirectory(defaultDirectory);
        File selectedFile = fc.showOpenDialog(primaryStage);

        //We check if the user exits pressing cancel o closing the window.
        if (selectedFile != null) {
            /*
             * We check if the selected file, it's a CSV.
             * If the result is false, we will show an alert to the
             * user, and depending of the response, we will show
             * again the Filechooser, or close the window.
             * */
            boolean result = ck.extensionCSV(selectedFile);
            if (!result) {
                result = Alerts.ErrorCSVNotSelected();
                if (result) {
                    selectCSVToLoad();
                }
            } else {
                /*
                 * If the selected file it's a CSV, we will create the hospital.
                 * Then check if the config file exist, and if it exist, overwrite it with the new data of the new hospital.
                 * If it doesn't exist, we ask to the user if he wants to create it.*/
                hospital.SetName("");
                CreateHospital(selectedFile.toString());
                try {
                    configFile = ck.ifConfigFileExist();
                    config.WriteContent(hospital.GetName(), selectedFile.toString());
                } catch (IOException ioex) {
                    boolean response = Alerts.ConfigFileNotFound();
                    if (response) {
                        config.CreateConfigFile();
                        config.WriteContent(hospital.GetName(), selectedFile.toString());
                    }
                }
            }
        }
    }

    /*
     * Method that creates an hospital from the csv loaded
     * First of all we ask to the user the name of the hospital. (It can't be empty so we loop the method until the user
     * enter the name).
     * When the hospital has a name, we put in the object hospital, and load the data in the collection Patient. (That
     * it's the content of the csv)
     * And then take positionated the user to the first tab. (Because we load a new Hospital)
     * And unlockAll the content. (It's usefull for when we create the hospital for the first time).
     * */
    public void CreateHospital(String selectedCSV) {
        if(hospital.GetName().isEmpty()) {
            String hospitalName = Alerts.SetHospitalName();
            if(hospitalName.isEmpty()) {
                Alerts.HospitalNameIsEmpty();
                CreateHospital(selectedCSV);
            }else {
                hospital.SetName(hospitalName);
                unlockAll();
            }
        }
            Collection<Patient> patients = hospital.loadPacients(selectedCSV);
            selector.getSelectionModel().selectFirst();
            patients.forEach(p -> System.out.println(p.getNom()));
    }
}
