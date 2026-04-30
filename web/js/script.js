function handleAdd() {
  let id = document.getElementById("id").value.trim();
  let msg = document.getElementById("msg").value.trim();
  if (id === "" || msg === "") {
    showPopup("Please enter both Crash ID and Message to log.", "info");
    return false;
  }
  if (!msg.includes(":")) {
    showPopup("Message missing format. Must contain ':' separating TYPE and message (e.g. NPE:NullPointer).", "error");
    return false;
  }
  return true;
}

function showPopup(message, type = "info") {
    const overlay = document.getElementById("popupOverlay");
    const msgBox = document.getElementById("popupMessage");
    const title = document.getElementById("popupTitle");
    const popupBox = document.querySelector(".popup-box");
    const iconContainer = document.getElementById("popupIcon");
    popupBox.classList.remove("success-theme", "error-theme", "info-theme");
    msgBox.innerText = message;
    // SVG Icons
    const successIcon = `<svg viewBox="0 0 24 24" fill="none" class="w-6 h-6" stroke="currentColor" stroke-width="2"><path stroke-linecap="round" stroke-linejoin="round" d="M5 13l4 4L19 7"></path></svg>`;
    const errorIcon = `<svg viewBox="0 0 24 24" fill="none" class="w-6 h-6" stroke="currentColor" stroke-width="2"><path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12"></path></svg>`;
    const infoIcon = `<svg viewBox="0 0 24 24" fill="none" class="w-6 h-6" stroke="currentColor" stroke-width="2"><path stroke-linecap="round" stroke-linejoin="round" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>`;
    if (type === "success") {
        title.innerText = "Success";
        popupBox.classList.add("success-theme");
        iconContainer.innerHTML = successIcon;
    } else if (type === "error") {
        title.innerText = "Error";
        popupBox.classList.add("error-theme");
        iconContainer.innerHTML = errorIcon;
    } else {
        title.innerText = "Information";
        popupBox.classList.add("info-theme");
        iconContainer.innerHTML = infoIcon;
    }
    overlay.style.display = "flex";
    setTimeout(() => {
        overlay.classList.add("show");
    }, 10);
}

function closePopup() {
    const overlay = document.getElementById("popupOverlay");
    overlay.classList.remove("show");
    
    setTimeout(() => {
        overlay.style.display = "none";
    }, 300);
}

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".log-form");
    if (form) {
        form.addEventListener("submit", (e) => {
            e.preventDefault(); // Prevent full page reload
            
            const submitter = e.submitter;
            const action = submitter ? submitter.getAttribute("formaction") : "";
            
            const formData = new URLSearchParams(new FormData(form));
            
            fetch(action, {
                method: "POST",
                body: formData,
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                }
            })
            .then(response => response.text())
            .then(html => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, "text/html");
                // Update terminal output
                const newTerminalOutput = doc.querySelector(".terminal-output");
                if (newTerminalOutput) {
                    document.querySelector(".terminal-output").value = newTerminalOutput.value;
                }
                // Handle popup message
                const msgElement = doc.getElementById("server-message");
                if (msgElement) {
                    const currentMsgElement = document.getElementById("server-message");
                    if (currentMsgElement) currentMsgElement.innerText = msgElement.innerText;
                    const msg = msgElement.innerText;
                    if (msg && msg.trim() !== "") {
                        const isSuccess = msg.toLowerCase().includes("success");
                        showPopup(msg, isSuccess ? "success" : "error");
                        // Clear form if a record was successfully added
                        if (action === "add" && isSuccess) {
                            form.reset();
                        }
                    }
                }
            })
            .catch(error => {
                console.error("Error submitting form:", error);
                showPopup("Failed to communicate with the server.", "error");
            });
        });
    }
});