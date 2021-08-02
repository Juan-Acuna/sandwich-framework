package com.jaxsandwich.framework.core.util;

import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
/**
 * [ES] Funciones útiles para mensajes en Discord.<br>
 * [EN] Useful functions for messages on Discord.
 * @author Juancho
 * @version 0.8
 */
public class MessageUtils {
	/**
	 * [ES] Envía un {@link MessageEmbed} en el {@link MessageChannel} y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a {@link MessageEmbed} to the {@link MessageChannel} and deletes itself after the specified time.
	 */
	public static void SendAndDestroy(MessageChannel c, MessageEmbed emb, int time) {
		c.sendMessageEmbeds(emb).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a message to the {@link MessageChannel} and deletes itself after the specified time.
	 */
	public static void SendAndDestroy(MessageChannel c, String msg,int time) {
		c.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	/**
	 * [ES] Envía un {@link Message} en el {@link MessageChannel} y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a {@link Message} to the {@link MessageChannel} and deletes itself after the specified time.
	 */
	public static void SendAndDestroy(MessageChannel c, Message msg,int time) {
		c.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	
	/**
	 * [ES] Envía un {@link MessageEmbed} en el {@link MessageChannel} y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a {@link MessageEmbed} to the {@link MessageChannel} and deletes itself after the specified time.
	 */
	public static void SendAndDestroy(MessageChannel c, MessageEmbed emb, int time, TimeUnit unit) {
		c.sendMessageEmbeds(emb).queue((message) -> message.delete().queueAfter(time, unit));
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a message to the {@link MessageChannel} and deletes itself after the specified time.
	 */
	public static void SendAndDestroy(MessageChannel c, String msg,int time, TimeUnit unit) {
		c.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, unit));
	}
	/**
	 * [ES] Envía un {@link Message} en el {@link MessageChannel} y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a {@link Message} to the {@link MessageChannel} and deletes itself after the specified time.
	 */
	public static void SendAndDestroy(MessageChannel c, Message msg,int time, TimeUnit unit) {
		c.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, unit));
	}
}
