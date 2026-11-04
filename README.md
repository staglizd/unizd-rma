# Putopis – vježbe iz Razvoja mobilnih aplikacija

Referentna aplikacija s laboratorijskih vježbi kolegija **Razvoj mobilnih aplikacija** (Sveučilište u Zadru, akademska godina 2026/27).
Cijeli semestar gradimo **jednu aplikaciju** (Putopis) korak po korak: svaka vježba je **jedan commit** s oznakom (**tag**) `lab-NN-end`.

| Tag | Vježba | Što je dodano |
|---|---|---|
| `lab-02-end` | V2 | Compose osnove: kartica, lista gradova (`LazyColumn`), pretraga, state hoisting |
| `lab-03-end` | V3 | slojevi (domain/data/presentation), `ViewModel`, `UiState`, Navigation 3 |
| `lab-04-end` | V4 | REST (Retrofit), JSON, stanja učitavanja i greške |
| `lab-05-end` | V5 | Hilt, use caseovi, test, APK → kraj Projekta 1 |
| `lab-06-end` | V6 | baza (Room), donja navigacija |
| `lab-07-end` | V7 | dodaj / uredi / obriši, forme, datum, validacija |
| `lab-08-end` | V8 | kamera, slike, pretraga i filtar → kraj Projekta 2 |
| `lab-09-end` | V9 | Firebase (Auth + Firestore), dodatno |
| `lab-10-end` | V10 | potpis, AAB, objava na Google Playu |

> Tagovi se dodaju **nakon** svake vježbe. Trenutno dostupni tagovi vide se pod *Releases / Tags* na GitHubu.

## Što je potrebno

- **Android Studio** (zadnja stabilna verzija): <https://developer.android.com/studio>
- **Android SDK Platform 37** (*Settings ▸ Languages & Frameworks ▸ Android SDK ▸ SDK Platforms*). Bez nje build javlja grešku da biblioteke traže `compileSdk` 37.
- Emulator (*Device Manager*) ili stvarni uređaj s uključenim USB debuggingom.
- Internet kod prvog otvaranja (Gradle preuzima biblioteke; prvi *sync* može trajati nekoliko minuta).

## Kako pokrenuti

```bash
git clone https://github.com/<korisnik>/<repo>.git
cd <repo>
git checkout lab-02-end        # stanje nakon vježbe 2 (ili bilo koji drugi tag)
```
Zatim u Android Studiju: **File ▸ Open** ▸ odaberi mapu ▸ pričekaj *Gradle sync* ▸ **Run ▶**.
Iz terminala: `./gradlew assembleDebug` (APK završi u `app/build/outputs/apk/debug/`).

## Zaostao si ili ti ne radi build?

1. Na GitHubu otvori **Tags**, odaberi `lab-NN-end` ▸ **Code ▸ Download ZIP** ▸ raspakiraj ▸ otvori u Android Studiju;
   ili u terminalu `git checkout lab-NN-end` (pa `git switch -c moj-rad` da možeš nastaviti raditi).
2. **Promijeni paket u svoj** (svaka aplikacija mora imati jedinstven `applicationId`; ovaj repo koristi `org.unizd.rma.putopis`):
   ```bash
   tools/rename-package.sh prezime     # prezime malim slovima, bez dijakritika (š→s, č→c, ž→z …)
   ```
   Skripta čita trenutni paket iz `namespace` u `app/build.gradle.kts`, premješta mape i mijenja tekst u datotekama. Zatim u Android Studiju **Build ▸ Clean Project**.
   Na Windowsu pokreni skriptu u *Git Bashu* ili preimenuj paket ručno (*Refactor ▸ Rename*).
3. **Fork** (ako želiš vlastitu kopiju na GitHubu): *Fork* ▸ `git clone` svog forka ▸ `git remote add upstream <adresa ovog repoa>` ▸ za novu vježbu `git fetch upstream --tags` ▸ `git checkout lab-NN-end`.

## Struktura koda

```
app/src/main/java/org/unizd/rma/putopis/
├─ MainActivity.kt          ulazna točka (tema + sadržaj)
├─ ui/                      Compose ekrani i komponente (od V3 se dijeli na slojeve)
│  └─ theme/                boje, tipografija, tema (Material 3)
└─ …                        od V3: domain/ (modeli, sučelja), data/ (izvori podataka), presentation/ (ekrani, ViewModel, navigacija)
```
Pravilo ovisnosti u arhitekturi: `presentation → domain ← data` (domain ne zna za Android ni za biblioteke).

## Pravila rada s Gitom (na vježbama)

- **Jedan commit po vježbi**, poruka `VNN: što smo radili` (npr. `V02: lista gradova s pretragom`).
- Svaka vježba završava **tagom** `lab-NN-end`.
- **Nikad ne commitaj:** `local.properties`, ključeve za potpis (`*.jks`, `keystore.properties`), API ključeve ni lozinke. Repozitorij je javan.
- Verzije biblioteka su na jednom mjestu: `gradle/libs.versions.toml`.

## Tehnologije

Kotlin · Jetpack Compose (Material 3) · ViewModel + StateFlow · Navigation 3 · Coroutines/Flow · Retrofit + kotlinx.serialization · Hilt · Room · CameraX/Activity Result API · Firebase · Gradle (Kotlin DSL, version catalog).
*(Tehnologije se uvode postupno, po vježbama; vidi tablicu gore.)*

## Napomena

Repozitorij služi kao **podrška nastavi**. Svaki student predaje **vlastiti projekt** (svoj repozitorij, svoj paket `org.unizd.rma.<prezime>`).
