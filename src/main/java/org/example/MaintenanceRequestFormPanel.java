package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MaintenanceRequestFormPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JComboBox<Bus> cbBus;
    private JTextField txtDate;
    private JTextField txtProblem;
    private JComboBox<String> cbPriority;
    private JComboBox<String> cbStatus;

    private final MaintenanceRequestDAO dao = new MaintenanceRequestDAO();
    private final BusDAO busDAO = new BusDAO();

    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MaintenanceRequestFormPanel() {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ===================== TABLE ======================
        model = new DefaultTableModel(
                new String[]{"ID", "Автобус", "Дата", "Проблема", "Пріоритет", "Статус"}, 0
        );
        table = new JTable(model);
        table.setRowHeight(24);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===================== FORM ======================
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Заявка на ремонт"));
        add(formPanel, BorderLayout.SOUTH);

        JPanel grid = new JPanel(new GridBagLayout());
        formPanel.add(grid, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        cbBus = new JComboBox<>();
        txtDate = new JTextField("2025-01-01");
        txtProblem = new JTextField();

        cbPriority = new JComboBox<>(new String[]{"Низький", "Середній", "Високий"});
        cbStatus = new JComboBox<>(new String[]{"Очікує", "В роботі", "Завершено"});

        int row = 0;
        addField(grid, c, row++, "Автобус:", cbBus);
        addField(grid, c, row++, "Дата:", txtDate);
        addField(grid, c, row++, "Проблема:", txtProblem);
        addField(grid, c, row++, "Пріоритет:", cbPriority);
        addField(grid, c, row++, "Статус:", cbStatus);

        // ===================== BUTTONS ======================
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
        btnAdd.addActionListener(e -> addRequest());
        btnUpdate.addActionListener(e -> updateRequest());
        btnDelete.addActionListener(e -> deleteRequest());

        table.getSelectionModel().addListSelectionListener(e -> fillForm());

        loadBuses();
        loadRequests();
    }

    private void addField(JPanel grid, GridBagConstraints c, int row, String name, JComponent comp) {
        c.gridx = 0; c.gridy = row;
        grid.add(new JLabel(name), c);

        c.gridx = 1; c.gridy = row; c.gridwidth = 2;
        comp.setPreferredSize(new Dimension(250, 28));
        grid.add(comp, c);
    }

    private void loadBuses() {
        cbBus.removeAllItems();
        for (Bus b : busDAO.getAllBuses()) cbBus.addItem(b);
    }

    private void loadRequests() {
        model.setRowCount(0);
        for (MaintenanceRequest r : dao.getAllRequests()) {
            model.addRow(new Object[]{
                    r.getId(),
                    r.getBusId(),
                    r.getRequestDate(),
                    r.getProblem(),
                    r.getPriority(),
                    r.getStatus()
            });
        }
    }

    private void addRequest() {
        try {
            Bus b = (Bus) cbBus.getSelectedItem();

            MaintenanceRequest r = new MaintenanceRequest(
                    b.getId(),
                    LocalDate.parse(txtDate.getText(), df),
                    txtProblem.getText(),
                    cbPriority.getSelectedItem().toString(),
                    cbStatus.getSelectedItem().toString()
            );

            dao.addRequest(r);
            loadRequests();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Помилка: " + e.getMessage());
        }
    }

    private void updateRequest() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        try {
            int id = (int) model.getValueAt(row, 0);
            Bus b = (Bus) cbBus.getSelectedItem();

            MaintenanceRequest r = new MaintenanceRequest(
                    id,
                    b.getId(),
                    LocalDate.parse(txtDate.getText(), df),
                    txtProblem.getText(),
                    cbPriority.getSelectedItem().toString(),
                    cbStatus.getSelectedItem().toString()
            );

            dao.updateRequest(r);
            loadRequests();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Помилка: " + e.getMessage());
        }
    }

    private void deleteRequest() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        int id = (int) model.getValueAt(row, 0);
        dao.deleteRequest(id);
        loadRequests();
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        txtDate.setText(model.getValueAt(row, 2).toString());
        txtProblem.setText(model.getValueAt(row, 3).toString());
        cbPriority.setSelectedItem(model.getValueAt(row, 4).toString());
        cbStatus.setSelectedItem(model.getValueAt(row, 5).toString());
    }
}
