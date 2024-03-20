package pianocontroller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MidiHandler
{
private boolean typing;
ArrayList<Integer> keysPressed;

    public MidiHandler(boolean typing) throws AWTException
    {
        this.typing = typing;
        keysPressed = new ArrayList<>();
        MidiDevice device;
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            try {
                device = MidiSystem.getMidiDevice(info);
                //does the device have any transmitters?
                //if it does, add it to the device list
                System.out.println(info);
                //get all transmitters
                List<Transmitter> transmitters = device.getTransmitters();
                //and for each transmitter
                for(int j = 0; j<transmitters.size();j++) {
                    //create a new receiver
                    transmitters.get(j).setReceiver(
                            //using my own MidiInputReceiver
                            new MidiInputReceiver(device.getDeviceInfo().toString())
                    );
                }   Transmitter trans = device.getTransmitter();
                trans.setReceiver(new MidiInputReceiver(device.getDeviceInfo().toString()));
                //open each device
                device.open();
                //if code gets this far without throwing an exception
                //print a success message
                System.out.println(device.getDeviceInfo()+" Was Opened");
            }catch (MidiUnavailableException e) {}
        }


    }
    
    public MidiHandler() throws AWTException
    {
        this(true);
    }
    
public class MidiInputReceiver implements Receiver {
    public String name;
    public MidiInputReceiver(String name) {
        this.name = name;
    }
    @Override
    public void send(MidiMessage msg, long timeStamp) {
        int out = msg.getMessage()[1];
        try {
            output(out);
        } catch (AWTException ex) {
            Logger.getLogger(MidiHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void close() {}
    }
    
    public void output(int in) throws AWTException {
        Robot robot = new Robot();
        System.out.println(in);
        //Comment out from here down to figure out which keys have which numbers
        System.out.println(keysPressed.contains(in));
        if(!keysPressed.contains(in)){
            keysPressed.add(in);
            if(typing)
            {
                if(in > 64 && in < 91)robot.keyPress(in);
                switch(in){
                    case 112: robot.keyPress(KeyEvent.VK_BACK_SPACE);break;
                    case 113: robot.keyPress(KeyEvent.VK_SPACE); break;
                    case 114: robot.keyPress(KeyEvent.VK_PERIOD); break;
                    case 115: robot.keyPress(KeyEvent.VK_SHIFT); break;
                }
            }else{
                switch(in){
                    case 75: robot.keyPress(KeyEvent.VK_ESCAPE); break;
                    case 68: robot.keyPress(KeyEvent.VK_W); break;
                    case 65: robot.keyPress(KeyEvent.VK_A); break;
                    case 71: robot.keyPress(KeyEvent.VK_D); break;
                    case 67: robot.keyPress(KeyEvent.VK_S); break;
                    case 69: robot.keyPress(KeyEvent.VK_S); break;
                    case 88: robot.keyPress(KeyEvent.VK_C); break;
                    case 84: robot.keyPress(KeyEvent.VK_X); break;
                    case 81: robot.keyPress(KeyEvent.VK_Z); break;
                }
            }
        }else{
            keysPressed.remove(keysPressed.indexOf(in));
            
            if(typing)
            {
                if(in > 64 && in < 91)robot.keyRelease(in);
                switch(in){
                    case 112: robot.keyRelease(KeyEvent.VK_BACK_SPACE);break;
                    case 113: robot.keyRelease(KeyEvent.VK_SPACE); break;
                    case 114: robot.keyRelease(KeyEvent.VK_PERIOD); break;
                    case 115: robot.keyRelease(KeyEvent.VK_SHIFT); break;
                }
            }else{
                switch(in){
                    case 75: robot.keyRelease(KeyEvent.VK_ESCAPE); break;
                    case 68: robot.keyRelease(KeyEvent.VK_W); break;
                    case 65: robot.keyRelease(KeyEvent.VK_A); break;
                    case 71: robot.keyRelease(KeyEvent.VK_D); break;
                    case 67: robot.keyRelease(KeyEvent.VK_S); break;
                    case 69: robot.keyRelease(KeyEvent.VK_S); break;
                    case 88: robot.keyRelease(KeyEvent.VK_C); break;
                    case 84: robot.keyRelease(KeyEvent.VK_X); break;
                    case 81: robot.keyRelease(KeyEvent.VK_Z); break;
                }
            }
        }
    }
}