package app;
//todo:o clasa este formata din atribute si metode
public class Masina {
    //atribute
    public String marca;
    public String model;
    public int kilometraj;
    public int pret;
    public int anFabricatie;
    public String modTransmisie;

    //metode

    public  String   descriere(){

        String text="";
        text+="Marca "+marca+"\n";
        text+="Model "+model+"\n";
        text+="Kilometraj "+kilometraj+"km"+"\n";
        text+="Pret: $" +pret+"\n";
        text+="An fabricatie: " +anFabricatie +"\n";
        text+="Mod Transmisie: " +modTransmisie+"\n";


        return  text;

    }



}
