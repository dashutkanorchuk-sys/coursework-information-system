package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BusFormPanel extends JPanel {

    private JTextField txtModel, txtSeats, txtPlate;
    private JTable table;
    private DefaultTableModel model;

    private BusDAO busDAO = new BusDAO();

    public BusFormPanel() {

        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Дані автобуса"));

        form.add(new JLabel("Модель:"));
        txtModel = new JTextField();
        form.add(txtModel);

        form.add(new JLabel("Кількість місць:"));
        txtSeats = new JTextField();
        form.add(txtSeats);

        form.add(new JLabel("Номерний знак:"));
        txtPlate = new JTextField();
        form.add(txtPlate);

        add(form, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Модель", "Місця", "Номер"}, 0);
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

        // ------- HANDLERS ----------

        btnAdd.addActionListener(e -> {
            busDAO.addBus(new Bus(
                    0,
                    txtModel.getText(),
                    Integer.parseInt(txtSeats.getText()),
                    txtPlate.getText()
            ));
            loadBuses();
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) return;

            int id = (int) model.getValueAt(row, 0);

            busDAO.updateBus(new Bus(
                    id,
                    txtModel.getText(),
                    Integer.parseInt(txtSeats.getText()),
                    txtPlate.getText()
            ));
            loadBuses();
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) return;

            int id = (int) model.getValueAt(row, 0);
            busDAO.deleteBus(id);
            loadBuses();
        });

        loadBuses();
    }

    private void loadBuses() {
        model.setRowCount(0);

        List<Bus> list = busDAO.getAllBuses();

        for (Bus b : list)
            model.addRow(new Object[]{b.getId(), b.getModel(), b.getSeats(), b.getPlateNumber()});
    }
}
