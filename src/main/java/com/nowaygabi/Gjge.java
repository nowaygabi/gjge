package com.nowaygabi;

import com.nowaygabi.engine.Window;

public class Gjge {
    public static void main(String[] args) {
        Window window = Window.getInstance();
        window.setProperties(1024, 576);
        window.run();
    }
}
