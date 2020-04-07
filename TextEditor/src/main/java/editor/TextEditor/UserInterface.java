package editor.TextEditor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
				try {
					Model.getInstance().setCurrentTabName(tabbedpane.getName());
					Model.getInstance().setTextArea(textAreaList.get(tabbedpane.getSelectedIndex()));
					Model.getInstance().getTextArea().requestFocusInWindow();
				} catch (Exception e1) {
					addTextArea();
				}
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
		scrollPane.setBorder(new TitledBorder(new EtchedBorder(), "Start writing your text here!"));

		Model.getInstance().setLatestTab(Math.max(Model.getInstance().getLatestTab() + 1, tabbedpane.getTabCount()));

		if (tabbedpane.getTabCount() == 0) {
			Model.getInstance().setLatestTab(1);
		}

		String currentTabName = "New " + (Model.getInstance().getLatestTab());

		Model.getInstance().setCurrentTabName(currentTabName);
		tabbedpane.add(currentTabName, scrollPane);
		tabbedpane.setSelectedIndex(tabbedpane.getTabCount() - 1);
		tabbedpane.setTabComponentAt(tabbedpane.indexOfComponent(scrollPane),
				getTitlePanel(tabbedpane, scrollPane, currentTabName));
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

	private JPanel getTitlePanel(final JTabbedPane tabbedPane, final JScrollPane panel, String title) {
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		titlePanel.setOpaque(false);
		JLabel titleLbl = new JLabel(title);
		titleLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		titlePanel.add(titleLbl);
		JButton closeButton = new JButton("x");
		closeButton.setContentAreaFilled(false);
		closeButton.setBorderPainted(false);
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int userResponse = JOptionPane.showConfirmDialog(null,
						"Are you sure to close this tab? Any unsaved changes will be lost!");
				if (userResponse == 0) {
					tabbedPane.remove(panel);
				}
			}
		});
		titlePanel.add(closeButton);

		return titlePanel;
	}

	public static void main(String[] args) {
		new UserInterface();
	}

}
