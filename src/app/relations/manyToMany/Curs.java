package app.relations.manyToMany;

import java.util.ArrayList;
import java.util.List;

public class Curs {
    private String numeCurs;
    private List<Student> studenti = new ArrayList<>();


    public Curs(String numeCurs){
        this.numeCurs = numeCurs;
    }

    @Override
    public String toString(){
        return numeCurs;
    }

    public List<Student> getStudenti(){

        List<Student> studentiCopie = new ArrayList<>(studenti);
        return studentiCopie;
    }


    public void inscrieStudent(Student student){
        if(student !=null){
            if(studenti.contains(student)){
                System.out.println("Studentul face deja parte din acest curs.");
                return;
            }

            this.studenti.add(student);
            student.adaugaCurs(this);

        }
    }

    public void removeStudent(Student student){
        studenti.remove(student);      //elimina student din lista <Curs> cursuri
        student.removeCurs(this);      // elimina cursul din lista <Student> studenti
    }

}
