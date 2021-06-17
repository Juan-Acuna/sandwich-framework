package xyz.sandwichframework.core.util;

import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.AuthorInfo;
import net.dv8tion.jda.api.entities.MessageEmbed.Provider;
import net.dv8tion.jda.api.entities.MessageEmbed.VideoInfo;
/**
 * Funciones útiles para mensajes en Discord.
 * Useful functions for messages on Discord.
 * @author Juancho
 * @version 0.7
 */
public class MessageUtils {
	public static void SendAndDestroy(MessageChannel c, MessageEmbed emb, int time) {
		c.sendMessage(emb).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	public static void SendAndDestroy(MessageChannel c, String msg,int time) {
		c.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	public static void SendAndDestroy(MessageChannel c, Message msg,int time) {
		c.sendMessage(msg).queue((message) -> message.delete().queueAfter(time, TimeUnit.SECONDS));
	}
	/*public static MessageEmbed createVideoEmbed(String title, String desc, String url, String providerName, String providerUrl) {
		EmbedBuilder eb = new EmbedBuilder();
		Provider p = new Provider(providerName, providerUrl);
		System.out.println("p:"+p);
		eb.setTitle(title);
		eb.setDescription(desc);
		MessageEmbed me = eb.build();
		MessageEmbed me2 = new MessageEmbed(null, 
				title, 
				desc, 
				EmbedType.VIDEO,
				me.getTimestamp(),
				me.getColorRaw(), 
				null, 
				p, 
				new AuthorInfo("Sandwich", null, null, null), 
				new VideoInfo(url, 1280, 720), 
				me.getFooter(), 
				me.getImage(), 
				me.getFields());
		
		return me2;
	}*/
}
