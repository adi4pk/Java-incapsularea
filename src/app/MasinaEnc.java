package app;

import java.sql.SQLOutput;

public class MasinaEnc {
    //atribute
    private String marca;
    private String model;
    private int kilometraj;
    private int pret;
    private int anFabricatie;
    private String modTransmisie;


    //metode

    public MasinaEnc(){

        this.descriere();
    }

    public MasinaEnc(String marca, String model, int kilometraj, int pret, int anFabricatie, String modTransmisie){
        this.marca = marca;
        this.model = model;



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
        System.out.println(this.marca);
        return this.marca;
    }

    public String getModel(){
        System.out.println(this.model);
        return this.model;
    }

    public int getKm(){
        System.out.println(this.kilometraj);
        return this.kilometraj;
    }

    public int getPret(){
        if (pret > 0) {
            System.out.println(this.pret);
            return this.pret;
        } else return 0;
    }

    public void setPret(int pret){
        if (pret > 0) {
            this.pret = pret;
            System.out.println(this.pret);
        } else System.out.println("Pretul nu poate fi mai mai mic decat 0.");
    }

    public void setKilometraj(int kilometraj){
        if(kilometraj >=0){
            this.kilometraj = kilometraj;
        } else System.out.println("Kilometrajul nu poate fi 0 sau mai mic");
    }

    public void setAnFabricatie(int an){
        if(anFabricatie >=1950 && anFabricatie<=2025){
            this.anFabricatie = anFabricatie;
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
}

