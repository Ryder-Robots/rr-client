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
import org.ryderrobot.constants.Constants;
import org.ryderrobot.drones.Drone;

import static org.ryderrobot.constants.Constants.ROW_HEIGHT;
import static org.ryderrobot.constants.Constants.ROW_WIDTH;

public class DroneDetailsScreen  extends Stage implements Screen {
    private final Viewport viewPort;
    private final Texture backgroundTexture;
    private final Camera camera;
    private final Skin skin;
    private final ScreensProcessor screensProcessor;
    private final Drone drone;
    private final Stage stage;

    public DroneDetailsScreen(Viewport viewport, Texture backgroundTexture, Camera camera, Skin skin,
                              ScreensProcessor screensProcessor, Drone drone) {
        super(viewport, new SpriteBatch());
        this.viewPort = viewport;
        this.backgroundTexture = backgroundTexture;
        this.camera = camera;
        this.skin = skin;
        this.screensProcessor = screensProcessor;
        this.drone = drone;
        this.stage = this;
    }

    @Override
    public void show() {
        final Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();
        mainTable.add(new Label("version: ", skin)).right().width(ROW_WIDTH).height(ROW_HEIGHT).pad(10);
        mainTable.add( new TextField(
            String.valueOf(drone.getIdentity().getVersion()), skin)
        ).width(ROW_WIDTH).height(ROW_HEIGHT).left();
        mainTable.row();

        mainTable.add(new Label("multitype: ", skin)).right().width(ROW_WIDTH).height(ROW_HEIGHT).pad(10);
        mainTable.add(new TextField(drone.getIdentity().getMultitype().name(), skin))
            .width(ROW_WIDTH).height(ROW_HEIGHT).left();
        mainTable.row();

        mainTable.add(new Label("mspversion: ", skin)).right().width(ROW_WIDTH).height(ROW_HEIGHT).pad(10);
        mainTable.add(new TextField(drone.getIdentity().getMspversion().name(), skin))
            .width(ROW_WIDTH).height(ROW_HEIGHT).left();
        mainTable.row();

        mainTable.add(new Label("capability: ", skin)).right().width(ROW_WIDTH).height(ROW_HEIGHT).pad(10);
        mainTable.add(
            new TextField(String.valueOf(drone.getIdentity().getCapability()), skin)
        ).width(ROW_WIDTH).height(ROW_HEIGHT).left();
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
        buttonTable.add(back).width(ROW_WIDTH).height(ROW_HEIGHT);

        addActor(mainTable);
        addActor(buttonTable);
        Gdx.input.setInputProcessor(this);
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
