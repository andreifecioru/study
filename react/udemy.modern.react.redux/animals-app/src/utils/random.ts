function pickFromList<T>(aList: T[]): T | undefined {
  if (aList.length === 0) {
    return undefined;
  }
  const idx = Math.floor(Math.random() * aList.length);
  return aList[idx];
}

export {pickFromList};