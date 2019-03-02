package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import model.Patient;
import model.Persona;
import strings.StringStats;

import java.util.ArrayList;
import java.util.Collection;


public class PatientStatsController {

    @FXML
    PieChart genderPieChart,agesPieChart,weightPieChart,sizePieChart,waitListPieChart;

    @FXML
    Text totalMen,totalWomen,maxAgeRanked,minAgeRanked;

    protected static ObservableList<Patient> data3 = FXCollections.observableArrayList();


    public static void init(Collection<Patient> patients) {
        data3.clear();
        patients.forEach(patient -> {
            Patient p = new Patient(patient.getDNI(), patient.getNom(), patient.getCognoms(), patient.getDataNaixament(), patient.getGenere(), patient.getTelefon(), patient.getPes(), patient.getAlçada(),new CheckBox());
            data3.add(p);
        });
    }

    public void initialize() {
        createCharts();
    }

    public void createCharts() {
        createGenderPieChart();
        createYearsPieChart();
    }

    public void createGenderPieChart() {
        genderPieChart.getData().clear();
        long dones = data3.stream()
                .filter(pacient -> pacient.getGenere() == Persona.Genere.DONA)
                .count();
        long homes = data3.stream()
                .filter(pacient -> pacient.getGenere() == Persona.Genere.HOME)
                .count();
        genderPieChart.setTitle(StringStats.TITLE_GENDER_PIE_CHART);
        genderPieChart.getData().add(new PieChart.Data(StringStats.WOMEN_DATA, dones));
        genderPieChart.getData().add(new PieChart.Data(StringStats.MEN_DATA, homes));
        String totalMenString = StringStats.MEN_QUANTITY+" " + homes;
        String totalWomenString = StringStats.WOMEN_QUANTITY+" "+dones;
        totalMen.setText(totalMenString);
        totalWomen.setText(totalWomenString);
    }

    public void createYearsPieChart() {
        agesPieChart.getData().clear();
        long min = 0;
        long max = 0;
        long[] ages = new long[100];
        ArrayList<Integer> agesaddes = new ArrayList<>();
        for(int i=0;i<data3.size();i++) {
            final long patientYear = data3.get(i).getEdat();
            ages[i] = data3.stream()
                    .filter(patient -> patient.getEdat() == patientYear)
                    .count();
            if(!agesaddes.contains((int)patientYear)) {
                agesPieChart.getData().add(new PieChart.Data(String.valueOf(patientYear),ages[i]));
            }
            agesaddes.add((int) patientYear);
            i = i+1;
            if(min>patientYear || min==0) {
                min = patientYear;
            }
            if(max<patientYear) {
                max = patientYear;
            }
        }
        agesPieChart.setTitle(StringStats.TITLE_AGE_PIE_CHART);
        String maxYearRankedString = StringStats.MAX_AGE_RANKED+" "+max;
        String minYearRankedString = StringStats.MIN_AGE_RANKED+" "+min;
        maxAgeRanked.setText(maxYearRankedString);
        minAgeRanked.setText(minYearRankedString);

    }
}
