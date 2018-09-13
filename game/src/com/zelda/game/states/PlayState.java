package com.zelda.game.states;

import com.zelda.game.GamePanel;
import com.zelda.game.entity.Enemy;
import com.zelda.game.entity.Player;
import com.zelda.game.graphics.Font;
import com.zelda.game.graphics.Sprite;
import com.zelda.game.tiles.TileManager;
import com.zelda.game.util.KeyHandler;
import com.zelda.game.util.MouseHandler;
import com.zelda.game.util.Vector2f;

import java.awt.Graphics2D;

public class PlayState extends GameState {

	private Font font;
	private Player player;
	private Enemy enemy, enemy2;
	private TileManager tm;

	public static Vector2f map;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		map = new Vector2f();
		Vector2f.setWorldVar(map.x, map.y);

		tm = new TileManager("tile/tilemap.xml");
		font = new Font("font/font.png", 10, 10);

		enemy = new Enemy(new Sprite("entity/littlegirl.png", 48, 48), new Vector2f(0 + (GamePanel.width / 2) - 32 + 150, 0 + (GamePanel.height / 2) - 32 + 150), 64);
		enemy2 = new Enemy(new Sprite("entity/littlegirl.png"), new Vector2f(0 + (GamePanel.width / 2) - 32, 0 + (GamePanel.height / 2) - 32), 64);
		player = new Player(new Sprite("entity/linkFormatted.png"), new Vector2f(0 + (GamePanel.width / 2) - 32, 0 + (GamePanel.height / 2) - 32), 64);
	}

	public void update() {
		Vector2f.setWorldVar(map.x, map.y);
		player.update(enemy);
		player.update(enemy2);
		enemy.update(player);
		enemy2.update(player);
	}

	public void input(MouseHandler mouse, KeyHandler key) {
		player.input(mouse, key);
	}

	public void render(Graphics2D g) {
		tm.render(g);
		String fps = GamePanel.oldFrameCount + " FPS";
		Sprite.drawArray(g, font, fps, new Vector2f(GamePanel.width - fps.length() * 32, 32), 32, 24);

		String tps = GamePanel.oldTickCount + " TPS";
		Sprite.drawArray(g, tps, new Vector2f(GamePanel.width - tps.length() * 32, 64), 32, 24);
		
		player.render(g);
		enemy.render(g);
		enemy2.render(g);
	}
}
