package org.ryderrobot.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.ryderrobot.Constants;
import org.ryderrobot.listeners.MenuControllerListener;
import org.ryderrobot.models.Drone;

import static java.lang.Math.round;
import static org.ryderrobot.Constants.ROW_HEIGHT;
import static org.ryderrobot.Constants.ROW_WIDTH;

public class MainMenuScreen extends Stage implements Screen {
    private final Viewport viewPort;
    private final Texture backgroundTexture;
    private final Camera camera;
    private final ScreensProcessor screensProcessor;

    final String[] menuItems = {
        "Connect",
        "Create Flight Path",
        "Manual Fly (Data Collection)",
        "Training Mode",
        "Assisted Flight",
        "Automated Flight",
        "Exit"
    };

    // Maps actions to the menu items above.
    final ClickListener[] menuItemsListener = {
        new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screensProcessor.setCurrScreen(1);
            }
        }, // connect
        new ClickListener(), // Create Flight Path
        new ClickListener(), // Manual Fly (Data Collection)
        new ClickListener(), // Training Mode
        new ClickListener(), // Assisted Flight
        new ClickListener(), // Automated Flight
        new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
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
        Controller controller = Controllers.getCurrent();
        controller.addListener(new MenuControllerListener(buttons) {
                @Override
                public boolean buttonDownEvent(Controller controller, int i) {
                    if (i == Constants.CTRL_X_BUTTON) {
                        switch (getCurrButtonIdx()) {
                            case 0:
                                screensProcessor.setCurrScreen(ScreensProcessor.SCR_CONNECT);
                                break;
                            case 6:
                                Gdx.app.exit();
                                break;
                        }
                        return true;
                    }
                    return false;
                }
            }
        );
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

    }
}
