# Project Beschrijving
Na de brainstormsessie zijn wij met een concept gekomen om bezienswaardigheden in een stad op een leuke manier te ontdekken. Via een web interface kan je in het centrum van een stad naar keuze een spel opzetten. Ook kun je een van onze voor ingestelde spellen spelen van bekende steden. Het spel wordt gespeeld in groepen, je hebt minimaal 2 teams nodig om het spel te kunnen spelen.

Het idee is gebaseerd op ‘capture the flag’ wat je veel in video games ziet. Capture the flag houdt in dat je op een vastgesteld punt een bepaalde tijd moet staan om het te veroveren. Wij gaan dit doen met bezienswaardigheden binnen een bepaald gebied. Ga je met je groepje bij een bezienswaardigheid staan, dan wordt deze zogezegd van jou. wanneer je een punt veroverd hebt krijg je vragen en raadsels over het monument. Beantwoord je deze vragen goed dan wordt het moeilijker voor een ander team om het punt te veroveren. Dit is niet alleen om het spel wat moeilijker te maken, maar ook om wat bij te leren. Zo ligt de focus niet alleen op het veroveren van punten, maar ook op het bestuderen van de monumenten.

Via augmented reality kan je op willekeurige plekken op het monument hints/informatie vinden. Deze informatie kan je helpen met het oplossen van vragen of raadsels. Ook voor het vinden van de hint scoor je wat extra punten.
Op het einde van het spel wordt er gekeken naar wie de meeste monumenten bezit om zo de winnaar te bepalen. Als er een gelijkspel is wordt er gekeken naar welk team de meeste vragen in heeft opgelost.

Dus in het kort:
* Ga met je team bij een monument staan om deze te veroveren.
* Beantwoord vragen en los raadsels op om de capture sterker te maken.
* Verover zoveel mogelijk monumenten met je team.
* Gebruik augemented reality om hints te vinden en extra punten te scoren.

## Schema Architectuur

<br />
<img src="images/schema_architectuur_globaal.png" />
Communicatie systeem<br /><br />


<img src="images/schema_architectuur.png" />
Technologie en Software architectuur<br /><br />


## Web interface
### Login
Onderstaande afbeelding laat het scherm zien dat je ziet als je bent ingelogd. Links zie je een lijst met je gemaakte games die je kan aanpassen als je er op klikt, onderaan kan je een nieuw spel maken. Rechts zie je data van je profiel.
<img src="images/New Mockup 7.png" /> <br /> 
### Spelinstellingen
Op onderstaande afbeelding zie je hoe je het spel kan installen. Onderaan zie algemene instellingen van het spel, daarboven zie je een kaart met de ingestelde plaatsen erop. Als je op een plaats op de kaart klikt kan je een locatie toevoegen.
<img src="images/New Mockup 1.png" /> <br /> 
### Locatie-instellingen
Bij een locatie kan je je eigen vragen, hints en puzzels toevoegen. Lins zie je knoppen om dingen toe te voegen, in het midden en rechts zie je lijsten waar alle vragen en hints in staan.
<img src="images/New Mockup 2.png" /> <br /> 
### AR hints
AR hints zijn hints die je kan vinden voor de vragen op de locatie door gebruik te maken van AR. Links kan je je afbeelding toevoegen, als deze is toegevoegd kan je de afbeelding zien. In het midden kan je de tekst intypen die tevoorschijn zal komen met je AR camera. Rechts zie je wat je zal zien met je AR camera.
<img src="images/New Mockup 3.png" /> <br /> 
### Vragen toevoegen
Om een vraag toe te voegen kan je optioneel een afbeelding toevoegen, een vraag toevoegen en het antwoord toevoegen.
<img src="images/New Mockup 4.png" /> <br /> 
### puzzels toevoegen
Over de puzzels moet nog nagedacht worden, op onderstaande afbeeldingen zie je al hoe een kruiswoordraadsel en sudoku kan worden toegevoegd.
<img src="images/New Mockup 5.png" />
<img src="images/New Mockup 6.png" />


### Login Scherm:
<div style="text-align: right">
<img src="images/login scherm.png" width="100" style="float: left" />
<p>Het scherm dat men ziet wanneer de game is opgestart, men kan een naam opgeven en ofwel in een bestaand spel meespelen ofwel zelf een nieuw spel starten. Men kan kiezen in welk team men wilt en ook het aantal teams aanpassen wanneer men een nieuw spel starten.</p>
</div>

### Nieuw Spel Starten:
<div style="text-align: right">
<img src="images/opzetten nieuwe game.png" width="100" />
Wanneer men een nieuw spel aan het opzetten is zal men een lobby id zijn toegewezen waarmee anderen kunnen joinen.
Men kan kiezen welk puzzel te starten en ook welke locaties er niet meer toegankelijk zijn.
</div>

### De Map:
<div style="text-align: right">
<img src="images/main game -map.png" width="100" />
Men kan hier de huidige locatie zien, de locaties die gecaptured zijn, locaties die nog gecaptured moeten worden, informatie over wie de beste is en meer.
</div>

### Capturen locatie:
<div style="text-align: right">
<img src="images/details captured locatie.png" width="100" />
Je kan zien wie een locatie heeft gecaptured door erop te klikken op de map.
</div>

### Locatie capturen:
<div style="text-align: right">
<img src="images/capturen locatie.png" width="100" />
Wanneer deze locatie al gecaptured was door een ander team zal erop verschijnen door wie en hoe sterk de capture is, je kan deze capture dan afbreken door puzzels op te lossen, je kan het dan capturen en zelf sterker maken.
</div>


