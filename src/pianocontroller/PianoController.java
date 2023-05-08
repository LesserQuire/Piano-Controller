package pianocontroller;

import java.awt.AWTException;

public class PianoController 
{
    public static void main(String[] args) throws AWTException 
    {
        // Change this to true to put it in typing mode
        new MidiHandler(false);
    }

}
