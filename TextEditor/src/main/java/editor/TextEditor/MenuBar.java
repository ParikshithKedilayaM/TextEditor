package editor.TextEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class MenuBar extends JMenuBar implements ActionListener, Observer {

	private static final long serialVersionUID = 1L;
	private FileManager fileManager;
	private FindReplace findReplace;
	private JMenu fileMenu = null;
	private JMenu editMenu = null;
	
	public MenuBar() {
		this.fileManager = new FileManager();
		this.fileManager.addObserver(this);
		this.findReplace = new FindReplace();
		createMenu();
		setVisible(true);
	}
	
	private void createMenu() {
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		createMenuItems();
		this.add(fileMenu);
		this.add(editMenu);
	}
	
	private void createMenuItems() {
		JMenuItem newMenuItem = new JMenuItem("New"); 
        JMenuItem openMenuItem = new JMenuItem("Open"); 
        JMenuItem saveMenuItem = new JMenuItem("Save"); 
        
        JMenuItem cutMenuItem = new JMenuItem("Cut"); 
        JMenuItem copyMenuItem = new JMenuItem("Copy"); 
        JMenuItem pasteMenuItem = new JMenuItem("Paste"); 
        JMenuItem findOneMenuItem = new JMenuItem("Find"); 
        JMenuItem findAllMenuItem = new JMenuItem("FindAll"); 
        
        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        
        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        pasteMenuItem.addActionListener(this);
        findOneMenuItem.addActionListener(this);
        findAllMenuItem.addActionListener(this);
        
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.add(findOneMenuItem);
        editMenu.add(findAllMenuItem);
	}

	public void actionPerformed(ActionEvent e) {
		String eventPerformed = e.getActionCommand();
		HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(
				Model.getInstance().getTextArea().getSelectionColor());
		if (eventPerformed.equals("Open")) {
			fileManager.loadFile(chooseFile());
		} else if (eventPerformed.equals("Save")) {
			String chooseFile = chooseFile();
			if (chooseFile!=null && !chooseFile.equals(""))
				fileManager.saveAsFile(chooseFile);
		} else if (eventPerformed.equals("New")) {
			createNewEditorWorkspace();
		} else if (eventPerformed.equals("Cut")) {
			Model.getInstance().getTextArea().cut();
		} else if (eventPerformed.equals("Copy")) {
			Model.getInstance().getTextArea().copy();
		} else if (eventPerformed.equals("Paste")) {
			Model.getInstance().getTextArea().paste();
		} else if (eventPerformed.equals("Find")) {
			String showInputDialog = JOptionPane.showInputDialog(null, "Enter text to search: ");
			findReplace.searchOne(showInputDialog, painter);
		} else if (eventPerformed.equals("FindAll")) {
			String showInputDialog = JOptionPane.showInputDialog(null, "Enter text to search: ");
			findReplace.searchAll(showInputDialog, painter);
		}
	}
	
	private void createNewEditorWorkspace() {
		String text = Model.getInstance().getText();
		if (text == null || text.equals("")) {} else {
			int showConfirmDialog = JOptionPane.showConfirmDialog(null, 
					"Are you sure to clear current workspace? Any unsaved changes will be discarded");
			if (showConfirmDialog == 0) {
				Model.getInstance().setText("");
			}
		}
	}

	private String chooseFile() {
		JFileChooser chosenFile = new JFileChooser();
		int showOpenDialog = chosenFile.showOpenDialog(null);
		if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
			return chosenFile.getSelectedFile().getAbsolutePath();
		}
		return "";
	}
	
	private void displayMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	public void update(Observable o, Object arg) {
		displayMessage(Model.getInstance().getDisplayMessage());
	}
	
}
