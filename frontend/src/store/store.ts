import { reactive } from "vue";

import { type ConversionStatus } from "./status.js";

class Store {
  private displayProgress = false;

  private conversions: ConversionStatus[] = [];

  private abortController = new AbortController();

  inProgress(): boolean {
    return this.displayProgress;
  }

  beginProgress(): void {
    this.conversions = [];
    this.displayProgress = true;
    this.abortController = new AbortController();
  }

  endProgress(): void {
    this.abortController.abort();

    this.displayProgress = false;
    this.conversions = [];
  }

  getConversions(): ConversionStatus[] {
    return this.conversions;
  }

  addConversion(name: string): number {
    const id: number = this.conversions.length;

    this.conversions.push({
      status: "converting",
      id: id,
      name: name
    });

    return id;
  }

  succeed(id: number, file: string, data: Blob): void {
    if (this.conversions[id]) {
      this.conversions[id] = {
        status: "succeeded",
        id: id,
        name: this.conversions[id].name,
        file: file,
        data: data,
      };
    }
  }

  fail(id: number, error: string): void {
    if (this.conversions[id]) {
      this.conversions[id] = {
        status: "failed",
        id: id,
        name: this.conversions[id].name,
        error: error
      };
    }
  }

  abortSignal(): AbortSignal {
    return this.abortController.signal;
  }
}

export const store = reactive(new Store());
