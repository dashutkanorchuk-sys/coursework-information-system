package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DriverFormPanel extends JPanel {

    private JTextField txtName, txtPhone, txtLicense;
    private JTable table;
    private DefaultTableModel model;

    private DriverDAO driverDAO = new DriverDAO();

    public DriverFormPanel() {

        setLayout(new BorderLayout());

        // -------- Верхня форма --------------------
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Дані водія"));

        form.add(new JLabel("ПІБ:"));
        txtName = new JTextField();
        form.add(txtName);

        form.add(new JLabel("Телефон:"));
        txtPhone = new JTextField();
        form.add(txtPhone);

        form.add(new JLabel("Номер ліцензії:"));
        txtLicense = new JTextField();
        form.add(txtLicense);

        add(form, BorderLayout.NORTH);

        // ------------- Таблиця ---------------------
        model = new DefaultTableModel(new String[]{"ID", "ПІБ", "Телефон", "Номер ліцензії"}, 0);
        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // ------------- Кнопки ----------------------
        JPanel buttons = new JPanel();

        JButton btnAdd = new JButton("Додати");
        JButton btnUpdate = new JButton("Оновити");
        JButton btnDelete = new JButton("Видалити");

        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);

        add(buttons, BorderLayout.SOUTH);

        // ========= ОБРОБНИКИ =============

        btnAdd.addActionListener(e -> {
            driverDAO.addDriver(new Driver(0,
                    txtName.getText(),
                    txtPhone.getText(),
                    txtLicense.getText()
            ));
            loadDrivers();
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) return;

            int id = (int) model.getValueAt(row, 0);

            driverDAO.updateDriver(new Driver(
                    id,
                    txtName.getText(),
                    txtPhone.getText(),
                    txtLicense.getText()
            ));

            loadDrivers();
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) return;
            int id = (int) model.getValueAt(row, 0);
            driverDAO.deleteDriver(id);
            loadDrivers();
        });

        loadDrivers();
    }

    private void loadDrivers() {
        model.setRowCount(0);

        List<Driver> list = driverDAO.getAllDrivers();
        for (Driver d : list) {
            model.addRow(new Object[]{
                    d.getId(),
                    d.getName(),
                    d.getPhone(),
                    d.getLicense()
            });
        }
    }
}
