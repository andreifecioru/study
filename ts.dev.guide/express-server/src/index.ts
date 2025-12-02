import express from "express";
import { router } from "./routes/loginRoutes.js";
import bodyParser from "body-parser";
import cookieSession from "cookie-session";

const app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(cookieSession({ keys: ['random-string-here'] }));
app.use(router);

app.listen(3000, () => {
  console.log("Server started on port 3000");
});
