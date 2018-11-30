package view;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import algorithmeOutil.Simulation;
import controleur.ControlButton;
import controleur.ControlFiltreComboBox;
import controleur.ControlMapComboBox;

@SuppressWarnings("serial")
public class Views extends JFrame {
	
	Simulation sim;
	IHMSwing ihmSWING;
	
	JButton start = new JButton("Start");
	JButton reset = new JButton("Reset");
	JButton resume = new JButton("Resume");
	JButton pause= new JButton("Pause");
	JButton filtrer = new JButton("Filtrer");
	JButton saveCommande= new JButton("Save Commande");
	JButton saveImage= new JButton("Save Image");
	JButton quit = new JButton("Quit");
	
	public Views(String titre, int largeur, int hauteur){
		this.setTitle(titre);
		this.setSize(largeur, hauteur);
	}
	
	public Views(String titre, Simulation sim, IHMSwing ihmSWING){
		this.setTitle(titre);
		this.ihmSWING = ihmSWING;
		this.sim = sim;
	}
	public Views(String titre){
		this.setTitle(titre);
	}
	
	public void buld(IHMSwing ihmSWING, ControlButton controlButton, ControlMapComboBox controlMapComboBox, ControlFiltreComboBox controlFiltreComboBox, JLabel nbCoup){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contenaireJFrame = this.getContentPane();
		contenaireJFrame.setLayout(new BorderLayout());		

		JPanel controleButtonPanel = new JPanel(new BorderLayout());
		Box buttonNorthBox = Box.createVerticalBox();
		Box comboBoxCenterBox = Box.createVerticalBox();
		Box buttonSouthBox = Box.createVerticalBox();
		
		controlButton.setButton(start, reset, resume, pause, filtrer,saveCommande, saveImage, quit);
		start.addActionListener(controlButton);
		reset.addActionListener(controlButton);
		resume.addActionListener(controlButton);
		pause.addActionListener(controlButton);
		filtrer.addActionListener(controlButton);
		saveCommande.addActionListener(controlButton);
		saveImage.addActionListener(controlButton);
		quit.addActionListener(controlButton);
				
		reset.setEnabled(false);
		resume.setEnabled(false);
		pause.setEnabled(false);

		buttonNorthBox.add(start);
		buttonNorthBox.add(reset);
		buttonNorthBox.add(resume);
		buttonNorthBox.add(pause);
		buttonNorthBox.add(nbCoup);
		
		buttonSouthBox.add(filtrer);
		buttonSouthBox.add(saveImage);
		buttonSouthBox.add(saveCommande);
		buttonSouthBox.add(quit);
				
		JComboBox mapComboBox = new JComboBox();
		controlMapComboBox.setButton(start, reset, resume, pause, saveCommande, quit);
		mapComboBox.addActionListener(controlMapComboBox);
		mapComboBox.addItem("1_safe");
		mapComboBox.addItem("2_safe");
		mapComboBox.addItem("3_safe");
		mapComboBox.addItem("4_safe");
		mapComboBox.addItem("5_safe");
		mapComboBox.addItem("6_safe");
		mapComboBox.addItem("7_safe");
		mapComboBox.addItem("8_safe");
		mapComboBox.addItem("aufeu");
		mapComboBox.addItem("bond_safe");
		mapComboBox.addItem("Een2");
		mapComboBox.addItem("labymod");
		mapComboBox.addItem("labyperso");
		mapComboBox.addItem("perso");
		mapComboBox.addItem("t260_safe");
		mapComboBox.addItem("t2009");
		
		JComboBox filtreComboBox = new JComboBox();
		filtreComboBox.addItem("filtre diago");
		filtreComboBox.addItem("filtre licer");
		controlFiltreComboBox.setFiltreComboBox(filtreComboBox);
		controlButton.setControlComboBoxFiltre(controlFiltreComboBox);
		
		comboBoxCenterBox.add(mapComboBox);
		comboBoxCenterBox.add(filtreComboBox);
		
		controleButtonPanel.add(buttonNorthBox, BorderLayout.NORTH);
		controleButtonPanel.add(buttonSouthBox, BorderLayout.SOUTH);
		controleButtonPanel.add(comboBoxCenterBox, BorderLayout.CENTER);
		
		contenaireJFrame.add(controleButtonPanel,BorderLayout.EAST);
		contenaireJFrame.add(ihmSWING,BorderLayout.CENTER);
//		contenaireJFrame.add(new JScrollPane((JPanel)ihmSWING),BorderLayout.CENTER);

		this.setVisible(true);
		this.pack();
	}
}
