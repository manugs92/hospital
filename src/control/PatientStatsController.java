package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import model.Patient;
import model.Persona;
import model.WaitingList;
import strings.StringStats;

import java.util.ArrayList;
import java.util.Collection;


public class PatientStatsController {


    //TODO: Crear pieChart de Lista de Espera.

    @FXML
    PieChart genderPieChart,agesPieChart,weightPieChart,sizePieChart,waitListPieChart;

    @FXML
    Text totalMen,totalWomen,maxAgeRanked,minAgeRanked,maxWeightRanked,minWeightRanked,minSizeRanked,maxSizeRanked;

    protected static ObservableList<Patient> data3 = FXCollections.observableArrayList();
    protected static WaitingList waitingList = new WaitingList();


    public static void init(Collection<Patient> patients) {
        data3.clear();
        patients.forEach(patient -> {
            Patient p = new Patient(patient.getDNI(), patient.getNom(), patient.getCognoms(), patient.getDataNaixament(), patient.getGenere(), patient.getTelefon(), patient.getPes(), patient.getAlçada(),new CheckBox());
            data3.add(p);
        });

    }

    public static void updateWaitingList(WaitingList wl) {
        waitingList=wl;
    }

    public void initialize() {
        createCharts();
    }

    public void createCharts() {
        createGenderPieChart();
        createYearsPieChart();
        createWeightPieChart();
        createSizePieChart();
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
        int min = 0;
        int max = 0;
        ArrayList<Integer> ageslist = new ArrayList<>();
        for(int i=0;i<=data3.size()-1;i++) {
            final int patientAge = data3.get(i).getEdat();
            if(!ageslist.contains((int)patientAge)) { agesPieChart.getData().add(new PieChart.Data(String.valueOf(patientAge),patientAge)); }
            ageslist.add((int) patientAge);
            if(min>patientAge || min==0) { min = patientAge; }
            if(max<patientAge) { max = patientAge; }
        }
        agesPieChart.setTitle(StringStats.TITLE_AGE_PIE_CHART);
        String maxYearRankedString = StringStats.MAX_AGE_RANKED+" "+max;
        String minYearRankedString = StringStats.MIN_AGE_RANKED+" "+min;
        maxAgeRanked.setText(maxYearRankedString);
        minAgeRanked.setText(minYearRankedString);

    }

    public void createWeightPieChart() {
        weightPieChart.getData().clear();
        float min = 0;
        float max = 0;
        ArrayList<Float> weighlist = new ArrayList<>();
        for(int i=0;i<=data3.size()-1;i++) {
            final float patientWeight = data3.get(i).getPes();
            if(!weighlist.contains(patientWeight)) { weightPieChart.getData().add(new PieChart.Data(String.valueOf(patientWeight),patientWeight));}
            weighlist.add((float)patientWeight);
            if(min>patientWeight || min==0) { min =  patientWeight; }
            if(max<patientWeight) { max =  patientWeight; }
        }
        weightPieChart.setTitle(StringStats.TITLE_WEIGHT_PIE_CHART);
        String maxYearRankedString = StringStats.MAX_WEIGHT_RANKED+" "+max;
        String minYearRankedString = StringStats.MIN_WEIGHT_RANKED+" "+min;
        maxWeightRanked.setText(maxYearRankedString);
        minWeightRanked.setText(minYearRankedString);
    }

    public void createSizePieChart() {
        sizePieChart.getData().clear();
        int min = 0;
        int max = 0;
        ArrayList<Integer> sizelist = new ArrayList<>();
        for(int i=0;i<=data3.size()-1;i++) {
            final int patientSize = data3.get(i).getAlçada();
            if(!sizelist.contains(patientSize)) { sizePieChart.getData().add(new PieChart.Data(String.valueOf(patientSize),patientSize));}
            sizelist.add((int)patientSize);
            if(min>patientSize || min==0) { min =  patientSize; }
            if(max<patientSize) { max =  patientSize; }
        }
        sizePieChart.setTitle(StringStats.TITLE_SIZE_PIE_CHART);
        String maxYearRankedString = StringStats.MAX_SIZE_RANKED+" "+max;
        String minYearRankedString = StringStats.MIN_SIZE_RANKED+" "+min;
        maxSizeRanked.setText(maxYearRankedString);
        minSizeRanked.setText(minYearRankedString);
    }
}
