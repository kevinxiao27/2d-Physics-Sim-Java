package ui;

import java.awt.*;

// store constants used across different classes
public class Constants {
    public static final Dimension GUI_SIZE = new Dimension(1300, 760);
    public static final Dimension LIST_COMPONENT_SIZE = new Dimension(580, 760);
    public static final Dimension COLLIDER_PANEL_SIZE = new Dimension(LIST_COMPONENT_SIZE.width - 30,
            LIST_COMPONENT_SIZE.height - 175);
    public static final Dimension SIM_PANEL_SIZE = new Dimension(400, 300);

    public static final Dimension ADD_COLLIDER_BUTTON = new Dimension(LIST_COMPONENT_SIZE.width - 80, 50);
    public static final Dimension TICK_SIMULATION_BUTTON = new Dimension(GUI_SIZE.width
            - LIST_COMPONENT_SIZE.width - 300,
            50);

    public static final Dimension COLLIDER_PERSISTENCE = new Dimension(COLLIDER_PANEL_SIZE.width,
            50);
    public static final Dimension BANNER_SIZE = new Dimension(400, 50);
    public static final Dimension COLLIDER_FIELD_SIZE = new Dimension((int)(COLLIDER_PANEL_SIZE.width / 6), 20);
    public static final Dimension ADD_CHECK_SIZE = new Dimension(50, 50);
    public static final Dimension DELETE_BUTTON_SIZE = new Dimension(50, 50);
}