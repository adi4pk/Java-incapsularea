package app.relations.OnToManyBidirectional;

public class Player {

    private String name;
    private int age;
    private Echipa echipa;


    public Player(String name, int age){

        setName(name);
        setAge(age);
    }


    @Override
    public String toString(){
        return name;
    }

    public void setEchipa(Echipa echipa){
        this.echipa = echipa;
        System.out.println(detailsJucator());
    }

    public Echipa getEchipa(){
        return echipa;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String detailsJucator(){
        if(echipa != null){
            return "Jucatorul " + name + " joaca acum la echipa " + echipa.getName();
        } else{
            return "Jucatorul " + name + " este momentan liber de contract.";
        }
    }
}
