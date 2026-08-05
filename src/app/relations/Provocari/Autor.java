package app.relations.Provocari;
import java.util.ArrayList;
import java.util.List;

public class Autor {
    private String nume;
    private List<Carte> carti;


    public Autor(String nume) {
        this.nume = nume;
        this.carti = new ArrayList<>();
    }

    //getter
    public String getNume() {
        return nume;
    }

    public List<Carte> getCarti() {
        return carti;
    }


    //setter
    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setCarti(List<Carte> carti) {
        this.carti = carti;
    }


    public void adaugaCarte(Carte carte) {
        carti.add(carte);
    }
    @Override
    public String toString() {
        return nume;
    }

    public void cartileAutoruluiLaEditura(Editura edt) {

        // for each
//        for (Carte carte : carti) {
//        -> ia pe rând fiecare Carte din lista carti și pune-o în variabila temporară carte.

        for(int i=0; i<carti.size(); i++){
            Carte carte = carti.get(i);
            if (carte.getEditura().equals(edt)) {

                System.out.println(carte.getTitlu());
            }
        }

    }
}
