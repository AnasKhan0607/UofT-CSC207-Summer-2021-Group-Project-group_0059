package Entity;

/**
 * The entity which represents Templates and contains the necessary attributes as well as getters and setters.
 */

public class Template {
    private String templatename; // name of this template
    private String description; // description of the template
    private int numchoice; // max number of choices

    /**
     * The constructor when a template is first called.
     * @param templatename The name that will be set for this template.
     */

    public Template (String templatename) {
        this.templatename = templatename;
    }

    /**
     * Getter for the name of this template
     * @return the name of the template
     */

    public String getTemplatename() {
        return this.templatename;
    }

    /**
     * Setter for the name of the template
     * @param templatename the new name of the template
     */

    public void setTemplatename (String templatename) {
        this.templatename = templatename;
    }

    /**
     * Setter for the template's description
     * @param description The description to set for this template.
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the description of the template.
     * @return the description of the template
     */

    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for the maximum number of choices this template can have.
     * @param numchoice The max number of choices to set.
     */

    public void setNumchoice(int numchoice) {
        this.numchoice = numchoice;
    }

    /**
     * Getter for the maximum number of choices that this template can have.
     * @return The maximum number of choices.
     */

    public int getNumchoice() {
        return this.numchoice;
    }
}