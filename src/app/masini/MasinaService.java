package app.masini;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.text.NumberFormat;



public class MasinaService {
    private List<Masina> masini;

    //todo create constructor and add func loadMasini()
    public MasinaService(){

        loadMasini();
    }

    void  loadMasini(){


        masini = new ArrayList<>();


        Masina a = new Masina(
                "Audi",
                "A4",
                12,
                18000,
                1993,
                "Manuala"
        );


        Masina b = new Masina("BMW", "X5", 13, 15000, 2010, "Automata");
        Masina c = new Masina("Toyota", "Corolla", 45000, 16000, 2019, "Manuala");
        Masina d = new Masina("Mercedes", "C-Class", 54000, 20000, 2015, "Automata");
        Masina e = new Masina("Volkswagen", "Golf", 120000, 9000, 2012, "Manuala");
        Masina f = new Masina("Ford", "Focus", 75000, 14000, 2018, "Manuala");
        Masina g = new Masina("BMW", "X3", 30000, 28000, 2020, "Automata");
        Masina h = new Masina("Audi", "A6", 98000, 17000, 2014, "Automata");
        Masina i = new Masina("Skoda", "Octavia", 67000, 15500, 2017, "Manuala");
        Masina j = new Masina("Seat", "Leon", 88000, 11000, 2013, "Manuala");
        Masina k = new Masina("Hyundai", "Tucson", 25000, 27000, 2021, "Automata");
        Masina l = new Masina("Opel", "Astra", 134000, 8000, 2011, "Manuala");
        Masina m = new Masina("Mazda", "CX-5", 92000, 18000, 2016, "Automata");
        Masina n = new Masina("Kia", "Sportage", 41000, 19500, 2019, "Manuala");
        Masina o = new Masina("Tesla", "Model 3", 15000, 35000, 2022, "Automata");
        Masina p = new Masina("Renault", "Megane", 76000, 13000, 2015, "Manuala");
        Masina q = new Masina("Peugeot", "308", 99000, 10500, 2014, "Manuala");
        Masina r = new Masina("Volvo", "XC60", 32000, 33000, 2020, "Automata");



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
            System.out.println(masini.get(i).getMarca());
        }
    }

    void afiseazaMarcaModel(){
        for(int i=0; i<masini.size();i++){
            System.out.println(masini.get(i).getMarca() + masini.get(i).getModel());
        }
    }

    void afiseazaPreturi(){

        char nume = 'a';
        for(int i=0; i<masini.size();i++){

            System.out.println("Pret masina " + nume + " $"+masini.get(i).getPret());

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
            if(masini.get(i).getModTransmisie().equals("Automata")){     // Regula: la String mereu .equals(). | NU String == String
                nrAutomate++;
            }
        }

        System.out.println("Numarul de masini automate: "+nrAutomate);
        return nrAutomate;
    }

    int numarMasiniManuale(){
        int nrManuale = 0;
String txt= "Manuala";
        for(int i=0;i<masini.size();i++){
            if(masini.get(i).equals(txt)){
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
            if (marca.equalsIgnoreCase(masini.get(i).getMarca())) {
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
            if(masini.get(i).getPret() > prag){
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
            if(masini.get(i).getKm() < prag){
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

            totalPreturi+=masini.get(i).getPret();
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
            kilometraj+=masini.get(i).getKm();
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
            if(pret < masini.get(i).getPret()){
                pret = masini.get(i).getPret();
                ceaMaiScumpa = masini.get(i).getMarca() + " " + masini.get(i).getModel();
            }
        }
        System.out.println("Cea mai scumpa masina: " + ceaMaiScumpa + ": $" + pret);
    }

    //16. Cea mai ieftina masina;
    void afiseazaMasinaCeaMaiIeftina(){

        int pretMin = Integer.MAX_VALUE;
        String ceaMaiIeftina = "";

        for(int i=0;i<masini.size();i++){
            if(masini.get(i).getPret() < pretMin){
                pretMin = masini.get(i).getPret();
                ceaMaiIeftina = masini.get(i).getMarca() + " " + masini.get(i).getModel();
            }
        }
        System.out.println("Cea mai ieftina masina: " + ceaMaiIeftina + ": $" + pretMin); //prints the last result of masini.get(i).marca
    }


    //17. Cea mai noua masina - cel mai mare an de fabricatie;
    void afiseazaCeaMaiNouaMasina(){

        int anFabricatie = 0;
        String masinaNoua = "";

        for(int i=0; i<masini.size();i++){
            if(anFabricatie < masini.get(i).getAnFabricatie()){
                anFabricatie = masini.get(i).getAnFabricatie();
                masinaNoua = "Cea mai noua masina: " + masini.get(i).getMarca() + " " + masini.get(i).getModel() + " (" + anFabricatie +")";
            }

        }
        System.out.println(masinaNoua);
    }

    //18. Cea mai veche masina - cel mai mic an de fabricatie.
    void afiseazaCeaMaiVecheMasina(){

        int anFabricatie = Integer.MAX_VALUE;
        String masinaVeche = "";

        for(int i=0; i<masini.size();i++){
            if(masini.get(i).getAnFabricatie() < anFabricatie){
                anFabricatie = masini.get(i).getAnFabricatie();
                masinaVeche = "Cea mai veche masina: " + masini.get(i).getMarca() + " " + masini.get(i).getModel() + " (" + anFabricatie +")";
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
            if(kilometraj < masini.get(i).getKm()){
                kilometraj = masini.get(i).getKm();
                masina = "Masina cu cei mai multi km: " + masini.get(i).getMarca() + " " + masini.get(i).getModel() + " (" + nf.format(kilometraj) + ")";
            }
        }


        System.out.println((masina));
    }


    //20. Pretul maxim;
    int pretMaxim(){

        int pretMaxim = 0;
        for(int i=0; i<masini.size();i++){
            if(pretMaxim < masini.get(i).getPret()){
                pretMaxim = masini.get(i).getPret();
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
            if(marca.equalsIgnoreCase(masini.get(i).getMarca())){

                String masina = masini.get(i).getMarca() + " " + masini.get(i).getModel();
                cars.add(masina);
            }

        }
        System.out.println(marca + " -> " +cars);
    }

    //22. Afiseaza masini automate;
    void afiseazaMasiniAutomate(){
        ArrayList<String> automate = new ArrayList<>();

        for(int i=0; i<masini.size();i++){
            if(masini.get(i).getModTransmisie().equals("Automata")){

                String masinaAutomata = masini.get(i).getMarca() + " " + masini.get(i).getModel();
                automate.add(masinaAutomata);
            }
        }
        System.out.println("Masini automate: " + automate);
    }

    //23. Afiseaza masinile dintr-un interval de pret;
    void afiseazaMasinileIntrePreturi(int min, int max){

        ArrayList<String> cars = new ArrayList<>();

        for(int i=0; i<masini.size();i++){
            if(masini.get(i).getPret() > min && masini.get(i).getPret() < max){

                String masina = masini.get(i).getMarca() + " " + masini.get(i).getModel();
                cars.add(masina);
            }
        }

        System.out.println("Masini cuprinse in intervalul: $" +min +" - " +max + " " + cars);

    }

    //24. Cauta prima masina de o marca;
    void cautaPrimaMasinaMarca(String marca){

        int ctMasina = 0;

        for(int i=0; i<masini.size();i++){
            if(masini.get(i).getMarca().equalsIgnoreCase(marca)){
                System.out.println(marca + " " +masini.get(i).getModel());
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
            if(masini.get(i).getMarca().equalsIgnoreCase(marca)){
                System.out.println(masini.get(i).getMarca());
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
            if (masini.get(i).getAnFabricatie() >= anMin && masini.get(i).getPret() < pretMax) {
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
            if(!masini.get(i).getMarca().isEmpty() && masini.get(i).getMarca().equalsIgnoreCase(marca)){
                totalPret+=masini.get(i).getPret();
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

            int numKm = masini.get(i).getKm();
            numKm += km;
            masini.get(i).setKilometraj(numKm);
        }

        afisareMasini();
    }

    //29. Aplica o reducere tuturor masinilor;
    void aplicaReducere(int procentReducere){

        // int pretRedus = (100 - procentReducere) / 100; //
        //  100 lei 10%red =>    10%din 100 = 10 pretul este 90

        //todo / și * au aceeași prioritate
        // se evaluează stânga → dreapta, deci întâi se calculează procentReducere/100

        for(int i=0;i<masini.size();i++){
            double pret= (double) masini.get(i).getPret() - ((double)(procentReducere/100) * (double) masini.get(i).getPret());
        }

        afisareMasini();
    }


    //30. Afiseaza masinile ordonate dupa pret;
    void afiseazaMasiniOrdonateDupaPret(){

        //todo creeaza o copie a listei originale peste care rulezi functia/for-loops
        // -> apoi afisezi masinile ordonate din listaCopie.
        // lista originala ramane neschimbata;

        ArrayList<Masina> copieMasini = new ArrayList<>(masini);


        for(int i= 0;i<copieMasini.size();i++){
            for(int j=i+1;j<copieMasini.size();j++){
                if(copieMasini.get(j).getPret() < copieMasini.get(i).getPret()){

                   Masina aux= copieMasini.get(i);
                    copieMasini.set(i, copieMasini.get(j));
                    copieMasini.set(j, aux);

                }
            }
            System.out.println(copieMasini.get(i).getMarca() + " " + copieMasini.get(i).getModel() + ": $" + copieMasini.get(i).getPret());
        }



    }

}
