package server;

import javax.validation.MessageInterpolator;
import java.util.Locale;

public class ValidationInterpolator implements MessageInterpolator {

    private final MessageInterpolator defaultInterpolator;

    public ValidationInterpolator(MessageInterpolator defaultInterpolator) {
        this.defaultInterpolator = defaultInterpolator;
    }

    @Override
    public String interpolate(String messageTemplate, Context context) {
        return null;
    }

    @Override
    public String interpolate(String messageTemplate, Context context, Locale locale) {
        return null;
    }
}
