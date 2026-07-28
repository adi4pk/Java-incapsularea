package app.relations.OnToManyBidirectional;

import java.util.ArrayList;
import java.util.List;

public class Echipa {

    private String name;
    private List<Player> jucatori = new ArrayList<> ();

    public List<Player> getJucatori(){
        return new ArrayList<>(jucatori);
    }



    public Echipa(String name){
        setName(name);
    }


    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }


    public void transferEchipa(Player jucator){



        if(jucator !=null){
            if(jucatori.contains(jucator)){
                System.out.println("Jucatorul este deja in echipa curenta.");
                return;
            }

            Echipa oldTeam = jucator.getEchipa();

            if (oldTeam != null){   //daca are o echipa, scoate-l din ea.
                oldTeam.getJucatori().remove(jucator);
            }

            this.jucatori.add(jucator);
            jucator.setEchipa(this);

        } else {
            System.out.println("Jucatorul nu se afla in aceasta ehipa.");
        }
    }

    public void incheieContract(Player player){
        if(player != null){
            if(jucatori.contains(player)){
                jucatori.remove(player);
            } else {
                // -- eroare, daca jucatorul e null.
                System.out.println("Jucatorul " + player.toString() + " nu joaca la echipa aceasta.");
            }
        }


    }
}
