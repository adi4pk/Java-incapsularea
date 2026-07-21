package app.catalog;
import java.util.ArrayList;
import java.util.List;

public class Catalog {
    //todo se initiaza goala in constructor
    private List<String> produse;

    public Catalog (){
        produse = new ArrayList<>();
    }

    //what happens if you don't add public or private?
    public void adauga(String produs){


        //todo verificarea de NULL e mereu prima.
        if(produs == null || produs.equals("")){

            System.out.println("Produsul nu poate fi NULL sau empty string");
            return;
        } else if(produse.contains(produs)){

            System.out.println("Produsul exista deja");
            return;
        }

        produse.add(produs);

        this.descriereCatalog();


    }

    public int numarProduse(){
        return produse.size();
    }

    public boolean contine(String produs){

        if (produse.contains(produs)){
            return produse.contains(produs);    //returns true;
        } else return false;
    }


    private void descriereCatalog(){
        System.out.println(produse);
    }

    public void sterge(String produs){
        if(produse.contains(produs)){
            produse.remove(produs);

            descriereCatalog();
        } else{
            System.out.println("Produsul nu exista in catalog.");
        }
    }

    public List<String> toateProdusele(){

        List<String> copieLista = new ArrayList(produse);

        return copieLista;

    }
}
