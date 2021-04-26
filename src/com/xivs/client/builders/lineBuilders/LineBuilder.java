package com.xivs.client.builders.lineBuilders;

import com.xivs.client.builders.Builder;
import com.xivs.common.io.InputManager;
import com.xivs.common.io.OutputManager;

public abstract class LineBuilder<T extends Object> extends Builder<T> {
    InputManager inputManager;
    OutputManager outputManager;

    public LineBuilder(InputManager inputManager, OutputManager outputManager) {
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }


}
