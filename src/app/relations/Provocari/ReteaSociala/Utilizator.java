package app.relations.Provocari.ReteaSociala;

import java.util.ArrayList;
import java.util.List;

public class Utilizator {

    private String nume;
    private int varsta;
    private String meserie;
    private String oras;

    private ArrayList<Utilizator> prieteni = new ArrayList<>();


    public Utilizator(String nume, int age, String job, String oras){

        setNume(nume);
        setVarsta(age);
        setMeserie(job);
        setOras(oras);
    }

    //setter
    public void setNume(String nume){
        this.nume = nume;
    }
    public void setVarsta(int varsta){
        this.varsta = varsta;
    }
    public void setMeserie(String meserie){
        this.meserie = meserie;
    }
    public void setOras(String oras){
        this.oras = oras;
    }

    public void setPrieten(Utilizator prieten){
        prieteni.add(prieten);
    }


    //getter
    public String getNume(){
        return nume;
    }


    public void imprietenire(Utilizator pers){
        prieteni.add(pers);
        pers.setPrieten(this);
    }

    public ArrayList<Utilizator> getPrieteni(){
        ArrayList<Utilizator> shallowPrieteni = new ArrayList<>(prieteni);
        return shallowPrieteni;
    }

    @Override
    public String toString() {
        return nume;
    }


}
