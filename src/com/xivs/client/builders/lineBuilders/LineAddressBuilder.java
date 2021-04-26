package com.xivs.client.builders.lineBuilders;

import com.xivs.common.io.InputManager;
import com.xivs.common.io.OutputManager;
import com.xivs.common.lab.Address;

import java.io.IOException;

public class LineAddressBuilder extends LineBuilder<Address> {


    public LineAddressBuilder(InputManager inputManager, OutputManager outputManager) {
        super(inputManager, outputManager);
    }

    public Address build() throws IOException {

        do {

            outputManager.print("Введите почтовый индекс: ");
            inputManager.nextLine();
        }

        while (!Address.Params.zipCode.parse(inputManager.getString()));
        do {

            outputManager.print("Введите улицу: ");
            inputManager.nextLine();

        }
        while (!Address.Params.street.parse(inputManager.getString()));
        return new Address(Address.Params.street.get(), Address.Params.zipCode.get());


    }
}
