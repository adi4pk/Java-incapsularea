package app.simple.Banca;

import java.util.ArrayList;
import java.util.List;

public class ContBancar {

    private String titular;
    private double sold;
    private boolean isActive;

    //todo NOT static - fiecare cont are propriul istoric, unic
    // nu impartim acelasi istoric la toate conturile.
    private List<String> istoric = new ArrayList<>();


    //todo method public sau private -
    //daca nu este public nu o sa poata fi folosita in alt package.
    public void blocheaza(){

        if(isActive){
            this.isActive = false;

        }
    }

    public void deblocheaza(){
        if(!isActive){
            this.isActive = true;
        }
    }

//    List<String> titulari = Arrays.asList("popescu", "ionescu", "pancu");

    // todo public ContBancar() // gol
    // todo public ContBancar(String titular) // doar titular, sold 0
    // todo public ContBancar(String titular, double soldInitial)

    public void setTitular(String titular){

        if(!titular.equals(null)){
            this.titular = titular;
        }

    }

//    public ContBancar(){
//
//        System.out.println("gol");
//        sold = 0;
//    }

    public ContBancar(String titular){

        if(titular !=null){
            this.titular = titular;
            sold = 0;
//            System.out.println(titular);
        }

        isActive = true;

        System.out.println(this.descriere());       //printeaza ce returneaza functia descriere();


    }

    public ContBancar(String titular, double soldInitial){

        if(titular != null){
            this.titular = titular;
            this.sold = soldInitial;
        }

        isActive = true;

    }

    public double getSold(String titular){

            //todo
        // GETTER trebuie sa returneze MEREU ceva; // RETURN

        if(!titular.isEmpty()){ // ← != pe String compară referințe, nu conținut
            System.out.println("Soldul tau curent este: " +sold + "LEI");
            return sold;
        } else{
            System.out.println("Titularul nu exista");
            return 0;
        }

    }


    //DEPUNERE
    public void depune(double suma){

        if(!isActive){
            System.out.println("Contul este blocat.");
            return;
        }
        if(suma > 0){
            this.sold += suma;
            System.out.println("Ai depus: " + suma);

            String depunere = "Depunere: +" + suma;
            istoric.add(depunere);
        } else{
            System.out.println("Suma depusa trebuie sa fie pozitiva.");
        }
    }


    //RETRAGERE
    public void retrage(double suma){
        if(!isActive){
            System.out.println("Contul este blocat.");
            return;
        }
        if(suma >0 && suma <= sold){
            this.sold -= suma;
            System.out.println("Ai retras suma de: " +suma);
            String retragere = "Retragere: -" + suma;
            istoric.add(retragere);

        } else{
            System.out.println("Fonduri insuficiente sau suma invalida");
        }
    }


    public String descriere(){
        if(titular == null){
            System.out.println("Titularul nu exista.");
            return null;
        }

//        System.out.println(titular + " - sold: " +sold);

        String text ="";
        text+="Titular: " + titular +"\n";
        text+="Sold: " + sold;

        return text;
    }

    public List<String> extras(){

        List<String> copieIstoric = new ArrayList(istoric);
        System.out.println(copieIstoric);
        return copieIstoric;
    }





    //todo -- != ==  vs EQUALS
    // primitivele: int, double, bool - != sau ==
    // Obiecte (String, clase etc.) folosim - equals(); sau !"".equals(String ) sau !String.isEmpty();
    // daca folosim == la String sau alte Obiecte -> la string, sistemul va compara doar prima litera
}
