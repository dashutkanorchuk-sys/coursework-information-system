package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PassengerFormPanel extends JPanel {

    private JTextField txtName, txtPhone, txtEmail;
    private JTable table;
    private DefaultTableModel model;

    private PassengerDAO passengerDAO = new PassengerDAO();

    public PassengerFormPanel() {

        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Пасажир"));

        form.add(new JLabel("ПІБ:"));
        txtName = new JTextField();
        form.add(txtName);

        form.add(new JLabel("Телефон:"));
        txtPhone = new JTextField();
        form.add(txtPhone);

        form.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        form.add(txtEmail);

        add(form, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "ПІБ", "Телефон", "Email"}, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton btnAdd = new JButton("Додати");
        JButton btnUpdate = new JButton("Оновити");
        JButton btnDelete = new JButton("Видалити");

        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);

        add(buttons, BorderLayout.SOUTH);

        /// ==== HANDLERS ======

        btnAdd.addActionListener(e -> {
            passengerDAO.addPassenger(new Passenger(
                    0,
                    txtName.getText(),
                    txtPhone.getText(),
                    txtEmail.getText()
            ));
            loadPassengers();
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) return;

            int id = (int) model.getValueAt(row, 0);

            passengerDAO.updatePassenger(new Passenger(
                    id,
                    txtName.getText(),
                    txtPhone.getText(),
                    txtEmail.getText()
            ));

            loadPassengers();
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) return;

            int id = (int) model.getValueAt(row, 0);
            passengerDAO.deletePassenger(id);
            loadPassengers();
        });

        loadPassengers();
    }

    private void loadPassengers() {
        model.setRowCount(0);

        List<Passenger> list = passengerDAO.getAllPassengers();

        for (Passenger p : list)
            model.addRow(new Object[]{
                    p.getId(),
                    p.getName(),
                    p.getPhone(),
                    p.getEmail()
            });
    }
}
