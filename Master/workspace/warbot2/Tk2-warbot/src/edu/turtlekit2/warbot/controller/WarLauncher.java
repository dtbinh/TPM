/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.turtlekit2.warbot.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * Classe permettant a l'utilisateur d'effectuer un choix des equipes
 * a travers deux modes (un contre un et tournoi). 
 * 
 * @author Bonnotte Jessy, Burc Pierre, Duplouy Olivier, Polizzi Mathieu
 *
 */
@SuppressWarnings("serial")
public class WarLauncher extends javax.swing.JFrame {
	
	private static boolean					MUSIC = false;

    private HashMap<String, Team> 			mapTeam;
    private Team[] 							listeTeam;
    private Clip							_clipEntrance = null;
    
    /**
     * Creates new form WarLauncher
     */
    public WarLauncher() {
        mapTeam = Controller._listeTeam;
        initComponents();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        
        /*try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/edu/turtlekit2/warbot/controller/entrance.wav"));
			_clipEntrance = AudioSystem.getClip();
			_clipEntrance.open(audioInputStream);
			_clipEntrance.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        listeTeam = mapTeam.values().toArray(new Team[0]);
        
        /***********************************************************************
         *                      Gestion du mode 1 (1 vs 1)                     *
         ***********************************************************************
         */
        Object[][] dataTeamTableModel = new Object[listeTeam.length][3];
        
        for (int i = 0; i < listeTeam.length ; i++) {
            
            dataTeamTableModel[i][0] = false;
            dataTeamTableModel[i][1] = listeTeam[i].getName();
            dataTeamTableModel[i][2] = listeTeam[i].getImage();
            
        }
        
        TableModel teamTableModel = new DefaultTableModel(dataTeamTableModel, new String[] {"Selection", "Equipe", "Logo"}){
            
            @SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
            public Class getColumnClass(int column){
                 if (column==0) { return Boolean.class; }
                return getValueAt(1, column).getClass();
            }
            
            @Override
            public boolean isCellEditable(int row, int column){
                if(column == 0){ return true; }
                
                return false;
            }
            
            
        };
        teamTable.setModel(teamTableModel);
        teamTable.setRowHeight(50);
        teamTable.getColumnModel().getColumn(2).setWidth(10);
        teamTable.getColumnModel().getColumn(0).setWidth(10);
        
        /***********************************************************************
         *                      Gestion du mode 2 (tournoi)                    *
         ***********************************************************************
         */
        int nbMatch = 0;
        if((listeTeam.length) % 2 != 0){
            nbMatch = (listeTeam.length / 2) + 1;
        }else{
            nbMatch = listeTeam.length / 2;
        }
        
        Object[][] dataMatchTableModel = new Object[nbMatch][5];
        String[] tmpName = new String[listeTeam.length];
        
        for (int i = 0; i < listeTeam.length; i++) {
            tmpName[i] = listeTeam[i].getName();
        }
        
        for (int i = 0; i < nbMatch ; i++) {
            
            dataMatchTableModel[i][0] = "Match "+i;
            dataMatchTableModel[i][1] = listeTeam[i].getName();
            dataMatchTableModel[i][2] = listeTeam[i].getImage();          
            dataMatchTableModel[i][3] = listeTeam[i].getImage();
            dataMatchTableModel[i][4] = listeTeam[i].getName();
            
        }
        
        TableModel matchTableModel = new DefaultTableModel(dataMatchTableModel, new String[] {"Match", "Equipe 1", "Logo 1", "Logo 2", "Equipe 2"}){
            
            @SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
            public Class getColumnClass(int column){
                return getValueAt(1, column).getClass();
            }
            
            @Override
            public boolean isCellEditable(int row, int column){
                if(column == 1 || column == 4){ return true; }
                
                return false;
            }
        };
        
        matchTable.setModel(matchTableModel);
        
        JComboBox<Object> tmp1 = new JComboBox<Object>(tmpName);
        JComboBox<Object> tmp2 = new JComboBox<Object>(tmpName);
        
        ActionListener monListener = new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < matchTable.getRowCount(); i++) {
                    matchTable.setValueAt(mapTeam.get(matchTable.getValueAt(i, 1)).getImage(), i, 2);
                    matchTable.setValueAt(mapTeam.get(matchTable.getValueAt(i, 4)).getImage(), i, 3);
                }

            }
        };
        
        tmp1.addActionListener(monListener);
        tmp2.addActionListener(monListener);
                
        matchTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(tmp1));
        matchTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(tmp2));
        
        matchTable.setRowHeight(50);
        matchTable.getColumnModel().getColumn(2).setWidth(10);
        matchTable.getColumnModel().getColumn(0).setWidth(10);
        
        if(MUSIC){
        	muteSound.setSelected(false);
        }else{
        	muteSound.setSelected(true);
        }
        
        muteSoundActionPerformed(null);
    }

    @Override
    public void setVisible(boolean tmp){
    	super.setVisible(tmp);
    	/*if(!tmp)
    		_clipEntrance.stop();*/
    }
    
    public static boolean getMusic(){
    	return MUSIC;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelMode1 = new javax.swing.JPanel();
        startMode1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        teamTable = new javax.swing.JTable();
        explorerSlider = new javax.swing.JSlider();
        explorerCounter = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        rocketSlider = new javax.swing.JSlider();
        rocketCounter = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        baseSlider = new javax.swing.JSlider();
        baseCounter = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        foodSlider = new javax.swing.JSlider();
        foodCounter = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        startMode2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        matchTable = new javax.swing.JTable();
        exit = new javax.swing.JButton();
        muteSound = new javax.swing.JCheckBox();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        startMode1.setText("OK");
        startMode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startMode1ActionPerformed(evt);
            }
        });

        teamTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Selection", "Equipe", "Logo"
            }
        ));
        teamTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(teamTable);
        teamTable.getColumnModel().getColumn(0).setMinWidth(30);
        teamTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        teamTable.getColumnModel().getColumn(0).setMaxWidth(30);
        teamTable.getColumnModel().getColumn(2).setMinWidth(50);
        teamTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        teamTable.getColumnModel().getColumn(2).setMaxWidth(50);

        explorerSlider.setMaximum(9);
        explorerSlider.setMinimum(0);
        explorerSlider.setValue(5);
        explorerSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                explorerSliderStateChanged(evt);
            }
        });

        explorerCounter.setText("5");
        explorerCounter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                explorerCounterKeyReleased(evt);
            }
        });

        jLabel1.setText("Nombre explorer :");

        rocketSlider.setMaximum(9);
        rocketSlider.setMinimum(0);
        rocketSlider.setValue(5);
        rocketSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rocketSliderStateChanged(evt);
            }
        });

        rocketCounter.setText("5");
        rocketCounter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rocketCounterKeyReleased(evt);
            }
        });

        jLabel3.setText("Nombre Launcher :");

        baseSlider.setMaximum(4);
        baseSlider.setMinimum(1);
        baseSlider.setValue(1);
        baseSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                baseSliderStateChanged(evt);
            }
        });

        baseCounter.setText("1");
        baseCounter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                baseCounterKeyReleased(evt);
            }
        });

        jLabel4.setText("Nombre base :");

        foodSlider.setMajorTickSpacing(1);
        foodSlider.setMaximum(50);
        foodSlider.setValue(5);
        foodSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                foodSliderStateChanged(evt);
            }
        });
        foodSlider.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                foodSliderKeyReleased(evt);
            }
        });

        foodCounter.setText("0.5");
        foodCounter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foodCounterActionPerformed(evt);
            }
        });
        foodCounter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                foodCounterKeyReleased(evt);
            }
        });

        jLabel5.setText("Nourriture en %");

        javax.swing.GroupLayout panelMode1Layout = new javax.swing.GroupLayout(panelMode1);
        panelMode1.setLayout(panelMode1Layout);
        panelMode1Layout.setHorizontalGroup(
            panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMode1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMode1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(startMode1))
                    .addGroup(panelMode1Layout.createSequentialGroup()
                        .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelMode1Layout.createSequentialGroup()
                                .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(explorerCounter, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                                    .addComponent(rocketCounter)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMode1Layout.createSequentialGroup()
                                .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelMode1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(foodCounter, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(baseCounter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(foodSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(baseSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                            .addComponent(rocketSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(explorerSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelMode1Layout.setVerticalGroup(
            panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMode1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
                    .addGroup(panelMode1Layout.createSequentialGroup()
                        .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(explorerSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(explorerCounter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(rocketCounter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                            .addComponent(rocketSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(baseSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(baseCounter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(foodSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelMode1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(foodCounter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(startMode1)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("1 vs 1", panelMode1);

        startMode2.setText("OK");

        matchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Matchs", "Equipe 1", "Logo 1", "Logo 2", "Equipe 2"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        matchTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(matchTable);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 752, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(startMode2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(startMode2))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Tournois", jPanel2);

        exit.setText("Quitter");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        muteSound.setSelected(true);
        muteSound.setText("Mute");
        muteSound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                muteSoundActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(muteSound)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exit)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exit)
                    .addComponent(muteSound))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {                                     
        // TODO add your handling code here:
        Controller._boucle = false;    
        /*_clipEntrance.stop();*/
        System.exit(0);
    }                                    

    private void startMode1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    	MUSIC = !muteSound.isSelected();
    	/*_clipEntrance.stop();*/
        HashMap<String, Team> retour = new HashMap<String, Team>();
        
        for(int i = 0 ; i < teamTable.getRowCount() ; i++){
            Boolean tmp = (Boolean) teamTable.getValueAt(i, 0);
            if(tmp){
                retour.put((String) teamTable.getValueAt(i, 1), mapTeam.get(teamTable.getValueAt(i, 1)));
            }
        }
        
        if(retour.size() > 2 || retour.size() < 2){
            DialogWrongNumberPlayer dialog = new DialogWrongNumberPlayer(this, rootPaneCheckingEnabled);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }else{
            
            Controller._listeTeam = retour;
            Controller._simulationOption = new Simulation(explorerSlider.getValue(), rocketSlider.getValue(), baseSlider.getValue(), Simulation.VS, foodSlider.getValue()*10);
            Controller._boucle = false;      
            dispose();
        }
    }                                          

    private void explorerSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                            
        // TODO add your handling code here:
        explorerCounter.setText(String.valueOf(explorerSlider.getValue()));
    }                                           

    private void rocketSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                          
        // TODO add your handling code here:
        rocketCounter.setText(String.valueOf(rocketSlider.getValue()));
    }                                         

    private void baseSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                        
        // TODO add your handling code here:
        baseCounter.setText(String.valueOf(baseSlider.getValue()));
    }                                       

    private void explorerCounterKeyReleased(java.awt.event.KeyEvent evt) {                                            
        // TODO add your handling code here:
        int value = 1;
        
        try{
            value = Integer.parseInt(explorerCounter.getText());
        }catch(Exception e){
            if(!explorerCounter.getText().equals("")){
                explorerCounter.setText("1");
            }
        }
        
        if(value > 0 && value < 21){
            explorerSlider.setValue(value);
        }else if(value < 1){
            explorerSlider.setValue(1);
        }else if(value > 20){
            explorerCounter.setText("20");
            explorerSlider.setValue(20);
        }
    }                                           

    private void rocketCounterKeyReleased(java.awt.event.KeyEvent evt) {                                          
        // TODO add your handling code here:
        int value = 1;
        
        try{
            value = Integer.parseInt(rocketCounter.getText());
        }catch(Exception e){
            if(!rocketCounter.getText().equals("")){
                rocketCounter.setText("1");
            }
        }
        
        if(value > 0 && value < 21){
            rocketSlider.setValue(value);
        }else if(value < 1){
            rocketSlider.setValue(1);
        }else if(value > 20){
            rocketCounter.setText("5");
            rocketSlider.setValue(20);
        }
    }                                         

    private void baseCounterKeyReleased(java.awt.event.KeyEvent evt) {                                        
        // TODO add your handling code here:
        int value = 1;
        
        try{
            value = Integer.parseInt(baseCounter.getText());
        }catch(Exception e){
            if(!baseCounter.getText().equals("")){
                baseCounter.setText("1");
            }
        }
        
        if(value > 0 && value < 21){
            baseSlider.setValue(value);
        }else if(value < 1){
            baseSlider.setValue(1);
        }else if(value > 20){
            baseCounter.setText("5");
            baseSlider.setValue(5);
        }
    }                                       

    private void foodSliderKeyReleased(java.awt.event.KeyEvent evt) {                                       
        // TODO add your handling code here:
        
    }                                      

    private void foodSliderStateChanged(javax.swing.event.ChangeEvent evt) {                                        
        // TODO add your handling code here:
        foodCounter.setText(String.valueOf((double)foodSlider.getValue()/10));
    }                                       

    private void foodCounterKeyReleased(java.awt.event.KeyEvent evt) {                                        
        // TODO add your handling code here:
        double value = 1;
        
        try{
            value = Double.parseDouble(foodCounter.getText())*10;
        }catch(Exception e){
            if(!foodCounter.getText().equals("")){
                foodCounter.setText("0.1");
            }
        }
        
        if(value > -1 && value < 21){
            foodSlider.setValue((int)(value*10));
        }else if(value < 1){
            foodSlider.setValue(0);
        }else if(value > 5){
            foodCounter.setText("5");
            foodSlider.setValue(50);
        }
    }                                       

    private void foodCounterActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void muteSoundActionPerformed(java.awt.event.ActionEvent evt) {                                          
        /*if(muteSound.isSelected()){
        	_clipEntrance.stop();
        }else{
        	_clipEntrance.start();
        }*/
    }                                         

    // Variables declaration - do not modify                     
    private javax.swing.JTextField baseCounter;
    private javax.swing.JSlider baseSlider;
    private javax.swing.JButton exit;
    private javax.swing.JTextField explorerCounter;
    private javax.swing.JSlider explorerSlider;
    private javax.swing.JTextField foodCounter;
    private javax.swing.JSlider foodSlider;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable matchTable;
    private javax.swing.JCheckBox muteSound;
    private javax.swing.JPanel panelMode1;
    private javax.swing.JTextField rocketCounter;
    private javax.swing.JSlider rocketSlider;
    private javax.swing.JButton startMode1;
    private javax.swing.JButton startMode2;
    private javax.swing.JTable teamTable;
    // End of variables declaration                   
}