package app.relations.OneToMany;

import java.util.ArrayList;
import java.util.List;

public class Autor {
    private List<Carte> carti = new ArrayList<>();
    private String nume;



    public Autor(String nume){


        setNume(nume);
    }

    public void adaugaCarte(Carte carte){
        if(carte == null){
            System.out.println("Cartea este null");
            return;
        } else if(carti.contains(carte)){

            System.out.println("Cartea este deja in lista.");
        } else carti.add(carte);
    }

    public List<Carte> getCarti(){
        List<Carte> copieCarti = new ArrayList<>(carti);
        return copieCarti;
    }

    public void setNume(String nume){
        this.nume = nume;
    }


}
