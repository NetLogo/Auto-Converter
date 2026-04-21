import { exec, type ExecException } from "child_process";
import express, { type Request, type Response } from "express";
import fs from "fs";
import multer, { diskStorage } from "multer";

const host = "127.0.0.1";
const port = 4242;

const app = express();
const mult = multer({
  storage: diskStorage({
    filename: (_: Request, file: Express.Multer.File, callback: (error: Error | null, filename: string) => any) => {
      callback(null, file.originalname);
    }
  })
});

app.post("/convert", mult.single("model"), (request: Request, response: Response) => {
  if (request.file) {
    const path: string = fs.realpathSync(request.file?.path) ?? "";
    const command: string = `java -cp "out/lib/*" org.nlogo.convert.AutoConverter "${path}"`;

    exec(command, (error: ExecException | null, stdout: string, stderr: string) => {
      if (error) {
        console.error(stderr);

        response.status(500).send();
      } else {
        response.sendFile(fs.realpathSync(stdout.trim()));
      }
    });
  } else {
    response.status(400).send();
  }
});

app.listen(port, host, () => {
  console.log(`Service running at ${host}:${port}`);
});
