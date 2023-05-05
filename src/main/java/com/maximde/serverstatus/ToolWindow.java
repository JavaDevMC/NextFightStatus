package com.maximde.plugin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class ToolWindow implements ToolWindowFactory {

    @Override
    public boolean isApplicable(@NotNull Project project) {
        return ToolWindowFactory.super.isApplicable(project);
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, com.intellij.openapi.wm.@NotNull ToolWindow toolWindow) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        // Ping the Minecraft server and get player count and logo
        int playerCount = getPlayerCount("nextfight.net");
        ImageIcon logo = getLogo("nextfight.net");

        // Add player count label and logo to panel
        JLabel playerCountLabel = new JLabel("Player count: " + playerCount);
        panel.add(playerCountLabel);
        JLabel logoLabel = new JLabel(logo);
        panel.add(logoLabel);

        // Set tool window content
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(panel, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    private int getPlayerCount(String serverAddress) {
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

    private int getPlayerCountMax(String serverAddress) {
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

    private ImageIcon getLogo(String serverAdress) {
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


    @Override
    public void init(com.intellij.openapi.wm.@NotNull ToolWindow toolWindow) {
        ToolWindowFactory.super.init(toolWindow);
    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return ToolWindowFactory.super.shouldBeAvailable(project);
    }

    @Override
    public boolean isDoNotActivateOnStart() {
        return ToolWindowFactory.super.isDoNotActivateOnStart();
    }

    @Nullable
    @Override
    public ToolWindowAnchor getAnchor() {
        return ToolWindowAnchor.RIGHT;
    }

    @Override
    public @Nullable Icon getIcon() {
        return ToolWindowFactory.super.getIcon();
    }
}
