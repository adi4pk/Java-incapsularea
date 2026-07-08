package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContBancar {

    private String titular;
    private double sold;


//    List<String> titulari = Arrays.asList("popescu", "ionescu", "pancu");

    // todo public ContBancar() // gol
    // todo public ContBancar(String titular) // doar titular, sold 0
    // todo public ContBancar(String titular, double soldInitial)

    public void setTitular(String titular){

        if(titular !=null && titular !=""){
            this.titular = titular;
            sold = 0;
        }

    }

//    public ContBancar(){
//
//        System.out.println("gol");
//        sold = 0;
//    }

    public ContBancar(String titular){

        if(titular !=null){
            this.titular = titular;
            sold = 0;
//            System.out.println(titular);
        }

        System.out.println(this.descriere());       //printeaza ce returneaza functia descriere();


    }

    public ContBancar(String titular, double soldInitial){

        if(titular != null){
            this.sold = soldInitial;
        }
    }

    public void getSold(String titular){

        if(titular !=null && titular !=""){
            System.out.println("Soldul tau curent este: " +sold + "LEI");
        } else{
            System.out.println("Titularul nu exista");
        }

    }


    //DEPUNERE
    public void depune(double suma){

        if(suma > 0){
            this.sold += suma;
            System.out.println("Ai depus: " + suma);
        } else{
            System.out.println("Suma depusa trebuie sa fie pozitiva.");
        }
    }


    //RETRAGERE
    public void retrage(double suma){
        if(suma >0 && suma < sold){
            this.sold -= suma;
            System.out.println("Ai retras suma de: " +suma);

        } else{
            System.out.println("Fonduri insuficiente sau suma invalida");
        }
    }


    public String descriere(){
        if(titular == null){
            System.out.println("Titularul nu exista.");
            return null;
        }

//        System.out.println(titular + " - sold: " +sold);

        String text ="";
        text+="Titular: " + titular +"\n";
        text+="Sold: " + sold;

        return text;
    }


}
