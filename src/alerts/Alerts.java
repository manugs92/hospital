package alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;
import strings.StringAlerts;

import java.util.Optional;

public class Alerts {

    /*
    * Alert that it's showed when the config file is not present.
    * */
    public static boolean configFileNotFound() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(StringAlerts.ERROR);
        alert.setHeaderText(StringAlerts.HEADER_ALERT_CONFIG_FILE_NOT_FOUNDED);
        alert.setContentText(StringAlerts.CONTENT_ALERT_CONFIG_FILE_NOT_FOUNDED);
        alert.getButtonTypes().setAll(ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.YES) {
            return true;
        }else {
            return false;
        }
    }

    /*
    * Alert showed when the file selected isn't be a CSV.
    * */
    public static boolean errorCSVNotSelected() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(StringAlerts.ERROR);
        alert.setHeaderText(StringAlerts.HEADER_ALERT_CSV_NOT_SELECTED);
        alert.setContentText(StringAlerts.CONTENT_ALERT_CSV_NOT_SELECTED);
        alert.getButtonTypes().setAll(ButtonType.OK,ButtonType.CLOSE);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK) {
            return true;
        }else {
            return false;
        }
    }

    /*
    * Alert showed to set the hospital name.
    * */
    public static String setHospitalName() {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle(StringAlerts.WINDOW_INPUT_TEXT);
        textInputDialog.setHeaderText(StringAlerts.HEADER_ALERT_INSERT_HOSPITAL_NAME);
        textInputDialog.setContentText(StringAlerts.CONTENT_ALERT_INSERT_HOSPITAL_NAME);
        textInputDialog.initStyle(StageStyle.UTILITY);
        Optional<String> hospitalName = textInputDialog.showAndWait();
        if(hospitalName.isEmpty()) {
            return "";
        }else {
            return hospitalName.get();
        }
    }

    /*
    * Alert showed when the hospital name it's empty.
    * */
    public static void hospitalNameIsEmpty() {
        Alert alert= new Alert(Alert.AlertType.ERROR);
        alert.setTitle(StringAlerts.ERROR);
        alert.setHeaderText(StringAlerts.HEADER_ALERT_EMPTY_HOSPITAL_NAME);
        alert.setContentText(StringAlerts.CONTENT_ALERT_EMPTY_HOSPITAL_NAME);
        alert.showAndWait();
    }

    public static void editAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(StringAlerts.WARNING);
        alert.setContentText(StringAlerts.CONTENT_ALERT_EDIT_METHOD);
        alert.showAndWait();
    }

    public static boolean confirmDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(StringAlerts.WARNING);
        alert.setHeaderText(StringAlerts.HEADER_ALERT_CONFIRM_DELETE_PATIENTS);
        alert.setContentText(StringAlerts.CONTENT_ALERT_CONFIRM_DELETE_PATIENTS);
        alert.getButtonTypes().setAll(ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.YES) {
            return true;
        }else {
            return false;
        }
    }
}
