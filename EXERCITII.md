# Exerciții — procesarea mașinilor în `MasinaService`

Toate metodele se implementează în clasa `MasinaService` (peste lista `masini`) și se apelează din `Main`, după `service.loadMasini()`.

Regula jocului: **citești cerința, scrii metoda singur, verifici rezultatul** cu valoarea așteptată de mai jos. Datele sunt fixe (cele 8 mașini din `loadMasini()`), deci rezultatele sunt fixe.

## Datele de lucru

| Var | Marca | Model | An | Km | Pret | Transmisie |
|-----|-------|-------|------|--------|-------|-----------|
| a | Audi | A4 | 1993 | 12 | 18000 | Manuala |
| b | BMW | X5 | 2010 | 13 | 15000 | Automata |
| c | Toyota | Corolla | 2019 | 45000 | 16000 | Manuala |
| d | Mercedes | C-Class | 2015 | 54000 | 20000 | Automata |
| e | Volkswagen | Golf | 2012 | 120000 | 9000 | Manuala |
| f | Ford | Focus | 2018 | 75000 | 14000 | Manuala |
| g | BMW | X3 | 2020 | 30000 | 28000 | Automata |
| h | Audi | A6 | 2014 | 98000 | 17000 | Automata |

---

## Nivel 1 — Parcurgere și afișare

### 1. Afișează doar mărcile
```java
void afiseazaMarci()
```
Afișează marca fiecărei mașini, câte una pe linie.

Rezultat: `Audi`, `BMW`, `Toyota`, `Mercedes`, `Volkswagen`, `Ford`, `BMW`, `Audi`

### 2. Afișează marca + model
```java
void afiseazaMarcaModel()
```
Afișează pe fiecare linie marca și modelul: `Audi A4`, `BMW X5`, ...

### 3. Afișează toate prețurile
```java
void afiseazaPreturi()
```
Afișează prețul fiecărei mașini, câte unul pe linie.

### 4. Câte mașini sunt în listă
```java
int numarMasini()
```
Returnează numărul de mașini.

Rezultat: `8`

---

## Nivel 2 — Numărare cu condiție

### 5. Câte mașini sunt automate
```java
int numarMasiniAutomata()
```
Rezultat: `4`

### 6. Câte mașini sunt manuale
```java
int numarMasiniManuala()
```
Rezultat: `4`

### 7. Câte mașini sunt de o anumită marcă
```java
int numarMasiniMarca(String marca)
```
Rezultat: pentru `"BMW"` → `2`, pentru `"Audi"` → `2`, pentru `"Ford"` → `1`

### 8. Câte mașini sunt mai scumpe decât un prag
```java
int numarMasiniPestePret(int prag)
```
Rezultat: pentru `16000` → `4`

### 9. Câte mașini au sub un anumit kilometraj
```java
int numarMasiniSubKm(int prag)
```
Rezultat: pentru `50000` → `4`

---

## Nivel 3 — Sume și medii

### 10. Suma tuturor prețurilor
```java
int sumaPreturi()
```
Rezultat: `137000`

### 11. Prețul mediu
```java
double mediePreturi()
```
Rezultat: `17125.0`

### 12. Total kilometraj
```java
int sumaKilometraj()
```
Rezultat: `422025`

### 13. Anul mediu de fabricație
```java
double medieAnFabricatie()
```
Rezultat: `2012.625`

### 14. Suma prețurilor pentru o marcă
```java
int sumaPreturiMarca(String marca)
```
Rezultat: pentru `"BMW"` → `43000`, pentru `"Audi"` → `35000`

---

## Nivel 4 — Minim și maxim

### 15. Cea mai scumpă mașină
```java
void afiseazaMasinaCeaMaiScumpa()
```
Afișează descrierea mașinii cu prețul maxim.

Rezultat: `BMW X3` (28000)

### 16. Cea mai ieftină mașină
```java
void afiseazaMasinaCeaMaiIeftina()
```
Rezultat: `Volkswagen Golf` (9000)

### 17. Cea mai nouă mașină
```java
void afiseazaMasinaCeaMaiNoua()
```
Mașina cu anul de fabricație cel mai mare.

Rezultat: `BMW X3` (2020)

### 18. Cea mai veche mașină
```java
void afiseazaMasinaCeaMaiVeche()
```
Rezultat: `Audi A4` (1993)

### 19. Mașina cu cei mai mulți kilometri
```java
void afiseazaMasinaCuCeiMaiMultiKm()
```
Rezultat: `Volkswagen Golf` (120000)

### 20. Prețul maxim (doar valoarea)
```java
int pretMaxim()
```
Rezultat: `28000`

---

## Nivel 5 — Căutare și filtrare

### 21. Afișează mașinile unei mărci
```java
void afiseazaMasiniMarca(String marca)
```
Afișează descrierea fiecărei mașini de marca dată.

Rezultat: pentru `"BMW"` → `BMW X5` și `BMW X3`

### 22. Afișează doar mașinile automate
```java
void afiseazaMasiniAutomata()
```
Rezultat: `BMW X5`, `Mercedes C-Class`, `BMW X3`, `Audi A6`

### 23. Afișează mașinile dintr-un interval de preț
```java
void afiseazaMasiniIntrePreturi(int min, int max)
```
Afișează mașinile cu prețul între `min` și `max` (inclusiv).

Rezultat: pentru `14000` și `18000` → `Audi A4`, `Toyota Corolla`, `Ford Focus`, `Audi A6`

### 24. Caută prima mașină de o marcă
```java
Masina cautaPrimaMasinaMarca(String marca)
```
Returnează prima mașină găsită de marca dată (sau `null` dacă nu există).

Rezultat: pentru `"Audi"` → mașina `Audi A4`

### 25. Există o mașină de o marcă
```java
boolean existaMasinaMarca(String marca)
```
Rezultat: pentru `"Ford"` → `true`, pentru `"Dacia"` → `false`

---

## Nivel 6 — Provocări

### 26. Mașini noi ȘI ieftine
```java
int numarMasiniNoiSiIeftine(int anMin, int pretMax)
```
Numără mașinile fabricate în `anMin` sau mai târziu ȘI cu prețul sub `pretMax`.

Rezultat: pentru `2018` și `18000` → `2`

### 27. Prețul mediu pentru o marcă
```java
double mediePreturiMarca(String marca)
```
Rezultat: pentru `"Audi"` → `17500.0`, pentru `"BMW"` → `21500.0`

### 28. Crește kilometrajul tuturor mașinilor
```java
void cresteKilometrajul(int km)
```
Adaugă `km` la kilometrajul fiecărei mașini din listă. Apoi apelează `afisareMasini()` ca să verifici.

### 29. Aplică o reducere tuturor mașinilor
```java
void aplicaReducere(int procent)
```
Scade `procent` la sută din prețul fiecărei mașini. Apoi apelează `afisareMasini()` ca să verifici.

Rezultat: pentru `10` → Audi A4 ajunge `16200`, Volkswagen Golf ajunge `8100`

### 30. Afișează mașinile ordonate după preț
```java
void afiseazaMasiniOrdonateDupaPret()
```
Afișează mașinile de la cea mai ieftină la cea mai scumpă.

Rezultat: `Volkswagen Golf` (9000) ... până la `BMW X3` (28000)
