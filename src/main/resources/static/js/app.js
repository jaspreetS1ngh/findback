const reportForm = document.getElementById("report-form");
const saveButton = document.getElementById("save-report-button");
const reportMessage = document.getElementById("report-message");
const lostReportIdInput = document.getElementById("lost-report-id");

reportForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const formData = new FormData(reportForm);

    const request = {
        type: formData.get("type"),
        category: formData.get("category"),
        color: formData.get("color").trim() || null,
        location: formData.get("location"),
        description: formData.get("description").trim(),
        eventDate: formData.get("eventDate")
    };

    saveButton.disabled = true;
    saveButton.textContent = "Saving...";
    reportMessage.className = "mt-3 text-break text-secondary";
    reportMessage.textContent = "Saving your report...";

    try {
        const response = await fetch("/api/reports", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(request)
        });

        const data = await response.json();

        if (!response.ok) {
            reportMessage.className = "mt-3 text-break text-danger";
            reportMessage.textContent =
                data.message || `Could not save report (${response.status}).`;
            return;
        }

        reportMessage.className = "mt-3 text-break text-success";
        reportMessage.textContent = `Report saved! ID: ${data.id}`;

        // Prepare the matching form when a lost report is created.
        if (data.type === "LOST") {
            lostReportIdInput.value = data.id;
        }

        reportForm.reset();
    } catch (error) {
        reportMessage.className = "mt-3 text-break text-danger";
        reportMessage.textContent =
            "Could not confirm the save. The server may have received it; check before retrying.";
        console.error("Report submission failed:", error);
    } finally {
        saveButton.disabled = false;
        saveButton.textContent = "Save report";
    }
});


saveButton.disabled = false;