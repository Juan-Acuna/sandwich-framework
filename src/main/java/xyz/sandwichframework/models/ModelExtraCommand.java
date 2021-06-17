package xyz.sandwichframework.models;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dv8tion.jda.api.entities.MessageChannel;
/**
 * Representa un Comando extra (comandos que se activan por otros comandos a la espera de una respuesta).
 * Represents an Extra command (commands which are activated by others[commands] and wait for an answer).
 * @author Juancho
 * @version 1.0
 */
public class ModelExtraCommand {
	private static Map<String, ModelExtraCommand> xcont = (Map<String, ModelExtraCommand>) Collections.synchronizedMap(new HashMap<String, ModelExtraCommand>());
	String name;
	Method action = null;
	Method _each = null;
	Method _after = null;
	Method _no = null;
	Method _finally = null;

	public ModelExtraCommand() {}
	
	public ModelExtraCommand(String name, Method action) {
		this.name = name;
		this.action = action;
	}
	
	public static ModelExtraCommand findByName(String name) {
		return xcont.get(name);
	}
	
	public static Collection<ModelExtraCommand> getExtraCommandList() {
		final Collection<ModelExtraCommand> l = xcont.values();
		return l;
	}
	
	public static void compute(ModelExtraCommand xcmd) {
		ModelExtraCommand m = xcont.get(xcmd.name);
		if(m==null){
			xcont.put(xcmd.name, xcmd);
			m = xcmd;
		}
		//System.out.println("comando computado: "+m.getName());
		if(xcmd.action!=null)
			m.setAction(xcmd.action);
		if(xcmd._each!=null)
			m.setEach(xcmd._each);
		if(xcmd._after!=null)
			m.setAfter(xcmd._after);
		if(xcmd._finally!=null)
			m.setFinally(xcmd._finally);
		if(xcmd._no!=null)
			m.setNoExecuted(xcmd._no);
		
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
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setAction(Method a) {
		action=a;
	}
	public void setEach(Method e) {
		_each=e;
	}
	public void setAfter(Method a) {
		_after=a;
	}
	public void setNoExecuted(Method n) {
		_no=n;
	}
	public void setFinally(Method f) {
		_finally=f;
	}
	public void Run(String command ,MessageChannel channel, String authorId, Object...args) {
		try {
			action.invoke(null, command ,channel, authorId, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void eachRun(MessageChannel channel, Object...args) {
		if(_each==null)
			return;
		try {
			_each.invoke(null, channel, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void afterRun(MessageChannel channel, Object...args) {
		if(_after==null)
			return;
		try {
			_after.invoke(null, channel, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void NoRun(MessageChannel channel, Object...args) {
		if(_no==null)
			return;
		try {
			_no.invoke(null, channel, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void finallyRun(MessageChannel channel, Object...args) {
		if(_finally==null)
			return;
		try {
			_finally.invoke(null, channel, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
