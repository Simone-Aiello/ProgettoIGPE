package application.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.Settings;

public class GameModel {

	private Player playerOne = null;
	private Player playerTwo = null;
	private List<Tile> tiles;
	private List<Enemy> enemies;
	private List<Bubble> bubbles;
	private List<Food> food;
	private boolean started;
	private int score = 0;
	public GameModel() {
		started = false;
	}

	public void startGame(boolean isSinglePlayer) {
		started = true;
		playerOne = new Player(Settings.INITIAL_POSITION_X, Settings.INITIAL_POSITION_Y);
		if (!isSinglePlayer)
			playerTwo = new Player(200, 200);
		tiles = new ArrayList<Tile>();
		enemies = new ArrayList<Enemy>();
		bubbles = new ArrayList<Bubble>();
		food = new ArrayList<Food>();
		tilesInitForTestPurposes();
	}

	// Getters
	public boolean isStarted() {
		return started;
	}

	public Player getPlayerOne() {
		return playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

	public List<Tile> getTiles() {
		return tiles;
	}

	public List<Bubble> getBubbles() {
		return bubbles;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}
	public List<Food> getFood() {
		return food;
	}
	public int getScore() {
		return score;
	}
	// Level handler
	private void tilesInitForTestPurposes() {
		try {
			BufferedReader in = new BufferedReader(
					new FileReader(getClass().getResource("/application/resources/level/Level0.csv").getFile()));
			int j = 0;
			while (in.ready()) {
				String s = in.readLine();
				String[] v = s.split(",");
				for (int i = 0; i < v.length; i++) {
					if (v[i].equals("0"))
						tiles.add(new Tile(i * Settings.TILE_WIDHT, j * Settings.TILE_HEIGHT, Settings.TILE_WIDHT,
								Settings.TILE_HEIGHT));
					else if (v[i].equals("2"))
						enemies.add(new RobotEnemy(i * Settings.TILE_WIDHT, j * Settings.TILE_HEIGHT,
								Settings.PLAYER_DIMENSION, Settings.PLAYER_DIMENSION));
				}
				j++;
			}
			in.close();
		} catch (IOException e) {
			System.out.println("Cannot load the level");
		}
	}

	// Funzioni di update chiamate dal controller
	public void update() {
		if (!started) return;
		//Update dei giocatori
		updateEntity(playerOne);
		if (playerTwo != null) updateEntity(playerTwo);
		//Update dei nemici
		for (Enemy e : enemies) {
			if(((Entity) e).isAlive) {
				e.nextMove();
				updateEntity((Entity) e);				
			}
		}
		//Update delle bolle
		for (int i = 0; i < bubbles.size(); i++) {
			Bubble b = bubbles.get(i);
			b.aliveFrame++;
			if (b.aliveFrame > Utilities.BUBBLE_TURN) b.affectedByGravity = true;
			if (b.aliveFrame >= Utilities.BUBBLE_LIFESPAN && b.enemyContained == null) {
				b.isAlive = false;
			}
			if(b.isAlive && b.enemyContained != null) {
				if(b.frameEnemyContained >= Utilities.BUBBLED_ENEMY_MAX_FRAME) {
					b.isAlive = false;
					Entity entity = (Entity) b.enemyContained;
					b.enemyContained = null;
					entity.x = b.x;
					entity.y = b.y;
					entity.getHitbox().x = b.getHitbox().x;
					entity.getHitbox().y = b.getHitbox().y;
					entity.isAlive = true;
				}
				else b.frameEnemyContained++;
			}
			updateEntity(b);
		}
		//Update del cibo
		for(Food f : food) {
			updateEntity(f);
		}
		// Collisioni nemici-bolle
		updateBubbleEnemyCollision();
		updatePlayerBubbleCollision(playerOne);
		updatePlayerFoodCollision(playerOne);
		if(playerTwo != null) {
			updatePlayerBubbleCollision(playerTwo);
			updatePlayerFoodCollision(playerTwo);
		}
	}
	private void updatePlayerFoodCollision(Player playerOne) {
		for(Food f : food) {
			//Collisione con il pavimento solo per motivi di gameplay, non necessario per il corretto funzionamento
			if(f.isAlive() && WallCollisionHandler.touchingGround(f, tiles) && f.getHitbox().intersects(playerOne.getHitbox())) {
				f.isAlive = false;
				score += f.getPoints();
			}
		}
	}

	private void updatePlayerBubbleCollision(Player player) {
		for(int i = 0; i < bubbles.size();i++) {
			Bubble b = bubbles.get(i);
			if(b.isAlive && b.enemyContained != null && b.getHitbox().intersects(player.getHitbox())) {
				b.isAlive = false;
				((Entity) b.enemyContained).isAlive = false;
				b.enemyContained = null;
				food.add(new Food(b.x, b.y, Utilities.CAKE));
			}
		}
	}
	private void updateBubbleEnemyCollision() {
		for (Enemy e : enemies) {
			Entity entity = (Entity) e;
			if(entity.isAlive) {
				for (int i = 0; i < bubbles.size(); i++) {
					Bubble b = bubbles.get(i);
					if (b.isAlive && entity.getHitbox().intersects(b.getHitbox()) && b.enemyContained == null) {
						entity.isAlive = false;
						b.enemyContained = e;
						break;
					}
				}				
			}
		}
	}

	// Chiamata dal controller ogni keypress o keyreleased
	public void movePlayer(Player player, int direction) {
		if (direction == Utilities.IDLE_LEFT) {
			if (player.xState == Utilities.MOVE_LEFT) {
				player.xState = direction;
			}
			return;
		} else if (direction == Utilities.IDLE_RIGHT) {
			if (player.xState == Utilities.MOVE_RIGHT)
				player.xState = direction;
			return;
		} else if (direction == Utilities.JUMPING) {
			player.requestJump();
		} else if (direction == Utilities.SHOOT) {
			player.requestBubble();
		} else
			player.xState = direction;
	}

	public void handleEntityJump(Entity entity) {
		if (!entity.jumping) {
			if (WallCollisionHandler.touchingGround(entity, tiles)
					&& !WallCollisionHandler.currentlyInsideTiles(entity, tiles)) {
				entity.jumping = true;
				entity.preJumpPos = entity.y;
			}
		}
	}

	private void handleNewBubble(Player player) {
		int spawnDirection = Utilities.MOVE_LEFT;
		if (player.xState == Utilities.MOVE_RIGHT || player.xState == Utilities.IDLE_RIGHT) {
			spawnDirection = Utilities.MOVE_RIGHT;
		}
		player.yState = Utilities.SHOOT;
		bubbles.add(new Bubble(player.x, player.y, spawnDirection));
	}

	private void tryToJump(Entity entity) {
		handleEntityJump(entity);
		entity.requestedJump = false;
	}

	private void tryToShoot(Player player) {
		handleNewBubble(player);
		player.requestedBubble = false;
	}

	private void processJump(Entity entity) {
		if (entity.y > 0 && entity.y + entity.yspeed > entity.preJumpPos - 2.3 * Settings.PLAYER_DIMENSION) {
			entity.jump();
			if(entity.yState != Utilities.SHOOT) entity.yState = Utilities.JUMPING;
		} else {
			entity.jumping = false;
		}
	}

	private void processGravity(Entity entity) {
		if (entity instanceof Bubble && !((Bubble) entity).affectedByGravity)
			return;
		Tile t = WallCollisionHandler.collideForGravity(entity, entity.yspeed, tiles);
		// Se t non Ã¨ nullo allora con la prossima "iterazione" della gravitÃ 
		// collidiamo con una tile, reset della posizione in base alla tile stessa
		if (t != null && !WallCollisionHandler.currentlyInsideTiles(entity, tiles)) {
			if (entity instanceof Bubble) {
				entity.y = t.y + Settings.TILE_HEIGHT;
				entity.hitbox.y = t.y + Settings.TILE_HEIGHT;
			} else {
				//entity.y = t.y - Settings.PLAYER_DIMENSION;
				entity.y = t.y - entity.getHitbox().height;
				//entity.hitbox.y = t.y - Settings.PLAYER_DIMENSION;
				entity.hitbox.y = t.y - entity.getHitbox().height;
			}
		} else {
			entity.fall();
			if(entity.yState != Utilities.SHOOT) entity.yState = Utilities.FALLING;
		}
	}

	private void updateEntity(Entity entity) {
		if(!entity.isAlive) return;
		if (entity.requestedJump) {
			tryToJump(entity);
		}
		entity.yState = Utilities.Y_IDLE;
		if (entity instanceof Player && ((Player) entity).requestedBubble) {
			tryToShoot((Player) entity);
		}
		if (entity.jumping) {
			processJump(entity);
		}
		// Se non tocca terra oppure è all'interno di una tile applichiamo la gravità
		else if (!WallCollisionHandler.touchingGround(entity, tiles)
				|| WallCollisionHandler.currentlyInsideTiles(entity, tiles)) {
			processGravity(entity);
		}
		// se sono in una tile a causa di un salto voglio ignorare le collisioni a dx e
		// sx perche voglio potermi muovere attraverso le tile
		boolean collidingWithRoof = WallCollisionHandler.currentlyInsideTiles(entity, tiles);

		// Controllo se esco fuori dai bordi con il prossimo step, ignorando le
		// collisioni nel salto potrebbe succedere
		if (WallCollisionHandler.collidingWithBorder(entity, entity.xState)) {
			if (entity instanceof Enemy)
				((Enemy) entity).reactOnCollisionWithWalls(entity.xState);
			return;
		}
		if (entity instanceof Bubble && ((Bubble) entity).affectedByGravity)
			return;
		switch (entity.xState) {
		case Utilities.MOVE_LEFT: {
			Tile t = WallCollisionHandler.collideWithWall(entity, entity.xState, tiles);
			if (t != null && !collidingWithRoof) {
				entity.hitbox.x = t.x + Settings.TILE_WIDHT;
				entity.x = t.x + Settings.TILE_WIDHT;
				if (entity instanceof Enemy)
					((Enemy) entity).reactOnCollisionWithWalls(entity.xState);
			} else {
				entity.move();
			}
			break;
		}
		case Utilities.MOVE_RIGHT: {
			Tile t = WallCollisionHandler.collideWithWall(entity, entity.xState, tiles);
			if (t != null && !collidingWithRoof) {
				entity.hitbox.x = t.x - entity.hitbox.width;
				entity.x = t.x - entity.hitbox.width;
				if (entity instanceof Enemy)
					((Enemy) entity).reactOnCollisionWithWalls(entity.xState);
			} else {
				entity.move();
			}
			break;
		}
		default:
			return;
		}
	}

	// Funzioni chiamate dall'update del gameloop in caso di multiplayer
	// Struttura del messaggio:pos who indice posx posy xState yState alive/dead
	public void setEnemyPosition(String[] message) {
			Entity e = (Entity) enemies.get(Integer.parseInt(message[2]));
			setEntityPosition(e, message);
	}

	public void setPlayerPosition(Player player, String[] message) {
		setEntityPosition(player, message);	
	}

	public void setBubblePosition(String[] message) {
		try {
			int index = Integer.parseInt(message[2]);
			if(index >= bubbles.size()) bubbles.add(new Bubble(Integer.parseInt(message[3]), Integer.parseInt(message[4]), Integer.parseInt(message[5])));
			else {
				Entity e = bubbles.get(index);
				setEntityPosition(e, message);				
			}
		} catch(NumberFormatException error) {
			return;
		}
	}
	public void setFoodPosition(String message[]) {
		try {
			int index = Integer.parseInt(message[2]);
			if(index >= food.size()) food.add(new Food(Integer.parseInt(message[3]), Integer.parseInt(message[4]), Integer.parseInt(message[5])));
			else {
				setEntityPosition(food.get(index), message);
			}
		} catch(NumberFormatException error) {
			return;
		}
	}
	public void capture(int bubbleIndex, int enemyIndex) {
		Bubble b = bubbles.get(bubbleIndex);
		Enemy e = enemies.get(enemyIndex);
		b.enemyContained = e;
		((Entity) e).isAlive = false;
		
	}
	private void setEntityPosition(Entity e,String[] message) {
		try {
			e.x = Integer.parseInt(message[3]);
			e.y = Integer.parseInt(message[4]);
			e.hitbox.x = Integer.parseInt(message[3]);
			e.hitbox.y = Integer.parseInt(message[4]);
			e.xState = Integer.parseInt(message[5]);
			e.yState = Integer.parseInt(message[6]);
			if(message[7].equals(Utilities.DEAD)) {
				e.isAlive = false;
				if(e instanceof Bubble) {
					((Bubble)e).enemyContained = null;
				}
			}
			else {
				e.isAlive = true;
			}
		}catch(NumberFormatException error) {
			return;
		}
	}
	public void setScore(int score) {
		this.score = score;
	}
}
