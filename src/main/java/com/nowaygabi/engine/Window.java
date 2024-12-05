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

package com.nowaygabi.engine;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width = 1600;
    private int height = 900;
    private String title = "Gjge (Gabi's Java Game Engine)";
    private long glfwWindow;

    private static Window instance = null;

    private Window() {}

    public static Window getInstance() {
        if (instance == null) {
            instance = new Window();
        }
        return instance;
    }

    public void setProperties(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public void setProperties(final int width, final int height, final String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void run() {
        System.out.println("Starting LWJGL...\nVersion: " + Version.getVersion());

        init();
        loop();

        // Closes glfw and clears the engine's memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        // Setup error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        // Create a new window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, 0, 0);
        if (glfwWindow == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);

        // Enable V-Sync
        glfwSwapInterval(1);

        // Make the windows visible
        glfwShowWindow(glfwWindow);

        // Loads available OpenGL methods
        GL.createCapabilities();
    }

    public void loop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll Events
            glfwPollEvents();

            glClearColor(1f, 0f, 0f, 1f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow);
        }
    }
}
