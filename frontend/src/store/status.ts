interface Converting {
  readonly status: 'converting';
  readonly id: number;
  readonly name: string;
  readonly originalFile: File;
}

interface Succeeded {
  readonly status: 'succeeded';
  readonly id: number;
  readonly name: string;
  readonly file: string;
  readonly data: Blob;
  readonly originalFile: File;
}

interface Failed {
  readonly status: 'failed';
  readonly id: number;
  readonly name: string;
  readonly error: string;
  readonly originalFile: File;
}

type ConversionStatus = Converting | Succeeded | Failed;

export { type Converting, type Succeeded, type Failed, type ConversionStatus };
