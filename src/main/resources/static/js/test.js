function ring(doorbell) {
    console.log("Ringing bell number  " + doorbell + " for test.s");
    $.ajax({
        type: "GET",
        url: "/api/test/event",
        success: function (value) {
            console.log("Ring, ring.");
        },
        error: function (errMsg) {
            console.log("Failed to ring doorbell " + doorbell + " for test. Error: " + errMsg);
        }
    });
}