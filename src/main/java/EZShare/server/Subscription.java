package EZShare.server;

import EZShare.message.Host;
import EZShare.message.SubscribeMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Encapsulation of subscriptions
 *
 * @author zenanz
 */
public class Subscription {
    private ConcurrentHashMap<SubscribeMessage,Integer> subscribeMessage = new ConcurrentHashMap<>();
    private String origin;
    private Host target;

    public Subscription(SubscribeMessage subscribeMessage, String origin, Host target) {
        this.origin = origin;
        this.target = target;
        this.subscribeMessage.put(subscribeMessage,0);
    }

    public Subscription(SubscribeMessage subscribeMessage, String origin) {
        this.origin = origin;
        this.subscribeMessage.put(subscribeMessage,0);
    }


    public void addSubscribeMessage(SubscribeMessage subscribeMessage){
        this.subscribeMessage.put(subscribeMessage,0);
    }

    public void removeSubscribeMessage(String id){
        for (Map.Entry<SubscribeMessage,Integer> entry: this.subscribeMessage.entrySet()) {
           if (entry.getKey().getId().equals(id)){
               this.subscribeMessage.remove(entry.getKey());
               // Personally I think the spec is problematic
               return;
           }
        }
    }

    public ConcurrentHashMap<SubscribeMessage,Integer> getSubscribeMessage() {
        return this.subscribeMessage;
    }

    public void addResult(String id) {
        for (Map.Entry<SubscribeMessage,Integer> entry: this.subscribeMessage.entrySet()) {
            if (entry.getKey().getId().equals(id)){
                int size = entry.getValue()+1;
                this.subscribeMessage.put(entry.getKey(),size);
            }
        }
    }

    public int getResultSize(String id){
        for (Map.Entry<SubscribeMessage,Integer> entry: this.subscribeMessage.entrySet()) {
            if (entry.getKey().getId().equals(id)){
                return entry.getValue();
            }
        }
        return 0;
    }

    public Host getTarget() {
        return target;
    }

    public String getOrigin() {
        return origin;
    }


}
