# Exerciții — Încapsulare (mai grele)

Ai făcut deja încapsularea de bază: atribute `private`, `getteri`, `setteri` cu verificare
(ca în clasa `Persoana`). Aici e la fel, doar că exercițiile sunt mai mari.

Trei lucruri noi, spuse pe scurt:

1. **Când o clasă ține o listă `private`, nu dai lista afară.** Nu faci un getter care
   întoarce chiar lista. Oferi metode: `adauga`, `numar`, `contine`. Iar dacă chiar vrei
   să dai lista, întorci o **copie** a ei, nu originalul.
2. **Un obiect poate ține în el alt obiect.** O `Comanda` are un client și mai multe
   produse. Fiecare clasă își păzește singură datele ei.
3. **O regulă poate lega două câmpuri.** De exemplu `min` să nu fie niciodată mai mare
   decât `max`. Verifici regula în constructor și în fiecare setter.

Regula jocului rămâne: **citești cerința, scrii singur clasa, verifici din `Main`**.
Fiecare clasă în fișierul ei, sub `src/app/`.

---

## Nivel 1 — O clasă care ține o listă private

Creează clasa `Catalog` cu un singur câmp: `private List<String> produse`
(o inițializezi goală în constructor). Ideea: **lista nu iese întreagă din clasă**.

### 1. Adaugă un produs
```java
void adauga(String produs)
```
Refuză `null`, textul gol, și un produs care există deja. Altfel afișează
`Produs invalid sau existent deja.`

### 2. Întrebări despre listă, fără s-o dai afară
```java
int numarProduse()
boolean contine(String produs)
```

### 3. Șterge un produs
```java
void sterge(String produs)
```
Dacă nu există, afișează `Produsul nu exista in catalog.`

### 4. Dă o copie, nu originalul
```java
List<String> toateProdusele()
```
Întoarce o listă **nouă** cu aceleași produse (`new ArrayList<>(produse)`), nu lista din clasă.

**Verificare:** iei lista din `toateProdusele()`, o golești cu `.clear()`, apoi
`numarProduse()` din `Catalog` trebuie să rămână la fel. Dacă scade, înseamnă că ai dat
afară lista adevărată — exact greșeala pe care o eviți aici.

---

## Nivel 2 — Un obiect care ține alt obiect

Ai nevoie de 3 clase mici. Fiecare cu câmpuri `private` și verificare în setteri.

`Produs`: `nume` (String), `pret` (double, trebuie `> 0`).

`LinieComanda`: `produs` (Produs), `cantitate` (int, trebuie `>= 1`).
```java
double subtotal()      // pret * cantitate
```

`Comanda`: `client` (String, ne-gol) și `private List<LinieComanda> linii`.

### 5. Construiește corect
Fiecare clasă primește valorile prin constructor și le trece prin setteri (ca în `Persoana`),
nu scrie direct în câmpuri.

### 6. Adaugă o linie în comandă
```java
void adaugaLinie(Produs produs, int cantitate)
```
Face singur o `LinieComanda` și o pune în listă. Verifică `cantitate`.

### 7. Totalul comenzii
```java
double total()
```
Aduni toate `subtotal()`-urile din linii.

**Verificare:** `Produs("Cafea", 15)` cu cantitate `2` plus `Produs("Ceai", 10)` cu
cantitate `3` -> `total()` = `60`.

### 8. Descriere
```java
String descriere()
```
Afișează clientul și fiecare linie (`nume x cantitate = subtotal`), apoi totalul.

---

## Nivel 3 — O regulă care leagă două câmpuri

Aici regula ține de două câmpuri deodată, deci o verifici în constructor ȘI în fiecare setter.

### 9. Interval corect
`Interval`: `private int min`, `private int max`, cu regula `min <= max`.
```java
void setMin(int min)     // refuza daca min ar deveni mai mare decat max
void setMax(int max)     // refuza daca max ar deveni mai mic decat min
boolean contine(int x)   // adevarat daca min <= x <= max
int lungime()            // max - min
```
**Verificare:** `Interval(5, 10)`, apoi `setMin(12)` -> refuz (12 > 10), `min` rămâne `5`.

### 10. Rezervare pe zile
`Rezervare`: `ziStart` (1..31), `ziEnd` (1..31), cu regula `ziStart <= ziEnd`.
```java
int nrNopti()                       // ziEnd - ziStart
boolean seSuprapune(Rezervare alta) // adevarat daca au zile comune
```
**Verificare:** `[3..7]` și `[6..9]` -> `true`; `[3..7]` și `[8..10]` -> `false`.

### 11. Produs cu preț redus
`ProdusRedus`: `pretIntreg` (`> 0`) și `pretRedus` (`> 0`), cu regula `pretRedus <= pretIntreg`.
```java
double procentReducere()   // cat la suta e mai mic pretul redus
```
**Verificare:** `pretIntreg = 100`, `pretRedus = 80` -> `procentReducere()` = `20.0`.

---

## Nivel 4 — Cont cu stare și cu listă de mișcări

Pornește de la `ContBancar` (titular și `sold`, ambele `private`). Adaugi două lucruri noi,
amândouă tot `private`.

### 12. Contul poate fi pornit sau oprit
Adaugă `private boolean activ` (pornește `true`). Câmpul NU are setter public — se schimbă
doar prin cele două metode:
```java
void blocheaza()
void deblocheaza()
boolean esteActiv()
```

### 13. Operațiile ascultă de stare
`depune` și `retrage` refuză orice, dacă contul e blocat: `Cont blocat. Operatie refuzata.`

**Verificare:** cont pornit, `depune(100)` merge; `blocheaza()`, apoi `depune(50)` -> refuz.

### 14. Listă privată cu ce s-a întâmplat
Adaugă `private List<String> istoric`. La fiecare depunere sau retragere reușită, adaugi o
linie (ex: `Depunere: +100`, `Retragere: -50`).
```java
List<String> extras()     // intoarce o COPIE a listei, ca la Nivel 1
int numarMiscari()
```

### 15. Soldul nu scade sub zero
`retrage` nu are voie să lase `sold` sub `0`. Verifici asta împreună cu starea `activ`.

---

## Nivel 5 — Exercițiu final: `Magazin`

Leagă ce ai făcut. `Magazin` are `private List<Produs> stoc` și
`private List<Comanda> comenzi`. Nicio listă nu iese întreagă afară.

### 16. Stocul
```java
void adaugaInStoc(Produs p)
Produs gasesteDupaNume(String nume)   // null daca nu exista
int numarProduseInStoc()
```

### 17. Trimite o comandă
```java
void plaseazaComanda(Comanda c)
```
Acceptă doar comenzi cu cel puțin o linie și total `> 0`. Altfel `Comanda invalida.`

### 18. Rapoarte
```java
double venitTotal()             // suma total() a tuturor comenzilor
Comanda comandaCeaMaiMare()     // comanda cu totalul cel mai mare
int numarComenzi()
```

### 19. Cel mai scump produs din stoc
```java
Produs celMaiScumpProdus()
```
Fără să dai lista `stoc` afară — cauți în interiorul clasei `Magazin`.

### 20. Întrebare (răspunzi în scris, într-un comentariu în `Main`)
De ce e periculos ca `Magazin` să aibă `public List<Produs> stoc` și cineva să scrie
`magazin.stoc.clear();`? Ce se pierde? Scrie 2–3 fraze.
