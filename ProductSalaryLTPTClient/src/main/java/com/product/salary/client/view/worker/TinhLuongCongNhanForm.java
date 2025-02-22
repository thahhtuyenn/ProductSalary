package com.product.salary.client.view.worker;

/**
 * @author Trần Tuấn Kiệt: Code giao diện
 * @author Lê Đôn Chủng: Code xử lý
 */

import com.product.salary.application.utils.*;
import com.product.salary.client.common.SystemConstants;
import com.product.salary.application.utils.pdf.LuongCongNhanPdfUtils;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class TinhLuongCongNhanForm extends JPanel {
    private final ResourceBundle BUNDLE = ResourceBundle.getBundle("app");
    private final JYearChooser yearChooser;
    private final JButton btnTinhLuong;
    private final JButton btnExport;
    private final DefaultTableModel tableModelLuongCongNhan;
    private final JTable tblLuongCongNhan;
    private final JMonthChooser monthChooseer;
    private List<Map<String, Object>> luongCongNhans;
    private final JButton btnGuiLuong;

    /**
     * Create the panel.
     */
    public TinhLuongCongNhanForm() {

        init();

        setLayout(null);

        JPanel pnlMain = new JPanel();
        pnlMain.setBounds(10, 10, 1213, 821);
        pnlMain.setLayout(null);
        add(pnlMain);

        JLabel lblTitle = new JLabel(SystemConstants.BUNDLE.getString("luongCongNhan.title"));
        lblTitle.setBorder(null);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblTitle.setBounds(0, 0, 1250, 80);
        pnlMain.add(lblTitle);

        JPanel pnlField = new JPanel();
        pnlField.setBounds(10, 90, 1193, 100);
        pnlMain.add(pnlField);
        pnlField.setLayout(null);

        yearChooser = new JYearChooser();
        yearChooser.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        yearChooser.getSpinner().setFont(new Font("Times New Roman", Font.PLAIN, 16));
        yearChooser.setBounds(396, 10, 100, 40);
        pnlField.add(yearChooser);

        monthChooseer = new JMonthChooser();
        monthChooseer.getComboBox().setFont(new Font("Times New Roman", Font.PLAIN, 16));
        monthChooseer.getSpinner().setFont(new Font("Times New Roman", Font.PLAIN, 16));
        monthChooseer.setBounds(151, 10, 120, 40);
        pnlField.add(monthChooseer);

        JLabel lblThang = new JLabel(SystemConstants.BUNDLE.getString("luongCongNhan.thang"));
        lblThang.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblThang.setBounds(81, 10, 60, 40);
        pnlField.add(lblThang);

        JLabel lblNam = new JLabel(SystemConstants.BUNDLE.getString("luongCongNhan.nam"));
        lblNam.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblNam.setBounds(336, 10, 60, 40);
        pnlField.add(lblNam);

        btnTinhLuong = new JButton(String.format("<html><p>%s</p></html>",
                SystemConstants.BUNDLE.getString("luongCongNhan.btnTinhLuong")));
        btnTinhLuong.setBounds(609, 6, 170, 44);
        pnlField.add(btnTinhLuong);
        btnTinhLuong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btnTinhLuong.setIcon(new ImageIcon("src/main/resources/icon/png/ic_add.png"));

        btnExport = new JButton(String.format("<html><p>%s</p></html>",
                SystemConstants.BUNDLE.getString("luongCongNhan.btnXuatBaoCao")));
        btnExport.setIcon(new ImageIcon("src/main/resources/icon/png/ic_pdf.png"));
        btnExport.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btnExport.setBounds(806, 6, 170, 44);
        pnlField.add(btnExport);

        btnGuiLuong = new JButton(
                String.format("<html><p>%s</p></html>", SystemConstants.BUNDLE.getString("luongCongNhan.btnGuiLuong")));
        btnExport.setIcon(new ImageIcon("src/main/resources/icon/png/ic_pdf.png"));
        btnGuiLuong
                .setIcon(new ImageIcon("src/main/resources/icon/png/ic_send.png"));
        btnGuiLuong.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btnGuiLuong.setBounds(1001, 6, 170, 44);
        pnlField.add(btnGuiLuong);

        String hanhDong = "Update salary bonus";
        if (SystemConstants.LANGUAGE == 0) {
            hanhDong = "Cập nhật lưởng thưởng";
        }
        tableModelLuongCongNhan = new DefaultTableModel(
                new String[]{
                        String.format("<html><p>%s</p></html>",
                                SystemConstants.BUNDLE.getString("luongCongNhan.tableCongNhan.STT")),
                        String.format("<html><p>%s</p></html>",
                                SystemConstants.BUNDLE.getString("luongCongNhan.tableCongNhan.maLuong")),
                        String.format("<html><p>%s</p></html>",
                                SystemConstants.BUNDLE.getString("luongCongNhan.tableCongNhan.maCongNhan")),
                        String.format("<html><p>%s</p></html>",
                                SystemConstants.BUNDLE.getString("luongCongNhan.tableCongNhan.tenCongNhan")),
                        String.format("<html><p>%s</p></html>",
                                SystemConstants.BUNDLE.getString("luongCongNhan.tableCongNhan.gioiTinh")),
                        String.format("<html><p>%s</p></html>",
                                SystemConstants.BUNDLE.getString("luongCongNhan.tableCongNhan.soDienThoai")),
                        String.format("<html><p>%s</p></html>",
                                SystemConstants.BUNDLE.getString("luongCongNhan.tableCongNhan.luongThang")),
                        String.format("<html><p>%s</p></html>",
                                SystemConstants.BUNDLE.getString("luongCongNhan.tableCongNhan.troCap")),
                        String.format("<html><p>%s</p></html>",
                                SystemConstants.BUNDLE.getString("luongCongNhan.tableCongNhan.luongThuong")),
                        String.format("<html><p>%s</p></html>",
                                SystemConstants.BUNDLE.getString("luongCongNhan.tableCongNhan.tongLuong")),
                        String.format("<html><p>%s</p></html>",
                                SystemConstants.BUNDLE.getString("luongCongNhan.tableCongNhan.ngayTinhLuong")),
                        hanhDong},
                0);

        tblLuongCongNhan = new JTable(tableModelLuongCongNhan) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        tblLuongCongNhan.getColumn(hanhDong).setCellRenderer(new ButtonRenderer());
        tblLuongCongNhan.getColumn(hanhDong).setCellEditor(new ButtonEditor(new JCheckBox()));

        tblLuongCongNhan.setShowVerticalLines(true);
        tblLuongCongNhan.setShowHorizontalLines(true);
        tblLuongCongNhan.setRowHeight(25);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
        tblLuongCongNhan.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        tblLuongCongNhan.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        tblLuongCongNhan.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
        JScrollPane scrLuong = new JScrollPane(tblLuongCongNhan);
        scrLuong.setBounds(10, 200, 1193, 515);
        pnlMain.add(scrLuong);

        event();
    }

    private void init() {
        this.luongCongNhans = new ArrayList<>();
    }

    private void event() {
        this.btnGuiLuong.addActionListener((e) -> thucHienChucNangGuiThongTin());
        this.btnTinhLuong.addActionListener((e) -> thucHienChucNangTinhLuong());
        this.btnExport.addActionListener((e) -> thucHienChucNangXuatDanhSach());
        tblLuongCongNhan.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (tblLuongCongNhan.getSelectedRow() != -1 && e.getClickCount() == 2) {
                    try {
                        int index = tblLuongCongNhan.getSelectedRow();
                        Map<String, Object> result = luongCongNhans.get(index);

                        new ChiTietLuongCongNhanForm(result.get("MaCongNhan").toString(),
                                Integer.valueOf(result.get("LuongThang").toString().replace(".0", "")), yearChooser.getYear())
                                .setVisible(true);
                    } catch (Exception x) {
                        x.printStackTrace();
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void thucHienChucNangGuiThongTin() {
//        boolean status = false;
//        for (Map<String, Object> luongCongNhan : luongCongNhans) {
//            String maCongNhan = luongCongNhan.get("MaCongNhan").toString();
//            CongNhan congNhan = congNhanService.timKiemBangMaCongNhan(maCongNhan);
//
//            int year = yearChooser.getYear();
//            int month = monthChooseer.getMonth() + 1;
//            String message = String.format("CTK Home thông báo: Lương tháng %s năm %s của công nhân %s là: %s", month,
//                    year, congNhan.getHoTen() + " - " + congNhan.getMaCongNhan(),
//                    PriceFormatterUtils.format(Double.valueOf(luongCongNhan.get("TongLuong").toString())));
//            String phone = String.format("84%s", congNhan.getSoDienThoai().substring(1));
//            PhoneUtils.sendNotification(phone, message);
//            status = true;
//        }
//        if (status) {
//            JOptionPane.showMessageDialog(this,
//                    SystemConstants.BUNDLE.getString("luongCongNhan.thongBao.guiThanhCong"));
//        } else {
//            JOptionPane.showMessageDialog(this, SystemConstants.BUNDLE.getString("luongCongNhan.thongBao.guiThatBai"));
//        }
    }

    private void thucHienChucNangXuatDanhSach() {
        int year = yearChooser.getYear();
        int month = monthChooseer.getMonth() + 1;
        LuongCongNhanPdfUtils.writeLuongCongNhan(month, year, luongCongNhans);
    }

    private void thucHienChucNangTinhLuong() {
        new Thread(() -> {
            try (var socket = new Socket(
                    BUNDLE.getString("host"),
                    Integer.parseInt(BUNDLE.getString("server.port")));
                 var dos = new DataOutputStream(socket.getOutputStream());
                 var dis = new DataInputStream(socket.getInputStream())) {

                int year = yearChooser.getYear();
                int month = monthChooseer.getMonth() + 1;

                // send request
                RequestDTO request = RequestDTO
                        .builder()
                        .request("tinhLuongCongNhan")
                        .requestType("LuongCongNhanForm")
                        .data(Map.of("thang", month, "nam", year))
                        .build();
                String json = AppUtils.GSON.toJson(request);
                dos.writeUTF(json);
                dos.flush();

                // receive response
                json = new String(dis.readAllBytes());
                ResponseDTO response = AppUtils.GSON.fromJson(json, ResponseDTO.class);

                boolean trangThai = (boolean) ((Map<String, Object>) response.getData()).get("result");
                if (trangThai) {

                    String message = "";
                    if (SystemConstants.LANGUAGE == 0) {
                        message = String.format("Tính lương công nhân %02d/%04d thành công.", month, year);
                    } else {
                        message = String.format("Calculating worker salary %02d/%04d successfully.", month, year);
                    }
                    JOptionPane.showMessageDialog(null, message);
                    List<Map<String, Object>> luongCongNhans = (List<Map<String, Object>>) ((Map<String, Object>) response.getData()).get("luongCongNhans");
                    loadDanhSachLuong(luongCongNhans);
                } else {
                    thucHienChucNangLamMoi();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void thucHienChucNangLamMoi() {
        yearChooser.setYear(LocalDate.now().getYear());
        monthChooseer.setMonth(LocalDate.now().getMonthValue());
        this.tableModelLuongCongNhan.setRowCount(0);
    }

    private void loadDanhSachLuong(List<Map<String, Object>> luongCongNhans) {
        this.luongCongNhans = luongCongNhans;
        tableModelLuongCongNhan.setRowCount(0);
        int stt = 1;
        for (Map<String, Object> luongCongNhan : this.luongCongNhans) {
            tableModelLuongCongNhan.addRow(new Object[]{stt++, luongCongNhan.get("MaLuong"),
                    luongCongNhan.get("MaCongNhan"), luongCongNhan.get("TenCongNhan"), luongCongNhan.get("GioiTinh"),
                    luongCongNhan.get("SoDienThoai"), luongCongNhan.get("LuongThang").toString().replace(".0", ""),
                    PriceFormatterUtils.format(Double.valueOf(luongCongNhan.get("TroCap").toString())),
                    PriceFormatterUtils.format(Double.valueOf(luongCongNhan.get("LuongThuong").toString())),
                    PriceFormatterUtils.format(Double.valueOf(luongCongNhan.get("TongLuong").toString())),
                    luongCongNhan.get("NgayTinhLuong"), new ButtonRenderer()});
        }
    }

    private void thucHienChucNangCapNhatLuongThuong(double luongThuong) {
        new Thread(() -> {
            try (var socket = new Socket(
                    BUNDLE.getString("host"),
                    Integer.parseInt(BUNDLE.getString("server.port")));
                 var dos = new DataOutputStream(socket.getOutputStream());
                 var dis = new DataInputStream(socket.getInputStream())) {
                int index = this.tblLuongCongNhan.getSelectedRow();
                if (index >= 0) {
                    Map<String, Object> luongCongNhan = luongCongNhans.get(index);
                    Map<String, Object> data = Map.of("MaLuong", luongCongNhan.get("MaLuong").toString(), "LuongThuong", luongThuong);

                    // send request
                    RequestDTO request = RequestDTO
                            .builder()
                            .request("capNhatLuongThuong")
                            .requestType("LuongCongNhanForm")
                            .data(data)
                            .build();

                    String json = AppUtils.GSON.toJson(request);
                    dos.writeUTF(json);
                    dos.flush();

                    // receive response
                    json = new String(dis.readAllBytes());
                    ResponseDTO response = AppUtils.GSON.fromJson(json, ResponseDTO.class);
                    boolean trangThai = (boolean) response.getData();
                    if (trangThai) {
                        thucHienChucNangTinhLuong();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }

            String txtUpDateLuongThuong = "Cập nhật";
            if (SystemConstants.LANGUAGE == 1) {
                txtUpDateLuongThuong = "Update";
            }
            setText(txtUpDateLuongThuong);
            return this;
        }
    }


    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> {
                String value = JOptionPane.showInputDialog(null, "Nhập lương thưởng", 0);
                thucHienChucNangCapNhatLuongThuong(PriceFormatterUtils.parse(value));
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                                                     int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = "Cập nhật";
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                JOptionPane.showMessageDialog(button, label + ": Ouch!");
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}


