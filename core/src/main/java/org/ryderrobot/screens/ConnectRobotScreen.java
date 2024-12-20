package org.ryderrobot.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.czyzby.kiwi.util.common.Strings;
import org.ryderrobot.constants.Constants;
import org.ryderrobot.drones.Drone;
import com.badlogic.gdx.Gdx;
import org.ryderrobot.models.protocols.rrp.MspIdentPayload;
import org.ryderrobot.models.protocols.rrp.RrpCommands;
import org.ryderrobot.models.protocols.rrp.RrpEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.Math.round;
import static org.ryderrobot.constants.Constants.*;

public class ConnectRobotScreen extends Stage implements Screen  {
    private final Viewport viewPort;
    private final Texture backgroundTexture;
    private final Camera camera;
    private final Skin skin;
    private final ScreensProcessor screensProcessor;
    private final Drone drone;
    private final Stage stage;

    /**
     * Class constructor
     *
     * @param viewport          common view port.
     * @param backgroundTexture common background
     * @param camera            non connected camera
     * @param skin              program layout
     * @param screensProcessor  connect to other screens.
     */
    public ConnectRobotScreen(Viewport viewport, Texture backgroundTexture, Camera camera, Skin skin,
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
        final Json json = new Json();
        final FileHandle file = Gdx.files.internal(CONNECTION_DETAILS_FL);
        String addstr = "", port = "";
        if (file.exists()) {
            Map<String, String>  jsonData = json.fromJson(HashMap.class, file);
            addstr = jsonData.get("address");
            port = jsonData.get("port");
        }

        final TextField addrTextField = new TextField(addstr, skin);
        final TextField portTxtField = new TextField(port, skin);
        final TextButton connect = new TextButton("Connect", skin);
        final TextField status = new TextField("connecting", skin);
        status.setDisabled(true);

        connect.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Optional<Exception> whatHappened = Optional.empty();
                try {
                    if (Strings.isNotEmpty(addrTextField.getText())) {
                        drone.getSocketClient().connect(addrTextField.getText(),
                            Integer.parseInt(portTxtField.getText()));
                        if (drone.isConnected()) {
                            Thread thread = new Thread(drone);
                            thread.setDaemon(true);
                            thread.start();
                            while(!drone.isIdentitySet()) {
                                render(10);
                                Thread.sleep(10);
                            }

                            //TODO: call drone factory here.
                        }
                    } else {
                        throw new RuntimeException("missing required fields");
                    }
                } catch (Exception e) {
                    whatHappened = Optional.of(e);
                }

                if (drone.isConnected()) {
                    screensProcessor.setCurrScreen(0);
                } else {
                    String err = "unknown error";
                    if (whatHappened.isPresent()) {
                        err = whatHappened.get().getMessage() + "\n:" + whatHappened.get().getCause();
                    }
                    final Dialog dialog = new Dialog("could not connect", skin);
                    dialog.text(err);
                    dialog.button("Ok", false);
                    dialog.show(stage);
                }
            }
        });

        final TextButton back = new TextButton("Back", skin);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screensProcessor.setCurrScreen(ScreensProcessor.SCR_MM);
            }
        });

        final Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();
        mainTable.add(new Label("IP or CNAME: ", skin)).right().width(ROW_WIDTH).height(ROW_HEIGHT).pad(10);
        mainTable.row();
        mainTable.add(addrTextField).width(ROW_WIDTH).height(ROW_HEIGHT).left();
        mainTable.add(portTxtField).height(ROW_HEIGHT).width(round((float) ROW_WIDTH / 3)).pad(10);
        mainTable.row();
        mainTable.row();
        mainTable.add(status).right().width(ROW_WIDTH).height(ROW_HEIGHT).pad(10);

        final Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.setY(-200);
        buttonTable.add(connect).width(ROW_WIDTH).height(ROW_HEIGHT).pad(10);
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
