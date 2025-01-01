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

public class StatusScreen extends Stage implements Screen  {
    private final Viewport viewPort;
    private final Texture backgroundTexture;
    private final Camera camera;
    private final Skin skin;
    private final ScreensProcessor screensProcessor;
    private final Stage stage;

    private final int BARO  =0b000000000000001;  // Barometer
    private final int MAG   =0b000000000000010;  // Magnometer
    private final int GPS   =0b000000000000100;  // GPS
    private final int SONAR =0b000000000001000;  // Ultrasonic
    private final int CAMERA=0b000000000010000;

    private final int INITILIZING    = 1;  // indicates that the handler has been started but is not yet active
    private final int ACTIVE         = 2;  // indicates that the handler is an active state, and ready to consume or produce.
    private final int ERROR          = 4;  // indicates that an error has occured, and the handler will need to be reloaded.
    private final int RELOADING      = 8;  // reloading after an error has occured.
    private final int SHUTTING_DOWN  = 16; // indicates that system is now shutting down.
    private final int TERMINATED     = 32;

    StatusScreen(Viewport viewport, Texture backgroundTexture, Camera camera, Skin skin,
                 ScreensProcessor screensProcessor) {
        super(viewport, new SpriteBatch());
        this.viewPort = viewport;
        this.backgroundTexture = backgroundTexture;
        this.camera = camera;
        this.skin = skin;
        this.screensProcessor = screensProcessor;
        this.stage = this;
    }

    private Table createSensorTable() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        int sensors = screensProcessor.getDrone().getStatus().getSensor();
        mainTable.center();
        mainTable.add(new Label("baro: ", skin)).right().width(((float)ROW_WIDTH)/4).height(ROW_HEIGHT).pad(10);
        mainTable.add(new TextField(String.valueOf((sensors & BARO) == BARO), skin))
            .width(((float)ROW_WIDTH)/4).height(ROW_HEIGHT).left();

        mainTable.add(new Label("mag: ", skin)).right().width(((float)ROW_WIDTH)/4).height(ROW_HEIGHT).pad(10);
        mainTable.add(new TextField(String.valueOf((sensors & MAG) == MAG), skin))
            .width(((float)ROW_WIDTH)/4).height(ROW_HEIGHT).left();

        mainTable.add(new Label("gps: ", skin)).right().width(((float)ROW_WIDTH)/4).height(ROW_HEIGHT).pad(10);
        mainTable.add(new TextField(String.valueOf((sensors & GPS) == GPS), skin))
            .width(((float)ROW_WIDTH)/4).height(ROW_HEIGHT).left();

        mainTable.add(new Label("sonar: ", skin)).right().width(((float)ROW_WIDTH)/4).height(ROW_HEIGHT).pad(10);
        mainTable.add(new TextField(String.valueOf((sensors & SONAR) == SONAR), skin))
            .width(((float)ROW_WIDTH)/4).height(ROW_HEIGHT).left();

        mainTable.add(new Label("camera: ", skin)).right().width(((float)ROW_WIDTH)/4).height(ROW_HEIGHT).pad(10);
        mainTable.add(new TextField(String.valueOf((sensors & CAMERA) == CAMERA), skin))
            .width(((float)ROW_WIDTH)/4).height(ROW_HEIGHT).left();
        mainTable.row();

        mainTable.setY(-200);

        return mainTable;
    }

    private Table createTable() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        mainTable.add(new Label("cycle time: ", skin)).right().width(ROW_WIDTH).height(ROW_HEIGHT).pad(10);
        mainTable.add( new TextField(
            String.valueOf(screensProcessor.getDrone().getStatus().getCycletime()), skin)
        ).width(ROW_WIDTH).height(ROW_HEIGHT).left();
        mainTable.row();

        mainTable.add(new Label("errors count: ", skin)).right().width(ROW_WIDTH).height(ROW_HEIGHT).pad(10);
        mainTable.add(new TextField(String.valueOf(screensProcessor.getDrone().getStatus().getI2c_errors_count()), skin))
            .width(ROW_WIDTH).height(ROW_HEIGHT).left();
        mainTable.row();

        int flags = screensProcessor.getDrone().getStatus().getFlag();
        int[] statusA = {RELOADING, ERROR, INITILIZING, SHUTTING_DOWN, TERMINATED, ACTIVE};
        String status = "INITILIZING";
        String[] statusS = {"RELOADING", "ERROR", "INITILIZING", "SHUTTING_DOWN", "TERMINATED", "ACTIVE"};
        for (int s = 0; s < statusA.length; s++) {
            if ((statusA[s] & flags) == statusA[s]) {
                status = statusS[s];
                break;
            }
        }

        mainTable.add(new Label("status: ", skin)).right().width(ROW_WIDTH).height(ROW_HEIGHT).pad(10);
        mainTable.add(new TextField(status, skin))
            .width(ROW_WIDTH).height(ROW_HEIGHT).left();
        mainTable.row();

        return mainTable;
    }

    @Override
    public void show() {

        final TextButton back = new TextButton("Back", skin);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screensProcessor.setCurrScreen(ScreensProcessor.SCR_MM);
            }
        });
        final Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.setY(-300);
        buttonTable.add(back).width(ROW_WIDTH).height(ROW_HEIGHT);

        addActor(buttonTable);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float v) {
        // this method will need to call show, in order to refresh the screen.
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        getBatch().begin();
        getBatch().draw(backgroundTexture, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        getBatch().end();

        camera.update();
        addActor(createTable());
        addActor(createSensorTable());
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
