package bluetooth;


//import gnu.io.*;
import jssc.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

public class BTConnection extends Throwable{

    private SerialPort serialPort;

    private int currentPosx;
    private int currentPosy;
    private int homePosx;
    private int homePosy;
    private String comPort;
    private String name;
    private int direction = 2;

    public BTConnection(String name, String port, int Posx, int Posy)
    {
        this.homePosx = Posx;
        this.homePosy = Posy;
        this.currentPosx = Posx;
        this.currentPosy = Posy;
        this.name = name;
        this.comPort = port;

        serialPort = new SerialPort(comPort);
        try {
            serialPort.openPort();//Open serial port
            serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
            sendData(253);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendData(currentPosx);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendData(currentPosy);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendData(253);
        }
        catch (SerialPortException ex) {
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void setName(String name) {
        this.name = name;}

    public void setHome(int xPos, int yPos) {
        this.homePosx = xPos;
        this.homePosy = yPos;}

    public void setCurrentPos(int posX, int posY){
        this.currentPosx = posX;
        this.currentPosy = posY;}

    public void setCurrentPos(int posX, int posY, int dir)
    {
        this.currentPosx = posX;
        this.currentPosy = posY;
        this.direction = dir;
    }

    public void setDirection(int dir)
    {
        this.direction = dir;
    }

    public int getDirection()
    {
        return this.direction;
    }

    public int getCurrentPosy() {
        return currentPosy;
    }

    public int getCurrentPosx() {
        return currentPosx;
    }

    public String getName(){
        return this.name;}

    public int getHomex() {
        return this.homePosx;}

    public int getHomey() {
        return this.homePosy;}

    public String getPort(){
        return comPort;}

    public void sendData(int data) {
            try {
                serialPort.writeByte((byte)data);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public int[] receiveData(int bytes)
    {
            try {
                byte data[] = serialPort.readBytes(bytes);
                    int[] datai = new int[data.length];
                    for(int i = 0; i < bytes; i++) {
                        datai[i] = data[i] & 0xff;
                    }
                    System.out.println(datai);
                    return datai;
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        return null;
    }

    public void disconnect() {
        if (serialPort != null) {
            try {
                serialPort.closePort();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }
}