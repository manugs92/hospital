package control;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.text.Text;
import model.Persona;


public class PatientStatsController extends PatientsListController {


    @FXML
    PieChart genderPieChart,yearsPieChart,weightPieChart,sizePieChart,waitListPieChart;

    @FXML
    Text totalMen,totalWomen;

    @Override
    public void initialize() {
        genderPieChart.getData().clear();
        long dones = data.stream()
                .filter(pacient -> pacient.getGenere() == Persona.Genere.DONA)
                .count();
        long homes = data.stream()
                .filter(pacient -> pacient.getGenere() == Persona.Genere.HOME)
                .count();
        genderPieChart.setTitle("Gènere");
        genderPieChart.getData().add(new PieChart.Data(Persona.Genere.DONA.toString(), dones));
        genderPieChart.getData().add(new PieChart.Data(Persona.Genere.HOME.toString(), homes));
        StringBuilder text = new StringBuilder();
        text.append(totalMen.getText() + " " + homes);
        totalMen.setText(text.toString());
        text = new StringBuilder();
        text.append(totalWomen.getText()+" "+dones);
        totalWomen.setText(text.toString());
    }
}
