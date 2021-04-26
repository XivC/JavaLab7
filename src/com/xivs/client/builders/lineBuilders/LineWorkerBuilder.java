package com.xivs.client.builders.lineBuilders;


import com.xivs.common.io.InputManager;
import com.xivs.common.io.OutputManager;
import com.xivs.common.lab.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class LineWorkerBuilder extends LineBuilder<Worker> {

    public LineWorkerBuilder(InputManager inputManager, OutputManager outputManager) {

        super(inputManager, outputManager);
    }


    public Worker build() throws IOException {

        ArrayList<String> params = inputManager.getWords();
        String name;
        String salary;
        if (params.size() < 4) {
            name = "";
            salary = "";
        } else {
            name = params.get(2);
            salary = params.get(3);
        }
        Coordinates coordinates = Coordinates.DEFAULT;
        Organization organization = Organization.DEFAULT;
        while (!Worker.Params.name.parse(name)) {

            outputManager.print("Имя: ");
            inputManager.nextLine();
            name = inputManager.getString();
        }
        while (!Worker.Params.salary.parse(salary)) {

            outputManager.print("Зарплата: ");
            inputManager.nextLine();
            salary = inputManager.getString();
        }
        do {

            outputManager.print("\nДата окончания контракта: ");
            inputManager.nextLine();
            if (inputManager.getString().isEmpty()) {
                Worker.Params.endDate.set(LocalDate.MAX);
                break;
            }
        }
        while (!Worker.Params.endDate.parse(inputManager.getString()));


        do {
            outputManager.print("Доступные должности: " + Arrays.asList(Position.values()));
            outputManager.print("\nВведите должность: ");
            inputManager.nextLine();

        }
        while (!Worker.Params.position.parse(inputManager.getString()));
        do {
            outputManager.println("Доступные статусы: " + Arrays.asList(Status.values()));
            outputManager.print("\nВведите статус: ");
            inputManager.nextLine();
            if (inputManager.getString().isEmpty()) {
                Worker.Params.status.set(Status.NONE);
                break;
            }

        }
        while (!Worker.Params.status.parse(inputManager.getString()));
        LineCoordinatesBuilder coordinates_builder = new LineCoordinatesBuilder(inputManager, outputManager);
        coordinates = coordinates_builder.build();


        boolean flg = false;
        while (!flg) {
            outputManager.print("Вводить организацию? (y/n)");
            inputManager.nextLine();
            switch (inputManager.getString().toLowerCase(Locale.ROOT)) {
                case "y":
                    LineOrganizationBuilder org_builder = new LineOrganizationBuilder(inputManager, outputManager);
                    organization = org_builder.build();
                    flg = true;
                    break;
                case "n":
                    flg = true;
                    break;
            }
        }
        return new Worker(Worker.Params.name.get(), Worker.Params.salary.get(), Worker.Params.endDate.get(), Worker.Params.status.get(), Worker.Params.position.get(), organization, coordinates);
    }


}


