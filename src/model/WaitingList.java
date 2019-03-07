package model;


import java.util.HashMap;
import java.util.Map;

public class WaitingList {

    //TODO: Añadir Paciente, motivo, fecha.
    //TODO: Visualizar la lista de espera por "motivos" o por fechas.
    //TODO: Guardarla en un .csv con el mismo nombre que el csv asignado a este hospital.

    private Map<Patient,String> mapWaitingList = new HashMap<>();


    public void setPatient(Patient patient,String reason) {
        this.mapWaitingList.put(patient,reason);
    }

    public int getSize() {
        return this.mapWaitingList.size();
    }
}
