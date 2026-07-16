package app;

public class Produs {

    private String name;
    private double pret;

    public Produs(String name, double pret){
        this.name = name;
        this.pret = pret;
    }

    public double getPretProdus(){
        return pret;
    }

    public String getNumeProd(){
        return name;
    }
}
