package control;

import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Patient;
import java.util.Collection;


public class PatientsListController {


    /*
    *  TODO: Poner botones de editar o borrar. (Seleccion multiple)
    *  TODO: Al hacer doble click, abrir ventana con los datos del paciente. (Y lo puedes gestionar)
    *  TODO: Amplicar tamaño de tabla y colocar scroll.
    * */

    @FXML
    TableView<Patient> tablePatients;


    private static final ObservableList<Patient> data = FXCollections.observableArrayList();

    /*
    * Method that loads all the patients in the tableView.
    * All times that we create a new Hospital, this method it's called.
    * */
    public void init(Collection<Patient> patients) {
        data.removeAll(data);
        patients.forEach(patient -> {
            Patient p = new Patient(patient.getDNI(), patient.getNom(), patient.getCognoms(), patient.getDataNaixament(), patient.getGenere(), patient.getTelefon(), patient.getPes(), patient.getAlçada(),patient.getCb());
            data.add(p);
        });
    }


    /*
    * Method that runs every time that the patientList.fxml is loaded.
    * The main function of this method, is create the TableView, and set the data of
    * each patient on it.
    * */
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
        CB.setCellValueFactory(new PropertyValueFactory<CheckBox, BooleanProperty>("cb"));
        tablePatients.getColumns().addAll(DNI, Nom, Cognoms, DataNaix, Gender, Telefon, pes, Alçada,CB);
        tablePatients.setItems(data);
    }

    //TODO: Renombrar el método a fila doble-clickada.
    // (Preguntar si quieres editarlo o borrarlo o salir o añadirlo a lista de espera.
    public void xd() {
        //System.out.println(tablePatients.getSelectionModel().getSelectedItem());
        ObservableList<TablePosition> xd = tablePatients.getSelectionModel().getSelectedCells();
        xd.forEach(tablePosition -> {
            System.out.println( "Columna presionada : "+tablePosition.getColumn());
            System.out.println("Fila presionada: "+tablePatients.getSelectionModel().getFocusedIndex());
        });
    }

    //TODO: Renombrar método a ManageList.
    //TODO: Detectar con cual botón hemos clickado, y segun cual sea, hacer una cosa u otra.
    public void xd2(ActionEvent event) {
        ObservableList<Patient> dataToDelete = FXCollections.observableArrayList();
        tablePatients.getItems().forEach(patient -> {
            if(patient.getCb().isSelected()) {
                System.out.println("selecionada");
                dataToDelete.add(patient);
            }else {
                System.out.println("false");
            }
        });
        data.removeAll(dataToDelete);
        String[] boton = event.getSource().toString().split("'");
        System.out.println(boton[1]);
    }

}
