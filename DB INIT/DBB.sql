-- phpMyAdmin SQL Dump
-- version 4.6.6deb4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Creato il: Ott 16, 2022 alle 16:39
-- Versione del server: 10.1.23-MariaDB-9+deb9u1
-- Versione PHP: 7.0.30-0+deb9u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `DBB`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `blog`
--

CREATE TABLE `blog` (
  `id` int(11) NOT NULL,
  `categoria` varchar(45) DEFAULT NULL,
  `nome` varchar(45) NOT NULL,
  `testo` text NOT NULL,
  `descrizione` text NOT NULL,
  `meta_descrizione` varchar(160) NOT NULL,
  `immagine` varchar(1000) NOT NULL,
  `creatore` varchar(45) NOT NULL,
  `data` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `views` int(11) NOT NULL DEFAULT '0',
  `pubblicato` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `blog`
--

INSERT INTO `blog` (`id`, `categoria`, `nome`, `testo`, `descrizione`, `meta_descrizione`, `immagine`, `creatore`, `data`, `views`, `pubblicato`) VALUES
(163, 'Il Trentino', 'I Canederli Tirolesi', '<div><div></div><div><span style=\"letter-spacing: 0px;\">I</span><strong style=\"letter-spacing: 0px;\"> canederli</strong><span style=\"letter-spacing: 0px;\"> sono uno dei più importanti piatti della tradizione gastronomica del Trentino. è tuttavia importante sottolineare che questo delizioso piatto, che oggi è tanto ricercato per le emozioni che trasmette, </span><strong style=\"letter-spacing: 0px;\">è nato per necessità</strong><span style=\"letter-spacing: 0px;\"> delle famiglie di contadini che da sempre popolano il </span><strong style=\"letter-spacing: 0px;\">Trentino</strong><span style=\"letter-spacing: 0px;\">. I canederli sono infatti un</span><strong style=\"letter-spacing: 0px;\"> piatto povero</strong><span style=\"letter-spacing: 0px;\"> che permette anche a chi ha disponibilità economiche limitate di apprezzarlo.</span><br></div><div><div>Sei curioso? Continua a leggere e scopriamo assieme il motivo del loro successo!</div></div><div><br></div><div><br></div><div><div></div><div><h2>La storia dei Canederli Tirolesi</h2></div><div></div></div><div><div></div><div><p>Come ho già detto, questo piatto nasce dalla necessità dei meno facoltosi di poter mangiare qualcosa di sostanzioso a basso prezzo, tuttavia la loro diffusione e il loro successo sono dovuti a un altro punto fondamentale che, sopratutto oggi, rappresenta un tema critico. <strong>Lo spreco alimentare</strong>.<br>Sempre più spesso si sente parlare dello spreco alimentare e delle conseguenze che provoca sull\'ambiente e sull\'economia. Tuttavia, nel passato, esso rappresentava sopratutto un problema interno alle famiglie piuttosto che all\'ecosistema in generale.</p></div><div><p> </p></div><div><p>In questo contesto familiare ricorrente del Trentino pre-globalizzazione nascono i Canederli, un\'ottima e deliziosa soluzione per combattere lo spreco alimentare utilizzando <strong>alimenti non più freschi</strong> per realizzare un pasto squisito e nutriente.</p></div><div></div></div><div><br></div><div><br></div><div><div></div><div><h2>Cosa sono i Canederli Tirolesi</h2></div><div><img src=\"https://lh3.googleusercontent.com/73JKt_UfbASuehh_0fZsCmpIpCl87viJImnTtQvFSJIqQZCapr0pRmdRouGg90jOxq9D1c-Y5Pzdhd_cDJ0ETPr4_uKJKDZf8El1AuMrdzfJVM9Xqm-S16jUazNEIn4doLqGUrNOww=w2400\" alt=\"I Canederli Tirolesi\" width=\"400\" height=\"300\"><br></div><div><br></div><div></div></div><div><div></div><div><p>Sfere poco più piccole di una palla da tennis, i canederli sono uno delle principali<strong> specialità trentine</strong>, ma con cosa sono fatti? Perché sono importanti per un <strong>consumo sostenibile</strong>? In poche parole, cosa sono i canederli?</p></div></div></div><blockquote style=\"margin: 0 0 0 40px; border: none; padding: 0px;\"><div><div><div><p>L\'impasto dei canederli è il frutto di una <strong>lunga lavorazione</strong> che inizia con il <strong>pane raffermo</strong>, il quale viene tagliato a cubetti piccolini, vengono uniti il <strong>latte fresco</strong> e le <strong>uova di montagna biologiche</strong>. Bisogna ottenere un impasto omogeneo che rappresenta la base dei canederli. A questo punto c\'è molta libertà sul guarnimento ma quelli tradizionali sono sicuramente i <strong>canederli al formaggio</strong> o i <strong>canederli allo speck</strong>. Si procede quindi unendo lo speck o il formaggio e mescolando per ottenere l\'impasto completo. Con le mani umide si possono realizzare delle sfere di <strong>circa 100 grammi</strong>. </p></div></div></div></blockquote><div><div><div><p> </p></div><div><p>Questi sono i canederli tirolesi pronti per essere cucinati <strong>in brodo</strong> o <strong>asciutti</strong>.</p></div><div></div></div><div><br></div><div><br></div><div><h2>Come Cucinarli</h2></div><div><div style=\"text-align: left;\"><img src=\"https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Knoedeltopf.jpg/796px-Knoedeltopf.jpg\" alt=\"Cucinare i canederli\" width=\"254\" height=\"193\"></div></div><div><br></div><div><div></div><div><p>I Canederli rappresentano un\'idea semplice si, ma l’esperienza la fa da padrona nella bontà che si riesce a conferire al piatto. La nostra ricetta gode di un’<strong>esperienza secolare</strong> che rende il sapore inconfondibile! La bontà parte dalle <strong>materie prime</strong>, per questo i nostri canederli sono realizzati con il pane dei panifici locali, latte Trento, uova di montagna biologiche, formaggio del caseificio di Predazzo e, ovviamente, lo <strong>speck di nostra produzione</strong>.</p></div><div><p> </p></div><div><p>La parte di cottura dei canederli è <strong>fondamentale</strong> perché, se sbagliata, l\'impasto non lo sopporterà e il canederlo si sfalderà, aprendosi e diventando impossibile da mangiare.<br>Le indicazioni che ti darò per cucinare i canederli sono <strong>su misura per i canederli di nostra produzione</strong>, infatti ogni ricetta ha dei suoi accorgimenti per renderli ancora più gustosi.</p></div><div><p> </p></div><div><p>Se vuoi conoscere come noi consigliamo di cucinare i canederli <a title=\"Canederli al Formaggio in Brodo\" href=\"https://macelleriadellantonio.it/Bortoleto/idea/1/Canederli-al-formaggio-in-brodo\">leggi l\'idea che ti proponiamo a questo link!</a></p></div><div></div></div><div></div></div><div><br></div>', 'I canederli sono uno dei più importanti piatti della tradizione gastronomica del Trentino. è tuttavia importante sottolineare che questo delizioso piatto, che oggi è tanto ricercato per le emozioni che trasmette, è nato per necessità delle famiglie di contadini che da sempre popolano il Trentino. I canederli sono infatti un piatto povero che permette anche a chi ha disponibilità economiche limitate di apprezzarlo.Sei curioso? Continua a leggere e scopriamo assieme il motivo del loro successo!La storia dei Canederli TirolesiCome ho già detto, questo piatto nasce dalla necessità dei meno facoltosi di poter mangiare qualcosa di sostanzioso a basso prezzo, tuttavia la loro diffusione e il loro successo sono dovuti a un altro punto fondamentale che, sopratutto oggi, rappresenta un tema critico. Lo spreco alimentare.Sempre più spesso si sente parlare dello spreco alimentare e delle conseguenze che provoca sull\'ambiente e sull\'economia. Tuttavia, nel passato, esso rappresentava sopratutto un problema interno alle famiglie piuttosto che all\'ecosistema in generale. In questo contesto familiare ricorrente del Trentino pre-globalizzazione nascono i Canederli, un\'ottima e deliziosa soluzione per combattere lo spreco alimentare utilizzando alimenti non più freschi per realizzare un pasto squisito e nutriente.Cosa sono i Canederli TirolesiSfere poco più piccole di una palla da tennis, i canederli sono uno delle principali specialità trentine, ma con cosa sono fatti? Perché sono importanti per un consumo sostenibile? In poche parole, cosa sono i canederli?L\'impasto dei canederli è il frutto di una lunga lavorazione che inizia con il pane raffermo, il quale viene tagliato a cubetti piccolini, vengono uniti il latte fresco e le uova di montagna biologiche. Bisogna ottenere un impasto omogeneo che rappresenta la base dei canederli. A questo punto c\'è molta libertà sul guarnimento ma quelli tradizionali sono sicuramente i canederli al formaggio o i canederli allo speck. Si procede quindi unendo lo speck o il formaggio e mescolando per ottenere l\'impasto completo. Con le mani umide si possono realizzare delle sfere di circa 100 grammi.  Questi sono i canederli tirolesi pronti per essere cucinati in brodo o asciutti.Come CucinarliI Canederli rappresentano un\'idea semplice si, ma l’esperienza la fa da padrona nella bontà che si riesce a conferire al piatto. La nostra ricetta gode di un’esperienza secolare che rende il sapore inconfondibile! La bontà parte dalle materie prime, per questo i nostri canederli sono realizzati con il pane dei panifici locali, latte Trento, uova di montagna biologiche, formaggio del caseificio di Predazzo e, ovviamente, lo speck di nostra produzione. La parte di cottura dei canederli è fondamentale perché, se sbagliata, l\'impasto non lo sopporterà e il canederlo si sfalderà, aprendosi e diventando impossibile da mangiare.Le indicazioni che ti darò per cucinare i canederli sono su misura per i canederli di nostra produzione, infatti ogni ricetta ha dei suoi accorgimenti per renderli ancora più gustosi. Se vuoi conoscere come noi consigliamo di cucinare i canederli leggi l\'idea che ti proponiamo a questo link!', 'I canederli tirolesi fanno parte delle ricette della tradizione Trentina e rappresentano uno dei piatti più gustosi e interessanti! Scopri cosa sono!', '../console/img/blog/IlTrentino/163.png', 'Roberto Dellantonio', '2020-02-14 16:37:51', 29, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `blog_cat`
--

CREATE TABLE `blog_cat` (
  `id_cat` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `blog_cat`
--

INSERT INTO `blog_cat` (`id_cat`, `nome`) VALUES
(267, 'Il Trentino');

-- --------------------------------------------------------

--
-- Struttura della tabella `blog_commenti`
--

CREATE TABLE `blog_commenti` (
  `id_commento` int(11) NOT NULL,
  `id_blog` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `commento` varchar(1000) NOT NULL,
  `data` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `blog_commenti`
--

INSERT INTO `blog_commenti` (`id_commento`, `id_blog`, `nome`, `commento`, `data`) VALUES
(5, 2, 'Roberto Dellantonio', 'Articolo moolto interessante!!\r\n', '2019-04-20 22:00:00'),
(8, 3, 'Roberto Dellantonio', 'Bello! :D\r\n', '2019-04-20 22:00:00'),
(9, 1, 'Roberto Dellantonio', 'Interessante! Complimenti', '2019-04-20 22:00:00'),
(11, 1, 'Roberto Dellantonio', 'Bello! :D', '2019-04-21 22:00:00'),
(14, 3, 'Roberto', 'asdfasdf', '2019-04-22 12:49:21'),
(16, 2, 'Roberto Dellantonio', 'Vediamo se funzionano ancora i commenti\r\n', '2019-05-18 12:56:02');

-- --------------------------------------------------------

--
-- Struttura della tabella `blog_tags`
--

CREATE TABLE `blog_tags` (
  `id` int(11) NOT NULL,
  `blog` int(11) DEFAULT NULL,
  `tag` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dump dei dati per la tabella `blog_tags`
--

INSERT INTO `blog_tags` (`id`, `blog`, `tag`) VALUES
(598, 163, 107),
(599, 163, 109),
(600, 163, 110);

-- --------------------------------------------------------

--
-- Struttura della tabella `categorie`
--

CREATE TABLE `categorie` (
  `id` int(11) NOT NULL,
  `nome` varchar(75) DEFAULT NULL,
  `immagine` varchar(1000) NOT NULL,
  `descrizione` text NOT NULL,
  `meta_descrizione` varchar(160) NOT NULL,
  `freschi` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `categorie`
--

INSERT INTO `categorie` (`id`, `nome`, `immagine`, `descrizione`, `meta_descrizione`, `freschi`) VALUES
(1, 'Affumicati', '../console/img/catProd/confezionati/1.jpg', '<p></p><p>L’affumicatura è presente nella tradizione <b>Trentina</b> sin dai tempi più remoti per prolungare la conservazione degli alimenti. Oggi noi la utilizziamo inoltre per <b>insaporire</b> e <strong>aromatizzare</strong>, grazie al legno di prima qualità, solo i prodotti migliori. I prodotti affumicati che trovi in questa categoria sono scelti con cura e sottoposti al processo di <strong>affumicatura a caldo</strong> dopo un’attenta lavorazione.</p>\n<p>Siamo in Trentino, e per questo il legname che utilizziamo per l’affumicatura è legna di <b>Faggio</b> (14% del bosco Trentino), il quale conferisce alla carne un aroma delicato e leggermente dolce. Il processo di affumicatura avviene rigorosamente nel nostro <b>forno a legna</b>, per garantire un insaporimento lento e duraturo.<br>\nProva i prodotti affumicati de<strong> ‘L Bortoleto</strong> e facci sapere se ti sono piaciuti!</p>\n<p></p>', 'Prodotti Affumicati nel forno a legna di faggio per conferire alla carne aromi delicati e leggermente dolci. I migliori prodotti affumicati da \'L Bortoleto!', 0),
(2, 'Bresaola', '../console/img/catProd/confezionati/2.jpg', '<p></p><p>La<strong> Bresaola</strong> è una famiglia di salumi tipici del nord Italia. Spesso questi salumi sono erroneamente chiamati insaccati ma è bene ricordare che <strong>la bresaola non è un insaccato</strong>. Infatti, gli insaccati sono tutti quei salumi che vengono avvolti da un budello naturale o sintetico per conferirne la forma. La Bresaola si ricava da <strong>parti molto magre</strong> dell’animale adeguatamente insaporite secondo la tradizione. Passa poi per un <strong>processo di stagionatura</strong> che può durare anche diversi mesi in base al tipo di carne e di animale.</p>\n<p>Nelle regioni del nord Italia questo salume si è ampiamente diffuso nella cultura popolare e con questo sono nate <strong>moltissime varianti</strong> e molti accostamenti diversi.<br>\nQui troverai la <strong>Bresaola del Trentino Alto Adige</strong> che produciamo direttamente noi nei nostri laboratori. Ci teniamo a ricordarti che i nostri salumi, compresa la bresaola, sono <strong>prodotti Artigianali</strong> lavorati da mastri salumieri da più di 110 anni.<br>\nLe bresaole che noi ti offriamo sono realizzate con carne di manzo allevati nei pascoli della val di Fiemme, Cavallo, e Cervo.</p>\n<p>Al contrario di quel che si può pensare, stiamo parlando di un <strong>salume estremamente salutare</strong> per chi fa attività fisica e <strong>sport</strong>. Infatti, la bresaola, non è un insaccato, ed è realizzato con parti estremamente magre dell’animale, per questo un panino con 80 / 100 grammi affettati contiene tutte le <strong>proteine</strong> necessarie dopo un buon allenamento all’aria aperta.</p>\n<p>Cosa stai aspettando? Provale subito e facci sapere cosa ne pensi!</p>\n<p></p>', 'La Bresaola è un salume magro, gustoso e con una grande storia. Spesso viene confusa ma la bresaola non è un insaccato.Scopri quella Trentina da L Bortoleto', 0),
(3, 'Canederli', '../console/img/catProd/confezionati/3.jpeg', '<p></p><p>I <strong>Canederli</strong> sono uno dei piatti più particolari e importanti della tradizione gastronomica del Trentino Alto Adige. I più classici sono sicuramente i <strong>canederli al formaggio</strong> o allo <strong>speck</strong>.<br>\nPiccole sfere morbide e dal sapore inconfondibile i canederli tirolesi nascono dall’esigenza delle famiglie di allevatori e agricoltori di consumare anche tutto quel<strong> cibo non più fresco</strong>. Sono infatti, un fantastico piatto <strong>contro lo spreco alimentare</strong>, che rappresenta, oggi più che mai, un problema serio e rilevante.</p>\n<p>I <strong>canederli tirolesi</strong> sono un impasto a base di pane raffermo, uova e latte, il quale viene poi guarnito con i sapori che più piacciono.<br>\nCome puoi intuire la <strong>qualità delle materie prime</strong> è fondamentale.<br>\nNoi li realizziamo con il pane dei panifici locali, latte Trento, uova biologiche di montagna, formaggio del <a href=\"http://www.puzzonedimoena.com/\" target=\"_blank\" rel=\"noopener noreferrer\">caseificio di Predazzo</a> e, ovviamente, lo <a href=\"/bortoleto/categoria-prodotto/specialita-del-trentino/speck/\" target=\"_blank\" rel=\"noopener noreferrer\">speck di nostra produzione</a>!</p>\n<p>Qui puoi trovare i quattro principali tipi di nostra produzione:<br>\nI classici <em>canederli al formaggio</em> e allo <em>speck</em> oppure i nuovi agli <em>spinaci</em> e alle <em>rape rosse</em>! Scegli tu!</p>\n<p>Se questo argomento ti incuriosisce ti consiglio di dare una letta al <a href=\"/bortoleto/il-blog/il-trentino/i-canederli-tirolesi/\" target=\"_blank\" rel=\"noopener noreferrer\">nostro articolo</a>!</p>\n<p></p>', 'I Canederli tirolesi al formaggio, allo speck, agli spinaci e alle rape. Prova i canederli Trentini Tirolesi artigianali fatti a mano in brodo o asciutti!', 0),
(5, 'Carne salada', '../console/img/catProd/confezionati/5.jpg', '<p></p><p>La <strong>carne salada Trentina</strong> è un piatto tipico del Trentino Alto Adige realizzato con la parte della <strong>fesa del manzo</strong>, che viene <strong>sapientemente aromatizzata</strong> per portarla ad avere un gusto deciso ma non invasivo, rendendola piacevole sia agli adulti che ai più piccoli.<br>\nPartendo dalla fesa aromatizzata, si tagliano solitamente fette molto sottili (tipo carpaccio) per <strong>mangiarla cruda</strong> condita con olio, grana ed eventualmente qualche foglia di rucola.<br>\nLa versione della carne salada da <strong>mangiare cotta</strong> invece, si taglia un po’ più spessa e la si scotta velocemente in padella. L’accostamento tradizionale in questo caso sono sicuramente i fagioli, ma anche con le patate al forno il piatto è particolarmente interessante.</p>\n<p>Questo metodo di condimento rende la carne, tagliata a fette e imbustata sottovuoto, particolarmente resistente nel tempo. Tuttavia se dopo l’acquisto, non sei sicuro di consumarla in breve tempo ti consigliamo di congelarla, infatti, <strong>la carne salada si può congelare</strong> tranquillamente. L’importante è scongelarla almeno il giorno prima!</p>\n<p>Scegli la carne salada trentina cotta o cruda, il peso e il numero di buste. Confezioneremo le fette in confezioni sottovuoto che ti verranno spedite con <strong>corriere in 24 / 48 ore</strong>. Scegli di provare la qualità trentina de ‘L Bortoleto.</p>\n<p></p>', 'Carne salada Trentina di carne locale con gli aromi della tradizione. Acquista la carne salada del Trentino da mangiare cruda o cotta! | Bortoleto', 0),
(6, 'Formaggi', '../console/img/catProd/confezionati/6.jpg', '<p></p><p>I <strong>Formaggi Trentini</strong> sono il risultato del duro lavoro di casari, malgari e contadini che passano gran parte del loro tempo nei pascoli e negli <strong>alpeggi</strong> per la realizzazione di formaggi di qualità. Il processo per la realizzazione di questi prodotti inizia proprio in alta quota dove, gli allevatori, seguendo le rigorose regole dei vari <strong>consorzi</strong>, si impegnano per garantire agli animali la miglior qualità di vita possibile. Il latte raccolto viene portato nei caseifici dei paesi o nelle malghe dove viene analizzato e, successivamente, lavorato per dar vita ai migliori <strong>formaggi Trentini</strong>.</p>\n<p>Qui troverai principalmente formaggi della <strong>val di Fiemme</strong> e della <strong>val di Fassa</strong> come il famoso <strong>Puzzone di Moena DOP</strong>, orgoglio del nostro territorio.<br>\nPasta dura o morbida? Latte Vaccino o Caprino? Non preoccuparti! Siamo in una zona caratterizzata da una <strong>biodiversità ricca e varia</strong>. I Formaggi con <strong>vero latte di capra</strong> non mancano, assieme a quelli vaccini a pasta morbida come i Fontal oppure dura come gli stagionati al vino o al fieno.</p>\n<p>Nel territorio degli alpeggi e dei pascoli è doveroso dare importanza ai prodotti locali. Per questo motivo, in questa bottega online, troverai prodotti che rispettano la <strong>filiera a km0</strong> come i formaggi Trentini provenienti dai caseifici della val di Fiemme, val di Fassa, e dalle malghe negli alpeggi in alta quota.</p>\n<p></p>', 'Formaggi Trentini realizzati nei caseifici di val di Fassa, Fiemme e nelle Malghe degli Alpeggi.Formaggi Vaccini e Caprini, Freschi e Stagionati | Bortoleto', 0),
(7, 'Insaccati affumicati', '../console/img/catProd/confezionati/7.jpg', '<p></p><p>Gli <strong>insaccati affumicati</strong> artigianali di nostra produzione sono salumi tipici del <strong>Trentino Alto Adige</strong>, affumicati nel nostro <strong>forno a legna</strong> e lasciati ad asciugare per qualche giorno.<br>\nImpasto misto tra carne di <strong>manzo</strong> e di <strong>maiale</strong>, gli insaccati affumicati che ti proponiamo, sono di due tipi diversi.</p>\n<ul>\n<li><em>Le Landjager</em></li>\n<li><em>La Luganega Affumicata</em></li>\n</ul>\n<p>Le Landjager sono piccole salsicce (vendute a coppie) tipicamente mangiate durante i <strong>tracking in alta montagna</strong> o comunque nell’attività sportiva.<br>\nLe Luganeghe affumicate, invece, hanno un formato più tipico da salume, infatti, sono ottime come salume da taglio negli <strong>aperitivi</strong> o durante i pasti.</p>\n<p>In trentino, come in altre località alpine, il sapore dell’affumicato è molto apprezzato, forse per il <strong>legname</strong> di prima qualità o, forse, per l’<strong>ottima carne</strong>.<br>\nI nostri insaccati affumicati godono di entrambe queste caratteristiche e vengono graditi da persone di tutte le età.</p>\n<p></p>', 'Insaccati affumicati artigianali rentini di carne di manzo e maiale con erbe aromatiche naturali e lavorati a mano. Prova l\'affumicatura con legna di faggio', 0),
(8, 'Insaccati di selvaggina', '../console/img/catProd/confezionati/8.jpg', '<p></p><p><strong>Insaccati di selvaggina</strong> nostrana dal Trentino Alto Adige. Qui troverai diversi <strong>tipi</strong> di salumi<strong> di carne di selvaggina</strong>, in particolare:</p>\n<ul>\n<li><em>Il Cervo</em></li>\n<li><em>Il Capriolo</em></li>\n<li><em>Il Camoscio</em></li>\n</ul>\n<p>Gli insaccati realizzati con questi tipi di carne, sono <strong>insaccati di selvaggina</strong> dal sapore intenso che viene però attenuato da una piccola percentuale di maiale (circa il 20%) che rende complessivamente più apprezzabile il salume anche ai più piccoli.<br>\nQuesto tipo di salumi sono <strong>prodotti artigianali</strong> che realizziamo noi partendo da materie prime di qualità. Infatti, proprio durante i periodi di <strong>cacciagione</strong>, i cacciatori soddisfano il fabbisogno della materia prima fondamentale, alla quale, durante la lavorazione, aggiungiamo i giusti <strong>aromi</strong> per esaltare il sapore della <strong>carne di selvaggina</strong>.</p>\n<p>Nei boschi Trentini la <strong>biodiversità</strong> è ricca e varia. Per quanto riguarda la selvaggina, i tipi di animali selvatici che popolano queste zone sono, come già anticipato, <em>il cervo, il capriolo e il camoscio</em>. Non vivono qui invece i cinghiali, che si trovano più in basso nella penisola.<br>\nGli insaccati di selvaggina sono quindi tra i salumi più particolari e legati a i boschi presenti<strong> ai piedi delle Dolomiti</strong>.</p>\n<p></p>', 'Insaccati e salumi di selvaggina artigianali di nostra produzione. Diversi tipi di carne di selvaggina locale direttamente dal Trentino', 0),
(9, 'Salumi Trentini', '../console/img/catProd/confezionati/9.jpg', '<p></p><p>I <strong>Salumi Trentini</strong> e gli <strong>insaccati stagionati</strong> sono i nostri prodotti di cui più andiamo fieri. Ci permettono, infatti, di raccontare la storia della nostra azienda, la tradizione del Trentino e tutta la passione per il nostro lavoro. Oltre alla <strong>tradizione</strong> è importante anche l’<strong>innovazione</strong>, infatti, proprio per andare incontro alle nuove esigenze alimentari, i nostri salumi sono <strong>senza glutine</strong> e <strong>senza lattosio</strong>.</p>\r\n<p>L’esperienza nella scelta delle materie prime e nella lavorazione ci permette di preparare prodotti di altissima qualità.<br>\r\nTutto inizia proprio dalle <strong>materie prime</strong> che, scelte dopo attente riflessioni, rappresentano la parte fondamentale per riuscire a creare <strong>insaccati magri</strong> e <strong>gustosi</strong>. Ogni salume viene toccato con mano da mastri salumieri per verificarne la conformità ai nostri standard di qualità. Passano poi nelle <strong>celle di stagionatura</strong>, dove ci resteranno per alcuni mesi a temperatura e umidità controllate. Quando il livello di stagionatura raggiunge la perfezione, sia passa alla pulizia dei salumi ed eventualmente al confezionamento. Questo lungo procedimento per realizzare i migliori salumi Trentini rende evidente come questi prodotti riescono a relazionare perfettamente <strong>tradizione</strong>, <strong>innovazione</strong> e <strong>passione</strong> per questo lavoro.</p>\r\n<p>La scelta è sicuramente vasta, partendo dai classici come il <strong>salame gentile</strong> o nostrano, passando dalle <strong>luganeghe</strong> a pasta fine o grossa, fino ai più particolari come il salame al puzzone di Moena DOP. Tutti i nostri salumi sono rigorosamente <strong>senza glutine</strong> e, a eccezione di qualche salume particolare nel quale è ampiamente segnalato, sono <strong>senza lattosio</strong>.</p>\r\n<p></p>', 'Insaccati Stagionati e Salumi Trentini Online. Salumi nostrani artigianali senza glutine e senza lattosio. Qualità della tradizione Trentina. Scopri di più!', 0),
(10, 'Speck', '../console/img/catProd/confezionati/10.jpg', '<p></p><p>Lo <strong>speck</strong> Tirolese è uno dei salumi tipici della tradizione del Trentino Alto Adige e rappresenta un classico. Questa categoria è dedicata proprio alla <strong>vendita online</strong> del nostro <strong>speck Trentino artigianale</strong> online.</p>\r\n<p>Affumicato con sola legna naturale dei boschi del Trentino e prodotto <b>artigianalmente</b> nei nostri laboratori, il nostro speck, è suddiviso in 3 tipologie principali.</p>\r\n<ul>\r\n<li>I <b>magri</b> come il filetto o il fiocco</li>\r\n<li>I <b>classici</b>, che raggiunti i 6 mesi di stagionatura vengono costantemente controllati per evitare un’eccessiva asciugatura fino al raggiungimento massimo di 8 mesi.</li>\r\n<li><b>Il grasso</b>. Prodotto con scrofe Trentine stagionato per 14 mesi dall’amabile sapore dolce.</li>\r\n</ul>\r\n<p>Probabilmente i prodotti che più ci caratterizzano in termini di qualità, i nostri <strong>speck</strong> e le nostre <strong>Pancette affumicate</strong> sono realizzati dai nostri salumieri che toccano con mano ogni singola unità per assicurarne l’<b>eccellenza</b>.</p>\r\n<p>Anche qui, come per tutti i nostri prodotti, la qualità inizia con la scelta delle <strong>materie prime pregiate</strong>. Il primo passaggio consiste nel selezionare le cosce di suino, le quali vengono prima disossate e poi rifilate per conferire la forma. Di seguito passiamo alla salatura con la <strong>ricetta di famiglia</strong>, usando i giusti aromi per esaltare il sapore. Infine avviene l’<strong>affumicatura</strong> e la <strong>stagionatura</strong> a temperatura e umidità controllate. Il tempo di stagionatura varia in base al tipo di speck, per quello classico il periodo oscilla <strong>tra i 6 e gli 8 mesi</strong> per evitare un’asciugatura eccessiva.</p>\r\n<p>Da ormai più di 110 anni la nostra azienda è un punto di riferimento per la <strong>vendita di speck artigianale</strong> Tirolese, ma da adesso lo puoi acquistare comodamente <strong>online</strong>!</p>\r\n<p></p>', 'Speck Tirolese dal Trentino Alto Adige Online. Produzione e vendita di speck artigianale dal Trentino. Acquistalo direttamente online! | Bortoleto', 0),
(11, 'Wurstel', '../console/img/catProd/confezionati/11.jpg', 'I <strong>wurstel</strong> tirolesi sono unici nel loro genere. Morbidi, gustosi e facili da cucinare sono apprezzati a tutte le età! In questa categoria potrai acquistare i <strong>wurstel artigianali di maiale</strong> tipici della cultura tirolese direttamente <strong>online</strong>!\r\n\r\nPiccoli insaccati dalla forma allungata, si tratta di salsicciotti si suino cotti e spesso affumicati pratici e veloci da cucinare. Proprio perché sono <strong>prodotti cotti</strong>, non è necessario cucinarli a lungo, basterà solo riscaldarli in padella, alla piastra o nell\'acqua bollente per 5 / 6 minuti.\r\n\r\nNoi te ne proponiamo di diversi tipi, in particolare abbiamo\r\n<ul>\r\n 	<li>I <strong>Wurstel affumicati</strong> che sono i classici. Puoi scegliere tu se prenderli con la pelle al naturale e quindi un po\' più croccanti e saporiti grazie al budello oppure senza pelle più morbidi e delicati.</li>\r\n 	<li>I <strong>Wurstel Bianchi</strong>. Di suino e manzo prodotti allo stesso modo ma senza la fase di affumicatura. Il colore bianco deriva proprio dalla mancanza dell\'affumicatura. Questi sono i più classici da fare bolliti.</li>\r\n</ul>\r\nSe ti abbiamo incuriosito leggi di più all\'interno dei vari prodotti e acquista direttamente <strong>online</strong> i migliori <strong>wurstel Tirolesi</strong> di maiale <strong>artigianali</strong>!', 'Wurstel del Trentino Alto Adige Artigianali online. Acquista online le Wurstel di Suino Tirolesi di Qualità, tanti tipi tra cui scegliere | Bortoleto', 0),
(30, 'Fresca di Prova', '../console/img/catProd/freschi/30.jpg', 'Descrizione Fresca di Prova', '', 1),
(31, 'Nuova Categoria di Prova', '../console/img/catProd/freschi/31.png', 'Descrizione principale della categoria di prova', 'MEta Descrizione della categoria di prova', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `commenti`
--

CREATE TABLE `commenti` (
  `id` int(11) NOT NULL,
  `id_ricetta` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `testo` varchar(500) NOT NULL,
  `data` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `curr_week_views`
--

CREATE TABLE `curr_week_views` (
  `pagina` varchar(30) NOT NULL,
  `data` date NOT NULL,
  `views` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `email_sub`
--

CREATE TABLE `email_sub` (
  `email` varchar(75) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `email_sub`
--

INSERT INTO `email_sub` (`email`, `date`) VALUES
('dellantonio47@gmail.com', '2020-01-16 08:15:41'),
('dellantonio@email.it', '2020-01-08 09:14:56'),
('roberto.dellantonio@studenti.unitn.it', '2020-01-07 18:52:04');

-- --------------------------------------------------------

--
-- Struttura della tabella `menu`
--

CREATE TABLE `menu` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) COLLATE utf8_bin NOT NULL,
  `copertina` varchar(1000) COLLATE utf8_bin NOT NULL,
  `immagine` varchar(1000) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dump dei dati per la tabella `menu`
--

INSERT INTO `menu` (`id`, `nome`, `copertina`, `immagine`) VALUES
(1, 'Dai banchi e scaffali della macelleria', '../console/img/menu/copertine/Daibanchiescaffalidellamacelleria.jpg', '../console/img/menu/Daibanchiescaffalidellamacelleria.jpg'),
(7, 'La Carne Dellantonio', '../console/img/menu/copertine/LaCarneDellantonio.png', '../console/img/menu/LaCarneDellantonio.jpg'),
(8, 'La Carta', '../console/img/menu/copertine/LaCarta.jpg', '../console/img/menu/LaCarta.jpg'),
(9, 'I Dolci', '../console/img/menu/copertine/IDolci.jpg', '../console/img/menu/IDolci.jpg');

-- --------------------------------------------------------

--
-- Struttura della tabella `notifiche`
--

CREATE TABLE `notifiche` (
  `id` int(11) NOT NULL,
  `testo` varchar(45) COLLATE utf8_bin NOT NULL,
  `data` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `link` varchar(100) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Struttura della tabella `orderSum`
--

CREATE TABLE `orderSum` (
  `id` varchar(100) NOT NULL,
  `totale` double(10,2) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `delivery` varchar(45) NOT NULL,
  `items` varchar(10000) NOT NULL,
  `varianti` varchar(5000) DEFAULT NULL,
  `nome` varchar(100) NOT NULL,
  `email` varchar(75) NOT NULL,
  `citta` varchar(100) NOT NULL,
  `indirizzo` varchar(100) NOT NULL,
  `zip` varchar(10) NOT NULL,
  `stato` varchar(45) NOT NULL DEFAULT 'preparazione'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `orderSum`
--

INSERT INTO `orderSum` (`id`, `totale`, `date`, `delivery`, `items`, `varianti`, `nome`, `email`, `citta`, `indirizzo`, `zip`, `stato`) VALUES
('1', 14.35, '2019-05-19 08:25:15', 'Refrigerato', '', NULL, '', '', '', '', '', 'preparazione'),
('10', 50.00, '2019-05-26 08:25:15', 'Ritiro in negozio', '', NULL, '', '', '', '', '', 'preparazione'),
('11', 56.00, '2019-06-01 13:34:06', 'Normale', '', NULL, '', '', '', '', '', 'preparazione'),
('12', 0.01, '2019-06-01 13:46:19', 'Refrigerato', '', NULL, '', '', '', '', '', 'preparazione'),
('13', 0.01, '2019-06-01 13:50:09', 'Refrigerato', '', NULL, '', '', '', '', '', 'preparazione'),
('14', 0.01, '2019-06-01 14:10:11', 'Normale', '', NULL, '', '', '', '', '', 'preparazione'),
('15', 15.90, '2019-06-08 09:36:00', 'Ritiro in negozio', '', NULL, '', '', '', '', '', 'spedito'),
('16', 130.30, '2019-05-02 08:45:00', 'Normale', '', NULL, '', '', '', '', '', 'preparazione'),
('17', 35.80, '2019-06-10 10:16:07', 'Refrigerato', '', NULL, '', '', '', '', '', 'preparazione'),
('2', 10.50, '2019-05-26 08:25:15', 'Ritiro in negozio', '', NULL, '', '', '', '', '', 'preparazione'),
('32', 0.01, '2019-06-10 13:10:32', 'Refrigerato', 'Carre affumicato_1;Braciole di Suino_1', NULL, '', '', '', '', '', 'spedito'),
('33', 0.01, '2019-06-10 13:11:35', 'Normale', 'Stinco affumicato_2', NULL, '', '', '', '', '', 'preparazione'),
('34', 0.01, '2019-06-20 16:10:16', 'Normale', 'Prosciuttino tirolese_1;Carre affumicato_1 ', NULL, 'Roberto,Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'altro'),
('35', 0.01, '2019-06-20 16:13:30', 'Normale', 'Coppa_1;Carre affumicato_1', NULL, 'Roberto Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'ritirato'),
('36', 0.01, '2019-06-20 16:29:18', 'Normale', 'Coppa_1;Carre affumicato_1', NULL, 'Roberto Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'preparazione'),
('Bortol04072019201419', 0.01, '2019-07-04 18:18:37', 'Refrigerato', 'Canederli allo speck_1;Bresaola di bovino_1;Coppa_1;Carre affumicato_1;Braciole di Suino_1', NULL, 'Roberto Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'preparazione'),
('Bortol04092019114731', 32.00, '2019-09-04 16:56:12', 'Refrigerato', 'Canederli allo speck_1;Bresaola di bovino_1;Coppa_1;Carre affumicato_1;Braciole di Suino_1', NULL, 'Roberto Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'preparazione'),
('Bortol05072019103032', 250.00, '2019-07-05 08:32:20', 'Refrigerato', 'Canederli allo speck_1;Bresaola di bovino_1;Coppa_1;Carre affumicato_1;Braciole di Suino_1', NULL, 'Roberto Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'preparazione'),
('Bortol05072019103254', 250.00, '2019-07-05 08:32:59', 'Refrigerato', 'Canederli allo speck_1;Bresaola di bovino_1;Coppa_1;Carre affumicato_1;Braciole di Suino_1', NULL, 'Roberto Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'preparazione'),
('Bortol06072019183748', 387.20, '2019-07-06 09:32:32', 'Refrigerato', 'Canederli allo speck_1;Bresaola di bovino_20;Coppa_2;Carre affumicato_1;Braciole di Suino_1', NULL, 'Roberto Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'consegnato'),
('Bortol10092019171752', 291.76, '2019-09-10 17:18:16', 'Refrigerato', 'Braciole di Suino_1;Costata di manzo con l\'osso_1;Carre\'_1;Formae di Fiemme_1;Caciotta malga Cerin_1', '84*1:51*1:126*2:129*1:128*1', 'Roberto Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'spedito'),
('Bortol12072019114731', 25.00, '2019-07-12 09:47:39', 'Refrigerato', 'Canederli allo speck_1;Bresaola di bovino_1;Coppa_1;Carre affumicato_1;Braciole di Suino_1', '', 'Roberto Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'preparazione'),
('Bortol20062019183412', 50.40, '2019-06-20 16:34:16', 'Normale', 'Coppa_1;Carre affumicato_1', NULL, 'Roberto Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'spedito'),
('Bortol20062019183748', 12.50, '2019-06-20 16:37:53', 'Normale', 'Coppa_2;Carre affumicato_1', NULL, 'Roberto Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'consegnato'),
('Bortol21092019100734', 25.00, '2019-09-21 10:08:19', 'Refrigerato', 'Speck_1;Carre\'_1;Braciole di Suino_1;Costata di manzo con l\'osso_1', '126*3:129*2:130*1:51*1:118*1', 'Roberto Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'preparazione'),
('Bortol21092019100911', 1.00, '2019-09-21 10:09:26', 'Refrigerato', 'Speck_1;Carre\'_1;Braciole di Suino_1;Costata di manzo con l\'osso_1', '126*3:129*2:130*1:51*1:118*1', 'Roberto Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'preparazione'),
('Bortol21092019104100', 0.01, '2019-09-21 10:41:09', 'Refrigerato', 'Speck_1;Carre\'_1;Braciole di Suino_1;Costata di manzo con l\'osso_1', '126*3:129*2:130*1:51*1:118*1', 'Roberto Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'preparazione'),
('Bortol21092019104852', 102.25, '2019-09-21 10:49:10', 'Refrigerato', 'Speck_1;Carre\'_1;Braciole di Suino_1;Costata di manzo con l\'osso_1', '126*3:129*2:130*1:51*1:118*1', 'Roberto Dellantonio', 'dellantonio47@gmail.com', 'Predazzo', 'Via Cesare Battisti 2', '38037', 'spedito');

-- --------------------------------------------------------

--
-- Struttura della tabella `prodotti_idea`
--

CREATE TABLE `prodotti_idea` (
  `id` int(11) NOT NULL,
  `id_prod` int(11) NOT NULL,
  `id_idea` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dump dei dati per la tabella `prodotti_idea`
--

INSERT INTO `prodotti_idea` (`id`, `id_prod`, `id_idea`) VALUES
(33, 10, 58),
(38, 11, 58);

-- --------------------------------------------------------

--
-- Struttura della tabella `prodotto`
--

CREATE TABLE `prodotto` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `categoria` varchar(45) DEFAULT NULL,
  `immagine` varchar(1000) NOT NULL,
  `descrizione` text NOT NULL,
  `meta_descrizione` varchar(160) NOT NULL,
  `costo` float NOT NULL DEFAULT '0',
  `disponibile` tinyint(4) NOT NULL DEFAULT '1',
  `fresco` tinyint(4) NOT NULL DEFAULT '0',
  `num_acquisti` int(11) NOT NULL DEFAULT '0',
  `peso` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `prodotto`
--

INSERT INTO `prodotto` (`id`, `nome`, `categoria`, `immagine`, `descrizione`, `meta_descrizione`, `costo`, `disponibile`, `fresco`, `num_acquisti`, `peso`) VALUES
(2, 'Carre\' Affumicato', 'Affumicati', '../console/img/prodotti/confezionati/2.jpg', 'Carre\' di maiale affumicato e tagliato a fette di circa 130gr.\r\nQuesto tipo di braciola presenta esternamente il classico colore oro perlato che e\' sinonimo di un\'affumicatura fatta a regola d\'arte, mentre l\'interno, molto tenero, e\' tendente ad un rosa beige che indica la giusta cottura e l\'irrinunciabile freschezza della carne.\r\n', 'Fette di carre\' di maiale affumicato. Braciole con l\'osso affumicate e confezionate sottovuoto. Ogni fetta ha un peso di circa 200 grammi.', 4, 1, 0, 15, 0.4),
(3, 'Lonza', 'Affumicati', '../console/img/prodotti/confezionati/3.jpg', 'Lonza di maiale affumicata e tagliata a fette di circa 130 gr.\r\nLa braciola disossata e affumicata e\' particolarmente morbida e saporita e puo\' essere sia arrostita in padella o in forno sia aggiunta alle minestre per insaporirle. Il sapore particolare non e\' invasivo nel piatto e permette di essere accostato anche ad abbinamenti tipicamente delicati.', '', 4, 1, 0, 0, 0.4),
(4, 'Prosciuttino tirolese', 'Affumicati', '../console/img/prodotti/confezionati/4.jpg', 'Perfetto connubio tra il nostro prosciutto cotto e lo speck, il prosciuttino tirolese, e\' un comodo trancio di prosciutto cotto leggermente affumicato e sapientemente aromatizzato con i profumi dello speck. ', '', 9.9, 1, 0, 5, 0.5),
(5, 'Stinco', 'Affumicati', '../console/img/prodotti/confezionati/5.jpg', 'Stinco di suino affumicato con cotenna per mantenerne la morbidezza. Lo stinco e\' precotto in forno a basse temperature e va finito di cucinare in forno per circa 1 ora a 160\' coperto da carta stagnola', '', 4.5, 1, 0, 0, 0.8),
(6, 'Bresaola di manzo', 'Bresaola', '../console/img/prodotti/confezionati/6.jpg', 'Bresaola di manzo leggermente affumicata realizzata con bovini dei pascoli locali per garantire la provenienza a km0. Bresaola da 500 e 600 grammi di 1\' Qualita\'', '', 15.9, 1, 0, 6, 0.5),
(7, 'Bresaola di cervo', 'Bresaola', '../console/img/prodotti/confezionati/7.jpg', 'Bresaola di cervo, la selvaggina piu\' delicata in val di Fiemme. Carne magra da\' una bresaola magra e in questo caso anche saporita al punto giusto. Una leggera affumicatura conferisce al sapore una caratteristica nota montana.', '', 7.9, 1, 0, 0, 0),
(8, 'Bresaola di equino', 'Bresaola', '../console/img/prodotti/confezionati/8.jpg', 'Bresaola di equino leggermente affumicata. Leggera, magra e gustosa.', '', 16.9, 1, 0, 0, 0),
(9, 'Coppa', 'Bresaola', '../console/img/prodotti/confezionati/9.jpg', 'La Coppa Stagionata, chiamata anche capocollo e\' un insaccato stagionato di suino prodotto col la parte finale del carre\' (il collo) del maiale. Una volta lavorata, la carne, viene sapientemente aromatizzata.', '', 8.9, 1, 0, 10, 0),
(10, 'Canederli al formaggio', 'Canederli', '../console/img/prodotti/confezionati/10.jpeg', 'Canederli guarniti con formaggi dolci locali come il fontal di Cavalese.\r\nPrediligono una cottura in acqua salata per una consumazione senza brodo e conditi con burro, grana e salvia!', 'MEta DEscrizione dei canederli al formaggioMEta DEscrizione dei canederli al formaggioMEta DEscrizione dei canederli al formaggioMEta DEscrizione dei canederli ', 2.9, 1, 0, 0, 0),
(11, 'Canederli allo speck', 'Canederli', '../console/img/prodotti/confezionati/11.jpeg', 'Canederli guarniti con speck nostrano.\r\nBuoni asciutti ma insuperabili nel brodo!\r\n', '', 2.9, 1, 0, 6, 0),
(12, 'Carne salada', 'Carne salada', '../console/img/prodotti/confezionati/12.jpg', 'Buste di carne salada tagliata un po spessa da scottare in padella o sottile da mangiare come carpaccio.\r\nLa prima ottima accompagnata dai fagioli, la seconda guarnita con olio, grana e rucola fresca.', '', 2.5, 1, 0, 0, 0),
(14, 'Caciotta malga Cerin', 'Formaggi', '../console/img/prodotti/confezionati/14.jpg', 'Caciotta Malga Cerin proveniente da una piccola malga locale a Tesero. Peso di circa 300g', '', 4.9, 1, 0, 0, 0),
(15, 'Fontal di Cavalese', 'Formaggi', '../console/img/prodotti/confezionati/15.jpg', 'Il termine Fontal e\' entrato in uso negli anni \'50 e, dagli anni \'70, questo formaggio viene prodotto dai caseifici cooperativi trentini. Il Fontal di Cavalese e\' un formaggio con caratteristiche organolettiche particolari, legate soprattutto alla freschezza e alla qualita\' del latte utilizzato.\r\nE\' un formaggio a pasta semidura e semicotta, di colore leggermente paglierino, compatta, in rada occhiatura e di forma cilindrica con scalzo concavo.', '', 2.9, 1, 0, 0, 0),
(16, 'Formae di Fiemme', 'Formaggi', '../console/img/prodotti/confezionati/16.jpg', 'Il Formae Val di Fiemme e\' prodotto da latte crudo, al termine della stagionatura si ottiene un formaggio dalle\r\ncaratteristiche uniche.', '', 3.2, 1, 0, 5, 0),
(17, 'Malga Cerin', 'Formaggi', '../console/img/prodotti/confezionati/17.jpg', 'Formaggio morbido e dolce realizzato artigianalmente da una piccola malga a Tesero (val di Fiemme). Peso di circa 300g', '', 4.9, 1, 0, 0, 0),
(18, 'Puzzone di Moena DOP', 'Formaggi', '../console/img/prodotti/confezionati/18.jpg', 'Il Puzzone di Moena, prodotto nelle valli di Fiemme, Fassa e Primiero, e\' un formaggio DOP e quello prodotto con il latte di alpeggio, e\' tutelato dal presidio slow food. Deve il suo nome all\'intenso aroma dovuto al latte con cui e\' prodotto e alla pratica della spugnatura giornaliera durante la stagionatura.', '', 3.6, 1, 0, 0, 0),
(19, 'Stagionato nel fieno', 'Formaggi', '../console/img/prodotti/confezionati/19.jpg', 'Formaggio a pasta dura stagionato nel fieno. Sapore particolare da abbinare con il miele.', '', 6.6, 1, 0, 0, 0),
(20, 'Stagionato nel vino', 'Formaggi', '../console/img/prodotti/confezionati/20.jpg', 'Formaggio a pasta dura stagionato nel vino. ', '', 6.6, 1, 0, 0, 0),
(21, 'Landjager', 'Insaccati affumicati', '../console/img/prodotti/confezionati/21.jpg', 'Le Landjager sono i cosi detti salamini da passeggio, questo perche\' il modo migliore per apprezzarle e\' durante una gita in montagna con la landjager in una mano e il pane nell\'altra.\r\nQuesti salamini vengono realizzati a mano e vengono tutti legati a coppie.', '', 2.5, 1, 0, 0, 0),
(22, 'Luganega affumicata', 'Insaccati affumicati', '../console/img/prodotti/confezionati/22.jpg', 'Morbida e magra, la luganega affumicata e\' un buonissimo compromesso per un antipasto tirolese.', '', 2.8, 1, 0, 0, 0),
(23, 'Il cervo', 'Insaccati di selvaggina', '../console/img/prodotti/confezionati/23.jpg', 'Piccole e originali di cervo. Da portare con voi ovunque', '', 4.5, 1, 0, 6, 0),
(24, 'Luganega di cavallo', 'Insaccati di selvaggina', '../console/img/prodotti/confezionati/24.jpg', 'Con carne equina dal gusto forte e aromatizzato, ottimo accompagnato al pane.', '', 2.8, 1, 0, 0, 0),
(25, 'Luganega di cervo', 'Insaccati di selvaggina', '../console/img/prodotti/confezionati/25.jpg', 'Con carne di cervi delle valli di Fassa e Fiemme dal gusto deciso e accattivante.', '', 4.5, 1, 0, 0, 0),
(26, 'Salame di camoscio', 'Insaccati di selvaggina', '../console/img/prodotti/confezionati/26.jpg', 'Salame di selvaggina locale. Di camoscio piu saporito. Peso variabile tra 200g e 300g', '', 5.5, 1, 0, 0, 0),
(27, 'Salame di capriolo', 'Insaccati di selvaggina', '../console/img/prodotti/confezionati/27.jpg', 'Salame di selvaggina locale. Di capriolo dal sapore forte ma non invadente. Peso variabile tra 200g e 300g', '', 5.5, 1, 0, 0, 0),
(28, 'Salame di cervo', 'Insaccati di selvaggina', '../console/img/prodotti/confezionati/28.jpg', 'Salame di selvaggina locale. Di cervo piu dolce. Peso variabile tra 200g e 300g', '', 5.5, 1, 0, 0, 0),
(29, 'Il Feudo', 'Salumi Trentini', '../console/img/prodotti/confezionati/29.jpg', 'Salsiccia secca sale e pepe dalla forma allungata, delicato e ideale per i bambini. Peso di circa 130g - 150g', '', 4, 1, 0, 0, 0),
(30, 'Luganega fina', 'Salumi Trentini', '../console/img/prodotti/confezionati/30.jpg', 'Un vero e proprio ungheresino nostrano a pasta fine lievemente affumicato e aromatizzato. Peso di circa 150g - 200g', '', 2.8, 1, 0, 0, 0),
(31, 'Luganega grossa', 'Salumi Trentini', '../console/img/prodotti/confezionati/31.jpg', 'Un vero e proprio ungheresino nostrano a pasta grossa aromatizzato e senza affumicatura. Peso di circa 150g - 200g', '', 2.8, 1, 0, 0, 0),
(32, 'Salame al puzzone', 'Salumi Trentini', '../console/img/prodotti/confezionati/32.jpg', 'Salame nostrano con all interno pezzetti di formaggio Puzzone di Moena. Peso di circa 500g', '', 10.2, 1, 0, 0, 0),
(33, 'Salame nostrano', 'Salumi Trentini', '../console/img/prodotti/confezionati/33.jpg', 'In budello di manzo (piu saporito) o di suino (piu dolce), un classico. Peso di circa 400g - 500g', '', 9.5, 1, 0, 0, 0),
(34, 'Salame piccante', 'Salumi Trentini', '../console/img/prodotti/confezionati/34.jpg', 'Salame nostrano con aroma piccante. Peso di circa 150g', '', 2.8, 1, 0, 0, 0),
(35, 'Angolare', 'Speck', '../console/img/prodotti/confezionati/35.jpg', 'Parte angolare di speck confezionato sottovuoto dal peso di circa 1 kg.', '', 13.9, 1, 0, 0, 0),
(36, 'Centrale', 'Speck', '../console/img/prodotti/confezionati/36.jpg', 'Parte centrale di speck confezionato sottovuoto dal peso di circa 0,5 / 0,6 kg.', '', 9.5, 1, 0, 0, 0),
(37, 'Filetto di speck', 'Speck', '../console/img/prodotti/confezionati/37.jpg', 'Filetto di speck confezionato sottovuoto dal peso di circa 0,15 kg.', '', 3.9, 1, 0, 0, 0),
(38, 'Fiocco di speck', 'Speck', '../console/img/prodotti/confezionati/38.jpg', 'Fiocco di speck (Parte magra dello speck) confezionato sottovuoto dal peso di circa 0,4 kg.', '', 6.9, 1, 0, 0, 0),
(39, 'Intero', 'Speck', '../console/img/prodotti/confezionati/39.jpg', 'Speck intero, peso variabile tra i 4,5 kg. ai 5,5 kg.', '', 59.9, 1, 0, 0, 0),
(40, 'Meta', 'Speck', '../console/img/prodotti/confezionati/40.jpg', 'Meta Speck confezionato sottovuoto dal peso di circa 2,5 kg.', '', 29.9, 1, 0, 0, 0),
(41, 'Pancetta affumicata', 'Speck', '../console/img/prodotti/confezionati/41.jpg', 'Pancetta affumicata confezionata sottovuoto.', '', 3, 1, 0, 0, 0),
(42, 'Merano', 'Wurstel', '../console/img/prodotti/confezionati/42.jpg', 'Wurstel di suino. Il classico col la pelle al naturale per dare sapore.', '', 2.9, 1, 0, 0, 0),
(43, 'Spellati', 'Wurstel', '../console/img/prodotti/confezionati/43.jpg', 'Wurstel di suino. Senza pelle per dare delicatezza. Apprezzate sopratutto dai piu\' piccoli.', '', 2.5, 1, 0, 0, 0.25),
(44, 'Weisswurs', 'Wurstel', '../console/img/prodotti/confezionati/44.jpg', 'Wurstel di suino non affumicate. Ottime da fare bollite.', '', 2.5, 0, 0, 0, 0),
(58, 'Fresco di Prova', 'Fresca di Prova', '../console/img/prodotti/freschi/58.jpg', 'Descrizione Prodotto Fresco di Prova', '', 15.5, 1, 1, 0, 0),
(59, 'Prodotto fresco', 'Fresca di Prova', '../console/img/prodotti/freschi/59.jpg', 'Descrizione Prodotto Fresco', 'Meta Descrizione Prodotto Fresco', 10.5, 1, 1, 0, 0.8);

-- --------------------------------------------------------

--
-- Struttura della tabella `products_variants`
--

CREATE TABLE `products_variants` (
  `id` int(11) NOT NULL,
  `idProd` int(11) DEFAULT NULL,
  `variant` varchar(45) COLLATE utf8_bin NOT NULL,
  `variantName` varchar(45) COLLATE utf8_bin NOT NULL,
  `supplement` double NOT NULL DEFAULT '0',
  `pesoMaggiore` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dump dei dati per la tabella `products_variants`
--

INSERT INTO `products_variants` (`id`, `idProd`, `variant`, `variantName`, `supplement`, `pesoMaggiore`) VALUES
(54, 3, 'Fette per confezione', 'n° 2', 4, 0.4),
(55, 3, 'Fette per confezione', 'n° 3', 6, 0.6),
(56, 3, 'Fette per confezione', 'n° 4', 8, 0.8),
(62, 7, 'Peso', '200 gr', 0, 0),
(63, 7, 'Peso', '300 gr', 3, 0),
(66, 8, 'Peso', '500 gr', 0, 0),
(67, 8, 'Peso', '600 gr', 3, 0),
(68, 10, 'Numero per confezione', '2', 0, 0),
(69, 10, 'Numero per confezione', '3', 1.5, 0),
(70, 10, 'Numero per confezione', '4', 3, 0),
(71, 11, 'Numero per confezione', '2', 0, 0),
(72, 11, 'Numero per confezione', '3', 1.5, 0),
(73, 11, 'Numero per confezione', '4', 3, 0),
(74, 12, 'Utilizzo', 'Carpaccio', 0.5, 0.5),
(75, 12, 'Utilizzo', 'Da Scottare', 0, 0),
(76, 12, 'Peso', '100 gr', 0.1, 0),
(77, 12, 'Peso', '150 gr', 1.1, 0.2),
(78, 12, 'Peso', '200 gr', 2.2, 0),
(79, 12, 'Peso', '250 gr', 3.3, 0),
(80, 12, 'Peso', '300 gr', 4.4, 0),
(81, 15, 'Peso', '200 gr', 0, 0),
(82, 15, 'Peso', '400 gr', 2.5, 0),
(83, 15, 'Peso', '600 gr', 5, 0),
(84, 16, 'Peso', '200 gr', 0, 0),
(85, 16, 'Peso', '400 gr', 3.2, 0),
(86, 16, 'Peso', '600 gr', 6.4, 0),
(87, 18, 'Peso', '200 gr', 0, 0),
(88, 18, 'Peso', '400 gr', 3.6, 0),
(89, 18, 'Peso', '600 gr', 7.2, 0),
(90, 19, 'Peso', '300 gr', 0, 0),
(91, 19, 'Peso', '600 gr', 6.6, 0),
(92, 20, 'Peso', '300 gr', 0, 0),
(93, 20, 'Peso', '600 gr', 6.6, 0),
(94, 39, 'Divisione', 'intero', 0, 0),
(95, 39, 'Divisione', 'due metà', 0, 0),
(96, 39, 'Divisione', '4 quarti', 0, 0),
(97, 39, 'Divisione', '6 sesti', 0, 0),
(98, 40, 'Divisione', 'metà', 0, 0),
(99, 40, 'Divisione', 'due quarti', 0, 0),
(100, 40, 'Divisione', '3 sesti', 0, 0),
(101, 41, 'Peso', '200 gr', 0, 0),
(102, 41, 'Peso', '300 gr', 1.5, 0),
(103, 41, 'Peso', '400 gr', 3, 0),
(104, 41, 'Peso', '500 gr', 4.5, 0),
(105, 41, 'Peso', '1 kg', 11.9, 0),
(112, 42, ' Numero per confezione', '2', 0, 0),
(113, 42, ' Numero per confezione', '3', 1.5, 0),
(114, 42, ' Numero per confezione', '4', 3, 0),
(115, 44, ' Numero per confezione', '2', 0, 0),
(116, 44, ' Numero per confezione', '3', 1.25, 0),
(117, 44, ' Numero per confezione', '4', 2.5, 0),
(144, 58, ' Variante 1', 'Scelta 1', 0, 0),
(145, 58, ' Variante 2', 'Scelta 1', 0, 0),
(146, 58, ' Variante 1', 'Scelta 2', 2, 0),
(147, 58, ' Variante 2', 'Scelta 2', 2, 0),
(160, 43, 'Numero per confezione', 'n° 2', 2.5, 0.25),
(161, 43, 'Numero per confezione', 'n° 3', 3.75, 0.35),
(162, 43, 'Numero per confezione', 'n° 4', 5, 0.5),
(163, 2, 'Fette per confezione', 'n° 2', 4, 0.4),
(164, 2, 'Fette per confezione', 'n° 3', 6, 0.6),
(165, 2, 'Fette per confezione', 'n° 4', 8, 0.8),
(166, 2, 'Fette per confezione', 'n° 5', 10, 1),
(167, 3, 'Fette per confezione', 'n° 5', 10, 1),
(171, 5, 'Peso', '0.8 kg', 4.5, 0.8),
(172, 5, 'Peso', '1.0 kg', 5.5, 1),
(173, 5, 'Peso', '1.2 kg', 6.5, 1.2),
(174, 6, 'Peso', '500 gr', 15.9, 0.5),
(175, 6, 'Peso', '600 gr', 18.9, 0.6),
(176, 59, 'variante1', 'scelta1', 4.5, 0.4);

-- --------------------------------------------------------

--
-- Struttura della tabella `ricette`
--

CREATE TABLE `ricette` (
  `id` int(11) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `ingredienti` text NOT NULL,
  `procedimento` text NOT NULL,
  `descrizione` text NOT NULL,
  `meta_descrizione` varchar(160) NOT NULL,
  `immagine` varchar(1000) NOT NULL,
  `tempo` int(11) NOT NULL,
  `difficolta` varchar(45) NOT NULL,
  `creatore` varchar(75) NOT NULL,
  `data` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `views` int(11) NOT NULL DEFAULT '0',
  `categoria` tinyint(4) NOT NULL DEFAULT '1',
  `approvata` tinyint(4) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `ricette`
--

INSERT INTO `ricette` (`id`, `nome`, `ingredienti`, `procedimento`, `descrizione`, `meta_descrizione`, `immagine`, `tempo`, `difficolta`, `creatore`, `data`, `views`, `categoria`, `approvata`) VALUES
(58, 'Titolo', 'ingrediente 1 Quantità 1_Ingrediente 2 Quantità 2_', '<span style=\"color: rgb(0, 0, 0); font-family: \" open=\"\" sans\",=\"\" arial,=\"\" sans-serif;=\"\" text-align:=\"\" justify;\"=\"\">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</span><div><div style=\"text-align: justify;\"><font color=\"#000000\" face=\"Open Sans, Arial, sans-serif\"><br></font></div><div><span style=\"color: rgb(0, 0, 0); font-family: \" open=\"\" sans\",=\"\" arial,=\"\" sans-serif;=\"\" text-align:=\"\" justify;\"=\"\">Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?</span><span style=\"color: rgb(0, 0, 0); font-family: \" open=\"\" sans\",=\"\" arial,=\"\" sans-serif;=\"\" text-align:=\"\" justify;\"=\"\"><br></span></div><div><span style=\"color: rgb(0, 0, 0); font-family: \" open=\"\" sans\",=\"\" arial,=\"\" sans-serif;=\"\" text-align:=\"\" justify;\"=\"\"><br></span></div><blockquote style=\"margin: 0 0 0 40px; border: none; padding: 0px;\"><div><span style=\"color: rgb(0, 0, 0); font-family: \" open=\"\" sans\",=\"\" arial,=\"\" sans-serif;=\"\" text-align:=\"\" justify;\"=\"\">But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?</span></div></blockquote><font color=\"#000000\"><br></font><div><span style=\"color: rgb(0, 0, 0); font-family: \" open=\"\" sans\",=\"\" arial,=\"\" sans-serif;=\"\" text-align:=\"\" justify;\"=\"\">At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.</span><span style=\"color: rgb(0, 0, 0); font-family: \" open=\"\" sans\",=\"\" arial,=\"\" sans-serif;=\"\" text-align:=\"\" justify;\"=\"\"><br></span></div></div>', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, or avoids pleasure itself, because it is pleasure, but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias consequatur aut perferendis doloribus asperiores repellat.', 'Meta Descrizione dell\'idea', '../console/img/idee/nostre/58.png', 10, 'Difficile', 'New', '2020-02-12 15:35:13', 30, 1, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `statoSito`
--

CREATE TABLE `statoSito` (
  `stato` tinyint(4) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dump dei dati per la tabella `statoSito`
--

INSERT INTO `statoSito` (`stato`) VALUES
(1);

-- --------------------------------------------------------

--
-- Struttura della tabella `tags`
--

CREATE TABLE `tags` (
  `id` int(11) NOT NULL,
  `testo` varchar(100) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dump dei dati per la tabella `tags`
--

INSERT INTO `tags` (`id`, `testo`) VALUES
(107, 'Canederli'),
(109, 'Canederli al formaggio'),
(110, 'Canederli allo speck');

-- --------------------------------------------------------

--
-- Struttura della tabella `valutazione_blog`
--

CREATE TABLE `valutazione_blog` (
  `id_blog` int(11) NOT NULL,
  `rate5` int(11) NOT NULL DEFAULT '0',
  `rate4` int(11) NOT NULL DEFAULT '0',
  `rate3` int(11) NOT NULL DEFAULT '0',
  `rate2` int(11) NOT NULL DEFAULT '0',
  `rate1` int(11) NOT NULL DEFAULT '0',
  `rate05` int(11) NOT NULL DEFAULT '0',
  `rate15` int(11) NOT NULL DEFAULT '0',
  `rate25` int(11) NOT NULL DEFAULT '0',
  `rate35` int(11) NOT NULL DEFAULT '0',
  `rate45` int(11) NOT NULL DEFAULT '0',
  `value` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `valutazione_prod`
--

CREATE TABLE `valutazione_prod` (
  `id_prod` int(11) NOT NULL,
  `rate5` int(11) NOT NULL DEFAULT '0',
  `rate4` int(11) NOT NULL DEFAULT '0',
  `rate3` int(11) NOT NULL DEFAULT '0',
  `rate2` int(11) NOT NULL DEFAULT '0',
  `rate1` int(11) NOT NULL DEFAULT '0',
  `rate05` int(11) NOT NULL DEFAULT '0',
  `rate15` int(11) NOT NULL DEFAULT '0',
  `rate25` int(11) NOT NULL DEFAULT '0',
  `rate35` int(11) NOT NULL DEFAULT '0',
  `rate45` int(11) NOT NULL DEFAULT '0',
  `value` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `valutazione_prod`
--

INSERT INTO `valutazione_prod` (`id_prod`, `rate5`, `rate4`, `rate3`, `rate2`, `rate1`, `rate05`, `rate15`, `rate25`, `rate35`, `rate45`, `value`) VALUES
(1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5),
(2, 4, 1, 0, 0, 0, 0, 0, 1, 1, 0, 4.29),
(3, 0, 0, 0, 1, 0, 0, 0, 0, 1, 2, 3.63),
(4, 2, 0, 0, 0, 0, 0, 1, 0, 1, 0, 3.75),
(5, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 4.33),
(7, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 4.25),
(9, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2.5),
(10, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 4.5),
(11, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 4.9),
(12, 1, 0, 1, 0, 2, 0, 1, 0, 1, 0, 2.5),
(14, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 2.92),
(15, 2, 0, 1, 1, 11, 1, 0, 0, 1, 0, 1.76),
(16, 2, 0, 0, 0, 0, 1, 0, 0, 0, 0, 3.5),
(17, 0, 2, 2, 1, 1, 1, 1, 1, 1, 1, 3.45),
(18, 4, 2, 1, 2, 3, 1, 1, 0, 0, 1, 2.97),
(19, 2, 1, 1, 1, 0, 0, 0, 0, 1, 10, 4.22),
(20, 2, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2.83),
(23, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 3.5),
(24, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5),
(29, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 3.5),
(32, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 2.5),
(33, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5),
(36, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 4.5),
(43, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5),
(58, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 3.75);

-- --------------------------------------------------------

--
-- Struttura della tabella `valutazione_ricetta`
--

CREATE TABLE `valutazione_ricetta` (
  `id_ricetta` int(11) NOT NULL,
  `rate5` int(11) NOT NULL DEFAULT '0',
  `rate4` int(11) NOT NULL DEFAULT '0',
  `rate3` int(11) NOT NULL DEFAULT '0',
  `rate2` int(11) NOT NULL DEFAULT '0',
  `rate1` int(11) NOT NULL DEFAULT '0',
  `rate05` int(11) NOT NULL DEFAULT '0',
  `rate15` int(11) NOT NULL DEFAULT '0',
  `rate25` int(11) NOT NULL DEFAULT '0',
  `rate35` int(11) NOT NULL DEFAULT '0',
  `rate45` int(11) NOT NULL DEFAULT '0',
  `value` double NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `weeks_views`
--

CREATE TABLE `weeks_views` (
  `pagina` varchar(30) NOT NULL,
  `week` date NOT NULL,
  `views` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `weeks_views`
--

INSERT INTO `weeks_views` (`pagina`, `week`, `views`) VALUES
('blog', '2019-06-01', 2),
('bottega', '2019-06-01', 11),
('home', '2019-06-01', 10),
('idee', '2019-06-01', 2),
('macelleria', '2019-06-01', 2),
('ristorante', '2019-06-01', 1),
('blog', '2019-06-03', 1),
('bottega', '2019-06-03', 30),
('home', '2019-06-03', 3),
('idee', '2019-06-03', 3),
('macelleria', '2019-06-03', 3),
('ristorante', '2019-06-03', 2),
('blog', '2019-06-10', 103),
('bottega', '2019-06-10', 223),
('home', '2019-06-10', 27),
('idee', '2019-06-10', 46),
('macelleria', '2019-06-10', 6),
('blog', '2019-06-17', 602),
('bottega', '2019-06-17', 112),
('home', '2019-06-17', 11),
('idee', '2019-06-17', 189),
('macelleria', '2019-06-17', 7),
('ristorante', '2019-06-17', 6),
('blog', '2019-07-01', 164),
('bottega', '2019-07-01', 68),
('home', '2019-07-01', 58),
('idee', '2019-07-01', 75),
('macelleria', '2019-07-01', 13),
('ristorante', '2019-07-01', 36),
('bottega', '2019-07-01', 12),
('home', '2019-07-01', 1),
('bottega', '2019-07-01', 12),
('home', '2019-07-01', 1),
('bottega', '2019-07-01', 12),
('home', '2019-07-01', 1),
('bottega', '2019-07-01', 12),
('home', '2019-07-01', 1),
('bottega', '2019-07-01', 12),
('home', '2019-07-01', 1),
('bottega', '2019-07-01', 12),
('home', '2019-07-01', 1),
('bottega', '2019-07-01', 12),
('home', '2019-07-01', 1),
('bottega', '2019-07-01', 12),
('home', '2019-07-01', 1),
('bottega', '2019-07-01', 12),
('home', '2019-07-01', 1),
('bottega', '2019-07-01', 12),
('home', '2019-07-01', 1),
('bottega', '2019-07-01', 12),
('home', '2019-07-01', 1),
('bottega', '2019-07-01', 12),
('home', '2019-07-01', 1),
('bottega', '2019-07-01', 12),
('home', '2019-07-01', 1),
('bottega', '2019-07-01', 1),
('home', '2019-07-01', 1),
('idee', '2019-07-01', 1),
('macelleria', '2019-07-01', 1),
('ristorante', '2019-07-01', 1),
('bottega', '2019-07-01', 1),
('home', '2019-07-01', 1),
('idee', '2019-07-01', 1),
('macelleria', '2019-07-01', 1),
('bottega', '2019-07-01', 1),
('home', '2019-07-01', 1),
('idee', '2019-07-01', 1),
('macelleria', '2019-07-01', 1),
('bottega', '2019-07-01', 1),
('home', '2019-07-01', 1),
('idee', '2019-07-01', 1),
('macelleria', '2019-07-01', 1),
('bottega', '2019-07-01', 1),
('home', '2019-07-01', 1),
('idee', '2019-07-01', 1),
('macelleria', '2019-07-01', 1),
('bottega', '2019-07-01', 1),
('home', '2019-07-01', 1),
('idee', '2019-07-01', 1),
('macelleria', '2019-07-01', 1),
('bottega', '2019-07-01', 1),
('home', '2019-07-01', 1),
('idee', '2019-07-01', 1),
('macelleria', '2019-07-01', 1),
('bottega', '2019-07-01', 1),
('home', '2019-07-01', 1),
('idee', '2019-07-01', 1),
('macelleria', '2019-07-01', 1),
('bottega', '2019-07-01', 1),
('home', '2019-07-01', 1),
('idee', '2019-07-01', 1),
('macelleria', '2019-07-01', 1),
('bottega', '2019-07-01', 1),
('home', '2019-07-01', 1),
('idee', '2019-07-01', 1),
('macelleria', '2019-07-01', 1),
('bottega', '2019-07-01', 1),
('home', '2019-07-01', 1),
('idee', '2019-07-01', 1),
('macelleria', '2019-07-01', 1),
('bottega', '2019-07-01', 1),
('home', '2019-07-01', 1),
('idee', '2019-07-01', 1),
('macelleria', '2019-07-01', 1),
('bottega', '2019-07-01', 1),
('home', '2019-07-01', 1),
('idee', '2019-07-01', 1),
('macelleria', '2019-07-01', 1),
('bottega', '2019-07-01', 1),
('home', '2019-07-01', 1),
('idee', '2019-07-01', 1),
('macelleria', '2019-07-01', 1),
('bottega', '2019-07-01', 1),
('home', '2019-07-01', 1),
('idee', '2019-07-01', 1),
('macelleria', '2019-07-01', 1),
('bottega', '2019-07-08', 1),
('home', '2019-07-08', 1),
('idee', '2019-07-08', 1),
('macelleria', '2019-07-08', 1),
('bottega', '2019-07-08', 1),
('home', '2019-07-08', 1),
('idee', '2019-07-08', 1),
('macelleria', '2019-07-08', 1),
('bottega', '2019-07-08', 1),
('home', '2019-07-08', 1),
('idee', '2019-07-08', 1),
('macelleria', '2019-07-08', 1),
('bottega', '2019-07-08', 1),
('home', '2019-07-08', 1),
('idee', '2019-07-08', 1),
('macelleria', '2019-07-08', 1),
('bottega', '2019-07-08', 1),
('home', '2019-07-08', 1),
('idee', '2019-07-08', 1),
('macelleria', '2019-07-08', 1),
('bottega', '2019-07-08', 1),
('macelleria', '2019-07-08', 1),
('blog', '2019-07-08', 145),
('bottega', '2019-07-08', 36),
('home', '2019-07-08', 90),
('idee', '2019-07-08', 74),
('macelleria', '2019-07-08', 3),
('ristorante', '2019-07-08', 1),
('bottega', '2019-07-15', 80),
('home', '2019-07-15', 65),
('macelleria', '2019-07-15', 35),
('ristorante', '2019-07-15', 26),
('idee', '2019-07-15', 49),
('blog', '2019-07-15', 77),
('blog', '2019-07-15', 56),
('bottega', '2019-07-15', 14),
('home', '2019-07-15', 13),
('idee', '2019-07-15', 56),
('macelleria', '2019-07-15', 2),
('ristorante', '2019-07-15', 1),
('home', '2019-07-22', 12),
('bottega', '2019-07-22', 14),
('blog', '2019-07-22', 26),
('macelleria', '2019-07-22', 3),
('ristorante', '2019-07-22', 4),
('idee', '2019-07-22', 8),
('home', '2019-07-29', 3),
('bottega', '2019-07-29', 8),
('blog', '2019-07-29', 4),
('ristorante', '2019-07-29', 1),
('macelleria', '2019-07-29', 1),
('home', '2019-08-05', 3),
('bottega', '2019-08-05', 10),
('blog', '2019-08-05', 6),
('idee', '2019-08-05', 2),
('home', '2019-08-12', 1),
('bottega', '2019-08-12', 18),
('home', '2019-08-19', 1),
('bottega', '2019-08-19', 15),
('home', '2019-08-26', 17),
('bottega', '2019-08-26', 62),
('blog', '2019-08-26', 3),
('macelleria', '2019-08-26', 3),
('idee', '2019-08-26', 2),
('home', '2019-09-02', 41),
('bottega', '2019-09-02', 85),
('macelleria', '2019-09-02', 7),
('blog', '2019-09-02', 22),
('ristorante', '2019-09-02', 5),
('idee', '2019-09-02', 11),
('home', '2019-09-09', 15),
('bottega', '2019-09-09', 53),
('ristorante', '2019-09-09', 2),
('blog', '2019-09-09', 5),
('idee', '2019-09-09', 6),
('macelleria', '2019-09-09', 1),
('home', '2019-09-16', 39),
('bottega', '2019-09-16', 66),
('blog', '2019-09-16', 9),
('macelleria', '2019-09-16', 6),
('ristorante', '2019-09-16', 9),
('idee', '2019-09-16', 9),
('home', '2019-09-23', 4),
('ristorante', '2019-09-23', 2),
('macelleria', '2019-09-23', 1),
('bottega', '2019-09-23', 7),
('home', '2019-12-30', 39),
('bottega', '2019-12-30', 22),
('idee', '2019-12-30', 17),
('ristorante', '2019-12-30', 2),
('blog', '2019-12-30', 27),
('macelleria', '2019-12-30', 1),
('home', '2020-01-06', 50),
('bottega', '2020-01-06', 86),
('macelleria', '2020-01-06', 8),
('ristorante', '2020-01-06', 11),
('blog', '2020-01-06', 47),
('idee', '2020-01-06', 21),
('home', '2020-01-13', 39),
('ristorante', '2020-01-13', 7),
('bottega', '2020-01-13', 81),
('macelleria', '2020-01-13', 6),
('blog', '2020-01-13', 11),
('idee', '2020-01-13', 4),
('home', '2020-01-20', 9),
('bottega', '2020-01-20', 20),
('blog', '2020-01-20', 4),
('macelleria', '2020-01-20', 4),
('ristorante', '2020-01-20', 4),
('idee', '2020-01-20', 3),
('home', '2020-01-27', 8),
('bottega', '2020-01-27', 7),
('home', '2020-02-03', 10),
('ristorante', '2020-02-03', 9),
('macelleria', '2020-02-03', 6),
('bottega', '2020-02-03', 11),
('home', '2020-02-10', 56),
('ristorante', '2020-02-10', 23),
('macelleria', '2020-02-10', 17),
('bottega', '2020-02-10', 80),
('blog', '2020-02-10', 26),
('idee', '2020-02-10', 40),
('home', '2020-02-17', 9),
('blog', '2020-02-17', 12),
('idee', '2020-02-17', 10),
('macelleria', '2020-02-17', 2),
('ristorante', '2020-02-17', 4),
('bottega', '2020-02-17', 16),
('home', '2020-02-24', 15),
('bottega', '2020-02-24', 22),
('ristorante', '2020-02-24', 7),
('macelleria', '2020-02-24', 7),
('blog', '2020-02-24', 10),
('idee', '2020-02-24', 4),
('home', '2020-03-02', 16),
('blog', '2020-03-02', 8),
('bottega', '2020-03-02', 17),
('ristorante', '2020-03-02', 5),
('macelleria', '2020-03-02', 3),
('idee', '2020-03-02', 6),
('home', '2020-03-09', 4),
('bottega', '2020-03-09', 15),
('blog', '2020-03-09', 4),
('home', '2020-03-23', 1),
('home', '2020-03-30', 1),
('bottega', '2020-03-30', 3),
('home', '2020-09-07', 1),
('bottega', '2020-09-07', 4),
('idee', '2020-09-07', 2),
('blog', '2020-09-07', 1),
('ristorante', '2020-09-07', 1),
('macelleria', '2020-09-07', 1),
('home', '2020-11-16', 1),
('bottega', '2020-11-16', 4),
('macelleria', '2020-11-16', 1),
('ristorante', '2020-11-16', 1),
('blog', '2020-11-16', 2),
('idee', '2020-11-16', 2),
('home', '2022-10-10', 1),
('bottega', '2022-10-10', 5),
('ristorante', '2022-10-10', 1),
('blog', '2022-10-10', 2),
('macelleria', '2022-10-10', 1);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `blog`
--
ALTER TABLE `blog`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cat_fk_idx` (`categoria`),
  ADD KEY `rate_FKY` (`id`);

--
-- Indici per le tabelle `blog_cat`
--
ALTER TABLE `blog_cat`
  ADD PRIMARY KEY (`id_cat`),
  ADD KEY `cat_blog_FKY` (`nome`);

--
-- Indici per le tabelle `blog_commenti`
--
ALTER TABLE `blog_commenti`
  ADD PRIMARY KEY (`id_commento`),
  ADD KEY `blog_FKY_idx` (`id_blog`);

--
-- Indici per le tabelle `blog_tags`
--
ALTER TABLE `blog_tags`
  ADD PRIMARY KEY (`id`),
  ADD KEY `tags_FKY_idx` (`tag`),
  ADD KEY `blog_tags_FKY_idx` (`blog`);

--
-- Indici per le tabelle `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`id`),
  ADD KEY `categoria_FKY_idx` (`nome`);

--
-- Indici per le tabelle `commenti`
--
ALTER TABLE `commenti`
  ADD PRIMARY KEY (`id`),
  ADD KEY `commento_ricetta_FKY_idx` (`id_ricetta`);

--
-- Indici per le tabelle `email_sub`
--
ALTER TABLE `email_sub`
  ADD PRIMARY KEY (`email`);

--
-- Indici per le tabelle `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `notifiche`
--
ALTER TABLE `notifiche`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `orderSum`
--
ALTER TABLE `orderSum`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `prodotti_idea`
--
ALTER TABLE `prodotti_idea`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `prodotto`
--
ALTER TABLE `prodotto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `categoria_FKY_idx` (`categoria`);

--
-- Indici per le tabelle `products_variants`
--
ALTER TABLE `products_variants`
  ADD PRIMARY KEY (`id`),
  ADD KEY `product_variant_FKY_idx` (`idProd`);

--
-- Indici per le tabelle `ricette`
--
ALTER TABLE `ricette`
  ADD PRIMARY KEY (`id`),
  ADD KEY `rate_FKY` (`id`);

--
-- Indici per le tabelle `statoSito`
--
ALTER TABLE `statoSito`
  ADD PRIMARY KEY (`stato`);

--
-- Indici per le tabelle `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `testo_UNIQUE` (`testo`),
  ADD KEY `id_tag_FKY` (`id`);

--
-- Indici per le tabelle `valutazione_blog`
--
ALTER TABLE `valutazione_blog`
  ADD PRIMARY KEY (`id_blog`);

--
-- Indici per le tabelle `valutazione_prod`
--
ALTER TABLE `valutazione_prod`
  ADD PRIMARY KEY (`id_prod`);

--
-- Indici per le tabelle `valutazione_ricetta`
--
ALTER TABLE `valutazione_ricetta`
  ADD PRIMARY KEY (`id_ricetta`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `blog`
--
ALTER TABLE `blog`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=164;
--
-- AUTO_INCREMENT per la tabella `blog_cat`
--
ALTER TABLE `blog_cat`
  MODIFY `id_cat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=268;
--
-- AUTO_INCREMENT per la tabella `blog_commenti`
--
ALTER TABLE `blog_commenti`
  MODIFY `id_commento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT per la tabella `blog_tags`
--
ALTER TABLE `blog_tags`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=601;
--
-- AUTO_INCREMENT per la tabella `categorie`
--
ALTER TABLE `categorie`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;
--
-- AUTO_INCREMENT per la tabella `commenti`
--
ALTER TABLE `commenti`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT per la tabella `menu`
--
ALTER TABLE `menu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT per la tabella `notifiche`
--
ALTER TABLE `notifiche`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT per la tabella `prodotti_idea`
--
ALTER TABLE `prodotti_idea`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;
--
-- AUTO_INCREMENT per la tabella `prodotto`
--
ALTER TABLE `prodotto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;
--
-- AUTO_INCREMENT per la tabella `products_variants`
--
ALTER TABLE `products_variants`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=177;
--
-- AUTO_INCREMENT per la tabella `ricette`
--
ALTER TABLE `ricette`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;
--
-- AUTO_INCREMENT per la tabella `tags`
--
ALTER TABLE `tags`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;
--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `blog`
--
ALTER TABLE `blog`
  ADD CONSTRAINT `cat_blog_FKY` FOREIGN KEY (`categoria`) REFERENCES `blog_cat` (`nome`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Limiti per la tabella `blog_commenti`
--
ALTER TABLE `blog_commenti`
  ADD CONSTRAINT `blog_FKY` FOREIGN KEY (`id_blog`) REFERENCES `blog` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `blog_tags`
--
ALTER TABLE `blog_tags`
  ADD CONSTRAINT `blog_tags_FKY` FOREIGN KEY (`blog`) REFERENCES `blog` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tags_FKY` FOREIGN KEY (`tag`) REFERENCES `tags` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `commenti`
--
ALTER TABLE `commenti`
  ADD CONSTRAINT `commento_ricetta_FKY` FOREIGN KEY (`id_ricetta`) REFERENCES `ricette` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `prodotto`
--
ALTER TABLE `prodotto`
  ADD CONSTRAINT `categoria_FKY_idx` FOREIGN KEY (`categoria`) REFERENCES `categorie` (`nome`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Limiti per la tabella `products_variants`
--
ALTER TABLE `products_variants`
  ADD CONSTRAINT `product_variant_FKY` FOREIGN KEY (`idProd`) REFERENCES `prodotto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `valutazione_blog`
--
ALTER TABLE `valutazione_blog`
  ADD CONSTRAINT `rate_blog_FKY` FOREIGN KEY (`id_blog`) REFERENCES `blog` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `valutazione_prod`
--
ALTER TABLE `valutazione_prod`
  ADD CONSTRAINT `product_FKY` FOREIGN KEY (`id_prod`) REFERENCES `prodotto` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `valutazione_ricetta`
--
ALTER TABLE `valutazione_ricetta`
  ADD CONSTRAINT `rating_Ricetta_FKY` FOREIGN KEY (`id_ricetta`) REFERENCES `ricette` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

DELIMITER $$
--
-- Eventi
--
CREATE DEFINER=`user`@`%` EVENT `copy_week_views` ON SCHEDULE EVERY 1 WEEK STARTS '2019-06-17 00:00:00' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN

    INSERT INTO `weeks_views`(pagina, week, views) 

    SELECT pagina, CURRENT_DATE - INTERVAL 7 DAY AS date, SUM(`views`) AS views FROM curr_week_views group by pagina;

    DELETE FROM `curr_week_views`;
    
END$$

DELIMITER ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
