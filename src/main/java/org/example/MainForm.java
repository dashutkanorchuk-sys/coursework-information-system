package org.example;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {

    public MainForm() {
        setTitle("Bus Management System");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        // ==== ВКЛАДКИ ====
        tabs.addTab("Автобуси", new BusFormPanel());
        tabs.addTab("Водії", new DriverFormPanel());
        tabs.addTab("Пасажири", new PassengerFormPanel());
        tabs.addTab("Маршрути", new RouteFormPanel());
        tabs.addTab("Розклад", new ScheduleFormPanel());
        tabs.addTab("Квитки", new TicketFormPanel());
        tabs.addTab("Ремонт", new MaintenanceRequestFormPanel());

        add(tabs, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainForm frame = new MainForm();
            frame.setVisible(true);
        });
    }
}
