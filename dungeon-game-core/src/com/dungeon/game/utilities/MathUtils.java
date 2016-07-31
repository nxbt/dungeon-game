package com.dungeon.game.utilities;

import java.util.Random;

public class MathUtils {

	
	public static Random getRandomFromSeedAndCords(int seed, int x, int y){ //this is not very efficent...
		Random xRandomer = new Random(x);
		Random yRandomer = new Random(y);
		xRandomer.nextFloat();
		yRandomer.nextFloat();
		yRandomer.nextFloat();
		Random fin = new Random((long) (1f+((float)seed)*xRandomer.nextFloat()*yRandomer.nextFloat()));
		fin.nextFloat();
		return fin;
	}
	
	public static  Random getRandomFromSeedAndX(int seed, int x){
		Random xRandomer = new Random(x);
		xRandomer.nextFloat();
		Random fin = new Random((long) (1f+((float)seed)*xRandomer.nextFloat()));
		fin.nextFloat();
		return fin;
	}
	
	public static  Random getRandomFromSeedAndY(int seed, int y){
		Random yRandomer = new Random(y);
		yRandomer.nextFloat();
		yRandomer.nextFloat();
		Random fin = new Random((long) (1f+((float)seed)*yRandomer.nextFloat()));
		fin.nextFloat();
		return fin;
	}

	public static float noise(int seed, int x, int y, float amp){
		Random rand = new Random(seed);
		float bl = getRandomFromSeedAndCords(seed, (int)(x/amp), (int)(y/amp)).nextFloat();
		float tl = getRandomFromSeedAndCords(seed, (int)(x/amp), (int)(y/amp)+1).nextFloat();
		float br = getRandomFromSeedAndCords(seed, (int)(x/amp)+1, (int)(y/amp)).nextFloat();
		float tr = getRandomFromSeedAndCords(seed, (int)(x/amp)+1, (int)(y/amp)+1).nextFloat();
		
		float setX,setY;
        setX = x-((int)(x/amp)*amp);
        setY = y-((int)(y/amp)*amp);
        
        float lv, rv, bv, tv, la, ra, ba, ta, lxOff, rxOff, bxOff, txOff, lyOff, ryOff, byOff, tyOff;
        
        la = (bl-tl)/2;
        ra = (br-tr)/2;
        ba = (bl-br)/2;
        ta = (tl-tr)/2;
        
        lxOff = amp/2;
        rxOff = amp/2;
        bxOff = amp/2;
        txOff = amp/2;
        
        lyOff = (bl+tl)/2;
        ryOff = (br+tr)/2;
        byOff = (bl+br)/2;
        tyOff = (tl+tr)/2;
        
        lv = (float) (la*Math.sin((Math.PI/amp)*(setY+lxOff))+lyOff);
        rv = (float) (ra*Math.sin((Math.PI/amp)*(setY+rxOff))+ryOff);
        bv = (float) (ba*Math.sin((Math.PI/amp)*(setX+bxOff))+byOff);
        tv = (float) (ta*Math.sin((Math.PI/amp)*(setX+txOff))+tyOff);
        
        float lDist, rDist, bDist, tDist;
        lDist = setX;
        rDist = amp-lDist;
        bDist = setY;
        tDist = amp-bDist;
        
        float value = 0;
        value += lv*(amp-lDist);
        value += rv*(amp-rDist);
        value += bv*(amp-bDist);
        value += tv*(amp-tDist);
        return value/(amp*2);
		
	}
	
	public static float perturbedSinNoise(int seed, int x, int y, float period /* period of the side function*/, float perturbPeriod /*higher means smoother perturb*/, float perturbAmp/*higher means more perturb*/){
		return (float) (1f+Math.sin(((float)x*Math.PI*2f+noise(seed,x,y,perturbPeriod)*perturbAmp)/period))/2f;
	}
}
