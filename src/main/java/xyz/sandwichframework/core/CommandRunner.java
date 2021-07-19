package xyz.sandwichframework.core;

import java.lang.reflect.Method;
import xyz.sandwichframework.models.CommandPacket;
/**
 * Clase que ejecuta los comandos.
 * Class that runs the commands.
 * @author Juancho
 * @version 1.2
 */
class CommandRunner implements Runnable{
	/**
	 * Metodo a ser ejecutado({@link xyz.sandwichframework.models.ModelCommand#getAction()}).
	 * Method wich will be executed({@link xyz.sandwichframework.models.ModelCommand#getAction()}).
	 */
	private Method method;
	/**
	 * Paquete que se enviará al comando.
	 * Packet wich will be send to the command.
	 */
	private CommandPacket packet;
	/**
	 * Constructor de CommandRunner.
	 * Constructor of CommandRunner.
	 */
	protected CommandRunner(Method method, CommandPacket packet) {
		this.method=method;
		this.packet=packet;
	}
	/**
	 * Heredado de Runnable
	 * Inherited from Runnable.
	 */
	@Override
	public void run() {
		try {
			method.invoke(null, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
