<!--
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright 2021 Juan Acuña                                                   *
 *                                                                             *
 * Licensed under the Apache License, Version 2.0 (the "License");             *
 * you may not use this file except in compliance with the License.            *
 * You may obtain a copy of the License at                                     *
 *                                                                             *
 *     http://www.apache.org/licenses/LICENSE-2.0                              *
 *                                                                             *
 * Unless required by applicable law or agreed to in writing, software         *
 * distributed under the License is distributed on an "AS IS" BASIS,           *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.    *
 * See the License for the specific language governing permissions and         *
 * limitations under the License.                                              *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 -->
 
[framework]:http://sandwichcord.jaxsandwich.com/
[actual]:http://docs.jaxsandwich.com/sandwichcord/latest
[estable]:http://docs.jaxsandwich.com/sandwichcord/v0.9.x/javadoc/
[web-jax]:http://jaxsandwich.com/
[jda-github]:https://github.com/DV8FromTheWorld/JDA
[jda]:https://ci.dv8tion.net/job/JDA/javadoc/
[gradle]:https://docs.gradle.org/current/userguide/declaring_dependencies.html#sub:file_dependencies
[maven]:https://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html  

[PACKET]:http://docs.jaxsandwich.com/sandwichcord/latest?legacy=true&q=/javadoc/com/jaxsandwich/sandwichcord/models/packets/Packet.html  

[BOT]:http://docs.jaxsandwich.com/sandwichcord/latest?legacy=true&q=/javadoc/com/jaxsandwich/sandwichcord/core/Bot.html

[BOTRUNNER]:http://docs.jaxsandwich.com/sandwichcord/latest?legacy=true&q=/javadoc/com/jaxsandwich/sandwichcord/core/BotRunner.html

[COMMANDPACKET]:http://docs.jaxsandwich.com/sandwichcord/latest?legacy=true&q=/javadoc/com/jaxsandwich/sandwichcord/models/CommandPacket.html

[MSG-RECEIVED]:http://docs.jaxsandwich.com/sandwichcord/latest?legacy=true&q=/javadoc/com/jaxsandwich/sandwichcord/core/Bot.html#onMessageReceived(net.dv8tion.jda.api.events.message.MessageReceivedEvent)

[RUN-CMD]:http://docs.jaxsandwich.com/sandwichcord/latest?legacy=true&q=/javadoc/com/jaxsandwich/sandwichcord/core/Bot.html#runCommand(net.dv8tion.jda.api.events.message.MessageReceivedEvent)

[RUN-BOT]:http://docs.jaxsandwich.com/sandwichcord/latest?legacy=true&q=/javadoc/com/jaxsandwich/sandwichcord/core/Bot.html#runBot()

[LANG]:http://docs.jaxsandwich.com/sandwichcord/latest?legacy=true&q=/javadoc/com/jaxsandwich/sandwichcord/core/util/Language.html

[INIT-BOT]:http://docs.jaxsandwich.com/sandwichcord/latest?legacy=true&q=/javadoc/com/jaxsandwich/sandwichcord/core/BotRunner.html#singleBotModeInit(com.jaxsandwich.sandwichcord.core.Bot)

[A-CAT]:http://docs.jaxsandwich.com/sandwichcord/latest?legacy=true&q=/javadoc/com/jaxsandwich/sandwichcord/annotations/Category.html  

[A-CMD]:http://docs.jaxsandwich.com/sandwichcord/latest?legacy=true&q=/javadoc/com/jaxsandwich/sandwichcord/annotations/Command.html  

[JDABUILDER]:https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/JDABuilder.html

> ## AVISO
> Tanto el framework como la documentación estan aún en desarrollo y podrían variar en su contenido, contener bugs no reportados, o encontrarse incompleta en el caso de la documentación. Por favor tener esto en consideración al momento de su uso.

# [Sandwichcord Framework][framework]
### Un framework simple en Java 8 para el desarrollo de bots para Discord.

Si eres nuevo en el desarrollo de bots para Discord, puede que esto te interese.  
Este framework contiene lo necesario para desarrollar un bot básico pero útil, simplificando algunas cosas.

#### **Este framework depende de la librería [JDA 4][jda-github]*
##### **Sandwich Framework ayuda con la gestion de categorias, comandos, opciones/parametros, idioma entre otros, mas se debe estudiar la [documentación de la librería JDA 4][jda] para un correcto desarrollo.*
###### **Actualmente solo cuenta con soporte para inglés y español*

## Instalación  
###### Versión estable: [0.9.0][estable](BETA)  
<!--###### Versión más reciente: [0.9.0][actual](BETA)-->  
### Importando en Maven  
Actualmente no cuenta con soporte a Maven, por lo que si lo utilizas, tendrás que realizar una importación local del archivo `sandwichcord-framework-VERSION.jar` siguiendo las [instucciones para importar archivos .jar en proyecto Maven][maven](en inglés).  

<!--Se debe reemplazar *VERSION* por la versión que se desea utilizar(se recomienda la más reciente).  
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
  <url>https://maven.pkg.github.com/sandwichbotsteam/*</url>
</repository>
```
-->  

### Importando en Gradle
Actualmente no cuenta con soporte a Gradle, por lo que si lo utilizas, tendrás que realizar una importación local del archivo `sandwichcord-framework-VERSION.jar` siguiendo las [instucciones para importar archivos .jar en proyecto Gradle][gradle](en inglés).
  
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
}
```
El constructor de la clase [Bot][BOT] requiere dos parametros: el Token de Discord y el idioma por defecto del bot, el cual corresponde al valor del enum [Language][LANG].  
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
  
Con lo anterior, ¡ya tienes un bot de Discord!... Un bot que aún no hace nada, ya que no conoce ningún comando.
  
### 2.- Crear los comandos
Los comandos definen las capacidades del bot, son todo lo que este puede hacer y son la parte mas interesante de programar(y la que más tiempo lleva). Los comandos también son objetos, pero a diferencia del bot, no necesitas crear una clase por cada uno.  
Para crear un comando es necesario crear una categoría. Esto ayuda a mantener un orden no solo durante el desarrollo del bot, sino que también para los usuarios de Discord que lo usen.  
Lo primero es importar dos anotaciones y la clase [CommandPacket][COMMANDPACKET].  
```java
import com.jaxsandwich.sandwichcord.annotations.Category;
import com.jaxsandwich.sandwichcord.annotations.Command;
import com.jaxsandwich.sandwichcord.models.packets.CommandPacket;
```
Comenzamos por crear la clase MyCategory, que será nuestra categoría. Para ello debemos indicarselo al framework anotando la clase con la anotación [Category][A-CAT]. Luego en ella crearemos un metodo que será nuestro comando, también indicándolo mediante la anotación [Command][A-CMD].
La clase [CommandPacket][COMMANDPACKET] es la encargada de comunicar el mensaje que recibe el bot ya procesado con el comando. Puedes usar también alguna de las clases superiores en gerarquía, pero por ahora bastará con esta.  
A continuación, un ejemplo de comando, recordando que estos deben pertenecer a una categoría obligatoriamente (requisito del framework, no de Discord). Todos los comandos deben ser metodos estáticos y recibir como único parámetro un objeto tipo [Packet][PACKET] o subclases de este, de lo contrario serán ignorados.
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
Para más información, revise la [documentación][actual] al respecto.
  
### 3.- Iniciar la ejecución del Bot
El bot ya está listo, es un poco básico, pero ya cumple con los componentes requeridos para funcionar. Ahora solo queda encenderlo.  
Para ejecutar el bot, se necesita crear una clase con el metodo estático main, como en cualquier otro programa Java, donde comenzaremos la ejecución del bot.  
He aquí un ejemplo de la clase que contiene el metodo main, considerando que la clase MyBot.java y ClasePrincipal.java se encuentran en el mismo package:
```java
public class ClasePrincipal {

	public static void main(String[] args) throws Exception{
		String discord_token = "Enter your Discord Token here!";
		MyBot bot = new MyBot(discord_token);
		bot.runBot();
	}
}
```
Primero se instancia la clase MyBot creada anteriormente y luego se inicia la ejecución del bot con el metodo [bot.runBot()][RUN-BOT]. Esto último permite hacer configuraciones al objeto [JDABuilder][JDABUILDER] contenido en el bot, ya que una vez este comienza su ejecución, estas no pueden ser aplicadas.  
  
### 4.- ¡Probar y seguir mejorando!
El bot ya está listo y corriendo, solo queda invitarlo a tu servidor de Discord y seguir agregándole características.

<!--
## Ejemplo de bot desarrollado con Sandwichcord Framework
Un ejemplo de bot desarrollado con este framework es el bot multiuso llamado [Jax Sandwich](https://github.com/Juan-Acuna/jax-sndwch-bot)(no disponible en estos momentos).
-->