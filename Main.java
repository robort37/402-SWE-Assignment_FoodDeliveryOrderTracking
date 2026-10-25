import java.util.ArrayList;
import java.util.List;

// Watcher Interface
interface Watcher {
    void update(String status);
}

// SMS Notification for Customer
class SMSNotification implements Watcher {
    private String customerName;

    public SMSNotification(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public void update(String status) {
        System.out.println("  SMS to " + customerName + ": your order is now " + status);
    }
}

// Kitchen Screen
class KitchenScreen implements Watcher {
    @Override
    public void update(String status) {
        System.out.println("  Kitchen screen: " + status);
    }
}

// Order Class (Subject)
class Order {
    private String status;
    private List<Watcher> watchers = new ArrayList<>();

    public void addWatcher(Watcher watcher) {
        watchers.add(watcher);
    }

    public void removeWatcher(Watcher watcher) {
        watchers.remove(watcher);
    }

    public void setStatus(String status) {
        this.status = status;
        System.out.println("Order is now: " + status);
        notifyWatchers();

        // Requirement: When the order is delivered, the kitchen is not notified anymore
        if ("Delivered".equals(status)) {
            watchers.removeIf(watcher -> watcher instanceof KitchenScreen);
        }
    }

    private void notifyWatchers() {
        for (Watcher watcher : new ArrayList<>(watchers)) {
            watcher.update(status);
        }
    }
}

// Main Execution
public class Main {
    public static void main(String[] args) {
        Order order = new Order();

        Watcher smsWatcher = new SMSNotification("Rahim");
        Watcher kitchenWatcher = new KitchenScreen();

        order.addWatcher(smsWatcher);
        order.addWatcher(kitchenWatcher);

        order.setStatus("Cooking");
        System.out.println();
        order.setStatus("Out for delivery");
        System.out.println();
        order.setStatus("Delivered");
    }
}
