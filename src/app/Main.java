package app;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//      ContBancar contBancarTest=  ex4();

        ex4();

//        ContBancar contBancar = new ContBancar("test");

//        ex05();
//       creareObiecte();
//        demoAliasing();
//        demoNrInstante();
//
//        creareCatalog();
//        Nivel2();

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



        ContBancar cont1 = new ContBancar("Popescu", 1000);
//        ContBancar cont2 =cont1;

//        cont2.setTitular("ana");


//        System.out.println(cont1.descriere());

//        cont1.getSold("Alex");
//
        cont1.depune(100);
//
//        cont1.getSold("dasd");
//
        cont1.retrage(50);
        cont1.blocheaza();

        cont1.deblocheaza();
        cont1.depune(1000);
        cont1.extras();

        return cont1;
    }

    public static void creareObiecte(){

        ContBancar cont_1 = new ContBancar("Popescu");
        ContBancar cont_2 = new ContBancar("Buia");

        System.out.println(cont_1.descriere());
        System.out.println(cont_2.descriere());
    }


    public static void ex05(){

        MasinaEnc masinaEncapsed_1 = new MasinaEnc("BMW", "X4", -1, 1666, 1853, "Hibrid");

        System.out.println(masinaEncapsed_1);       // returneaza adresa din memorie a Obiectului

        System.out.println(masinaEncapsed_1.descriere());
    }

    public static void demoAliasing(){
        Masina a = new Masina();
        Masina b = a;

        a.pret = 666;
        System.out.println("pret inainte de aliasing " + a.pret);

        b.pret = 999;
        System.out.println(a.pret);
    }

    public  static void demoInstanteSeparate(){

        Masina x = new Masina();
        Masina y = new Masina();

        System.out.println(x==y);
    }


    public static void demoNrInstante(){

        new MasinaEnc();
        new MasinaEnc();
        new MasinaEnc();

        System.out.println(MasinaEnc.nrInstante);


    }


    public static void creareCatalog(){

        Catalog x = new Catalog();

        x.adauga("banane");
    }

    public static void Nivel2(){

        Produs p1 = new Produs("laptop", 900);
        Produs p2 = new Produs("tableta", 450);
        Produs p3 = new Produs("cafea", 50);

        Comanda c1 = new Comanda("Adrian");
        c1.adaugaLinie(p1, 1);
        c1.adaugaLinie(p3, 10);

        c1.descriere();

//        c1.total();

    }

}