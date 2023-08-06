function triggerTestRing() {
    fetch("./api/test/event", {
        method: 'POST'
    })
        .then(() => console.log("Successfully triggered test ring."))
        .catch(() => console.error("Failed to trigger test ring."));
}

function triggerTestOpenDoor() {
    fetch("./api/test/open", {
        method: 'POST'
    })
        .then(() => console.log("Successfully triggered test open."))
        .catch(() => console.error("Failed to trigger test open."));
}

function triggerTestMail() {
    fetch("./api/test/mail", {
        method: 'POST'
    })
        .then(response => {
            if(response.ok) {
                console.log("Successfully triggered test mail.");
            }
            else {
                console.error("Error triggering request: " + response.status);
            }
        })
        .catch(() => console.error("Failed to trigger test mail."));
}

function triggerWebNotification() {
    fetch('./api/webpush/notify', {
        method: 'post',
        headers: {
            'Content-type': 'application/json'
        }
    })
        .then(() => console.log("Successfully triggered web notification."))
        .catch(() => console.error("Failed to trigger web notification."));
}