function subscribe() {
    console.log("Let's subscribe...");
    navigator.serviceWorker.ready
        .then(function (registration) {
            // Use the PushManager to get the user's subscription to the push service.
            return registration.pushManager.getSubscription()
                .then(async function (subscription) {
                    // If a subscription was found, return it.
                    if (subscription) {
                        console.debug("Existing subscription found.");
                        return subscription;
                    }

                    const response = await fetch('./api/webpush/publicKey');
                    const vapidPublicKey = await response.text();

                    // Otherwise, subscribe the user (userVisibleOnly allows to specify that we don't plan to
                    // send notifications that don't have a visible effect for the user).
                    return registration.pushManager.subscribe({
                        userVisibleOnly: true,
                        applicationServerKey: vapidPublicKey
                    });
                });
        }).then(function (subscription) {
            console.debug("The subscription: " + JSON.stringify(subscription));

            fetch('./api/webpush/subscribe', {
                method: 'post',
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify(subscription),
            });
    })
    .catch((error) => {
        console.error("Something went wrong: " + error);
    });
}

function unsubscribe() {
    console.log("Unsubscribe called but not implemented...");
}