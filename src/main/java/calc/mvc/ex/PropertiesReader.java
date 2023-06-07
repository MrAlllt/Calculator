package calc.mvc.ex;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesReader extends Properties {
    private String file;
    private boolean error;

    public PropertiesReader(String file) {
        try {
            this.file = Objects.requireNonNull(getClass().getResource(file)).getFile();
            FileInputStream input = new FileInputStream(this.file);

            load(input);
            input.close();
        } catch (Exception e) {
            error = true;
        }
    }

    public boolean isError() {
        return error;
    }

    public void saveProperties() {
        try {
            FileOutputStream output = new FileOutputStream(file);
            store(output, null);
            output.close();
        } catch (Exception e) {
            error = true;
        }
    }
}