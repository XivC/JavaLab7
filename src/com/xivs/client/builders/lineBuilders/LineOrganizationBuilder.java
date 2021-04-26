package com.xivs.client.builders.lineBuilders;

import com.xivs.common.io.InputManager;
import com.xivs.common.io.OutputManager;
import com.xivs.common.lab.Address;
import com.xivs.common.lab.Organization;
import com.xivs.common.lab.OrganizationType;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class LineOrganizationBuilder extends LineBuilder<Organization> {


    public LineOrganizationBuilder(InputManager inputManager, OutputManager outputManager) {

        super(inputManager, outputManager);
    }

    public Organization build() throws IOException {

        Address addr = Address.DEFAULT;
        do {
            outputManager.print("Введите годовой оборот: ");
            inputManager.nextLine();

        }
        while (!Organization.Params.annualTurnover.parse(inputManager.getString()));
        do {
            outputManager.println("Доступные типы: " + Arrays.asList(OrganizationType.values()));
            outputManager.print("\nВведите тип организации: ");
            inputManager.nextLine();
            if (inputManager.getString().isEmpty()) {
                Organization.Params.type.set(OrganizationType.NONE);
                break;
            }
        }
        while (!Organization.Params.type.parse(inputManager.getString()));
        boolean flg = false;
        while (!flg) {
            outputManager.print("Вводить адрес? (y/n) ");
            inputManager.nextLine();
            switch (inputManager.getString().toLowerCase(Locale.ROOT)) {
                case "y":
                    LineAddressBuilder builder = new LineAddressBuilder(inputManager, outputManager);
                    addr = builder.build();
                    flg = true;
                    break;
                case "n":
                    flg = true;
                    break;
            }
        }

        return new Organization(Organization.Params.annualTurnover.get(), Organization.Params.type.get(), addr);

    }

}

