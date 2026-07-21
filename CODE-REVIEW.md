---
title: "Code Review #3 — Proiect Încapsulare"
subtitle: "Runda 3: stare curentă după fix-urile din runda 2"
author: "MyCodeSchool"
lang: ro
geometry: margin=2.2cm
mainfont: "Helvetica Neue"
monofont: "Menlo"
fontsize: 11pt
---

# Cum citești acest review

A treia rundă. Progresul e real — 4 din cele mai grele constatări din runda 2 sunt
rezolvate. Review-ul de față e pe **codul curent**; unde o constatare veche a rămas
deschisă, e marcată ca atare.

Constatările sunt grupate pe priorități:

- 🔴 **Critice (B1, B2…)** — bug-uri reale: codul nu face ce pare că face.
- 🟡 **Importante (M1, M2…)** — design / pattern greșit, chiar tema încapsulării.
- 🟢 **Cleanups (C1, C2…)** — stil, naming, mărunțișuri.

# Progres față de runda 2 ✅

| Constatare veche | Stare |
|---|---|
| B1 `Comanda.adaugaLinie` hardcoda `3` | ✅ Rezolvat — folosește `cantitate`, `Comanda.java:24` |
| B2 verificarea de `null` după `.equals()` | ✅ Rezolvat — `null` primul, `Catalog.java:18` |
| B3 `ContBancar.istoric` era `static` | ✅ Rezolvat — acum instanță, `ContBancar.java:14` |
| B4 `aplicaReducere` împărțire întreagă | ⚠️ Încă deschis + un bug în plus (vezi B2 runda asta) |
| M1 `==`/`!=` la String | ✅ Rezolvat în settere; a rămas doar problema din `numarMasiniManuale` (B1) |
| M3 `nrInstante` doar în constructorul gol | ⚠️ Reparat în `Masina`, dar NU în `MasinaEnc` (vezi M1) |
| M2 `getSold` cu parametru inutil | ❌ Încă deschis (vezi M2) |
| M4 `Persoana.descriere()` privată | ✅ Acum `public`; lipsesc încă getterele (vezi M5) |

\newpage

# 🔴 Critice

## B1. `numarMasiniManuale` compară un obiect `Masina` cu un `String` → mereu 0

`MasinaService.java:124`

```java
String txt = "Manuala";
for(int i=0; i<masini.size(); i++){
    if(masini.get(i).equals(txt)){   // ← Masina.equals("Manuala") e MEREU false
        nrManuale++;
    }
}
```

Compari o **mașină întreagă** cu textul `"Manuala"`. Un obiect `Masina` nu va fi
niciodată `equals` cu un `String`, deci `nrManuale` rămâne `0` indiferent de date.

**Mecanismul de învățat:** sora ei, `numarMasiniAutomate` (linia 111), o face corect:
`masini.get(i).getModTransmisie().equals("Automata")`. Diferența e un singur apel —
`.getModTransmisie()`. Când două metode surori fac același lucru, compară-le linie cu
linie: bug-ul sare imediat în ochi.

## B2. `aplicaReducere` — dublu rupt: împărțire întreagă + rezultatul nu se aplică

`MasinaService.java:467`

```java
double pret = (double) masini.get(i).getPret()
            - ((double)(procentReducere/100) * (double) masini.get(i).getPret());
```

Două bug-uri suprapuse:

1. `procentReducere/100` se calculează în `int` (ambii operanzi sunt `int`), deci pentru
   orice procent sub 100 dă `0`. Cast-ul `(double)` vine **după** împărțire — prea
   târziu, `0` a fost deja pierdut. Reducerea e mereu 0.
2. Chiar dacă math-ul ar fi corect, `pret` e o **variabilă locală** care nu e pusă
   înapoi cu `setPret()`. Deci nimic nu se schimbă în listă. `afisareMasini()` afișează
   prețurile neatinse.

**Mecanismul de învățat:** e a treia întâlnire cu împărțirea întreagă (runda 1 făcea
prețurile 0, runda 2 le lăsa neschimbate). Regula fixă: **la împărțire, măcar un operand
trebuie să fie `double` ÎNAINTE de `/`**. Și separat: un calcul care nu e scris înapoi în
obiect (prin setter) nu are niciun efect — „a calcula" ≠ „a aplica".

\newpage

## Tabel Before/After — criticele

| # | Before (actual) | After (corect) |
|---|---|---|
| B1 | `if(masini.get(i).equals(txt))` | `if(masini.get(i).getModTransmisie().equals(txt))` |
| B2 | `double pret = pret - (double)(procentReducere/100) * pret;` *(local, neaplicat)* | `int nou = (int)(getPret() * (100 - procentReducere) / 100.0); masini.get(i).setPret(nou);` |

*(corecturile NU sunt aplicate în cod — le faci tu, apoi rulezi `Main` să verifici)*

\newpage

# 🟡 Importante

## M1. `MasinaEnc` — constructorul cu 6 parametri nu incrementează `nrInstante`

`MasinaEnc.java:26`

`nrInstante++` există doar în `MasinaEnc()` (fără parametri, linia 20). Obiectele create
cu constructorul complet **nu sunt numărate** — contorul minte.

**Atenție:** în `Masina.java` ai reparat exact asta — ambii constructori incrementează
(liniile 20 și 37). Fix-ul a fost aplicat pe `Masina`, dar nu și pe geamăna ei
`MasinaEnc`. Regula: dacă un câmp `static` numără instanțele, **fiecare** constructor
trebuie să-l incrementeze.

## M2. `getSold(String titular)` — condiție redundantă + parametrul nu verifică nimic

`ContBancar.java:70-83`

```java
if(!titular.isEmpty() && !"".equals(titular)){
```

Trei probleme: (1) cele două condiții verifică **exact același lucru** — a doua e
redundantă; (2) `titular` nu e comparat niciodată cu `this.titular`, deci orice string
nevid „primește" soldul; (3) `null` → NPE la `.isEmpty()`.

Un getter curat n-are parametru: `public double getSold(){ return sold; }`. Dacă vrei
verificare de identitate, aia e altă metodă, cu alt nume, care compară
`titular.equals(this.titular)`.

## M3. `mediePreturiMarca` — împărțire la zero când marca nu există

`MasinaService.java:438`

```java
double mediePreturi = (double) totalPret / (double) ct;   // ct == 0 → NaN
```

Pentru o marcă inexistentă, `ct` rămâne 0, iar `0.0 / 0.0` în `double` dă `NaN` (nu
crapă — și mai viclean). Verifică `ct == 0` înainte de împărțire și tratează cazul
explicit, ca în `numarMasiniMarca`.

## M4. Verificări de `null` lipsă — NPE pe argument `null`

- `ContBancar.java:32` — `setTitular`: `!titular.isEmpty()` fără verificare de `null`;
- `Comanda.java:15` — constructor: `!client.isEmpty()`; dacă primește `""`, obiectul se
  creează dar `client` rămâne `null`, iar `descriere()` va afișa „null";
- `getSold` (vezi M2).

Aceeași ordine peste tot: **întâi `null`, apoi conținut** (`x == null || x.isEmpty()`) —
`||` se oprește la primul `true`, deci partea dreaptă nu se mai evaluează pe `null`.

## M5. `Persoana` — cutie doar-scriere: settere multe, niciun getter

`Persoana.java`

`descriere()` e acum `public` (reparat din runda 2, bun). Dar clasa n-are niciun getter:
poți scrie valorile (settere) fără să le poți citi individual. Încapsularea nu înseamnă
„totul privat" — înseamnă **acces controlat**, adică și intrări (settere validate) și
ieșiri (gettere). Adaugă `getNume()`, `getVarsta()` etc.

\newpage

# 🟢 Cleanups

- **C1. Importuri nefolosite** — `Main.java:3-5` (`ArrayList`, `Arrays`, `List`),
  `Persoana.java:3` (`ArrayList`; `Arrays` și `List` sunt folosite).
- **C2. `Catalog.contine`** (`Catalog.java:41-43`) — `if(x) return x; else return false;`
  → direct `return produse.contains(produs);`.
- **C3. Raw types** — `new ArrayList(produse)` la `Catalog.java:63` și
  `new ArrayList(istoric)` la `ContBancar.java:140` → `new ArrayList<>(...)`.
- **C4. An hardcodat** — `setAnFabricatie` acceptă până la `2025` (`Masina.java:94`,
  `MasinaEnc.java:83`); suntem în 2026.
- **C5. Marcă+model fără spațiu** — `MasinaService.java:84` (`afiseazaMarcaModel`) →
  afișează „BMWX5". Rămas din rundele anterioare.
- **C6. Settere care printează** — `setPret` și `setAnFabricatie` în `Masina`/`MasinaEnc`
  afișează valoarea. Un setter setează; afișarea e treaba apelantului.
- **C7. `Comanda.descriere()`** (`Comanda.java:30-39`) — printează, cheamă `total()`
  (care printează și el) și returnează `""`. Alege un contract: ori returnezi textul (ca
  `Masina.descriere()`), ori e `void afiseaza()`.
- **C8. Naming** — `Main.Nivel2()` (`Main.java:130`) e PascalCase; convenția Java pentru
  metode e `nivel2()`.
- **C9. Vizibilitate inconsistentă** — `blocheaza()` e `public`, `deblocheaza()` e
  package-private (`ContBancar.java:16,20`); `adauga` e package-private
  (`Catalog.java:14`). Pentru tema încapsulării, decide explicit ce face parte din API-ul
  public al clasei.
- **C10. Blacklist case-sensitive** — `Persoana.java:55`: „ana" e blocat, dar „Ana"
  trece; inconsistent cu `setOras`, care normalizează cu `toLowerCase()`.

\newpage

# Rezumat pentru Adrian

Progres solid: `adaugaLinie`, ordinea `null`-ului, `istoric` static și `==` la String —
toate rezolvate. Rundă nouă, în ordinea în care le ataci:

1. **B1** — `numarMasiniManuale` compară `Masina` cu `String` → mereu 0. Un `.getModTransmisie()` lipsă.
2. **B2** — `aplicaReducere`: împărțire întreagă (reducerea = 0) **și** rezultatul nu se aplică prin `setPret`.
3. **M1** — `MasinaEnc` cu 6 parametri nu numără instanța (reparat în `Masina`, uitat aici).

Tema comună: când ai **două metode/clase surori**, una corectă și una greșită
(`numarMasiniAutomate` vs `numarMasiniManuale`, `Masina` vs `MasinaEnc`), citește-le
paralel — bug-ul e mereu diferența dintre ele.

# Q&A — verifică-ți înțelegerea

1. În `numarMasiniManuale`, de ce `masini.get(i).equals("Manuala")` e mereu `false`,
   deși mașina chiar are transmisia „Manuala"? Ce compară de fapt `equals` aici — ce tip
   e în stânga și ce tip e în dreapta?

2. În `aplicaReducere`, presupune că repari doar împărțirea întreagă (faci math-ul
   corect), dar lași `double pret = ...` ca variabilă locală. Rulezi metoda. Ce prețuri
   afișează `afisareMasini()` și de ce? Ce lipsește ca schimbarea să „prindă"?

3. Creezi 3 obiecte `MasinaEnc`: unul cu `new MasinaEnc()` și două cu constructorul de 6
   parametri. Cât returnează `MasinaEnc.getNrInstante()` și de ce nu 3? Unde „locuiește"
   câmpul `nrInstante` — în fiecare obiect sau în clasă?
