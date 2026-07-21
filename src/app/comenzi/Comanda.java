package app.comenzi;

import java.util.ArrayList;
import java.util.List;

public class Comanda {

    private String client;

    //NOT static - o comanda are propria lista
    //Lista de liniiComanda nu se imparte la toate comenzile.
    private List<LinieComanda> listaLinii = new ArrayList<>();

    public Comanda(String client){
        if(!client.isEmpty()){
            this.client = client;
        }

    }

    public void adaugaLinie(Produs produs, int cantitate){

        if(cantitate >=1) {
            LinieComanda lineItem = new LinieComanda(produs, cantitate);
            listaLinii.add(lineItem);
        }

    }

    public String descriere(){

        System.out.println(client);
        for(int i=0; i<listaLinii.size();i++){
            System.out.println(listaLinii.get(i).lineDetails());
        }

        total();
        return "";
    }

    public double total(){

        double total = 0;
        for(int i=0; i<listaLinii.size(); i++ ){

            double pretLinie = listaLinii.get(i).subtotal();
            total+=pretLinie;
        }

        System.out.println("Totalul comenzii lui " +client + " este: " +total + " LEI");
        return total;
    }
}
