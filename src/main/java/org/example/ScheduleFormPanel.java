package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScheduleFormPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JComboBox<Route> cbRoute;
    private JComboBox<Bus> cbBus;
    private JComboBox<Driver> cbDriver;

    private JTextField txtDeparture;
    private JTextField txtArrival;

    private final ScheduleDAO scheduleDAO = new ScheduleDAO();
    private final RouteDAO routeDAO = new RouteDAO();
    private final BusDAO busDAO = new BusDAO();
    private final DriverDAO driverDAO = new DriverDAO();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ScheduleFormPanel() {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ===========================================
        //               ТАБЛИЦЯ
        // ===========================================
        model = new DefaultTableModel(
                new String[]{"ID", "Маршрут", "Автобус", "Водій", "Відправлення", "Прибуття"}, 0
        );

        table = new JTable(model);
        table.setRowHeight(24);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===========================================
        //             ФОРМА ДАНИХ
        // ===========================================
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Дані розкладу"));
        add(formPanel, BorderLayout.SOUTH);

        JPanel grid = new JPanel(new GridBagLayout());
        formPanel.add(grid, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        cbRoute = new JComboBox<>();
        cbBus = new JComboBox<>();
        cbDriver = new JComboBox<>();

        txtDeparture = new JTextField("2025-01-01 08:00");
        txtArrival = new JTextField("2025-01-01 12:00");

        int row = 0;

        addField(grid, c, row++, "Маршрут:", cbRoute);
        addField(grid, c, row++, "Автобус:", cbBus);
        addField(grid, c, row++, "Водій:", cbDriver);
        addField(grid, c, row++, "Час відправлення:", txtDeparture);
        addField(grid, c, row++, "Час прибуття:", txtArrival);

        // ===========================================
        //                КНОПКИ
        // ===========================================
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnAdd = new JButton("Додати");
        JButton btnUpdate = new JButton("Оновити");
        JButton btnDelete = new JButton("Видалити");

        btnAdd.setPreferredSize(new Dimension(120, 30));
        btnUpdate.setPreferredSize(new Dimension(120, 30));
        btnDelete.setPreferredSize(new Dimension(120, 30));

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);

        formPanel.add(btnPanel, BorderLayout.SOUTH);

        // ===== LISTENERS =====
        btnAdd.addActionListener(e -> addSchedule());
        btnUpdate.addActionListener(e -> updateSchedule());
        btnDelete.addActionListener(e -> deleteSchedule());

        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());

        loadCombos();
        loadSchedules();
    }

    private void addField(JPanel grid, GridBagConstraints c, int row, String label, JComponent field) {
        c.gridx = 0; c.gridy = row; c.gridwidth = 1;
        grid.add(new JLabel(label), c);

        c.gridx = 1; c.gridy = row; c.gridwidth = 2;
        field.setPreferredSize(new Dimension(250, 28));
        grid.add(field, c);
    }

    private void loadCombos() {
        cbRoute.removeAllItems();
        for (Route r : routeDAO.getAllRoutes()) cbRoute.addItem(r);

        cbBus.removeAllItems();
        for (Bus b : busDAO.getAllBuses()) cbBus.addItem(b);

        cbDriver.removeAllItems();
        for (Driver d : driverDAO.getAllDrivers()) cbDriver.addItem(d);
    }

    private void loadSchedules() {
        model.setRowCount(0);
        for (Schedule s : scheduleDAO.getAllSchedules()) {
            model.addRow(new Object[]{
                    s.getId(),
                    s.getRouteId(),
                    s.getBusId(),
                    s.getDriverId(),
                    s.getDepartureTime().format(formatter),
                    s.getArrivalTime().format(formatter)
            });
        }
    }

    private void addSchedule() {
        try {
            Route r = (Route) cbRoute.getSelectedItem();
            Bus b = (Bus) cbBus.getSelectedItem();
            Driver d = (Driver) cbDriver.getSelectedItem();

            if (r == null || b == null || d == null) {
                JOptionPane.showMessageDialog(this, "Оберіть маршрут, автобус і водія!");
                return;
            }

            LocalDateTime dep = LocalDateTime.parse(txtDeparture.getText(), formatter);
            LocalDateTime arr = LocalDateTime.parse(txtArrival.getText(), formatter);

            scheduleDAO.addSchedule(new Schedule(
                    r.getId(), b.getId(), d.getId(), dep, arr
            ));

            loadSchedules();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Помилка: " + ex.getMessage());
        }
    }

    private void updateSchedule() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        try {
            int id = (int) model.getValueAt(row, 0);

            Route r = (Route) cbRoute.getSelectedItem();
            Bus b = (Bus) cbBus.getSelectedItem();
            Driver d = (Driver) cbDriver.getSelectedItem();

            LocalDateTime dep = LocalDateTime.parse(txtDeparture.getText(), formatter);
            LocalDateTime arr = LocalDateTime.parse(txtArrival.getText(), formatter);

            scheduleDAO.updateSchedule(new Schedule(
                    id, r.getId(), b.getId(), d.getId(), dep, arr
            ));

            loadSchedules();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Помилка: " + ex.getMessage());
        }
    }

    private void deleteSchedule() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        int id = (int) model.getValueAt(row, 0);
        scheduleDAO.deleteSchedule(id);
        loadSchedules();
    }

    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        txtDeparture.setText(model.getValueAt(row, 4).toString());
        txtArrival.setText(model.getValueAt(row, 5).toString());
    }
}
