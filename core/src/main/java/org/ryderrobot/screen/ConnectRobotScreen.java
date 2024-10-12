package org.ryderrobot.screen;

/*
 * Screen to connect to drone.
 */

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
import com.github.czyzby.kiwi.util.common.Strings;
import org.ryderrobot.Constants;
import org.ryderrobot.client.SocketClient;
import org.ryderrobot.client.SocketWriter;
import org.ryderrobot.env.Drone;

import java.util.Optional;

import static java.lang.Math.round;
import static org.ryderrobot.Constants.ROW_HEIGHT;
import static org.ryderrobot.Constants.ROW_WIDTH;

/**
 * connect to drone.
 */
public class ConnectRobotScreen extends Stage implements Screen  {
    private final Viewport viewPort;
    private final Texture backgroundTexture;
    private final Camera camera;
    private final Skin skin;
    private final ScreensProcessor screensProcessor;
    private final Drone drone;
    private final Stage stage;
    private SocketWriter socketWriter;
    private Thread writer;

    /**
     * Class constructor
     *
     * @param viewport common view port.
     * @param backgroundTexture common background
     * @param camera non connected camera
     * @param skin program layout
     * @param screensProcessor connect to other screens.
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

    /**
     * initialize and draw the screen, when menu first called, (set the stage.)
     */
    @Override
    public void show() {
        final TextField addrTextField = new TextField("", skin);
        final TextField portTxtField = new TextField("", skin);
        final TextField clientId = new TextField("", skin);
        final TextField atHash = new TextField("", skin);


            final TextButton connect = new TextButton("Connect", skin);
            connect.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Optional<Exception> whatHappened = Optional.empty();
                    try {
                        drone.setSocketClient(new SocketClient());
                        if (Strings.isNotEmpty(addrTextField.getText())) {
                             drone.getSocketClient().init(
                                addrTextField.getText(),
                                Integer.parseInt(portTxtField.getText()),
                                clientId.getText(),
                                atHash.getText(), drone);

                            // Create the writer socket.
                            socketWriter = new SocketWriter(drone);
                            writer = new Thread(socketWriter, "socketWriter");

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
        mainTable.add(addrTextField).width(ROW_WIDTH).height(ROW_HEIGHT).left();
        mainTable.add(portTxtField).height(ROW_HEIGHT).width(round((float) ROW_WIDTH / 3)).pad(10);
        mainTable.row();
        mainTable.add(new Label("Client Id: ", skin)).right().width(ROW_WIDTH).height(ROW_HEIGHT).pad(10);
        mainTable.add(clientId).width(ROW_WIDTH).height(ROW_HEIGHT).left();
        mainTable.row();
        mainTable.add(new Label("At Hash: ", skin)).right().width(ROW_WIDTH).height(ROW_HEIGHT).pad(10);
        mainTable.add(atHash).width(ROW_WIDTH).height(ROW_HEIGHT).left();

        final Table buttonTable = new Table();
        buttonTable.setFillParent(true);
        buttonTable.setY(-200);
        buttonTable.add(connect).width(ROW_WIDTH).height(ROW_HEIGHT).pad(10);
        buttonTable.add(back).width(ROW_WIDTH).height(ROW_HEIGHT);

        addActor(mainTable);
        addActor(buttonTable);
        Gdx.input.setInputProcessor(this);
    }

    /**
     * called each time the screen is rendered.
     *
     * @param v ignored only included for interface compliance.
     */
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

    /**
     * Resizes the screen to dimensions X, and Y
     *
     * @param width viewable width
     * @param height viewable height
     */
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
