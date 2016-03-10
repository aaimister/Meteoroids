package com.fatdolphingames.screens;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.fatdolphingames.gameobjects.ScreenText;
import com.fatdolphingames.gameobjects.Ship;
import com.fatdolphingames.gameworld.GameWorld;
import com.fatdolphingames.helpers.AssetLoader;
import com.fatdolphingames.helpers.InputHandler;
import com.fatdolphingames.meteoroids.MGame;

public class TutorialScreen implements Screen {

    private MGame game;
    private GameWorld world;
    private SpriteBatch batcher;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera cam;
    private Ship ship;
    private ScreenText[] text;
    private String move;
    private Rectangle teleRec;
    private Rectangle skipBox;

    private BitmapFont font;
    private BitmapFont outline;

    private boolean left;
    private boolean right;
    private boolean done;

    private float gameWidth;
    private float gameHeight;
    private float midPointY;
    private float runTime;
    private float prevX;

    private int step;
    private int setup[];

    public TutorialScreen(MGame game, TweenManager tweenManager) {
        this.game = game;
        move = "";

        gameWidth = 136.0f;
        float scaleX = ((float) Gdx.graphics.getWidth()) / gameWidth;
        gameHeight = ((float) Gdx.graphics.getHeight()) / scaleX;
        midPointY = gameHeight / 2.0f;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, gameWidth, gameHeight);

        world = new GameWorld(tweenManager, gameWidth, gameHeight, midPointY);
        ship = world.getShip();
        teleRec = new Rectangle(0.0f, 0.0f, ship.getWidth(), ship.getHeight());
        skipBox = new Rectangle(0.0f, 0.0f, gameWidth, 30.0f);

        outline = AssetLoader.outline;
        font = AssetLoader.font;

        setup = new int[5];
        float textHeight = AssetLoader.calculateFontHeight("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 0.2f) * -1;
        text = new ScreenText[] { new ScreenText(world, 0.0f, 0.0f, (int) AssetLoader.calculateFontWidth("TAP + HOLD", 0.2f), (int) textHeight, "TAP + HOLD", 0.2f),
                new ScreenText(world, 0.0f, 0.0f, (int) AssetLoader.calculateFontWidth("TAP + HOLD", 0.2f), (int) textHeight, "TAP + HOLD", 0.2f),
                new ScreenText(world, 0.0f, 0.0f, (int) AssetLoader.calculateFontWidth("TAP", 0.2f), (int) textHeight, "TAP", 0.2f),
                new ScreenText(world, 0.0f, 0.0f, (int) AssetLoader.calculateFontWidth("TAP", 0.2f), (int) textHeight, "TAP", 0.2f),
                new ScreenText(world, 0.0f, 0.0f, (int) AssetLoader.calculateFontWidth("DODGE TIMER ->", 0.2f), (int) textHeight, "DODGE TIMER ->", 0.2f),
                new ScreenText(world, 0.0f, 0.0f, (int) AssetLoader.calculateFontWidth("TAP + TAP", 0.2f), (int) textHeight, "TAP + TAP", 0.2f),
                new ScreenText(world, 0.0f, 0.0f, (int) AssetLoader.calculateFontWidth("TAP + TAP", 0.2f), (int) textHeight, "TAP + TAP", 0.2f),
                new ScreenText(world, 0.0f, 0.0f, (int) AssetLoader.calculateFontWidth("TAP + TAP", 0.2f), (int) textHeight, "TAP + TAP", 0.2f),
                new ScreenText(world, 0.0f, 0.0f, (int) AssetLoader.calculateFontWidth("--> SWIPE", 0.2f), (int) textHeight, "--> SWIPE", 0.2f),
                new ScreenText(world, 0.0f, 0.0f, (int) AssetLoader.calculateFontWidth("SWIPE <--", 0.2f), (int) textHeight, "SWIPE <--", 0.2f),
                new ScreenText(world, 0.0f, 0.0f, (int) AssetLoader.calculateFontWidth("SWIPE <--", 0.2f), (int) textHeight, "SWIPE <--", 0.2f),
                new ScreenText(world, 0.0f, 0.0f, (int) AssetLoader.calculateFontWidth("--> SWIPE", 0.2f), (int) textHeight, "--> SWIPE", 0.2f) };

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        Gdx.input.setInputProcessor(new InputHandler(world, scaleX, ((float) Gdx.graphics.getHeight()) / gameHeight));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        runTime += delta;
        update(delta);

        batcher.begin();
        world.drawBatcher(batcher, runTime);

        if (!done && !world.isMenuOpen()) {
            batcher.setColor(AssetLoader.WHITE);
            AssetLoader.setFontScale(0.4f);
            outline.draw(batcher, "Tutorial", 0, 10.0f, gameWidth, Align.center, false);
            font.draw(batcher, "Tutorial", 0, 10.0f, gameWidth, Align.center, false);
            AssetLoader.setFontScale(0.2f);
            outline.draw(batcher, "(PRESS TO SKIP)", 0, 20.0f, gameWidth, Align.center, false);
            font.draw(batcher, "(PRESS TO SKIP)", 0, 20.0f, gameWidth, Align.center, false);
            AssetLoader.setFontScale(0.3f);
            outline.draw(batcher, move, 0, midPointY, gameWidth, Align.center, false);
            font.draw(batcher, move, 0, midPointY, gameWidth, Align.center, false);
        }

        text[0].drawBatcher(batcher, runTime);
        text[1].drawBatcher(batcher, runTime);
        text[2].drawBatcher(batcher, runTime);
        text[3].drawBatcher(batcher, runTime);
        text[4].drawBatcher(batcher, runTime);
        text[5].drawBatcher(batcher, runTime);
        text[6].drawBatcher(batcher, runTime);
        text[7].drawBatcher(batcher, runTime);
        text[8].drawBatcher(batcher, runTime);
        text[9].drawBatcher(batcher, runTime);
        text[10].drawBatcher(batcher, runTime);
        text[11].drawBatcher(batcher, runTime);
        batcher.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        world.drawShapeRenderer(shapeRenderer, runTime);
        if (step == 3) {
            Color c = !left ? ship.getX() == gameWidth - ship.getWidth() ? AssetLoader.GREEN : AssetLoader.RED : ship.getX() == 0 ? AssetLoader.GREEN : AssetLoader.RED;
            shapeRenderer.setColor(c.r, c.g, c.b, 0.3f);
            shapeRenderer.rect(teleRec.x, teleRec.y, teleRec.width, teleRec.height);
        }
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void show() {
        // Do nothing.
    }

    @Override
    public void resize(int width, int height) {
        // Do nothing.
    }

    @Override
    public void pause() {
        // Do nothing.
    }

    @Override
    public void resume() {
        // Do nothing.
    }

    @Override
    public void hide() {
        // Do nothing.
    }

    @Override
    public void dispose() {
        // Do nothing.
    }

    private void update(float delta) {
        world.update(delta);
        setup(step);

        if (Gdx.input.justTouched() && skipBox.contains(Gdx.input.getX() / (Gdx.graphics.getWidth() / gameWidth), Gdx.input.getY() / (Gdx.graphics.getHeight() / gameHeight))) {
            skip();
        }

        if (step == 0) {
            // Test Movement
            if (!ship.isResetting()) {
                move = left ? "Move Right" : "Move Left";
                if (!left) {
                    text[0].show();
                    if (prevX > ship.getX()){
                        left = true;
                        text[0].setColor(AssetLoader.GREEN);
                        text[0].hide();
                        text[1].show();
                    }
                } else if (prevX < ship.getX()) {
                    text[1].setColor(AssetLoader.GREEN);
                    text[1].hide();
                    left = false;
                    step++;
                }
            }
        } else if (step == 1) {
            // Test Dodge
            move = "Dodge";
            text[2].show();
            text[3].show();
            text[4].show();
            if (ship.getScaleX() < 1.0f) {
                text[2].setColor(AssetLoader.GREEN);
                text[2].hide();
                text[3].setColor(AssetLoader.GREEN);
                text[3].hide();
                text[4].hide();
                step++;
            }
        } else if (step == 2) {
            // Test SideRoll
            move = left ? "Zooom Right" : "Zoom Left";
            if (!left) {
                text[5].show();
                if (ship.isSideRoll() && prevX > ship.getX()) {
                    left = true;
                    text[5].setColor(AssetLoader.GREEN);
                    text[5].hide();
                    text[6].show();
                }
            } else if (ship.isSideRoll() && prevX < ship.getX()) {
                text[6].setColor(AssetLoader.GREEN);
                text[6].hide();
                left = false;
                step++;
            }
        } else if (step == 3) {
            // Test Teleport
            move = left ? "Teleport Right" : "Teleport Left";
            if (!left) {
                text[5].show();
                if (ship.isTeleporting() && ship.getX() > (gameWidth - ship.getWidth())) {
                    left = true;
                    text[5].setColor(AssetLoader.GREEN);
                    text[5].hide();
                    teleRec.setX(0.0f);
                    text[7].show();
                }
            } else if (!right && ship.isTeleporting() && ship.getX() < 0) {
                text[7].setColor(AssetLoader.GREEN);
                text[7].hide();
                left = false;
                step++;
            }
            right = ship.isTeleporting();
        } else if (step == 4) {
            // Test Menu
            if (!ship.isTeleporting()) {
                if (ship.isAlive()) {
                    ship.collidedWith(ship);
                }
                if (!world.isMenuOpen() && !right && !done) {
                    move = "Open Menu";
                    text[8].show();
                    text[9].show();
                }
                if (world.isMenuOpen() && !right) {
                    text[8].setColor(AssetLoader.GREEN);
                    text[8].hide();
                    text[9].setColor(AssetLoader.GREEN);
                    text[9].hide();
                    text[10].show();
                    text[11].show();
                    right = true;
                } else if (!world.isMenuOpen() && right && !done) {
                    text[10].setColor(AssetLoader.GREEN);
                    text[10].hide();
                    text[11].setColor(AssetLoader.GREEN);
                    text[11].hide();
                    done = true;
                } else if (done && !text[11].showing()) {
                    skip();
                }
            } else {
                right = false;
            }
        }
        prevX = ship.getX();
    }

    private void skip() {
        step = 5;
        game.setScreen(new GameScreen(world, batcher, shapeRenderer));
    }

    private void setup(int step) {
        if (setup[step] != 1) {
            setup[step] = 1;
            switch (step) {
                case 0:
                    text[0].setColor(AssetLoader.RED);
                    text[0].setAlpha(0.0f);
                    text[0].setPosition(5.0f, gameHeight - 70.0f);
                    text[1].setColor(AssetLoader.RED);
                    text[1].setAlpha(0.0f);
                    text[1].setPosition(gameWidth - text[1].getWidth() - 5, gameHeight - 70.0f);
                    break;
                case 1:
                    text[2].setColor(AssetLoader.RED);
                    text[2].setAlpha(0.0f);
                    text[2].setPosition(5.0f, gameHeight - 70.0f);
                    text[3].setColor(AssetLoader.RED);
                    text[3].setAlpha(0.0f);
                    text[3].setPosition(gameWidth - text[3].getWidth() - 5, gameHeight - 70.0f);
                    text[4].setPosition(gameWidth - 35.0f - text[4].getWidth() - 5.0f, gameHeight - 15.0f);
                    break;
                case 2:
                    text[5].setColor(AssetLoader.RED);
                    text[5].setAlpha(0.0f);
                    text[5].setPosition(5.0f, gameHeight - 70.0f);
                    text[6].setColor(AssetLoader.RED);
                    text[6].setAlpha(0.0f);
                    text[6].setPosition(gameWidth - text[5].getWidth() - 5, gameHeight - 70.0f);
                    break;
                case 3:
                    text[5].setColor(AssetLoader.RED);
                    text[5].setAlpha(0.0f);
                    text[5].setPosition(gameWidth - text[5].getWidth() - 5, gameHeight - 70.0f);
                    teleRec.setPosition(gameWidth - ship.getWidth(), ship.getY());
                    text[7].setColor(AssetLoader.RED);
                    text[7].setAlpha(0.0f);
                    text[7].setPosition(5.0f, gameHeight - 70.0f);
                    break;
                case 4:
                    text[8].setColor(AssetLoader.RED);
                    text[8].setAlpha(0.0f);
                    text[8].setPosition(5.0f, gameHeight - 70.0f);
                    text[9].setColor(AssetLoader.RED);
                    text[9].setAlpha(0.0f);
                    text[9].setPosition(gameWidth - text[1].getWidth() - 5, gameHeight - 70.0f);
                    text[10].setColor(AssetLoader.RED);
                    text[10].setAlpha(0.0f);
                    text[10].setPosition(5.0f, gameHeight - 70.0f);
                    text[11].setColor(AssetLoader.RED);
                    text[11].setAlpha(0.0f);
                    text[11].setPosition(gameWidth - text[1].getWidth() - 5, gameHeight - 70.0f);
                    break;
                default:
            }
        }
    }
}
