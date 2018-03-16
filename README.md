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

## Applikasjonen
Her finner du prosjektmappa med kildekode, ressurser og diverse tilhørende treningsdagbok-applikasjonen. 

Denne applikasjonen lar brukere (datagivere) loggføre treningsøkter slik at brukerens personlige trener (tjenesteyter) 
kan komme med forslag til forbedringer av treningen o.l.
Tjenesteyteren vil få opp en grafisk representasjon av dataene brukeren har logget.

Applikasjonen vil først implementere støtte for styrketrening for så å kunne utvides med støtte for kondisjonstrening, samt et brukergrensesnitt for datagivere. 
Dersom det er innen for vår tidsramme vil vi utvidde med "manager"-funksjonalitet som viser data relatert til treningssenteret (f.eks. pågang iløpet av en dag).

### Rammeverk
- Applikasjonen er skrevet i Java, med bruk av Eclipse IDE
- Applikasjonen er basert på testdrevet utvikling (TDD)
- Applikasjonen er en tre-lags klientapplikasjon
- Applikasjonen benytter MVC-arkitekturen

### Standarer og konvensjoner
- Alle variabelnavn, metodenavn, osv. følger lower camel case notasjon
- Alle variabelnavn, metodenavn, osv. skal være gjennomtenkt og relatere til funksjonen de har
- Alle variabelnavn, metodenavn, osv. skal skrives på engelsk
- Alt som ikke er variabelnavn, metodenavn, osv. skal skrives på norsk (f.eks. commit-meldinger, kommentarer i kode, o.l.) 
- Koden skal være godt dokumentert gjennom kommentarer
- Hver commit-melding skal begynne med #n, der n er issuet commiten relaterer til, 
    for så å inneholde en presis melding om hva som commites
- Koden følger i stor grad konvensjonene gitt av oracle (se: http://www.oracle.com/technetwork/java/codeconventions-150003.pdf)
    f.eks. brukes K&R-stilen

#### Database 
> https://mysqladmin.stud.ntnu.no/index.php?db=&token=38a7fdfd5b38012b12cf4bfc29e510cb&old_usr=didris_test


