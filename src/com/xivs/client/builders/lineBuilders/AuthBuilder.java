package com.xivs.client.builders.lineBuilders;

import com.xivs.common.dataTransfer.Auth;
import com.xivs.common.io.CommandLineInputManager;
import com.xivs.common.io.InputManager;
import com.xivs.common.io.OutputManager;
import com.xivs.common.lab.Address;
import com.xivs.common.lab.Coordinates;

import java.io.IOException;

public class AuthBuilder extends LineBuilder<Auth>{
    public AuthBuilder(InputManager inputManager, OutputManager outputManager) {

        super(inputManager, outputManager);
    }

    public Auth build() throws IOException {


        outputManager.print("Имя пользователя: ");
        inputManager.nextLine();
        String login = inputManager.getString();
        String password = "";
        outputManager.print("Пароль: ");
        try{
            password = ((CommandLineInputManager)inputManager).readPassword();
            if (password == null){
                inputManager.nextLine();
                password = inputManager.getString();
            }
        }
        catch (ClassCastException ex){
            inputManager.nextLine();
            password = inputManager.getString();
        }
        return new Auth(login, password);
    }
}
