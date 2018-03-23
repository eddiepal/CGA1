package ie.wit.cgd.bunnyhop.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import ie.wit.cgd.bunnyhop.game.objects.BunnyHead;
import ie.wit.cgd.bunnyhop.game.objects.BunnyHead.JUMP_STATE;
import ie.wit.cgd.bunnyhop.game.objects.BunnyLife;
import ie.wit.cgd.bunnyhop.game.objects.Feather;
import ie.wit.cgd.bunnyhop.game.objects.Goal;
import ie.wit.cgd.bunnyhop.game.objects.GoldCoin;
import ie.wit.cgd.bunnyhop.game.objects.Rock;
import ie.wit.cgd.bunnyhop.game.objects.Bird;
import ie.wit.cgd.bunnyhop.util.CameraHelper;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import ie.wit.cgd.bunnyhop.util.Constants;

public class WorldController extends InputAdapter {

	private static final String TAG = WorldController.class.getName();
	public CameraHelper cameraHelper;
	public Level level;
	public Level currLevel;
	public Level initCurrLevelReset;
	public Level initCurrLevel;
	public int lives;
	public int score;
<<<<<<< HEAD
	public float gameOverTimer = Constants.ITEM_FEATHER_POWERUP_DURATION;
	
	public WorldController() {

		init();
	}
=======
	public float gameOverTimer = Constants.GAME_OVER_TIMER;
>>>>>>> f513a203e9cae4c07bc534489ce20c7b792e006d

	private void initCurrLevelReset() {
		if (Constants.currLevel == Constants.LEVEL_01) {
			score = 0;
			level = new Level(Constants.LEVEL_01);
			cameraHelper.setTarget(level.bunnyHead);
		}

		else if (Constants.currLevel == Constants.LEVEL_02) {
			score = 0;
			level = new Level(Constants.LEVEL_02);
			cameraHelper.setTarget(level.bunnyHead);
		}

		else if (Constants.currLevel == Constants.LEVEL_03) {
			score = 0;
			level = new Level(Constants.LEVEL_03);
			// level = new level(String.format("levels/level-%02d.png", number));
			cameraHelper.setTarget(level.bunnyHead);
		}

	}

	private void initCurrLevel() {

		if (Constants.currLevel == Constants.LEVEL_01) {
			level.bunnyHead.position.set(1, 2);
			cameraHelper.setTarget(level.bunnyHead);
		}

		if (Constants.currLevel == Constants.LEVEL_02) {
			level.bunnyHead.position.set(1, 10);
			cameraHelper.setTarget(level.bunnyHead);
		}

		if (Constants.currLevel == Constants.LEVEL_03) {
			level.bunnyHead.position.set(1, 2);
			cameraHelper.setTarget(level.bunnyHead);
		}
	}

	private void onCollisionBunnyHeadWithRock(Rock rock) {
		BunnyHead bunnyHead = level.bunnyHead;
		float heightDifference = Math.abs(bunnyHead.position.y - (rock.position.y + rock.bounds.height));
		if (heightDifference > 0.25f) {
			boolean hitLeftEdge = bunnyHead.position.x > (rock.position.x + rock.bounds.width / 2.0f);
			if (hitLeftEdge) {
				bunnyHead.position.x = rock.position.x + rock.bounds.width;
			} else {
				bunnyHead.position.x = rock.position.x - bunnyHead.bounds.width;
			}
			return;
		}

		switch (bunnyHead.jumpState) {
		case GROUNDED:
			break;
		case FALLING:
		case JUMP_FALLING:
			bunnyHead.position.y = rock.position.y + bunnyHead.bounds.height + bunnyHead.origin.y;
			bunnyHead.jumpState = JUMP_STATE.GROUNDED;
			break;
		case JUMP_RISING:
			bunnyHead.position.y = rock.position.y + bunnyHead.bounds.height + bunnyHead.origin.y;
			break;
		}
	}

	private void onCollisionBunnyWithGoldCoin(GoldCoin goldcoin) {
		goldcoin.collected = true;
		score += goldcoin.getScore();
		Gdx.app.log(TAG, "Gold coin collected");
	};

	private void onCollisionBunnyWithFeather(Feather feather) {
		feather.collected = true;
		score += feather.getScore();
		level.bunnyHead.setFeatherPowerup(true);
		Gdx.app.log(TAG, "Feather collected");
	};

	private void onCollisionBunnyWithBunnyLife(BunnyLife bunnyLife) {
		bunnyLife.collected = true;
		lives++;
		Gdx.app.log(TAG, "Feather collected");
	};
	
	private void onCollisionBunnyWithBird(Bird bird) {
		bird.collected = true;
		lives++;
		Gdx.app.log(TAG, "Feather collected");
	};

	private void onCollisionBunnyWithGoal(Goal goal) {
		goal.collected = true;
		score += goal.getScore();
		level.bunnyHead.setIsGameWon(true);
		Gdx.app.log(TAG, "Goal Collected");
		timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
	};

	// Rectangles for collision detection
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();

	private void testCollisions() {
		r1.set(level.bunnyHead.position.x, level.bunnyHead.position.y, level.bunnyHead.bounds.width,
				level.bunnyHead.bounds.height);

		// Test collision: Bunny Head <-> Rocks
		for (Rock rock : level.rocks) {
			r2.set(rock.position.x, rock.position.y, rock.bounds.width, rock.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionBunnyHeadWithRock(rock);
			// IMPORTANT: must do all collisions for valid
			// edge testing on rocks.
		}

		// Test collision: Bunny Head <-> Gold Coins
		for (GoldCoin goldCoin : level.goldCoins) {
			if (goldCoin.collected)
				continue;
			r2.set(goldCoin.position.x, goldCoin.position.y, goldCoin.bounds.width, goldCoin.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionBunnyWithGoldCoin(goldCoin);
			break;
		}
		
		// Test collision: Bunny Head <-> Birds
		for (Bird bird : level.birds) {
			if (bird.collected)
				continue;
			r2.set(bird.position.x, bird.position.y, bird.bounds.width, bird.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionBunnyWithBird(bird);
		}

		// Test collision: Bunny Head <-> Feathers
		for (Feather feather : level.feathers) {
			if (feather.collected)
				continue;
			r2.set(feather.position.x, feather.position.y, feather.bounds.width, feather.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionBunnyWithFeather(feather);
			break;
		}

		// Test collision: Bunny Head <-> Bunny Lives
		for (BunnyLife bunnyLife : level.bunnyLives) {
			if (bunnyLife.collected)
				continue;
			r2.set(bunnyLife.position.x, bunnyLife.position.y, bunnyLife.bounds.width, bunnyLife.bounds.height);
			if (!r1.overlaps(r2))
				continue;
			onCollisionBunnyWithBunnyLife(bunnyLife);
			break;
		}

		Goal goal = level.goal;
		if (!goal.collected)
			r2.set(goal.position.x, goal.position.y, goal.bounds.width, goal.bounds.height);
		if (r1.overlaps(r2))
			onCollisionBunnyWithGoal(goal);
	}

	private void handleInputGame(float deltaTime) {

		if (cameraHelper.hasTarget(level.bunnyHead)) {

			// Player Movement
			if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				level.bunnyHead.velocity.x = -level.bunnyHead.terminalVelocity.x;
			} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				level.bunnyHead.velocity.x = level.bunnyHead.terminalVelocity.x;
			} else {
				// Execute auto-forward movement on non-desktop platform
				if (Gdx.app.getType() != ApplicationType.Desktop) {
					level.bunnyHead.velocity.x = level.bunnyHead.terminalVelocity.x;
				}
			}

			// Bunny Jump
			if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) {
				level.bunnyHead.setJumping(true);
			} else {
				level.bunnyHead.setJumping(false);
			}
		}
	}

	@Override
	public boolean keyUp(int keycode) {

		if (keycode == Keys.R) { // Reset game world
			init();
			Gdx.app.debug(TAG, "Game world resetted");
		} else if (keycode == Keys.ENTER) { // Toggle camera follow
			cameraHelper.setTarget(cameraHelper.hasTarget() ? null : level.bunnyHead);
			Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
		}
		return false;
	}

	private void init() {
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		lives = Constants.LIVES_START;
		timeLeftGameOverDelay = 0;
		initCurrLevelReset();
		cameraHelper.setTarget(level.bunnyHead);
	}

	public void update(float deltaTime) {
		
	      if (gameOverTimer >= 0) {
	    	  gameOverTimer -= deltaTime;
	          if (gameOverTimer < 0) {
	              // disable power-up
	        	  gameOverTimer = Constants.ITEM_FEATHER_POWERUP_DURATION;
	              init();
	          }
	      }

		if (lives > 3) {
			lives = 3;
		}
		deltaTime = MathUtils.clamp(deltaTime, 0f, 0.02f);
		handleDebugInput(deltaTime);
		level.update(deltaTime);
		testCollisions();
		cameraHelper.update(deltaTime);

		if (isGameOver() || isGameWon()) {
			timeLeftGameOverDelay -= deltaTime;
			if (timeLeftGameOverDelay < 0)
				init();
		} else {
			handleInputGame(deltaTime);
		}

		if (!isGameOver() && isPlayerInWater()) {
			lives--;
			if (isGameOver())
				timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
			else
				initCurrLevel();
		}

		if (isGameWon() || Gdx.input.isKeyPressed(Keys.F2)) {
			Constants.currLevel = Constants.LEVEL_02;
			timeLeftGameOverDelay -= deltaTime;
			if (timeLeftGameOverDelay < 0) {
				initCurrLevelReset();
				//init();
			}
		}
		
		if(Gdx.input.isKeyPressed(Keys.F1)){
			Constants.currLevel = Constants.LEVEL_01;
			initCurrLevelReset();
		}

	}

	private void handleDebugInput(float deltaTime) {

		if (Gdx.app.getType() != ApplicationType.Desktop)
			return;

		// Camera Controls (move)
		if (!cameraHelper.hasTarget(level.bunnyHead)) {

			float camMoveSpeed = 5 * deltaTime;
			float camMoveSpeedAccelerationFactor = 5;
			if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
				camMoveSpeed *= camMoveSpeedAccelerationFactor;
			if (Gdx.input.isKeyPressed(Keys.LEFT))
				moveCamera(-camMoveSpeed, 0);
			if (Gdx.input.isKeyPressed(Keys.RIGHT))
				moveCamera(camMoveSpeed, 0);
			if (Gdx.input.isKeyPressed(Keys.UP))
				moveCamera(0, camMoveSpeed);
			if (Gdx.input.isKeyPressed(Keys.DOWN))
				moveCamera(0, -camMoveSpeed);
			if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
				cameraHelper.setPosition(0, 0);
		}

		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA))
			cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD))
			cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH))
			cameraHelper.setZoom(1);
	}

	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}

	private float timeLeftGameOverDelay;

	public boolean isGameOver() {
		return lives <= 0;
	}

	public boolean isGameWon() {
		if (level.goal.collected) {
			return true;
		} else
			return false;
	}

	public boolean isPlayerInWater() {
		return level.bunnyHead.position.y < -5;
	}

}