package org.ryderrobot.models.protocols.rrp;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import java.util.Map;

public class RrpCurator {

    public RrpEvent curate(String j) {
        RrpRequest request = (new Json()).fromJson(RrpRequest.class, j);
        return curate(request);
    }

    public RrpEvent curate(RrpRequest request) {
        RrpEvent event = new RrpEvent(RrpCommands.MSP_NONE);

        if (request.getPayload() != null) {
            Map<String, String> payload = request.getPayload();
            switch (request.getCommand()) {
                case MSP_ERROR:
                    String message = payload.get("message");
                    event = new RrpEvent<>(RrpCommands.MSP_ERROR, new MspErrorPayload(message));
                    break;
                case MSP_IDENT:
                    event = new RrpEvent<>(RrpCommands.MSP_IDENT, new MspIdentPayload(
                        Integer.parseInt(request.getPayload().get("version")),
                        MultiType.valueOf(request.getPayload().get("multitype")),
                        MspVersion.valueOf(request.getPayload().get("msp_version")),
                        Integer.parseInt(request.getPayload().get("capability"))
                    ));
                    break;
                case MSP_STATUS:
                    event = new RrpEvent<>(RrpCommands.MSP_STATUS, new MspStatus(
                        Integer.parseInt(request.getPayload().get("cycletime")),
                        Integer.parseInt(request.getPayload().get("i2c_errors_count")),
                        Integer.parseInt(request.getPayload().get("sensor")),
                        Integer.parseInt(request.getPayload().get("flag")),
                        Integer.parseInt(request.getPayload().get("current_set"))
                    ));
            }
        } else {
            if (request.getCommand() == RrpCommands.MSP_ERROR) {
                event = new RrpEvent<>(RrpCommands.MSP_ERROR);
            }
        }
        return event;
    }

    public String toJson(RrpEvent event) {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);

        JsonValue jsonObject = new JsonValue(JsonValue.ValueType.object);
        jsonObject.addChild("command", new JsonValue(event.getCommand().name()));
        if (event.hasPayload()) {
            String s = json.toJson(event.getPayload());
            jsonObject.addChild("payload", new JsonValue(s));
        }
        return jsonObject.toJson(JsonWriter.OutputType.json);
    }
}
