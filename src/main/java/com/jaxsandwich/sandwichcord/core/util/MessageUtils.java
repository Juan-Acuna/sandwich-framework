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

package com.jaxsandwich.sandwichcord.core.util;

import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import com.jaxsandwich.sandwichcord.models.CommandPacket;

/**
 * [ES] Funciones útiles para mensajes en Discord.<br>
 * [EN] Useful functions for messages on Discord.
 * @author Juancho
 * @version 0.9
 */
public class MessageUtils {
	/**
	 * [ES] Envía un {@link MessageEmbed} en el {@link MessageChannel} y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a {@link MessageEmbed} to the {@link MessageChannel} and deletes itself after the specified time.<br>
	 * @deprecated [ES] En su lugar use {@link CommandPacket#SendAndDestroy(MessageEmbed, int)}.<br>
	 * [EN] Better use {@link CommandPacket#SendAndDestroy(MessageEmbed, int)}.
	 */
	@Deprecated
	public static void SendAndDestroy(MessageChannel c, MessageEmbed emb, int time) {
		c.sendMessageEmbeds(emb).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a message to the {@link MessageChannel} and deletes itself after the specified time.<br>
	 * @deprecated En su lugar use {@link CommandPacket#SendAndDestroy(String, int)}.<br>
	 * [EN] Better use {@link CommandPacket#SendAndDestroy(String, int)}.
	 */
	@Deprecated
	public static void SendAndDestroy(MessageChannel c, String msg, int time) {
		c.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	/**
	 * [ES] Envía un {@link Message} en el {@link MessageChannel} y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a {@link Message} to the {@link MessageChannel} and deletes itself after the specified time.<br>
	 * @deprecated [ES] En su lugar use {@link CommandPacket#SendAndDestroy(Message, int)}.<br>
	 * [EN] Better use {@link CommandPacket#SendAndDestroy(Message, int)}.
	 */
	@Deprecated
	public static void SendAndDestroy(MessageChannel c, Message msg, int time) {
		c.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	
	/**
	 * [ES] Envía un {@link MessageEmbed} en el {@link MessageChannel} y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a {@link MessageEmbed} to the {@link MessageChannel} and deletes itself after the specified time.<br>
	 * @deprecated [ES] En su lugar use {@link CommandPacket#SendAndDestroy(MessageEmbed, int, TimeUnit)}.<br>
	 * [EN] Better use {@link CommandPacket#SendAndDestroy(MessageEmbed, int, TimeUnit)}.
	 */
	@Deprecated
	public static void SendAndDestroy(MessageChannel c, MessageEmbed emb, int time, TimeUnit unit) {
		c.sendMessageEmbeds(emb).queue((message) -> message.delete().queueAfter(time, unit));
	}
	/**
	 * [ES] Envía un mensaje en el {@link MessageChannel} y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a message to the {@link MessageChannel} and deletes itself after the specified time.<br>
	 * @deprecated [ES] En su lugar use {@link CommandPacket#SendAndDestroy(String, int, TimeUnit)}.<br>
	 * [EN] Better use {@link CommandPacket#SendAndDestroy(String, int, TimeUnit)}.
	 */
	@Deprecated
	public static void SendAndDestroy(MessageChannel c, String msg, int time, TimeUnit unit) {
		c.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, unit));
	}
	/**
	 * [ES] Envía un {@link Message} en el {@link MessageChannel} y se elimina después del tiempo especificado.<br>
	 * [EN] Sends a {@link Message} to the {@link MessageChannel} and deletes itself after the specified time.<br>
	 * @deprecated [ES] En su lugar use {@link CommandPacket#SendAndDestroy(Message, int, TimeUnit)}.<br>
	 * [EN] Better use {@link CommandPacket#SendAndDestroy(Message, int, TimeUnit)}.
	 */
	@Deprecated
	public static void SendAndDestroy(MessageChannel c, Message msg, int time, TimeUnit unit) {
		c.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, unit));
	}
}
