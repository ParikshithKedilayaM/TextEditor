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

public class MenuBar extends JMenuBar implements ActionListener, Observer {

	private static final long serialVersionUID = 1L;
	private FileManager fileManager;
	private JMenu fileMenu = null;
	
	public MenuBar() {
		this.fileManager = new FileManager();
		this.fileManager.addObserver(this);
		fileMenu = new JMenu("File");
		createMenuItems();
		this.add(fileMenu);
		setVisible(true);
	}
	
	private void createMenuItems() {
		JMenuItem newMenuItem = new JMenuItem("New"); 
        JMenuItem openMenuItem = new JMenuItem("Open"); 
        JMenuItem saveMenuItem = new JMenuItem("Save"); 
        
        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
	}

	public void actionPerformed(ActionEvent e) {
		String eventPerformed = e.getActionCommand();
		if (eventPerformed.equals("Open")) {
			fileManager.loadFile(chooseFile());
		} else if (eventPerformed.equals("Save")) {
			String chooseFile = chooseFile();
			if (chooseFile!=null && !chooseFile.equals(""))
				fileManager.saveAsFile(chooseFile);
		} else if (eventPerformed.equals("New")) {
			createNewEditorWorkspace();
		}
	}
	
	private void createNewEditorWorkspace() {
		String text = Model.getInstance().getText();
		if (text == null || text.equals("")) {} else {
			int showConfirmDialog = JOptionPane.showConfirmDialog(null, 
					"Are you sure to clear a new workspace? Any unsaved changes will be discarded");
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
