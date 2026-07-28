package app.relations.compozitieVSagregare;

public class LinieComanda {

    private int numarLinie = 1;
    private int cantitate;
    private Produs produs;
    private double total;


    public LinieComanda(Produs p, int cantitate){
        numarLinie++;
        setProdus(p);
        setCantitate(cantitate);
    }

    public void setCantitate(int cantitate){
        this.cantitate = cantitate;
    }

    public void setProdus(Produs p){
        this.produs = p;
    }

    public double subtotal(){

        total += produs.getPret() * cantitate;
        return total;
    }

}
