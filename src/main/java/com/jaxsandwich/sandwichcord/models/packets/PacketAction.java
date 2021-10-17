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

package com.jaxsandwich.sandwichcord.models.packets;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.MentionType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyAction;
import net.dv8tion.jda.api.utils.AttachmentOption;
/**
 * [ES] <br>
 * [EN] 
 * @author Juan Acuña
 * @version 1.1
 * @since 0.9.0
 */
public class PacketAction<T>{
	private static final String PLACEHOLDER = "...";
	private Message message = null;
	private ReplyAction replyAction = null;
	private MessageAction messageAction = null;
	private List<ActionRow> ars = null;
	private List<MessageEmbed> embs = null;
	private String cont = null;
	public PacketAction(Message message) {
		this.message=message;
	}
	public PacketAction(ReplyAction replyAction) {
		this.replyAction = replyAction;
	}
	public PacketAction(MessageAction messageAction) {
		this.messageAction = messageAction;
	}
	public MessageAction getAsMessageAction() {
		return this.messageAction;
	}
	public ReplyAction getAsReplyAction() {
		return this.replyAction;
	}
	public PacketAction<T> addActionRow(Collection<? extends Component> components) {
		if(this.replyAction!=null) {
			this.replyAction.addActionRow(components);
		}else if(this.messageAction!=null) {
			if(this.ars==null)
				this.ars = new ArrayList<ActionRow>();
			this.ars.add(ActionRow.of(components));
			this.messageAction.setActionRows(this.ars);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			if(this.ars==null)
				this.ars = new ArrayList<ActionRow>();
			this.ars.add(ActionRow.of(components));
			this.messageAction.setActionRows(this.ars);
		}
		return this;
	}
	public PacketAction<T> addActionRow(Component...components) {
		if(this.replyAction!=null) {
			this.replyAction.addActionRow(components);
		}else if(this.messageAction!=null) {
			if(this.ars==null)
				this.ars = new ArrayList<ActionRow>();
			this.ars.add(ActionRow.of(components));
			this.messageAction.setActionRows(this.ars);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			if(this.ars==null)
				this.ars = new ArrayList<ActionRow>();
			this.ars.add(ActionRow.of(components));
			this.messageAction.setActionRows(this.ars);
		}
		return this;
	}
	public PacketAction<T> addActionRows(Collection<? extends ActionRow> rows) {
		if(this.replyAction!=null) {
			this.replyAction.addActionRows(rows);
		}else if(this.messageAction!=null) {
			if(this.ars==null)
				this.ars = new ArrayList<ActionRow>();
			this.ars.addAll(rows);
			this.messageAction.setActionRows(this.ars);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			if(this.ars==null)
				this.ars = new ArrayList<ActionRow>();
			this.ars.addAll(rows);
			this.messageAction.setActionRows(this.ars);
		}
		return this;
	}
	public PacketAction<T> addActionRows(ActionRow...rows) {
		if(this.replyAction!=null) {
			this.replyAction.addActionRows(rows);
		}else if(this.messageAction!=null) {
			if(this.ars==null)
				this.ars = new ArrayList<ActionRow>();
			if(rows!=null) {
				for(ActionRow a : rows) {
					this.ars.add(a);
				}
			}
			this.messageAction.setActionRows(this.ars);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			if(this.ars==null)
				this.ars = new ArrayList<ActionRow>();
			if(rows!=null) {
				for(ActionRow a : rows) {
					this.ars.add(a);
				}
			}
			this.messageAction.setActionRows(this.ars);
		}
		return this;
	}
	public PacketAction<T> addEmbeds(Collection<? extends MessageEmbed> embeds){
		if(this.replyAction!=null) {
			this.replyAction.addEmbeds(embeds);
		}else if(this.messageAction!=null) {
			if(this.embs==null)
				this.embs = new ArrayList<MessageEmbed>();
			this.embs.addAll(embeds);
			this.messageAction.setEmbeds(this.embs);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			if(this.embs==null)
				this.embs = new ArrayList<MessageEmbed>();
			this.embs.addAll(embeds);
			this.messageAction.setEmbeds(this.embs);
		}
		return this;
	}
	public PacketAction<T> addEmbeds(MessageEmbed...embeds){
		if(this.replyAction!=null) {
			this.replyAction.addEmbeds(embeds);
		}else if(this.messageAction!=null) {
			if(this.embs==null)
				this.embs = new ArrayList<MessageEmbed>();
			if(this.embs!=null) {
				for(MessageEmbed m : embeds) {
					this.embs.add(m);
				}
			}
			this.messageAction.setEmbeds(this.embs);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			if(this.embs==null)
				this.embs = new ArrayList<MessageEmbed>();
			if(this.embs!=null) {
				for(MessageEmbed m : embeds) {
					this.embs.add(m);
				}
			}
			this.messageAction.setEmbeds(this.embs);
		}
		return this;
	}
	public PacketAction<T> setActionRows(Collection<? extends ActionRow> rows) {
		if(this.replyAction!=null) {
			this.replyAction.addActionRows(rows);
		}else if(this.messageAction!=null) {
			this.messageAction.setActionRows(rows);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.setActionRows(rows);
		}
		return this;
	}
	public PacketAction<T> setActionRows(ActionRow...rows) {
		if(this.replyAction!=null) {
			this.replyAction.addActionRows(rows);
		}else if(this.messageAction!=null) {
			this.messageAction.setActionRows(rows);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.setActionRows(rows);
		}
		return this;
	}
	public PacketAction<T> setEmbeds(Collection<? extends MessageEmbed> embeds){
		if(this.replyAction!=null) {
			this.replyAction.addEmbeds(embeds);
		}else if(this.messageAction!=null) {
			this.messageAction.setEmbeds(embeds);
		}else if(this.messageAction!=null) {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.setEmbeds(embeds);
		}
		return this;
	}
	public PacketAction<T> setEmbeds(MessageEmbed...embeds){
		if(this.replyAction!=null) {
			this.replyAction.addEmbeds(embeds);
		}else if(this.messageAction!=null) {
			this.messageAction.setEmbeds(embeds);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.setEmbeds(embeds);
		}
		return this;
	}
	public PacketAction<T> setTTS(boolean tts){
		if(this.replyAction!=null) {
			this.replyAction.setTTS(tts);
		}else if(this.messageAction!=null) {
			this.messageAction.tts(tts);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.tts(tts);
		}
		return this;
	}
	public PacketAction<T> timeout(long timeout, TimeUnit unit){
		if(this.replyAction!=null) {
			this.replyAction.timeout(timeout, unit);
		}else if(this.messageAction!=null) {
			this.messageAction.timeout(timeout, unit);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.timeout(timeout, unit);
		}
		return this;
	}
	public PacketAction<T> setEphemeral(boolean ephemeral){
		if(this.replyAction!=null) {
			this.replyAction.setEphemeral(ephemeral);
		}/*else {
			this.messageAction.setEphemeral(ephemeral);
		}*/
		return this;
	}
	public PacketAction<T> setContent(String content){
		if(this.replyAction!=null) {
			this.replyAction.setContent(content);
		}else if(this.messageAction!=null) {
			this.messageAction.content(content);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.content(content);
		}
		return this;
	}
	public PacketAction<T> append(String content){
		if(this.replyAction!=null) {
			if(this.cont==null)
				this.cont = "";
			this.cont += content;
			this.replyAction.setContent(this.cont);
		}else if(this.messageAction!=null) {
			this.messageAction.append(content);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.append(content);
		}
		return this;
	}
	public PacketAction<T> appendFormat(String format, Object...args){
		if(this.replyAction!=null) {
			if(this.cont==null)
				this.cont = "";
			this.cont += String.format(format, args);
			this.replyAction.setContent(this.cont);
		}else if(this.messageAction!=null) {
			this.messageAction.appendFormat(format, args);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.appendFormat(format, args);
		}
		return this;
	}
	public PacketAction<T> 	addFile​(byte[] data, String name, AttachmentOption... options){
		if(this.replyAction!=null) {
			this.replyAction.addFile(data, name, options);
		}else if(this.messageAction!=null) {
			this.messageAction.addFile(data, name, options);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.addFile(data, name, options);
		}
		return this;
	}
	public PacketAction<T> 	addFile​(File file, String name, AttachmentOption... options){
		if(this.replyAction!=null) {
			this.replyAction.addFile(file, name, options);
		}else if(this.messageAction!=null) {
			this.messageAction.addFile(file, name, options);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.addFile(file, name, options);
		}
		return this;
	}
	public PacketAction<T> 	addFile​addFile​(File file, AttachmentOption... options){
		if(this.replyAction!=null) {
			this.replyAction.addFile(file, options);
		}else if(this.messageAction!=null) {
			this.messageAction.addFile(file, options);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.addFile(file, options);
		}
		return this;
	}
	public PacketAction<T> 	addFile​(InputStream data, String name, AttachmentOption... options){
		if(this.replyAction!=null) {
			this.replyAction.addFile(data, name, options);
		}else if(this.messageAction!=null) {
			this.messageAction.addFile(data, name, options);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.addFile(data, name, options);
		}
		return this;
	}
	public JDA getJDA() {
		if(this.replyAction!=null) {
			return this.replyAction.getJDA();
		}else if(this.messageAction!=null) {
			return this.messageAction.getJDA();
		}else{
			return this.message.getJDA();
		}
	}
	public PacketAction<T> setCheck(BooleanSupplier checks) {
		if(this.replyAction!=null) {
			this.replyAction.setCheck(checks);
		}else if(this.messageAction!=null) {
			this.messageAction.setCheck(checks);
		}
		return this;
	}
	@SuppressWarnings("unchecked")
	public void queue(Consumer<? super T> success, Consumer<? super Throwable> failure) {
		if(this.replyAction!=null) {
			this.replyAction.queue((Consumer<? super InteractionHook>) success, failure);
		}else if(this.messageAction!=null){
			this.messageAction.queue((Consumer<? super Message>) success,failure);
		}
	}
	@SuppressWarnings("unchecked")
	public T complete(boolean shouldQueue) throws RateLimitedException {
		if(this.replyAction!=null) {
			return (T) this.replyAction.complete();
		}else if(this.messageAction!=null) {
			return (T) this.messageAction.complete();
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			return (T) this.messageAction.complete();
		}
	}
	@SuppressWarnings("unchecked")
	public CompletableFuture<T> submit(boolean shouldQueue) {
		if(this.replyAction!=null) {
			return (CompletableFuture<T>) this.replyAction.submit(shouldQueue);
		}else if(this.messageAction!=null) {
			return (CompletableFuture<T>) this.messageAction.submit(shouldQueue);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			return (CompletableFuture<T>) this.messageAction.submit(shouldQueue);
		}
	}
	public void queue() {
		if(this.replyAction!=null) {
			this.replyAction.queue();
		}else if(this.messageAction!=null) {
			this.messageAction.queue();
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.queue();
		}
	}
	@SuppressWarnings("unchecked")
	public void queue(Consumer<? super T> success) {
		if(this.replyAction!=null) {
			this.replyAction.queue((Consumer<? super InteractionHook>) success);
		}else if(this.messageAction!=null) {
			this.messageAction.queue((Consumer<? super Message>) success);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.queue((Consumer<? super Message>) success);
		}
	}
	@SuppressWarnings("unchecked")
	public T complete() throws RateLimitedException {
		if(this.replyAction!=null) {
			return (T) this.replyAction.complete();
		}else if(this.messageAction!=null) {
			return (T) this.messageAction.complete();
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			return (T) this.messageAction.complete();
		}
	}
	@SuppressWarnings("unchecked")
	public CompletableFuture<T> submit() {
		if(this.replyAction!=null) {
			return (CompletableFuture<T>) this.replyAction.submit();
		}else if(this.messageAction!=null) {
			return (CompletableFuture<T>) this.messageAction.submit();
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			return (CompletableFuture<T>) this.messageAction.submit();
		}
	}
	public PacketAction<T> deadline​(long timestamp){
		if(this.replyAction!=null) {
			this.replyAction.deadline(timestamp);
		}else if(this.messageAction!=null) {
			this.messageAction.deadline(timestamp);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.deadline(timestamp);
		}
		return this;
	}
	public ScheduledFuture<?> queueAfter​(long delay, TimeUnit unit) {
		if(this.replyAction!=null) {
			return this.replyAction.queueAfter(delay, unit);
		}else if(this.messageAction!=null) {
			return this.messageAction.queueAfter(delay, unit);
		}else  {
			this.messageAction = this.message.reply(PLACEHOLDER);
			return this.messageAction.queueAfter(delay, unit);
		}
	}
	public ScheduledFuture<?> queueAfter​(long delay, TimeUnit unit, ScheduledExecutorService executor) {
		if(this.replyAction!=null) {
			return this.replyAction.queueAfter(delay, unit, executor);
		}else if(this.messageAction!=null) {
			return this.messageAction.queueAfter(delay, unit, executor);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			return this.messageAction.queueAfter(delay, unit, executor);
		}
	}
	@SuppressWarnings("unchecked")
	public ScheduledFuture<?> queueAfter​(long delay, TimeUnit unit, Consumer<? super T> success) {
		if(this.replyAction!=null) {
			return this.replyAction.queueAfter(delay, unit, (Consumer<? super InteractionHook>) success);
		}else if(this.messageAction!=null) {
			return this.messageAction.queueAfter(delay, unit, (Consumer<? super Message>) success);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			return this.messageAction.queueAfter(delay, unit, (Consumer<? super Message>) success);
		}
	}
	@SuppressWarnings("unchecked")
	public ScheduledFuture<?> queueAfter​(long delay, TimeUnit unit, Consumer<? super T> success, ScheduledExecutorService executor) {
		if(this.replyAction!=null) {
			return this.replyAction.queueAfter(delay, unit, (Consumer<? super InteractionHook>) success, executor);
		}else if(this.messageAction!=null) {
			return this.messageAction.queueAfter(delay, unit, (Consumer<? super Message>) success, executor);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			return this.messageAction.queueAfter(delay, unit, (Consumer<? super Message>) success, executor);
		}
	}
	@SuppressWarnings("unchecked")
	public ScheduledFuture<?> queueAfter​(long delay, TimeUnit unit, Consumer<? super T> success, Consumer<? super Throwable> failure) {
		if(this.replyAction!=null) {
			return this.replyAction.queueAfter(delay, unit, (Consumer<? super InteractionHook>) success, failure);
		}else if(this.messageAction!=null) {
			return this.messageAction.queueAfter(delay, unit, (Consumer<? super Message>) success, failure);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			return this.messageAction.queueAfter(delay, unit, (Consumer<? super Message>) success, failure);
		}
	}
	@SuppressWarnings("unchecked")
	public ScheduledFuture<?> queueAfter​(long delay, TimeUnit unit, Consumer<? super T> success, Consumer<? super Throwable> failure, ScheduledExecutorService executor) {
		if(this.replyAction!=null) {
			return this.replyAction.queueAfter(delay, unit, (Consumer<? super InteractionHook>) success, failure, executor);
		}else if(this.messageAction!=null) {
			return this.messageAction.queueAfter(delay, unit, (Consumer<? super Message>) success, failure, executor);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			return this.messageAction.queueAfter(delay, unit, (Consumer<? super Message>) success, failure, executor);
		}
	}
	@SuppressWarnings("unchecked")
	public CompletableFuture<T> submitAfter(long delay, TimeUnit unit) {
		if(this.replyAction!=null) {
			return (CompletableFuture<T>) this.replyAction.submitAfter(delay, unit);
		}else if(this.messageAction!=null) {
			return (CompletableFuture<T>) this.messageAction.submitAfter(delay, unit);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			return (CompletableFuture<T>) this.messageAction.submitAfter(delay, unit);
		}
	}
	@SuppressWarnings("unchecked")
	public CompletableFuture<T> submitAfter(long delay, TimeUnit unit, ScheduledExecutorService executor) {
		if(this.replyAction!=null) {
			return (CompletableFuture<T>) this.replyAction.submitAfter(delay, unit, executor);
		}else if(this.messageAction!=null) {
			return (CompletableFuture<T>) this.messageAction.submitAfter(delay, unit, executor);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			return (CompletableFuture<T>) this.messageAction.submitAfter(delay, unit, executor);
		}
	}
	
	
	public PacketAction<T> mentionRepliedUser(boolean mention) {
		if(this.replyAction!=null) {
			this.replyAction.mentionRepliedUser(mention);
		}else if(this.messageAction!=null) {
			this.messageAction.mentionRepliedUser(mention);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.mentionRepliedUser(mention);
		}
		return this;
	}
	public PacketAction<T> allowedMentions(Collection<MentionType> allowedMentions) {
		if(this.replyAction!=null) {
			this.replyAction.allowedMentions(allowedMentions);
		}else if(this.messageAction!=null) {
			this.messageAction.allowedMentions(allowedMentions);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.allowedMentions(allowedMentions);
		}
		return this;
	}
	public PacketAction<T> mention(IMentionable... mentions) {
		if(this.replyAction!=null) {
			this.replyAction.mention(mentions);
		}else if(this.messageAction!=null) {
			this.messageAction.mention(mentions);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.mention(mentions);
		}
		return this;
	}
	public PacketAction<T> mentionUsers(String... userIds) {
		if(this.replyAction!=null) {
			this.replyAction.mentionUsers(userIds);
		}else if(this.messageAction!=null) {
			this.messageAction.mentionUsers(userIds);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.mentionUsers(userIds);
		}
		return this;
	}
	public PacketAction<T> mentionRoles(String... roleIds) {
		if(this.replyAction!=null) {
			this.replyAction.mentionRoles(roleIds);
		}else if(this.messageAction!=null) {
			this.messageAction.mentionRoles(roleIds);
		}else {
			this.messageAction = this.message.reply(PLACEHOLDER);
			this.messageAction.mentionRoles(roleIds);
		}
		return this;
	}
}
