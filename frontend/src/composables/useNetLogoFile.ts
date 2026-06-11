/**
 * Helpers for reasoning about NetLogo model files.
 */

/** Extensions accepted by the converter. */
const ACCEPTED_EXTENSIONS = ['.nlogo', '.nlogo3d', '.nlogox', '.nlogox3d'] as const;

/** Extensions accepted by the converter, as a value for `<input accept>`. */
export const ACCEPTED_EXTENSIONS_STR = ACCEPTED_EXTENSIONS.join(',');

/** Extensions that are already in the modern XML-based format. */
const MODERN_EXTENSIONS = ['.nlogox', '.nlogox3d'] as const;

export type NetLogoFileVariant = 'old' | 'new' | 'invalid';

/** True when the file matches one of the accepted formats. */
export function isValidFormat(file: File): boolean {
  return ACCEPTED_EXTENSIONS.some((ext) => file.name.endsWith(ext));
}

/** True when the file is already in the modern `.nlogox`/`.nlogox3d` format. */
export function isModernFormat(file: File): boolean {
  return MODERN_EXTENSIONS.some((ext) => file.name.endsWith(ext));
}

/** The icon variant to display for a given model file. */
export function fileVariant(file: File): NetLogoFileVariant {
  if (isValidFormat(file)) {
    return isModernFormat(file) ? 'new' : 'old';
  }

  return 'invalid';
}

/** The model name with its NetLogo extension stripped. */
export function modelName(file: File): string {
  return file.name.replace(/\.nlogo.*$/, '');
}

/** The expected output filename after converting a legacy model. */
export function convertedName(file: File): string {
  return file.name.replace(/\.nlogo(3d)?$/, '.nlogox$1');
}

/** A human-readable file size, e.g. `12.34 KB`. */
export function formatFileSize(bytes: number): string {
  return `${(bytes / 1024).toFixed(2)} KB`;
}

export function useNetLogoFile() {
  return {
    ACCEPTED_EXTENSIONS_STR,
    isValidFormat,
    isModernFormat,
    fileVariant,
    modelName,
    convertedName,
    formatFileSize,
  };
}
