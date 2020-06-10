package fr.iut.tetris.controllers;

import fr.iut.tetris.Config;
import fr.iut.tetris.MainController;
import fr.iut.tetris.models.SettingsModel;
import fr.iut.tetris.vues.SettingsVue;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsController implements ActionListener, ChangeListener {
	MainController mainCtrl;
	SettingsModel model;
	SettingsVue vue;
	AudioController audio;

	public SettingsController(MainController mainCtrl, SettingsModel model, SettingsVue vue, AudioController audio) {
		this.model = model;
		this.mainCtrl = mainCtrl;
		this.vue = vue;
		this.audio = audio;
	}

	public void setVue(SettingsVue vue) {
		this.vue = vue;
	}

	/**
	 * set the vue audio level when loaded
	 */
	public void enteredVue() {
		vue.soundMusicLevel.setValue( (int)audio.musicLineVolumeControl );
		vue.soundSFXMusicLevel.setValue( (int)audio.soundEffetLineVolumeControl );
	}

	/**
	 * Save the config file when the button is pressed
	 */
	void saveConfig() {
		Config.getInstance().putInt("VOLUME_SFX",(int)audio.soundEffetLineVolumeControl);
		Config.getInstance().putInt("VOLUME_MUSIC",(int)audio.musicLineVolumeControl);
		Config.getInstance().saveAsync();
	}

	/**
	 * Listen for incoming event and do some action accordingly
	 * @param e the event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand() ) {
			case "MOUSE:ENTER":
				this.audio.playSFX(getClass().getResource( "/res/sounds/menu_choose.wav"));
				break;
			case "CLICK:BACK":
				this.audio.playSFX(getClass().getResource( "/res/sounds/menu_select.wav"));
				saveConfig();
				mainCtrl.actionPerformed(e);
				break;
			default:
				break;
		}
	}

	/**
	 * Update our level when the sliders have moved
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		audio.musicLineVolumeControl = (float)vue.soundMusicLevel.getValue();
		audio.soundEffetLineVolumeControl = (float)vue.soundSFXMusicLevel.getValue();
	}
}
