package com.maximde.serverstatus;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ToolWindow implements ToolWindowFactory {

    private static final String SERVER_ADRESS = "nextfight.net";

    @Override
    public boolean isApplicable(@NotNull Project project) {
        return ToolWindowFactory.super.isApplicable(project);
    }


    @Override
    public void createToolWindowContent(@NotNull Project project, com.intellij.openapi.wm.@NotNull ToolWindow toolWindow) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        // Create labels for player count and logo
        JLabel playerCountLabel = new JLabel();
        playerCountLabel.setFont(new Font("Arial", Font.BOLD, 24));
        playerCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(playerCountLabel);

        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoLabel);

        ImageIcon logo = ServerData.getLogo(SERVER_ADRESS);
        int maxPlayerCount = ServerData.getPlayerCountMax(SERVER_ADRESS);
        /*
            Update server data every minute
         */
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            int playerCount = ServerData.getPlayerCount(SERVER_ADRESS);

            String playerCountString = playerCount + "/" + maxPlayerCount;
            playerCountLabel.setText(playerCountString);
            logoLabel.setIcon(logo);
        }, 0, 30, TimeUnit.SECONDS);


        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(panel, "", false);
        toolWindow.getContentManager().addContent(content);

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
        return false;
    }

    @Nullable
    @Override
    public ToolWindowAnchor getAnchor() {
        return ToolWindowAnchor.RIGHT;
    }

    @Override
    public Icon getIcon() {
        return new ImageIcon(getClass().getResource("/META-INF/logo_16.png"));
    }
}
