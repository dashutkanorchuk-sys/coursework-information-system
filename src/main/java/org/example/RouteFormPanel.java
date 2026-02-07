package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RouteFormPanel extends JPanel {

    private JTextField txtStart;
    private JTextField txtEnd;
    private JTextField txtDistance;

    private JTable table;
    private DefaultTableModel model;

    private RouteDAO routeDAO = new RouteDAO();

    public RouteFormPanel() {

        setLayout(new BorderLayout());

        // ==== ФОРМА ВВЕДЕННЯ ====
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Дані маршруту"));

        formPanel.add(new JLabel("Початкова точка:"));
        txtStart = new JTextField();
        formPanel.add(txtStart);

        formPanel.add(new JLabel("Кінцева точка:"));
        txtEnd = new JTextField();
        formPanel.add(txtEnd);

        formPanel.add(new JLabel("Дистанція (км):"));
        txtDistance = new JTextField();
        formPanel.add(txtDistance);

        add(formPanel, BorderLayout.NORTH);

        // ==== ТАБЛИЦЯ ====
        model = new DefaultTableModel(new String[]{
                "ID", "Початок", "Кінець", "Дистанція"
        }, 0);

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ==== КНОПКИ ====
        JPanel btnPanel = new JPanel();

        JButton btnAdd = new JButton("Додати");
        JButton btnUpdate = new JButton("Оновити");
        JButton btnDelete = new JButton("Видалити");

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);

        add(btnPanel, BorderLayout.SOUTH);

        // ==== ДІЇ ====

        btnAdd.addActionListener(e -> {
            Route route = new Route(
                    txtStart.getText(),
                    txtEnd.getText(),
                    Double.parseDouble(txtDistance.getText())
            );
            routeDAO.addRoute(route);
            loadRoutes();
        });

        btnUpdate.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected == -1) return;

            int id = Integer.parseInt(model.getValueAt(selected, 0).toString());

            Route route = new Route(
                    id,
                    txtStart.getText(),
                    txtEnd.getText(),
                    Double.parseDouble(txtDistance.getText())
            );

            routeDAO.updateRoute(route);
            loadRoutes();
        });

        btnDelete.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected == -1) return;

            int id = Integer.parseInt(model.getValueAt(selected, 0).toString());
            routeDAO.deleteRoute(id);
            loadRoutes();
        });

        // ==== ЗАВАНТАЖЕННЯ ====
        loadRoutes();
    }

    // ==== ОНОВЛЕННЯ ТАБЛИЦІ ====
    public void loadRoutes() {
        model.setRowCount(0);

        for (Route r : routeDAO.getAllRoutes()) {
            model.addRow(new Object[]{
                    r.getId(),
                    r.getStartPoint(),
                    r.getEndPoint(),
                    r.getDistance()
            });
        }
    }
}
