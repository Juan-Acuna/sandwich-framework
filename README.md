[framework]:http://jaxsandwich.com/sandwichcord/  
[actual]:http://jaxsandwich.com/sandwichcord/v0.7.0/javadoc/  
[web-jax]:http://jaxsandwich.com/  
[jda]:https://ci.dv8tion.net/job/JDA/javadoc/  
> ## AVISO
> Tanto el framework como la documentación estan aún en desarrollo y podrían variar en su contenido, contener bugs no reportados, o encontrarse incompleta(en el caso de la documentación). Por favor tener esto en consideración al momento de su uso.

# Sandwichcord Framework
### Un framework simple en Java 8 para el desarrollo de bots para Discord.

Si eres nuevo en el desarrollo de bots para Discord, puede que esto te interese.  
Este framework contiene lo necesario para desarrollar un bot basico pero útil, simplificando algunas cosas.

##### **Este framework depende de la librería [JDA 4][jda]*
##### **Sandwich Framework ayuda con la gestion de categorias, comandos, opciones/parametros, idioma entre otros, mas se recomienda estudiar la [documentación de la librería JDA 4][jda] para un correcto desarrollo.*
###### **Actualmente solo cuenta con soporte para inglés y español*

## Instalación | Versión actual: [0.7.0-SNAPSHOT(ALPHA)][actual]
### Insatalación Maven
Se debe reemplazar *VERSION* por la versión que se desea utilizar(se recomienda la más reciente).  
Dentro de las etiquetas ```<dependencies>``` en pom.xml:
```xml
<dependency>
  <groupId>com.jaxsandwich</groupId>
  <artifactId>sandwichcord-framework</artifactId>
  <version>0.7.0-SNAPSHOT</version>
</dependency>
```
Dentro de las etiquetas```<repositories>``` en pom.xml:  
```xml
<repository>
  <snapshots>
    <enabled>true</enabled>
  </snapshots>
  <id>github</id>
  <url>https://maven.pkg.github.com/juan-acuna/*</url>
</repository>
```

  
Si no usas un gestor de dependencias, puedes descargar el archivo "sandwichcord-framework-VERSION.jar" e importarlo en el proyecto java para crear un Bot de Discord.

## Instrucciones de implementación
  
### 1.- Crear una clase Bot.
Java es un lenguaje de programación orientado a objetos(POO) y siguiendo estas buenas practicas, todas las interacciones dentro del framework se basan en objetos.  
Una de las clases más importantes es la clase Bot y para crear un bot de Discord debes crear una clase que herede de esta.  
Para este ejemplo básico, comenzaremos importando una serie de clases
```java
import com.jaxsandwich.sandwichcord.core.Bot;
import com.jaxsandwich.sandwichcord.core.util.Language;
```
Después, creamos una clase que herede Bot, por ejemplo la clase MyBot
```java
public class MyBot extends Bot{

  public MyBot(String token) {
      super(token, Language.ES);
      this.setSingleGuildMode(true);
  }
  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    try {
      runCommand(event);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }
}
```
El constructor de la clase Bot requiere dos parametros: el Token de Discord y el idioma por defecto del Bot, el cual corresponde al valor del enum Language.   
El metodo abstracto "onMessageReceived" que se esta sobreescribiendo se ejecutará cada vez que se reciba un mensaje. en este metodo se debe manejar la logica para ejecutar comandos. Una vez que, dentro del metodo, se desea ejecutar el comando, se llama a la función "runCommand" pasando el evento como parametro.  
El comportamiento del bot puede ser personalizado en su constructor, he aquí un ejemplo:
```java
public MyBot(String token) {
  super(token, Language.ES);
  this.setPrefix("my!");
  this.setOptionsPrefix("-");
  this.setAutoHelpCommandEnabled(true);
  this.setHideNSFWCategory(true);
  this.setIgnoreSelfCommands(true);
  this.setTypingOnCommand(true);
  this.setIgnoreWebHook(true);
}
```
Para saber mas que hacen estas y otras configuraciones, revise la [documentación][framework].  
  
Con lo anterior, ya tienes un bot de Discord!... Un bot que aun no hace nada, ya que aún no conoe ningun comando.
  
### 2.- Crear los comandos
Los comandos definen las capacidades del bot, son todo lo que este puede hacer y son la parte mas interesante de programar(y la que más tiempo lleva). Los comandos tambien son objetos, pero a diferencia del bot, no necesitas crear una clase por cada uno.  
Para crear un comando es necesario crear una categoría. Esto ayuda a mantener un orden no solo durante el desarrollo del bot, sino que tambien para los usuarios de Discord que lo usen.  
Para crear un comando, lo primero es importar una serie de anotaciones y la clase CommandPacket. Estas son necesarias para definir un comando
```java
import com.jaxsandwich.sandwichcord.annotations.Category;
import com.jaxsandwich.sandwichcord.annotations.Command;
import com.jaxsandwich.sandwichcord.models.CommandPacket;
```
La clase CommandPacket es la encargada de comunicar el mensaje que recibe el bot ya procesado con el comando.  
A continuación, un ejemplo de comando, recordando que estos deben pertenecer a una categoría obligatoriamente (requisito del framework, no de Discord). Todos los comandos deben ser metodos estáticos y recibir como único parametro un objeto tipo CommandPacket, de lo contrario serán ignorados.
```java
@Category
public class MyCategory{
  
  @Command(id="HelloWorld", desc="Sends a Hello World! message", alias={"hw","hello"})
  public static void helloWorldCommand(CommandPacket packet){
    packet.sendMessage("Hello Wolrd!").queue();
  }
}
```
La clase CommandPacket ofrece una serie de metodos utiles para programar comandos, ademas de contener toda la información necesaria, ya que dentro de esta se encuentra el evento que desencadenó el comando, incluyendo información del servidor, del usuario que invocó el comando, entre otros.
Para más información, revise la documentación al respecto.
  
### 3.- Iniciar la ejecución del Bot
El bot ya está listo, es un poco básico, pero ya cumple con los componentes requeridos para funcionar. Ahora solo queda encenderlo.  
Para ejecutar el bot, se necesita crear una clase con el metodo estático main, como en cualquier otro programa Java, donde comenzaremos la ejecución del bot con la ayuda de la clase BotRunner.  
He aquí un ejemplo de la clase que contiene el metodo main, considerando que la clase MyBot y ClasePrincipal se encuentran en el mismo package:
```java
import com.jaxsandwich.sandwichcord.core.BotRunner;

public class ClasePrincipal {

	public static void main(String[] args) throws Exception{
		String discord_token = "Enter your Discord Token here!";
		MyBot bot = new MyBot(discord_token);
		BotRunner.singleBotModeInit(bot);
		bot.runBot();
	}
}
```
Primero se inicializa el bot con el metodo estático BotRunner.singleBotModeInit(bot), para que el framework analice el bot y configure todo lo necesario para funcionar. Luego se puede iniciar la ejecución del bot con el metodo bot.runBot(). Esto último permite hacer configuraciones al objeto JDABuilder(vea la [documentación de JDA4][jda]) contenido en el bot, ya que una vez este comienza su ejecución, estas no pueden ser aplicadas.  
  
### 4.- ¡Probar y seguir mejorando!
El bot ya está listo y corriendo, solo queda invitar este bot a tu servidor de Discord y seguir agregandole caracteristicas.


## Ejemplo de bot desarrollado con Sandwich Framework
Un ejemplo de bot desarrollado con este framework es el bot multiuso llamado [Jax Sandwich](https://github.com/Juan-Acuna/jax-sndwch-bot)