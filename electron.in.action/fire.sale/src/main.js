import * as path from "path";
import * as url from "url";
import { app, BrowserWindow } from "electron";

const fs = require("fs-extra");

const mode = process.env.NODE_ENV;
const noop = () => {};

// keep a global reference to the main window to avoid garbage collection
let mainWindow = null;

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

const launchApp = () => {
  mainWindow = new BrowserWindow({
    // don't show the window contents until we're ready
    show: false,
    // starting with electron 5.0 we need to enable this explicitly
    webPreferences: { nodeIntegration: true }
  });

  // load the content
  mainWindow.loadURL(
    url.format({
      pathname: path.join(__dirname, "../static/index.html"),
      protocol: "file:",
      slashes: true
    })
  );

  mainWindow.once("ready-to-show", () => {
    // show dev tools in 'dev' mode

    if (mode === "development") {
      mainWindow.webContents.openDevTools();
      mainWindow.maximize();
    }
    // we're now ready to show the window
    mainWindow.show();
  });

  const watcher = reloadOnChange(mainWindow);

  // clean-up activities
  mainWindow.on("closed", () => {
    mainWindow = null;
    watcher.close();
  });
};

// load files from local disk
const loadMarkdownFile = () => {
  const fileList = dialog.showOpenDialog(mainWindow, {
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
      mainWindow.webContents.send("open-file", fileName, content)
    )
    .catch(error => console.error(`ERROR: ${fileName}, ${error.message}`));
};

app.on("ready", launchApp);

app.on("window-all-closed", () => {
  // on OS-X we're not quitting when all windows are closed
  if (process.platform !== "darwin") {
    app.quit();
  }
});

app.on("activate", () => {
  // on OS-X we're relaunching on activation
  if (mainWindow === null) {
    launchApp();
  }
});

// export functionality to be accessed by the renderer process
exports.loadMarkdownFile = loadMarkdownFile;
