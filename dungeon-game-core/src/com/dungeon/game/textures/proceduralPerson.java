package com.dungeon.game.textures;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class proceduralPerson extends proceduralTexture {

	public proceduralPerson() {
		super(32, 32);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generateTexture() {
		Pixmap texMap = new Pixmap(32,32,Pixmap.Format.RGBA8888); //create pixmap
		//vars
		//skin vars
		Color skinColor = new Color((float) (160+Math.random()*80)/255f,(float) (55+Math.random()*70)/255f,(float) (10+Math.random()*40)/255f, 1);
		//hair vars
		Color hairColor = new Color(0.9f, 0.6f, 0.3f, 1);
		int hairStyle = (int) (Math.random()*1);
		//generation
		texMap.setColor(skinColor);
		texMap.fillCircle(16, 16, 11);
		texMap.setColor(hairColor);
		switch(hairStyle){
			case 0:
				//normal hair
				int x = 16;
				int y = 16;
				int numStrands = 500;
				for(int i = 0; i < numStrands; i++){
					Color tempColor = new Color((float) (0.2-Math.random()*0.4), (float) (0.2-Math.random()*0.4), (float) (0.2-Math.random()*0.4), 0);
					float angle = (float) (2f*Math.PI*i/numStrands);
					int length = 12;
					if(angle < Math.PI/3 || angle > 5*Math.PI/3)length = 9;
					hairColor.add(tempColor);
					texMap.setColor(hairColor);
					texMap.drawLine(16, 16, 16+(int)(Math.cos(angle)*length), 16+(int)(Math.sin(angle)*length));
					hairColor.sub(tempColor);
				}
			break;
		}
		texture = new Texture(texMap); //create texture

	}

}
