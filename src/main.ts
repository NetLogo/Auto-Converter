import express, { type Request, type Response } from "express";
import fs from "fs";
import multer from "multer";

const host = "localhost";
const port = 4242;

fs.rmSync("uploads", {
  recursive: true,
  force: true
});

const app = express();
const mult = multer({
  dest: "uploads"
});

app.post("/convert", mult.single("model"), (request: Request, response: Response) => {
  if (request.file) {
    console.log(request.file.path);

    response.sendFile(fs.realpathSync(request.file.path));
  } else {
    response.send("No file uploaded.");
  }
});

app.listen(port, host, () => {
  console.log(`Service running at ${host}:${port}`);
});
