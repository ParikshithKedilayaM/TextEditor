package editor.TextEditor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class UserInterface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedpane = null;
	private int tabCounter = 0;

	public UserInterface() {
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setTitle(" Simple Text Editor ");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		MenuBar menubar = new MenuBar();
		setJMenuBar(menubar);
		tabbedpane = new JTabbedPane();
		add(tabbedpane);
		addTextArea();
		setVisible(true);
	}
	
	private void addTextArea() {
		JTextArea textArea = new JTextArea();
		Model.getInstance().setTextArea(textArea);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setBorder(new TitledBorder(new EtchedBorder(),"Start writing your text here!"));
	    
	    String currentTabName = "New " + tabCounter++;
	    Model.getInstance().setCurrentTabName(currentTabName);
		tabbedpane.add(currentTabName, scrollPane);
		tabbedpane.setSelectedIndex(tabbedpane.getTabCount() - 1);
	}

	public static void main(String[] args) {
		new UserInterface();
	}
	
}
