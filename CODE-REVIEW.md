---
title: "Code Review #2 — Proiect Încapsulare"
subtitle: "Runda 2: Comanda / LinieComanda / Catalog / ContBancar / MasinaEnc / MasinaService"
author: "MyCodeSchool"
lang: ro
geometry: margin=2.2cm
mainfont: "Helvetica Neue"
monofont: "Menlo"
fontsize: 11pt
---

# Cum citești acest review

Este a doua rundă de review. Prima rundă (bug-urile `setAnFabricatie`, `aplicaReducere`
cu preț 0, titularul nesetat, `==` la String) a fost în mare parte rezolvată — vezi
secțiunea „Progres față de runda 1". Review-ul de față acoperă tot proiectul, inclusiv
clasele noi (`Produs`, `Comanda`, `LinieComanda`, `Catalog`).

Constatările sunt grupate pe priorități:

- 🔴 **Critice (B1, B2…)** — bug-uri reale: codul nu face ce pare că face.
- 🟡 **Importante (M1, M2…)** — design / pattern greșit, chiar tema încapsulării.
- 🟢 **Cleanups (C1, C2…)** — stil, naming, mărunțișuri.

# Progres față de runda 1 ✅

| Constatare veche | Stare |
|---|---|
| #1 `setAnFabricatie` verifica câmpul, nu parametrul | ✅ Rezolvat — `MasinaEnc.java:79` |
| #5 Constructorul `MasinaEnc` ignora parametrii | ✅ Rezolvat — trece prin settere, `MasinaEnc.java:27-30` |
| #3 `ContBancar(titular, sold)` nu seta titularul | ✅ Rezolvat — `ContBancar.java:60` |
| #8 `getSold` nu returna nimic | ✅ Parțial — acum returnează `double`, dar design-ul e tot confuz (vezi M2) |
| `retrage` cu `suma < sold` | ✅ Rezolvat — `ContBancar.java:109` |
| Import nefolosit `java.sql.SQLOutput` | ✅ Șters |
| #4 `==` la String | ⚠️ Parțial — rezolvat în `numarMasiniAutomate`, dar au mai rămas 3 locuri (vezi M1) |
| #6 `Persoana.descriere()` e `private` | ❌ Încă deschis (vezi M4) |

\newpage

# 🔴 Critice

## B1. `Comanda.adaugaLinie` ignoră cantitatea primită — hardcodat `3`

`Comanda.java:24`

```java
public void adaugaLinie(Produs produs, int cantitate){
    if(cantitate >=1) {
        LinieComanda lineItem = new LinieComanda(produs, 3);   // ← 3, nu cantitate!
```

Parametrul `cantitate` e folosit doar la validare, apoi e aruncat. În `Main.Nivel2`,
`c1.adaugaLinie(p3, 10)` ar trebui să adauge 10 cafele (500 LEI), dar adaugă 3 (150 LEI).
Totalul comenzii iese greșit **fără nicio eroare** — cel mai periculos tip de bug.

**Mecanismul de învățat:** un parametru validat dar nefolosit e simetricul bug-ului
`setAnFabricatie` din runda 1 (câmp folosit în loc de parametru). Ambele au aceeași
rădăcină: după ce scrii validarea, verifică că valoarea care *trece* de validare e cea
care se și *folosește*.

## B2. `Catalog.adauga` — verificarea de `null` vine DUPĂ `.equals()` → NPE

`Catalog.java:18`

```java
if(produs.equals("") || produs == null){   // ← dacă produs e null, .equals() aruncă NPE
```

Dacă cineva apelează `adauga(null)`, linia crapă cu `NullPointerException` **înainte**
să ajungă la verificarea `produs == null`. Mesajul de eroare pregătit („Produsul nu
poate fi NULL...") nu se afișează niciodată pentru cazul NULL.

**Mecanismul de învățat:** `||` evaluează stânga → dreapta și se oprește la primul
`true` (*short-circuit*). De aceea verificarea de `null` se pune **prima**:
`produs == null || produs.isEmpty()`. Cu ordinea corectă, dacă `produs` e `null`,
partea dreaptă nici nu se mai evaluează — exact ce te protejează de NPE.

## B3. `ContBancar.istoric` e `static` — toate conturile împart același istoric

`ContBancar.java:12`

```java
private static List<String> istoric = new ArrayList<>();
```

`static` = câmpul aparține **clasei**, nu obiectului. Dacă Popescu depune 100 și Ionescu
depune 500, `extras()` pe oricare dintre conturi arată **ambele** operațiuni. Într-o
bancă reală asta e scurgere de date între clienți.

**Mecanismul de învățat:** chiar tu ai scris regula corectă în `Comanda.java:10-11`:
*„NOT static — o comanda are propria lista"*. Aceeași regulă se aplică identic aici:
istoricul e al contului, nu al băncii. `static` e corect doar pentru ce e comun tuturor
instanțelor — ca `nrInstante` din `MasinaEnc`.

## B4. `aplicaReducere` — împărțire de întregi: reducerea nu face NIMIC

`MasinaService.java:612`

```java
int pret = masini.get(i).pret - procentReducere/100 * masini.get(i).pret;
```

`/` și `*` au aceeași prioritate și se evaluează stânga → dreapta, deci întâi se
calculează `procentReducere/100`. Pentru orice procent sub 100, în `int`, asta dă `0`
(ex. `10/100 = 0`). Apoi `0 * pret = 0`, iar `pret - 0 = pret`. Rezultat: metoda
afișează mașinile cu **prețurile neschimbate**, indiferent de procent.

**Mecanismul de învățat:** în runda 1 același calcul făcea toate prețurile 0; acum le
lasă neschimbate — ambele variante cad în aceeași capcană: **împărțirea între doi `int`
e împărțire întreagă**. Repararea cere ca măcar un operand să fie `double` înainte de
împărțire, apoi conversie la final.

\newpage

## Tabel Before/After — criticele

| # | Before (actual) | After (corect) |
|---|---|---|
| B1 | `new LinieComanda(produs, 3);` | `new LinieComanda(produs, cantitate);` |
| B2 | `if(produs.equals("") \|\| produs == null)` | `if(produs == null \|\| produs.isEmpty())` |
| B3 | `private static List<String> istoric = new ArrayList<>();` | `private List<String> istoric = new ArrayList<>();` |
| B4 | `int pret = masini.get(i).pret - procentReducere/100 * masini.get(i).pret;` | `int pret = (int)(masini.get(i).pret * (100 - procentReducere) / 100.0);` |

*(corecturile NU sunt aplicate în cod — le faci tu, apoi rulezi `Main` să verifici)*

\newpage

# 🟡 Importante

## M1. Au mai rămas comparații de String cu `==` / `!=` — plus un comentariu greșit

- `ContBancar.java:30` — `titular != ""` în `setTitular`;
- `MasinaService.java:275` — `modTransmisie == "Manuala"` în `numarMasiniManuale`;
- `MasinaService.java:493` — `modTransmisie == "Automata"` în `afiseazaMasiniAutomate`.

În `numarMasiniAutomate` (linia 262) ai deja varianta corectă cu `.equals()` și chiar
regula scrisă în comentariu — aplic-o și în celelalte 3 locuri.

**Atenție la comentariul din `ContBancar.java:150`:** *„la string, sistemul va compara
doar prima litera"* — e **fals**. `==` la String compară **referințele** (dacă e fix
același obiect în memorie), nu prima literă și nu conținutul. Aici „merge din
întâmplare" doar pentru că literalii identici sunt internați în *string pool*.
Corectează comentariul, altfel înveți o regulă greșită din propriile notițe.

## M2. `getSold(String titular)` — parametrul nu e comparat cu nimic

`ContBancar.java:68-81`

```java
if(!titular.isEmpty()  && !"".equals(titular)){
```

Trei probleme: (1) cele două condiții verifică **exact același lucru** — a doua e
redundantă; (2) parametrul `titular` nu e comparat niciodată cu `this.titular`, deci
orice string nevid „primește" soldul — verificarea nu verifică nimic real; (3) dacă
primești `null`, `titular.isEmpty()` aruncă NPE (aceeași lecție ca B2).

Un getter curat nu are nevoie de parametru: `public double getSold(){ return sold; }`.
Dacă vrei verificare de identitate, aia e altă metodă, cu alt nume, care compară
`titular.equals(this.titular)`.

## M3. `nrInstante` se incrementează doar în constructorul fără parametri

`MasinaEnc.java:16-33`

`nrInstante++` există doar în `MasinaEnc()`. Obiectele create cu constructorul cu 6
parametri **nu sunt numărate** — contorul minte. Regula: dacă un câmp `static` numără
instanțele, **fiecare** constructor trebuie să-l incrementeze. Bonus: `nrInstante` e
package-private; ca să respecți încapsularea, fă-l `private` + un getter
`public static int getNrInstante()`.

## M4. `Persoana.descriere()` e tot `private` și clasa nu are niciun getter

`Persoana.java:92` — rămasă din runda 1. O clasă în care totul intră (settere) dar
nimic nu iese (fără gettere, `descriere()` privată) e o cutie sigilată: nu poți nici
măcar testa că setterele au funcționat. Fă `descriere()` publică și adaugă getterele.

## M5. `mediePreturiMarca` — împărțire la zero când marca nu există

`MasinaService.java:589`

```java
double mediePreturi = (double) totalPret / (double) ct;   // ct == 0 → NaN
```

Pentru o marcă inexistentă, `ct` rămâne 0, iar `0.0 / 0.0` în `double` dă `NaN` (nu
crapă, ceea ce e și mai viclean). Verifică `ct == 0` înainte de împărțire și tratează
cazul explicit, ca în `numarMasiniMarca`.

## M6. `Comanda` — clientul poate rămâne `null`, iar `null` ca argument crapă

`Comanda.java:14-19`

`if(!client.equals(""))`: dacă primești `null` → NPE; dacă primești `""` → obiectul se
creează dar `client` rămâne `null` și `descriere()` va afișa „null". Aceeași ordine de
verificare ca la B2: întâi `null`, apoi conținut.

## M7. `MasinaService.masini` e `public` și neinițializat la declarare

`MasinaService.java:12` — lista e `public` (oricine o poate înlocui sau goli din afară)
și e creată abia în `loadMasini()`. Dacă cineva apelează `numarMasini()` înainte de
`loadMasini()` → NPE. Fă câmpul `private` și inițializează-l la declarare, ca în
`Comanda.java:12` unde ai făcut-o deja corect.

\newpage

# 🟢 Cleanups

- **C1. Importuri nefolosite** — `Main.java:4-6` (`ArrayList`, `Arrays`, `List`),
  `Catalog.java:2` (`StringReader`), `Persoana.java:3` (`ArrayList`).
- **C2. `Catalog.contine`** (`Catalog.java:41-43`) — `if(cond) return true; else return false;`
  se scrie direct `return produse.contains(produs);`.
- **C3. Raw types** — `new ArrayList(produse)` la `Catalog.java:63` și
  `ContBancar.java:138`; corect: `new ArrayList<>(produse)` (altfel pierzi verificarea
  de tip la compilare).
- **C4. Mesaje de validare care mint** — `MasinaEnc.java:76`: condiția acceptă `>= 0`
  dar mesajul zice „nu poate fi 0 sau mai mic"; `MasinaEnc.java:91`: mesajul cere
  „manual sau automat" dar codul acceptă doar „Manuala"/„Automata" exact; anul maxim
  `2025` hardcodat (`MasinaEnc.java:80`) — suntem deja în 2026.
- **C5. Marcă+model fără spațiu** — `MasinaService.java:413, 429, 446` → afișează
  „BMWX3". Rămas din runda 1.
- **C6. Settere care printează** — `MasinaEnc.java:69` (`setPret` afișează prețul),
  `MasinaEnc.java:82` (`setAnFabricatie` afișează anul). Un setter setează; afișarea e
  treaba apelantului. Tot aici: constructorul fără parametri (`MasinaEnc.java:18`)
  apelează `this.descriere()` și aruncă rezultatul — apel inutil.
- **C7. `Comanda.descriere()`** (`Comanda.java:30-39`) — printează, apelează `total()`
  (care printează și el) și returnează `""`. Alege un contract: ori construiește și
  returnează textul (ca `MasinaEnc.descriere()`), ori e `void afiseaza()`.
- **C8. Naming și side-effects** — `Main.java:127`: metoda `Nivel2()` cu PascalCase
  (convenția Java: `nivel2()`); `MasinaService.java:621`:
  `afiseazaMasiniOrdonateDupaPret` **sortează lista reală** — o metodă „afișează" nu ar
  trebui să modifice datele.
- **C9. Blacklist case-sensitive** — `Persoana.java:55-56`: „ana" e blocat dar „Ana"
  trece; inconsistent cu `setOras` care normalizează cu `toLowerCase()`. Rămas din
  runda 1.

\newpage

# Rezumat pentru Adrian

Progres real față de runda 1 — 6 din 8 constatări rezolvate. Rundă nouă, în ordine:

1. **B1** — `adaugaLinie` cu `3` hardcodat: totalul comenzii iese greșit în tăcere.
2. **B2** — verificarea de `null` după `.equals()`: NPE garantat pe `adauga(null)`.
3. **B3** — `istoric` static: toate conturile împart același istoric.
4. **B4** — `aplicaReducere`: împărțirea întreagă face reducerea un no-op.

Tema comună a rundei: **B2, M2, M6 sunt același mecanism** (ordinea verificării de
`null` + short-circuit la `||`/`&&`), iar **B4 e a doua întâlnire cu împărțirea
întreagă**. Dacă stăpânești aceste două mecanisme, 5 constatări cad deodată.

# Q&A — verifică-ți înțelegerea

1. În `Catalog.adauga`, de ce `produs.equals("") || produs == null` aruncă NPE pentru
   `null`, dar `produs == null || produs.isEmpty()` nu? Ce face exact operatorul `||`
   cu partea dreaptă când stânga e `true`?

2. Creezi `ContBancar popescu` și `ContBancar ionescu`, depui 100 în primul și 500 în
   al doilea, apoi apelezi `popescu.extras()`. Ce se afișează cu `istoric` static și ce
   s-ar afișa fără `static`? Unde „locuiește" un câmp static în memorie?

3. Cât valorează în Java expresia `10 / 100 * 500`? Dar `500 * 10 / 100`? De ce diferă,
   dacă operatorii au aceeași prioritate? (indiciu: ordinea evaluării + tipul
   intermediar al fiecărui pas)
