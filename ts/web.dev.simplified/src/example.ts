// Quick verification that strict mode and ES modules work
const greet = (name: string): string => {
  return `Hello, ${name}!`;
};

console.log(greet("TypeScript"));
