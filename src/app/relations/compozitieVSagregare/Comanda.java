package app.relations.compozitieVSagregare;

import java.util.ArrayList;
import java.util.List;

public class Comanda {
    private int nrComanda = 1;
    private double total;
    private List<LinieComanda> arrLinii = new ArrayList<>();

    //detine Liniile

    public Comanda(){

        nrComanda++;
    }

    public void adaugaLinie(Produs p, int cantitate){

        LinieComanda linie = new LinieComanda(p, cantitate);
        arrLinii.add(linie);
    }

    public List<LinieComanda> getArrLinii(){

        List<LinieComanda> arrLiniiCopy = new ArrayList<>(arrLinii);
        return arrLiniiCopy;
    }

    public double total(){

        for(int i=0; i<getArrLinii().size(); i++){
            total += getArrLinii().get(i).getSubtotal();
        }

        return total;
    }

    public String getTotal(){
        return "Pretul total al comenzii este: " + this.total;
    }
}
