---
title: "Code Review — Proiect Încapsulare"
subtitle: "Analiză completă a codului curent"
author: "MyCodeSchool"
lang: ro
geometry: margin=2.2cm
mainfont: "Helvetica Neue"
monofont: "Menlo"
fontsize: 11pt
---

# Cum citești acest review

Am recitit tot proiectul, clasă cu clasă și metodă cu metodă. Constatările sunt grupate
pe priorități — atacă-le în ordinea asta:

- 🔴 **Critice (B1, B2…)** — bug-uri reale: codul rulează, dar nu face ce pare că face.
- 🟡 **Importante (M1, M2…)** — design / pattern greșit, chiar tema încapsulării.
- 🟢 **Cleanups (C1, C2…)** — stil, naming, mărunțișuri.

Separ mereu **ce e greșit** (diagnostic cu `fișier:linie`) de **ce înveți din asta**
(mecanismul de dedesubt, pe scurt). Corecturile NU sunt aplicate în cod — le faci tu,
apoi rulezi ca să verifici.

\newpage

# 🔴 Critice

## B1. `numarMasiniManuale` compară un obiect `Masina` cu un `String` → mereu 0

`MasinaService.java:120-131`

```java
int numarMasiniManuale(){
    int nrManuale = 0;
    String txt = "Manuala";
    for(int i=0; i<masini.size(); i++){
        if(masini.get(i).equals(txt)){   // ← Masina.equals("Manuala") e MEREU false
            nrManuale++;
        }
    }
    ...
}
```

`masini.get(i)` e un obiect `Masina` întreg; îl compari cu textul `"Manuala"`. Un
`Masina` nu va fi niciodată `equals` cu un `String`, deci `nrManuale` rămâne `0`
indiferent câte mașini manuale ai.

**Mecanismul de învățat:** metoda soră, `numarMasiniAutomate` (linia 106), o face corect:
```java
if(masini.get(i).getModTransmisie().equals("Automata")){ ... }
```
Diferența e un singur apel — `.getModTransmisie()`, care scoate `String`-ul din obiect
înainte de comparație. Când ai două metode surori și una merge iar alta nu, citește-le
paralel: bug-ul e fix diferența dintre ele.

## B2. `aplicaReducere` — dublu rupt: împărțire întreagă ȘI rezultatul nu se aplică

`MasinaService.java:458-471`

```java
void aplicaReducere(int procentReducere){
    for(int i=0; i<masini.size(); i++){
        double pret = (double) masini.get(i).getPret()
                    - ((double)(procentReducere/100) * (double) masini.get(i).getPret());
    }
    afisareMasini();
}
```

Două bug-uri suprapuse:

1. **Împărțire întreagă:** `procentReducere/100` are ambii operanzi `int`, deci pentru
   orice procent sub 100 rezultatul e `0` (ex. `10/100 = 0`). Cast-ul `(double)` vine
   *după* împărțire — prea târziu, zeroul e deja fixat. Reducerea e mereu 0.
2. **Rezultat neaplicat:** chiar dacă math-ul ar fi corect, `pret` e o variabilă
   **locală** care nu e pusă înapoi în obiect cu `setPret()`. Se calculează și se aruncă.
   `afisareMasini()` afișează prețurile neatinse.

**Mecanismul de învățat:** două lecții într-un loc. (a) La împărțire, **măcar un operand
trebuie să fie `double` ÎNAINTE de `/`** — altfel pierzi partea fracționară. (b) Un
calcul care nu e scris înapoi în obiect (prin setter) nu are niciun efect: „a calcula" ≠
„a aplica". Compară cu `cresteKilometrajul` (linia 445), unde chiar apelezi
`setKilometraj(...)` — de-aia *acolo* schimbarea prinde.

\newpage

## Tabel Before/After — criticele

| # | Before (actual) | After (corect) |
|---|---|---|
| B1 | `if(masini.get(i).equals(txt))` | `if(masini.get(i).getModTransmisie().equals(txt))` |
| B2 | `double pret = getPret() - (double)(procentReducere/100) * getPret();` *(local, neaplicat)* | `int nou = (int)(getPret() * (100 - procentReducere) / 100.0); masini.get(i).setPret(nou);` |

\newpage

# 🟡 Importante

## M1. `MasinaEnc` — constructorul cu 6 parametri nu incrementează `nrInstante`

`MasinaEnc.java:26-36`

`nrInstante++` apare doar în constructorul fără parametri (linia 20). Obiectele create cu
constructorul complet **nu sunt numărate**, deci `getNrInstante()` returnează un număr
mai mic decât realitatea.

**Mecanismul de învățat:** în `Masina.java` ai reparat exact asta — ambii constructori
fac `nrInstante++` (liniile 20 și 37). Fix-ul a fost aplicat pe `Masina`, dar uitat pe
geamăna `MasinaEnc`. Regula: dacă un câmp `static` numără instanțele, **fiecare**
constructor trebuie să-l incrementeze.

## M2. `getSold(String titular)` — condiție redundantă, parametru inutil, NPE pe `null`

`ContBancar.java:70-83`

```java
public double getSold(String titular){
    if(!titular.isEmpty() && !"".equals(titular)){   // ambele condiții verifică ACELAȘI lucru
        System.out.println("Soldul tau curent este: " + sold + "LEI");
        return sold;
    } ...
}
```

Trei probleme: (1) `!titular.isEmpty()` și `!"".equals(titular)` verifică exact același
lucru — a doua e redundantă; (2) parametrul `titular` nu e comparat niciodată cu
`this.titular`, deci orice string nevid „primește" soldul — verificarea nu verifică nimic
real; (3) dacă primești `null`, `titular.isEmpty()` aruncă `NullPointerException`.

**Mecanismul de învățat:** un getter curat returnează starea obiectului, nu are nevoie de
parametru: `public double getSold(){ return sold; }`. Dacă vrei verificare de identitate
(„ești chiar titularul?"), aia e altă metodă, cu alt nume, care compară
`titular.equals(this.titular)`.

## M3. `mediePreturiMarca` — împărțire la zero când marca nu există

`MasinaService.java:426-441`

```java
double mediePreturi = (double) totalPret / (double) ct;   // ct == 0 → NaN
```

Pentru o marcă inexistentă, `ct` rămâne `0`, iar `0.0 / 0.0` în `double` dă `NaN`. Nu
crapă — și de-aia e viclean: afișează „media este: NaN" în loc de un mesaj clar. Verifică
`ct == 0` înainte de împărțire și tratează cazul explicit, exact ca în `numarMasiniMarca`
(linia 147).

## M4. Verificări de `null` lipsă — NPE pe argument `null`

Același tipar în mai multe locuri:

- `ContBancar.java:32` — `setTitular`: `!titular.isEmpty()` fără verificare de `null`;
- `Comanda.java:15` — constructor: `!client.isEmpty()`; dacă primești `""`, obiectul se
  creează dar `client` rămâne `null`, iar `descriere()` va afișa „null";
- `Persoana.java:76,85` — `setOras`/`setGen`: `oras.toLowerCase()` / `gen.equalsIgnoreCase(...)` pe `null` crapă.

**Mecanismul de învățat:** verificarea de `null` se pune **prima**, cu `||`:
`x == null || x.isEmpty()`. Operatorul `||` se oprește la primul `true` (*short-circuit*),
deci dacă `x` e `null`, partea dreaptă nici nu se mai evaluează — exact ce te apără de NPE.

## M5. `Persoana` — cutie doar-scriere: settere multe, niciun getter

`Persoana.java`

`descriere()` e `public` (bun), dar clasa n-are niciun getter. Poți **scrie** valorile
(settere validate), dar nu le poți **citi** individual din afară. Încapsularea nu
înseamnă „totul privat" — înseamnă **acces controlat**: și intrări (settere care
validează), și ieșiri (gettere). Adaugă `getNume()`, `getVarsta()`, `getOras()`,
`getGen()`.

\newpage

# 🟢 Cleanups

- **C1. Importuri nefolosite** — `Main.java:3-5` (`ArrayList`, `Arrays`, `List` — niciunul
  folosit); `Persoana.java:3` (`ArrayList`; `Arrays` și `List` sunt folosite, `ArrayList` nu).
- **C2. `Catalog.contine`** (`Catalog.java:39-44`) — `if(x) return x; else return false;`
  apelează `contains` de două ori; scrie direct `return produse.contains(produs);`.
- **C3. Raw types** — `new ArrayList(produse)` (`Catalog.java:63`) și `new ArrayList(istoric)`
  (`ContBancar.java:140`) → `new ArrayList<>(...)`, altfel pierzi verificarea de tip la compilare.
- **C4. An hardcodat** — `setAnFabricatie` acceptă până la `2025` (`Masina.java:94`,
  `MasinaEnc.java:83`); suntem în 2026, mașinile din anul curent sunt respinse.
- **C5. Marcă+model fără spațiu** — `afiseazaMarcaModel` (`MasinaService.java:84`) afișează
  „AudiA4"; lipsește un `" "` între ele.
- **C6. Settere care printează / apel inutil** — `setPret` și `setAnFabricatie`
  (`Masina`/`MasinaEnc`) afișează valoarea; un setter setează, afișarea e treaba
  apelantului. Tot aici: constructorul fără parametri (`Masina.java:19`, `MasinaEnc.java:19`)
  apelează `this.descriere()` și aruncă rezultatul — apel fără efect.
- **C7. `Comanda.descriere()`** (`Comanda.java:30-39`) — printează, cheamă `total()` (care
  printează și el) și returnează `""`. Alege un contract: ori construiește și returnează
  textul (ca `Masina.descriere()`), ori e `void afiseaza()`.
- **C8. Naming** — `Main.Nivel2()` (`Main.java:130`) e PascalCase; convenția Java pentru
  metode e `camelCase`: `nivel2()`.
- **C9. Vizibilitate inconsistentă** — `blocheaza()` e `public`, `deblocheaza()` e
  package-private (`ContBancar.java:16,20`); `adauga` e package-private (`Catalog.java:14`).
  Pentru tema încapsulării, decide explicit ce face parte din API-ul public al clasei.
- **C10. Validări inconsistente** — `Persoana.setNume` (`Persoana.java:55`) are blacklist
  scris cu litere mici, dar compară `nume` direct: „ana" e blocat, „Ana" trece (spre
  deosebire de `setOras`, care normalizează cu `toLowerCase()`). Și: `setKilometraj`
  (`Masina.java:88`) acceptă `>= 0`, dar mesajul zice „nu poate fi 0 sau mai mic".

\newpage

# Ce e deja bine făcut

Merită spus explicit, ca să știi ce să păstrezi:

- **Copii defensive** — `extras()` (`ContBancar.java:140`), `toateProdusele()`
  (`Catalog.java:63`) și `afiseazaMasiniOrdonateDupaPret()` (`MasinaService.java:481`)
  lucrează pe o copie a listei, nu pe cea originală. Exact așa se protejează starea internă.
- **Câmpuri instanță, nu static** — comentariile din `Comanda.java:10` și `ContBancar.java:12`
  arată că ai înțeles de ce `istoric`/`listaLinii` sunt pe obiect, nu pe clasă.
- **Validare la intrare** — majoritatea setterelor verifică valorile înainte să scrie
  (vârstă ≥ 0, preț > 0, oraș din listă). E chiar miezul încapsulării.

# Rezumat — ordinea de atac

1. **B1** — `numarMasiniManuale`: adaugă `.getModTransmisie()` înainte de `.equals(txt)`.
2. **B2** — `aplicaReducere`: repară împărțirea (`/100.0`) **și** aplică rezultatul cu `setPret`.
3. **M1** — `MasinaEnc`: `nrInstante++` și în constructorul cu 6 parametri.
4. **M2–M5** — getter curat pentru sold, `ct == 0` la medie, `null`-checks, gettere pe `Persoana`.

Firul roșu: când ai **două entități surori** — una corectă, una nu
(`numarMasiniAutomate` vs `numarMasiniManuale`, `Masina` vs `MasinaEnc`) — citește-le
paralel. Bug-ul e mereu diferența dintre ele.

# Q&A — verifică-ți înțelegerea

1. În `numarMasiniManuale`, de ce `masini.get(i).equals("Manuala")` e mereu `false`, deși
   mașina chiar are transmisia „Manuala"? Ce tip e în stânga lui `equals` și ce tip e în
   dreapta?

2. Presupune că în `aplicaReducere` repari doar împărțirea (faci `/100.0`), dar lași
   `double pret = ...` ca variabilă locală. Rulezi metoda. Ce prețuri afișează
   `afisareMasini()` și de ce? Ce linie lipsește ca schimbarea să „prindă"?

3. Creezi 3 obiecte `MasinaEnc`: unul cu `new MasinaEnc()` și două cu constructorul de 6
   parametri. Cât returnează `MasinaEnc.getNrInstante()` și de ce nu 3? Unde „locuiește"
   câmpul `nrInstante` — în fiecare obiect, sau unul singur în clasă?
