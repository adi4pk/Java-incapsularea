package app.comenzi;

public class LinieComanda {

    //todo atribute
    private Produs produs;
    private int cantitate;

    public LinieComanda(Produs produs, int cantitate){

        this.produs = produs;

        if(cantitate >=1){
            this.cantitate = cantitate;
        } else {
            System.out.println("cantitatea nu poate fi mai mica de 1.");
        }
    }

    public double subtotal(){

        double pretLinie = produs.getPretProdus() * cantitate;
        return pretLinie;
    }


    public String lineDetails(){

        String lineDetails = produs.getNumeProd() + " cantitate: " + cantitate + " subtotal: " +subtotal() ;
        return lineDetails;

    }
}
