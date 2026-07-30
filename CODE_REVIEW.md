# Code Review — adrian-incapsularea

**Scop:** review pe livrarea nouă (`aa00fbd..1bd3893`) — modulele `manyToMany` (Student/Curs), `compozitieVSagregare` (Comanda/LinieComanda/Produs), demo-ul `oneToMany` bidirectional și curățenia din `Main`.

**Focus lecție:** încapsularea relațiilor (cine are voie să modifice legătura) și corectitudinea sincronizării bidirecționale.

---

## 🔴 Critice

### B1 — `Comanda.total()` acumulează într-un câmp, nu într-o variabilă locală
`src/app/relations/compozitieVSagregare/Comanda.java:33`

`total` e câmp de instanță și nu se resetează la începutul metodei. La al doilea apel al lui `total()` valoarea se adună peste cea veche → rezultat dublat. Un total ar trebui să fie o funcție **pură** de liniile comenzii: aceleași linii → același rezultat, oricâte apeluri.

### B2 — `LinieComanda.getSubtotal()` este un getter care mută starea
`src/app/relations/compozitieVSagregare/LinieComanda.java:27`

```java
subtotal += (double) produs.getPret() * cantitate;
```

Un `get...()` trebuie să **citească**, nu să modifice. Aici câmpul `subtotal` crește cu `pret*cantitate` la fiecare citire. Fiindcă `Comanda.total()` apelează `getSubtotal()`, orice recalcul umflă subtotalul fiecărei linii — bug-ul se compune cu B1.

### B3 — `Echipa.transferEchipa()` scoate jucătorul dintr-o COPIE a listei
`src/app/relations/OnToManyBidirectional/Echipa.java:46`

```java
Echipa oldTeam = jucator.getEchipa();
if (oldTeam != null){
    oldTeam.getJucatori().remove(jucator);
}
```

`getJucatori()` întoarce o copie defensivă (`new ArrayList<>(jucatori)`) — corect pentru încapsulare. Exact de aceea `.remove()` pe rezultatul lui nu atinge lista reală a vechii echipe. După transfer, vechea echipă **tot îl conține** pe jucător → relația bidirecțională rămâne desincronizată. Ăsta e chiar obiectivul lecției one-to-many bidirectional.

### B4 — `nrComanda` / `numarLinie` sunt câmpuri de instanță cu `++` → mereu 2
`src/app/relations/compozitieVSagregare/Comanda.java:7,15` · `.../LinieComanda.java:5,12`

```java
private int nrComanda = 1;
public Comanda(){ nrComanda++; }   // fiecare comandă ajunge la 2
```

Un increment pe câmp de instanță nu produce numere unice — fiecare obiect pornește de la 1 și devine 2. Dacă intenția era un ID auto-incremental, contorul trebuie să fie `static` (partajat între toate instanțele).

---

## 🟡 Importante

### M1 — `total()` copiază lista la fiecare iterație
`src/app/relations/compozitieVSagregare/Comanda.java:32-33`

`getArrLinii()` (care face `new ArrayList<>`) e apelat și în condiția `for`, și în corp, la fiecare pas → o copie nouă a listei de fiecare dată. Iterează o singură dată pe lista internă cu un `for-each`.

### M2 — `level4_Comanda()` este metodă moartă/incompletă
`src/app/Main.java`

Creează `p1,p2,p3` și `new Comanda()`, dar nu adaugă nicio linie, nu apelează `total()`, iar `p2`/`p3` nu se folosesc. Nu e nici apelată din `main()`. Ori o completezi ca demo (adaugă linii + print total), ori o scoți.

---

## 🟢 Cleanups

### C1 — cod comentat lăsat în urmă
`src/app/Main.java` — importurile `catalog/comenzi` și metodele `creareCatalog()`, `Nivel2()` comentate în bloc. La predare le-aș șterge; sunt acoperite de modulul nou.

### C2 — `System.out.println` în setter
`src/app/relations/OnToManyBidirectional/Player.java:23` — `setEchipa()` face I/O. Ok pentru demo, dar print-ul stă mai bine în `detailsJucator()`, apelat explicit din `main`.

### Notă — NU e bug: `j5.setEchipa(bvbDortmund)`
`src/app/Main.java` — Adrian a marcat singur cu comentariu „BUG - nu apelăm .setEchipa prin jucător". Bună conștientizare: setează doar direcția player→echipă, deci `bvbDortmund.getJucatori()` nu-l conține. E reversul didactic al lui B3, nu o eroare de corectat.

---

## Before / After (în document, NU aplicat în cod)

| # | Acum | Corect |
|---|------|--------|
| B1 | `private double total;`<br>`public double total(){`<br>`  for(...) total += ...getSubtotal();`<br>`  return total; }` | `public double total(){`<br>`  double total = 0;`<br>`  for (LinieComanda l : arrLinii)`<br>`    total += l.getSubtotal();`<br>`  return total; }` |
| B2 | `private double subtotal;`<br>`public double getSubtotal(){`<br>`  subtotal += produs.getPret()*cantitate;`<br>`  return subtotal; }` | `public double getSubtotal(){`<br>`  return produs.getPret() * cantitate;`<br>`}`  *(fără câmp `subtotal`)* |
| B3 | `oldTeam.getJucatori().remove(jucator);` | `oldTeam.incheieContract(jucator);`<br>*(operează pe lista reală)* |
| B4 | `private int nrComanda = 1;`<br>`public Comanda(){ nrComanda++; }` | `private static int counter = 0;`<br>`private final int nrComanda = ++counter;` |

---

## Q&A — verificare înțelegere

1. **B2:** De ce spunem că un `getSubtotal()` care face `+=` „minte"? Ce garanție se așteaptă de la un getter și ce se întâmplă dacă apelezi metoda de două ori la rând?
2. **B3:** `getJucatori()` întoarce o copie ca să protejeze lista internă — lucru bun. De ce tocmai bunătatea asta face ca `getJucatori().remove(x)` să nu șteargă nimic din echipă? Ce metodă din `Echipa` operează pe lista reală?
3. **B4:** Care e diferența dintre un câmp `static` și unul de instanță când vrei un contor unic pe toate comenzile? De ce `private int nrComanda = 1; nrComanda++;` dă mereu 2?

---

## Ce a mers bine

- Copii defensive consecvente pe toate getterele de listă (`getCursuri`, `getStudenti`, `getJucatori`, `getArrLinii`).
- Sincronizare bidirecțională **corectă** în `Curs.inscrieStudent` / `removeStudent` (adaugă în ambele direcții, verifică duplicat).
- Restrângerea vizibilității `adaugaCurs` / `removeCurs` la package-private — exact ideea de încapsulare a relației: doar `Curs` poate modifica legătura, nu oricine din afară.
