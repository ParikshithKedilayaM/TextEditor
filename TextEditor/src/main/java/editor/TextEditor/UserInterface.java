package editor.TextEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class UserInterface extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane = null;
	private MenuBar menubar = null;
//	private KeyHandler keyHandler = null;
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
		menubar = new MenuBar(this);
//		keyHandler = new KeyHandler(menubar);
		tabbedPane = new JTabbedPane();
		setJMenuBar(menubar);
		add(tabbedPane);
		addTextArea();
		addNewTabButton();
		tabbedPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					addTextArea();
				}
			}

		});
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				try {
					Model.getInstance().setCurrentTabName(tabbedPane.getName());
					Model.getInstance().setTextArea(textAreaList.get(tabbedPane.getSelectedIndex()));
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
		textArea.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(
						Color.YELLOW);
				if (e.getClickCount() == 2) {
					String selectedText = Model.getInstance().getTextArea().getSelectedText();
					if (selectedText != null) {
						menubar.searchAll(selectedText, painter);
					}
				} else {
					Model.getInstance().getTextArea().getHighlighter().removeAllHighlights();
				}
			}
		});
//		textArea.addKeyListener(keyHandler);
		textAreaList.add(textArea);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(new TitledBorder(new EtchedBorder(), "Start writing your text here!"));

		Model.getInstance().setLatestTab(Math.max(Model.getInstance().getLatestTab() + 1, tabbedPane.getTabCount()));

		if (tabbedPane.getTabCount() == 0) {
			Model.getInstance().setLatestTab(1);
		}

		String currentTabName = "New " + (Model.getInstance().getLatestTab());

		Model.getInstance().setCurrentTabName(currentTabName);
		tabbedPane.add(currentTabName, scrollPane);
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
		tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(scrollPane),
				getTitlePanel(tabbedPane, scrollPane, currentTabName));
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

	@Override
	public void update(Observable o, Object arg) {
		if (arg == "setTabName") {
			String currentTabName = Model.getInstance().getCurrentTabName();
			Component selectedComponent = tabbedPane.getSelectedComponent();
			tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(selectedComponent),
					getTitlePanel(tabbedPane, (JScrollPane) selectedComponent, currentTabName));
		}
	}

}
