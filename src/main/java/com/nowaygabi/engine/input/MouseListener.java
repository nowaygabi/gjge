/*
 * Copyright (c) 2024. Gabriela Santos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nowaygabi.engine.input;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance;
    private double xPos, yPos, lastX, lastY;
    private double scrollX, scrollY;
    private final boolean[] mousePressed = new boolean[3];
    private boolean isDragging;

    private MouseListener() {
        this.xPos = 0f;
        this.yPos = 0f;
        this.lastX = 0f;
        this.lastY = 0f;
        this.scrollX = 0f;
        this.scrollY = 0f;
    }

    public static MouseListener getInstance() {
        if (instance == null) {
            instance = new MouseListener();
        }
        return instance;
    }

    public static void mousePosCallback(long window, double xPos, double yPos) {
        getInstance().lastX = getInstance().xPos;
        getInstance().lastY = getInstance().yPos;
        getInstance().xPos = xPos;
        getInstance().yPos = yPos;
        getInstance().isDragging = getInstance().mousePressed[0] || getInstance().mousePressed[1] || getInstance().mousePressed[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            if (button < getInstance().mousePressed.length) {
                getInstance().mousePressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            if (button < getInstance().mousePressed.length) {
                getInstance().mousePressed[button] = false;
                getInstance().isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        getInstance().scrollX = xOffset;
        getInstance().scrollY = yOffset;
    }

    public static void endFrame() {
        getInstance().scrollX = 0f;
        getInstance().scrollY = 0f;
        getInstance().lastX = getInstance().xPos;
        getInstance().lastY = getInstance().yPos;
    }

    public static double getX() {
        return getInstance().xPos;
    }

    public static double getY() {
        return getInstance().yPos;
    }

    public static double getDX() {
        return (getInstance().lastX - getInstance().xPos);
    }

    public static double getDY() {
        return (getInstance().lastY - getInstance().yPos);

    }

    public static double getScrollX() {
        return getInstance().scrollX;
    }

    public static double getScrollY() {
        return getInstance().scrollY;
    }

    public static boolean isDragging() {
        return getInstance().isDragging;
    }

    public static boolean isMouseButtonDown(int button) {
        if (button < getInstance().mousePressed.length) return getInstance().mousePressed[button];
        return false;
    }
}
