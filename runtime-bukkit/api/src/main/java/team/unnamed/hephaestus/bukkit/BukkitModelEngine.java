/*
 * This file is part of hephaestus-engine, licensed under the MIT license
 *
 * Copyright (c) 2021-2023 Unnamed Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package team.unnamed.hephaestus.bukkit;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.jetbrains.annotations.NotNull;
import team.unnamed.hephaestus.Model;
import team.unnamed.hephaestus.ModelEngine;
import team.unnamed.hephaestus.bukkit.track.BukkitModelViewTracker;
import team.unnamed.hephaestus.bukkit.track.ModelViewPersistenceHandler;
import team.unnamed.hephaestus.view.track.ModelViewTrackingRule;

import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * The hephaestus model engine abstraction for
 * Bukkit-based server implementations such as
 * Spigot and Paper
 *
 * @since 1.0.0
 */
public interface BukkitModelEngine extends ModelEngine<Player, Location> {
    @Override
    @NotNull BukkitModelViewTracker tracker();

    @NotNull ModelViewPersistenceHandler persistence();

    @NotNull ModelView createViewAndTrack(Model model, Pattern partFilter, Location location, CreatureSpawnEvent.SpawnReason reason);

    @Override
    default @NotNull ModelView createViewAndTrack(Model model, Pattern partFilter, Location location) {
        return createViewAndTrack(model, partFilter, location, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    @NotNull ModelView createView(Model model, Pattern partFilter, Location location);

    //#region --- Helper methods ---
    default @NotNull ModelView spawn(final @NotNull Model model, final @NotNull Pattern partFilter, final @NotNull Entity base) {
        final var view = createView(model, partFilter, base.getLocation());
        tracker().startGlobalTrackingOn(view, base);
        return view;
    }

    default @NotNull ModelView spawn(final @NotNull Model model, final @NotNull Pattern partFilter, final @NotNull Entity base, final @NotNull ModelViewTrackingRule<Player> trackingRule) {
        final var view = createView(model, partFilter, base.getLocation());
        tracker().startTrackingOn(view, base, trackingRule);
        return view;
    }

    default @NotNull ModelView spawn(final @NotNull Model model, final @NotNull Pattern partFilter, final @NotNull Entity base, final @NotNull Predicate<Player> trackingRule) {
        return spawn(model, partFilter, base, (_view, player) -> trackingRule.test(player));
    }
    //#endregion

    void close();
}