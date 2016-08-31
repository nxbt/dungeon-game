# dungeon-game
The is a top-down rougelike-like game written in Java. Currently being worked on by Brendan Thompson and Ethan  Bull-Vulpe.

##TODO high priority (within a week or two):
* finish tutorial sequence
* set up playtesting sessions
* swap x and y in arrays
* make doors open away from the player

##TODO low priority (within a month or two?):
* continue adding procedural room decoration
* continue proceedurally generated textures
* make wood texture rotate to make more sense in small hallways
* add logical pathfinding around entities and logical group movement
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
* add pathing around entities
* add more enemies
* dungeons become more complex decending
* final dungeon is culmination of all previous dungeons
* only access to 1 dungeon at a time
* hidden player "level" controlls dungeon difficulty and loot

##BUG TRACKER
* dual wielding will occasionally cause your own weapons to collide, sending you flying.
* floating torches in castle rooms and where multiple halls meet (and other places)
* characters on the border of a wall will have a visPoly that crashes
* dialogue bubbles jump after empty dialogue skip (fixed?)
* fireplaces turn on at the wrong times
