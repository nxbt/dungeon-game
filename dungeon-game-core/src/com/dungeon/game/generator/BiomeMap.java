package com.dungeon.game.generator;

import java.util.*;

public class BiomeMap {
    private float[][][] biomeMap; // [x][y][types]
    private ArrayList<float[]> biomeCenters; //holds locations of centers of all biomes
    
    private static final int BIOME_SPACING = 5; //min space between biome centers
    
    private static final int BIOME_NUM = 8; //number of biomes
    
    //constants for reference to biome types
    private static final int ROOMS = 0;
    private static final int CASTLE = 1;
    private static final int TEMPLE = 2;
    private static final int LABYRINTH = 3;
    private static final int RUINS = 4;
    private static final int CELL_BLOCK = 5;
    private static final int GRID = 6;
    private static final int CAVES = 7;
    
    class BiomeMap(int width, int height){
    
    }
}
