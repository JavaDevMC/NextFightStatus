package com.maximde.serverstatus;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ServerData {



    public static int getPlayerCount(String serverAddress) {
        String apiUrl = "https://eu.mc-api.net/v3/server/ping/" + serverAddress;
        try {
            URL url = new URL(apiUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            return json.getAsJsonObject("players").get("online").getAsInt();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getPlayerCountMax(String serverAddress) {
        String apiUrl = "https://eu.mc-api.net/v3/server/ping/" + serverAddress;
        try {
            URL url = new URL(apiUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            return json.getAsJsonObject("players").get("max").getAsInt();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static ImageIcon getLogo(String serverAdress) {
        String serverIconUrl = "https://eu.mc-api.net/v3/server/favicon/"+serverAdress;
        try {
            URL url = new URL(serverIconUrl);
            BufferedImage image = ImageIO.read(url);
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
