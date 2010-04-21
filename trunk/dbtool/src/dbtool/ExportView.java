/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * ExportTool.java
 *
 * Created on 2009-12-21, 9:14:55
 */
package dbtool;

import java.awt.Cursor;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author xhwu
 */
public class ExportView extends JFrame {

    private DefaultListModel logModel = new DefaultListModel();
    DefaultListModel selectedTablesModel = new DefaultListModel();//全局变量，右侧jList用来存储已经选择的所有报表名称
    private DefaultListModel selectedStscModel = new DefaultListModel();//全局变量，右侧jLiist 用来存储已经选择的所有测站编码
    private DefaultListModel selectedSnameModel = new DefaultListModel();//全局变量，右侧jList 用来存储已经选择的所有＝【测站编码】测站名称
    private DefaultListModel selectedYearsModel = new DefaultListModel();
    final DefaultListModel stscNameModel_source = new DefaultListModel();
    final DefaultListModel listParamModel_source = new DefaultListModel();//全局变量，左侧jList 用来存储已经选择的所有＝【测站编码】测站名称
    final DefaultListModel liststscModel_source = new DefaultListModel();//全局变量，左侧jList 用来存储已经选择的所有测站编码
    DBTool dbTool = null;
    String JdbcUrl = "";
    String DiverClass = "";
    String expModel = "";
    String exportTabStr = "";
    boolean excel = true;
    int expType = 1;
    String filepath = "";
    private int byteread;

    /**
     * 全局变量：源数据库参数
     * 取得这个窗口上的参数数据
     * 对象名称：cbDriver:：数据库驱动类型
     * 对象名称：txtIP:：服务器ip地址
     * 对象名称：txtUser:：用户名
     * 对象名称：txtPass：:密码
     * 对象名称：txtDataDir:：导出目录
     * 对象名称：txtDBName:：数据库名称
     */
    /** Creates new form ExportTool */
    @SuppressWarnings("unchecked")
    public ExportView() {
        initComponents();

        tbMainSelect.remove(ExpView);//删除后不再显示
        tbMainSelect.remove(SelectTable);//选择导出表的，下一步的时候加载出来。
        tbMainSelect.remove(SelectStsc);//选择测站编码的页面，选择报表后，下一步的时候加载
        tbMainSelect.remove(ExpData);//导出数据的页面，选择测站后，点开始导出按钮的时候加载
        tbMainSelect.remove(ExpModelSet);//导出模式设置，是全部导出，还是按年份导出
        tbMainSelect.remove(DataView);//数据查看的页面，在没有设置参数的时候不显示
        tbMainSelect.remove(dataIndexPanel);//数据索引页面，设置数据库初始参数后，一直显示
        jButton5.setVisible(false);//隐藏“重新读取”的按钮
//        exportType.setSelectedIndex(0);
//        exportType.setEnabled(false);
//        exportType.removeItemAt(1);
        charBox.setEnabled(false);
        ButtonGroup bgroup = new ButtonGroup();
        bgroup.add(jRadioButtonAll);
        bgroup.add(jRadioButtonY);
        jRadioButtonAll.setSelected(true);
        ButtonGroup bgroup2 = new ButtonGroup();
        bgroup2.add(jRadioButton1);
        bgroup2.add(jRadioButton2);
        jRadioButton1.setSelected(true);
        jRadioButton2.setEnabled(false);
        btnPreview2.setVisible(false);
        DefaultListModel yearModel = new DefaultListModel();
        int currentYear = (Calendar.getInstance()).get(Calendar.YEAR);
        for (int i = currentYear; i > currentYear - 100; i--) {
            yearModel.addElement(i);
        }
        jList1.setModel(yearModel);
        jList2.setModel(new DefaultListModel());
        jList1.setEnabled(false);
        jList2.setEnabled(false);

        addButtonYear.setEnabled(false);
        addButtonYears.setEnabled(false);
        removeButtonYear.setEnabled(false);
        removeButtonYears.setEnabled(false);

        DefaultComboBoxModel comboxModel = new DefaultComboBoxModel();
        for (String t : Consts.jdbcclass) {
            comboxModel.addElement(t);
        }
        cbDriver.setModel(comboxModel);
        
        File file = new File("c://stcd.xls");
        if (!file.exists()) {
            try {
                InputStream is = ExportView.class.getResourceAsStream("/stcd.xls");
                FileOutputStream fs = new FileOutputStream("c:/stcd.xls");
                byte[] buffer = new byte[1444];
                while ((byteread = is.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        File indexfile = new File("c://tables.xls");
        if (!indexfile.exists()) {
            try {
                InputStream is = ExportView.class.getResourceAsStream("/tables.xls");
                FileOutputStream fs = new FileOutputStream("c:/tables.xls");
                byte[] buffer = new byte[1444];
                while ((byteread = is.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NoTablesDialog = new javax.swing.JDialog();
        buttonGroup1 = new javax.swing.ButtonGroup();
        tbMainSelect = new javax.swing.JTabbedPane();
        ParametSet = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbDriver = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtIP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDataDir = new javax.swing.JTextField();
        NextBtnOne = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        DataName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPort = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        exportType = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        Version = new javax.swing.JComboBox();
        TestConn = new javax.swing.JButton();
        charBox = new javax.swing.JCheckBox();
        SelectTable = new javax.swing.JPanel();
        SelectTableLPanel = new javax.swing.JScrollPane();
        ListTables = new javax.swing.JList();
        AddBtn = new javax.swing.JButton();
        CancelBtn = new javax.swing.JButton();
        SelectTableRPanel = new javax.swing.JScrollPane();
        SelectedTables = new javax.swing.JList();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnTableNextStep = new javax.swing.JButton();
        AddBtnAllTables = new javax.swing.JButton();
        CancelBtnAllTables = new javax.swing.JButton();
        btnTablePerStep = new javax.swing.JButton();
        btnPreview2 = new javax.swing.JButton();
        SelectStsc = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listStsc = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        SelectedStsc = new javax.swing.JList();
        AddStstBtn = new javax.swing.JButton();
        CancelStscBtn = new javax.swing.JButton();
        NextBtn = new javax.swing.JButton();
        AddStstBtnAll = new javax.swing.JButton();
        CancelStscBtnAll = new javax.swing.JButton();
        NextBtn1 = new javax.swing.JButton();
        SearchText = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        ExpView = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        listPreview = new javax.swing.JList();
        jScrollPane9 = new javax.swing.JScrollPane();
        preViewTable = new javax.swing.JTable();
        btnPreview = new javax.swing.JButton();
        btnPreview1 = new javax.swing.JButton();
        ExpData = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        logList = new javax.swing.JList();
        btnExport = new javax.swing.JButton();
        exitBtn = new javax.swing.JButton();
        MessageLabel = new javax.swing.JLabel();
        btnExportPerStep = new javax.swing.JButton();
        MessageLabel1 = new javax.swing.JLabel();
        MessageLabel2 = new javax.swing.JLabel();
        DataView = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jScrollPane6 = new javax.swing.JScrollPane();
        exportTabList = new javax.swing.JList();
        jScrollPane7 = new javax.swing.JScrollPane();
        resultViewTable = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        ExpModelSet = new javax.swing.JPanel();
        jRadioButtonAll = new javax.swing.JRadioButton();
        jRadioButtonY = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        addButtonYear = new javax.swing.JButton();
        removeButtonYear = new javax.swing.JButton();
        addButtonYears = new javax.swing.JButton();
        removeButtonYears = new javax.swing.JButton();
        dataIndexPanel = new javax.swing.JTabbedPane();
        jScrollPane10 = new javax.swing.JScrollPane();
        dataIndexTable = new javax.swing.JTable();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(dbtool.DbtoolApp.class).getContext().getResourceMap(ExportView.class);
        NoTablesDialog.setTitle(resourceMap.getString("NoTablesDialog.title")); // NOI18N
        NoTablesDialog.setAlwaysOnTop(true);
        NoTablesDialog.setName("NoTablesDialog"); // NOI18N
        NoTablesDialog.setResizable(false);

        javax.swing.GroupLayout NoTablesDialogLayout = new javax.swing.GroupLayout(NoTablesDialog.getContentPane());
        NoTablesDialog.getContentPane().setLayout(NoTablesDialogLayout);
        NoTablesDialogLayout.setHorizontalGroup(
            NoTablesDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        NoTablesDialogLayout.setVerticalGroup(
            NoTablesDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        tbMainSelect.setName("tbMainSelect"); // NOI18N

        ParametSet.setName("ParametSet"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        cbDriver.setModel(new javax.swing.DefaultComboBoxModel(new String[] {}));
        cbDriver.setName("cbDriver"); // NOI18N
        cbDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDriverActionPerformed(evt);
            }
        });

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        txtIP.setText(resourceMap.getString("txtIP.text")); // NOI18N
        txtIP.setName("txtIP"); // NOI18N
        txtIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIPActionPerformed(evt);
            }
        });

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        txtUser.setText(resourceMap.getString("txtUser.text")); // NOI18N
        txtUser.setName("txtUser"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        txtDataDir.setText(resourceMap.getString("txtDataDir.text")); // NOI18N
        txtDataDir.setName("txtDataDir"); // NOI18N

        NextBtnOne.setText(resourceMap.getString("NextBtnOne.text")); // NOI18N
        NextBtnOne.setName("NextBtnOne"); // NOI18N
        NextBtnOne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBtnOneActionPerformed(evt);
            }
        });

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        DataName.setText(resourceMap.getString("DataName.text")); // NOI18N
        DataName.setName("DataName"); // NOI18N
        DataName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DataNameActionPerformed(evt);
            }
        });

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        txtPort.setText(resourceMap.getString("txtPort.text")); // NOI18N
        txtPort.setName("txtPort"); // NOI18N
        txtPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPortActionPerformed(evt);
            }
        });

        txtPass.setText(resourceMap.getString("txtPass.text")); // NOI18N
        txtPass.setName("txtPass"); // NOI18N

        jRadioButton1.setText(resourceMap.getString("jRadioButton1.text")); // NOI18N
        jRadioButton1.setName("jRadioButton1"); // NOI18N
        jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton1MouseClicked(evt);
            }
        });

        jRadioButton2.setText(resourceMap.getString("jRadioButton2.text")); // NOI18N
        jRadioButton2.setName("jRadioButton2"); // NOI18N
        jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton2MouseClicked(evt);
            }
        });

        exportType.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
            "只导出文本文件","同时导出文本文件和数据库文件","只生成本地数据库索引"
        }));
        exportType.setName("exportType"); // NOI18N
        exportType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportTypeActionPerformed(evt);
            }
        });

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        Version.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"3.0","4.0"}));
        Version.setName("Version"); // NOI18N
        Version.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VersionActionPerformed(evt);
            }
        });

        TestConn.setText(resourceMap.getString("TestConn.text")); // NOI18N
        TestConn.setName("TestConn"); // NOI18N
        TestConn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TestConnActionPerformed(evt);
            }
        });

        charBox.setText(resourceMap.getString("charBox.text")); // NOI18N
        charBox.setName("charBox"); // NOI18N

        javax.swing.GroupLayout ParametSetLayout = new javax.swing.GroupLayout(ParametSet);
        ParametSet.setLayout(ParametSetLayout);
        ParametSetLayout.setHorizontalGroup(
            ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ParametSetLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(TestConn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NextBtnOne)
                .addGap(51, 51, 51))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ParametSetLayout.createSequentialGroup()
                .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(ParametSetLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ParametSetLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(28, 28, 28)
                                .addComponent(txtIP, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ParametSetLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbDriver, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Version, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ParametSetLayout.createSequentialGroup()
                                .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(DataName, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                                    .addComponent(txtUser, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, ParametSetLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                            .addGroup(ParametSetLayout.createSequentialGroup()
                                .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPass, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ParametSetLayout.createSequentialGroup()
                                        .addComponent(txtDataDir, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(exportType, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(charBox)))))))
                .addGap(67, 67, 67))
        );
        ParametSetLayout.setVerticalGroup(
            ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ParametSetLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbDriver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jRadioButton2)
                        .addComponent(jRadioButton1)
                        .addComponent(Version, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)
                        .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)))
                .addGap(19, 19, 19)
                .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIP)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DataName)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUser)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPass))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(charBox)
                    .addComponent(jLabel14)
                    .addComponent(exportType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ParametSetLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(ParametSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TestConn)
                            .addComponent(NextBtnOne)))
                    .addGroup(ParametSetLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel16)))
                .addGap(17, 17, 17))
        );

        jLabel5.getAccessibleContext().setAccessibleName(resourceMap.getString("jLabel5.AccessibleContext.accessibleName")); // NOI18N

        tbMainSelect.addTab(resourceMap.getString("ParametSet.TabConstraints.tabTitle"), ParametSet); // NOI18N

        SelectTable.setName("SelectTable"); // NOI18N

        SelectTableLPanel.setName("SelectTableLPanel"); // NOI18N

        ListTables.setDropMode(javax.swing.DropMode.ON);
        ListTables.setFocusCycleRoot(true);
        ListTables.setName("ListTables"); // NOI18N
        ListTables.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ListTablesMouseClicked(evt);
            }
        });
        SelectTableLPanel.setViewportView(ListTables);

        AddBtn.setText(resourceMap.getString("AddBtn.text")); // NOI18N
        AddBtn.setName("AddBtn"); // NOI18N
        AddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBtnActionPerformed(evt);
            }
        });

        CancelBtn.setText(resourceMap.getString("CancelBtn.text")); // NOI18N
        CancelBtn.setName("CancelBtn"); // NOI18N
        CancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelBtnActionPerformed(evt);
            }
        });

        SelectTableRPanel.setName("SelectTableRPanel"); // NOI18N

        SelectedTables.setName("SelectedTables"); // NOI18N
        SelectedTables.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SelectedTablesMouseClicked(evt);
            }
        });
        SelectTableRPanel.setViewportView(SelectedTables);

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        btnTableNextStep.setText(resourceMap.getString("btnTableNextStep.text")); // NOI18N
        btnTableNextStep.setName("btnTableNextStep"); // NOI18N
        btnTableNextStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTableNextStepActionPerformed(evt);
            }
        });

        AddBtnAllTables.setText(resourceMap.getString("AddBtnAllTables.text")); // NOI18N
        AddBtnAllTables.setName("AddBtnAllTables"); // NOI18N
        AddBtnAllTables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBtnAllTablesActionPerformed(evt);
            }
        });

        CancelBtnAllTables.setText(resourceMap.getString("CancelBtnAllTables.text")); // NOI18N
        CancelBtnAllTables.setName("CancelBtnAllTables"); // NOI18N
        CancelBtnAllTables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelBtnAllTablesActionPerformed(evt);
            }
        });

        btnTablePerStep.setText(resourceMap.getString("btnTablePerStep.text")); // NOI18N
        btnTablePerStep.setName("btnTablePerStep"); // NOI18N
        btnTablePerStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTablePerStepActionPerformed(evt);
            }
        });

        btnPreview2.setText(resourceMap.getString("btnPreview2.text")); // NOI18N
        btnPreview2.setName("btnPreview2"); // NOI18N
        btnPreview2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreview2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SelectTableLayout = new javax.swing.GroupLayout(SelectTable);
        SelectTable.setLayout(SelectTableLayout);
        SelectTableLayout.setHorizontalGroup(
            SelectTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SelectTableLayout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(SelectTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SelectTableLPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(32, 32, 32)
                .addGroup(SelectTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(SelectTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(AddBtnAllTables, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CancelBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AddBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                    .addComponent(CancelBtnAllTables))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(SelectTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SelectTableRPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(157, 157, 157))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SelectTableLayout.createSequentialGroup()
                .addContainerGap(503, Short.MAX_VALUE)
                .addComponent(btnPreview2)
                .addGap(18, 18, 18)
                .addComponent(btnTablePerStep)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTableNextStep)
                .addGap(51, 51, 51))
        );
        SelectTableLayout.setVerticalGroup(
            SelectTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SelectTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SelectTableRPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(SelectTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTablePerStep)
                    .addComponent(btnTableNextStep)
                    .addComponent(btnPreview2))
                .addContainerGap())
            .addGroup(SelectTableLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(SelectTableLPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addGap(46, 46, 46))
            .addGroup(SelectTableLayout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(AddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(CancelBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(AddBtnAllTables, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(CancelBtnAllTables, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );

        tbMainSelect.addTab(resourceMap.getString("SelectTable.TabConstraints.tabTitle"), SelectTable); // NOI18N

        SelectStsc.setForeground(resourceMap.getColor("SelectStsc.foreground")); // NOI18N
        SelectStsc.setName("SelectStsc"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        listStsc.setName("listStsc"); // NOI18N
        listStsc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listStscMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(listStsc);

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        SelectedStsc.setName("SelectedStsc"); // NOI18N
        SelectedStsc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SelectedStscMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(SelectedStsc);

        AddStstBtn.setText(resourceMap.getString("AddStstBtn.text")); // NOI18N
        AddStstBtn.setName("AddStstBtn"); // NOI18N
        AddStstBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddStstBtnActionPerformed(evt);
            }
        });

        CancelStscBtn.setText(resourceMap.getString("CancelStscBtn.text")); // NOI18N
        CancelStscBtn.setName("CancelStscBtn"); // NOI18N
        CancelStscBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelStscBtnActionPerformed(evt);
            }
        });

        NextBtn.setText(resourceMap.getString("NextBtn.text")); // NOI18N
        NextBtn.setName("NextBtn"); // NOI18N
        NextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBtnActionPerformed(evt);
            }
        });

        AddStstBtnAll.setText(resourceMap.getString("AddStstBtnAll.text")); // NOI18N
        AddStstBtnAll.setName("AddStstBtnAll"); // NOI18N
        AddStstBtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddStstBtnAllActionPerformed(evt);
            }
        });

        CancelStscBtnAll.setText(resourceMap.getString("CancelStscBtnAll.text")); // NOI18N
        CancelStscBtnAll.setName("CancelStscBtnAll"); // NOI18N
        CancelStscBtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelStscBtnAllActionPerformed(evt);
            }
        });

        NextBtn1.setText(resourceMap.getString("NextBtn1.text")); // NOI18N
        NextBtn1.setName("NextBtn1"); // NOI18N
        NextBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBtn1ActionPerformed(evt);
            }
        });

        SearchText.setText(resourceMap.getString("SearchText.text")); // NOI18N
        SearchText.setName("SearchText"); // NOI18N

        searchButton.setText(resourceMap.getString("searchButton.text")); // NOI18N
        searchButton.setName("searchButton"); // NOI18N
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        jScrollPane11.setName("jScrollPane11"); // NOI18N

        jTextArea1.setBackground(resourceMap.getColor("jTextArea1.background")); // NOI18N
        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setFont(resourceMap.getFont("jTextArea1.font")); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText(resourceMap.getString("jTextArea1.text")); // NOI18N
        jTextArea1.setBorder(null);
        jTextArea1.setCaretColor(resourceMap.getColor("jTextArea1.caretColor")); // NOI18N
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane11.setViewportView(jTextArea1);

        javax.swing.GroupLayout SelectStscLayout = new javax.swing.GroupLayout(SelectStsc);
        SelectStsc.setLayout(SelectStscLayout);
        SelectStscLayout.setHorizontalGroup(
            SelectStscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SelectStscLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(SelectStscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SelectStscLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(SelectStscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CancelStscBtnAll)
                            .addComponent(CancelStscBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AddStstBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                            .addComponent(AddStstBtnAll, 0, 0, Short.MAX_VALUE)))
                    .addGroup(SelectStscLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchText, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton)))
                .addGroup(SelectStscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SelectStscLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel9))
                    .addGroup(SelectStscLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(SelectStscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SelectStscLayout.createSequentialGroup()
                        .addComponent(NextBtn1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(NextBtn))
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );
        SelectStscLayout.setVerticalGroup(
            SelectStscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SelectStscLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SelectStscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SelectStscLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(AddStstBtn)
                        .addGap(18, 18, 18)
                        .addComponent(CancelStscBtn)
                        .addGap(18, 18, 18)
                        .addComponent(AddStstBtnAll)
                        .addGap(18, 18, 18)
                        .addComponent(CancelStscBtnAll))
                    .addGroup(SelectStscLayout.createSequentialGroup()
                        .addGroup(SelectStscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(SearchText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchButton)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(SelectStscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SelectStscLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NextBtn1)
                    .addComponent(NextBtn))
                .addGap(19, 19, 19))
        );

        tbMainSelect.addTab(resourceMap.getString("SelectStsc.TabConstraints.tabTitle"), SelectStsc); // NOI18N

        ExpView.setName("ExpView"); // NOI18N

        jScrollPane8.setName("jScrollPane8"); // NOI18N

        listPreview.setName("listPreview"); // NOI18N
        listPreview.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listPreviewMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(listPreview);

        jScrollPane9.setName("jScrollPane9"); // NOI18N

        preViewTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"11", "22", null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "字段1", "Title 2", "Title 3", "Title 4"
            }
        ));
        preViewTable.setName("preViewTable"); // NOI18N
        jScrollPane9.setViewportView(preViewTable);

        btnPreview.setText(resourceMap.getString("btnPreview.text")); // NOI18N
        btnPreview.setName("btnPreview"); // NOI18N
        btnPreview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviewActionPerformed(evt);
            }
        });

        btnPreview1.setText(resourceMap.getString("btnPreview1.text")); // NOI18N
        btnPreview1.setName("btnPreview1"); // NOI18N
        btnPreview1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreview1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ExpViewLayout = new javax.swing.GroupLayout(ExpView);
        ExpView.setLayout(ExpViewLayout);
        ExpViewLayout.setHorizontalGroup(
            ExpViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExpViewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ExpViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ExpViewLayout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ExpViewLayout.createSequentialGroup()
                        .addComponent(btnPreview1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPreview)
                        .addContainerGap())))
        );
        ExpViewLayout.setVerticalGroup(
            ExpViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ExpViewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ExpViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ExpViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPreview)
                    .addComponent(btnPreview1))
                .addContainerGap())
        );

        tbMainSelect.addTab(resourceMap.getString("ExpView.TabConstraints.tabTitle"), ExpView); // NOI18N

        ExpData.setName("ExpData"); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        logList.setModel(logModel);
        logList.setName("logList"); // NOI18N
        jScrollPane5.setViewportView(logList);

        btnExport.setText(resourceMap.getString("btnExport.text")); // NOI18N
        btnExport.setName("btnExport"); // NOI18N
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        exitBtn.setText(resourceMap.getString("exitBtn.text")); // NOI18N
        exitBtn.setName("exitBtn"); // NOI18N
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitBtnActionPerformed(evt);
            }
        });

        MessageLabel.setForeground(resourceMap.getColor("MessageLabel.foreground")); // NOI18N
        MessageLabel.setText(resourceMap.getString("MessageLabel.text")); // NOI18N
        MessageLabel.setName("MessageLabel"); // NOI18N

        btnExportPerStep.setText(resourceMap.getString("btnExportPerStep.text")); // NOI18N
        btnExportPerStep.setName("btnExportPerStep"); // NOI18N
        btnExportPerStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportPerStepActionPerformed(evt);
            }
        });

        MessageLabel1.setForeground(resourceMap.getColor("MessageLabel1.foreground")); // NOI18N
        MessageLabel1.setText(resourceMap.getString("MessageLabel1.text")); // NOI18N
        MessageLabel1.setName("MessageLabel1"); // NOI18N
        MessageLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MessageLabel1MouseClicked(evt);
            }
        });

        MessageLabel2.setForeground(resourceMap.getColor("MessageLabel2.foreground")); // NOI18N
        MessageLabel2.setText(resourceMap.getString("MessageLabel2.text")); // NOI18N
        MessageLabel2.setName("MessageLabel2"); // NOI18N

        javax.swing.GroupLayout ExpDataLayout = new javax.swing.GroupLayout(ExpData);
        ExpData.setLayout(ExpDataLayout);
        ExpDataLayout.setHorizontalGroup(
            ExpDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExpDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ExpDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ExpDataLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(ExpDataLayout.createSequentialGroup()
                        .addComponent(MessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(MessageLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(MessageLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                        .addGap(125, 125, 125)
                        .addComponent(btnExportPerStep)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExport)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(exitBtn)
                        .addGap(28, 28, 28))))
        );
        ExpDataLayout.setVerticalGroup(
            ExpDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ExpDataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(ExpDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ExpDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnExportPerStep)
                        .addComponent(btnExport)
                        .addComponent(exitBtn))
                    .addComponent(MessageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MessageLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MessageLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tbMainSelect.addTab(resourceMap.getString("ExpData.TabConstraints.tabTitle"), ExpData); // NOI18N

        DataView.setName("DataView"); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "导入来源数据查看", "导出结果查看" }));
        jComboBox1.setName("jComboBox1"); // NOI18N
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jComboBox1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBox1PropertyChange(evt);
            }
        });

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        exportTabList.setName("exportTabList"); // NOI18N
        exportTabList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exportTabListMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(exportTabList);

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        resultViewTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        resultViewTable.setName("resultViewTable"); // NOI18N
        jScrollPane7.setViewportView(resultViewTable);

        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DataViewLayout = new javax.swing.GroupLayout(DataView);
        DataView.setLayout(DataViewLayout);
        DataViewLayout.setHorizontalGroup(
            DataViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataViewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DataViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DataViewLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE))
                    .addGroup(DataViewLayout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)))
                .addContainerGap())
        );
        DataViewLayout.setVerticalGroup(
            DataViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DataViewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DataViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DataViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tbMainSelect.addTab(resourceMap.getString("DataView.TabConstraints.tabTitle"), DataView); // NOI18N

        ExpModelSet.setName("ExpModelSet"); // NOI18N

        jRadioButtonAll.setText(resourceMap.getString("jRadioButtonAll.text")); // NOI18N
        jRadioButtonAll.setName("jRadioButtonAll"); // NOI18N
        jRadioButtonAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButtonAllMouseClicked(evt);
            }
        });

        jRadioButtonY.setText(resourceMap.getString("jRadioButtonY.text")); // NOI18N
        jRadioButtonY.setName("jRadioButtonY"); // NOI18N
        jRadioButtonY.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButtonYMouseClicked(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setName("jList1"); // NOI18N
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = {};
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList2.setName("jList2"); // NOI18N
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jList2);

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        addButtonYear.setText(resourceMap.getString("addButtonYear.text")); // NOI18N
        addButtonYear.setName("addButtonYear"); // NOI18N
        addButtonYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonYearActionPerformed(evt);
            }
        });

        removeButtonYear.setText(resourceMap.getString("removeButtonYear.text")); // NOI18N
        removeButtonYear.setName("removeButtonYear"); // NOI18N
        removeButtonYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonYearActionPerformed(evt);
            }
        });

        addButtonYears.setText(resourceMap.getString("addButtonYears.text")); // NOI18N
        addButtonYears.setName("addButtonYears"); // NOI18N
        addButtonYears.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonYearsActionPerformed(evt);
            }
        });

        removeButtonYears.setText(resourceMap.getString("removeButtonYears.text")); // NOI18N
        removeButtonYears.setName("removeButtonYears"); // NOI18N
        removeButtonYears.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonYearsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ExpModelSetLayout = new javax.swing.GroupLayout(ExpModelSet);
        ExpModelSet.setLayout(ExpModelSetLayout);
        ExpModelSetLayout.setHorizontalGroup(
            ExpModelSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExpModelSetLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(ExpModelSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jRadioButtonY, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButtonAll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(208, 208, 208)
                .addGroup(ExpModelSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ExpModelSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(removeButtonYears, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addButtonYears, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeButtonYear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addButtonYear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addGroup(ExpModelSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(136, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ExpModelSetLayout.createSequentialGroup()
                .addContainerGap(449, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(275, 275, 275))
        );
        ExpModelSetLayout.setVerticalGroup(
            ExpModelSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExpModelSetLayout.createSequentialGroup()
                .addGroup(ExpModelSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ExpModelSetLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jRadioButtonAll)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButtonY))
                    .addGroup(ExpModelSetLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(ExpModelSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(ExpModelSetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))))
                .addContainerGap(54, Short.MAX_VALUE))
            .addGroup(ExpModelSetLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(addButtonYear)
                .addGap(18, 18, 18)
                .addComponent(removeButtonYear)
                .addGap(18, 18, 18)
                .addComponent(addButtonYears)
                .addGap(18, 18, 18)
                .addComponent(removeButtonYears)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(29, 29, 29))
        );

        tbMainSelect.addTab(resourceMap.getString("ExpModelSet.TabConstraints.tabTitle"), ExpModelSet); // NOI18N

        dataIndexPanel.setName("dataIndexPanel"); // NOI18N

        jScrollPane10.setName("jScrollPane10"); // NOI18N

        dataIndexTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "测站名称", "水流沙", "降水", "水温", "蒸发", "冰凌", "潮位", "起止年份"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        dataIndexTable.setName("dataIndexTable"); // NOI18N
        jScrollPane10.setViewportView(dataIndexTable);
        dataIndexTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("dataIndexTable.columnModel.title0")); // NOI18N
        dataIndexTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("dataIndexTable.columnModel.title1")); // NOI18N
        dataIndexTable.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("dataIndexTable.columnModel.title2")); // NOI18N
        dataIndexTable.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("dataIndexTable.columnModel.title3")); // NOI18N
        dataIndexTable.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("dataIndexTable.columnModel.title4")); // NOI18N
        dataIndexTable.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("dataIndexTable.columnModel.title5")); // NOI18N
        dataIndexTable.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("dataIndexTable.columnModel.title6")); // NOI18N
        dataIndexTable.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("dataIndexTable.columnModel.title7")); // NOI18N

        dataIndexPanel.addTab(resourceMap.getString("jScrollPane10.TabConstraints.tabTitle"), jScrollPane10); // NOI18N

        tbMainSelect.addTab(resourceMap.getString("dataIndexPanel.TabConstraints.tabTitle"), dataIndexPanel); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbMainSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 806, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbMainSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("unchecked")
    private void cbDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDriverActionPerformed
        int driveClassIndex = cbDriver.getSelectedIndex();

        if (driveClassIndex == 1) {//sqlserver
            txtPort.setEnabled(true);txtIP.setEnabled(true);//exportType.setEnabled(true);
            txtPort.setText("1433");
            charBox.setSelected(false);
            charBox.setEnabled(false);
            jLabel11.setText(" 数据库名称：");
        }
        if (driveClassIndex == 2) {//sysbase
            txtPort.setEnabled(true);txtIP.setEnabled(true);//exportType.setEnabled(true);
            charBox.setEnabled(true);
            charBox.setSelected(true);
            txtPort.setText("5000");
            jLabel11.setText(" 数据库名称：");
        }
        if (driveClassIndex == 3) {//oracle
            txtPort.setEnabled(true);txtIP.setEnabled(true);//exportType.setEnabled(true);
            charBox.setSelected(false);
            charBox.setEnabled(false);
            txtPort.setText("1521");
            jLabel11.setText(" 数据库名称：");
        }
        if(driveClassIndex == 4){//foxpro
            txtPort.setText("0000");
            charBox.setSelected(false);
            charBox.setEnabled(false);
            exportType.setSelectedIndex(0);
            //exportType.setEnabled(false);
            txtPort.setEnabled(false);
            txtIP.setEnabled(false);
            jLabel11.setText(" 数据源名称：");
        }
}//GEN-LAST:event_cbDriverActionPerformed

    private void txtIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIPActionPerformed
//        propertiesChange();
}//GEN-LAST:event_txtIPActionPerformed

    private void DataNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DataNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DataNameActionPerformed
    @SuppressWarnings("unchecked")
    private void NextBtnOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextBtnOneActionPerformed
         int driveClassIndex = cbDriver.getSelectedIndex();
        String serverPort = txtPort.getText();
        String ipAddress = txtIP.getText();
        String dbname = DataName.getText();

        if (driveClassIndex == 0) {
            JOptionPane.showMessageDialog(null, "请选择数据库类型！", "错误", 0);
        } else {
            if (serverPort.trim().equals("")) {
                JOptionPane.showMessageDialog(null, "数据库端口号不能为空！", "错误", 0);
            } else {
                if ("".trim().equals(ipAddress)) {
                    JOptionPane.showMessageDialog(null, "服务器ip地址不能为空！", "错误", 0);
                } else {
                    if ("".trim().equals(dbname)) {
                        JOptionPane.showMessageDialog(null, "服务名不能为空！", "错误", 0);
                    } else {
                        expType = exportType.getSelectedIndex();
                        if (driveClassIndex == 1) {//sqlserver
                            DiverClass = "net.sourceforge.jtds.jdbc.Driver";
                            JdbcUrl = "jdbc:jtds:sqlserver://" + txtIP.getText() + ":" + serverPort + "/" + DataName.getText();
                        }
                        if (driveClassIndex == 2) {//sysbase
                            if(charBox.isSelected()){
                                DiverClass = "com.sybase.jdbc3.jdbc.SybDriver";
                                JdbcUrl = "jdbc:sybase:Tds:" + txtIP.getText() + ":" + serverPort + "/" + DataName.getText()+"?charset=cp850";
                            }else{
                                 DiverClass = "com.sybase.jdbc3.jdbc.SybDriver";
                                 JdbcUrl = "jdbc:sybase:Tds:" + txtIP.getText() + ":" + serverPort + "/" + DataName.getText();
                            }
                        }
                        if (driveClassIndex == 3) {//sysbase             
                            DiverClass = "oracle.jdbc.driver.OracleDriver";
                            JdbcUrl = "jdbc:oracle:thin:@" + txtIP.getText() + ":" + serverPort + ":" + DataName.getText();
                        }
                        if (driveClassIndex == 4) {//foxpro
                            DiverClass = "sun.jdbc.odbc.JdbcOdbcDriver";
                            JdbcUrl = "jdbc:odbc:"+DataName.getText();
                        }
//                        if (exportType.getSelectedIndex() == 0) {
//                            jComboBox1.setEnabled(false);
//                        } else {
//                            jComboBox1.setEnabled(true);
//                        }
                        try {
                            boolean flg = FileAccess.deleteDirectory(txtDataDir.getText());
                            if (flg) {
                                dbTool = new DBTool(DiverClass, JdbcUrl, txtUser.getText(),
                                        txtPass.getText(), txtDataDir.getText());
                                tbMainSelect.remove(ParametSet);
                                tbMainSelect.add("数据选择", SelectTable);
                                //初始化数据库
                                 JdbcTemplate jt_Target = dbTool.getJt2();
                                if(flg){
                                    if (Version.getSelectedIndex() == 1) {//符合4.0的标准
                                        for (String sql : InitTargetDB.createTab) {
                                            jt_Target.execute(sql);
                                        }
                                        jt_Target.batchUpdate(InitTargetDB.insertTables);
                                    } else {//符合3.0的标准
                                        for (String sql : InitTargetDB_TVersion.createTab) {
                                            jt_Target.execute(sql);
                                        }
                                        for (String sql2 : InitTargetDB_TVersion.insertTables) {
                                            jt_Target.execute(sql2);
                                        }
                                    }
                                    String initStscTab[] = ExcelService.readStcdFromExcel("c://stcd.xls");
                                    for (String initstscsql : initStscTab) {
                                        if (initstscsql != null && !initstscsql.trim().equals("null")) {
                                            jt_Target.execute(initstscsql);
                                        }
                                    }

                                    String initIndexMsg[] = ExcelService.readIndexMsgFromExcel("c://tables.xls");
                                    for (String initIndexsql : initIndexMsg) {
                                        if (initIndexsql != null && !initIndexsql.trim().equals("null")) {
                                            jt_Target.execute(initIndexsql);
                                        }
                                    }
                                }
                                List<HY_DBTP_JBean> tabList = HY_DBTP_JDao.getAllTabs(dbTool);
                                if (tabList != null && tabList.size() > 0) {
                                    selectedTablesModel.removeAllElements();
                                    int i = 0;
                                    for (HY_DBTP_JBean bean : tabList) {
                                        bean = (HY_DBTP_JBean) tabList.get(i);
                                        selectedTablesModel.addElement(bean.getTBCNNM().trim());
                                        i++;
                                    }
                                }
                                DefaultListModel model = new DefaultListModel();
                                ListTables.setModel(model);
                                SelectedTables.setModel(selectedTablesModel);

                                //从源数据库中读取测站基本信息

                                JdbcTemplate jt_stsc = dbTool.getJt1();//从源数据库中取得测站名称信息
                                String queryStcdSQL = "";
                                if (Version.getSelectedIndex() == 1) {//符合4.0的标准
                                    queryStcdSQL = "select *  from HY_STSC_A order by STNM";
                                } else {
                                    queryStcdSQL = "select *  from STHD ORDER BY STNM";
                                }
                                listParamModel_source.removeAllElements();
                                liststscModel_source.removeAllElements();
                                jt_stsc.query(queryStcdSQL, new RowMapper() {

                                    public List<HY_STSC_ABean> mapRow(final ResultSet rs, int rowNum) throws SQLException{
                                        List<HY_STSC_ABean> resultList = null;
//                                        System.out.println(rs.getString("stnm").trim());
                                        try{
                                        String stnm = rs.getString("STNM");
                                        String stcd = rs.getString("STCD");
                                        if(charBox.isSelected())
                                            stnm = new String(stnm.getBytes("ISO-8859-1"),"GBK");
                                        stnm=stnm==null?"":stnm;
                                        stscNameModel_source.addElement(stnm.trim());
                                        listParamModel_source.addElement("[" + stcd.trim() + "]" + stnm.trim());
                                        liststscModel_source.addElement(stcd.trim());
                                        }catch(Exception ex){ex.printStackTrace();}
                                        return resultList;
                                    }
                                });

                                //检验并过滤掉不符合导出条件的测站
                                JdbcTemplate jt_target = dbTool.getJt2();
                                selectedSnameModel.removeAllElements();
                                selectedStscModel.removeAllElements();
                                for (int k = 0; k < liststscModel_source.size(); k++) {
                                    int count = jt_target.queryForInt("select count(*) from TABLE_STCD where STCD='" + liststscModel_source.getElementAt(k).toString() + "'");
                                    if (count > 0) {
                                        if ("".trim().equals(stscNameModel_source.getElementAt(k).toString())) {
                                            selectedSnameModel.addElement("[" + liststscModel_source.getElementAt(k).toString().trim() + "]~" + dbTool.getStscName(jt_Target, liststscModel_source.getElementAt(k).toString()));
                                        } else {
                                            selectedSnameModel.addElement(listParamModel_source.getElementAt(k));
                                        }
                                    }
                                }
                                listStsc.setModel(listParamModel_source);
                                SelectedStsc.setModel(selectedSnameModel);
                            } else {
//                                if(dbTool!=null){
//                                    dbTool.shutdown();
//                                }
                                tbMainSelect.remove(SelectTable);
                                tbMainSelect.add("参数设置", ParametSet);
                                JOptionPane.showMessageDialog(null, "导出目录删除失败，请关闭所有打开的导出目录下的文件重试！", "错误", 0);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "读取配置文件或者测站信息表失败，请确认参数设置！", "错误", 0);
                            dbTool.shutdown();
                            tbMainSelect.remove(SelectTable);
                            tbMainSelect.add("参数设置", ParametSet);
                        }
                    }
                }

            }
        }
    }//GEN-LAST:event_NextBtnOneActionPerformed
    @SuppressWarnings("unchecked")
    private void btnTableNextStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTableNextStepActionPerformed
        if (selectedTablesModel.isEmpty()) {
            JOptionPane.showMessageDialog(null, "您没有选择任何数据表，请选择！", "错误", 0);
        } else {
            exportTabList.setModel(selectedTablesModel);
            tbMainSelect.remove(SelectTable);

            tbMainSelect.add("测站选择", SelectStsc);
        }
    }//GEN-LAST:event_btnTableNextStepActionPerformed

    private void NextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextBtnActionPerformed
         if (selectedSnameModel.isEmpty()) {
            JOptionPane.showMessageDialog(null, "您没有选择任何测站，请选择！", "错误", 0);
        } else {
            tbMainSelect.remove(SelectStsc);
            tbMainSelect.add("数据导出", ExpData);
            tbMainSelect.add("数据查看", DataView);
        }
    }//GEN-LAST:event_NextBtnActionPerformed

    private void btnPreviewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviewActionPerformed
//        tbMainSelect.setSelectedIndex(4);
    }//GEN-LAST:event_btnPreviewActionPerformed
    @SuppressWarnings("unchecked")
    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed

        btnExportPerStep.setEnabled(false);
        btnExport.setEnabled(false);
        File dirFile = new File(txtDataDir.getText() + "\\excel\\");
        boolean bFile = dirFile.exists();
        if (bFile == false) {
            dirFile.mkdir();
        }
        ((DefaultListModel) logModel).removeAllElements();

        new Thread() {

            @Override
            public void run() {
                DefaultListModel allTablesmodel = (DefaultListModel) ListTables.getModel();
                dbTool.process(logList, allTablesmodel, selectedTablesModel, selectedStscModel, selectedYearsModel,
                        selectedSnameModel, dbTool, txtDataDir.getText(), expType,
                        Version.getSelectedIndex(),charBox.isSelected(),cbDriver.getSelectedIndex());
                MessageLabel.setText("系统消息：导出成功结束,可查看目录[");
                MessageLabel1.setText("<html><a href='file:///" + txtDataDir.getText() + "'>" + txtDataDir.getText() + "\\excel</a></html>");
                MessageLabel2.setText("]请查看！");
                MessageLabel1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                MessageLabel.setVisible(true);
                MessageLabel1.setVisible(true);
                MessageLabel2.setVisible(true);
                exitBtn.setVisible(true);
                btnExport.setEnabled(true);
                btnExportPerStep.setEnabled(true);
            }
        }.start();
    }//GEN-LAST:event_btnExportActionPerformed

   /**
     * 选择item后，通过按钮进行操作，
     * 实现从左向右的移动
     * 从左侧移出条目
     * 右侧增加移出的条目
     * @param evt
     */
    private void AddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBtnActionPerformed
        // TODO add your handling code here:
        int index[] = ListTables.getSelectedIndices();
        Object values[] = ListTables.getSelectedValues();
        if (index != null && index.length > 0) {
            DefaultListModel model = (DefaultListModel) ListTables.getModel();
            for (int k = 0; k < index.length; k++) {
                selectedTablesModel.addElement(values[k]);
                SelectedTables.setModel(selectedTablesModel);
            }
            for (int k = 0; k < index.length; k++) {
                model.removeElement(values[k]);
            }
        }

    }//GEN-LAST:event_AddBtnActionPerformed
   /**
     * 双击事件，双击item自动移动从左向右的移动
     * @param evt
     */
    private void ListTablesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListTablesMouseClicked
        if (evt.getClickCount() == 2) {
            DefaultListModel model = (DefaultListModel) ListTables.getModel();

            SelectedTables.setModel(selectedTablesModel);
            selectedTablesModel.addElement(ListTables.getSelectedValue());
            model.remove(ListTables.getSelectedIndex());
        }
    }//GEN-LAST:event_ListTablesMouseClicked
    /**
     * 双击事件，双击item自动移动从右向左的移动
     * @param evt
     */
    private void SelectedTablesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SelectedTablesMouseClicked
        if (evt.getClickCount() == 2) {
            DefaultListModel model = (DefaultListModel) ListTables.getModel();
            model.addElement(SelectedTables.getSelectedValue());
            selectedTablesModel.remove(SelectedTables.getSelectedIndex());
        }
    }//GEN-LAST:event_SelectedTablesMouseClicked
    /**
     * 选择item后，通过按钮进行操作，
     * 实现从右向左的移动
     * 从右侧移出条目
     * 左侧增加移出的条目
     * @param evt
     */
    private void CancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelBtnActionPerformed
        // TODO add your handling code here:
        int index[] = SelectedTables.getSelectedIndices();
        Object values[] = SelectedTables.getSelectedValues();
        if (index != null && index.length > 0) {
            DefaultListModel model = (DefaultListModel) ListTables.getModel();
            for (int k = 0; k < index.length; k++) {
                model.addElement(values[k]);
            }
            for (int k = 0; k < index.length; k++) {
                selectedTablesModel.removeElement(values[k]);
            }
        }
    }//GEN-LAST:event_CancelBtnActionPerformed
    /**
     * 选择测站编码过滤条件
     * 鼠标点击的时候触发
     * 双击移动从左侧到右侧
     *
     * @param evt
     */
    private void listStscMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listStscMouseClicked
        if (evt.getClickCount() == 2) {
            DefaultListModel model = (DefaultListModel) listStsc.getModel();

            SelectedStsc.setModel(selectedSnameModel);
            selectedSnameModel.addElement(listStsc.getSelectedValue());
            model.remove(listStsc.getSelectedIndex());
        }
    }//GEN-LAST:event_listStscMouseClicked
     /**
     * 选择测站编码过滤条件
     * 鼠标点击的时候触发
     * 双击移动从右侧到左侧
     *
     * @param evt
     */
    private void SelectedStscMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SelectedStscMouseClicked
         String selectedValue = SelectedStsc.getSelectedValue().toString();

        if (!"".trim().equals(selectedValue)) {

            int point = selectedValue.lastIndexOf("]");
            if (selectedValue.indexOf("~") > 0) {
                point++;
            }
            if (selectedValue.length() > (point + 3)) {
                selectedValue = selectedValue.substring(point + 1, point + 3);
            } else {
                selectedValue = selectedValue.substring(point + 1, selectedValue.length());
            }
            SearchText.setText(selectedValue);
            //从源数据库中查询数据库，并且返回测站编码不在右侧list选项框中的数据
            JdbcTemplate jt_stsc = dbTool.getJt1();//从源数据库中取得测站名称信息
            String queryStcdSQL = "";

            if (Version.getSelectedIndex() == 1) //符合4.0的标准
            {
                queryStcdSQL = "select *  from HY_STSC_A where STNM like '%" + selectedValue.trim() + "%'";
            } else {
                queryStcdSQL = "select *  from STHD where STNM like '%" + selectedValue.trim() + "%'";
            }
            listParamModel_source.removeAllElements();
            liststscModel_source.removeAllElements();
            jt_stsc.query(queryStcdSQL, new RowMapper() {

                public List<HY_STSC_ABean> mapRow(final ResultSet rs, int rowNum) throws SQLException {
                    List<HY_STSC_ABean> resultList = null;
                    try{
                    String stnm = rs.getString("STNM").trim();
                    if(charBox.isSelected())
                        stnm = new String(stnm.getBytes("ISO-8859-1"),"GBK");
                    stscNameModel_source.addElement(stnm);
                    listParamModel_source.addElement("[" + rs.getString("STCD").trim() + "]" + stnm);
                    liststscModel_source.addElement(rs.getString("STCD").trim());
                    }catch(Exception ex){ex.printStackTrace();}
                    return resultList;
                }
            });
            listStsc.setModel(listParamModel_source);

        }

    }//GEN-LAST:event_SelectedStscMouseClicked
    /**
     * 点击加入按钮触发
     * 将选中的测站item移动到右侧
     * @param evt
     */
    private void AddStstBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddStstBtnActionPerformed
        int index[] = listStsc.getSelectedIndices();
        Object values[] = listStsc.getSelectedValues();
        if (index != null && index.length > 0) {
            DefaultListModel model = (DefaultListModel) listStsc.getModel();
            for (int k = 0; k < index.length; k++) {
                selectedSnameModel.addElement(values[k]);
                SelectedStsc.setModel(selectedSnameModel);
            }
            for (int k = 0; k < index.length; k++) {
                model.removeElement(values[k]);
            }
        }
    }//GEN-LAST:event_AddStstBtnActionPerformed
    /**
     * 点击取消测站按钮触发
     * 将选中的测站item移动到左侧
     * @param evt
     */
    private void CancelStscBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelStscBtnActionPerformed
        int index[] = SelectedStsc.getSelectedIndices();
        Object values[] = SelectedStsc.getSelectedValues();
        if (index != null && index.length > 0) {
            DefaultListModel model = (DefaultListModel) listStsc.getModel();
            for (int k = 0; k < index.length; k++) {
                model.addElement(values[k]);
            }
            for (int k = 0; k < index.length; k++) {
                selectedSnameModel.removeElement(values[k]);
            }
        }
    }//GEN-LAST:event_CancelStscBtnActionPerformed

    private void AddBtnAllTablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBtnAllTablesActionPerformed
        DefaultListModel model = (DefaultListModel) ListTables.getModel();
        if (!model.isEmpty()) {

            for (int i = 0; i < model.size(); i++) {
                selectedTablesModel.addElement(model.get(i));
            }

            SelectedTables.setModel(selectedTablesModel);
            model.removeAllElements();
        }
    }//GEN-LAST:event_AddBtnAllTablesActionPerformed
     /**
     * 取消已经选择的所有tables
     * 将右侧条目全部移动到左侧
     * @param evt
     */
    private void CancelBtnAllTablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelBtnAllTablesActionPerformed
        // TODO add your handling code here:
        DefaultListModel model = (DefaultListModel) ListTables.getModel();
        if (!selectedTablesModel.isEmpty()) {
            for (int i = 0; i < selectedTablesModel.size(); i++) {
                model.addElement(selectedTablesModel.get(i));
            }
            selectedTablesModel.removeAllElements();
        }
    }//GEN-LAST:event_CancelBtnAllTablesActionPerformed
    /**
     * 测站选择全部移动
     * 从左侧到右侧
     * @param evt
     */
    private void AddStstBtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddStstBtnAllActionPerformed
        // TODO add your handling code here:
        DefaultListModel model = (DefaultListModel) listStsc.getModel();
        if (!model.isEmpty()) {

            for (int i = 0; i < model.size(); i++) {
                selectedSnameModel.addElement(model.get(i));
            }

            SelectedStsc.setModel(selectedSnameModel);
            model.removeAllElements();
        }
    }//GEN-LAST:event_AddStstBtnAllActionPerformed
     /**
     * 取消已经选择的所有测站
     * 实现从右侧到左侧的移动
     * @param evt
     */
    private void CancelStscBtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelStscBtnAllActionPerformed
        // TODO add your handling code here:
        DefaultListModel model = (DefaultListModel) listStsc.getModel();
        if (!selectedSnameModel.isEmpty()) {
            for (int i = 0; i < selectedSnameModel.size(); i++) {
                model.addElement(selectedSnameModel.get(i));
            }
            selectedSnameModel.removeAllElements();
        }
    }//GEN-LAST:event_CancelStscBtnAllActionPerformed

    /**
     * 点击报表名称后，重新加载右侧表信息
     * @param evt
     */
    private void listPreviewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listPreviewMouseClicked
        // TODO add your handling code here:
//        String colStr = "";
//        int type = jComboBox1.getSelectedIndex();
//        List<HY_DBFP_JBean> colList = null;
//        List<List<Map<String, String>>> resulstList = null;
//        try {
//            colList = HY_DBFP_JDao.colColumnBeanList(listPreview.getSelectedValue().toString(), "", "FLID",dbTool,jComboBox1.getSelectedIndex());
//            colStr = HY_DBFP_JDao.colSqlFactory(listPreview.getSelectedValue().toString(), "FLID",dbTool,jComboBox1.getSelectedIndex());
//            if (colStr == null || colStr.trim().equals(";")) {
//                JOptionPane.showMessageDialog(null, "没有取得表结构信息！", "提示", 0);
//            } else {
//                String colAndNameStri[] = colStr.split(";");
//                String tabid = HY_DBTP_JDao.getTabid(listPreview.getSelectedValue().toString());
//                String stscStr = "";
//                if (!selectedStscModel.isEmpty()) {
//
//                    for (int i = 0; i < selectedStscModel.size(); i++) {
//                        if (stscStr.trim().equals("")) {
//                            stscStr = "'" + selectedStscModel.get(i) + "'";
//                        } else {
//                            stscStr += "," + "'" + selectedStscModel.get(i) + "'";
//                        }
//                    }
//                }
//                stscStr = HY_DBFP_JDao.getStscd(stscStr);
//               resulstList =  HY_DBFP_JDao.findAllList(tabid, colAndNameStri[0], stscStr, type, dbTool, Version.getSelectedIndex());
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        if (colStr == null || colStr.trim().equals(";")) {
//            JOptionPane.showMessageDialog(null, "没有取得表结构信息！", "提示", 0);
//        } else {
//            String colcode[] = colStr.split(";")[0].split(",");
//            if (colList != null && colList.size() > 0) {
//                String title[] = new String[colList.size()];
//                String valueArr[][] = new String[resulstList.size()][colList.size()];
//                for (int i = 0; i < colList.size(); i++) {
//                    HY_DBFP_JBean colBean = (HY_DBFP_JBean) colList.get(i);
//                    title[i] = colBean.getFLDCNNM();
//                }
//                if (resulstList != null && resulstList.size() > 0) {
//                    for (int i = 0; i < resulstList.size(); i++) {
//                        List colvalueList = (List) resulstList.get(i);
//                        for (int k = 0; k < colvalueList.size(); k++) {
//                            Map valueMap = (Map) colvalueList.get(k);
//                            valueArr[i][k] = valueMap.get(colcode[k].toString()).toString();
//                        }
//                    }
//                }
//                //开始重构jtable
//                DefaultTableModel tabModel = new DefaultTableModel(
//                        valueArr, title);
//                preViewTable.setModel(tabModel);
//                preViewTable.setAutoResizeMode(preViewTable.AUTO_RESIZE_OFF);
//            }
//        }

    }//GEN-LAST:event_listPreviewMouseClicked
    /**
     * 数据导出后的数据查看
     * @param evt
     */
    private void exportTabListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exportTabListMouseClicked
        // TODO add your handling code here:
        int type = jComboBox1.getSelectedIndex();

        String colStr = "";
        List<List<Map<String, String>>> resulstList = null;
        String tabid = HY_DBTP_JDao.getTabid(exportTabList.getSelectedValue().toString(), dbTool);
        try {
            colStr = dbTool.getFileds(tabid, dbTool, type,cbDriver.getSelectedIndex());
            if (colStr == null || colStr.trim().equals(";")) {
                JOptionPane.showMessageDialog(null, "没有可供查看数据！", "提示", 1);
            } else {
                String stscStr = "";
                if (!selectedSnameModel.isEmpty()) {
                    for (int i = 0; i < selectedSnameModel.size(); i++) {
                        //返回内容为编码＋名称，处理后得到编码
                        String stcdandname = selectedSnameModel.get(i).toString();
                        int poi = stcdandname.lastIndexOf("]");
                        String stcd = stcdandname.substring(1, poi);//编码
                        if (stscStr.trim().equals("")) {
                            stscStr = "'" + stcd + "'";
                        } else {
                            stscStr += "," + "'" + stcd + "'";
                        }
                    }
                }
                resulstList = HY_DBFP_JDao.findAllList(tabid, colStr, stscStr, type, dbTool,
                        Version.getSelectedIndex(),cbDriver.getSelectedIndex(),charBox.isSelected());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (resulstList != null && resulstList.size()>0) {
            String colcode[] = colStr.split(",");
            if (colStr != null && colStr.length() > 0) {
                String title[] = new String[colcode.length];
                int K=0;
                for(String col:colcode){
                    String name = dbTool.getFiledsCNNM(tabid, col);
                    title[K]=name;
                    K++;
                }
                String valueArr[][] = new String[resulstList.size()][colcode.length];
                if (resulstList != null && resulstList.size() > 0) {

                    for (int i = 0; i < resulstList.size(); i++) {
                        List colvalueList = (List) resulstList.get(i);
                        for (int k = 0; k < colvalueList.size(); k++) {
                            Map valueMap = (Map) colvalueList.get(k);
                            valueArr[i][k] = valueMap.get(colcode[k].toString()).toString();
                        }
                    }
                }
                //开始重构jtable
                DefaultTableModel tabModel = new DefaultTableModel(
                        valueArr, title);
                resultViewTable.setModel(tabModel);
                resultViewTable.setAutoResizeMode(resultViewTable.AUTO_RESIZE_OFF);

            }
        }
    }//GEN-LAST:event_exportTabListMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        tbMainSelect.setSelectedIndex(4);
        btnExportActionPerformed(evt);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPortActionPerformed

    private void jRadioButtonAllMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButtonAllMouseClicked
        // TODO add your handling code here:
        expModel = "all";
        jList1.setEnabled(false);
        jList2.setEnabled(false);

        addButtonYear.setEnabled(false);
        addButtonYears.setEnabled(false);
        removeButtonYear.setEnabled(false);
        removeButtonYears.setEnabled(false);
    }//GEN-LAST:event_jRadioButtonAllMouseClicked

    private void jRadioButtonYMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButtonYMouseClicked
        // TODO add your handling code here:

        jList1.setEnabled(true);
        jList2.setEnabled(true);

        addButtonYear.setEnabled(true);
        addButtonYears.setEnabled(true);
        removeButtonYear.setEnabled(true);
        removeButtonYears.setEnabled(true);
        expModel = "all";
    }//GEN-LAST:event_jRadioButtonYMouseClicked

    private void addButtonYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonYearActionPerformed
        // TODO add your handling code here:
        int index[] = jList1.getSelectedIndices();
        Object values[] = jList1.getSelectedValues();
        if (index != null && index.length > 0) {
            DefaultListModel model = (DefaultListModel) jList1.getModel();
            for (int k = 0; k < index.length; k++) {
                selectedYearsModel.addElement(values[k]);
                jList2.setModel(selectedYearsModel);
                //model.remove(index[k]);
            }
            for (int k = 0; k < index.length; k++) {
                model.removeElement(values[k]);
            }
        }
    }//GEN-LAST:event_addButtonYearActionPerformed

    private void removeButtonYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonYearActionPerformed
        // TODO add your handling code here:
        int index[] = jList2.getSelectedIndices();
        Object values[] = jList2.getSelectedValues();
        if (index != null && index.length > 0) {
            DefaultListModel model = (DefaultListModel) jList1.getModel();
            for (int k = 0; k < index.length; k++) {
                model.addElement(values[k]);
            }
            for (int k = 0; k < index.length; k++) {
                selectedYearsModel.removeElement(values[k]);
            }
        }
    }//GEN-LAST:event_removeButtonYearActionPerformed

    private void addButtonYearsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonYearsActionPerformed
        // TODO add your handling code here:
        DefaultListModel model = (DefaultListModel) jList1.getModel();
        if (!model.isEmpty()) {

            for (int i = 0; i < model.size(); i++) {
                selectedYearsModel.addElement(model.get(i));
            }

            jList2.setModel(selectedYearsModel);
            model.removeAllElements();
        }
    }//GEN-LAST:event_addButtonYearsActionPerformed

    private void removeButtonYearsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonYearsActionPerformed
        // TODO add your handling code here:
        DefaultListModel model = (DefaultListModel) jList1.getModel();
        if (!selectedYearsModel.isEmpty()) {
            for (int i = 0; i < selectedYearsModel.size(); i++) {
                model.addElement(selectedYearsModel.get(i));
            }
            selectedYearsModel.removeAllElements();
        }
    }//GEN-LAST:event_removeButtonYearsActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            DefaultListModel model = (DefaultListModel) jList1.getModel();

            jList2.setModel(selectedYearsModel);
            selectedYearsModel.addElement(jList1.getSelectedValue());
            model.remove(jList1.getSelectedIndex());
        }
    }//GEN-LAST:event_jList1MouseClicked

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            DefaultListModel model = (DefaultListModel) jList1.getModel();

            model.addElement(jList2.getSelectedValue());

            selectedYearsModel.remove(jList2.getSelectedIndex());
        }
    }//GEN-LAST:event_jList2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        tbMainSelect.remove(ExpModelSet);//导出模式设置，是全部导出，还是按年份导出
        tbMainSelect.remove(DataView);
        tbMainSelect.remove(dataIndexPanel);
        MessageLabel.setVisible(false);
        MessageLabel1.setVisible(false);
        MessageLabel2.setVisible(false);
        exitBtn.setVisible(false);
        tbMainSelect.add("数据导出", ExpData);
        tbMainSelect.add("数据查看", DataView);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jComboBox1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBox1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1PropertyChange

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
       if (jComboBox1.getSelectedIndex() == 1) {
            selectedTablesModel.addElement("数据索引表");
        } else {
            selectedTablesModel.removeElement("数据索引表");
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void exitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitBtnActionPerformed
        // TODO add your handling code here:
        dbTool.shutdown();
        System.exit(1);
    }//GEN-LAST:event_exitBtnActionPerformed

    private void btnTablePerStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTablePerStepActionPerformed
        // TODO add your handling code here:
        dbTool.shutdown();
        tbMainSelect.remove(SelectTable);
        tbMainSelect.add("参数设置", ParametSet);
    }//GEN-LAST:event_btnTablePerStepActionPerformed

    private void jRadioButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2MouseClicked

    private void btnExportPerStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportPerStepActionPerformed
        // TODO add your handling code here:
        MessageLabel.setText("");
        MessageLabel1.setText("");
        MessageLabel2.setText("");
        tbMainSelect.remove(ExpData);
        tbMainSelect.remove(DataView);
        tbMainSelect.add("测站选择", SelectStsc);
    }//GEN-LAST:event_btnExportPerStepActionPerformed

    private void jRadioButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1MouseClicked

    private void MessageLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MessageLabel1MouseClicked
        // TODO add your handling code here:
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(new File(txtDataDir.getText()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }//GEN-LAST:event_MessageLabel1MouseClicked

    private void exportTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportTypeActionPerformed
        // TODO add your handling code here:
        if(exportType.getSelectedIndex() == 0){
            jLabel15.setText("只导出文本文件:导出本地数据库中符合导出条件的所有数据，并且写入文本文件。");
            jLabel16.setText("              ");
        }
        if(exportType.getSelectedIndex() == 1){
            jLabel15.setText("同时导出文本文件和数据库：导出本地数据库中符合导出条件的所有数据，并且在写入文本文件的同时写入本导出工具的临时数据库。");
            jLabel16.setText("                 这个导出方式要求您本地数据库结构和标准结构完全一致，否则无法执行导出。");
        }
        if(exportType.getSelectedIndex() == 2){
            jLabel15.setText("只生成本地数据库索引目录：不执行导出数据操作，将您本地数据库中的数据索引以文本文件形式输出，如果数据导出成功。需要重新");
            jLabel16.setText("                生成数据索引可以通过此选项再次生成。运行此操作前请先备份导出的数据。");
        }
    }//GEN-LAST:event_exportTypeActionPerformed

    private void VersionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VersionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VersionActionPerformed

    private void NextBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextBtn1ActionPerformed
        // TODO add your handling code here:

        tbMainSelect.remove(SelectStsc);
        tbMainSelect.add("数据选择", SelectTable);
    }//GEN-LAST:event_NextBtn1ActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
         String selectedValue = SearchText.getText();


        //从源数据库中查询数据库，并且返回测站编码不在右侧list选项框中的数据
        JdbcTemplate jt_stsc = dbTool.getJt1();//从源数据库中取得测站名称信息
        String queryStcdSQL = "";

        if (Version.getSelectedIndex() == 1) {//符合4.0的标准
            queryStcdSQL = "select *  from HY_STSC_A where STNM like '%" + selectedValue + "%'";
        } else {
            queryStcdSQL = "select *  from STHD where STNM like '%" + selectedValue + "%'";
        }
        if (!selectedStscModel.isEmpty()) {
            String expStsc = "";
            for (int i = 0; i < selectedStscModel.size(); i++) {
                if ("".trim().equals(expStsc)) {
                    expStsc = "'" + selectedStscModel.get(i).toString() + "'";
                } else {
                    expStsc += "," + "'" + selectedStscModel.get(i).toString() + "'";
                }
            }
            queryStcdSQL += " and STCD not in(" + expStsc + ")";
        }
        listParamModel_source.removeAllElements();
        liststscModel_source.removeAllElements();
        jt_stsc.query(queryStcdSQL, new RowMapper() {

            public List<HY_STSC_ABean> mapRow(final ResultSet rs, int rowNum) throws SQLException {
                List<HY_STSC_ABean> resultList = null;
                while (rs.next()) {
                    try{
                    String stnm = rs.getString("stnm").trim();
                    if(charBox.isSelected())
                        stnm = new String(stnm.getBytes("ISO-8859-1"),"GBK");
                    stscNameModel_source.addElement(stnm);
                    listParamModel_source.addElement("[" + rs.getString("stcd").trim() + "]" + stnm);
                    liststscModel_source.addElement(rs.getString("stcd").trim());
                    }catch(Exception ex){ex.printStackTrace();}
                }
                return resultList;
            }
        });
        listStsc.setModel(listParamModel_source);

    }//GEN-LAST:event_searchButtonActionPerformed

    private void TestConnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TestConnActionPerformed
        // TODO add your handling code here:
         int driveClassIndex = cbDriver.getSelectedIndex();
        String serverPort = txtPort.getText();
        String ipAddress = txtIP.getText();
        String dbname = DataName.getText();

        if (driveClassIndex == 0) {
            JOptionPane.showMessageDialog(null, "请选择数据库类型！", "错误", 0);
        } else {
            if (serverPort.trim().equals("")) {
                JOptionPane.showMessageDialog(null, "数据库端口号不能为空！", "错误", 0);
            } else {
                if ("".trim().equals(ipAddress)) {
                    JOptionPane.showMessageDialog(null, "服务器ip地址不能为空！", "错误", 0);
                } else {
                    if ("".trim().equals(dbname)) {
                        JOptionPane.showMessageDialog(null, "服务名不能为空！", "错误", 0);
                    } else {
                        expType = exportType.getSelectedIndex();
                        if (driveClassIndex == 1) {//sqlserver
                            DiverClass = "net.sourceforge.jtds.jdbc.Driver";
                            JdbcUrl = "jdbc:jtds:sqlserver://" + txtIP.getText() + ":" + serverPort + "/" + DataName.getText();
                        }
                        if (driveClassIndex == 2) {//sysbase
                            if(charBox.isSelected()){
                                DiverClass = "com.sybase.jdbc3.jdbc.SybDriver";
                                JdbcUrl = "jdbc:sybase:Tds:" + txtIP.getText() + ":" + serverPort + "/" + DataName.getText()+"?charset=cp850";
                            }else{
                                 DiverClass = "com.sybase.jdbc3.jdbc.SybDriver";
                                 JdbcUrl = "jdbc:sybase:Tds:" + txtIP.getText() + ":" + serverPort + "/" + DataName.getText();
                            }
                        }
                        if (driveClassIndex == 3) {//sysbase
                            DiverClass = "oracle.jdbc.driver.OracleDriver";
                            JdbcUrl = "jdbc:oracle:thin:@" + txtIP.getText() + ":" + serverPort + ":" + DataName.getText();
                        }
                        if (driveClassIndex == 4) {//odbc数据源
                            DiverClass = "sun.jdbc.odbc.JdbcOdbcDriver";
                            JdbcUrl = "jdbc:odbc:"+DataName.getText();
                        }
                    }
                }
                try{
                    dbTool = new DBTool(DiverClass, JdbcUrl, txtUser.getText(),
                                            txtPass.getText(), txtDataDir.getText());
                    JdbcTemplate jt_Target = dbTool.getJt1();
                    String checkSQL = "";
                    if (Version.getSelectedIndex() == 1) {//符合4.0的标准
                        checkSQL = "SELECT COUNT(*) FROM HY_STSC_A";
                    } else {
                        checkSQL = "SELECT COUNT(*) FROM STHD";
                    }
                    int checkCount = jt_Target.queryForInt(checkSQL);
                    dbTool.shutdown();
                    JOptionPane.showMessageDialog(null, "连接成功!", "提示",JOptionPane.INFORMATION_MESSAGE);

                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "连接失败，请检查连接参数", "错误", 0);
                }
            }
        }
    }//GEN-LAST:event_TestConnActionPerformed

    private void btnPreview1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreview1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPreview1ActionPerformed

    private void btnPreview2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreview2ActionPerformed
        // TODO add your handling code here:
        ExcelService.createTemplateXls(dbTool,txtDataDir.getText());
    }//GEN-LAST:event_btnPreview2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ExportView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBtn;
    private javax.swing.JButton AddBtnAllTables;
    private javax.swing.JButton AddStstBtn;
    private javax.swing.JButton AddStstBtnAll;
    private javax.swing.JButton CancelBtn;
    private javax.swing.JButton CancelBtnAllTables;
    private javax.swing.JButton CancelStscBtn;
    private javax.swing.JButton CancelStscBtnAll;
    private javax.swing.JTextField DataName;
    private javax.swing.JPanel DataView;
    private javax.swing.JPanel ExpData;
    private javax.swing.JPanel ExpModelSet;
    private javax.swing.JPanel ExpView;
    private javax.swing.JList ListTables;
    private javax.swing.JLabel MessageLabel;
    private javax.swing.JLabel MessageLabel1;
    private javax.swing.JLabel MessageLabel2;
    private javax.swing.JButton NextBtn;
    private javax.swing.JButton NextBtn1;
    private javax.swing.JButton NextBtnOne;
    private javax.swing.JDialog NoTablesDialog;
    private javax.swing.JPanel ParametSet;
    private javax.swing.JTextField SearchText;
    private javax.swing.JPanel SelectStsc;
    private javax.swing.JPanel SelectTable;
    private javax.swing.JScrollPane SelectTableLPanel;
    private javax.swing.JScrollPane SelectTableRPanel;
    private javax.swing.JList SelectedStsc;
    private javax.swing.JList SelectedTables;
    private javax.swing.JButton TestConn;
    private javax.swing.JComboBox Version;
    private javax.swing.JButton addButtonYear;
    private javax.swing.JButton addButtonYears;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnExportPerStep;
    private javax.swing.JButton btnPreview;
    private javax.swing.JButton btnPreview1;
    private javax.swing.JButton btnPreview2;
    private javax.swing.JButton btnTableNextStep;
    private javax.swing.JButton btnTablePerStep;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbDriver;
    private javax.swing.JCheckBox charBox;
    private javax.swing.JTabbedPane dataIndexPanel;
    private javax.swing.JTable dataIndexTable;
    private javax.swing.JButton exitBtn;
    private javax.swing.JList exportTabList;
    private javax.swing.JComboBox exportType;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButtonAll;
    private javax.swing.JRadioButton jRadioButtonY;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JList listPreview;
    private javax.swing.JList listStsc;
    private javax.swing.JList logList;
    private javax.swing.JTable preViewTable;
    private javax.swing.JButton removeButtonYear;
    private javax.swing.JButton removeButtonYears;
    private javax.swing.JTable resultViewTable;
    private javax.swing.JButton searchButton;
    private javax.swing.JTabbedPane tbMainSelect;
    private javax.swing.JTextField txtDataDir;
    private javax.swing.JTextField txtIP;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables

}
