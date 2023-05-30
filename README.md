# Simulazione Artista da Strada

Questo progetto è una simulazione di un artista da strada che esegue ritratti. Utilizza i semafori come meccanismo di sincronizzazione per gestire l'accesso dei clienti alle sedie disponibili e alla zona di lavoro dell'artista. L'obiettivo è creare una simulazione realistica in cui i clienti si siedono, aspettano il loro turno e vengono serviti dall'artista. Nel caso in cui un cliente debba aspettare più di 3 secondi senza essersi seduto, se ne andrà insoddisfatto.

### Algoritmo della classe **Main**

L'algoritmo del programma può essere suddiviso nei seguenti passi:

1. Inizializzazione: Vengono creati i semafori `semaphore` e `mutex` per gestire l'accesso alle risorse condivise.
2. Input del numero di clienti: Viene richiesto all'utente di inserire il numero di clienti che parteciperanno alla simulazione.
3. Iterazione sui clienti: Per ciascun cliente, vengono eseguiti i seguenti passi:
    - Generazione del tempo di arrivo: Viene generato casualmente un tempo di arrivo tra `tempoMinArrivoClienti` e `tempoMaxArrivoClienti`.
4. Attesa: Il thread del cliente viene sospeso per il tempo di arrivo generato.
5. Creazione del cliente: Viene creato un oggetto cliente con l'ID corrispondente.
6. Avvio del thread cliente: Il thread del cliente viene avviato.
7. Stampa del messaggio di arrivo: Viene stampato un messaggio per indicare che il cliente è arrivato.
8. Fine del programma: Una volta che tutti i clienti hanno completato l'esecuzione, il programma termina.

### Algoritmo della classe **cliente**
La classe cliente rappresenta un singolo cliente che desidera farsi fare un ritratto dall'artista. Di seguito viene descritto l'algoritmo di funzionamento della classe:

1. Inizializzazione: All'istanziazione di un oggetto cliente, vengono impostati l'ID del cliente e il tempo necessario per eseguire il ritratto. Il tempo di esecuzione del ritratto viene generato casualmente tra tempoMinRitratto e tempoMaxRitratto.

2. Acquisizione del permesso: Il cliente tenta di acquisire un permesso dal semaforo `semaphore` utilizzando il metodo `tryAcquire()` con un tempo massimo di attesa di `tempoAttesaClienti` secondi. Se il permesso viene ottenuto entro il tempo di attesa, il cliente procede al passo successivo. Altrimenti, il cliente rinuncia e viene stampato un messaggio appropriato.

3. Accesso alla zona di lavoro: Dopo aver acquisito il permesso, il cliente può accedere alla zona di lavoro dell'artista. Prima di farlo, acquisisce il mutex `mutex` utilizzando il metodo `acquire()`. Ciò garantisce l'accesso esclusivo alla zona di lavoro e impedisce ad altri clienti di accedervi contemporaneamente.

4. Esecuzione del ritratto: Una volta acquisito il mutex, viene stampato un messaggio per indicare che il cliente si è seduto e l'artista sta dipingendo il suo ritratto. Il thread del cliente viene sospeso per il tempo necessario per eseguire il ritratto utilizzando il metodo `sleep()`.

5. Rilascio del mutex: Dopo aver completato il ritratto, il cliente rilascia il mutex utilizzando il metodo `release()`. Ciò permette ad altri clienti di accedere alla zona di lavoro dell'artista.

6. Completamento del ritratto: Viene stampato un messaggio per indicare che l'artista ha completato il ritratto del cliente. Successivamente, il cliente rilascia il permesso acquisito precedentemente dal semaforo semaphore utilizzando il metodo `release()`.

7. Gestione della rinuncia: Se il cliente non riesce ad acquisire il permesso entro il tempo di attesa, viene stampato un messaggio per indicare che il cliente se ne è andato.

8. Questo algoritmo garantisce che ogni cliente che riesce ad acquisire un permesso possa accedere alla zona di lavoro dell'artista, eseguire il ritratto e successivamente rilasciare le risorse acquisite. In caso di rinuncia da parte di un cliente, l'artista può procedere a servire gli altri clienti senza causare stallo o starvation.

### Sincronizzazione dei processi
La sincronizzazione dei processi viene gestita utilizzando i semafori. In particolare, vengono utilizzati due semafori:

- `semaphore`: Rappresenta il numero massimo di sedie disponibili per i clienti. Viene inizializzato con il valore 4, indicando che ci sono inizialmente 4 sedie disponibili. Ogni volta che un cliente si siede, acquisisce un permesso dal semaforo. Quando il cliente ha terminato, rilascia il permesso.

- `mutex`: Assicura l'accesso esclusivo alla sezione critica dell'artista, ovvero quando un cliente viene servito. Viene utilizzato per evitare che più clienti accedano contemporaneamente alla zona di lavoro dell'artista. Quando un cliente si siede, acquisisce il mutex per impedire l'accesso di altri clienti. Quando ha terminato, rilascia il mutex.
Attraverso l'utilizzo di questi semafori, si garantisce che solo un numero limitato di clienti possa sedersi contemporaneamente e che solo un cliente alla volta possa accedere alla zona di lavoro dell'artista.

### Inquadramento rispetto ai problemi di stallo e starvation

Il programma implementa una soluzione che evita sia il problema dello stallo che quello della starvation.

Problema dello **stallo**: Non si verifica stallo perché il numero di permessi nel semaforo semaphore corrisponde al numero di sedie disponibili. Quando un cliente si siede, acquisisce un permesso dal semaforo, e quando ha terminato, rilascia il permesso. In questo modo, anche se tutti i clienti si siedono contemporaneamente, non si verifica un'interblocco tra di loro.

Problema della **starvation**: Non si verifica starvation perché i clienti vengono serviti in ordine di arrivo. Ogni cliente attende il proprio turno per accedere alla zona di lavoro dell'artista. Se un cliente non riesce a ottenere un permesso dal semaforo semaphore entro il tempo di attesa (tempoAttesaClienti), rinuncia e si allontana. Ciò evita che un cliente rimanga in attesa indefinita e garantisce che tutti i clienti abbiano l'opportunità di farsi fare il ritratto.
