# PARTE TEORICA

### Arquitecturas de UI: MVP, MVVM y MVI

#### MVVM

##### ¿En qué consiste esta arquitectura?
Es un patrón de arquitectura cuyo propósito principal es la separación de responsabilidades mediante una clara distinción de roles en cada una de sus capas.

- La vista muestra la interfaz de usuario e informa al resto de capas sobre las acciones del usuario

    Es el componente con el que el usuario interactúa. Muestra la interfaz y normalmente se representa como Activities o Fragments. Su rol principal es observar uno o más ViewModels para obtener la información que necesita y actualizar la interfaz.

    También informa a los ViewModels sobre las acciones del usuario. Esta comunicación se realiza mediante Observables, usando librerías como RxJava, LiveData o DataBinding.

- Los ViewModel exponen la información a las vistas

    Recupera la información necesaria de el modelo, aplica las operaciones necesarias y expone a las vistas los datos que necesiten.

- El modelo recupera información de la fuente de datos y la expone a los ViewModels.

    También deberían recibir de los ViewModels cualquier evento que implique realizar una operación CRUD del backend.

    En Android, normalmente son data classes de Kotlin que representan la información que se obtiene de la fuente de datos. Deber haber un modelo por cada objeto del dominio de la aplicación, de tal manera que se respete el principio de única responsabilidad. 

La principal diferencia respecto a otros patrones de arquitectura es que los ViewModels no deberían contener ninguna referencia a las vistas, de tal manera que un ViewModel puede ser utilizado por todas las vistas que lo necesiten. Los ViewModels también son responsables de exponer los eventos que las vistas deben observar.

##### ¿Cuáles son sus ventajas?
- Con esta arquitectura, ya que toda la manipulación de datos se encuentra en los ViewModels y, por tanto, esta lógica se encuentra desacoplada de las vistas, realizar test unitarios es más sencillo.
- Además, MVVM ofrece una mejor separación de responsabilidades que otras arquitecturas, evitando los "fat controllers", que acaban siendo exageradamente grandes y difíciles de mantener.

##### ¿Qué inconvenientes tiene?
Aplicar esta arquitectura en una aplicación con UI muy sencilla podría llegar a considerarse sobreingeniería debido a la complejidad innecesaria que añade al proyecto.


#### MVP

##### ¿En qué consiste esta arquitectura?
En este patrón de arquitectura, el modelo y la vista no conocen de la existencia del otro, por lo tanto hay una mayor separación de responsabilidades. Esto facilita la testabilidad de los Presenter, que no extienden de ninguna clase de Android.

- Modelo: La capa de datos, responsable de la lógica de negocio

    Se encarga de recuperar datos, almacenarlos y cambiarlos.

- Vista: Punto de entrada de la aplicación. Muestra la interfaz y escucha las acciones del usuario. Normalmente una activity o fragmento. Implementa una ViewInterface que hace de contrato.

    Se encarga de ocultar y mostrar Views, de gestionar la navegación a otras actividades a través de intents y de escuchar las interacciones del sistema operativo y el input del usuario.

- Presenter: Intermediario entre el modelo y la vista. Gestiona la lógica de presentación. Implementa una PresenterInterface, que también sirve de contrato. Tiene una referencia a una instancia de ViewInterface para facilitar el testeo y el mockeo de la vista.

    Todo el código que no gestione la UI o cualquier otra lógica específica de Android se debería sacar de la vista y mover a la clase Presenter. 

##### ¿Cuáles son sus ventajas?
Las ventajas que proporciona esta arquitectura es la separación de responsabilidades, y la facilidad que aporta para testear los Modelos y Presenters. 

##### ¿Qué inconvenientes tiene?
Hay que ser cuidadoso al trabajar con esta arquitectura. Al destruir una Activity, hay que asegurarse de que no queden referencias al presenter en otra clase que continúe en memoria. Si es necesario que NO se destruya, se puede tratar de guardar su estado en onSaveInstanceState o usar loaders para evitar problemas.


#### MVI

##### ¿En qué consiste esta arquitectura?
Es uno de los patrones de arquitectura más recientes que existen para Android.

- Modelo: Representa un estado. Deberían ser inmutables para asegurar un flujo de datos unidireccional entre ellos y el resto de capas de la arquitectura, de manera que solamente haya una fuente de la verdad.

    Solo va a haber un único estado de toda la aplicación que se gestiona en un único sitio. La inmutabilidad de los objetos garantiza que no habrá efectos secundarios como objetos modificando el modelo desde diferentes hilos.

- Intent: Representa una intención o deseo por parte del usuario de realizar una acción. Para cada acción, la vista recibirá un intent, que será observado por el Presenter y traducido a un nuevo estado de los modelos. Puede haber tantos como número de acciones pueda realizar el usuario. No debemos confundirlos con los intents que utilizamos para lanzar Activities.
- View: Como en MVP, se representan mediante interfaces que actúan como contratos, que se implementan en uno o más Actividades o Fragmentos. Tienden a tener un único método render que acepta un estado para renderizar en la pantalla y diferentes métodos intent() como Observables que responden a las acciones del usuario.

El presenter tiene solo una salida: el estado de la vista, que tendrá un método render que reciba como argumento el estado actual de la app.

##### ¿Cuáles son sus ventajas?
Las principales ventajas de esta arquitectura son que se obtiene un flujo de datos cíclico y unidireccional en la aplicación, que la aplicación será consistente durante todo el ciclo de vida de las vistas involucradas; y la fiabilidad que proporcionan los modelos inmutables, que nos dan la certeza de que nunca se producirán efectos secundarios.

##### ¿Qué inconvenientes tiene?
La curva de aprendizaje de este patrón tiende a ser mayor ya que hay que tener mucho conocimiento base sobre programación reactiva, multi-hilo y RxJava. Por lo tanto, otras arquitecturas como MVC o MVP pueden ser más sencillas de abordar para developers con menos experiencia.



---

### Testing

#### ¿Qué tipo de tests se deberían incluir en cada parte de la pirámide de test? Pon ejemplos de librerías de testing para cada una de las partes.

Según la pirámide de tests, los tests de nuestras aplicaciones se dividen en tres grupos:

  - Las pruebas de nivel inferior, donde se encuentran los tests unitarios.
  Nos permiten probar el comportamiento de partes muy pequeñas de código de normalmente un mismo componente (sustituyendo sus dependencias por mocks o stubs) para que cuando integremos esta pieza con el resto del código, tengamos la certeza de que funciona como esperamos. 
  Lo único que se necesita para desarrollar tests unitarios es Junit, el framework de testing por excelencia de Java. Estos tests no se van a ejecutar en un emulador o dispositivo Android, sino que estamos probando el comportamiento de nuestros componentes de forma aislada al resto del sistema, por lo que no necesitamos ninguna librería de Android de testeo adicional para desarrollarlos.
  - Las pruebas de nivel intermedio, donde se encuentran los tests de integración.
  Estos tests nos ayudan a comprobar que los componentes de nuestra aplicación interactúan como esperamos, así como con otras partes del propio framework de Android.
    Una de las herramientas más populares para desarrollar este tipo de tests en Android es Roboelectric, que ejecuta nuestros tests dentro de una caja de pruebas sin emuladores o dispositivos físicos.
  - Las pruebas de nivel superior, donde se encuentran los tests end to end o E2E que testean la interfaz.
  Son tests de integración y de la interfaz de usuario que nos permiten simular la interacción del usuario con la interfaz de nuestra aplicación y comprobar que ésta reacciona de la forma en la que esperamos.
  Estos tests son los más costosos de desarrollar y deberíamos tener una cantidad muy reducida de ellos. Como cabe esperar, son los tests que más tardan en ejecutarse y, además, son difíciles de mantener a lo largo del tiempo ya que un pequeño cambio en la interfaz puede hacer que nuestros tests E2E estén en rojo.




#### ¿Por qué los desarrolladores deben centrarse sobre todo en los Unido Tests?
Estos tests son los que realmente aportan más valor, pues son los menos costosos de desarrollar (son pequeños y relativamente sencillos de escribir) y son los que más rápido se ejecutan, ya que no dependen de ningún emulador o dispositivo físico para ejecutarse. Tener todas las piezas de nuestro código testeadas nos aporta la seguridad de que el conjunto formado por ellas, en este caso nuestra aplicación, también funcionará de la forma esperada.

---

### Inyección de dependencias

#### Explica en qué consiste y por qué nos ayuda a mejorar nuestro código.
La inyección de dependencias consiste en que las clases que dependan de otras, reciban estas instancias del exterior en lugar de inicializarlas dentro de la propia clase. Lo más común es que reciban dichas dependencias como parámetros de su constructor.
Los beneficios que esto nos aporta son los siguientes:
  - Respetamos el principio de responsabilidad única: una clase debe encargarse de crear únicamente sus propios objetos.
  - Obtenemos un código más desacoplado, y por tanto más fácilmente testeable, ya que a la hora de escribir los tests para una clase A concreta, podremos pasarle desde el exterior sus dependencias B y C mockeadas de tal manera que nos aseguramos de que estamos únicamente testeando el comportamiento de A.
  - Podremos testear el comportamiento de A, incluso si aún no hemos desarrollado dependencias B (suponiendo que formen parte del dominio de nuestra aplicación) o si queremos abstraernos de los detalles de implementación de C (suponiendo que se trata de una librería de terceros).
  - Podremos reutilizar nuestro código más fácilmente gracias a la flexibilidad que nos aporta.


#### Explica cómo se hace para aplicar inyección de dependencias de forma manual a un proyecto (sin utilizar librerías externas).

Para aplicar inyección de dependencias de forma manual en un proyecto, debemos:
  1.  En la clase en la que vayamos a inyectar sus dependencias, debemos crear un constructor que reciba tantos argumentos como dependencias necesite, y dentro del constructor asignarlo a los atributos de la clase correspondiente. Si fuera necesario realizar alguna lógica o conversión adicional, podríamos considerar crear un método de factoría.
  2. Desde cada punto del código en el que queramos crear una nueva instancia de nuestra clase, debemos previamente instanciar cada una de las dependencias que ésta necesita.
  3. Para crear cada nueva instancia de nuestra clase, utilizamos el constructor o método de factoría que creamos en el paso 1 y le pasamos cada una de las dependencias que acabamos de instanciar en el paso 2 en el mismo orden.
  
  

---

### Análisis de código estático

#### Ejecuta Lint al proyecto final mediante Analyze> Inspect Code. Haz una lista con 5 warnings/errores y explica de qué problema te avisan y cómo corregirlos.

Tras ejecutar el lint de Android Studio en todo el proyecto, aparece una lista con diferentes elementos desplegables que representan una categoría diferente de avisos. Por ejemplo Android, Kotlin... Todos son warnings y no hay ningún error. Estos son 5 de los warnings que aparecen:

-  Android - Correctness: Obsolete Grade Dependency
    
    Este warning nos avisa de que hay una versión más reciente para una dependencia que se encuentra en nuestro fichero build.grade. Para corregirlo, podemos hacer doble click sobre el warning. En los IDEs de IDEA, y en Android Studio pues está basado en IntelliJ, si hacemos ctrl+Enter sobre un warning, podremos ver cómo nos sugiere el IDE corregirlo. En este caso, nos sugiere la última versión de la librería que tengamos desactualizada.
    
- Android - Accessibility: Image without content description

    Este warning nos avisa de que tenemos vistas de imágenes (ImageViews) sin el atributo content description, que son una ayuda de accesibilidad para los usuarios que necesiten utilizar lectores de pantalla u otras herramientas similares. Para corregirlo, de nuevo, podemos hacer ctrl+Enter en el ImageView para que el IDE nos añada el atributo, y solamente tendremos que añadir el texto que describe la imagen.
    
- Android - Performance: Overdraw: Painting regions more than once

    Este warning nos avisa de que la View padre, en este caso NestedScrollView, tiene un atributo que pinta el background de blanco, y que la theme que aplica también lo hace. Para corregirlo, podemos eliminar el atributo de la vista.

- Kotlin - Redundant Constructors: Remove empty primary constructor

    Este warning nos indica que el constructor vacío en `class StreamsActivity() : AppCompatActivity() {` no es necesario. Para corregiro, podemos quitar los paréntesis que hay después del nombre de la clase.
    
- Kotlin - Unnecesary local variable: Variable used only in following return and should be inlined

    Este warning nos indica que una variable local solo se utiliza para devolverla en la línea siguiente y que podemos aplicar el Refactor Inline (ctrl+alt+N) para aplicarlo. Sin embargo, hay ocasiones en las que es posible que no queramos aplicar esta sugerencia si el nombre de la variable ayuda a entender el código aportando semántica al mismo.
