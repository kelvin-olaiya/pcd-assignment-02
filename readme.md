# PCD Assignment 02

## Introduzione

L'obiettivo è quello di affrontare il problema descritto nell'assignment 1, utilizzando quattro diversi approcci di programmazione _asincrona_, descritti di seguito.

Si è deciso di uniformare l'interfaccia degli approcci, sfruttando opportune astrazioni, sia per la versione _CLI_, che per la versione _GUI_.

```java
public interface SourceAnalyzer {
    Future<Report> getReport(Directory directory);
    ObservableReport analyzeSources(Directory directory);
}
```

Nel caso di `getReport(Directory directory)`, verrà restituita una _Future_, che sarà completata, in modo asincrono, con il risultato dell'analisi. 
Questo permette, in tutti i casi, di sottomettere la computazione e successivamente attendere il risultato, bloccando opportunamente il Thread che vuole ottenere il risultato.

Un approccio alternativo è quello di aggiungere un _Runnable_ al metodo, che verrà eseguito quando la computazione sarà completata.
Questo permette di non dover attendere un risultato, ma il codice specificato, sarà eseguito al termine del calcolo delle statistiche (e.g una stampa a video del risultato).


## Executor



## Virtual Threads



## Event Loop

L'approccio a _Event Loop_ è stato implementato utilizzando la libreria [Vertx](https://vertx.io).

## Reactive

