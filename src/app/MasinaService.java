package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.text.NumberFormat;



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

        Masina i = new Masina();

        i.kilometraj = 67000;
        i.anFabricatie = 2017;

        i.marca = "Skoda";
        i.model = "Octavia";
        i.pret = 15500;
        i.modTransmisie = "Manuala";


        Masina j = new Masina();

        j.kilometraj = 88000;
        j.anFabricatie = 2013;

        j.marca = "Seat";
        j.model = "Leon";
        j.pret = 11000;
        j.modTransmisie = "Manuala";


        Masina k = new Masina();

        k.kilometraj = 25000;
        k.anFabricatie = 2021;

        k.marca = "Hyundai";
        k.model = "Tucson";
        k.pret = 27000;
        k.modTransmisie = "Automata";


        Masina l = new Masina();

        l.kilometraj = 134000;
        l.anFabricatie = 2011;

        l.marca = "Opel";
        l.model = "Astra";
        l.pret = 8000;
        l.modTransmisie = "Manuala";


        Masina m = new Masina();

        m.kilometraj = 92000;
        m.anFabricatie = 2016;

        m.marca = "Mazda";
        m.model = "CX-5";
        m.pret = 18000;
        m.modTransmisie = "Automata";


        Masina n = new Masina();

        n.kilometraj = 41000;
        n.anFabricatie = 2019;

        n.marca = "Kia";
        n.model = "Sportage";
        n.pret = 19500;
        n.modTransmisie = "Manuala";


        Masina o = new Masina();

        o.kilometraj = 15000;
        o.anFabricatie = 2022;

        o.marca = "Tesla";
        o.model = "Model 3";
        o.pret = 35000;
        o.modTransmisie = "Automata";


        Masina p = new Masina();

        p.kilometraj = 76000;
        p.anFabricatie = 2015;

        p.marca = "Renault";
        p.model = "Megane";
        p.pret = 13000;
        p.modTransmisie = "Manuala";


        Masina q = new Masina();

        q.kilometraj = 99000;
        q.anFabricatie = 2014;

        q.marca = "Peugeot";
        q.model = "308";
        q.pret = 10500;
        q.modTransmisie = "Manuala";


        Masina r = new Masina();

        r.kilometraj = 32000;
        r.anFabricatie = 2020;

        r.marca = "Volvo";
        r.model = "XC60";
        r.pret = 33000;
        r.modTransmisie = "Automata";


        masini.add(a);
        masini.add(b);
        masini.add(c);
        masini.add(d);
        masini.add(e);
        masini.add(f);
        masini.add(g);
        masini.add(h);

        masini.addAll(Arrays.asList(i, j, k, l, m, n, o, p, q, r));


    }

    void afisareMasini(){
        for(int i=0;i< masini.size();i++){
            System.out.println(masini.get(i).descriere());
        }
    }

    void afiseazaMarci(){
        for(int i=0; i<masini.size();i++){
            System.out.println(masini.get(i).marca);
        }
    }

    void afiseazaMarcaModel(){
        for(int i=0; i<masini.size();i++){
            System.out.println(masini.get(i).marca + masini.get(i).model);
        }
    }

    void afiseazaPreturi(){

        char nume = 'a';
        for(int i=0; i<masini.size();i++){

            System.out.println("Pret masina " + nume + " $"+masini.get(i).pret);

            nume++;
        }
    }

    int numarMasini(){
        System.out.println(masini.size());
        return masini.size();

    }

    // NIVEL 2
    int numarMasiniAutomate(){

        int nrAutomate = 0;

        for(int i=0;i<masini.size();i++){
            if(masini.get(i).modTransmisie == "Automata"){
                nrAutomate++;
            }
        }

        System.out.println("Numarul de masini automate: "+nrAutomate);
        return nrAutomate;
    }

    int numarMasiniManuale(){
        int nrManuale = 0;

        for(int i=0;i<masini.size();i++){
            if(masini.get(i).modTransmisie == "Manuala"){
                nrManuale++;
            }
        }

        System.out.println("Numarul de masini manuale: "+nrManuale);
        return nrManuale;
    }

    //7. Câte mașini sunt de o anumită marcă;

    int numarMasiniMarca(String marca) {

        int ctMarca = 0;



        for (int i = 0; i < masini.size(); i++) {
            if (marca.equalsIgnoreCase(masini.get(i).marca)) {
                ctMarca++;
            }
        }

        if(ctMarca == 0){
            System.out.println("Marca de masina " +marca +" nu a fost gasita");
        }

        System.out.println(marca + " " +ctMarca);
        return ctMarca;
    }

    //8. Cate masini sunt mai scumpe decat un prag?

    int numarMasiniPestePret(int prag){

        int ct=0;

        for (int i=0; i<masini.size(); i++){
            if(masini.get(i).pret > prag){
                ct++;
            }
        }

        System.out.println("Numarul de masnini peste pragul de " + prag +": " +ct);
        return ct;
    }

    //9. Cate masini au sub un anumit kilometraj?

    int numarMasiniSubKm(int prag){

        int ct=0;

        for(int i=0; i<masini.size(); i++){
            if(masini.get(i).kilometraj < prag){
                ct++;
            }
        }

        System.out.println("Numar de masini cu kilometraj sub " +prag +"km: " +ct);
        return ct;
    }


    //11. Pretul mediu

    double mediePreturi(){

        int totalPreturi = 0;

        for(int i=0; i<masini.size(); i++){

            totalPreturi+=masini.get(i).pret;
        }

        double mediePret = (double) totalPreturi / (double) masini.size();

        NumberFormat nf = NumberFormat.getInstance(new Locale("ro", "RO"));
        System.out.println("Media preturilor este: $" +nf.format(mediePret));
        return mediePret;
    }

    //12. Total kilometraj

    int sumaKilometraj(){

        int kilometraj = 0;

        for(int i=0; i<masini.size();i++){
            kilometraj+=masini.get(i).kilometraj;
        }

        NumberFormat nf = NumberFormat.getInstance(new Locale("ro", "RO"));
        System.out.println("TOTAL Kilometraj: " + nf.format(kilometraj));
        return kilometraj;
    }

    //15. Cea mai scumpa masina;

    void afiseazaMasinaCeaMaiScumpa(){

        int pret = 0;
        String ceaMaiScumpa = "";

        for(int i=0; i< masini.size(); i++){
            if(pret < masini.get(i).pret){
                pret = masini.get(i).pret;
                ceaMaiScumpa = masini.get(i).marca + " " + masini.get(i).model;
            }
        }
        System.out.println("Cea mai scumpa masina: " + ceaMaiScumpa + ": $" + pret);
    }

    //16. Cea mai ieftina masina;
    void afiseazaMasinaCeaMaiIeftina(){

        int pretMin = Integer.MAX_VALUE;
        String ceaMaiIeftina = "";

        for(int i=0;i<masini.size();i++){
            if(masini.get(i).pret < pretMin){
                pretMin = masini.get(i).pret;
                ceaMaiIeftina = masini.get(i).marca + " " + masini.get(i).model;
            }
        }
        System.out.println("Cea mai ieftina masina: " + ceaMaiIeftina + ": $" + pretMin); //prints the last result of masini.get(i).marca
    }


    //17. Cea mai noua masina - cel mai mare an de fabricatie;
    void afiseazaCeaMaiNouaMasina(){

        int anFabricatie = 0;
        String masinaNoua = "";

        for(int i=0; i<masini.size();i++){
            if(anFabricatie < masini.get(i).anFabricatie){
                anFabricatie = masini.get(i).anFabricatie;
                masinaNoua = "Cea mai noua masina: " + masini.get(i).marca + masini.get(i).model + " (" + anFabricatie +")";
            }

        }
        System.out.println(masinaNoua);
    }

    //18. Cea mai veche masina - cel mai mic an de fabricatie.
    void afiseazaCeaMaiVecheMasina(){

        int anFabricatie = Integer.MAX_VALUE;
        String masinaVeche = "";

        for(int i=0; i<masini.size();i++){
            if(masini.get(i).anFabricatie < anFabricatie){
                anFabricatie = masini.get(i).anFabricatie;
                masinaVeche = "Cea mai veche masina: " + masini.get(i).marca + masini.get(i).model + " (" + anFabricatie +")";
            }
        }
        System.out.println(masinaVeche);
    }

    //19. Masina cu cei mai multi km
    void afiseazaMasinaCuCeiMaiMultiKm(){

        int kilometraj = 0;
        String masina = "";

        NumberFormat nf = NumberFormat.getInstance(new Locale("ro", "RO"));

        for(int i=0; i<masini.size();i++){
            if(kilometraj < masini.get(i).kilometraj){
                kilometraj = masini.get(i).kilometraj;
                masina = "Masina cu cei mai multi km: " + masini.get(i).marca + masini.get(i).model + " (" + nf.format(kilometraj) + ")";
            }
        }


        System.out.println((masina));
    }


    //20. Pretul maxim;
    int pretMaxim(){

        int pretMaxim = 0;
        for(int i=0; i<masini.size();i++){
            if(pretMaxim < masini.get(i).pret){
                pretMaxim = masini.get(i).pret;
            }
        }

        System.out.println("Pretul maxim este: $" +pretMaxim);
        return pretMaxim;
    }


    //NIVEL 5
    //21. Afiseaza masinile unei marci;

    void afiseazaMasiniMarca(String marca){

        ArrayList<String> cars = new ArrayList<>();

        for(int i=0;i<masini.size();i++){
            if(marca.equalsIgnoreCase(masini.get(i).marca)){

                String masina = masini.get(i).marca + " " + masini.get(i).model;
                cars.add(masina);
            }

        }
        System.out.println(marca + " -> " +cars);
    }

    //22. Afiseaza masini automate;
    void afiseazaMasiniAutomate(){
        ArrayList<String> automate = new ArrayList<>();

        for(int i=0; i<masini.size();i++){
            if(masini.get(i).modTransmisie == "Automata"){

                String masinaAutomata = masini.get(i).marca + " " + masini.get(i).model;
                automate.add(masinaAutomata);
            }
        }
        System.out.println("Masini automate: " + automate);
    }

    //23. Afiseaza masinile dintr-un interval de pret;
    void afiseazaMasinileIntrePreturi(int min, int max){

        ArrayList<String> cars = new ArrayList<>();

        for(int i=0; i<masini.size();i++){
            if(masini.get(i).pret > min && masini.get(i).pret < max){

                String masina = masini.get(i).marca + " " + masini.get(i).model;
                cars.add(masina);
            }
        }

        System.out.println("Masini cuprinse in intervalul: $" +min +" - " +max + " " + cars);

    }

    //24. Cauta prima masina de o marca;
    void cautaPrimaMasinaMarca(String marca){

        int ctMasina = 0;

        for(int i=0; i<masini.size();i++){
            if(masini.get(i).marca.equalsIgnoreCase(marca)){
                System.out.println(marca + " " +masini.get(i).model);
                ctMasina++;
               return;
            }
        }
        if(ctMasina == 0){
            System.out.println("Masina " +marca + " nu a fost gasita");
        }
    }

    //25. Exista o masina de o marca;
    boolean existaMasinaMarca(String marca){

        int ct = 0;

        for(int i=0;i<masini.size();i++){
            if(masini.get(i).marca.equalsIgnoreCase(marca)){
                System.out.println(masini.get(i).marca);
                ct++;
                break;

            }
        }
        if(ct == 0){
            System.out.println("Masina nu a fost gasita");
            return false;
        } else{
            return true;
        }
    }

    //NIVEL 6.
    //26. Masini noi si ieftine
    int numarMasiniNoisiIeftine(int anMin, int pretMax) {

        int ct = 0;
        for (int i = 0; i < masini.size(); i++) {
            if (masini.get(i).anFabricatie >= anMin && masini.get(i).pret < pretMax) {
                ct++;
            }
        }

        if (ct != 0) {
            System.out.println("Pentru " + anMin + " si $" + pretMax +" -> " +ct);
            return ct;

        } return 0;
    }

    //27. Pretul mediu pentru o marca;

    double mediePreturiMarca(String marca){

        int ct=0;
        int totalPret =0;

        for(int i=0;i<masini.size();i++){
            if(masini.get(i).marca.equalsIgnoreCase(marca)){
                totalPret+=masini.get(i).pret;
                ct++;
            }
        }

        double mediePreturi = (double) totalPret / (double) ct;
        System.out.println("Media preturilor pentru: " +marca +": $" + mediePreturi);
        return mediePreturi;
    }

    //28. Creste Kilometrajul tuturor masinilor;

    void cresteKilometrajul(int km){

        for(int i=0;i<masini.size();i++){
            masini.get(i).kilometraj += km;
        }

        afisareMasini();
    }

    //29. Aplica o reducere tuturor masinilor;
    void aplicaReducere(int procentReducere){

        int pretRedus = (100 - procentReducere) / 100;

        for(int i=0;i<masini.size();i++){
            masini.get(i).pret*= pretRedus;
        }

        afisareMasini();
    }


    //30. Afiseaza masinile ordonate dupa pret;
    void afiseazaMasiniOrdonateDupaPret(){

        for(int i= 0;i<masini.size();i++){
            for(int j=i+1;j<masini.size();j++){
                if(masini.get(j).pret < masini.get(i).pret){

                   Masina aux= masini.get(i);
                    masini.set(i, masini.get(j));
                    masini.set(j, aux);

                }
            }
            System.out.println(masini.get(i).marca + " " + masini.get(i).model + ": $" + masini.get(i).pret);
        }



    }

}
