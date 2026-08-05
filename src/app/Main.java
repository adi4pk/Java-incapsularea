package app;


import app.relations.Buletin;
import app.relations.OnToManyBidirectional.Echipa;
import app.relations.OnToManyBidirectional.Player;
import app.relations.Provocari.Autor;
import app.relations.Provocari.Carte;
import app.relations.Provocari.Editura;
import app.relations.Provocari.ReteaSociala.Utilizator;
import app.relations.manyToMany.Curs;
import app.relations.manyToMany.Student;
import app.simple.Banca.ContBancar;

//import app.simple.catalog.Catalog;
//import app.simple.comenzi.Comanda;
//import app.simple.comenzi.Produs;
import app.simple.masini.Masina;
import app.simple.masini.MasinaEnc;
import app.relations.Persoana;

import app.relations.compozitieVSagregare.Comanda;
import app.relations.compozitieVSagregare.LinieComanda;
import app.relations.compozitieVSagregare.Produs;

public class Main {
    public static void main(String[] args) {

//      ContBancar contBancarTest=  ex4();

//        ex4();

//        ContBancar contBancar = new ContBancar("test");

//        ex05();
//       creareObiecte();
//        demoAliasing();
//        demoNrInstante();
//
//        creareCatalog();
//        Nivel2();

//        exRelationare();
//        oneToMany_Echipa();
//        manyToMany_Student_Curs();
//        level4_Comanda();
//        level4_Autor_provocare();
        ReteaSociala();
    }





//    public  static  void ex1(){
//        Persoana pers1 = new Persoana();
//
//        Persoana pers2= new Persoana(12);
//
////
////        pers1.setNume("ana");
////        pers1.setVarsta(-1);
////        pers1.setOras("Bucuresti");
//
//        Persoana pers3 = new Persoana("Adi", 30, "Craiova", "masculin");
//    }

    public static ContBancar ex4(){



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

        a.setPret(666);
        System.out.println("pret inainte de aliasing " + a.getPret());

        b.setPret(999);
        System.out.println(a.getPret());
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

        System.out.println(MasinaEnc.getNrInstante());


    }


//    public static void creareCatalog(){
//
//        Catalog x = new Catalog();
//
//        x.adauga("banane");
//    }

//    public static void Nivel2(){
//
//        Produs p1 = new Produs("laptop", 900);
//        Produs p2 = new Produs("tableta", 450);
//        Produs p3 = new Produs("cafea", 50);
//
//        Comanda c1 = new Comanda("Adrian");
//        c1.adaugaLinie(p1, 1);
//        c1.adaugaLinie(p3, 10);
//
//        c1.descriere();
//
////        c1.total();
//
//    }


    public static void exRelationare(){

        Persoana pers1 = new Persoana("Andrei", 20);

        Persoana pers2 = new Persoana("Bogdan", 30);


        Buletin b1 = new Buletin("VECHI", 12345);
        Buletin b2 = new Buletin("NOU", 17);

        pers1.atribuieBuletin(b1);
        System.out.println(pers1.descriere());



        pers1.atribuieBuletin(b2);
        System.out.println(pers1.descriere());

        System.out.println(b2.getPersoana().descriere());
        System.out.println(b1.getPersoana().descriere());

    }

    public static void oneToMany_Echipa(){

        Player j1 = new Player("Dembele", 25);

        Player j2 = new Player("Mbappe", 27);

        Echipa realMadrid = new Echipa("Real Madrid");
        realMadrid.transferEchipa(j2);



        System.out.println(realMadrid.getJucatori());
        System.out.println(j1.detailsJucator());
        System.out.println(j1.detailsJucator());

        Echipa psg = new Echipa("Paris Saint-German");
        psg.transferEchipa(j1);

        System.out.println(j1.detailsJucator());

        Player j3 = new Player("Diomande", 19);

        Echipa bvbDortmund = new Echipa("Borussia Dortmund");
        Player j4 = new Player("Adeyemi", 25);
        bvbDortmund.transferEchipa(j4);

        Player j5 = new Player("Olise", 23);

        Echipa bayernMuenchen = new Echipa("Bayern Muenchen");
        bayernMuenchen.transferEchipa(j4);
        bayernMuenchen.transferEchipa(j5);

        System.out.println(bayernMuenchen.getJucatori());

        bayernMuenchen.incheieContract(j1);
        bayernMuenchen.incheieContract(j4);

        System.out.println(j4.getEchipa());
        System.out.println(bayernMuenchen.getJucatori());

        j5.setEchipa(bvbDortmund);      //BUG - nu apelam .setEchipa prin intermediul jucatorului
                                        // .setEchipa se apeleaza din clasa Echipa, metoda transferEchipa()
        System.out.println(bvbDortmund.getJucatori());

    }

    public static void manyToMany_Student_Curs(){

        Student s1 = new Student("Popescu", 20);
        Student s2 = new Student("Mihailovic", 20);
        Student s3 = new Student("Buia", 20);
        Student s4 = new Student("Dumitru", 20);


        Curs c1 = new Curs("Germana");
        Curs c2 = new Curs("Desen");
        Curs c3 = new Curs("Economie");

        c1.inscrieStudent(s1);
        System.out.println(s1.detaliiStudent());

        c1.inscrieStudent(s2);
        c1.inscrieStudent(s3);
        System.out.println(c1.getStudenti());

        c2.inscrieStudent(s1);
        System.out.println(s1.getCursuri());

//        s1.getCursuri();

    }

    public static void level4_Comanda(){

        Produs p1 = new Produs("Cafea", 100);
        Produs p2 = new Produs("Tiramisu", 250);
        Produs p3 = new Produs("Apa", 20);

        Comanda c1 = new Comanda();

        c1.adaugaLinie(p2, 10);
        c1.adaugaLinie(p3, 4);

        System.out.println(c1.getTotal());
        c1.total();
//        System.out.println(c1.total());
//        System.out.println(c1.total());
        System.out.println(c1.getTotal());
        c1.total();
        System.out.println(c1.getTotal());
    }

    public static void level4_Autor_provocare(){


                // Autori
                Autor Eminescu = new Autor("Mihai Eminescu");
                Autor Doe = new Autor("John Doe");
                Autor Martin = new Autor("George R. R. Martin");

                // Edituri
                Editura humanitas = new Editura("Humanitas");
                Editura abc = new Editura("Polirom");

                // Carti
                Carte c1 = new Carte("Luceafarul", Eminescu, humanitas);
                Carte c2 = new Carte("Poezii", Eminescu, humanitas);
                Carte c3 = new Carte("Basme", Eminescu, abc);
                Carte c4 = new Carte("A song of Ice and Fire", Martin, abc);

                Carte c5 = new Carte("Humankind", Doe, humanitas);

                // Afisare carti Eminescu la Humanitas
        Eminescu.cartileAutoruluiLaEditura(humanitas);

        Martin.cartileAutoruluiLaEditura(abc);
    }

    public static void ReteaSociala(){

        Utilizator user1 = new Utilizator("Fane", 45, "faiantar", "Bucuresti");
        Utilizator user2 = new Utilizator("Andrei", 30, "corporatist", "Cluj");

        user1.imprietenire(user2);
        System.out.println("Prietenii lui " + user1.getNume() + " sunt: " + user1.getPrieteni());
        System.out.println("Prietenii lui " + user2.getNume() + " sunt: " + user2.getPrieteni());


    }

}