# eMovies

1. ¿En qué consiste el principio de responsabilidad única? ¿Cuál es su propósito?

El principio de responsabilidad única sugiere que una clase, módulo o función debería tener sólo un motivo para modificarse. Con esto se consigue,o se intenta, evitar que se creen "súper clases" que sean enormes y contengan muchas funcionalidades, lo que afectaría la escalabilidad de la aplicación (ya que una clase con muchas funcionalidades difícilmente pueda ser reutilizada) y dificultaría el mantenimiento a futuro.

2. ¿Qué características tiene, según su opinión, un “buen” código o código limpio?

En mi opinión, un buen código debe estar enfocado en la mantenibilidad, escalabilidad y reusabilidad del mismo, pudiendose tomar como una buena base los principios SOLID junto con algunos patrones de diseño populares como pueden ser por ejemplo el patrón Observer, que busca desacoplar la lógica para mostrar los datos delegando esa responsabilidad a los observers, o el patrón Strategy, que permite separar la lógica de negocio de la implementación en sí. Por otro lado, debemos considerar que idealmente no se deberían seguir estar reglas ciegamente, si no que debemos centrarnos en el scope de la aplicación y la utilización que va a tener ese código en particular, ya que de no tenerlo en cuenta podríamos terminar complicando innecesariamente nuestra aplicación, afectando de esta forma la posibilidad de entender con facilidad que es lo que estamos intentando hacer.

3. Detalla cómo harías todo aquello que no hayas llegado a completar.

En particular, me hubiese gustado llegar a implementar casos de uso como intermediarios del acceso del ViewModel al repositorio, declarando uno para cada uno de los casos que identifico (GetTopRatedMovies, GetUpcomingMovies, GetRecommendedMovies y GetMovieDetails), pudiendo incluir dentro de GetRecommendedMovies la lógica para la muestra de las películas filtradas según correspondiera. (Implementación: una clase por cada caso de uso, sobrecargando el operador invoke con la lógica de la llamada al repositorio y la lógica a aplicar en caso de ser necesario, como el caso del filtrado).
