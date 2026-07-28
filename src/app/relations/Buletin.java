package app.relations;

public class Buletin {
    private String serie;
    private int numar;
    private Persoana persoana;


    public Buletin(String serie, int numar){

        setPersoana(persoana);
        setSerie(serie);
        setNumar(numar);
    }



    public void setSerie(String serie){
        this.serie = serie;
    }

    public void setNumar(int numar){
        this.numar = numar;
    }

    public void setPersoana(Persoana persoana) {
        this.persoana = persoana;
    }

    public String getSerie(){
        if (serie.isEmpty()) {
            return null;
        }
        else return serie;}

    public Persoana getPersoana(){
        return persoana;
    }

    public int getNumar(){return numar;}
}
