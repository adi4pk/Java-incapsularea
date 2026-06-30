package app;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {



        MasinaService service = new MasinaService();


        service.loadMasini();


//        service.afisareMasini();
//        service.afiseazaMarci();
//        service.afiseazaMarcaModel();

//        service.afiseazaPreturi();
//        service.numarMasini();
//        service.numarMasiniAutomate();
//        service.numarMasiniManuale();
//
//        service.numarMasiniMarca("bmw");
//        service.numarMasiniPestePret(12000);
//        service.numarMasiniSubKm(20000);
//        service.mediePreturi();
//        service.sumaKilometraj();
        service.afiseazaMasinaCeaMaiScumpa();
        service.afiseazaMasinaCeaMaiIeftina();
        service.afiseazaCeaMaiNouaMasina();
        service.afiseazaCeaMaiVecheMasina();

    }
}