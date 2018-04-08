<img src="/tdt4140-gr1837/app.ui/Images/Div/icon.png" width="50" height="50" />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
[![pipeline status](https://gitlab.stud.iie.ntnu.no/tdt4140-2018/37/badges/master/pipeline.svg)](https://gitlab.stud.iie.ntnu.no/tdt4140-2018/37/commits/master)
[![coverage report](https://gitlab.stud.iie.ntnu.no/tdt4140-2018/37/badges/master/coverage.svg)](https://gitlab.stud.iie.ntnu.no/tdt4140-2018/37/commits/master)

## Treningsdagbok

### Om applikasjonen
Her finner du prosjektmappe med kildekode, ressurser og diverse tilhørende treningsdagbok-applikasjonen. 

Denne applikasjonen lar brukere (datagivere) loggføre treningsøkter slik at brukerens personlige trener (tjenesteyter) 
kan komme med forslag til forbedringer av treningen o.l.
Tjenesteyteren vil få opp en grafisk representasjon av dataene brukeren har logget.

Applikasjonen vil først implementere støtte for styrketrening, for så å bli utvidet med støtte for kondisjonstrening.

### Rammeverk
- Applikasjonen er skrevet i Java, med bruk av Eclipse IDE
- Applikasjonen er basert på testdrevet utvikling (TDD)
- Applikasjonen er en tre-lags klientapplikasjon
- Applikasjonen benytter MVC-arkitekturen

### Standarder og konvensjoner
- Alle variabelnavn, metodenavn, osv. følger lower camel case notasjon
- Alle variabelnavn, metodenavn, osv. skal være gjennomtenkt og relatere til funksjonen de har
- Alle variabelnavn, metodenavn, osv. skal skrives på engelsk
- Alt som ikke er variabelnavn, metodenavn, osv. skal skrives på norsk (f.eks. commit-meldinger, kommentarer i kode, o.l.) 
- Koden skal være godt dokumentert gjennom kommentarer
- Hver commit-melding skal begynne med [#n], der n er issuet commiten relaterer til, 
    for så å inneholde en presis melding om hva som commites
- Koden følger i stor grad konvensjonene gitt av oracle (se: http://www.oracle.com/technetwork/java/codeconventions-150003.pdf)
    f.eks. brukes K&R-stilen

## For å kjøre programmet selv må du
- Ha JDK version 8 installert på din maskin. <br />

Du kan så *enten* bruke en IDE som støtter Java (f.eks. Eclipse eller IntelliJ): 
1. Last ned prosjektet og åpne det i IDE'en. 
2. Finn "ManagerLauncher.java"-filen inne i tdt4140-gr1837/app.ui/src/main/java/tdt4140/gr1837/app/ui og kjør dette scriptet som java-applikasjon

*eller* kjøre programmet fra terminalen: 
1. Last ned prosjektet på maskinen din 
2. Sørg for å ha nyeste versjon av Maven innstallert på maskinen din
3. Naviger til tdt4140-gr1837/app.ui/src/main/java/tdt4140/gr1837/app/ui i kommando-vinduet ditt, med kommandoen ```cd```
4. Kompilere "ManagerLauncher.java"-filen som .class-fil med kommandoen  ```javac ManagerLauncher.java```
5. Kjør "ManagerLauncher.class"-filen med kommandoen ```java ManagerLauncher.class```

## Eksterne ressurser
- [TestFX - rammeverk for testing av javaFX](https://github.com/TestFX/TestFX)
- [Jacoco - rapportering av code coverage](https://github.com/jacoco/jacoco)
- [Apache HttpComponents - verktøy for HTTP-protokoll](https://hc.apache.org/)
- [ControlsFX - bibliotek med javaFX-komponenter](https://github.com/schmir/controlsfx)
- [JFoenix - bibliotek med javaFX-komponenter](https://github.com/jfoenixadmin/JFoenix)

## Forfattere
- Lise Hove
- Didrik Sten
- Sindre Aubert
- Kevin Kristiansen
- Anna Hermansen
- Lars Martin Kleppe
- Magnus Kristiansen

## Database 
> https://mysqladmin.stud.ntnu.no/index.php?db=&token=38a7fdfd5b38012b12cf4bfc29e510cb&old_usr=didris_test


