> ## AVISO
> Tanto el framework como la documentación estan aún en desarrollo y podrían variar en su contenido, contener bugs no reportados, o encontrarse incompleta(en el caso de la documentación). Por favor tener esto en consideración al momento de su uso.

# Sandwichcord Framework
### Un framework simple en Java 8 para el desarrollo de bots para Discord.

Si eres nuevo en el desarrollo de bots para Discord, puede que esto te interese.  
Este framework contiene lo necesario para desarrollar un bot basico pero útil, simplificando algunas cosas.

##### **Este framework depende de la librería [JDA 4](https://github.com/DV8FromTheWorld/JDA)*
##### **Sandwich Framework ayuda con la gestion de categorias, comandos, opciones/parametros, idioma entre otros, mas se recomienda estudiar la [documentación de la librería JDA 4](https://ci.dv8tion.net/job/JDA/javadoc/) para un correcto desarrollo.*
###### **Actualmente solo cuenta con soporte para inglés y español*

## Instrucciones de implementación

### 1.- Instalación
Para instalar el framework basta con descargar el archivo "sandwichcord-framework-VERSION.jar" e importarlo en el proyecto java para crear un Bot de Discord.  
Si usas gestores de dependencias, como Apache Maven o Gradle, debes seguir los pasos correspondientes según el gestor para poder importar el archivo localmente.
##### **Se planifica su futura publicación en repositorios Maven/Gradle a través de GitHub Packages.*
### 2.- Crear una clase Bot.
Java es un lenguaje de programación orientado a objetos(POO) y siguiendo estas buenas practicas, todas las interacciones dentro del framework se basan en objetos.  
Una de las clases más importantes es la clase Bot y para crear un bot de Discord debes crear una clase que herede de esta.  
Para este ejemplo, comenzaremos importando una serie de clases
```java
import com.jaxsandwich.sandwichcord.core.Bot;
import com.jaxsandwich.sandwichcord.models.discord.GuildConfig;
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
Para saber mas que hacen estas y otras configuraciones, revise la documentación.  
### 3.- Crear los comandos
Los comandos tambien son objetos, pero a diferencia del bot, no necesitas crear una clase por cada uno.  
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
    packet.getMessageReceivedEvent().getMessageChannel().sendMessage("Hello Wolrd!").queue();
  }

}
```
[...]


## Ejemplo de bot desarrollado con Sandwich Framework
Un ejemplo de bot desarrollado con este framework es el bot multiuso llamado [Jax Sandwich](https://github.com/Juan-Acuna/jax-sndwch-bot)