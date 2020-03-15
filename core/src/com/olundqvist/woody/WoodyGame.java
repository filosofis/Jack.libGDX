package com.olundqvist.woody;

import com.badlogic.gdx.Game;

public class WoodyGame extends Game {
	@Override
	public void create () {
		setScreen(new GameplayScreen());
	}

}
