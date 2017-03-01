/**
 * Created by Trung on 2/28/2017.
 */
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class MQTTSample {
    private static final String FILENAME = "C:\\Users\\hieut\\Desktop\\Test.txt";
    public static void main(String[] args) throws IOException {
        BufferedReader br = null;
        FileReader fr = null;
        String topic = "Time";
        fr = new FileReader(FILENAME);
        br = new BufferedReader(fr);
        br = new BufferedReader(new FileReader(FILENAME));
        while (true) {
            String content = br.readLine();
            if (Objects.equals(content, null)) {
                System.exit(0);
                break;
            }
            int qos = 1;
            String broker = "tcp://m10.cloudmqtt.com:10203";

            //MQTT client id to use for the device. "" will generate a client id automatically
            String clientId = "";

            MemoryPersistence persistence = new MemoryPersistence();
            try {
                MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
                mqttClient.setCallback(new MqttCallback() {
                    public void messageArrived(String topic, MqttMessage msg)
                            throws Exception {
                        System.out.println("Received:" + topic);
                        System.out.println("Received:" + new String(msg.getPayload()));
                    }

                    public void deliveryComplete(IMqttDeliveryToken arg0) {
                        System.out.println("Delivery complete");
                    }

                    public void connectionLost(Throwable arg0) {
                        // TODO Auto-generated method stub
                    }
                });

                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                connOpts.setUserName("rqsiijsp");
                connOpts.setPassword(new char[]{'w', '3', 'D', 'd', 'H', 'P', 'f', '0', 'w', 'A', 'j', '4'});
                mqttClient.connect(connOpts);
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);
                System.out.println("Publish message: " + message);
                mqttClient.subscribe(topic, qos);
                mqttClient.publish(topic, message);
                mqttClient.getTopic(topic);
                mqttClient.disconnect();


            } catch (MqttException me) {
                System.out.println("reason " + me.getReasonCode());
                System.out.println("msg " + me.getMessage());
                System.out.println("loc " + me.getLocalizedMessage());
                System.out.println("cause " + me.getCause());
                System.out.println("excep " + me);
                me.printStackTrace();
            }
        }
    }
}