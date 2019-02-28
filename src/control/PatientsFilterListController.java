package control;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Patient;

import java.util.Collection;

public class PatientsFilterListController extends PatientsListController {

    @FXML
    TableView<Patient> tablePatients;

    private static ObservableList<Patient> data2 = FXCollections.observableArrayList();


    public void initialize() {
        TableColumn DNI = new TableColumn<Patient,String>("DNI");
        DNI.setPrefWidth(80);
        DNI.setResizable(false);
        TableColumn Nom = new TableColumn("Nom");
        Nom.setPrefWidth(80);
        Nom.setResizable(false);
        TableColumn Cognoms = new TableColumn("Cognoms");
        Cognoms.setPrefWidth(100);
        Cognoms.setResizable(false);
        TableColumn DataNaix = new TableColumn("Data de Naixament");
        DataNaix.setPrefWidth(100);
        DataNaix.setResizable(false);
        TableColumn Gender = new TableColumn("Gènere");
        Gender.setPrefWidth(100);
        Gender.setResizable(false);
        TableColumn Telefon = new TableColumn("Telèfon");
        Telefon.setPrefWidth(100);
        Telefon.setResizable(false);
        TableColumn pes = new TableColumn("Pes");
        pes.setPrefWidth(70);
        pes.setResizable(false);
        TableColumn Alçada = new TableColumn("Alçada");
        Alçada.setPrefWidth(70);
        Alçada.setResizable(false);
        TableColumn CB = new TableColumn("Gestionar");
        CB.setPrefWidth(90);
        CB.setResizable(false);
        tablePatients.setEditable(true);

        DNI.setCellValueFactory(new PropertyValueFactory<Patient, String>("DNI"));
        Nom.setCellValueFactory(new PropertyValueFactory<Patient, String>("Nom"));
        Cognoms.setCellValueFactory(new PropertyValueFactory<Patient, String>("Cognoms"));
        DataNaix.setCellValueFactory(new PropertyValueFactory<Patient, String>("DataNaixament"));
        Gender.setCellValueFactory(new PropertyValueFactory<Patient, String>("genere"));
        Telefon.setCellValueFactory(new PropertyValueFactory<Patient, String>("Telefon"));
        pes.setCellValueFactory(new PropertyValueFactory<Patient, Float>("Pes"));
        Alçada.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("Alçada"));
        CB.setCellValueFactory(new PropertyValueFactory<Patient, CheckBox>("cb"));
        tablePatients.getColumns().addAll(DNI, Nom, Cognoms, DataNaix, Gender, Telefon, pes, Alçada,CB);
        tablePatients.setItems(data2);
    }

    public static void init(Collection<Patient> patients) {
        data2.removeAll(data2);
        patients.forEach(patient -> {
            Patient p = new Patient(patient.getDNI(), patient.getNom(), patient.getCognoms(), patient.getDataNaixament(), patient.getGenere(), patient.getTelefon(), patient.getPes(), patient.getAlçada(),new CheckBox());
            data2.add(p);
        });
    }

    @Override
    public void tableClicked(MouseEvent event) {
        super.tableClicked(event);
    }

    public void manage(ActionEvent event) {
        ObservableList<Patient> dataToDelete = FXCollections.observableArrayList();
        tablePatients.getItems().forEach(patient -> {
            if(patient.getCb().isSelected()) {
                dataToDelete.add(patient);
            }
        });
        String[] boton = event.getSource().toString().split("'");
        switch(boton[1]) {
            case "Borrar":
                data2.removeAll(dataToDelete);
                PatientsListController.init(data2);
                break;
            case "Editar":
                //TODO: Ventana de edición de datos.
                break;
            case "Añadir a lista de espera":
                //TODO: Añadir a lista de espera.
                break;
        }
    }

    public void search() {

    }
}
