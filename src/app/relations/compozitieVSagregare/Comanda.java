package app.relations.compozitieVSagregare;

import java.util.ArrayList;
import java.util.List;

public class Comanda {
    private static int nrComanda = 1;   //ID contor auto-incremental STATIC
    private int idComanda;
    private double total;
    private List<LinieComanda> arrLinii = new ArrayList<>();

    //detine Liniile

    public Comanda(){

        nrComanda++;
        this.idComanda = nrComanda;
    }

    public void adaugaLinie(Produs p, int cantitate){

        LinieComanda linie = new LinieComanda(p, cantitate);
        arrLinii.add(linie);

    }

    public List<LinieComanda> getArrLinii(){
        return arrLinii;
    }

    public void total(){

        total=0;


        for(int i=0; i<arrLinii.size(); i++){
            total += arrLinii.get(i).getSubtotal();
        }
    }

    public String getTotal(){
        total();
        return "Pretul total al comenzii este: LEI " + this.total;
    }
}
