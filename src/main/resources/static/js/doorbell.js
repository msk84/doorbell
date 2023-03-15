// Register a Service Worker.
navigator.serviceWorker.register('./webpush-service-worker.js')
.then(() => {
    console.debug("Successfully registered webpush service worker.")
})
.catch(() => {
    console.error("Failed to register webpush service worker.")
});
