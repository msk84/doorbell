// Register a Service Worker.
navigator.serviceWorker.register('./webpush-service-worker.js')
.then(() => {
    console.debug("Successfully registered web push service worker.")
})
.catch(() => {
    console.error("Failed to register web push service worker.")
});

function deleteEventHistory() {
    fetch('/api/eventhistory', {
        method: 'DELETE',
    })
    .then(() => {
        console.log("Cleaned event history.");
        location.reload();
    });
}