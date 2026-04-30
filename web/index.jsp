<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Application Crash Logger</title>
    <!-- Google Fonts: Inter -->
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap"
      rel="stylesheet"
    />

    <link rel="stylesheet" href="web/css/styles.css" />
    <script src="web/js/script.js" defer></script>
  </head>

  <body>
    <div class="glass-bg-accent"></div>
    <div class="glass-bg-accent-2"></div>

    <main class="container glass-panel">
      <header class="app-header">
        <div class="logo-icon">
          <svg
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <path
              d="m21.73 18-8-14a2 2 0 0 0-3.48 0l-8 14A2 2 0 0 0 4 21h16a2 2 0 0 0 1.73-3Z"
            />
            <line x1="12" y1="9" x2="12" y2="13" />
            <line x1="12" y1="17" x2="12.01" y2="17" />
          </svg>
        </div>
        <h2>Application Crash Logger</h2>
        <p class="subtitle">System Event Monitor</p>
      </header>

      <form method="post" class="log-form" action="add">
        <div class="input-group">
          <div class="form-control">
            <input
              type="text"
              id="id"
              name="id"
              placeholder=" "
              autocomplete="off"
            />
            <label for="id">Crash ID</label>
          </div>

          <div class="form-control">
            <input
              type="text"
              id="msg"
              name="message"
              placeholder=" "
              autocomplete="off"
            />
            <label for="msg">Message (e.g. NPE:NullPointer)</label>
          </div>
        </div>

        <div class="buttons">
          <button
            type="submit"
            formaction="add"
            onclick="return handleAdd();"
            class="btn btn-primary"
          >
            <svg
              class="btn-icon"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
            >
              <path d="M12 5v14M5 12h14" />
            </svg>
            Add Record
          </button>
          <button type="submit" formaction="display" class="btn btn-secondary">
            <svg
              class="btn-icon"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            >
              <line x1="8" y1="6" x2="21" y2="6"></line>
              <line x1="8" y1="12" x2="21" y2="12"></line>
              <line x1="8" y1="18" x2="21" y2="18"></line>
              <line x1="3" y1="6" x2="3.01" y2="6"></line>
              <line x1="3" y1="12" x2="3.01" y2="12"></line>
              <line x1="3" y1="18" x2="3.01" y2="18"></line>
            </svg>
            Display
          </button>
          <button type="submit" formaction="filter" class="btn btn-secondary">
            <svg
              class="btn-icon"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
            >
              <polygon points="22 3 2 3 10 12.46 10 19 14 21 14 12.46 22 3" />
            </svg>
            Filter
          </button>
          <button type="submit" formaction="sort" class="btn btn-secondary">
            <svg
              class="btn-icon"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
            >
              <path d="m3 16 4 4 4-4" />
              <path d="M7 20V4" />
              <path d="m21 8-4-4-4 4" />
              <path d="M17 4v16" />
            </svg>
            Sort
          </button>
        </div>
      </form>

      <div class="terminal-wrapper">
        <div class="terminal-header">
          <span class="dot red"></span>
          <span class="dot yellow"></span>
          <span class="dot green"></span>
          <span class="terminal-title">System Console</span>
        </div>
        <textarea
          class="terminal-output"
          readonly
          spellcheck="false"
          placeholder="Waiting for output..."
        >
${logs}</textarea
        >
      </div>
    </main>

    <div id="popupOverlay" class="popup-overlay">
      <div class="popup-box glass-panel">
        <div class="popup-icon" id="popupIcon"></div>
        <h3 id="popupTitle" class="popup-title">Message</h3>
        <p id="popupMessage" class="popup-message"></p>
        <button onclick="closePopup()" class="btn btn-primary btn-block">
          Acknowledge
        </button>
      </div>
    </div>

    <div id="server-message" style="display: none">
      <%= request.getAttribute("popup") == null ? "" :
      request.getAttribute("popup") %>
    </div>
    <script>
      window.onload = function () {
        var msg = document.getElementById("server-message").innerText;
        if (msg && msg.trim() !== "") {
          showPopup(
            msg,
            msg.toLowerCase().includes("success") ? "success" : "error",
          );
        }
      };
    </script>
  </body>
</html>
