# Code Review — adrian-incapsularea

**Scop:** review pe relații OOP — `manyToMany`, `compozitieVSagregare`, `oneToMany` bidirectional.

> Document pe **runde**. Runda 2 (corecturi `7789d19`) e sus; runda 1 (istoric) e jos.

---

# Runda 2 — corecturi (`1bd3893..7789d19`)

Bine făcut: B1, B2 și B3 din runda 1 sunt **rezolvate corect**, cu comentarii care explică *de ce* — exact ce voiam să înțelegi, nu doar să repari. Dar corectura a introdus una nouă și a rezolvat B4 doar pe jumătate.

## 🔴 Critice

### B1 (nou) — `getArrLinii()` returnează acum lista internă direct → încapsulare spartă
`src/app/relations/compozitieVSagregare/Comanda.java:26`

```java
public List<LinieComanda> getArrLinii(){
    return arrLinii;          // înainte: return new ArrayList<>(arrLinii);
}
```

Ai scos copia defensivă ca să eviți copierea la fiecare iterație (M1 din runda 1) — corect ca intenție, dar ai overcorectat: acum oricine din afară primește **referința la lista reală** și poate face `comanda.getArrLinii().clear()` peste starea comenzii. Fix corect: iterezi `arrLinii` **direct în interiorul clasei** (ce ai și făcut în `total()`), dar getter-ul public rămâne cu copie defensivă. Cele două nu se bat cap în cap.

## 🟡 Importante

### M1 — contor `static` fără câmp de instanță care să CAPTEZE valoarea
`src/app/relations/compozitieVSagregare/Comanda.java:7,15` · `.../LinieComanda.java:5,12`

```java
private static int nrComanda = 1;
public Comanda(){ nrComanda++; }
```

Ai prins jumătatea bună: contorul trebuie `static` (partajat între toate instanțele). Dar acum `nrComanda` e **doar** contorul — nu există un câmp de instanță care să rețină numărul *acestei* comenzi. Toate obiectele citesc aceeași valoare curentă a contorului, deci nu obții ID-uri unice per obiect. Îți trebuie amândouă: un `static` care numără + un câmp de instanță care fotografiază valoarea la construcție.

### M2 — `subtotal` calculat o singură dată, la construcție → se învechește
`src/app/relations/compozitieVSagregare/LinieComanda.java:16,19,27`

`setSubtotal()` e chemat doar în constructor. Dacă apelezi `setCantitate(5)` după creare, `subtotal` rămâne cel vechi și `getSubtotal()` întoarce o valoare stală. Câmp derivat care nu se recalculează când se schimbă sursele lui. Fix: ori recalculezi în `setCantitate`/`setProdus`, ori calculezi on-the-fly în getter (`return produs.getPret() * cantitate;`) și scapi complet de câmpul `subtotal`.

### M3 — `total()` e `void` + `getTotal()` citește câmpul → dependență de ordine
`src/app/relations/compozitieVSagregare/Comanda.java:29,39`

`getTotal()` întoarce corect doar *după* ce ai chemat `total()`. În `Main` se vede: primul `getTotal()` printează „LEI 0.0" pentru că `total()` n-a rulat încă. Mai curat și fără capcane: `total()` să **returneze** valoarea, iar `getTotal()` s-o formateze apelând `total()` — un singur punct de adevăr, imposibil să-l citești „gol".

## ✅ Rezolvate în runda 2

| # runda 1 | Ce era | Cum ai reparat |
|---|---|---|
| B1 | `total()` acumula în câmp (dubla la reapel) | `total = 0;` la începutul metodei ✅ |
| B2 | `getSubtotal()` muta starea cu `+=` | split în `setSubtotal()` (assignment) + `getSubtotal()` pur ✅ |
| B3 | `oldTeam.getJucatori().remove(x)` pe copie | `oldTeam.jucatori.remove(x)` pe lista reală, cu comentariu corect despre copia shallow ✅ |
| B4 | contor pe câmp de instanță `++` → mereu 2 | trecut pe `static` → **parțial**, vezi M1 |

---

## Before / After runda 2 (în document, NU aplicat în cod)

| # | Acum | Corect |
|---|------|--------|
| B1 | `public List<LinieComanda> getArrLinii(){`<br>`  return arrLinii; }` | `public List<LinieComanda> getArrLinii(){`<br>`  return new ArrayList<>(arrLinii); }`<br>*(în `total()` iterezi `arrLinii` direct — ai deja)* |
| M1 | `private static int nrComanda = 1;`<br>`public Comanda(){ nrComanda++; }` | `private static int counter = 0;`<br>`private final int nrComanda = ++counter;` |
| M2 | `setSubtotal()` doar în constructor | `public double getSubtotal(){`<br>`  return produs.getPret() * cantitate; }`<br>*(fără câmp `subtotal`)* |
| M3 | `public void total(){ ... }`<br>`getTotal()` citește `this.total` | `public double total(){ ... return total; }`<br>`getTotal(){ return "... LEI " + total(); }` |

## Q&A runda 2

1. **B1:** Ai scos copia defensivă din getter ca să nu copiezi la fiecare iterație. De ce puteai să rezolvi viteza *fără* să sacrifici încapsularea? (Indiciu: cine iterează lista — codul din interiorul clasei sau cel din afară?)
2. **M1:** Dacă faci `new Comanda()` de 3 ori și apoi citești `nrComanda` la fiecare, ce valoare obții pe fiecare? De ce nu-ți dă „1, 2, 3"? Ce câmp lipsește?
3. **M3:** De ce e riscant ca `getTotal()` să depindă de faptul că ai chemat `total()` înainte? Ce se întâmplă dacă altcineva folosește clasa ta și uită pasul ăsta?

---
---

# Runda 1 — livrare inițială (`aa00fbd..1bd3893`)

*(istoric — toate rezolvate sau reclasate în runda 2)*

## 🔴 Critice
- **B1** `Comanda.total()` acumula în câmpul `total` (nu resetat) → dubla la al doilea apel. → rezolvat.
- **B2** `LinieComanda.getSubtotal()` getter care muta starea (`+=`). → rezolvat.
- **B3** `Echipa.transferEchipa()` scotea jucătorul dintr-o copie defensivă → vechea echipă îl păstra. → rezolvat.
- **B4** `nrComanda`/`numarLinie` câmp de instanță cu `++` → mereu 2; trebuie `static`. → parțial (M1 runda 2).

## 🟡 Importante
- **M1** `total()` copia lista la fiecare iterație. → rezolvat (iterează `arrLinii` direct).
- **M2** `level4_Comanda()` moartă/incompletă. → completată (adaugă linii + print).

## 🟢 Cleanups
- **C1** cod comentat în `Main.java` (imports + `creareCatalog`/`Nivel2`).
- **C2** `System.out.println` în `Player.setEchipa()`.
- **Notă:** `j5.setEchipa(...)` e demonstrația didactică marcată de Adrian, nu bug.

## Ce a mers bine (constant pe ambele runde)
- Copii defensive pe getterele de listă (atenție la regresia B1 runda 2 pe `getArrLinii`).
- Sincronizare bidirecțională corectă în `Curs.inscrieStudent`/`removeStudent`.
- `adaugaCurs`/`removeCurs` package-private — încapsularea relației prinsă corect.
