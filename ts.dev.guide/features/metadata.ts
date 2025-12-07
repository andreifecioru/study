import 'reflect-metadata'; // Adds the Reflect var to the gloabl scope

const plane = {
    color: 'red'
}

Reflect.defineMetadata('note', 'hello there', plane);

const note = Reflect.getMetadata('note', plane);
console.log(`Note on plane: ${note}`);


type AnyFunction = (...args: any[]) => any
const callCount = (target: Object, key: string | symbol, descriptor?: TypedPropertyDescriptor<AnyFunction>): TypedPropertyDescriptor<AnyFunction> | void  => {
    const method = descriptor?.value
    if (typeof method !== 'function') return descriptor;

    descriptor!.value = function(...args: any[]) {
        // `this` is the instace at call time
        const instance = this as object;

        const _callCount: number = Reflect.hasMetadata('call_count', instance, key) 
            ? Reflect.getMetadata('call_count', instance, key) as number 
            : 0;

        Reflect.defineMetadata('call_count', _callCount + 1, instance, key);
        return method.apply(this, args);
    }

    return descriptor;
};

class Person {
    constructor(readonly firstName: string,
                readonly lastName: string,
                readonly age: number) {}

    @callCount
    fullName(): string {
        return `${this.firstName} ${this.lastName}`;
    }
}

const john = new Person('John', 'Smith', 30);

Reflect.defineMetadata('gender', 'M', john);
const gender = Reflect.getMetadata('gender', john);
console.log('gender', john);

console.log(john.fullName());
console.log(john.fullName());
console.log(john.fullName());

const _callCount = Reflect.getMetadata('call_count', john, 'fullName');
console.log(`Call count: ${_callCount}`);