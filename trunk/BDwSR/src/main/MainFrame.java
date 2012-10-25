package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public final class MainFrame extends JFrame {

	private static final long		serialVersionUID		= 958317504694425777L;
	public static final int			DEFAULT_WIDTH			= 1200;
	public static final int			DEFAULT_HEIGHT			= 700;
	public static final int			DELAY_TIME_MULTIPLIER	= 16; // Tak obliczyliśmy podczas używania
	public static final String		DEFAULT_TITLE			= "BDwSR - projekt (Łukasz Krok, Tobiasz Siemiński)";
	public static final Border		DEFAULT_BORDER			= BorderFactory.createEtchedBorder(1);
	public static final String		LOG_FILE_NAME	 		= "BDWSR_log-";

	private final TextArea			textArea				= new TextArea(8, 40);

	private final static MainFrame	instance				= new MainFrame();
	private Controller controller;
	
	public static MainFrame getInstance() {
		return instance;
	}

	private MainFrame() {
	    controller = Controller.getInstance();
	    
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
		leftUpperPanel.setLayout(new GridLayout(3, 1));
		leftUpperPanel.add(new JLabel("Wybór koordynatora algorytmem tyrana."));
		leftUpperPanel.add(new JLabel("Autorzy:"));
		leftUpperPanel.add(new JLabel("Łukasz Krok, Tobiasz Siemiński."));
		leftUpperPanel.setBorder(DEFAULT_BORDER);

		JPanel leftCenterPanel = new JPanel();
		leftCenterPanel.setLayout(new GridLayout(8, 2));
		final JTextField threadsNumber = new JTextField("16");
	    final JTextField priorityMax = new JTextField("100");

		leftCenterPanel.add(new JLabel("Podaj liczbę wątków:"));
		leftCenterPanel.add(threadsNumber);
		leftCenterPanel.add(new JLabel("Podaj max. priorytet wątku:"));
		leftCenterPanel.add(priorityMax);

		leftCenterPanel.add(new JLabel("Koordynator (nr wątku):"));
		final JTextField coordinatorNumber = new JTextField("1");
		leftCenterPanel.add(coordinatorNumber);
		
//		leftCenterPanel.add(new JLabel("Czas zwłoki w ms:"));
//		final JTextField delayTime = new JTextField("3000");
//		leftCenterPanel.add(delayTime);
		
		leftCenterPanel.setBorder(DEFAULT_BORDER);

		
		JPanel leftSouthPanel = new JPanel();
		leftSouthPanel.setLayout(new GridLayout(4, 1));

		// ///////////////// START BUTTON /////////////
		JButton startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			    textArea.setText("");
				textArea.append("Startujemy!\n");
				StringBuilder builder = new StringBuilder();
				builder.append("Liczba wątków: "+threadsNumber.getText()+". Max priorytet: "+priorityMax.getText());
				
				int numberOfThreads = Integer.valueOf(threadsNumber.getText());
				int maxPriority = Integer.valueOf(priorityMax.getText());
				
				controller.startThreads(numberOfThreads, maxPriority, Integer.valueOf(coordinatorNumber.getText()), Integer.valueOf(threadsNumber.getText())*MainFrame.DELAY_TIME_MULTIPLIER);
			}
		});
		leftSouthPanel.add(startButton);

		// ///////////////// ELECTION BUTTON /////////////
		JButton startAlgorithmButton = new JButton("Rozpocznij algorytm elekcji");
		startAlgorithmButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.append("=============================================================\nRozpoczynam proces elekcji algorytmem tyrana!\n");
				controller.coordinatorIsDead();
			}
		});
		leftSouthPanel.add(startAlgorithmButton);

		// ///////////////// STOP BUTTON /////////////
		JButton stopButton = new JButton("STOP");
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.append("STOP!\n");
				controller.stopThreads();
			}
		});
		leftSouthPanel.add(stopButton);

		leftSouthPanel.setBorder(DEFAULT_BORDER);

		leftPanel.setBorder(DEFAULT_BORDER);
		leftPanel.add(leftUpperPanel, BorderLayout.NORTH);
		leftPanel.add(leftCenterPanel, BorderLayout.CENTER);
		leftPanel.add(leftSouthPanel, BorderLayout.SOUTH);
		
		// ///////////////// SAVE BUTTON /////////////
		JButton btnZapiszDoPliku = new JButton("Zapisz do pliku");
		leftSouthPanel.add(btnZapiszDoPliku);
		btnZapiszDoPliku.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PrintWriter out;
				try {
					out = new PrintWriter(new FileWriter(
							MainFrame.LOG_FILE_NAME
									+ Calendar.getInstance().getTime()
											.toString().replace(" ", "_")
											.replace(":", "-") + ".txt"));
					out.print(textArea.getText());
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				  System.out.printf("Testing String");
			}
		});

		getContentPane().add(leftPanel, BorderLayout.WEST);

		// ///////////////////// PRAWA STRONA /////////////////
		JPanel rightPanel = new JPanel();

		rightPanel.setLayout(new GridLayout(1, 1));
		rightPanel.add(textArea, BorderLayout.SOUTH);
		rightPanel.setBorder(DEFAULT_BORDER);

		getContentPane().add(rightPanel, BorderLayout.CENTER);
	}

	public void init() {
		setVisible(true);
	}

	public TextArea getTextArea() {
		return textArea;
	}

}
