---
title: "Code Review — Proiect Încapsulare (runda 2)"
subtitle: "Delta față de review-ul din 21 iulie + constatări noi"
author: "MyCodeSchool"
lang: ro
geometry: margin=2.2cm
mainfont: "Helvetica Neue"
monofont: "Menlo"
fontsize: 11pt
---

# Cum citești acest review

Runda 2 — am recitit tot proiectul (inclusiv `comenzi/` — `Produs`, `LinieComanda`,
care n-au fost sub lupă în runda 1) după commit-urile `incapsulare-realtii` și
`reorder folder structure` (21 iulie). Documentul are trei părți:

1. **Ce ai rezolvat** din runda 1 — ca să știi ce a „prins".
2. **Constatări noi sau rămase**, pe priorități: 🔴 critice, 🟡 importante, 🟢 cleanups.
   Numerotarea **continuă** din runda 1 (B3, M6, C13…), ca să putem vorbi de „B1" fără confuzie.
3. **Q&A** de verificare.

Corecturile NU sunt aplicate în cod — le faci tu, apoi rulezi ca să verifici.

\newpage

# Scoreboard runda 1

| # | Constatare | Status |
|---|---|---|
| B1 | `numarMasiniManuale` compară `Masina` cu `String` | 🔴 **DESCHIS** — bug-ul e tot acolo |
| B2 | `aplicaReducere` — împărțire întreagă + rezultat neaplicat | ✅ Rezolvat (param `double`, `setPret` apelat) |
| M1 | `MasinaEnc` — constructorul cu 6 parametri nu număra instanțele | ✅ Rezolvat (ambii constructori incrementează) |
| M2 | `getSold(String titular)` — parametru fără sens | 🟡 Parțial — condiția redundantă a dispărut, restul rămâne |
| M3 | `mediePreturiMarca` — NaN la marcă inexistentă | 🟡 Parțial — mesaj adăugat, dar împărțirea tot rulează |
| M4 | Null-checks lipsă | 🟡 Deschis — iar în `setNume` riscul a crescut (vezi M4-bis) |
| M5 | `Persoana` fără gettere | ✅ Rezolvat |
| C1 | Importuri nefolosite | ✅ Rezolvat |
| C9 | Vizibilitate inconsistentă (`deblocheaza`, `adauga`) | ✅ Rezolvat (ambele `public`) |
| C10 | Blacklist case-sensitive / mesaj kilometraj | ✅ Rezolvat (`toLowerCase()`; condiția aliniată la mesaj) |
| C12 | Interval de preț cu margini exclusive | ✅ Rezolvat (`>=` / `<=`) |
| C2–C8, C11 | Restul cleanup-urilor | 🟢 Deschise (lista la final) |

Bilanț bun: 7 rezolvate curat, iar B2 + M1 erau cele mai grase. Au rămas un critic
vechi, unul nou și câteva jumătăți de fix.

\newpage

# 🔴 Critice

## B1 (din runda 1) — `numarMasiniManuale` tot compară obiectul cu un `String`

`MasinaService.java:124`

```java
if(masini.get(i).equals("Manuala")){
```

Ai șters variabila `txt` (e comentată la linia 122), dar comparația a rămas identică:
un obiect `Masina` întreg pus la `equals` cu textul `"Manuala"` — mereu `false`, deci
rezultatul e mereu `0`. Fix-ul e tot cel din runda 1: scoate `String`-ul din obiect
înainte de comparație, exact ca în `numarMasiniAutomate` (linia 111):

```java
if(masini.get(i).getModTransmisie().equals("Manuala")){
```

## B3 (nou) — `setTitular`: `!titular.equals(null)` — garda crapă exact în cazul de care trebuia să apere

`ContBancar.java:41`

```java
public void setTitular(String titular){
    if(!titular.equals(null)){
        this.titular = titular;
    }
}
```

Două lucruri, ambele rele:

1. **Pentru orice `titular` non-null**, contractul lui `equals` spune că
   `x.equals(null)` returnează **mereu `false`** — deci `!titular.equals(null)` e mereu
   `true`. Condiția nu filtrează nimic.
2. **Pentru `titular == null`** — chemi metoda `.equals(...)` PE `null`, deci ai
   `NullPointerException` înainte ca comparația să apuce să se evalueze. Garda crapă
   fix în singurul caz pe care voia să-l prindă.

**Mecanismul de învățat:** `null` nu e un obiect — e absența unuia. Nu poți chema
metode pe el, nici măcar `equals`. Verificarea de `null` se face doar cu operatorul
`==` / `!=`, care compară referința fără să atingă obiectul:

```java
if(titular != null){
    this.titular = titular;
}
```

Ai scris exact varianta corectă cu 14 linii mai jos, în constructorul
`ContBancar(String titular)` (linia 55: `if(titular != null)`). Compară-le.

\newpage

## Tabel Before/After — criticele

| # | Before (actual) | After (corect) |
|---|---|---|
| B1 | `if(masini.get(i).equals("Manuala"))` | `if(masini.get(i).getModTransmisie().equals("Manuala"))` |
| B3 | `if(!titular.equals(null))` | `if(titular != null)` |

\newpage

# 🟡 Importante

## M3 (jumătate de fix) — `mediePreturiMarca`: mesajul e afișat, dar NaN-ul tot apare

`MasinaService.java:438-444`

```java
if(ct == 0){
    System.out.println("Numarul de masini este 0.");
}

double mediePreturi = (double) totalPret / (double) ct;   // tot se execută cu ct == 0
System.out.println("Media preturilor pentru: " +marca +": $" + mediePreturi);
```

Ai adăugat mesajul (bine!), dar după `if` execuția **continuă**: împărțirea `0.0/0.0`
tot rulează și utilizatorul vede ambele linii:

```
Numarul de masini este 0.
Media preturilor pentru: Dacia: $NaN
```

**Mecanismul de învățat:** un `if` fără `return` doar *adaugă* un pas, nu *oprește*
drumul. Când cazul special e tratat, ieși din metodă:

```java
if(ct == 0){
    System.out.println("Numarul de masini este 0.");
    return 0;
}
```

Ai pattern-ul ăsta deja corect în `depune`/`retrage` (`ContBancar.java:98-101`):
„cont blocat → mesaj → `return`". Același „early return", alt context.

Bonus, aceeași metodă, linia 432: `!masini.get(i).getMarca().isEmpty() &&` nu ajută cu
nimic — o marcă nevidă care nu e egală cu `marca` pică oricum la a doua condiție.

## M2 (rămas) — `getSold(String titular)`: parametrul tot nu verifică nimic real

`ContBancar.java:79-92`

Condiția redundantă a dispărut, dar esența a rămas: parametrul `titular` nu e comparat
niciodată cu `this.titular` — orice string nevid „primește" soldul — iar pe `null`,
`titular.isEmpty()` aruncă NPE. Recomandarea din runda 1 rămâne: getter curat fără
parametru (`public double getSold(){ return sold; }`); verificarea de identitate, dacă
o vrei, e o metodă separată care face `titular.equals(this.titular)`.

## M4 / M4-bis — null-checks: fix-ul de la C10 a introdus un NPE nou

- `Persoana.java:56` — `blacklist.contains(nume.toLowerCase())`: `toLowerCase()` pe
  `null` crapă. În runda 1, `setNume(null)` doar seta greșit; acum aruncă NPE. Fix-ul
  de case-sensitivity a fost corect, dar a mărit suprafața de `null`.
- `Persoana.java:76,85` — `setOras`/`setGen`: neschimbate, același risc.
- `Comanda.java:15` — `!client.isEmpty()`: neschimbat.

**Mecanismul de învățat (recap):** verificarea de `null` e **prima**, legată cu `||`
short-circuit: `if(nume == null || blacklist.contains(nume.toLowerCase()))`. Ai scris
deja șablonul perfect în `Catalog.adauga` (`Catalog.java:18`):
`if(produs == null || produs.equals(""))` — plus comentariul tău „verificarea de NULL
e mereu prima". Aplică-l și în `Persoana`/`Comanda`.

## M6 (nou) — gemenele au divergat iar: `Masina` a evoluat, `MasinaEnc` a rămas în urmă

Fix-urile din runda asta au fost aplicate doar pe `Masina`:

| Aspect | `Masina` | `MasinaEnc` |
|---|---|---|
| `pret` | `double` (`Masina.java:8`), `setPret(double)` | `int` (`MasinaEnc.java:8`), `setPret(int)` la :69 |
| `setKilometraj` | `> 0` (`Masina.java:88`) | `>= 0` (`MasinaEnc.java:77`) |
| Gettere | complete | fără `getAnFabricatie()`/`getModTransmisie()` (C11, rămas) |

E firul roșu din runda 1, doar cu rolurile inversate: atunci `Masina` era reparată și
`MasinaEnc` uitată la `nrInstante`; acum la `pret`/`kilometraj`. Când repari ceva
într-una din clasele-surori, ultima întrebare înainte de commit e mereu: „geamăna are
aceeași problemă?". (Discuție separată la curs: ăsta e exact motivul pentru care codul
duplicat e scump — și puntea spre moștenire.)

## M7 (nou) — comentarii-misconcepție: notițele greșite te învață greșit

Două locuri; contează pentru că notițele din cod devin materialul tău de recapitulare.

**a) `ContBancar.java:161`**

```java
// daca folosim == la String sau alte Obiecte -> la string, sistemul va compara doar prima litera
```

Fals. `==` pe obiecte compară **referințele** — „arată cele două variabile spre exact
același obiect în memorie?" — nu conținutul și nu vreo literă. De-aia două String-uri cu
același text pot da `false` la `==` (obiecte diferite) și de-aia există `equals`: el
compară conținutul.

**b) `Masina.java:106-107` și `MasinaEnc.java:95-96` (`setModTransmisie`)**

```java
return;
// CONSTRUCTORUL NU POATE RETURNA O VALOARE
```

Comentariul e într-un **setter**, nu într-un constructor — iar regula e altfel decât
scrie: și constructorii, și metodele `void` POT folosi `return;` (gol, ca „ieși acum");
ce nu pot constructorii e `return valoare;`. Separat, acest `return;` e ultima
instrucțiune din metodă — metoda se termina oricum, deci nu face nimic.

## M8 (nou) — constructori care refuză valoarea dar construiesc obiectul pe jumătate

Același tipar în ambele clase din `comenzi`:

**`LinieComanda.java:9-18`**

```java
public LinieComanda(Produs produs, int cantitate){
    this.produs = produs;                 // fără null-check → NPE mai târziu, în subtotal()
    if(cantitate >=1){
        this.cantitate = cantitate;
    } else {
        System.out.println("cantitatea nu poate fi mai mica de 1.");
    }
}
```

Dacă `cantitate` e 0, mesajul se afișează, dar **obiectul tot se creează**, cu
`cantitate = 0` — o linie de comandă „fantomă" cu subtotal 0. Iar dacă `produs` e
`null`, construcția reușește și abia `subtotal()` crapă cu NPE, departe de locul
greșelii. Același lucru în `Comanda.java:14-18`: `client` invalid → obiect creat cu
`client = null`, iar `total()` afișează „Totalul comenzii lui null".

**Mecanismul de învățat:** un constructor e paznicul **invariantului** clasei — promisiunea
că „orice `LinieComanda` care există e validă". Un `if` care doar afișează mesajul și
merge mai departe rupe promisiunea: refuză valoarea, dar livrează obiectul stricat.
La nivelul de acum (fără excepții încă), soluția practică e să validezi **înainte** de
`new` — exact ce face `Comanda.adaugaLinie` (linia 23), care nu creează linia dacă
`cantitate < 1`. De discutat la curs: pasul următor e ca constructorul să arunce
`IllegalArgumentException` — atunci obiectul invalid nici nu apucă să existe.

Contrast interesant: `Produs` (`Produs.java:8-11`) nu validează **nimic** — acceptă
preț negativ și nume `null`, deși `Masina.setPret` refuză prețuri negative. Într-un
proiect despre încapsulare, `Produs` e clasa care a rămas fără paznic.

\newpage

# 🟢 Cleanups

## Noi

- **C13. Gardurile din `blocheaza`/`deblocheaza` nu schimbă nimic** —
  `ContBancar.java:21,28`: a seta `isActive = false` când e deja `false` produce exact
  aceeași stare; `if`-ul e cod în plus fără efect observabil. Ar căpăta sens doar dacă
  ramura „deja blocat" ar face ceva (mesaj sau intrare în `istoric`) — altfel, versiunea
  din runda 1 (fără `if`) era mai simplă și la fel de corectă.
- **C14. Comentarii rătăcite** — `ContBancar.java:84`: `// ← != pe String compară
  referințe...` e lipit de o linie care folosește `isEmpty()`, nu `!=`; și
  `MasinaService.java:249`: `//prints the last result of masini.get(i).marca` descrie un
  bug care nu mai există (metoda afișează corect cea mai ieftină mașină). Rămase de la
  refactor-uri; șterge-le sau mută-le unde chiar sunt relevante.
- **C15. `setKilometraj > 0` respinge acum 0 km** — `Masina.java:88`: ai aliniat
  condiția la mesaj (ok ca decizie), dar o mașină nouă are legitim 0 km. De discutat
  care era intenția; era la fel de valid să corectezi mesajul și să păstrezi `>= 0`.
  Notă: pe `MasinaEnc.java:77-79` a rămas exact nepotrivirea veche (condiție `>= 0`,
  mesaj „nu poate fi 0 sau mai mic") — încă un exemplu pentru M6.
- **C16. `getSold` afișează „1000.0LEI"** — `ContBancar.java:85`: lipsește spațiul
  înainte de „LEI" (frate cu C5 „AudiA4").
- **C17. `descriere()` returnează `null`** — `ContBancar.java:133-136`: apelantul face
  `System.out.println(cont.descriere())` și vede „Titularul nu exista." urmat de
  „null". Returnează `""` (sau doar textul de eroare) în loc de `null`. Tot aici:
  constructorul cu 1 parametru printează `descriere()` (linia 63), cel cu 2 parametri
  nu — comportament inconsistent între constructori, iar afișarea nu e treaba
  constructorului (vezi C6).
- **C18. Contoare de unică folosință** — `cautaPrimaMasinaMarca`
  (`MasinaService.java:371-382`): `ctMasina++` urmat imediat de `return` înseamnă că
  după buclă `ctMasina` e mereu 0 — `if(ctMasina == 0)` e echivalent cu „dacă am ajuns
  aici". Poți șterge contorul: mesajul de „negăsit" de după buclă se execută oricum
  doar când `return`-ul din buclă nu s-a întâmplat. Similar `ct`+`break` în
  `existaMasinaMarca` (:388-397).
- **C19. `numarMasiniNoisiIeftine` tace când rezultatul e 0** — `MasinaService.java:417-421`:
  toate metodele-surori afișează ceva și pentru „nimic găsit"; asta returnează 0 în
  liniște. Aliniaz-o la restul (mesaj + `return ct;` simplu, fără `if` pe afișare).

## Rămase din runda 1 (neschimbate)

- **C2** — `Catalog.contine` (`Catalog.java:41-43`): `return produse.contains(produs);` direct.
- **C3** — raw types: `new ArrayList(produse)` (`Catalog.java:63`), `new ArrayList(istoric)` (`ContBancar.java:149`) → `new ArrayList<>(...)`.
- **C4** — an hardcodat `2025` (`Masina.java:94`, `MasinaEnc.java:83`); suntem în 2026.
- **C5** — „AudiA4" fără spațiu (`MasinaService.java:84`).
- **C6** — settere care printează (`setPret`, `setAnFabricatie`); constructorii fără parametri apelează `descriere()` și aruncă rezultatul (`Masina.java:19`, `MasinaEnc.java:19`, `Persoana.java:25`).
- **C7** — `Comanda.descriere()` (`Comanda.java:30-39`): printează + cheamă `total()` (care printează și el) + returnează `""` — alege un singur contract.
- **C8** — `Main.Nivel2()` (`Main.java:131`) → `nivel2()` (camelCase).
- **C11** — `MasinaEnc` fără `getAnFabricatie()`/`getModTransmisie()` (vezi M6).

# Rezumat — ordinea de atac

1. **B1** — `.getModTransmisie()` înainte de `.equals("Manuala")` (a doua oară cu sentimente).
2. **B3** — `setTitular`: `titular != null`, nu `!titular.equals(null)`.
3. **M3** — `return 0;` în interiorul lui `if(ct == 0)`.
4. **M8** — validare în `Produs`/`LinieComanda`/`Comanda`, fără obiecte pe jumătate construite.
5. **M6** — plimbă fix-urile de pe `Masina` și pe `MasinaEnc` (pret `double`, gettere lipsă).
6. **M4** — null-checks după șablonul tău din `Catalog.adauga`.
7. **M7** — corectează comentariile despre `==` și despre `return;`.

# Q&A — verifică-ți înțelegerea

1. **B3:** De ce `titular.equals(null)` nu poate returna `true` niciodată pentru un
   `titular` valid? Și ce se întâmplă, pas cu pas, când `titular` chiar E `null` —
   pe ce linie și de ce apare excepția?

2. **M3:** Apelezi `mediePreturiMarca("Dacia")` (marcă inexistentă în listă). Exact ce
   două linii se afișează în consolă, în ce ordine, și ce singur cuvânt-cheie lipsește
   ca a doua linie să nu mai apară deloc?

3. **M8:** Rulezi `new LinieComanda(produsValid, 0)`. Se creează obiectul? Ce valoare
   are `cantitate` și ce returnează `subtotal()`? Ce promisiune („invariant") a clasei
   e ruptă — și unde în `Comanda` există deja o verificare care previne exact cazul ăsta?

4. **M6:** `aplicaReducere` merge acum pe `Masina` pentru că `setPret` primește
   `double`. Dacă ai copia metoda identic într-un `MasinaEncService`, ar compila?
   Ce s-ar întâmpla la `setPret(pret)` și de ce tipul parametrului contează aici?
