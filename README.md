[framework]:http://jaxsandwich.com/sandwichcord/latest
[actual]:http://jaxsandwich.com/sandwichcord/v0.7.0/javadoc/
[web-jax]:http://jaxsandwich.com/
[jda-github]:https://github.com/DV8FromTheWorld/JDA
[jda]:https://ci.dv8tion.net/job/JDA/javadoc/
[gradle]:http://jaxsandwich.com/sandwichcord#instalacion-gradle

[BOT]:http://jaxsandwich.com/sandwichcord/latest?q=/javadoc/com/jaxsandwich/sandwichcord/core/Bot.html
[BOTRUNNER]:http://jaxsandwich.com/sandwichcord/latest?q=/javadoc/com/jaxsandwich/sandwichcord/core/BotRunner.html
[COMMANDPACKET]:http://jaxsandwich.com/sandwichcord/latest?q=/javadoc/com/jaxsandwich/sandwichcord/models/CommandPacket.html
[MSG-RECEIVED]:http://jaxsandwich.com/sandwichcord/latest?q=/javadoc/com/jaxsandwich/sandwichcord/core/Bot.html#onMessageReceived(net.dv8tion.jda.api.events.message.MessageReceivedEvent)
[RUN-CMD]:http://jaxsandwich.com/sandwichcord/latest?q=/javadoc/com/jaxsandwich/sandwichcord/core/Bot.html#runCommand(net.dv8tion.jda.api.events.message.MessageReceivedEvent)
[RUN-BOT]:http://jaxsandwich.com/sandwichcord/latest?q=/javadoc/com/jaxsandwich/sandwichcord/core/Bot.html#runBot()
[LANG]:http://jaxsandwich.com/sandwichcord/latest?q=/javadoc/com/jaxsandwich/sandwichcord/core/util/Language.html
[INIT-BOT]:http://jaxsandwich.com/sandwichcord/latest?q=/javadoc/com/jaxsandwich/sandwichcord/core/BotRunner.html#singleBotModeInit(com.jaxsandwich.sandwichcord.core.Bot)

[JDABUILDER]:https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/JDABuilder.html

> ## AVISO
> Tanto el framework como la documentación estan aún en desarrollo y podrían variar en su contenido, contener bugs no reportados, o encontrarse incompleta en el caso de la documentación. Por favor tener esto en consideración al momento de su uso.

# Sandwichcord Framework
### Un framework simple en Java 8 para el desarrollo de bots para Discord.

Si eres nuevo en el desarrollo de bots para Discord, puede que esto te interese.  
Este framework contiene lo necesario para desarrollar un bot básico pero útil, simplificando algunas cosas.

#### **Este framework depende de la librería [JDA 4][jda-github]*
##### **Sandwich Framework ayuda con la gestion de categorias, comandos, opciones/parametros, idioma entre otros, mas se debe estudiar la [documentación de la librería JDA 4][jda] para un correcto desarrollo.*
###### **Actualmente solo cuenta con soporte para inglés y español*

## Instalación | Versión actual: [0.7.0-SNAPSHOT(ALPHA)][actual]
### Importando en Maven
Se debe reemplazar *VERSION* por la versión que se desea utilizar(se recomienda la más reciente).  
Dentro de las etiquetas ```<dependencies>``` en pom.xml:
```xml
<dependency>
  <groupId>com.jaxsandwich</groupId>
  <artifactId>sandwichcord-framework</artifactId>
  <version>VERSION</version>
</dependency>
```
Dentro de las etiquetas ```<repositories>``` en pom.xml:  
```xml
<repository>
  <snapshots>
    <enabled>true</enabled>
  </snapshots>
  <id>github</id>
  <url>https://maven.pkg.github.com/juan-acuna/*</url>
</repository>
```
### Importando en Gradle
Actualmente no cuenta con soporte a Gradle, por lo que si lo utilizas, tendrás que realizar una importación local del archivo `sandwichcord-framework-VERSION.jar` siguiendo las [instucciones para importar archivos .jar en proyecto Gradle][gradle](este metodo no solo funciona con este framework).
  
### Importando en proyecto Java 
Si no usas alguna de las herramientas anteriores, puedes descargar el archivo `sandwichcord-framework-VERSION.jar` e importarlo en el proyecto java para crear un Bot de Discord.

## Instrucciones de implementación
  
### 1.- Crear una clase Bot.
Java es un lenguaje de programación orientado a objetos(POO) y siguiendo estas buenas practicas, todas las interacciones dentro del framework se basan en objetos.  
Una de las clases más importantes es la clase [Bot][BOT] y para crear un bot de Discord debes crear una clase que herede de esta.  
Para este ejemplo básico, comenzaremos importando una serie de clases
```java
import com.jaxsandwich.sandwichcord.core.Bot;
import com.jaxsandwich.sandwichcord.core.util.Language;
```
Después, creamos una clase que herede [Bot][BOT], por ejemplo la clase MyBot
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
El constructor de la clase [Bot][BOT] requiere dos parametros: el Token de Discord y el idioma por defecto del bot, el cual corresponde al valor del enum [Language][LANG].   
El metodo abstracto [Bot.onMessageReceived(MessageReceivedEvent)][MSG-RECEIVED] que se esta sobreescribiendo se ejecutará cada vez que se reciba un mensaje. En este metodo se debe manejar la lógica para ejecutar comandos. Una vez que, dentro del metodo, se desea ejecutar el comando, se llama a la función [Bot.runCommand(MessageReceivedEvent)][RUN-CMD] pasando el evento como parámetro.  
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
Para saber más que hacen estas y otras configuraciones, revise la documentación de la clase [Bot][BOT].  
  
Con lo anterior, ¡ya tienes un bot de Discord!... Un bot que aún no hace nada, ya que aún no conoce ningún comando.
  
### 2.- Crear los comandos
Los comandos definen las capacidades del bot, son todo lo que este puede hacer y son la parte mas interesante de programar(y la que más tiempo lleva). Los comandos también son objetos, pero a diferencia del bot, no necesitas crear una clase por cada uno.  
Para crear un comando es necesario crear una categoría. Esto ayuda a mantener un orden no solo durante el desarrollo del bot, sino que también para los usuarios de Discord que lo usen.  
Lo primero es importar dos anotaciones y la clase [CommandPacket][COMMANDPACKET].  
```java
import com.jaxsandwich.sandwichcord.annotations.Category;
import com.jaxsandwich.sandwichcord.annotations.Command;
import com.jaxsandwich.sandwichcord.models.CommandPacket;
```
Comenzamos por crear la clase MyCategory, que será nuestra categoría. Para ello debemos indicarselo al framework anotando la clase con la anotación [Category][A-CAT]. Luego en ella crearemos un metodo que será nuestro comando, también indicandolo mediante la anotación [Command][A-CMD].
La clase [CommandPacket][COMMANDPACKET] es la encargada de comunicar el mensaje que recibe el bot ya procesado con el comando.  
A continuación, un ejemplo de comando, recordando que estos deben pertenecer a una categoría obligatoriamente (requisito del framework, no de Discord). Todos los comandos deben ser metodos estáticos y recibir como único parámetro un objeto tipo [CommandPacket][COMMANDPACKET], de lo contrario serán ignorados.
```java
@Category
public class MyCategory{
  
  @Command(id="HelloWorld", desc="Sends a Hello World! message", alias={"hw","hello"})
  public static void helloWorldCommand(CommandPacket packet){
    packet.sendMessage("Hello Wolrd!").queue();
  }
}
```
La clase [CommandPacket][COMMANDPACKET] ofrece una serie de metodos utiles para programar comandos, además de contener toda la información necesaria, ya que dentro de esta se encuentra el evento que desencadenó el comando, incluyendo información del servidor, el usuario que invocó el comando, entre otros.
Para más información, revise la [documentación][framework] al respecto.
  
### 3.- Iniciar la ejecución del Bot
El bot ya está listo, es un poco básico, pero ya cumple con los componentes requeridos para funcionar. Ahora solo queda encenderlo.  
Para ejecutar el bot, se necesita crear una clase con el metodo estático main, como en cualquier otro programa Java, donde comenzaremos la ejecución del bot con la ayuda de la clase [BotRunner][BOTRUNNER].  
He aquí un ejemplo de la clase que contiene el metodo main, considerando que la clase MyBot.java y ClasePrincipal.java se encuentran en el mismo package:
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
Primero se inicializa el bot con el metodo estático [BotRunner.singleBotModeInit(Bot)][INIT-BOT], para que el framework analice el bot y configure todo lo necesario para funcionar. Luego se puede iniciar la ejecución del bot con el metodo [bot.runBot()][RUN-BOT]. Esto último permite hacer configuraciones al objeto [JDABuilder][JDABUILDER] contenido en el bot, ya que una vez este comienza su ejecución, estas no pueden ser aplicadas.  
  
### 4.- ¡Probar y seguir mejorando!
El bot ya está listo y corriendo, solo queda invitarlo a tu servidor de Discord y seguir agregándole características.


## Ejemplo de bot desarrollado con Sandwichcord Framework
Un ejemplo de bot desarrollado con este framework es el bot multiuso llamado [Jax Sandwich](https://github.com/Juan-Acuna/jax-sndwch-bot)