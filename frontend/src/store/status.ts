interface Converting {
  readonly status: "converting";
  readonly id: number;
  readonly name: string;
}

interface Succeeded {
  readonly status: "succeeded";
  readonly id: number;
  readonly name: string;
  readonly file: string;
  readonly data: Blob;
}

interface Failed {
  readonly status: "failed";
  readonly id: number;
  readonly name: string;
  readonly error: string;
}

type ConversionStatus = Converting | Succeeded | Failed;

export { type ConversionStatus };
