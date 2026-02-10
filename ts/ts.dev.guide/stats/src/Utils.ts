function parseDate(dateStr: string): Date {
  const [day, month, year] = dateStr.split('/')
    .map(part => parseInt(part, 10));
  return new Date(year, month - 1, day);
}

export { parseDate };
