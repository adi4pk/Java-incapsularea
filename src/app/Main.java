package app;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//      ContBancar contBancarTest=  ex4();

//        ex4();

//        ContBancar contBancar = new ContBancar("test");

        ex05();
    }


    public  static  void ex1(){
        Persoana pers1 = new Persoana();

        Persoana pers2= new Persoana(12);

//
//        pers1.setNume("ana");
//        pers1.setVarsta(-1);
//        pers1.setOras("Bucuresti");

        Persoana pers3 = new Persoana("Adi", 30, "Craiova", "masculin");
    }

    public static  ContBancar ex4(){



        ContBancar cont1 = new ContBancar("Popescu");
//        ContBancar cont2 =cont1;

//        cont2.setTitular("ana");


//        System.out.println(cont1.descriere());

        cont1.getSold("Alex");

        cont1.depune(100);

        cont1.getSold("dasd");

        cont1.retrage(50);

        return cont1;
    }


    public static void ex05(){

        MasinaEnc masinaEncapsed_1 = new MasinaEnc("BMW", "X4", -1, 1666, 1853, "Hibrid");

        System.out.println(masinaEncapsed_1);

        System.out.println(masinaEncapsed_1.descriere());
    }


}