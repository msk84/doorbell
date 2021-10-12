function ring(doorbell) {
    console.log("Ringing bell number " + doorbell + " for test.");
    $.ajax({
        type: "POST",
        url: "/api/test/event",
        success: function (value) {
            console.log("Ring, ring.");
        },
        error: function (errMsg) {
            console.log("Failed to ring doorbell " + doorbell + " for test. Error: " + errMsg);
        }
    });
}

function openDoor() {
    console.log("Open door");
    $.ajax({
        type: "POST",
        url: "/api/test/open",
        success: function (value) {
            console.log("Open the door.");
        },
        error: function (errMsg) {
            console.log("Failed to open the door. Error: " + errMsg);
        }
    });
}