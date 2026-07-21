package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Persoana {

    //ATRIBUTE
    private String nume;
    private int varsta;
    private String oras;
    private String gen;


    //todo: constructori
    //todo: metode ce se apelaza automat la instatiere
    // au acelasi nume ca si clasa
    // se pot supraincarca
    //nu au tip returnat


    public Persoana(){

        System.out.println("Eu sunt constructorul fara paramterii");
        this.descriere();

    }

    public Persoana(int varsta){

        this.setVarsta(varsta);

    }

    public Persoana(String nume,int varsta ,String oras,String gen){

        this.setNume(nume);
        this.setVarsta(varsta);
        this.setOras(oras);
        this.setGen(gen);



    }





    //modificattori de acces


    public  void  setNume(String nume){

        List<String> blacklist= Arrays.asList("ana","maria","alex");
        if(!blacklist.contains(nume)){
            this.nume=nume;
        }else{
            System.out.println("numele este pe lista neagra");
        }
    }


    public void setVarsta(int varsta){
        if(varsta >= 0){
            this.varsta = varsta;
        } else {
            System.out.println("Varsta nu poate fi mai mica de 0.");
        }
    }


    public void setOras(String oras){

        List<String> whiteList = Arrays.asList("craiova", "cluj", "sibiu", "timisoara");
        if(whiteList.contains(oras.toLowerCase())){
            this.oras = oras;
        } else{
            System.out.println("Orasul selectat nu se afla pe lista");
        }
    }

    public void setGen(String gen){

        if(gen.equalsIgnoreCase("masculin") || gen.equalsIgnoreCase("feminin") || gen.equalsIgnoreCase("altul")){
            this.gen = gen;
        } else{
            System.out.println("selecteaza un gen din lista");
        }
    }

    public String descriere(){

        String text = "";
        text+= "Nume " + nume +'\n';
        text+= "Varsta " + varsta + '\n';
        text+= "Oras " + oras + '\n';
        text+= "Gen " + gen + '\n';

        return text;
    }


    //getters
    public String getNume(){return nume;}

    public int getVarsta(){return varsta;}

    public String getOras(){return oras;}

    public String getGen(){return gen;}

}
