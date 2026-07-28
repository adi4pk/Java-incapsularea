package app.relations.manyToMany;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private String nume;
    private int varsta;
    private List<Curs> cursuri = new ArrayList<>();

    public Student(String nume, int varsta){
        this.nume = nume;
        this.varsta = varsta;
    }

    public List<Curs> getCursuri(){

        List<Curs> cursuriCopie = new ArrayList<>(cursuri);
//        System.out.println("Cursuri inrolate: " + cursuriCopie);
        return cursuriCopie;
    }

    public void setNume(String nume){
        this.nume = nume;

    }

    public void setVarsta(int varsta){
        this.varsta = varsta;
    }

    void adaugaCurs(Curs curs){

        if (curs != null){
            if(cursuri.contains(curs)){
                System.out.println("Studentul este deja inrolat la cursul acesta.");
                return;
            }
            cursuri.add(curs);
        }

    }

    void removeCurs(Curs curs){
        cursuri.remove(curs);


    }

    @Override
    public String toString(){
        return nume;
    }

    public String detaliiStudent(){

        String text = "";
        text += "nume: " + nume +"\n";
        text+= "varsta: " + varsta +"\n";
        text+= "Cursuri inrolate: " + getCursuri();

        return text;
    }
}
