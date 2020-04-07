package editor.TextEditor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UserInterface extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedpane = null;
	private int tabCounter = 0;
	private MenuBar menubar = null;
	private ArrayList<JTextArea> textAreaList;
	public UserInterface() {
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setTitle(" Simple Text Editor ");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		createComponents();
	}
	
	private void createComponents() {
		textAreaList = new ArrayList<JTextArea>();
		menubar = new MenuBar();
		tabbedpane = new JTabbedPane();
		setJMenuBar(menubar);
		add(tabbedpane);
		addTextArea();
		addNewTabButton();
		tabbedpane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					addTextArea();
				}
			}
		});
		tabbedpane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Model.getInstance().setCurrentTabName(tabbedpane.getName());
				Model.getInstance().setTextArea(textAreaList.get(tabbedpane.getSelectedIndex()));
				Model.getInstance().getTextArea().requestFocusInWindow();
			}
		});
	}
	
	private void addTextArea() {
		JTextArea textArea = new JTextArea();
		Model.getInstance().setTextArea(textArea);
		textAreaList.add(textArea);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setBorder(new TitledBorder(new EtchedBorder(),"Start writing your text here!"));
	    String currentTabName = "New " + tabCounter++;
	    Model.getInstance().setCurrentTabName(currentTabName);
		tabbedpane.add(currentTabName, scrollPane);
		tabbedpane.setSelectedIndex(tabbedpane.getTabCount() - 1);
	}
	
	private void addNewTabButton() {
		JButton newTabButton = new JButton("New Tab");
		newTabButton.setContentAreaFilled(false);
		newTabButton.setBorderPainted(false);
		newTabButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTextArea();
			}
		});
		menubar.add(newTabButton);
	}

	public static void main(String[] args) {
		new UserInterface();
	}
	
}
