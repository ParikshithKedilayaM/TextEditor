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

	public MenuBar(UserInterface ui) {
		this.fileManager = new FileManager();
		this.fileManager.addObserver(this);
		this.fileManager.addObserver(ui);
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

	public FindReplace getFindReplace() {
		return findReplace;
	}

	private void createMenuItems() {
		String[] fileMenuOptions = { "New", "Open", "Save" };
		String[] editMenuOptions = { "Cut", "Copy", "Paste", "Find", "FindAll", "Replace", "ReplaceAll" };
		for (String option : fileMenuOptions) {
			fileMenu.add(createMenuItem(option));
		}
		for (String option : editMenuOptions) {
			editMenu.add(createMenuItem(option));
		}
	}

	private JMenuItem createMenuItem(String option) {
		JMenuItem newMenuItem = new JMenuItem(option);
		newMenuItem.addActionListener(this);
		return newMenuItem;
	}

	public void actionPerformed(ActionEvent e) {
		String eventPerformed = e.getActionCommand();
		HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(
				Model.getInstance().getTextArea().getSelectionColor());
		switch (eventPerformed) {
		case "Open":
			fileManager.loadFile(chooseFile());
			break;
		case "Save":
			String chooseFile = chooseFile();
			if (chooseFile != null && !chooseFile.equals("")) {
				fileManager.saveAsFile(chooseFile);
			}
			break;
		case "New":
			createNewEditorWorkspace();
			break;
		case "Cut":
			Model.getInstance().getTextArea().cut();
			break;
		case "Copy":
			Model.getInstance().getTextArea().copy();
			break;
		case "Paste":
			Model.getInstance().getTextArea().paste();
			break;
		case "Find":
			String searchText = getUserInput("Enter text to search: ");
			findReplace.searchOne(searchText, painter);
			break;
		case "FindAll":
			String searchAllText = getUserInput("Enter text to search: ");
			findReplace.searchAll(searchAllText, painter);
			break;
		case "Replace":
			String replaceString = getUserInput("Enter characters to be replaced: ");
			String replaceNewString = getUserInput("Enter new characters to be replaced with: ");
			findReplace.replaceText(replaceString, replaceNewString);
			break;
		case "ReplaceAll":
			String replaceAllString = getUserInput("Enter characters to be replaced: ");
			String replaceAllNewString = getUserInput("Enter new characters to be replaced with: ");
			findReplace.replaceAll(replaceAllString, replaceAllNewString);
			break;

		}
	}

	private String getUserInput(String message) {
		String userText = JOptionPane.showInputDialog(message);
		return userText == null ? "" : userText;
	}

	private void createNewEditorWorkspace() {
		String text = Model.getInstance().getText();
		if (text == null || text.equals("")) {
		} else {
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
		if (arg == "display") {
			displayMessage(Model.getInstance().getDisplayMessage());
		}
	}

}
