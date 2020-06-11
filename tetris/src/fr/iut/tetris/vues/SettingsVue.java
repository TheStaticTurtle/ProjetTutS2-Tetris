package fr.iut.tetris.vues;


import fr.iut.tetris.Config;
import fr.iut.tetris.controllers.SettingsController;
import fr.iut.tetris.enums.Resolution;
import fr.iut.tetris.models.SettingsModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SettingsVue extends JPanel{

	SettingsModel model;
	SettingsController ctrl;

	public JSlider soundMusicLevel;
	public JSlider soundSFXMusicLevel;
	public JComboBox<Resolution> resolutionDropdown;

	public SettingsVue(SettingsModel model, SettingsController ctrl) {
		this.model = model;
		this.ctrl = ctrl;

		Color bg = Color.BLACK;

		JPanel mainPanel = new JPanel();
		GridLayout mainLayout = new GridLayout(13,1);
		GridLayout subLayout = new GridLayout(1,2);

		int wh = Config.getInstance().getInt("WINDOW_HEIGHT");
		int ww = Config.getInstance().getInt("WINDOW_WIDTH");
		setPreferredSize(new Dimension( ww, wh ));
		setBackground(bg);
		mainPanel.setPreferredSize(new Dimension((int) (ww*0.7), (int) (wh*0.85)));

		mainPanel.setLayout(mainLayout);
		mainPanel.setOpaque(false);
		mainLayout.setVgap(5);


		JPanel myLabel = new TetrisLogo(this, (int) (ww*0.7));

		JLabel soundSettingsLabel = new JLabel("Sound");
		JLabel soundMusicLabel = new JLabel("<html>Music: ");
		soundMusicLevel = new CustomSlider(-50,5,Color.WHITE,Color.ORANGE);
		JLabel soundSFXMusicLabel = new JLabel("<html>SFX: ");
		soundSFXMusicLevel = new CustomSlider(-50,5,Color.WHITE,Color.GREEN);
		JLabel resolutionLabel = new JLabel("<html>Size: ");
		resolutionDropdown = new JComboBox<Resolution>();

		JButton gotoKeysButton = new MenuButton("Controls",Color.orange,Color.WHITE,ctrl);
		JButton backButton = new MenuButton("Save",Color.GREEN,Color.WHITE,ctrl);

		myLabel.setFont(Config.getInstance().getFont("FONT_ULTRABIG"));
		soundSettingsLabel.setFont(Config.getInstance().getFont("FONT_BIG"));
		soundSettingsLabel.setForeground(Color.white);

		soundMusicLabel.setFont(Config.getInstance().getFont("FONT_NORMAL"));
		soundMusicLabel.setForeground(Color.white);
		soundSFXMusicLabel.setFont(Config.getInstance().getFont("FONT_NORMAL"));
		soundSFXMusicLabel.setForeground(Color.white);
		resolutionLabel.setFont(Config.getInstance().getFont("FONT_NORMAL"));
		resolutionLabel.setForeground(Color.white);

		gotoKeysButton.setFont(Config.getInstance().getFont("FONT_NORMAL"));
		backButton.setFont(Config.getInstance().getFont("FONT_NORMAL"));

		for (Resolution r : Resolution.values()) {
			resolutionDropdown.addItem(r);
		}
		Resolution r = Resolution.getFromSize(new Dimension(Config.getInstance().getInt("WINDOW_WIDTH"),Config.getInstance().getInt("WINDOW_HEIGHT")));
		if(r != null) {
			resolutionDropdown.setSelectedItem(r);
		}
		resolutionDropdown.setActionCommand("RESOLUTION_SELECT");
		resolutionDropdown.addActionListener(ctrl);
		resolutionDropdown.setRenderer(new CustomComboBoxRenderer());
		resolutionDropdown.setEditor(new CustomComboBoxEditor());
		resolutionDropdown.setFont(Config.getInstance().getFont("FONT_VERYTINY"));
		resolutionDropdown.setEditable(true);

		gotoKeysButton.addActionListener(ctrl);
		gotoKeysButton.setActionCommand("CLICK:MENU:SETTINGS:KEYS");

		backButton.addActionListener(ctrl);
		backButton.setActionCommand("CLICK:BACK");

		soundMusicLevel.addChangeListener(ctrl);
		soundSFXMusicLevel.addChangeListener(ctrl);

		mainPanel.add(myLabel);
		mainPanel.add( new Spacer());
		mainPanel.add( new Spacer());
		mainPanel.add(soundSettingsLabel);

		JPanel musicVolumeCtrlPanel = new JPanel();
		musicVolumeCtrlPanel.setOpaque(false);
		musicVolumeCtrlPanel.setLayout(subLayout);
		musicVolumeCtrlPanel.add(soundMusicLabel);
		musicVolumeCtrlPanel.add(soundMusicLevel);
		mainPanel.add(musicVolumeCtrlPanel);

		JPanel SFXVolumeCtrlPanel = new JPanel();
		SFXVolumeCtrlPanel.setOpaque(false);
		SFXVolumeCtrlPanel.setLayout(subLayout);
		SFXVolumeCtrlPanel.add(soundSFXMusicLabel);
		SFXVolumeCtrlPanel.add(soundSFXMusicLevel);
		mainPanel.add(SFXVolumeCtrlPanel);

		JPanel resolutionCtrlPannel = new JPanel();
		resolutionCtrlPannel.setOpaque(false);
		resolutionCtrlPannel.setLayout(subLayout);
		resolutionCtrlPannel.add(resolutionLabel);
		resolutionCtrlPannel.add(resolutionDropdown);
		mainPanel.add(resolutionCtrlPannel);

		mainPanel.add(gotoKeysButton);
		mainPanel.add( new Spacer());
		mainPanel.add( new Spacer());
		mainPanel.add( new Spacer());
		mainPanel.add( new Spacer());
		mainPanel.add(backButton);

		mainPanel.setLocation(0, 0);
		mainPanel.setPreferredSize(mainPanel.getPreferredSize());

		mainPanel.setBounds(0, 0, (int)mainPanel.getPreferredSize().getWidth(), (int)mainPanel.getPreferredSize().getHeight());
		mainPanel.setVisible(true);

		JLayeredPane testPane = new JLayeredPane();
		testPane.add(new MovingStarsAnimation(getPreferredSize()),JLayeredPane.DEFAULT_LAYER);
		testPane.add(mainPanel,JLayeredPane.PALETTE_LAYER);
		testPane.setPreferredSize(getPreferredSize());

		SpringLayout lyt = new SpringLayout();
		SpringLayout lyt2 = new SpringLayout();
		lyt.putConstraint(SpringLayout.HORIZONTAL_CENTER, mainPanel, 0, SpringLayout.HORIZONTAL_CENTER, testPane);
		lyt.putConstraint(SpringLayout.VERTICAL_CENTER, mainPanel, 0, SpringLayout.VERTICAL_CENTER, testPane);
		lyt2.putConstraint(SpringLayout.HORIZONTAL_CENTER, mainPanel, 0, SpringLayout.HORIZONTAL_CENTER, this);
		lyt2.putConstraint(SpringLayout.VERTICAL_CENTER, mainPanel, 0, SpringLayout.VERTICAL_CENTER, this);
		testPane.setLayout(lyt);
		setLayout(lyt2);

		add(testPane);

		JPanel t = this;
		new Timer(1, new ActionListener() { public void actionPerformed(ActionEvent e) {
			t.repaint();
			t.revalidate();
		}}).start();
	}

}
