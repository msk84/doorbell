function triggerTestRing() {
    fetch("/api/test/event", {
        method: 'POST'
    })
        .then(() => console.log("Successfully triggered test ring."))
        .catch(() => console.error("Failed to trigger test ring."));
}

function triggerTestOpenDoor() {
    fetch("/api/test/open", {
        method: 'POST'
    })
        .then(() => console.log("Successfully triggered test open."))
        .catch(() => console.error("Failed to trigger test open."));
}

function triggerTestMail() {
    fetch("/api/test/mail", {
        method: 'POST'
    })
        .then(() => console.log("Successfully triggered test mail."))
        .catch(() => console.error("Failed to trigger test mail."));
}