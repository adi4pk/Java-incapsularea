package app.relations.compozitieVSagregare;

public class LinieComanda {

    private static int numarLinie = 1;      //ID contor auto-incremental STATIC
    private int cantitate;
    private Produs produs;
    private double subtotal;


    public LinieComanda(Produs p, int cantitate){
        numarLinie++;
        setProdus(p);
        setCantitate(cantitate);

        setSubtotal();
    }

    public void setCantitate(int cantitate){
        this.cantitate = cantitate;
    }

    public void setProdus(Produs p){
        this.produs = p;
    }

    public void setSubtotal(){

        subtotal = (double) produs.getPret() * cantitate;
    }

    public double getSubtotal(){
        return subtotal;
    }

}
