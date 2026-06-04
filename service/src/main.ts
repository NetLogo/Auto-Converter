import cors from "cors";
import express, { type Request, type Response } from "express";
import { realpathSync } from "fs";
import multer, { diskStorage } from "multer";

import { execConversions } from "./convert.js";

const port = 4242;

const app = express();
const mult = multer({
  storage: diskStorage({
    filename: (_: Request, file: Express.Multer.File, callback: (error: Error | null, filename: string) => any) => {
      callback(null, file.originalname);
    }
  })
});

app.use(cors());

app.post("/convert", mult.single("model"), (request: Request, response: Response) => {
  if (request.file) {
    execConversions(response, realpathSync(request.file.path));
  } else {
    response.status(400).send();
  }
});

app.listen(port, () => {
  console.log(`Service running on port ${port}`);
});
