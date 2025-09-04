package org.ryderrobot.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.ryderrobot.display.DisplayHandler;

import static org.ryderrobot.constants.Constants.ROW_HEIGHT;
import static org.ryderrobot.constants.Constants.ROW_WIDTH;

public class ClientScreen extends Stage implements Screen  {
    private final Viewport viewPort;
    private final Texture backgroundTexture;
    private final Camera camera;
    private final Skin skin;
    private final ScreensProcessor screensProcessor;
    private final Stage stage;

    public ClientScreen(Viewport viewport, Texture backgroundTexture, Camera camera, Skin skin,
                        ScreensProcessor screensProcessor) {
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
        final Table mainTable = new Table();
        final DisplayHandler d = screensProcessor.displayHandler();
        mainTable.setFillParent(true);
        mainTable.center();
        mainTable.add(new Label("version: ", skin)).right().width(d.rowWidth()).height(d.rowHeight()).pad(10);

        // TODO: This should not be hardcoded! It should come from constants.
        mainTable.add( new TextField(
            "2.0.1", skin)
        ).width(d.rowWidth()).height(d.rowHeight()).left();
        mainTable.row();

        mainTable.add(new Label("monitor: ", skin)).right().width(d.rowWidth()).height(d.rowHeight()).pad(10);
        mainTable.add(new TextField(Gdx.graphics.getMonitor().name, skin))
            .width(d.rowWidth()).height(d.rowHeight()).left();
        mainTable.row();

        mainTable.add(new Label("monitor (dim): ", skin)).right().width(d.rowWidth())
            .height(d.rowHeight()).pad(10);
        mainTable.add(new TextField(Gdx.graphics.getDisplayMode().toString(),
                skin))
            .width(d.rowWidth()).height(d.rowHeight()).left();
        mainTable.row();

        mainTable.add(new Label("v-monitor (dim): ", skin)).right().width(d.rowWidth())
            .height(d.rowHeight()).pad(10);
        mainTable.add(new TextField(Gdx.graphics.getBackBufferWidth() + "x" + Gdx.graphics.getBackBufferHeight(),
                skin))
            .width(d.rowWidth()).height(d.rowHeight()).left();
        mainTable.row();

        mainTable.add(new Label("app type: ", skin)).right().width(d.rowWidth()).height(d.rowHeight()).pad(10);
        mainTable.add(
            new TextField(Gdx.app.getType().name(), skin)
        ).width(d.rowWidth()).height(d.rowHeight()).left();
        mainTable.row();

        final TextButton back = new TextButton("Back", skin);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screensProcessor.setCurrScreen(ScreensProcessor.SCR_MM);
            }
        });
        final Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.setY(-200);
        buttonTable.add(back).width(d.rowWidth()).height(d.rowHeight());

        addActor(mainTable);
        addActor(buttonTable);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float v) {
        DisplayHandler d = screensProcessor.displayHandler();
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        getBatch().begin();
        getBatch().draw(backgroundTexture, 0, 0, d.worldWidth(), d.worldHeight());
        getBatch().end();

        camera.update();
        act();
        draw();
    }

    @Override
    public void resize(int width, int height) {
        getBatch().begin();
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
}
