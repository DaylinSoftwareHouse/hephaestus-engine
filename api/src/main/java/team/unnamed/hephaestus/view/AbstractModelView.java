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
package team.unnamed.hephaestus.view;

import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.unnamed.hephaestus.Model;
import team.unnamed.hephaestus.animation.controller.AnimationPlayer;
import team.unnamed.hephaestus.animation.Animation;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * Base abstraction for representing a {@link Model}
 * view, allows multiple viewers and animation playing
 *
 * <p>Platform independent, to use more specific properties
 * and avoid using generics, use their implementations in
 * runtime-* projects</p>
 *
 * @since 1.0.0
 */
public interface AbstractModelView<TViewer> {
    /**
     * Returns the model linked to this view.
     *
     * @return The model
     * @since 1.0.0
     */
    @NotNull Model model();

    Collection<TViewer> viewers();

    boolean addViewer(TViewer viewer);

    boolean removeViewer(TViewer viewer);

    @NotNull Pattern partFilter();

    /**
     * Makes this model view emit the specified sound for
     * all of its viewers.
     *
     * @param sound The sound to emit.
     * @since 1.0.0
     */
    void emitSound(final @NotNull Sound sound);

    /**
     * Colorizes this view using the specified
     * {@code red}, {@code green} and {@code blue}
     * color components.
     * 
     * @param red The red component [0-255]
     * @param green The green component [0-255]
     * @param blue The blue component [0-255]
     * @since 1.0.0
     */
    default void colorize(final int red, final int green, final int blue) {
        for (final var bone : bones()) {
            bone.colorize(red, green, blue);
        }
    }

    /**
     * Colorizes this view using the specified,
     * encoded RGB (Red, Green, Blue) color
     *
     * @param rgb The encoded color
     * @since 1.0.0
     */
    default void colorize(final int rgb) {
        colorize((rgb >> 16) & 255, (rgb >> 8) & 255, rgb & 255);
    }

    /**
     * Colorizes this view using the default,
     * initial color {@link AbstractBoneView#DEFAULT_COLOR}
     *
     * @see AbstractModelView#colorize(int)
     */
    default void colorizeDefault() {
        colorize(AbstractBoneView.DEFAULT_COLOR);
    }

    /**
     * Returns a collection holding <strong>all</strong>
     * the bones created this model view
     *
     * @return The model view bone views
     * @since 1.0.0
     */
    Collection<? extends AbstractBoneView> bones();

    /**
     * Gets the bone with the specified name
     *
     * @param name The bone name
     * @return The bone view, null if absent
     */
    @Nullable AbstractBoneView bone(String name);

    /**
     * Returns the animation player linked to
     * this model view.
     *
     * @return The animation player
     * @since 1.0.0
     */
    @NotNull AnimationPlayer animationPlayer();

    /**
     * Finds and plays the animation with the
     * specified {@code name} for this model view
     * instance.
     *
     * @param name The animation name
     * @see Model#animations()
     */
    default void addAnimation(String name, Pattern boneMask) {
        Animation animation = model().animations().get(name);
        Objects.requireNonNull(animation, "Animation " + name);
        animationPlayer().add(animation, boneMask);
    }

    default void setAnimation(String name, Pattern boneMask) {
        Animation animation = model().animations().get(name);
        Objects.requireNonNull(animation, "Animation " + name);
        animationPlayer().clear();
        animationPlayer().add(animation, boneMask);
    }

    /**
     * Ticks animations, makes required bones pass
     * to the next animation frame
     */
    default void tickAnimations() {
        animationPlayer().tick();
    }

    default boolean hasAnimations() {
        AtomicInteger counter = new AtomicInteger();
        animationPlayer().animations().forEach((anim) -> {
            counter.getAndIncrement();
        });
        System.out.println("Has " + counter + " animations!");
        return animationPlayer().animations().isEmpty();
    }
}
