package bernard.core;

/**
 * Handles input and output for the Bernard Personal Assistant
 */
class GuiUi extends Ui {
    private String output = "";

    /**
     * Print output for user
     *
     * @param line String output to be printed
     */
    public void outputLine(String line) {
        output += line + "\n";
    }

    /**
     * Reset output buffer
     */
    public void resetOutput() {
        output = "";
    }

    /**
     * Retrieve output buffer of Ui
     *
     * @return Output buffer of Ui
     */
    @Override
    public String toString() {
        return output;
    }
}
