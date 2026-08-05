package app.relations.Provocari;

public class Carte {

    private String titlu;
    private Autor autor;
    private Editura editura;

    public Carte(String titlu, Autor autor, Editura editura) {
        this.titlu = titlu;
        this.autor = autor;
        this.editura = editura;

        autor.adaugaCarte(this);
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editura getEditura() {
        return editura;
    }

    public void setEditura(Editura editura) {
        this.editura = editura;
    }

    @Override
    public String toString() {
        return titlu;
    }
}