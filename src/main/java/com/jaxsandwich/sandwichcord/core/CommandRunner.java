/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
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
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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
	 * [ES] Metodo a ser ejecutado({@link com.jaxsandwich.sandwichcord.models.CommandObject#getAction()}).<br>
	 * [EN] Method wich will be executed({@link com.jaxsandwich.sandwichcord.models.CommandObject#getAction()}).
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
