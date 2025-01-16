/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.cobblemon.mod.common.api.pokemon.stats

import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer

/**
 * A type adapter responsible for (de)serializing [Stat]s.
 *
 * @author Licious
 * @since November 6th, 2022
 */
interface StatTypeAdapter : JsonDeserializer<Stat>, JsonSerializer<Stat>