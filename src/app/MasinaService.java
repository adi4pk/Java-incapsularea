package app;

import java.util.ArrayList;
import java.util.List;

public class MasinaService {
    public List<Masina> masini;

    void  loadMasini(){
        masini = new ArrayList<>();
        Masina a = new Masina();//am creat un obiect de tip masina

        a.anFabricatie=1993;
        a.kilometraj=12;
        a.marca = "Audi";
        a.model = "A4";
        a.pret = 18000;
        a.modTransmisie = "Manuala";



        Masina b = new Masina();

        b.kilometraj=13;
        b.anFabricatie=2010;
        b.marca = "BMW";
        b.model = "X5";
        b.pret = 15000;
        b.modTransmisie = "Automata";

        Masina c = new Masina();
        c.kilometraj = 45000;
        c.anFabricatie = 2019;

        c.marca = "Toyota";
        c.model = "Corolla";
        c.pret = 16000;
        c.modTransmisie = "Manuala";

        Masina d = new Masina();
        d.kilometraj = 54000;
        d.anFabricatie = 2015;

        d.marca = "Mercedes";
        d.model = "C-Class";
        d.pret = 20000;
        d.modTransmisie = "Automata";



        Masina e = new Masina();
        e.kilometraj = 120000;
        e.anFabricatie = 2012;

        e.marca = "Volkswagen";
        e.model = "Golf";
        e.pret = 9000;
        e.modTransmisie = "Manuala";


        Masina f = new Masina();
        f.kilometraj = 75000;
        f.anFabricatie = 2018;

        f.marca = "Ford";
        f.model = "Focus";
        f.pret = 14000;
        f.modTransmisie = "Manuala";


        Masina g = new Masina();
        g.kilometraj = 30000;
        g.anFabricatie = 2020;

        g.marca = "BMW";
        g.model = "X3";
        g.pret = 28000;
        g.modTransmisie = "Automata";


        Masina h = new Masina();
        h.kilometraj = 98000;
        h.anFabricatie = 2014;

        h.marca = "Audi";
        h.model = "A6";
        h.pret = 17000;
        h.modTransmisie = "Automata";


        masini.add(a);
        masini.add(b);
        masini.add(c);
        masini.add(d);
        masini.add(e);
        masini.add(f);
        masini.add(g);
        masini.add(h);


    }

    void afisareMasini(){
        for(int i=0;i< masini.size();i++){
            System.out.println(masini.get(i).descriere());
        }
    }
}
