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

package com.jaxsandwich.sandwichcord.core.builders;

import com.jaxsandwich.sandwichcord.models.packets.Packet;
/**
 * [ES] Herramienta para la construccion de {@link Packet}s.<br>
 * [EN] Tool for building of {@link Packet}s.
 * @author Juan Acuña
 * @version 1.0
 * @since 0.9.0
 */
public abstract class PacketBuilder<T extends Packet<?>> {
	/**
	 * [ES] Construye el Packet.
	 * [EN] Builds the Packet.
	 * @return [ES] Packet del tipo indicado.<br>[EN] Packet of indicated type.
	 */
	public abstract T build();
}
