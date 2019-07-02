const { app, BrowserWindow } = require("electron");

let mainWindow = null;

app.on("ready", () => {
  console.log("Hello World!");
  mainWindow = new BrowserWindow({
    webPreferences: {
      nodeIntegration: true
    }
  });

  mainWindow.webContents.loadFile('index.html');
});
