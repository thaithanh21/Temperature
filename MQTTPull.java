import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.awt.*;
import java.lang.String;
import java.util.*;
import java.util.List;

public class MQTTPull implements MqttCallback {
    public MQTTPull() {
    }

    public static void main(String[] args) {
        new MQTTPull().doDemo();

    }
    public void doDemo() {
        try {
            String broker       = "tcp://m10.cloudmqtt.com:10203";
            //MQTT client id to use for the device. "" will generate a client id automatically
            String clientId     = "1";
            MemoryPersistence persistence = new MemoryPersistence();
            MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("phong");
            connOpts.setPassword(new char[]{'1', '2', '3', '4'});

            /*Pulling*/
            mqttClient.connect(connOpts);
            mqttClient.setCallback(this);
            mqttClient.subscribe("Tester", 1);
            mqttClient.subscribe("Time", 2);
            MqttMessage message = new MqttMessage();
            message.setPayload("This is the temperature".getBytes());
            //mqttClient.publish("Time", message);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub

    }
    List<Integer> mylist = new ArrayList<Integer>();
    @Override

    public void messageArrived(String topic, MqttMessage message)
            throws Exception {
        float average =0;
        int count = 0;
        int max;
        int min;
        String numberOnly= message.toString().replaceAll("[^0-9]", "");
        mylist.add(Integer.parseInt(String.valueOf(numberOnly)));
        System.out.println(message);
        max = mylist.get(0);
        min = mylist.get(0);
        for (int i =0; i< mylist.size(); i++){
            if(max < mylist.get(i)){
                max = mylist.get(i);
            }
            if(min > mylist.get(i)){
                min = mylist.get(i);
            }
            average += mylist.get(i);
            count ++;
        }
        System.out.println("Temperature from " + topic);
        System.out.println("Average: "+ average/count);
        System.out.println("Maximum temperature: " + max + "\u00B0");
        System.out.println("Minimum temperature: " + min + "\u00B0");
        }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub

    }
}
