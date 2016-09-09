# dungeon-game
The is a top-down rougelike-like game written in Java. Currently being worked on by Brendan Thompson and Ethan  Bull-Vulpe.

##TODO high priority (within a week or two):
* swap x and y in arrays
* make doors open away from the player
* add gold keys and doors
* relplace the booleans for flip with functions (so hitbox and visbox can be flipped)
* make flip work in box2d
* stationary characters path to where they're standing
* player can't be pushed (kinematic body does not work)
* rework trapdoors
* reword path smoothing to go around entities
* pathfinding around entities and coordinated group movement / pathfinding (fix "traffic jams")
* intelligent movement to prevent overshooting sharp turns or a destination
* claw swings draw out claw marks

##TODO low priority (within a month or two?):
* continue adding procedural room decoration
* continue proceedurally generated textures
* make wood texture rotate to make more sense in small hallways
* add different shops to towns
* update wiki pages
* implement caves generation
* implement catacombs generation
* implement mixed (/recursive?) generation

##TODO very low priority (before final release):
* add multithreding!
* find artist (? still need ?)
* add "sounds" that characters can "hear"
* add a story
* add more friendlies
* add more enemies
* dungeons become more complex decending
* final dungeon is culmination of all previous dungeons
* only access to 1 dungeon at a time
* hidden player "level" controlls dungeon difficulty and loot

##BUG TRACKER
* floating torches in castle rooms and where multiple halls meet (and other places)
* fireplaces turn on when they should not
