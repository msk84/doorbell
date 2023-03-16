// Register event listener for the 'push' event.
self.addEventListener('push', function(event) {
    let data = event.data?.json() ?? {};

    // Keep the service worker alive until the notification is created.
    event.waitUntil(
        self.registration.showNotification(data.title, {
            body: data.message,
        })
    );
});
