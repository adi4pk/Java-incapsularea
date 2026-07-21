package app.simple.masini;
//todo:o clasa este formata din atribute si metode
public class Masina {
    //atribute
    private String marca;
    private String model;
    private int kilometraj;
    private double pret;
    private int anFabricatie;
    private String modTransmisie;

    //todo must add a getter for nrInstante;
    private static int nrInstante = 0;

    //metode

    public Masina(){

        this.descriere();
        nrInstante++;

    }

    //todo daca un camp numara instantele, fiecare Constructor() trebuie sa-l incrementeze.


    public Masina(String marca, String model, int kilometraj, int pret, int anFabricatie, String modTransmisie){
        this.marca = marca;
        this.model = model;


        setKilometraj(kilometraj);
        setPret(pret);
        setAnFabricatie(anFabricatie);
        setModTransmisie(modTransmisie);

        nrInstante++;

    }

    public  String descriere(){

        String text="";
        text+="Marca "+marca+"\n";
        text+="Model "+model+"\n";
        text+="Kilometraj "+kilometraj+"km"+"\n";
        text+="Pret: $" +pret+"\n";
        text+="An fabricatie: " +anFabricatie +"\n";
        text+="Mod Transmisie: " +modTransmisie+"\n";


        return  text;

    }

    public String getMarca(){
        return marca;
    }

    public String getModel(){
        return model;
    }

    public int getKm(){
        return kilometraj;
    }

    public double getPret(){
        return pret;
    }

    public int getAnFabricatie(){
        return anFabricatie;
    }

    public String getModTransmisie(){
        return modTransmisie;
    }

    public void setPret(double pret){
        if (pret > 0) {
            this.pret = pret;
            System.out.println(pret);
        } else System.out.println("Pretul nu poate fi mai mai mic decat 0.");
    }

    public void setKilometraj(int kilometraj){
        if(kilometraj >0){
            this.kilometraj = kilometraj;
        } else System.out.println("Kilometrajul nu poate fi 0 sau mai mic");
    }

    public void setAnFabricatie(int an){
        if(an >=1950 && an<=2025){
            this.anFabricatie = an;
            System.out.println("Anul fabricatiei este: " +anFabricatie);
        } else System.out.println("Anul fabricatiei nu poate fi mai vechi de 1950 sau mai nou de 2025.");

    }

    public void setModTransmisie(String modTransmisie){
        if(modTransmisie.equals("Manuala") || modTransmisie.equals("Automata")){
            this.modTransmisie = modTransmisie;
        } else{
            System.out.println("Alege un mod de transmisie dintre: manual sau automat");
            return;
            // CONSTRUCTORUL NU POATE RETURNA O VALOARE
        }
    }

    public static int getNrInstante(){
        return nrInstante;
    }

}
