package app.relations;

public class Persoana {
    String nume;
    int varsta;
    Buletin buletin;


    //this => obiectul curent: Persoana
    public Persoana(String nume, int varsta){
        setNume(nume);
        setVarsta(varsta);

    }

    //setters
    public void setNume(String nume){
        this.nume = nume;
    }

    public void setVarsta(int varsta){
        this.varsta = varsta;
    }



    public void atribuieBuletin(Buletin buletin){


        // dacă exista deja un buletin,
        // rupem relația veche prin atribuirea noului obiect
        //todo NU este nevoie sa setam vechiul obiect (null), doar il atribuim pe cel nou.
        if(buletin != null){
            if(this.buletin!=null){     //rupe linkul cu vechiul obiect
                this.buletin.setPersoana(null);
            }
            this.buletin = buletin; //seteaza capatul din persoana
            buletin.setPersoana(this); //seteaza capatul din buletin
        }else{
            System.out.println("buletinul este null");
        }


        //


    }


    public String descriere() {
//persoana
        if (this.buletin != null ){
            if(this.buletin.getPersoana() == null){
                System.out.println("Persoana este NULL");
                return "x";
            }
            else return "Persoana " + nume + " are buletin seria " + buletin.getSerie();
        } else return "metoda descriere() nu se poate executa pe buletin null";
    }

}
