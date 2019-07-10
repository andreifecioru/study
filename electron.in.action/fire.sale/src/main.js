import * as path from "path";
import * as url from "url";
import { app, BrowserWindow, dialog } from "electron";

const fs = require("fs-extra");

const mode = process.env.NODE_ENV;
const noop = () => {};

// store the app's windows in a global set
let windows = new Set();

const reloadOnChange = win => {
  // reload on change only in 'dev' mode
  if (mode !== "development") {
    return { close: noop };
  }

  // watch both the 'dist' and 'static' dirs
  const watcher = require("chokidar").watch(
    [path.join(__dirname, "**"), path.join(__dirname, "..", "**")],
    { ignoreInitial: true }
  );
  watcher.on("change", () => win.reload());

  return watcher;
};

const createWindow = () => {
  // shift the initial position of the new window
  // relative to the focused window (if it exists)
  let x, y;
  const focusedWindow = BrowserWindow.getFocusedWindow();
  if (focusedWindow) {
    const [focusedX, focusedY] = focusedWindow.getPosition();
    x = focusedX + 10;
    y = focusedY + 10;
  }

  const newWindow = new BrowserWindow({
    x, y,
    // don't show the window contents until we're ready
    show: false,
    // starting with electron 5.0 we need to enable this explicitly
    webPreferences: { nodeIntegration: true }
  });

  // load the content
  newWindow.loadURL(
    url.format({
      pathname: path.join(__dirname, "../static/index.html"),
      protocol: "file:",
      slashes: true
    })
  );

  newWindow.once("ready-to-show", () => {
    // show dev tools in 'dev' mode
    if (mode === "development") {
      // newWindow.webContents.openDevTools();
      // newWindow.maximize();
    }
    
    // we're now ready to show the window
    newWindow.show();
  });

  const watcher = reloadOnChange(newWindow);

  // clean-up activities
  newWindow.on("closed", () => {
    windows.delete(newWindow);
    watcher.close();
  });

  // add the window to the global set
  windows.add(newWindow);

  return newWindow;
};

// load files from local disk
const loadMarkdownFile = (targetWindow) => {
  const fileList = dialog.showOpenDialog(targetWindow, {
    properties: ["openFile"],
    filters: [{ name: "Markdown files", extensions: ["md", "markdown"] }]
  });

  if (!fileList) {
    return;
  }

  const fileName = fileList[0];
  console.log(`Opening file: ${fileName}`);
  fs.readFile(fileName)
    .then(content => content.toString())
    .then(content =>
      // send the file path/content on a dedicated channel 
      // to the renderer process
      targetWindow.webContents.send("open-file", fileName, content)
    )
    .catch(error => console.error(`ERROR: ${fileName}, ${error.message}`));
};

app.on("ready", createWindow);

app.on("window-all-closed", () => {
  // on OS-X we're not quitting when all windows are closed
  if (process.platform !== "darwin") {
    app.quit();
  }
});

app.on("activate", (event, hasVisibleWindows) => {
  // on OS-X we're relaunching on activation
  if (!hasVisibleWindows) {
    createWindow();
  }
});

// export functionality to be accessed by the renderer process
exports.loadMarkdownFile = loadMarkdownFile;
exports.createWindow = createWindow