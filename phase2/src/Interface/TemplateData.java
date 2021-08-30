package Interface;

import java.util.ArrayList;

/**
 * Interface for all of the Template data.
 */
public interface TemplateData {
    // expect template to return relevant info;
    // in this case, we want it to return choice number limit
    // after making the user choose a template
    ArrayList<Object> chooseTemplate ();
}
