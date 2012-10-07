package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public final class MainFrame extends JFrame {

	private static final long		serialVersionUID	= 958317504694425777L;
	public static final int			DEFAULT_WIDTH		= 1200;
	public static final int			DEFAULT_HEIGHT		= 700;
	public static final String		DEFAULT_TITLE		= "BDwSR - projekt (Łukasz Krok, Tobiasz Siemiński)";
	public static final Border		DEFAULT_BORDER		= BorderFactory.createEtchedBorder(1);

	private final TextArea			textArea			= new TextArea(8, 40);

	private final static MainFrame	instance			= new MainFrame();

	public static MainFrame getInstance() {
		return instance;
	}

	private MainFrame() {
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setTitle(DEFAULT_TITLE);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);

		// ///////////////// TEXT AREA /////////////////////
		textArea.setEnabled(false);
		// /////////////////////////////////////////////////

		// ///////////////// LEWA STRONA ///////////////////
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(3, 1));
		JPanel leftUpperPanel = new JPanel();
		leftUpperPanel.setLayout(new GridLayout(5, 1));
		leftUpperPanel.add(new JLabel("Wybór koordynatora algorytmem tyrana."));
		leftUpperPanel.add(new JLabel("Autorzy:"));
		leftUpperPanel.add(new JLabel("Łukasz Krok, Tobiasz Siemiński."));
		leftUpperPanel.add(new JLabel("Proszę wypełnić priorytety wątków"));
		leftUpperPanel.add(new JLabel("dowolnymi liczbami całkowitymi."));
		leftUpperPanel.setBorder(DEFAULT_BORDER);

		JPanel leftCenterPanel = new JPanel();
		leftCenterPanel.setLayout(new GridLayout(9, 2));
		final JTextField watek0TF = new JTextField();
		final JTextField watek1TF = new JTextField();
		final JTextField watek2TF = new JTextField();
		final JTextField watek3TF = new JTextField();
		final JTextField watek4TF = new JTextField();
		final JTextField watek5TF = new JTextField();
		final JTextField watek6TF = new JTextField();
		final JTextField watek7TF = new JTextField();

		leftCenterPanel.add(new JLabel("Podaj priorytety wątków:"));
		leftCenterPanel.add(new JLabel(""));
		leftCenterPanel.add(new JLabel("Wątek 0:"));
		leftCenterPanel.add(watek0TF);
		leftCenterPanel.add(new JLabel("Wątek 1:"));
		leftCenterPanel.add(watek1TF);
		leftCenterPanel.add(new JLabel("Wątek 2:"));
		leftCenterPanel.add(watek2TF);
		leftCenterPanel.add(new JLabel("Wątek 3:"));
		leftCenterPanel.add(watek3TF);
		leftCenterPanel.add(new JLabel("Wątek 4:"));
		leftCenterPanel.add(watek4TF);
		leftCenterPanel.add(new JLabel("Wątek 5:"));
		leftCenterPanel.add(watek5TF);
		leftCenterPanel.add(new JLabel("Wątek 6:"));
		leftCenterPanel.add(watek6TF);
		leftCenterPanel.add(new JLabel("Wątek 7:"));
		leftCenterPanel.add(watek7TF);
		leftCenterPanel.setBorder(DEFAULT_BORDER);

		JPanel leftSouthPanel = new JPanel();
		leftSouthPanel.setLayout(new GridLayout(4, 1));

		// ///////////////// START BUTTON /////////////
		JButton startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.append("Startujemy!\n");
				StringBuilder builder = new StringBuilder();
				builder.append("Priorytet wątku 0: ").append(watek0TF.getText()).append("\nPriorytet wątku 1: ").append(watek1TF.getText()).append("\nPriorytet wątku 2: ").append(watek2TF.getText()).append("\nPriorytet wątku 3: ").append(watek3TF.getText()).append("\nPriorytet wątku 4: ").append(watek4TF.getText()).append("\nPriorytet wątku 5: ").append(watek5TF.getText())
						.append("\nPriorytet wątku 6: ").append(watek6TF.getText()).append("\nPriorytet wątku 6: ").append(watek7TF.getText()).append("\n");
				textArea.append(builder.toString());
				
				int priority0 = Integer.valueOf(watek0TF.getText());
				int priority1 = Integer.valueOf(watek1TF.getText());
				int priority2 = Integer.valueOf(watek2TF.getText());
				int priority3 = Integer.valueOf(watek3TF.getText());
				int priority4 = Integer.valueOf(watek4TF.getText());
				int priority5 = Integer.valueOf(watek5TF.getText());
				int priority6 = Integer.valueOf(watek6TF.getText());
				int priority7 = Integer.valueOf(watek7TF.getText());
				
				
				Controller.startThreads(priority0, priority1, priority2, priority3, priority4, priority5, priority6, priority7);
			}
		});
		leftSouthPanel.add(startButton);

		// ///////////////// PAUSE BUTTON /////////////
		JButton startAlgorithmButton = new JButton("Rozpocznij algorytm tyrana");
		startAlgorithmButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.append("Rozpoczynam algorytm tyrana i losuję wątek, który zostanie zawieszony!\n");
			}
		});
		leftSouthPanel.add(startAlgorithmButton);

		// ///////////////// PAUSE BUTTON /////////////
		JButton pauseButton = new JButton("PAUZA");
		pauseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.append("Pauza!\n");
			}
		});
		leftSouthPanel.add(pauseButton);

		// ///////////////// STOP BUTTON /////////////
		JButton stopButton = new JButton("STOP");
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.append("STOP!\n");
				Controller.stopThreads();
			}
		});
		leftSouthPanel.add(stopButton);

		leftSouthPanel.setBorder(DEFAULT_BORDER);

		leftPanel.setBorder(DEFAULT_BORDER);
		leftPanel.add(leftUpperPanel, BorderLayout.NORTH);
		leftPanel.add(leftCenterPanel, BorderLayout.CENTER);
		leftPanel.add(leftSouthPanel, BorderLayout.SOUTH);

		add(leftPanel, BorderLayout.WEST);

		// ///////////////////// PRAWA STRONA /////////////////
		JPanel rightPanel = new JPanel();

		rightPanel.setLayout(new GridLayout(1, 1));
		rightPanel.add(textArea, BorderLayout.SOUTH);
		rightPanel.setBorder(DEFAULT_BORDER);

		add(rightPanel, BorderLayout.CENTER);
	}

	public void init() {
		setVisible(true);
	}

	public TextArea getTextArea() {
		return textArea;
	}

}
