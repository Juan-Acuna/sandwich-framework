package com.jaxsandwich.sandwichcord.core;

import java.lang.reflect.Method;

import com.jaxsandwich.sandwichcord.models.CommandPacket;
/**
 * [ES] Clase que ejecuta los comandos.<br>
 * [EN] Class that runs the commands.
 * @author Juancho
 * @version 1.3
 */
class CommandRunner implements Runnable{
	/**
	 * [ES] Metodo a ser ejecutado({@link com.jaxsandwich.sandwichcord.models.ModelCommand#getAction()}).<br>
	 * [EN] Method wich will be executed({@link com.jaxsandwich.sandwichcord.models.ModelCommand#getAction()}).
	 */
	private Method method;
	/**
	 * [ES] Paquete que se enviará al comando.<br>
	 * [EN] Packet wich will be send to the command.
	 */
	private CommandPacket packet;
	/**
	 * [ES] Constructor de CommandRunner.<br>
	 * [EN] Constructor of CommandRunner.
	 */
	protected CommandRunner(Method method, CommandPacket packet) {
		this.method=method;
		this.packet=packet;
	}
	@Override
	public void run() {
		try {
			method.invoke(null, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
