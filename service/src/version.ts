import { XMLParser } from "fast-xml-parser";
import { readFileSync } from "fs";

interface Version {
  number: string;
  threed: boolean;
};

const xmlParser = new XMLParser({
  ignoreAttributes: false
});

function parseVersionL(path: string): Version | undefined {
  const match = readFileSync(path).toString().match(/@#\$#@#\$#@\nNetLogo (?:3D )?(.+?)\n/);

  if (match && match[1]) {
    return { number: match[1], threed: match[0].includes("3D") };
  }

  return undefined;
}

function parseVersionX(path: string): Version | undefined {
  const version: string | undefined = xmlParser.parse(readFileSync(path))["model"]["@_version"];

  if (version) {
    const match = version.match(/NetLogo (?:3D )?(.+)/);

    if (match && match[1]) {
      return { number: match[1], threed: match[0].includes("3D") };
    }

    return undefined;
  }

  return undefined;
}

function parseVersion(path: string): Version | undefined {
  if (path.endsWith(".nlogo") || path.endsWith(".nlogo3d")) {
    return parseVersionL(path);
  }

  return parseVersionX(path);
}

export { parseVersion, type Version };
