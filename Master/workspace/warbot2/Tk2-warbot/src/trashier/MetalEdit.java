/*
 * MetalEdit.java
 */

package trashier; 

import java.awt.Dimension;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/** 
 * MetalEdit: The beginnings of the user interface for a word processor,
 * with no functionality.
 * 

* The example is on page 6 of the book. *

 */
public class MetalEdit extends javax.swing.JFrame {

    /** 
     * The resource bundle for the default locale 
     */
    private ResourceBundle resources = null;
  
    /** 
     * Path to the image resources in the resource bundle (in this 
     * case, .gif and .jpg files)
     */
    private String imagePath = null;
    
    /** 
     * Create a new MetalEdit window
     */
    public MetalEdit() {
	initResources();
        initComponents();
        initDocument();

	// All frames and dialogs should be packed before they are
	// first displayed. This allows the layout managers to size
	// everything appropriately before the window is made visible.
        pack();
    }

    /**
     * Loads locale-specific resources: strings, images, et cetera
     */
    private void initResources() {
        Locale locale = Locale.getDefault();
        resources = ResourceBundle.getBundle(
            "samples.resources.bundles.MetalEditResources", locale);
        imagePath = resources.getString("images.path");
    }

    private void initDocument() {

	// Get the document, and make sure it's a styled document
	StyledDocument doc = null;
	Document simpleDoc = textPane.getDocument();
	if (!(simpleDoc instanceof StyledDocument)) {
            String castErr = resources.getString("styledDocumentError");
	    if (castErr == null) {
	        castErr = "Must be StyledDocument";
	    }
	    throw new ClassCastException(castErr);
	} else {
            doc = (StyledDocument)textPane.getDocument();
	}

        StyleContext styleContext = new StyleContext();
//        IntroductionDocument introDoc = 
//				new IntroductionDocument(doc, styleContext);
//        introDoc.loadDocument();
    } // initDocument()
    
    /** 
     * This method is called from within the constructor to
     * initialize the window.
     */
  private void initComponents () {
    menuBar = new javax.swing.JMenuBar();
    menuFile = new javax.swing.JMenu();
    menuItemNew = new javax.swing.JMenuItem();
    menuItemOpen = new javax.swing.JMenuItem();
    menuItemClose = new javax.swing.JMenuItem();
    separator1 = new javax.swing.JSeparator ();
    menuItemSave = new javax.swing.JMenuItem();
    menuItemSaveAs = new javax.swing.JMenuItem();
    separator2 = new javax.swing.JSeparator ();
    menuItemPageSetup = new javax.swing.JMenuItem();
    menuItemPrint = new javax.swing.JMenuItem();
    separator3 = new javax.swing.JSeparator ();
    separator4 = new javax.swing.JSeparator ();
    menuItemExit = new javax.swing.JMenuItem();
    menuEdit = new javax.swing.JMenu();
    menuItemUndo = new javax.swing.JMenuItem();
    menuItemRedo = new javax.swing.JMenuItem();
    separator5 = new javax.swing.JSeparator ();
    menuItemCut = new javax.swing.JMenuItem();
    menuItemCopy = new javax.swing.JMenuItem();
    menuItemPaste = new javax.swing.JMenuItem();
    deleteMenuItem = new javax.swing.JMenuItem();
    deleteMenuItem = new javax.swing.JMenuItem();
    separator6 = new javax.swing.JSeparator ();
    menuItemFind = new javax.swing.JMenuItem();
    menuItemFindAgain = new javax.swing.JMenuItem();
    menuItemSelectAll = new javax.swing.JMenuItem();
    menuFormat = new javax.swing.JMenu();
    menuItemDocument = new javax.swing.JMenuItem();
    menuItemSection = new javax.swing.JMenuItem();
    menuItemParagraph = new javax.swing.JMenuItem();
    separator7 = new javax.swing.JSeparator ();
    subMenuFont = new javax.swing.JMenu();
    subMenuStyle = new javax.swing.JMenu();
    boldCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
    italicCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
    underlineCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
    subMenuSize = new javax.swing.JMenu();
    separator8 = new javax.swing.JSeparator ();
    alignLeftRadioMenuItem = new javax.swing.JRadioButtonMenuItem();
    alignCenterRadioMenuItem = new javax.swing.JRadioButtonMenuItem();
    alignRightRadioMenuItem = new javax.swing.JRadioButtonMenuItem();
    menuHelp = new javax.swing.JMenu();
    menuItemContents = new javax.swing.JMenuItem();
    menuItemTutorial = new javax.swing.JMenuItem();
    menuItemIndex = new javax.swing.JMenuItem();
    menuItemSearch = new javax.swing.JMenuItem();
    separator9 = new javax.swing.JSeparator ();
    menuItemAbout = new javax.swing.JMenuItem();
    popup = new javax.swing.JPopupMenu();
    popupItemCut = new javax.swing.JMenuItem();
    popupItemCopy = new javax.swing.JMenuItem();
    popupItemPaste = new javax.swing.JMenuItem();
    deletePopupItem = new javax.swing.JMenuItem();
    popupItemClear = new javax.swing.JMenuItem();
    toolBar = new javax.swing.JToolBar();
    buttonNew = new javax.swing.JButton();
    buttonOpen = new javax.swing.JButton();
    buttonSave = new javax.swing.JButton();
    buttonPrint = new javax.swing.JButton();
    buttonCut = new javax.swing.JButton();
    buttonCopy = new javax.swing.JButton();
    buttonPaste = new javax.swing.JButton();
    deleteButton = new javax.swing.JButton();
    buttonZoom = new javax.swing.JButton();
    boldToggleButton = new javax.swing.JToggleButton();
    italicToggleButton = new javax.swing.JToggleButton();
    underlineToggleButton = new javax.swing.JToggleButton();
    alignLeftToggleButton = new javax.swing.JToggleButton();
    alignCenterToggleButton = new javax.swing.JToggleButton();
    alignRightToggleButton = new javax.swing.JToggleButton();
    scrollPane = new javax.swing.JScrollPane();
    textPane = new javax.swing.JTextPane();

      menuFile.setMnemonic(resources.getString("mnemonicFile").charAt(0));
      menuFile.setText(resources.getString("menuFile"));
      menuFile.setMargin(new java.awt.Insets(2, 2, 2, 40));
  
        menuItemNew.setMnemonic(resources.getString("mnemonicItemNew").charAt(0));
        menuItemNew.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemNew")));
        menuItemNew.setText(resources.getString("menuItemNew"));
    
        menuFile.add(menuItemNew);
        menuItemOpen.setMnemonic(resources.getString("mnemonicItemOpen").charAt(0));
        menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemOpen")));
        menuItemOpen.setText(resources.getString("menuItemOpen"));
    
        menuFile.add(menuItemOpen);
        menuItemClose.setMnemonic(resources.getString("mnemonicItemClose").charAt(0));
        menuItemClose.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemClose")));
        menuItemClose.setText(resources.getString("menuItemClose"));
    
        menuFile.add(menuItemClose);
    
        menuFile.add(separator1);
        menuItemSave.setMnemonic(resources.getString("mnemonicItemSave").charAt(0));
        menuItemSave.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemSave")));
        menuItemSave.setText(resources.getString("menuItemSave"));
    
        menuFile.add(menuItemSave);
        menuItemSaveAs.setMnemonic(resources.getString("mnemonicItemSaveAs").charAt(0));
        menuItemSaveAs.setText(resources.getString("menuItemSaveAs"));
    
        menuFile.add(menuItemSaveAs);
    
        menuFile.add(separator2);
        menuItemPageSetup.setMnemonic(resources.getString("mnemonicItemPageSetup").charAt(0));
        menuItemPageSetup.setText(resources.getString("menuItemPageSetup"));
    
        menuFile.add(menuItemPageSetup);
        menuItemPrint.setMnemonic(resources.getString("mnemonicItemPrint").charAt(0));
        menuItemPrint.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemPrint")));
        menuItemPrint.setText(resources.getString("menuItemPrint"));
    
        menuFile.add(menuItemPrint);
    
        menuFile.add(separator4);
        menuItemExit.setMnemonic(resources.getString("mnemonicItemExit").charAt(0));
        menuItemExit.setText(resources.getString("menuItemExit"));
    
        menuFile.add(menuItemExit);
      menuBar.add(menuFile);
      menuEdit.setMnemonic(resources.getString("mnemonicEdit").charAt(0));
      menuEdit.setText(resources.getString("menuEdit"));
  
        menuItemUndo.setMnemonic(resources.getString("mnemonicItemUndo").charAt(0));
        menuItemUndo.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemUndo")));
        menuItemUndo.setText(resources.getString("menuItemUndo"));
    
        menuEdit.add(menuItemUndo);
        menuItemRedo.setMnemonic(resources.getString("mnemonicItemRedo").charAt(0));
        menuItemRedo.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemRedo")));
        menuItemRedo.setText(resources.getString("menuItemRedo"));
    
        menuEdit.add(menuItemRedo);
    
        menuEdit.add(separator5);
        menuItemCut.setMnemonic(resources.getString("mnemonicItemCut").charAt(0));
        menuItemCut.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemCut")));
        menuItemCut.setText(resources.getString("menuItemCut"));
    
        menuEdit.add(menuItemCut);
        menuItemCopy.setMnemonic(resources.getString("mnemonicItemCopy").charAt(0));
        menuItemCopy.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemCopy")));
        menuItemCopy.setText(resources.getString("menuItemCopy"));
    
        menuEdit.add(menuItemCopy);

	// paste
        menuItemPaste.setMnemonic(resources.getString("mnemonicItemPaste").charAt(0));
        menuItemPaste.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemPaste")));
        menuItemPaste.setText(resources.getString("menuItemPaste"));
    
        menuEdit.add(menuItemPaste);

	// delete
        deleteMenuItem.setMnemonic(resources.getString("menuitem.delete.label").charAt(0));
        deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(
	    resources.getString("menuitem.delete.accelerator")));
        deleteMenuItem.setText(
	    resources.getString("menuitem.delete.label"));
        menuEdit.add(deleteMenuItem);
    
        menuEdit.add(separator6);
        menuItemFind.setMnemonic(resources.getString("mnemonicItemFind").charAt(0));
        menuItemFind.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemFind")));
        menuItemFind.setText(resources.getString("menuItemFind"));
    
        menuEdit.add(menuItemFind);
        menuItemFindAgain.setMnemonic(resources.getString("mnemonicItemFindAgain").charAt(0));
        menuItemFindAgain.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemFindAgain")));
        menuItemFindAgain.setText(resources.getString("menuItemFindAgain"));
        menuItemFindAgain.setEnabled (false);
    
        menuEdit.add(menuItemFindAgain);
        menuItemSelectAll.setMnemonic(resources.getString("mnemonicItemSelectAll").charAt(0));
        menuItemSelectAll.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemSelectAll")));
        menuItemSelectAll.setText(resources.getString("menuItemSelectAll"));
    
        menuEdit.add(menuItemSelectAll);
      menuBar.add(menuEdit);
  
      menuFormat.setMnemonic(resources.getString("mnemonicFormat").charAt(0));
      menuFormat.setText(resources.getString("menuFormat"));
  
        menuItemDocument.setMnemonic(resources.getString("mnemonicItemDocument").charAt(0));
        menuItemDocument.setText(resources.getString("menuItemDocument"));
    
        menuFormat.add(menuItemDocument);
        menuItemSection.setMnemonic(resources.getString("mnemonicItemSection").charAt(0));
        menuItemSection.setText(resources.getString("menuItemSection"));
    
        menuFormat.add(menuItemSection);
        menuItemParagraph.setMnemonic(resources.getString("mnemonicItemParagraph").charAt(0));
        menuItemParagraph.setText(resources.getString("menuItemParagraph"));
    
        menuFormat.add(menuItemParagraph);
    
        menuFormat.add(separator7);
        subMenuFont.setMnemonic(resources.getString("mnemonicSubFont").charAt(0));
        subMenuFont.setText(resources.getString("subMenuFont"));
    
        menuFormat.add(subMenuFont);
        subMenuStyle.setMnemonic(resources.getString("mnemonicSubStyle").charAt(0));
        subMenuStyle.setText(resources.getString("subMenuStyle"));
    
	  // bold
          boldCheckBoxMenuItem.setSelected(true);
          boldCheckBoxMenuItem.setText(
	      resources.getString("menuitem.bold.label"));
	  boldCheckBoxMenuItem.setMnemonic(
	      resources.getString("menuitem.bold.mnemonic").charAt(0));
	  boldCheckBoxMenuItem.setAccelerator(KeyStroke.getKeyStroke(
	      resources.getString("menuitem.bold.accelerator")));
          subMenuStyle.add(boldCheckBoxMenuItem);

	  // italic
          italicCheckBoxMenuItem.setText(
	      resources.getString("menuitem.italic.label"));
	  italicCheckBoxMenuItem.setMnemonic(
	      resources.getString("menuitem.italic.mnemonic").charAt(0));
	  italicCheckBoxMenuItem.setAccelerator(KeyStroke.getKeyStroke(
	      resources.getString("menuitem.italic.accelerator")));
          subMenuStyle.add(italicCheckBoxMenuItem);

	  // underline
          underlineCheckBoxMenuItem.setText(
	      resources.getString("menuitem.underline.label"));
	  underlineCheckBoxMenuItem.setMnemonic(
	      resources.getString("menuitem.underline.mnemonic").charAt(0));
	  underlineCheckBoxMenuItem.setAccelerator(KeyStroke.getKeyStroke(
	      resources.getString("menuitem.underline.accelerator")));
          subMenuStyle.add(underlineCheckBoxMenuItem);

        menuFormat.add(subMenuStyle);
        subMenuSize.setMnemonic(resources.getString("mnemonicSubSize").charAt(0));
        subMenuSize.setText(resources.getString("subMenuSize"));
    
        menuFormat.add(subMenuSize);
    
        menuFormat.add(separator8);

	// align left
        alignLeftRadioMenuItem.setText(
	    resources.getString("menuitem.align.left.label"));
        alignLeftRadioMenuItem.setMnemonic(
	    resources.getString("menuitem.align.left.mnemonic").charAt(0));
        alignLeftRadioMenuItem.setAccelerator(KeyStroke.getKeyStroke(
	    resources.getString("menuitem.align.left.accelerator")));
        alignLeftRadioMenuItem.setSelected(true);
        menuFormat.add(alignLeftRadioMenuItem);

	// align center
        alignCenterRadioMenuItem.setText(
	    resources.getString("menuitem.align.center.label"));
        alignCenterRadioMenuItem.setMnemonic(
	    resources.getString("menuitem.align.center.mnemonic").charAt(0));
        alignCenterRadioMenuItem.setAccelerator(KeyStroke.getKeyStroke(
	    resources.getString("menuitem.align.center.accelerator")));
        menuFormat.add(alignCenterRadioMenuItem);

	// align right
        alignRightRadioMenuItem.setText(
	    resources.getString("menuitem.align.right.label"));
        alignRightRadioMenuItem.setMnemonic(
	    resources.getString("menuitem.align.right.mnemonic").charAt(0));
        alignRightRadioMenuItem.setAccelerator(KeyStroke.getKeyStroke(
	    resources.getString("menuitem.align.right.accelerator")));
        menuFormat.add(alignRightRadioMenuItem);
    
        // Group the radio buttons.
        ButtonGroup group1 = new ButtonGroup();
        group1.add(alignLeftRadioMenuItem);
        group1.add(alignCenterRadioMenuItem);
        group1.add(alignRightRadioMenuItem);
    
      menuBar.add(menuFormat);
      menuHelp.setMnemonic(resources.getString("mnemonicHelp").charAt(0));
      menuHelp.setText(resources.getString("menuHelp"));
  
        menuItemContents.setMnemonic(resources.getString("mnemonicItemContents").charAt(0));
        menuItemContents.setText(resources.getString("menuItemContents"));
    
        menuHelp.add(menuItemContents);
        menuItemTutorial.setMnemonic(resources.getString("mnemonicItemTutorial").charAt(0));
        menuItemTutorial.setText(resources.getString("menuItemTutorial"));
    
        menuHelp.add(menuItemTutorial);
        menuItemIndex.setMnemonic(resources.getString("mnemonicItemIndex").charAt(0));
        menuItemIndex.setText(resources.getString("menuItemIndex"));
    
        menuHelp.add(menuItemIndex);
        menuItemSearch.setMnemonic(resources.getString("mnemonicItemSearch").charAt(0));
        menuItemSearch.setText(resources.getString("menuItemSearch"));
    
        menuHelp.add(menuItemSearch);
    
        menuHelp.add(separator9);
        menuItemAbout.setMnemonic(resources.getString("mnemonicItemAbout").charAt(0));
        menuItemAbout.setText(resources.getString("menuItemAbout"));
    
        menuHelp.add(menuItemAbout);
      menuBar.add(menuHelp);

      popupItemCut.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemCut")));
      popupItemCut.setText(resources.getString("popupItemCut"));
      popupItemCut.setMnemonic(resources.getString("mnemonicPopupItemCut").charAt(0));
  
      popup.add(popupItemCut);
      popupItemCopy.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemCopy")));
      popupItemCopy.setText(resources.getString("popupItemCopy"));
      popupItemCopy.setMnemonic(resources.getString("mnemonicPopupItemCopy").charAt(0));
  
      popup.add(popupItemCopy);

      // paste
      popupItemPaste.setAccelerator(KeyStroke.getKeyStroke(resources.getString("keyStrokeItemPaste")));
      popupItemPaste.setText(resources.getString("popupItemPaste"));
      popupItemPaste.setMnemonic(resources.getString("mnemonicPopupItemPaste").charAt(0));
      popup.add(popupItemPaste);

      // delete
      deletePopupItem.setAccelerator(KeyStroke.getKeyStroke(
          resources.getString("menuitem.delete.accelerator")));
      deletePopupItem.setText(
          resources.getString("menuitem.delete.label"));
      deletePopupItem.setMnemonic(
          resources.getString("menuitem.delete.mnemonic").charAt(0));
      popup.add(deletePopupItem);

      // clear
      popupItemClear.setText(resources.getString("popupItemClear"));
      popupItemClear.setMnemonic(resources.getString("mnemonicPopupItemClear").charAt(0));
  
      popup.add(popupItemClear);
    setTitle (resources.getString("title"));
    addWindowListener (new java.awt.event.WindowAdapter () {
      public void windowClosing (java.awt.event.WindowEvent event) {
        exitMetalEdit(event);
      }
    }
    );

    toolBar.setName (resources.getString("toolbarName"));

      buttonNew.setIcon(new ImageIcon(getClass().
          getResource(imagePath+resources.getString("imageNew"))));
      buttonNew.setToolTipText(resources.getString("toolTipImageNew"));
      buttonNew.getAccessibleContext().setAccessibleName(resources.getString("accessibleNameImageNew"));
  
      toolBar.add(buttonNew);
  
      buttonOpen.setIcon(new ImageIcon(getClass().getResource(imagePath+resources.getString("imageOpen"))));
      buttonOpen.setToolTipText(resources.getString("toolTipImageOpen"));
      buttonOpen.getAccessibleContext().setAccessibleName(resources.getString("accessibleNameImageSave"));
  
      toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
  
      toolBar.add(buttonOpen);
  
      buttonSave.setIcon(new ImageIcon(getClass().getResource(imagePath+resources.getString("imageSave"))));
      buttonSave.setToolTipText(resources.getString("toolTipImageSave"));
      buttonSave.getAccessibleContext().setAccessibleName(resources.getString("accessibleNameImageSave"));
  
      toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
  
      toolBar.add(buttonSave);
  
      buttonPrint.setIcon(new ImageIcon(getClass().getResource(imagePath+resources.getString("imagePrint"))));
      buttonPrint.setToolTipText(resources.getString("toolTipImagePrint"));
      buttonPrint.getAccessibleContext().setAccessibleName(resources.getString("accessibleNameImagePrint"));
  
      toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
  
      toolBar.add(buttonPrint);
  
      buttonCut.setIcon(new ImageIcon(getClass().getResource(imagePath+resources.getString("imageCut"))));
      buttonCut.setToolTipText(resources.getString("toolTipImageCut"));
      buttonCut.getAccessibleContext().setAccessibleName(resources.getString("accessibleNameImageCut"));
  
      toolBar.add(Box.createRigidArea(new Dimension(11, 11)));
  
      toolBar.add(buttonCut);
  
      buttonCopy.setIcon(new ImageIcon(getClass().getResource(imagePath+resources.getString("imageCopy"))));
      buttonCopy.setToolTipText(resources.getString("toolTipImageCopy"));
      buttonCopy.getAccessibleContext().setAccessibleName(resources.getString("accessibleNameImageCopy"));
  
      toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
  
      toolBar.add(buttonCopy);
  
      // paste
      buttonPaste.setIcon(new ImageIcon(getClass().getResource(imagePath+resources.getString("imagePaste"))));
      buttonPaste.setToolTipText(resources.getString("toolTipImagePaste"));
      buttonPaste.getAccessibleContext().setAccessibleName(resources.getString("accessibleNameImagePaste"));
      toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
      toolBar.add(buttonPaste);

      // delete
      deleteButton.setIcon(new ImageIcon(
          getClass().getResource(
	      imagePath+resources.getString("menuitem.delete.image"))));
      deleteButton.setToolTipText(
          resources.getString("menuitem.delete.tooltip"));
      // This button has no label; give it a name for users with disabilities
      deleteButton.getAccessibleContext().setAccessibleName(
          resources.getString("menuitem.delete.label"));
      toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
      toolBar.add(deleteButton);

      // delete
  
      // zoom
      buttonZoom.setIcon(new ImageIcon(getClass().getResource(imagePath+resources.getString("imageZoom"))));
      buttonZoom.setToolTipText(resources.getString("toolTipImageZoom"));
      buttonZoom.getAccessibleContext().setAccessibleName(resources.getString("accessibleNameImageZoom"));
      toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
      toolBar.add(buttonZoom);
  
      // bold
      boldToggleButton.setIcon(new ImageIcon(getClass().
          getResource(imagePath+resources.getString("menuitem.bold.image"))));
      boldToggleButton.setToolTipText(
          resources.getString("menuitem.bold.tooltip"));
      boldToggleButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
      // has no visible text; set name for users with disabilities
      boldToggleButton.getAccessibleContext().setAccessibleName(
          resources.getString("menuitem.bold.label"));
      toolBar.add(Box.createRigidArea(new Dimension(11, 11)));
      toolBar.add(boldToggleButton);
  

      // italic
      italicToggleButton.setIcon(new ImageIcon(getClass().
          getResource(imagePath+resources.getString("menuitem.italic.image"))));
      italicToggleButton.setToolTipText(
          resources.getString("menuitem.italic.tooltip"));
      italicToggleButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
      // has no visible text; set name for users with disabilities
      italicToggleButton.getAccessibleContext().setAccessibleName(
          resources.getString("menuitem.italic.label"));
      toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
      toolBar.add(italicToggleButton);
  
      // underline
      underlineToggleButton.setIcon(new ImageIcon(getClass().
          getResource(
	      imagePath+resources.getString("menuitem.underline.image"))));
      underlineToggleButton.setToolTipText(
          resources.getString("menuitem.underline.tooltip"));
      underlineToggleButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
      // has no visible text; set name for users with disabilities
      underlineToggleButton.getAccessibleContext().setAccessibleName(
          resources.getString("menuitem.underline.label"));
      toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
      toolBar.add(underlineToggleButton);
  
      // align left
      alignLeftToggleButton.setIcon(new ImageIcon(getClass().
          getResource(imagePath+
	      resources.getString("menuitem.align.left.image"))));
      alignLeftToggleButton.setToolTipText(
          resources.getString("menuitem.align.left.tooltip"));
      alignLeftToggleButton.setSelected(true);
      alignLeftToggleButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
      // has no visible text; set name for users with disabilities
      alignLeftToggleButton.getAccessibleContext().setAccessibleName(
          resources.getString("menuitem.align.left.label"));
      toolBar.add(Box.createRigidArea(new Dimension(11, 11)));
      toolBar.add(alignLeftToggleButton);
  
      // align center
      alignCenterToggleButton.setIcon(
          new ImageIcon(getClass().getResource(imagePath+
	      resources.getString("menuitem.align.center.image"))));
      alignCenterToggleButton.setToolTipText(
          resources.getString("menuitem.align.center.tooltip"));
      alignCenterToggleButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
      // has no visible text; set name for users with disabilities
      alignCenterToggleButton.getAccessibleContext().setAccessibleName(
          resources.getString("menuitem.align.center.label"));
      toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
      toolBar.add(alignCenterToggleButton);
  
      // align right
      alignRightToggleButton.setIcon(
          new ImageIcon(getClass().getResource(imagePath+
	      resources.getString("menuitem.align.right.image"))));
      alignRightToggleButton.setToolTipText(
          resources.getString("menuitem.align.right.tooltip"));
      alignRightToggleButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
      // has no visible text; set name for users with disabilities
      alignRightToggleButton.getAccessibleContext().setAccessibleName(
          resources.getString("menuitem.align.right.label"));
      toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
      toolBar.add(alignRightToggleButton);
  
      toolBar.add(Box.createRigidArea(new Dimension(2, 2)));
  
      // Group the radio buttons.
      ButtonGroup group2 = new ButtonGroup();
      group2.add(alignLeftToggleButton);
      group2.add(alignCenterToggleButton);
      group2.add(alignRightToggleButton);
  
      // this rigid area stretch the height of the toolbar
      toolBar.add(Box.createRigidArea(new Dimension(0, 24)));
  
    getContentPane().add(toolBar, java.awt.BorderLayout.NORTH);

    // We manipulate the preferred size, rather than explicitly setting
    // the size. This allows the layout managers to properly adjust to 
    // localization and user preferences
    scrollPane.setPreferredSize (new java.awt.Dimension(500, 200));

      textPane.getAccessibleContext().setAccessibleName(resources.getString("accessibleNameTextPane"));
      textPane.getAccessibleContext().setAccessibleDescription(resources.getString("accessibleDescriptionTextPane"));
      textPane.addMouseListener (new java.awt.event.MouseAdapter () {
        public void mousePressed (java.awt.event.MouseEvent evt) {
          textPaneMousePressed (evt);
        }
      }
      );
  
      scrollPane.setViewportView (textPane);
  

    getContentPane().add(scrollPane, java.awt.BorderLayout.CENTER);

    setJMenuBar(menuBar);

  }

  private void textPaneMousePressed (java.awt.event.MouseEvent evt) {
//    if (evt.isPopupTrigger()) { -- doesn't work ?! //}
    if(evt.isPopupTrigger() || (evt.getSource().equals(textPane) && SwingUtilities.isRightMouseButton(evt))) {
      popup.show(textPane, evt.getX(), evt.getY());
    }
  }

    /** 
     * Exit the application. We always exit through this method,
     * so that we can add additional exit code if necessary.
     */
    private void exitMetalEdit(java.awt.event.WindowEvent evt) {
        System.exit (0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main (String args[]) {
      MetalEdit editor = new MetalEdit();
      editor.setVisible(true);
    }
  
    // Variables
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuItemNew;
    private javax.swing.JMenuItem menuItemOpen;
    private javax.swing.JMenuItem menuItemClose;
    private javax.swing.JSeparator separator1;
    private javax.swing.JMenuItem menuItemSave;
    private javax.swing.JMenuItem menuItemSaveAs;
    private javax.swing.JSeparator separator2;
    private javax.swing.JMenuItem menuItemPageSetup;
    private javax.swing.JMenuItem menuItemPrint;
    private javax.swing.JSeparator separator3;
    private javax.swing.JSeparator separator4;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenuItem menuItemUndo;
    private javax.swing.JMenuItem menuItemRedo;
    private javax.swing.JSeparator separator5;
    private javax.swing.JMenuItem menuItemCut;
    private javax.swing.JMenuItem menuItemCopy;
    private javax.swing.JMenuItem menuItemPaste;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JSeparator separator6;
    private javax.swing.JMenuItem menuItemFind;
    private javax.swing.JMenuItem menuItemFindAgain;
    private javax.swing.JMenuItem menuItemSelectAll;
    private javax.swing.JMenu menuFormat;
    private javax.swing.JMenuItem menuItemDocument;
    private javax.swing.JMenuItem menuItemSection;
    private javax.swing.JMenuItem menuItemParagraph;
    private javax.swing.JSeparator separator7;
    private javax.swing.JMenu subMenuFont;
    private javax.swing.JMenu subMenuStyle;
    private javax.swing.JCheckBoxMenuItem boldCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem italicCheckBoxMenuItem;
    private javax.swing.JCheckBoxMenuItem underlineCheckBoxMenuItem;
    private javax.swing.JMenu subMenuSize;
    private javax.swing.JSeparator separator8;
    private javax.swing.JRadioButtonMenuItem alignLeftRadioMenuItem;
    private javax.swing.JRadioButtonMenuItem alignCenterRadioMenuItem;
    private javax.swing.JRadioButtonMenuItem alignRightRadioMenuItem;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuItemContents;
    private javax.swing.JMenuItem menuItemTutorial;
    private javax.swing.JMenuItem menuItemIndex;
    private javax.swing.JMenuItem menuItemSearch;
    private javax.swing.JSeparator separator9;
    private javax.swing.JMenuItem menuItemAbout;
    private javax.swing.JPopupMenu popup;
    private javax.swing.JMenuItem popupItemCut;
    private javax.swing.JMenuItem popupItemCopy;
    private javax.swing.JMenuItem popupItemPaste;
    private javax.swing.JMenuItem deletePopupItem;
    private javax.swing.JMenuItem popupItemClear;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JButton buttonNew;
    private javax.swing.JButton buttonOpen;
    private javax.swing.JButton buttonSave;
    private javax.swing.JButton buttonPrint;
    private javax.swing.JButton buttonCut;
    private javax.swing.JButton buttonCopy;
    private javax.swing.JButton buttonPaste;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton buttonZoom;
    private javax.swing.JToggleButton boldToggleButton;
    private javax.swing.JToggleButton italicToggleButton;
    private javax.swing.JToggleButton underlineToggleButton;
    private javax.swing.JToggleButton alignLeftToggleButton;
    private javax.swing.JToggleButton alignCenterToggleButton;
    private javax.swing.JToggleButton alignRightToggleButton;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTextPane textPane;
  
} // class MetalEdit
