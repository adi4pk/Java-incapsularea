---
title: "Relații între modele — soluții"
subtitle: "one-to-one · one-to-many · many-to-many · compoziție vs agregare (Java OOP)"
author: "MyCodeSchool"
lang: ro
geometry: margin=2.2cm
mainfont: "Helvetica Neue"
monofont: "Menlo"
fontsize: 11pt
---

# Cum folosești soluțiile

Fiecare nivel are: codul complet + **de ce** e scris așa, legat de regula de aur:
*orice metodă care creează o relație bidirecțională setează ambele capete*. Nu copia —
compară cu ce ai scris tu și caută diferența de mecanism, nu de sintaxă.

Toate clasele stau într-un pachet nou, de ex. `app.relatii` (câte un subpachet pe
nivel, ca la `simple`).

\newpage

# Nivelul 0 — one-to-one: `Persoana` + `Buletin`

```java
public class Buletin {

    private String serie;
    private int numar;
    private Persoana persoana;

    public Buletin(String serie, int numar){
        this.serie = serie;
        this.numar = numar;
    }

    public String getSerie(){ return serie; }

    public int getNumar(){ return numar; }

    public Persoana getPersoana(){ return persoana; }

    void setPersoana(Persoana persoana){ this.persoana = persoana; }
}
```

```java
public class Persoana {

    private String nume;
    private Buletin buletin;

    public Persoana(String nume){
        this.nume = nume;
    }

    public void atribuie(Buletin b){
        if(b == null){
            System.out.println("Buletinul nu poate fi null");
            return;
        }
        if(this.buletin != null){
            this.buletin.setPersoana(null);
        }
        this.buletin = b;
        b.setPersoana(this);
    }

    public Buletin getBuletin(){ return buletin; }

    public String descriere(){
        if(buletin == null){
            return nume + " (fara buletin)";
        }
        return nume + " - buletin " + buletin.getSerie() + buletin.getNumar();
    }
}
```

**Mecanismul.** `atribuie` are trei mișcări, în ordinea asta: (1) rupe legătura veche
(`buletinul vechi` rămâne fără persoană — bifa 4 din DoD), (2) setează capătul meu
(`this.buletin = b`), (3) setează capătul celuilalt (`b.setPersoana(this)`). Dacă sari
pasul 3, `persoana.getBuletin().getPersoana()` dă altceva decât `persoana` — relația
„minte" într-un sens.

**Detaliu de încapsulare:** `setPersoana` e *package-private* (fără `public`) — doar
`Persoana`, din același pachet, are voie să lege capătul acela. Din exterior există un
singur punct de intrare: `atribuie`. Așa nu poate nimeni să seteze un singur capăt.

\newpage

# Nivelul 1 — one-to-many, un singur sens: `Autor` + `Carte`

```java
public class Carte {

    private String titlu;
    private int anAparitie;

    public Carte(String titlu, int anAparitie){
        this.titlu = titlu;
        this.anAparitie = anAparitie;
    }

    public String getTitlu(){ return titlu; }

    public int getAnAparitie(){ return anAparitie; }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Autor {

    private String nume;
    private List<Carte> carti = new ArrayList<>();

    public Autor(String nume){
        this.nume = nume;
    }

    public void adaugaCarte(Carte c){
        if(c == null){
            System.out.println("Cartea nu poate fi null");
            return;
        }
        if(carti.contains(c)){
            System.out.println("Cartea exista deja la acest autor");
            return;
        }
        carti.add(c);
    }

    public int numarCarti(){ return carti.size(); }

    public List<Carte> getCarti(){ return new ArrayList<>(carti); }
}
```

```java
public class MainAutori {
    public static void main(String[] args){
        Autor autor = new Autor("Marin Preda");
        autor.adaugaCarte(new Carte("Morometii", 1955));
        autor.adaugaCarte(new Carte("Cel mai iubit dintre pamanteni", 1980));
        autor.adaugaCarte(new Carte("Delirul", 1975));

        System.out.println(autor.numarCarti());
    }
}
```

**Mecanismul.** E `Comanda -> listaLinii` din proiectul tău, cu alt domeniu. Relația
merge doar dinspre autor spre cărți — `Carte` nu are câmp `autor`, deci nu există al
doilea capăt de sincronizat. `adaugaCarte` e „paznicul" listei (null + duplicate), iar
`getCarti()` dă o **copie** — exact regula pe care o respecți deja în
`extras()`/`toateProdusele()`.

\newpage

# Nivelul 2 — one-to-many bidirecțional: `Echipa` + `Jucator`

```java
public class Jucator {

    private String nume;
    private Echipa echipa;

    public Jucator(String nume){
        this.nume = nume;
    }

    public String getNume(){ return nume; }

    public Echipa getEchipa(){ return echipa; }

    void setEchipa(Echipa echipa){ this.echipa = echipa; }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Echipa {

    private String nume;
    private List<Jucator> jucatori = new ArrayList<>();

    public Echipa(String nume){
        this.nume = nume;
    }

    public void transfera(Jucator j){
        if(j == null || jucatori.contains(j)){
            return;
        }
        if(j.getEchipa() != null){
            j.getEchipa().jucatori.remove(j);
        }
        jucatori.add(j);
        j.setEchipa(this);
    }

    public String getNume(){ return nume; }

    public List<Jucator> getJucatori(){ return new ArrayList<>(jucatori); }
}
```

```java
public class MainEchipe {
    public static void main(String[] args){
        Echipa u = new Echipa("Universitatea");
        Echipa s = new Echipa("Stiinta");
        Jucator j = new Jucator("Popescu");

        u.transfera(j);
        System.out.println(j.getEchipa().getJucatori().contains(j));

        s.transfera(j);
        System.out.println(u.getJucatori().contains(j));
        System.out.println(s.getJucatori().contains(j));
    }
}
```

**Mecanismul — miezul întregului subiect.** `transfera` are patru mișcări:

1. gardă (null / deja în echipă);
2. **scoate-l din echipa veche** — altfel jucătorul apare în două liste simultan
   (bifa 4 din DoD picată);
3. adaugă-l în lista mea;
4. setează capătul lui: `j.setEchipa(this)`.

Testul de consistență din DoD (`j.getEchipa().getJucatori().contains(j)`) pică dacă
lipsește oricare din pașii 3-4; testul „nu apare în două echipe" pică fără pasul 2.

**Detaliu de limbaj:** `j.getEchipa().jucatori.remove(j)` accesează câmpul `private`
al *altei* instanțe — legal, pentru că `private` în Java înseamnă „privat față de
clasă", nu „privat față de obiect". Codul rulează tot în clasa `Echipa`, deci vede
`jucatori` al oricărei echipe.

\newpage

# Nivelul 3 — many-to-many: `Student` + `Curs`

```java
import java.util.ArrayList;
import java.util.List;

public class Student {

    private String nume;
    private List<Curs> cursuri = new ArrayList<>();

    public Student(String nume){
        this.nume = nume;
    }

    public String getNume(){ return nume; }

    public List<Curs> getCursuri(){ return new ArrayList<>(cursuri); }

    void adaugaCurs(Curs c){ cursuri.add(c); }

    boolean urmeaza(Curs c){ return cursuri.contains(c); }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Curs {

    private String titlu;
    private List<Student> studenti = new ArrayList<>();

    public Curs(String titlu){
        this.titlu = titlu;
    }

    public void inscrie(Student s){
        if(s == null || studenti.contains(s)){
            System.out.println("Student invalid sau deja inscris");
            return;
        }
        studenti.add(s);
        s.adaugaCurs(this);
    }

    public String getTitlu(){ return titlu; }

    public List<Student> getStudenti(){ return new ArrayList<>(studenti); }
}
```

```java
public class MainCursuri {
    public static void main(String[] args){
        Student s1 = new Student("Ana");
        Student s2 = new Student("Mihai");
        Curs c1 = new Curs("Java");
        Curs c2 = new Curs("SQL");
        Curs c3 = new Curs("Docker");

        c1.inscrie(s1);
        c2.inscrie(s1);
        c2.inscrie(s2);
        c3.inscrie(s2);

        for(Curs c : s1.getCursuri()){
            System.out.println("s1: " + c.getTitlu());
        }
        for(Student s : c2.getStudenti()){
            System.out.println("c2: " + s.getNume());
        }
    }
}
```

**Mecanismul.** O singură metodă publică — `Curs.inscrie` — leagă ambele liste; pe
partea cealaltă, `Student.adaugaCurs` e package-private, ca `setPersoana`/`setEchipa`
de mai sus. Ăsta e hint-ul din exercițiu luat în serios: dacă și `Student` ar avea un
`inscrieLa(Curs)` public cu propria logică, ai avea două drumuri de a crea aceeași
relație și, garantat, într-o zi unul din ele va uita un capăt. Un singur drum = un
singur loc de reparat.

Verificarea de duplicat se face pe **o singură** listă (`studenti.contains(s)`) —
suficient, pentru că listele nu pot diverge decât dacă cineva ocolește `inscrie`, iar
asta tocmai am blocat-o.

\newpage

# Nivelul 4 — compoziție vs agregare: `Comanda`/`LinieComanda`/`Produs`

Modelul tău din `app.simple.comenzi` bifează deja structura: `adaugaLinie` construiește
`LinieComanda` în interior, `total()` traversează liniile, `Produs` e partajat. Ce
lipsește (vezi M8 din review) e ca paznicii să nu lase obiecte pe jumătate construite:

```java
public class Produs {

    private String nume;
    private double pret;

    public Produs(String nume, double pret){
        this.nume = nume;
        this.pret = pret;
    }

    public String getNume(){ return nume; }

    public double getPret(){ return pret; }
}
```

```java
public class LinieComanda {

    private Produs produs;
    private int cantitate;

    LinieComanda(Produs produs, int cantitate){
        this.produs = produs;
        this.cantitate = cantitate;
    }

    public double subtotal(){ return produs.getPret() * cantitate; }

    public String detalii(){
        return produs.getNume() + " x" + cantitate + " = " + subtotal();
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Comanda {

    private String client;
    private List<LinieComanda> linii = new ArrayList<>();

    public Comanda(String client){
        this.client = client;
    }

    public void adaugaLinie(Produs p, int cantitate){
        if(p == null || cantitate < 1){
            System.out.println("Produs invalid sau cantitate < 1");
            return;
        }
        linii.add(new LinieComanda(p, cantitate));
    }

    public double total(){
        double total = 0;
        for(LinieComanda l : linii){
            total += l.subtotal();
        }
        return total;
    }

    public List<LinieComanda> getLinii(){ return new ArrayList<>(linii); }
}
```

**Mecanismul.** Uită-te unde s-a mutat validarea: constructorul lui `LinieComanda` nu
mai are `if`-uri, pentru că e **package-private** — singurul care îl poate apela e
`Comanda.adaugaLinie`, care a validat deja. Asta e compoziția exprimată în cod: linia
nu poate exista în afara unei comenzi, deci nici nu poate exista invalidă. `Produs`,
în schimb, se creează liber, oriunde, și e doar *referit* de linii (agregare) — aceeași
instanță poate apărea în comenzi diferite, iar dispariția unei comenzi nu-l afectează.

Compoziție = „parte-din, moare cu întregul". Agregare = „folosește, dar trăiește
separat". Diferența nu e un cuvânt-cheie Java — e **cine are voie să construiască** și
cine doar primește o referință.

\newpage

# Provocări

## 1. Bibliotecă — `Autor`, `Carte`, `Editura`

```java
public class Editura {

    private String nume;

    public Editura(String nume){
        this.nume = nume;
    }

    public String getNume(){ return nume; }
}
```

```java
public class Carte {

    private String titlu;
    private Autor autor;
    private Editura editura;

    Carte(String titlu, Autor autor, Editura editura){
        this.titlu = titlu;
        this.autor = autor;
        this.editura = editura;
    }

    public String getTitlu(){ return titlu; }

    public Autor getAutor(){ return autor; }

    public Editura getEditura(){ return editura; }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Autor {

    private String nume;
    private List<Carte> carti = new ArrayList<>();

    public Autor(String nume){
        this.nume = nume;
    }

    public Carte publica(String titlu, Editura editura){
        if(titlu == null || editura == null){
            System.out.println("Titlu sau editura invalide");
            return null;
        }
        Carte c = new Carte(titlu, this, editura);
        carti.add(c);
        return c;
    }

    public String getNume(){ return nume; }

    public List<Carte> getCarti(){ return new ArrayList<>(carti); }

    public static List<Carte> cartileAutoruluiLaEditura(Autor a, Editura e){
        List<Carte> rezultat = new ArrayList<>();
        for(Carte c : a.getCarti()){
            if(c.getEditura() == e){
                rezultat.add(c);
            }
        }
        return rezultat;
    }
}
```

**Mecanismul.** Trei relații, trei tratamente: `Carte -> Autor` bidirecțional (setat
o singură dată, în `publica`, ambele capete); `Carte -> Editura` many-to-one simplu
(un câmp, gata); `Editura` NU își știe cărțile — cerința spune explicit, deci nu-i
pui listă „că poate prinde bine". Interogarea `cartileAutoruluiLaEditura` traversează
dinspre capătul care ține lista (autorul) și filtrează pe câmpul celălalt. Comparația
`c.getEditura() == e` e intenționat cu `==`: întrebăm „e fix aceeași editură (același
obiect)?", nu „au același nume?".

## 2. Rețea socială — `Utilizator`

```java
import java.util.ArrayList;
import java.util.List;

public class Utilizator {

    private String nume;
    private List<Utilizator> prieteni = new ArrayList<>();

    public Utilizator(String nume){
        this.nume = nume;
    }

    public void imprietenire(Utilizator altul){
        if(altul == null || altul == this || prieteni.contains(altul)){
            return;
        }
        this.prieteni.add(altul);
        altul.prieteni.add(this);
    }

    public List<Utilizator> prieteniComuni(Utilizator altul){
        List<Utilizator> comuni = new ArrayList<>();
        for(Utilizator u : prieteni){
            if(altul.prieteni.contains(u)){
                comuni.add(u);
            }
        }
        return comuni;
    }

    public String getNume(){ return nume; }

    public List<Utilizator> getPrieteni(){ return new ArrayList<>(prieteni); }
}
```

**Mecanismul.** Many-to-many pe **aceeași clasă** — ambele capete sunt de tip
`Utilizator`, deci `imprietenire` scrie în două liste de același fel. Garda are un caz
nou față de nivelurile anterioare: `altul == this` — fără el, te împrietenești cu tine
însuți. Simetria e regula de aur în formă pură: dacă A e în lista lui B, B trebuie să
fie în lista lui A, mereu, iar singurul mod de a o garanta e ca aceeași metodă să scrie
ambele.

## 3. Playlist — `Melodie` + `Playlist`

```java
import java.util.ArrayList;
import java.util.List;

public class Melodie {

    private String titlu;
    private List<Playlist> playlisturi = new ArrayList<>();

    public Melodie(String titlu){
        this.titlu = titlu;
    }

    public String getTitlu(){ return titlu; }

    void adaugaPlaylist(Playlist p){ playlisturi.add(p); }

    public int inCatePlaylisturi(){ return playlisturi.size(); }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private String nume;
    private List<Melodie> melodii = new ArrayList<>();

    public Playlist(String nume){
        this.nume = nume;
    }

    public void adauga(Melodie m){
        if(m == null || melodii.contains(m)){
            return;
        }
        melodii.add(m);
        m.adaugaPlaylist(this);
    }

    public List<Melodie> getMelodii(){ return new ArrayList<>(melodii); }

    public static List<Melodie> melodiiPopulare(List<Melodie> toate){
        List<Melodie> rezultat = new ArrayList<>();
        for(Melodie m : toate){
            if(m.inCatePlaylisturi() >= 2){
                rezultat.add(m);
            }
        }
        return rezultat;
    }
}
```

**Mecanismul.** Interogarea „în cel puțin 2 playlist-uri" devine banală dacă alegi
bine **capătul care ține informația**: melodia își știe playlist-urile, deci răspunsul
e `playlisturi.size() >= 2` — fără nicio traversare dublă. Dacă ai fi ținut relația
doar dinspre playlist, aceeași întrebare ar fi cerut să numeri aparițiile fiecărei
melodii prin toate playlist-urile. Alegerea direcției relației = alegerea întrebărilor
la care răspunzi ieftin.

\newpage

# Capstone — „Școala"

```java
import java.util.ArrayList;
import java.util.List;

public class Profesor {

    private String nume;
    private List<Curs> cursuri = new ArrayList<>();

    public Profesor(String nume){
        this.nume = nume;
    }

    public Curs deschideCurs(String titlu){
        Curs c = new Curs(titlu, this);
        cursuri.add(c);
        return c;
    }

    public String getNume(){ return nume; }

    public List<Curs> getCursuri(){ return new ArrayList<>(cursuri); }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Curs {

    private String titlu;
    private Profesor profesor;
    private List<Student> studenti = new ArrayList<>();

    Curs(String titlu, Profesor profesor){
        this.titlu = titlu;
        this.profesor = profesor;
    }

    public void inscrie(Student s){
        if(s == null || studenti.contains(s)){
            return;
        }
        studenti.add(s);
        s.adaugaCurs(this);
    }

    public String getTitlu(){ return titlu; }

    public Profesor getProfesor(){ return profesor; }

    public List<Student> getStudenti(){ return new ArrayList<>(studenti); }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Student {

    private String nume;
    private List<Curs> cursuri = new ArrayList<>();

    public Student(String nume){
        this.nume = nume;
    }

    public String getNume(){ return nume; }

    public List<Curs> getCursuri(){ return new ArrayList<>(cursuri); }

    void adaugaCurs(Curs c){ cursuri.add(c); }
}
```

```java
public class Nota {

    private Student student;
    private Curs curs;
    private double valoare;

    Nota(Student student, Curs curs, double valoare){
        this.student = student;
        this.curs = curs;
        this.valoare = valoare;
    }

    public Student getStudent(){ return student; }

    public Curs getCurs(){ return curs; }

    public double getValoare(){ return valoare; }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Scoala {

    private List<Nota> note = new ArrayList<>();

    public void noteaza(Student s, Curs c, double valoare){
        if(s == null || c == null || valoare < 1 || valoare > 10){
            System.out.println("Nota invalida");
            return;
        }
        if(!c.getStudenti().contains(s)){
            System.out.println("Studentul nu e inscris la acest curs");
            return;
        }
        note.add(new Nota(s, c, valoare));
    }

    public double mediaStudentului(Student s){
        double suma = 0;
        int ct = 0;
        for(Nota n : note){
            if(n.getStudent() == s){
                suma += n.getValoare();
                ct++;
            }
        }
        if(ct == 0){
            System.out.println("Studentul nu are note");
            return 0;
        }
        return suma / ct;
    }

    public List<Student> studentiiCursului(Curs c){
        return c.getStudenti();
    }

    public List<Curs> cursurileProfesorului(Profesor p){
        return p.getCursuri();
    }
}
```

```java
public class MainScoala {
    public static void main(String[] args){
        Scoala scoala = new Scoala();

        Profesor pJava = new Profesor("Ionescu");
        Profesor pSql = new Profesor("Georgescu");

        Curs java = pJava.deschideCurs("Java");
        Curs oop = pJava.deschideCurs("OOP");
        Curs sql = pSql.deschideCurs("SQL");

        Student ana = new Student("Ana");
        Student mihai = new Student("Mihai");
        Student ioana = new Student("Ioana");
        Student dan = new Student("Dan");

        java.inscrie(ana);
        java.inscrie(mihai);
        oop.inscrie(ana);
        sql.inscrie(ioana);
        sql.inscrie(dan);

        scoala.noteaza(ana, java, 10);
        scoala.noteaza(ana, oop, 8);
        scoala.noteaza(mihai, java, 7);
        scoala.noteaza(ioana, sql, 9);

        System.out.println("Media Anei: " + scoala.mediaStudentului(ana));
        for(Student s : scoala.studentiiCursului(sql)){
            System.out.println("SQL: " + s.getNume());
        }
        for(Curs c : scoala.cursurileProfesorului(pJava)){
            System.out.println(pJava.getNume() + " preda: " + c.getTitlu());
        }
    }
}
```

**Mecanismul.** Tot ce ai exersat, asamblat:

- `Profesor -> Curs` e Nivelul 2: cursul se naște prin `deschideCurs`, care setează
  ambele capete (lista profesorului + câmpul `profesor` al cursului); constructorul
  `Curs` e package-private ca să nu existe curs fără profesor.
- `Student <-> Curs` e Nivelul 3, neschimbat.
- `Nota` e bonusul: many-to-many **cu atribut**. Relația student-curs-valoare nu
  încape într-un câmp simplu la niciun capăt, așa că devine propria clasă — un rând
  care leagă cele două părți plus datele relației. (Același tipar îl vei revedea la
  SQL ca tabelă de legătură și la JPA ca entitate de join.)
- `Scoala.noteaza` validează **relația**, nu doar valorile: nu poți nota un student
  la un curs la care nu e înscris. Media pe `ct == 0` face `return` devreme — fără
  NaN (vezi M3 din review).

# Firul roșu al întregului set

1. **Ambele capete, o singură metodă** — orice relație bidirecțională are exact un
   punct de intrare public care le scrie pe amândouă.
2. **Package-private e unealta** — capătul „pasiv" (`setPersoana`, `adaugaCurs`,
   constructorul `LinieComanda`/`Curs`) nu e public; așa nimeni nu poate lega jumătate
   de relație din exterior.
3. **Liste doar în copie** — `new ArrayList<>(lista)` la fiecare getter.
4. **Direcția relației = întrebările ieftine** — ții referința pe capătul dinspre care
   pui întrebările.
