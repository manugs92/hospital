package control;


import alerts.Alerts;
import checks.Checks;
import config.Config;
import data.CSV;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import model.Hospital;
import model.Patient;
import model.WaitingList;
import strings.StringController;
import strings.Strings;

public class Controller {

    @FXML
    MenuItem loadCSV;

    @FXML
    TabPane selector;

    @FXML
    Tab tab1, tab2, tab3, tab5;

    @FXML
    AnchorPane patientsList,patientsFilter,patientsStats,seeAwaitList;

    @FXML
    Text title;

    private Checks ck = new Checks();
    private Config config = new Config();
    private CSV csv = new CSV();
    private Hospital hospital = new Hospital();

    private static File configFile;

    public Controller() throws IOException {
    }

    /*
    * TODO: Crear FXML de añadir lista de espera.
    * TODO: Crear controller lista de espera.
    * */

    /*
     * Method that runs automatically when the application starts.
     * It checks if the config file exist, if it exist, and have valid data, we load it and create the hospital.
     * Else, disable  all the program, until the user loads a valid CSV file, and ask to the user
     * it he wants to create the config file.
     * */
    public void initialize() throws IOException {
        try {
            configFile = ck.ifConfigFileExist();
            String[] content = config.readContent();
            if (!config.readValueOfContent(content[0]).isEmpty() && !config.readValueOfContent(content[1]).isEmpty()) {
                hospital.SetName(config.readValueOfContent(content[0]));
                title.setText(Strings.NAME_OF_THE_APP+" "+hospital.GetName());
                csv.setPathCSVFile(config.readValueOfContent(content[1]));
                createHospital(config.readValueOfContent(content[1]));
            }else {
                selector.getTabs().forEach(tab -> { tab.setDisable(true);tab.getContent().setVisible(false); });
            }
        } catch (IOException ioex) {
            selector.getTabs().forEach(tab -> { tab.setDisable(true);tab.getContent().setVisible(false); });
            boolean response = Alerts.configFileNotFound();
            if (response) {
                config.createConfigFile();
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
                result = Alerts.errorCSVNotSelected();
                if (result) {
                    selectCSVToLoad();
                }
            } else {
                /*
                 * If the selected file it's a CSV, we will create the hospital.
                 * And make sure that the fxml for the list, is cleared.
                 * (Because you can load a csv file even if you have loaded one hospital before)
                 * Then check if the config file exist, and if it exist, overwrite it with the new data of the new hospital.
                 * If it doesn't exist, we ask to the user if he wants to create it.*/
                csv.setPathCSVFile(selectedFile.toString());
                hospital.SetName("");
                patientsList.getChildren().clear();
                createHospital(selectedFile.toString());
                try {
                    configFile = ck.ifConfigFileExist();
                    config.writeContent(hospital.GetName(), selectedFile.toString());
                } catch (IOException ioex) {
                    boolean response = Alerts.configFileNotFound();
                    if (response) {
                        config.createConfigFile();
                        config.writeContent(hospital.GetName(), selectedFile.toString());
                    }
                }
            }
        }
    }

    /*
     * Method that creates an hospital from the csv loaded
     * If the hospital doesn't have a name, we force to the user to put one name to the hospital.
     *(It can't be empty so we loop the method until the user enter the name).
     * When the hospital have a name, we put it in the object hospital, and load the data in the collection Patient. (That
     * it's the content of the csv)
     * And then take positionated the user to the first tab. (Because we load a new Hospital)
     * And unlockAll the content. (It's usefull for when we create the hospital for the first time).
     * Futhermore, we load the FXML that have the GUI of the patient's list.
     * */
    public void createHospital(String selectedCSV) throws IOException {
        if(hospital.GetName().isEmpty()) {
            String hospitalName = Alerts.setHospitalName();
            if(hospitalName.isEmpty()) {
                Alerts.hospitalNameIsEmpty();
                createHospital(selectedCSV);
            }else {
                hospital.SetName(hospitalName);
                title.setText(Strings.NAME_OF_THE_APP+" "+hospital.GetName());
                unlockAll();
            }
        }
        Collection<Patient> patients = hospital.loadPacients(selectedCSV);
        selector.getSelectionModel().selectFirst();
        WaitingList waitingList = new WaitingList();
        PatientsListController.init(patients);
        PatientsListController.updateWaitingList(waitingList);
        PatientsFilterListController.init(patients);
        PatientsFilterListController.updateWaitingList(waitingList);
        PatientStatsController.init(patients);
        PatientStatsController.updateWaitingList(waitingList);
        patientsList.getChildren().add(FXMLLoader.load(getClass().getResource("../fxml/patientsList.fxml")));
        patientsFilter.getChildren().add(FXMLLoader.load(getClass().getResource("../fxml/patientsFilterList.fxml")));
        patientsStats.getChildren().add(FXMLLoader.load(getClass().getResource("../fxml/patientsStats.fxml")));
    }
}
