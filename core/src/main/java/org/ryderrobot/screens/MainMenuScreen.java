package org.ryderrobot.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.ryderrobot.constants.Constants;
import org.ryderrobot.drones.Drone;

import static java.lang.Math.round;
import static org.ryderrobot.constants.Constants.ROW_HEIGHT;
import static org.ryderrobot.constants.Constants.ROW_WIDTH;

public class MainMenuScreen extends Stage implements Screen {
    private final Viewport viewPort;
    private final Texture backgroundTexture;
    private final Camera camera;
    private final ScreensProcessor screensProcessor;
    private final Stage stage;

    final String[] menuItems = {
        "Connect",
        "Manual Fly (Data Collection)",
        "Drone Details",
        "Drone Status",
        "Disconnect",
        "Exit",
    };

    // Maps actions to the menu items above.
    final ClickListener[] menuItemsListener = {
        new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screensProcessor.setCurrScreen(ScreensProcessor.SCR_CONNECT);
            }
        }, // connect
        new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (screensProcessor.getDrone().isConnected()) {
                    screensProcessor.setCurrScreen(ScreensProcessor.SCR_MAN_FLIGHT);
                } else {
                    final Dialog dialog = new Dialog("not connected", skin);
                    dialog.text("you must first select a drone before you can fly it");
                    dialog.button("Ok", false);
                    dialog.show(stage);
                }
            }
        }, // Manual Fly (Data Collection)
        new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (screensProcessor.getDrone().isConnected()) {
                    screensProcessor.setCurrScreen(ScreensProcessor.SCR_IDENT);
                } else {
                    final Dialog dialog = new Dialog("not connected", skin);
                    dialog.text("you must first select a drone before you can fly it");
                    dialog.button("Ok", false);
                    dialog.show(stage);
                }
            }

        }, // Display drone details

        new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (screensProcessor.getDrone().isConnected()) {
                    screensProcessor.setCurrScreen(ScreensProcessor.SCR_STATUS);
                } else {
                    final Dialog dialog = new Dialog("not connected", skin);
                    dialog.text("you must first select a drone before you can fly it");
                    dialog.button("Ok", false);
                    dialog.show(stage);
                }
            }

        }, // Display status

        new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screensProcessor.getDrone().dispose();
            }
        },
        new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screensProcessor.getDrone().dispose();
                Gdx.app.exit();
            }
        }, // Exit
    };

    private final Skin skin;

    public MainMenuScreen(Viewport viewport, Texture backgroundTexture, Camera camera,
                          Skin skin, ScreensProcessor screensProcessor) {
        super(viewport, new SpriteBatch());
        this.viewPort = viewport;
        this.backgroundTexture = backgroundTexture;
        this.camera = camera;
        this.skin = skin;
        this.screensProcessor = screensProcessor;
        this.stage = this;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();
        TextButton[] buttons = new TextButton[menuItems.length];

        for (int i = 0;i < menuItems.length; i++) {
            TextButton button = new TextButton(menuItems[i], skin);
            button.addListener(menuItemsListener[i]);

            mainTable.add(button).width(round(ROW_WIDTH * 1.5)).height(ROW_HEIGHT).pad(10);
            mainTable.row();
            buttons[i] = button;
        }
        setActionsRequestRendering(true);
        addActor(mainTable);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        getBatch().begin();
        getBatch().draw(backgroundTexture, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        getBatch().end();

        camera.update();
        act();
        draw();
    }

    @Override
    public void resize(int width, int height) {
        getBatch().begin();
        getBatch().draw(backgroundTexture, 0, 0, width, height);
        getBatch().end();

        viewPort.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        screensProcessor.getDrone().dispose();
    }
}
