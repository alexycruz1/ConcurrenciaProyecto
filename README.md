# Word count / Tokenizer

## Descripción
El objetivo de este trabajo es aprender cómo usar la aplicación de Map/Reduce en un sistema de archivos distribuidos de Hadoop, desarrollando conocimientos básicos sobre cómo encontrar conjuntos de elementos frecuentes para realizar posteriores análisis para minería de datos. 

Para poder llevar a cabo el proyecto se deberá desarrollar dos diferentes aplicaciones: 
* WordCount
* Frequencyanalysis 

Se proporcionará un archivo de 1 semana de consulta en Google como ejemplo para encontrar conjuntos de elementos frecuentes. El archivo de consulta deberá ser analizado, reducido y procesado utilizando las aplicaciones mencionadas anteriormente. Debido al gran tamaño del dataset y la gran cantidad de consultas que se procesarán, se sugiere un dataset mínimo de 5000 (Minimum Support). 

## Referencias Adicionales para el Proyecto:	
Se deberá realizar el procesamiento del dataset sobre una implementación de Hadoop (a realizar por el estudiante).

### Requisitos de Programación
Se sugiere desarrollar el proyecto en Java.


## Aplicación

### WordCount

En la elaboración de este proyecto, se deberá entregar la aplicación WordCount.  Esta aplicación básicamente recibe un archivo de texto y devuelve otro archivo que enumera cada palabra encontrada en el archivo de entrada y la cantidad de veces que dicha palabra apareció.

La aplicación deberá usarse dos veces en dicho proyecto: 
* Para contar la cantidad de veces que cada palabra aparece en el archivo de consulta.
* Para contar la cantidad de veces que cada conjunto de dos elementos aparece en el archivo de consulta. 


### Frequency Analysis 

Se deberá desarrollar una aplicación para poder lograr los objetivos del proyecto y encontrar los conjuntos de dos elementos frecuentes en el archivo de consulta. La aplicación deberá contener tres clases:
* Main: deberá ejecutar todo el proceso
* DataPreprocessor: deberá pre-procesar el archivo de consulta y reducirlo para facilitar el manejo.
* FrequencyAnalyzer: deberá de encontrar los conjuntos de elementos uno-frecuente y dos-frecuente en el archivo de consulta.


## Método de Evaluación

* El programa entregado deberá compilar. Fallar en este ítem generará un 100% de penalidad en la evaluación.
* Sobre programas entregados, habrá créditos parciales de acuerdo a la ponderación establecida en esta sección.
* Implementación Hadoop (40%).
* Realizar las validaciones y pre procesos en el dataset (10%).
* Por cada Segmentation Fault que aparezca en la revisión se multiplicará la nota por 90%.
* Se evaluará la utilización de estándares de programación.
* Se evaluará la utilización y desarrollo de las estructuras de datos / librerías / sistemas de archivos especificadas en este proyecto. No utilizar, desarrollar e implementar estas estructuras para que cumplan la función para la cual fueron definidas, será penalizado (20% por cada una).
* Se tomará en cuenta la creatividad. (10% extra sobre el total del proyecto), Conclusiones y recomendaciones sobre hallazgos interesantes.
* Se tomará en cuenta en la evaluación la participación de todos los miembros del equipo verificando en el Wiki y en GitHub
* Cada grupo deberá realizar tres críticas puntuales y constructivas de la presentación realizada por el resto de compañeros. Puntos de mejora.
* Ponderación (en base a 100%).


## Entregas Tardías

* No se aceptarán proyectos después de la fecha de entrega.
* No se aceptarán reclamos sobre el proyecto si no se presentan a revisión en la fecha estipulada.

## Fase Inicial de Revisión de Proyecto

* De acuerdo con lo estipulado en el sílabo, se tendrá revisión preliminar del proyecto.
* 20% de la nota total.
* Presentar un avance concreto
* Implementación de Hadoop & Mapreduce

## Método de Presentación

Cada grupo deberá realizar una pequeña presentación (15 – 20 mins) mostrando todas las etapas de desarrollo de su proyecto:
* Introducción
* Presentación de las aplicaciones desarrolladas
* Mostrar paso por paso la ejecución de su aplicación
* Resultados y conclusiones
* Hallazgos interesantes

# Entregables
* [Informe de proyecto - DOC]()
* [Presentacion de proyecto]()