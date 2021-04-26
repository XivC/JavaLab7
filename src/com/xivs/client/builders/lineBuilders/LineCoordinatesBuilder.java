package com.xivs.client.builders.lineBuilders;


import com.xivs.common.io.InputManager;
import com.xivs.common.io.OutputManager;
import com.xivs.common.lab.Coordinates;

import java.io.IOException;


public class LineCoordinatesBuilder extends LineBuilder<Coordinates> {


    public LineCoordinatesBuilder(InputManager inputManager, OutputManager outputManager) {

        super(inputManager, outputManager);
    }

    public Coordinates build() throws IOException {


        outputManager.println("Ввод координат: ");
        do {

            outputManager.print("x: ");
            inputManager.nextLine();

        }
        while (!Coordinates.Params.x.parse(inputManager.getString()));
        do {
            outputManager.print("y: ");
            inputManager.nextLine();

        }
        while (!Coordinates.Params.y.parse(inputManager.getString()));

        return new Coordinates(Coordinates.Params.x.get(), Coordinates.Params.y.get());
    }


}
