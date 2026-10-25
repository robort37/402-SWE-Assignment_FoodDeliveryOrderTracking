class Order:
    def __init__(self):
        self._status = None
        self._watchers = []

    def add_watcher(self, watcher):
        self._watchers.append(watcher)

    def remove_watcher(self, watcher):
        if watcher in self._watchers:
            self._watchers.remove(watcher)

    def set_status(self, status):
        self._status = status
        print(f"Order is now: {status}")
        self._notify()

        # Requirement: When the order is delivered, the kitchen is not notified
        if status == "Delivered":
            for watcher in list(self._watchers):
                if isinstance(watcher, KitchenScreen):
                    self.remove_watcher(watcher)

    def _notify(self):
        for watcher in list(self._watchers):
            watcher.update(self._status)


class SMSNotification:
    def __init__(self, name):
        self.name = name

    def update(self, status):
        print(f"  SMS to {self.name}: your order is now {status}")


class KitchenScreen:
    def update(self, status):
        print(f"  Kitchen screen: {status}")


# --- Execution ---
if __name__ == "__main__":
    order = Order()

    sms_watcher = SMSNotification("Rahim")
    kitchen_watcher = KitchenScreen()

    # Adding watchers
    order.add_watcher(sms_watcher)
    order.add_watcher(kitchen_watcher)

    # Status updates
    order.set_status("Cooking")
    print()
    order.set_status("Out for delivery")
    print()
    order.set_status("Delivered")
