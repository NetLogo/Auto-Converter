import { exec, type ExecException } from "child_process";
import { type Response } from "express";
import { realpathSync } from "fs";
import { env, exit } from "process";

import { parseVersion, type Version } from "./version.js";

interface Converter {
  version: string;
  java: string;
};

const converters: Converter[] = [
  { version: "5.0.0", java: locateJava("JAVA6") },
  { version: "7.0.4", java: locateJava("JAVA17") }
];

function locateJava(name: string): string {
  const java: string | undefined = env[name];

  if (!java || java.trim().length == 0) {
    console.error(`Error: You must set the environment variable ${name}.`);

    exit(1);
  }

  return java;
}

function nextConverter(version: string): number {
  if (parseInt(version.charAt(0)) < 5) {
    return 0;
  }

  return 1;
}

function execConversionsInternal(response: Response, path: string, index: number, threed: boolean): void {
  const converter: Converter | undefined = converters[index];

  if (!converter) {
    response.status(200).sendFile(path);

    return;
  }

  const options: string[] = [
    `-Dnetlogo.extensions.dir=out/lib/${converter.version}/extensions`,
    `-Dorg.nlogo.is3d=${threed}`
  ];

  const command: string = `${converter.java} -cp "out/lib/${converter.version}/*" ${options.join(" ")} org.nlogo.convert.AutoConverter "${path}"`;

  exec(command, (error: ExecException | null, stdout: string, stderr: string) => {
    if (error) {
      console.error(stderr);

      response.status(500).send();
    } else {
      execConversionsInternal(response, realpathSync(stdout.trim()), index + 1, threed);
    }
  });
}

function execConversions(response: Response, path: string): void {
  const version: Version | undefined = parseVersion(path);

  if (version) {
    execConversionsInternal(response, path, nextConverter(version.number), version.threed);
  } else {
    response.status(400).send();
  }
}

export { execConversions };
