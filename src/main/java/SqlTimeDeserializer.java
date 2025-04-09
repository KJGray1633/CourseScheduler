import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Time;

public class SqlTimeDeserializer extends JsonDeserializer<Time> {
    @Override
    public Time deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String timeString = p.getText();
        return Time.valueOf(timeString); // Converts "HH:mm:ss" to java.sql.Time
    }
}
