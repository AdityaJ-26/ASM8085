package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import cpu.CPU;
import operation.*;
import instruction.Instruction;

public class GUI extends JFrame {

    private final CPU cpu = new CPU();

    JTextArea memTab  = new JTextArea();
    JTextArea flagTab = new JTextArea();
    JTextArea regTab  = new JTextArea();
    private int workingAddress;

    public GUI() {
        setTitle("ASM8085");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        // ImageIcon logo = new ImageIcon(getClass().getResource("assets/logo.png"));
        // setIconImage(logo.getImage());

        Color BGColor = new Color(18, 18, 24);
        Color PANEL = new Color(28, 28, 36);
        Color BORDER = new Color(55, 55, 70);
        Color TEXT = new Color(200, 210, 230);
        Color ACCENT = new Color(80, 160, 255);
        Color MUTED = new Color(100, 110, 130);
        Font  MONO = new Font("Monospaced", Font.PLAIN, 15);
        Font  LABEL = new Font("SansSerif", Font.BOLD, 13);

        getContentPane().setBackground(BGColor);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("   8085 Simulator", SwingConstants.LEFT);
        title.setFont(new Font("Monospaced", Font.BOLD, 18));
        title.setForeground(ACCENT);
        title.setBorder(BorderFactory.createEmptyBorder(14, 14, 6, 14));
        title.setOpaque(true);
        title.setBackground(BGColor);
        add(title, BorderLayout.NORTH);

        JPanel statusRow = new JPanel(new GridLayout(1, 3, 10, 0));
        statusRow.setBackground(BGColor);
        statusRow.setBorder(BorderFactory.createEmptyBorder(0, 14, 0, 14));

        memTab = makeStatusArea(MONO, TEXT, PANEL);
        flagTab = makeStatusArea(MONO, TEXT, PANEL);
        regTab = makeStatusArea(MONO, TEXT, PANEL);

        statusRow.add(makeCard("MEMORY", memTab,  PANEL, BORDER, ACCENT, LABEL));
        statusRow.add(makeCard("FLAGS", flagTab, PANEL, BORDER, ACCENT, LABEL));
        statusRow.add(makeCard("REGISTERS", regTab,  PANEL, BORDER, ACCENT, LABEL));

        add(statusRow, BorderLayout.CENTER);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        buttonRow.setBackground(BGColor);
        buttonRow.setBorder(BorderFactory.createEmptyBorder(6, 0, 16, 0));

        JButton inputButton = makeButton("> INPUT <" , ACCENT, BGColor);
        JButton memButton = makeButton("> MEMORY ACCESS <", ACCENT, BGColor);
        JButton executeButton = makeButton("> EXECUTE <", ACCENT, BGColor);

        buttonRow.add(inputButton);
        buttonRow.add(memButton);
        buttonRow.add(executeButton);
        add(buttonRow, BorderLayout.SOUTH);

        new Timer(300, e -> refreshStatus()).start();


        inputButton.addActionListener(e -> {
            String startAddress = JOptionPane.showInputDialog(this, "Enter Starting Address:");
            if (startAddress == null) return;
            int address = StringConversion.hexaToDecimal(startAddress);

            workingAddress = address;
            cpu.inputAddress(address);

            while (true) {
                String line = JOptionPane.showInputDialog(this, "Address: " + StringConversion.decimalToHexa(address));
                if (line == null || line.equals("Q")) break;
                address = cpu.input(line);
            }
            refreshStatus();
        });

        memButton.addActionListener(e -> {
            String startAddress = JOptionPane.showInputDialog(this, "Enter Access Address:");
            if (startAddress == null) return;
            int address = StringConversion.hexaToDecimal(startAddress);
            while (true) {
                String val = JOptionPane.showInputDialog(this, StringConversion.decimalToHexa(address) + " : " + cpu.memRead(address));
                if (val.equals("Q")) break;
                if (val.length() == 3) cpu.memWrite(address, val);
                address++;
            }
            refreshStatus();
        });

        executeButton.addActionListener(e -> {
            String startAddress = JOptionPane.showInputDialog(this, "Enter Execution Address:");
            if (startAddress == null) return;
            try {
                cpu.execute(StringConversion.hexaToDecimal(startAddress));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
            refreshStatus();
        });

        setVisible(true);
        refreshStatus();
    }

    private void refreshStatus() {
        StringBuilder mem = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            String addr = StringConversion.decimalToHexa(i);
            mem.append(String.format("%-4s : %s%n", StringConversion.decimalToHexa(workingAddress + i), cpu.memRead(workingAddress + i)));
        }
        memTab.setText(mem.toString());

        flagTab.setText(cpu.flagStatus());

        regTab.setText(cpu.registerStatus());
    }

    private JTextArea makeStatusArea(Font font, Color text, Color bg) {
        JTextArea area = new JTextArea();
        area.setFont(font);
        area.setForeground(text);
        area.setBackground(bg);
        area.setEditable(false);
        area.setMargin(new Insets(8, 10, 8, 10));
        return area;
    }

    private JPanel makeCard(String label, JTextArea area, Color bg, Color border, Color accent, Font labelFont) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bg);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(border, 1),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        JLabel header = new JLabel("  " + label);
        header.setFont(labelFont);
        header.setForeground(accent);
        header.setOpaque(true);
        header.setBackground(new Color(35, 35, 50));
        header.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        card.add(header, BorderLayout.NORTH);
        card.add(new JScrollPane(area), BorderLayout.CENTER);
        return card;
    }

    private JButton makeButton(String text, Color fg, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Monospaced", Font.BOLD, 14));
        btn.setForeground(fg);
        btn.setBackground(new Color(35, 35, 50));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(fg, 1),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(50, 80, 130)); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(new Color(35, 35, 50)); }
        });
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}