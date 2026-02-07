package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TicketFormPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JComboBox<Passenger> cbPassenger;
    private JComboBox<Route> cbRoute;
    private JComboBox<Bus> cbBus;

    private JTextField txtDate;
    private JTextField txtPrice;
    private JTextField txtStatus;

    private final TicketDAO ticketDAO = new TicketDAO();
    private final PassengerDAO passengerDAO = new PassengerDAO();
    private final RouteDAO routeDAO = new RouteDAO();
    private final BusDAO busDAO = new BusDAO();

    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public TicketFormPanel() {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ===================== TABLE ======================
        model = new DefaultTableModel(
                new String[]{"ID", "Пасажир", "Маршрут", "Автобус", "Дата", "Ціна", "Статус"}, 0
        );
        table = new JTable(model);
        table.setRowHeight(24);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===================== FORM ========================
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Дані квитка"));
        add(formPanel, BorderLayout.SOUTH);

        JPanel grid = new JPanel(new GridBagLayout());
        formPanel.add(grid, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        cbPassenger = new JComboBox<>();
        cbRoute = new JComboBox<>();
        cbBus = new JComboBox<>();

        txtDate = new JTextField("2025-01-01");
        txtPrice = new JTextField();
        txtStatus = new JTextField();

        int row = 0;
        addField(grid, c, row++, "Пасажир:", cbPassenger);
        addField(grid, c, row++, "Маршрут:", cbRoute);
        addField(grid, c, row++, "Автобус:", cbBus);
        addField(grid, c, row++, "Дата:", txtDate);
        addField(grid, c, row++, "Ціна:", txtPrice);
        addField(grid, c, row++, "Статус:", txtStatus);

        // ===================== BUTTONS =======================
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

        // LISTENERS
        btnAdd.addActionListener(e -> addTicket());
        btnUpdate.addActionListener(e -> updateTicket());
        btnDelete.addActionListener(e -> deleteTicket());
        table.getSelectionModel().addListSelectionListener(e -> fillForm());

        loadCombos();
        loadTickets();
    }

    private void addField(JPanel grid, GridBagConstraints c, int row, String name, JComponent comp) {
        c.gridx = 0; c.gridy = row;
        grid.add(new JLabel(name), c);

        c.gridx = 1; c.gridy = row; c.gridwidth = 2;
        comp.setPreferredSize(new Dimension(250, 28));
        grid.add(comp, c);
    }

    private void loadCombos() {
        cbPassenger.removeAllItems();
        for (Passenger p : passengerDAO.getAllPassengers()) cbPassenger.addItem(p);

        cbRoute.removeAllItems();
        for (Route r : routeDAO.getAllRoutes()) cbRoute.addItem(r);

        cbBus.removeAllItems();
        for (Bus b : busDAO.getAllBuses()) cbBus.addItem(b);
    }

    private void loadTickets() {
        model.setRowCount(0);
        for (Ticket t : ticketDAO.getAllTickets()) {
            model.addRow(new Object[]{
                    t.getId(),
                    t.getPassengerId(),
                    t.getRouteId(),
                    t.getBusId(),
                    t.getDepartureDate(),
                    t.getPrice(),
                    t.getStatus()
            });
        }
    }

    private void addTicket() {
        try {
            Passenger p = (Passenger) cbPassenger.getSelectedItem();
            Route r = (Route) cbRoute.getSelectedItem();
            Bus b = (Bus) cbBus.getSelectedItem();

            Ticket t = new Ticket(
                    p.getId(),
                    r.getId(),
                    b.getId(),
                    LocalDate.parse(txtDate.getText(), df),
                    Double.parseDouble(txtPrice.getText()),
                    txtStatus.getText()
            );

            ticketDAO.addTicket(t);
            loadTickets();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Помилка: " + e.getMessage());
        }
    }

    private void updateTicket() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        try {
            int id = (int) model.getValueAt(row, 0);

            Passenger p = (Passenger) cbPassenger.getSelectedItem();
            Route r = (Route) cbRoute.getSelectedItem();
            Bus b = (Bus) cbBus.getSelectedItem();

            Ticket t = new Ticket(
                    id,
                    p.getId(),
                    r.getId(),
                    b.getId(),
                    LocalDate.parse(txtDate.getText(), df),
                    Double.parseDouble(txtPrice.getText()),
                    txtStatus.getText()
            );

            ticketDAO.updateTicket(t);
            loadTickets();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Помилка: " + e.getMessage());
        }
    }

    private void deleteTicket() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        int id = (int) model.getValueAt(row, 0);
        ticketDAO.deleteTicket(id);
        loadTickets();
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        txtDate.setText(model.getValueAt(row, 4).toString());
        txtPrice.setText(model.getValueAt(row, 5).toString());
        txtStatus.setText(model.getValueAt(row, 6).toString());
    }
}
