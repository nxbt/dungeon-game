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
	public void generateTexture() { //NEED TO ADD NOISE TO EVERYTHING!!!
		
		Pixmap texMap = new Pixmap(32,32,Pixmap.Format.RGBA8888); //create pixmap
		
		//vars
		
		//skin vars
		Color skinColor = new Color((float) (160+Math.random()*80)/255f,(float) (55+Math.random()*70)/255f,(float) (10+Math.random()*40)/255f, 1);
		//hair vars
		Color hairColor = Math.random() > 0.66?Color.RED:Math.random() > 0.5?Color.BROWN:Color.YELLOW;
		int hairStyle = (int) (Math.random()*1);
		//eye vars
		Color eyeColor = Math.random() > 0.66?Color.BLUE:Math.random() > 0.5?Color.BROWN:Color.FOREST;
		float eyeAngle = (float) (Math.PI/7f);
		//arm vars
		int sleveType = (int) (Math.random()*3); // 0 = full sleves, 1 = t sleves, 2 = no sleves
		Color sleveColor = Math.random() > 0.5?Color.PURPLE:Color.CYAN;
		int shoulderSize = 2+(int)(Math.random()*1);
		
		//generation
		
		//skin generation
		texMap.setColor(skinColor);
		texMap.fillCircle(16, 16, 11);
		texMap.setColor(hairColor);
		//hair generation
		switch(hairStyle){
			case 0:
				//normal hair
				int x = 16;
				int y = 16;
				int numStrands = 250;
				for(int i = 0; i < numStrands; i++){
					float angle = (float) (2f*Math.PI*i/numStrands);
					int length = 11 + (int)(Math.random()*2);
					if(angle < Math.PI/3 || angle > 5*Math.PI/3)length = 8 + (int)(Math.random()*3);
					texMap.setColor(new Color((float) (hairColor.r * (0.8f + Math.random()*0.2f)), (float) (hairColor.g * (0.8f + Math.random()*0.2f)), (float) (hairColor.b * (0.8f + Math.random()*0.2f)), 1));
					texMap.drawLine(16, 16, 16+(int)(Math.cos(angle)*length), 16+(int)(Math.sin(angle)*length));
				}
			break;
		}
		//eye generation
		texMap.setColor(new Color(0.9f+eyeColor.r*0.1f,0.9f+eyeColor.g*0.1f,0.9f+eyeColor.b*0.1f,1));
		texMap.drawPixel(15 + (int)Math.round(Math.cos(eyeAngle) * 11), 15 + (int)Math.round(Math.sin(eyeAngle) * 11));
		texMap.drawPixel(15 + (int)Math.round(Math.cos(-eyeAngle) * 11), 15 + (int)Math.round(Math.sin(-eyeAngle) * 11));
		
		texMap.setColor(new Color(0.7f+eyeColor.r*0.3f,0.7f+eyeColor.g*0.3f,0.7f+eyeColor.b*0.3f,1));
		texMap.drawPixel(16 + (int)Math.round(Math.cos(eyeAngle) * 11), 15 + (int)Math.round(Math.sin(eyeAngle) * 11));
		texMap.drawPixel(16 + (int)Math.round(Math.cos(-eyeAngle) * 11), 15 + (int)Math.round(Math.sin(-eyeAngle) * 11));
		texMap.drawPixel(15 + (int)Math.round(Math.cos(eyeAngle) * 11), 16 + (int)Math.round(Math.sin(eyeAngle) * 11));
		texMap.drawPixel(15 + (int)Math.round(Math.cos(-eyeAngle) * 11), 16 + (int)Math.round(Math.sin(-eyeAngle) * 11));
		
		texMap.setColor(eyeColor);
		texMap.drawPixel(16 + (int)Math.round(Math.cos(eyeAngle) * 11), 16 + (int)Math.round(Math.sin(eyeAngle) * 11));
		texMap.drawPixel(16 + (int)Math.round(Math.cos(-eyeAngle) * 11), 16 + (int)Math.round(Math.sin(-eyeAngle) * 11));
		
		//arm generation
		
		//hands
		texMap.setColor(new Color(skinColor.r*0.8f, skinColor.g*0.8f, skinColor.b*0.8f, 1));
		texMap.fillCircle(22 + Math.round(shoulderSize/2f), 29, 2);
		texMap.fillCircle(22 + Math.round(shoulderSize/2f), 3, 2);
		
		//lower arm
		texMap.setColor(new Color((sleveType == 0?sleveColor:skinColor).r*0.9f, (sleveType == 0?sleveColor:skinColor).g*0.9f, (sleveType == 0?sleveColor:skinColor).b*0.9f, 1));
		texMap.fillRectangle(16 + Math.round(shoulderSize/2f), 28, 5, 4);
		texMap.fillRectangle(16 + Math.round(shoulderSize/2f), 1, 5, 4);
		
		//upper arm
		texMap.setColor(sleveType == 0 || sleveType == 1?sleveColor:skinColor);
		texMap.fillCircle(Math.round(16), 28 + Math.round(shoulderSize/2f), shoulderSize);
		texMap.fillCircle(Math.round(16), 4 - Math.round(shoulderSize/2f), shoulderSize);
//		texMap.fillRectangle(Math.round(16 - shoulderSize/2f), 28, shoulderSize, 4);
//		texMap.fillRectangle(Math.round(16 - shoulderSize/2f), 1, shoulderSize, 4);
		
		
		texture = new Texture(texMap); //create texture
	}

}
