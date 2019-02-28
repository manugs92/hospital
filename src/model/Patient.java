package model;


import javafx.scene.control.CheckBox;

import java.time.LocalDate;

public class Patient extends Persona {

    private float Pes;
    private int Alçada;
    private String Telefon, DNI;
    private CheckBox cb;


    public CheckBox getCb() {
        return cb;
    }

    public void setCb(CheckBox cb) {
        this.cb = cb;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String dni) {
        DNI = dni;
    }

    public Patient(String dni, String Nom, String Cognoms, LocalDate dataN, Genere g, String telf, float p, int a,CheckBox cb) {
        super(Nom, Cognoms, g, dataN);
        DNI = dni;
        Pes = p;
        Alçada = a;
        Telefon = telf;
        this.cb=cb;
    }

    public float getPes() {
        return Pes;
    }

    public void setPes(float pes) {
        Pes = pes;
    }

    public int getAlçada() {
        return Alçada;
    }

    public void setAlçada(int alçada) {
        Alçada = alçada;
    }
}