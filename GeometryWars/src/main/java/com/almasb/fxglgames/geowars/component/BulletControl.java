/*
 * The MIT License (MIT)
 *
 * FXGL - JavaFX Game Library
 *
 * Copyright (c) 2015-2016 AlmasB (almaslvl@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.almasb.fxglgames.geowars.component;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxglgames.geowars.grid.Grid;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public class BulletControl extends Component {

    private static final Color PARTICLE_COLOR = Color.YELLOW.brighter();
    private static final Duration PARTICLE_DURATION = Duration.seconds(1.2);

    static {
        ExhaustParticleComponent.colorImage(PARTICLE_COLOR);
    }

    private BoundingBoxComponent bbox;
    private ViewComponent view;

    private Point2D velocity;
    private Grid grid;

    public BulletControl(Grid grid) {
        this.grid = grid;
    }

    @Override
    public void onAdded() {
        velocity = entity.getComponent(ProjectileComponent.class).getVelocity();

        //view.getView().setEffect(new Bloom());
    }

    @Override
    public void onUpdate(double tpf) {
        grid.applyExplosiveForce(velocity.magnitude() / 60 * 18, bbox.getCenterWorld(), 80 * 60 * tpf);

        if (bbox.getMinXWorld() < 0) {
            spawnParticles(0, bbox.getCenterWorld().getY(), 1, FXGLMath.random(-1.0f, 1.0f));

        } else if (bbox.getMaxXWorld() > FXGL.getAppWidth()) {
            spawnParticles(FXGL.getAppWidth(), bbox.getCenterWorld().getY(), -1, FXGLMath.random(-1.0f, 1.0f));

        } else if (bbox.getMinYWorld() < 0) {
            spawnParticles(bbox.getCenterWorld().getX(), 0, FXGLMath.random(-1.0f, 1.0f), 1);

        } else if (bbox.getMaxYWorld() > FXGL.getAppHeight()) {
            spawnParticles(bbox.getCenterWorld().getX(), FXGL.getAppHeight(), FXGLMath.random(-1.0f, 1.0f), -1);
        }
    }

    private void spawnParticles(double x, double y, double dirX, double dirY) {
//        FXGL.entityBuilder()
//                .at(x, y)
//                .viewFromNode(new Texture(ExhaustParticleComponent.coloredImages.get(PARTICLE_COLOR)))
//                .with(new ProjectileControl(new Point2D(dirX, dirY), FXGLMath.random(150, 280)),
//                        new ExpireCleanControl(PARTICLE_DURATION),
//                        new ParticleControl())
//                .buildAndAttach(FXGL.getApp().getGameWorld());
    }
}
