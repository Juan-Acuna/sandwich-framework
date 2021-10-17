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

package com.jaxsandwich.sandwichcord.models;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.jaxsandwich.sandwichcord.models.actionable.ActionableType;
import com.jaxsandwich.sandwichcord.models.actionable.IActionable;
import com.jaxsandwich.sandwichcord.models.packets.CommandPacket;
import com.jaxsandwich.sandwichcord.models.packets.ReplyablePacket;
import com.jaxsandwich.sandwichcord.models.packets.ResponseCommandPacket;
import com.jaxsandwich.sandwichcord.models.packets.SlashCommandPacket;
/**
 * Representa un comando de respuesta (comandos que se activan por otros comandos a la espera de una respuesta).
 * Represents an response command (commands which are activated by others commands and wait for an answer).
 * @author Juancho
 * @version 2.1
 * @since 0.3.0
 */
public class ResponseCommandObject extends CommandBase implements Comparable<ResponseCommandObject>, IActionable<ReplyablePacket<?>> {
	private static Map<String, ResponseCommandObject> cont = Collections.synchronizedMap(new HashMap<String, ResponseCommandObject>());
	Method _each = null;
	Method _after = null;
	Method _no = null;
	Method _finally = null;
	public static ResponseCommandObject find(String id) {
		return cont.get(id.toLowerCase());
	}
	public static ArrayList<ResponseCommandObject> getAsList() {
		ArrayList<ResponseCommandObject> l = new ArrayList<ResponseCommandObject>(cont.values());
		Collections.sort(l);
		return l;
	}
	public static int getExtraCommandCount() {
		return cont.size();
	}
	public static void compute(ResponseCommandObject xcmd) {
		ResponseCommandObject m = cont.get(xcmd.id);
		if(m==null){
			cont.put(xcmd.id.toLowerCase(), xcmd);
			m = xcmd;
		}
		//System.out.println("comando computado: "+m.getName());
		if(xcmd.action!=null)
			m.setAction(xcmd.action);
		if(xcmd._each!=null)
			m.setWaitingExecution(xcmd._each);
		if(xcmd._after!=null)
			m.setSuccessExecution(xcmd._after);
		if(xcmd._finally!=null)
			m.setFinallyExecution(xcmd._finally);
		if(xcmd._no!=null)
			m.setFailedExecution(xcmd._no);
		
		/*if(m.action!=null) {
			System.out.println("accion: "+m.action);
		}
		if(m._each!=null) {
			System.out.println("each: "+m._each);
		}
		if(m._after!=null) {
			System.out.println("after: "+m._after);
		}
		if(m._finally!=null) {
			System.out.println("finally: "+m._finally);
		}
		if(m._no!=null) {
			System.out.println("no: "+m._no);
		}*/
		
	}
	
	public ResponseCommandObject() {
		super();
	}
	public ResponseCommandObject(String id, Method action) {
		super(id);
		this.action = action;
		this.path = "*RESPONSE_COMMAND*/" + this.id;
	}
	public void forceId(String name) {
		this.id=name;
	}
	public void setWaitingExecution(Method e) {
		_each=e;
	}
	public void setSuccessExecution(Method a) {
		_after=a;
	}
	public void setFailedExecution(Method n) {
		_no=n;
	}
	public void setFinallyExecution(Method f) {
		_finally=f;
	}
	public void runWaitingExecution(ResponseCommandPacket packet) {
		if(_each==null)
			return;
		try {
			_each.invoke(null, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void runSuccessExecution(ResponseCommandPacket packet) {
		if(_after==null)
			return;
		try {
			_after.invoke(null, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void runFailedExecution(ResponseCommandPacket packet) {
		if(_no==null)
			return;
		try {
			_no.invoke(null, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void runFinallyExecution(ResponseCommandPacket packet) {
		if(_finally==null)
			return;
		try {
			_finally.invoke(null, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int compareTo(ResponseCommandObject o) {
		return id.compareTo(o.id);
	}
	@Override
	public void execute(ReplyablePacket<?> packet) throws Exception {
		if(packet instanceof CommandPacket) {
			this.action.invoke(null, (CommandPacket)packet);
		}else if(packet instanceof ResponseCommandPacket){
			this.action.invoke(null, (ResponseCommandPacket)packet);
		}else if(packet instanceof SlashCommandPacket){
			this.action.invoke(null, (SlashCommandPacket)packet);
		}else {
			this.action.invoke(null, packet);
		}
	}
	@Override
	public ActionableType getActionableType() {
		return ActionableType.RESPONSE_COMMAND;
	}
}
