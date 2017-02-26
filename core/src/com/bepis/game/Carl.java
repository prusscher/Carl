package com.bepis.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Carl extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	BitmapFont font;
	ShapeRenderer shapes;
	
	Floor test;
	ArrayList<Room> rooms;
	int level;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		shapes = new ShapeRenderer();
		font = new BitmapFont();
		
		level = 0;
		test = new Floor();
		rooms = test.generateFloor(level);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		shapes.begin(ShapeType.Filled);
		shapes.setColor(1, 1, 0, 1);

		for(Room r : rooms) {
			shapes.rect(15 + 15*r.getX() + Gdx.graphics.getWidth()/2, 15 + 15*r.getY() + Gdx.graphics.getHeight()/2, 10, 10);
		}
			
		shapes.end();
		
		batch.begin();
		font.draw(batch, "Level: " + level, 15, 15);
		batch.end();
		
		// Generate new map on keypress
		if(Gdx.input.isKeyJustPressed(Keys.SPACE) && Gdx.input.isKeyPressed(Keys.SPACE)) {
			if(level > 10)
				level = 0;
			level++;
			
			rooms = test.generateFloor(level);
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
